package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Items.Pottery.ItemPotteryMoldBase;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMeltedMetal extends ItemPotteryMoldBase
{
	public ItemMeltedMetal() 
	{
		super();
		setMaxDamage(101);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.setFolder("ingots/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
		this.hasSubtypes = true;
		this.metaNames = null;
	}	

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}
	

	@Override
	public IIcon getIconFromDamage(int i)
	{
			return this.itemIcon;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		// Partially-filled and hot unshaped ingots cannot stack
		if (isDamaged(is) || is.hasTagCompound() && TFC_ItemHeat.hasTemp(is))
		{
			return 1;
		}

		return super.getItemStackLimit(is);
	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{		
		if (is.getItemDamage() > 1)
		{
			arraylist.add(TFC_Core.translate("gui.units") + ": " + (100 - is.getItemDamage()) + " / 100");
		}
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is,world,entity,i,isSelected);
		if (is.hasTagCompound())
		{
			//NBTTagCompound stackTagCompound = is.getTagCompound();
			if(TFC_ItemHeat.hasTemp(is) && TFC_ItemHeat.getTemp(is) >= TFC_ItemHeat.isCookable(is))
			{
				if(is.getItemDamage()==0){
					is.setItemDamage(1);
				}
			}
			else if(is.getItemDamage()==1){
				is.setItemDamage(0);
			}

		}
		else if(is.getItemDamage()==1){
			is.setItemDamage(0);
		}
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getItemDamage() > 1;
	}
	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{	
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.MeltedMetal.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		//nothing
		list.add(new ItemStack(this, 1));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize <= 0) {
			itemstack.stackSize = 1;
		}

		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = itemstack.copy();

		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
		entityplayer.openGui(TerraFirmaCraft.instance, 38, world, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ);

		return itemstack;
	}
}
