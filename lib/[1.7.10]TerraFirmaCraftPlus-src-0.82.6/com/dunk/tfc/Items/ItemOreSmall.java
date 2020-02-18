package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemOreSmall extends ItemOre
{
	public ItemOreSmall()
	{
		super();
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.TINY);
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		if (metaIcons != null && i < metaIcons.length)
		{
			return metaIcons[i];
		}
		else
			return this.itemIcon;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < Global.ORE_METAL.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[Global.ORE_METAL.length];
		for(int i = 0; i < Global.ORE_METAL.length; i++)
		{
			metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder+getMetaname(i) + " Small Ore");
		}
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch (dam)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 16:
		case 17:
			return (short) TFCOptions.smallOreUnits;
		case Global.oreGrade1Offset + 0:
		case Global.oreGrade1Offset + 1:
		case Global.oreGrade1Offset + 2:
		case Global.oreGrade1Offset + 3:
		case Global.oreGrade1Offset + 4:
		case Global.oreGrade1Offset + 5:
		case Global.oreGrade1Offset + 6:
		case Global.oreGrade1Offset + 7:
		case Global.oreGrade1Offset + 8:
		case Global.oreGrade1Offset + 9:
		case Global.oreGrade1Offset + 10:
		case Global.oreGrade1Offset + 11:
		case Global.oreGrade1Offset + 12:
		case Global.oreGrade1Offset + 13:
		case Global.oreGrade1Offset + 16:
		case Global.oreGrade1Offset + 17:
			return (short) 15;
		case Global.oreGrade2Offset + 0:
		case Global.oreGrade2Offset + 1:
		case Global.oreGrade2Offset + 2:
		case Global.oreGrade2Offset + 3:
		case Global.oreGrade2Offset + 4:
		case Global.oreGrade2Offset + 5:
		case Global.oreGrade2Offset + 6:
		case Global.oreGrade2Offset + 7:
		case Global.oreGrade2Offset + 8:
		case Global.oreGrade2Offset + 9:
		case Global.oreGrade2Offset + 10:
		case Global.oreGrade2Offset + 11:
		case Global.oreGrade2Offset + 12:
		case Global.oreGrade2Offset + 13:
		case Global.oreGrade2Offset + 16:
		case Global.oreGrade2Offset + 17:
			return (short) 5;
		}
		return 0;
	}
}
