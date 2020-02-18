package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.SkillStats.SkillRank;
import com.dunk.tfc.Food.CropIndex;
import com.dunk.tfc.Food.CropManager;
import com.dunk.tfc.TileEntities.TECrop;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemCustomSeeds extends ItemTerra
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private int cropId;

	public ItemCustomSeeds(int cropId)
	{
		super();
		this.cropId = cropId;
		this.setCreativeTab(TFCTabs.TFC_FOODS);
		this.setFolder("food/");
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (side != 1 || world.isRemote)
			return false;
		else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
		{
			Block var8 = world.getBlock(x, y, z);
			if ((var8 == TFCBlocks.tilledSoil || var8 == TFCBlocks.tilledSoil2) && world.isAirBlock(x, y + 1, z))
			{
				CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
				if (crop.needsSunlight && !TECrop.hasSunlight(world, x, y + 1, z))
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedSun"));
					return false;
				}
				
				if(crop.requiresLadder &&  !player.inventory.hasItem(Item.getItemFromBlock(Blocks.ladder)))
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedLadder"));
					return false;
				}

				if (TFC_Climate.getHeightAdjustedTemp(world, x, y, z) <= crop.minAliveTemp && !crop.dormantInFrost)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("gui.seeds.failedTemp"));
					return false;
				}

				world.setBlock(x, y + 1, z, TFCBlocks.crops);

				TECrop te = (TECrop) world.getTileEntity(x, y + 1, z);
				te.cropId = cropId;
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				world.markBlockForUpdate(x, y, z);
				--stack.stackSize;
				
				if(crop.requiresLadder)
				{
					player.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.ladder));
					//player.inventory.markDirty();
					player.inventoryContainer.detectAndSendChanges();
				}
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);

		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_AGRICULTURE);
		int nutrient = CropManager.getInstance().getCropFromId(cropId).getCycleType();

		if (rank == SkillRank.Expert || rank == SkillRank.Master)
		{
			switch (nutrient)
			{
			case 0:
				arraylist.add(EnumChatFormatting.RED + TFC_Core.translate("gui.Nutrient.A"));
				break;
			case 1:
				arraylist.add(EnumChatFormatting.GOLD + TFC_Core.translate("gui.Nutrient.B"));
				break;
			case 2:
				arraylist.add(EnumChatFormatting.YELLOW + TFC_Core.translate("gui.Nutrient.C"));
				break;
			default:
				break;
			}

		}
	}
}
