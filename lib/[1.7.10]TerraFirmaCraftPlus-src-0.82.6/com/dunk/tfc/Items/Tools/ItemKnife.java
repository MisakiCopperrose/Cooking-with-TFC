package com.dunk.tfc.Items.Tools;

import java.util.List;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Render.Item.FoodItemRenderer;
import com.dunk.tfc.Render.Item.KnifeItemRenderer;
import com.dunk.tfc.TileEntities.TEFoodPrep;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Tools.IKnife;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemKnife extends ItemWeapon implements IKnife
{
	public ItemKnife(ToolMaterial e, float damage)
	{
		super(e, damage);
		this.setMaxDamage(e.getMaxUses());
		this.damageType = EnumDamageType.PIERCING;
	}
	
	@Override
	public EnumDamageType getDamageType(EntityLivingBase entity) 
	{
		if(entity instanceof EntityPlayer)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity));
			if(pi != null && pi.knifeMode==0)
			{
				return EnumDamageType.SLASHING;
			}
		}
		return damageType;
	}
	
	@Override
	public double getWeaponDamage(ItemStack is)
	{
		return super.getWeaponDamage(is)*0.5;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		super.registerIcons(registerer);
		MinecraftForgeClient.registerItemRenderer(this, new KnifeItemRenderer());
	}
	
	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block id = world.getBlock(x, y, z);
		if(!world.isRemote && id != TFCBlocks.toolRack)
		{
			int hasBowl = -1;

			for(int i = 0; i < 36 && hasBowl == -1;i++)
			{
				if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() == TFCItems.potteryBowl && entityplayer.inventory.mainInventory[i].getItemDamage() == 1)
					hasBowl = i;
			}

			Material mat = world.getBlock(x, y, z).getMaterial();

			if(side == 1 && id.isSideSolid(world, x, y, z, ForgeDirection.UP) &&!TFC_Core.isSoil(id) && !TFC_Core.isWater(id) && world.isAirBlock(x, y + 1, z) &&
					(mat == Material.wood || mat == Material.rock || mat == Material.iron))
			{
				world.setBlock(x, y + 1, z, TFCBlocks.foodPrep);
				TEFoodPrep te = (TEFoodPrep) world.getTileEntity(x, y + 1, z);
				if(hasBowl != -1 && te != null)
				{
					te.setInventorySlotContents(7, entityplayer.inventory.mainInventory[hasBowl]);
					entityplayer.inventory.mainInventory[hasBowl] = null;
				}
				return true;
			}
			if(side != 1 && side != 0)
			{
				Block b = world.getBlock(x, y, z);
				if(b instanceof BlockBranch || b instanceof BlockLogNatural)
				{					
					//We can try to get some resin
					itemstack.damageItem(1, entityplayer);
					world.playSoundAtEntity(entityplayer, "dig.wood", 1, 1);
					if(world.rand.nextInt(4)==0)
					{
						ItemStack resin = new ItemStack(TFCItems.resin);
						EntityItem ei = new EntityItem(world, entityplayer.posX,entityplayer.posY,entityplayer.posZ,resin);
						world.spawnEntityInWorld(ei);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public IIcon getIcon(ItemStack is, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		return super.getIcon(is, renderPass);
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		//arraylist.add(TFC_Core.translate("gui."+this.getReach(is).getName()));
		if (TFC_Core.showShiftInformation()) 
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.Knife.Inst0"));
			arraylist.add(TFC_Core.translate("gui.Knife.Inst1"));
			arraylist.add(TFC_Core.translate("gui.Knife.Inst2"));
			arraylist.add(TFC_Core.translate("gui.Knife.Inst3"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}
	
	@Override
	public int getAttackSpeed(EntityLivingBase entity)
	{
		return super.getAttackSpeed(entity) + (this.getDamageType(entity) == EnumDamageType.SLASHING?2:0);
	}

	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}

}