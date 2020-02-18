package com.dunk.tfc.Food;

import java.util.List;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemEgg extends ItemFoodTFC implements IFood
{
	public ItemEgg()
	{
		super(EnumFoodGroup.Protein, 0, 0, 0, 0, 0, false, false);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1), 2));
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(getFoodGroupName(this.getFoodGroup()));
		addFoodHeatInformation(is, arraylist);

		if(is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
				arraylist.add(EnumChatFormatting.GOLD + TFC_Core.translate("gui.fertilized"));
			else
				addFoodInformation(is, player, arraylist);
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.badnbt"));
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
		}
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				is.stackTagCompound.removeTag("Fertilized");
				is.stackTagCompound.removeTag("Genes");
			}
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public float getDecayRate(ItemStack is)
	{
		if (Food.isPickled(is))
			return 0.3f;
		return 0.5f;
	}
}
