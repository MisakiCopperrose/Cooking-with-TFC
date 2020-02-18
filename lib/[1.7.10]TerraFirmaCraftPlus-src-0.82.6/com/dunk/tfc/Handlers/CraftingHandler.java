package com.dunk.tfc.Handlers;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.TerraFirmaCraftCore;
import com.dunk.tfc.Core.Recipes;
import com.dunk.tfc.Core.TFC_Achievements;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;
import com.dunk.tfc.Items.ItemClothingPiece;
import com.dunk.tfc.Items.ItemDyeCustom;
import com.dunk.tfc.Items.ItemIngot;
import com.dunk.tfc.Items.ItemMeltedMetal;
import com.dunk.tfc.Items.ItemMetalSheet;
import com.dunk.tfc.Items.ItemYarn;
import com.dunk.tfc.Items.ItemBlocks.ItemAnvil1;
import com.dunk.tfc.Items.ItemBlocks.ItemAnvil2;
import com.dunk.tfc.Items.Pottery.ItemPotterySheetMold;
import com.dunk.tfc.Items.Tools.ItemCustomPickaxe;
import com.dunk.tfc.Items.Tools.ItemCustomSaw;
import com.dunk.tfc.Items.Tools.ItemMiscToolHead;
import com.dunk.tfc.Items.Tools.ItemPlasterBucket;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Crafting.AnvilManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class CraftingHandler
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)// (EntityPlayer player, ItemStack
												// itemstack, IInventory
												// iinventory)
	{
		EntityPlayer player = e.player;
		ItemStack itemstack = e.crafting;
		Item item = itemstack.getItem();
		int itemDamage = itemstack.getItemDamage();
		IInventory iinventory = e.craftMatrix;

		// int index = 0;
		if (iinventory != null)
		{
			// Tool Damaging
			if (item == TFCItems.stoneBrick || item == TFCItems.slateShingle)
			{
				List<ItemStack> chisels = OreDictionary.getOres("itemChisel", false);
				handleItem(player, iinventory, chisels);
			}
			else if (item == TFCItems.singlePlank || item == Item.getItemFromBlock(TFCBlocks.woodSupportH)
					|| item == Item.getItemFromBlock(TFCBlocks.woodSupportH2)
					|| item == Item.getItemFromBlock(TFCBlocks.woodSupportV)
					|| item == Item.getItemFromBlock(TFCBlocks.woodSupportV2) || item == TFCItems.logs)
			{
				List<ItemStack> axes = OreDictionary.getOres("itemAxe", false);
				List<ItemStack> saws = OreDictionary.getOres("itemSaw", false);
				handleItem(player, iinventory, axes);
				handleItem(player, iinventory, saws);
			}
			else if(item instanceof ItemDyeCustom && itemstack.getItemDamage()==15)
			{
				//Assume we're doing a hollow bone into a flute?
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);
				boolean wasHollowBone = false;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					wasHollowBone |= wasHollowBone||(iinventory.getStackInSlot(i) != null && iinventory.getStackInSlot(i).getItem() == TFCItems.hollowBone);
				}
				Random r = new Random();
				if(wasHollowBone && r.nextInt(4)==0)
				{
					TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.boneFlute, 1, 0), player);
				}
				else if(wasHollowBone)
				{
					TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.boneFragment, 1, 0), player);
				}
			}
			else if(item == TFCItems.blowingHorn || item == Item.getItemFromBlock(TFCBlocks.beehive) || item == TFCItems.fletching || item == TFCItems.hornStrips || item == TFCItems.unstrungBow || item==TFCItems.pole || item==TFCItems.woodenSpear || item == TFCItems.sisalFiber)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);		
			}
			else if(item == TFCItems.honeybowl)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);
				TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.emptyHoneycomb, 1, 0), player);
			}
			else if(item == TFCItems.clayTile)
			{
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.logs)
					{
						ItemStack itemL = iinventory.getStackInSlot(i).copy();
						iinventory.setInventorySlotContents(i, itemL);
						iinventory.getStackInSlot(i).stackSize = iinventory.getStackInSlot(i).stackSize + 1;
					}
				}
			}
			else if(item == TFCItems.cast)
			{
				/*//Make sure to return the bucket after making the cast
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.clayBucketPlaster)
						TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.clayBucketEmpty,1,1), player);
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.woodenBucketPlaster)
						TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.woodenBucketEmpty), player);					
				}	*/			
			}
			else if (item == TFCItems.wool)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);
				int size = 0;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.sheepSkin)
						size = iinventory.getStackInSlot(i).getItemDamage();
				}

				TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.hide, 1, size), player);
			}
			//Scraping fur into hide
			else if (item == TFCItems.hide)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);				
			}
			else if (item == TFCItems.bearFurHat)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);				
				int size = 0;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.bearFur)
						size = iinventory.getStackInSlot(i).getItemDamage();
				}
				TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.bearFurScrap, 1, size), player);
			}
			else if (item == TFCItems.wolfFurHat)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);				
				int size = 0;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.wolfFur)
						size = iinventory.getStackInSlot(i).getItemDamage();
				}
				TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.wolfFurScrap, 1, size), player);
			}
			else if (item == TFCItems.woodenBucketPlaster || item == TFCItems.woodenBucketEmpty)
			{
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.woodenBucketWater
							|| iinventory.getStackInSlot(i).getItem() == TFCItems.woodenBucketGypsum)
					{
						NBTTagCompound tag1 = iinventory.getStackInSlot(i).stackTagCompound;
						if (tag1 == null)
						{
							tag1 = new NBTTagCompound();
						}
						tag1.setBoolean("plaster", true);
						iinventory.getStackInSlot(i).stackTagCompound = tag1;
					}
				}
			}
			else if (item == TFCItems.woolYarn || item == TFCItems.linenString || item == TFCItems.cottonYarn)
			{
				handleItem(player, iinventory, Recipes.spindle);
			}
			else if (item == TFCItems.powder && itemDamage == 0)
			{
				List<ItemStack> hammers = OreDictionary.getOres("itemHammer", false);
				handleItem(player, iinventory, hammers);
			}
			else if(item instanceof ItemClothingPiece)
			{
				boolean hasMat = false;
				for (int i = 0; i < iinventory.getSizeInventory() && !hasMat; i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.flatWool)
					{
						hasMat = true;
						NBTTagCompound wool = new NBTTagCompound();
						wool.setString("material", "wool");
						itemstack.stackTagCompound = wool;
						break;
					}
				}
			}
			else if (item == TFCItems.boneNeedleStrung || item == TFCItems.ironNeedleStrung)
			{
				boolean hasYarn = false;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() instanceof ItemYarn)
						hasYarn = true;
				}
				if (hasYarn)
				{
					if (itemstack.hasTagCompound())
					{
						itemstack.stackTagCompound.setBoolean("stringed", true);
						itemstack.stackTagCompound.setString("string", "yarn");
					}
					else
					{
						NBTTagCompound s = new NBTTagCompound();
						s.setBoolean("stringed", true);
						s.setString("string", "yarn");
						itemstack.stackTagCompound = s;
					}
				}
			}

			// Achievements
			triggerAchievements(player, itemstack, item, itemDamage);

			// Packet Sending
			if (item == Item.getItemFromBlock(TFCBlocks.workbench))
			{
				if (!player.getEntityData().hasKey("craftingTable"))
					player.inventory.clearInventory(Item.getItemFromBlock(TFCBlocks.workbench), -1);

				if (!player.worldObj.isRemote)
				{
					if (!player.getEntityData().hasKey("craftingTable"))
					{
						player.getEntityData().setBoolean("craftingTable", true);
						try
						{
							AbstractPacket pkt = new PlayerUpdatePacket(player, 2);
							TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
						}
						catch (Exception e1)
						{
							TerraFirmaCraft.LOG.info("--------------------------------------------------");
							TerraFirmaCraft.LOG.catching(e1);
							TerraFirmaCraft.LOG.info("--------------------------------------------------");
						}
						PlayerInventory.upgradePlayerCrafting(player);
					}
				}
			}

			if (!player.worldObj.isRemote && item instanceof ItemIngot)
			{
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					ItemStack is = iinventory.getStackInSlot(i);
					if (is == null)
						continue;
					else if (is.getItem() instanceof ItemMeltedMetal)
					{
						if (player.worldObj.rand.nextInt(20) == 0)
							player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
									player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
						else
							TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.ceramicMold, 1, 1), player);

						break;
					}
				}
			}
			if (!player.worldObj.isRemote && (item instanceof ItemMetalSheet || item == Item.getItemFromBlock(Blocks.glass_pane)))
			{
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					ItemStack is = iinventory.getStackInSlot(i);
					if (is == null)
						continue;
					else if (is.getItem() instanceof ItemPotterySheetMold)
					{
						if (player.worldObj.rand.nextInt(20) == 0)
							player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
									player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
						else
							TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.clayMoldSheet, 1, 1), player);

						break;
					}
				}
			}
		}
	}

	public static void preCraft(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		triggerAchievements(player, itemstack, itemstack.getItem(), itemstack.getItemDamage());
	}

	public static void triggerAchievements(EntityPlayer player, ItemStack itemstack, Item item, int itemDamage)
	{
		if (item instanceof ItemCustomPickaxe)
		{
			player.triggerAchievement(TFC_Achievements.achPickaxe);
		}
		else if (item instanceof ItemCustomSaw)
		{
			player.triggerAchievement(TFC_Achievements.achSaw);
		}
		else if (item instanceof ItemAnvil1 && itemDamage == 2
				|| item instanceof ItemAnvil2 && (itemDamage == 1 || itemDamage == 2))
		{
			player.triggerAchievement(TFC_Achievements.achBronzeAge);
		}
		else if (item == Item.getItemFromBlock(TFCBlocks.blastFurnace))
			player.triggerAchievement(TFC_Achievements.achBlastFurnace);
		else if (item == TFCItems.clayBall && itemDamage == 1)
			player.triggerAchievement(TFC_Achievements.achFireClay);
		else if (item == TFCItems.unknownIngot)
			player.triggerAchievement(TFC_Achievements.achUnknown);
	}

	/*
	 * Called during ContainerPlayerTFC's onCraftMatrixChanged to update the
	 * item in the output slot.
	 */
	public static void transferNBT(boolean clearContents, EntityPlayer player, ItemStack itemstack,
			IInventory iinventory)
	{
		Item item = itemstack.getItem();
		int itemDamage = itemstack.getItemDamage();
		if (item instanceof ItemPlasterBucket)
		{

			NBTTagCompound tag = itemstack.getTagCompound();
			if (tag == null)
			{
				tag = new NBTTagCompound();
				itemstack.stackTagCompound = tag;
			}
			tag = itemstack.getTagCompound();
			long totalH = TFC_Time.getTotalHours();
			tag.setLong("HardenTimer", totalH);
		}
		else if (item instanceof ItemIngot)
		{
			float temperature = 0;
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if (iinventory.getStackInSlot(i) == null)
					continue;
				if (iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
				{
					temperature = TFC_ItemHeat.getTemp(iinventory.getStackInSlot(i));
				}
			}
			TFC_ItemHeat.setTemp(itemstack, temperature);
		}
		else if (item instanceof ItemMeltedMetal)
		{
			float temperature = 0;
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if (iinventory.getStackInSlot(i) == null)
					continue;
				if (iinventory.getStackInSlot(i).getItem() instanceof ItemIngot)
					temperature = TFC_ItemHeat.getTemp(iinventory.getStackInSlot(i));
			}
			TFC_ItemHeat.setTemp(itemstack, temperature);
		}
		else if (item == TFCItems.potterySmallVessel && itemDamage == 0)
		{
			int color = -1;
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if (iinventory.getStackInSlot(i) == null)
					continue;

				int[] ids = OreDictionary.getOreIDs(iinventory.getStackInSlot(i));
				float[] c = null;
				for (int id : ids)
				{
					String name = OreDictionary.getOreName(id);
					for (int j = 0; j < Global.DYE_NAMES.length; j++)
					{
						if (name.equals(Global.DYE_NAMES[j]))
						{
							c = EntitySheep.fleeceColorTable[j];
							break;
						}
					}
				}

				if (c != null)
				{
					int r = (int) (c[0] * 255);
					int g = (int) (c[1] * 255);
					int b = (int) (c[2] * 255);
					r = r << 16;
					g = g << 8;

					color += r += g += b;
				}
			}

			if (color != -1)
			{
				NBTTagCompound nbt = null;
				if (itemstack.hasTagCompound())
					nbt = itemstack.getTagCompound();
				else
					nbt = new NBTTagCompound();

				nbt.setInteger("color", color);
				itemstack.setTagCompound(nbt);
			}
		}

		for (int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if (iinventory.getStackInSlot(i) == null)
				continue;

			// Only transfer the tag when making something out of a tool head.
			// Don't transfer when crafting things with the completed tool.
			// Note: If crafting recipes with armor or completed tools to
			// further refine them are ever added, the instanceof will need to
			// be updated. -Kitty
			if (iinventory.getStackInSlot(i).getItem() instanceof ItemMiscToolHead
					&& iinventory.getStackInSlot(i).hasTagCompound()
					&& iinventory.getStackInSlot(i).getTagCompound().hasKey("craftingTag"))
			{
				AnvilManager.setCraftTag(itemstack, AnvilManager.getCraftTag(iinventory.getStackInSlot(i)));
			}
		}
	}

	public static boolean gridHasItem(IInventory iinventory, Item item)
	{
		for (int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if (iinventory.getStackInSlot(i) == null)
				continue;
			if (iinventory.getStackInSlot(i).getItem() == item)
				return true;
		}
		return false;
	}

	public static void handleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] items)
	{
		for (int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if (iinventory.getStackInSlot(i) == null)
				continue;
			for (int j = 0; j < items.length; j++)
				damageItem(entityplayer, iinventory, i, items[j]);
		}
	}

	public static void handleItem(EntityPlayer entityplayer, IInventory iinventory, List<ItemStack> items)
	{
		for (int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if (iinventory.getStackInSlot(i) == null)
				continue;
			for (ItemStack is : items)
				damageItem(entityplayer, iinventory, i, is.getItem());
		}
	}

	public static void damageItem(EntityPlayer entityplayer, IInventory iinventory, int i, Item shiftedindex)
	{
		if (iinventory.getStackInSlot(i).getItem() == shiftedindex)
		{
			int index = i;
			ItemStack item = iinventory.getStackInSlot(index).copy();
			if (item != null)
			{
				item.damageItem(1, entityplayer);
				if (item.getItemDamage() != 0 || entityplayer.capabilities.isCreativeMode)
				{
					iinventory.setInventorySlotContents(index, item);
					iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
					if (iinventory.getStackInSlot(index).stackSize > 2)
						iinventory.getStackInSlot(index).stackSize = 2;
				}
			}
		}
	}

}
