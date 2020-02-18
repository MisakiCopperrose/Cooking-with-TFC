package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISmeltable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemOre extends ItemTerra implements ISmeltable
{
	public ItemOre()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		/*
		 * metaNames = new String[]{ "Native Copper", "Native Gold",
		 * "Native Platinum", "Hematite", "Native Silver", "Cassiterite",
		 * "Galena", "Bismuthinite", "Garnierite", "Malachite", "Magnetite",
		 * "Limonite", "Sphalerite", "Tetrahedrite", "Bituminous Coal",
		 * "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite",
		 * "Graphite", "Kimberlite", "Petrified Wood", "Sulfur", "Jet",
		 * "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter",
		 * "Serpentine", "Sylvite", "Borax", "Olivine", "Lapis Lazuli",
		 * "Rich Native Copper", "Rich Native Gold", "Rich Native Platinum",
		 * "Rich Hematite", "Rich Native Silver", "Rich Cassiterite",
		 * "Rich Galena", "Rich Bismuthinite", "Rich Garnierite",
		 * "Rich Malachite", "Rich Magnetite", "Rich Limonite",
		 * "Rich Sphalerite", "Rich Tetrahedrite", "Poor Native Copper",
		 * "Poor Native Gold", "Poor Native Platinum", "Poor Hematite",
		 * "Poor Native Silver", "Poor Cassiterite", "Poor Galena",
		 * "Poor Bismuthinite", "Poor Garnierite", "Poor Malachite",
		 * "Poor Magnetite", "Poor Limonite", "Poor Sphalerite",
		 * "Poor Tetrahedrite"};
		 */
		setFolder("ore/");
		setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		if (getMetanamesLength() != 0)
		{
			/*for (int i = 0; i < Global.ORE_METAL.length; i++)
			{
				list.add(new ItemStack(this, 1, i));
			}*/
			for (int j = 0; j < 3; j++)
			{
				for (int i = 0; i < Global.ORE_METAL.length; i++)
				{
					list.add(new ItemStack(this, 1,i + (j==1?Global.oreGrade1Offset:j==2?Global.oreGrade2Offset:0)));
				}
			}
			for(int i = 0; i < Global.ORE_MINERAL.length;i++)
			{
				list.add(new ItemStack(this, 1,i + Global.oreSize));
			}
			for(int i = 0; i < Global.ORE_MINERAL2.length;i++)
			{
				list.add(new ItemStack(this, 1,i + Global.oreSize + Global.ORE_MINERAL.length));
			}
		}
		else
		{
			list.add(new ItemStack(this, 1));
		}
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[getMetanamesLength()];
		for (int j = 0; j < 3; j++)
		{
			for (int i = 0; i < Global.ORE_METAL.length; i++)
			{
				metaIcons[i + j * Global.ORE_METAL.length] = registerer
						.registerIcon(Reference.MOD_ID + ":" + textureFolder + getMetaname(i + (j==1?Global.oreGrade1Offset:j==2?Global.oreGrade2Offset:0)) + " Ore");
			}
		}
		for(int i = 0; i < Global.ORE_MINERAL.length;i++)
		{
			metaIcons[i + 3 * Global.ORE_METAL.length] = registerer
					.registerIcon(Reference.MOD_ID + ":" + textureFolder + getMetaname(i + Global.oreSize) + " Ore");
		}
		for(int i = 0; i < Global.ORE_MINERAL2.length;i++)
		{
			metaIcons[i + 3 * Global.ORE_METAL.length + Global.ORE_MINERAL.length] = registerer
					.registerIcon(Reference.MOD_ID + ":" + textureFolder + getMetaname(i + Global.oreSize + Global.ORE_MINERAL.length) + " Ore");
		}
	}

	private int getMetanamesLength()
	{
		return Global.ORE_METAL.length * 3 + Global.ORE_MINERAL.length + Global.ORE_MINERAL2.length;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if (getMetanamesLength() > 0)
			return getUnlocalizedName().concat("." + getMetaname(itemstack.getItemDamage()));
		return super.getUnlocalizedName(itemstack);
	}
	
	public int getMetaIconFromDamage(int i)
	{
		if (i < Global.ORE_METAL.length)
		{
			return i;
		}
		else if (i >= Global.oreGrade1Offset && i < Global.oreGrade1Offset + Global.ORE_METAL.length)
		{
			return (i-Global.oreGrade1Offset) + Global.ORE_METAL.length;
		}
		else if (i >= Global.oreGrade2Offset && i < Global.oreGrade2Offset + Global.ORE_METAL.length)
		{
			return  (i-Global.oreGrade2Offset )+ Global.ORE_METAL.length*2;
		}
		else if (i >= Global.oreSize)
		{
			return  (i-Global.oreSize) + Global.ORE_METAL.length * 3;
		}
		return 0;
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		int meta = getMetaIconFromDamage(i);
		if (metaIcons != null && meta < metaIcons.length)
		{
			return metaIcons[meta];
		}
		else
			return this.itemIcon;
	}

	protected String getMetaname(int i)
	{
		if (i < Global.ORE_METAL.length)
		{
			return Global.ORE_METAL[i];
		}
		else if (i >= Global.oreGrade1Offset && i < Global.oreGrade1Offset + Global.ORE_METAL.length)
		{
			return "Rich " + Global.ORE_METAL[i - Global.oreGrade1Offset];
		}
		else if (i >= Global.oreGrade2Offset && i < Global.oreGrade2Offset + Global.ORE_METAL.length)
		{
			return "Poor " + Global.ORE_METAL[i - Global.oreGrade2Offset];
		}
		else if (i >= Global.oreSize)
		{
			i -= Global.oreSize;
			if (i < Global.ORE_MINERAL.length)
			{
				return Global.ORE_MINERAL[i];
			}
			i -= Global.ORE_MINERAL.length;
			if (i < Global.ORE_MINERAL2.length)
			{
				return Global.ORE_MINERAL2[i];
			}
		}
		return "";
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (getMetalType(is) != null)
		{
			if (TFC_Core.showShiftInformation())
			{
				arraylist.add(TFC_Core.translate("gui.units") + ": " + getMetalReturnAmount(is));
			}
			else
			{
				arraylist.add(TFC_Core.translate("gui.ShowHelp"));
			}
		}
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		if (is.getItem().equals(TFCItems.silica))
		{
			return Global.SILICA;
		}
		else if (is.getItem().equals(TFCItems.lime))
		{
			return Global.LIME;
		}
		else if (is.getItem().equals(TFCItems.soda))
		{
			return Global.SODA;
		}
		else
		{
			int dam = is.getItemDamage();
			switch (dam)
			{
			case 0:
				return Global.COPPER;
			case 1:
				return Global.GOLD;
			case 2:
				return Global.PLATINUM;
			case 3:
				return Global.PIGIRON;
			case 4:
				return Global.SILVER;
			case 5:
				return Global.TIN;
			case 6:
				return Global.LEAD;
			case 7:
				return Global.BISMUTH;
			case 8:
				return Global.NICKEL;
			case 9:
				return Global.COPPER;
			case 10:
				return Global.PIGIRON;
			case 11:
				return Global.PIGIRON;
			case 12:
				return Global.ZINC;
			case 13:
				return Global.COPPER;
			case 16:
				return Global.COPPER;
			case 17:
				return Global.COPPER;
			// Rich Ores
			case Global.oreGrade1Offset + 0:
				return Global.COPPER;
			case Global.oreGrade1Offset + 1:
				return Global.GOLD;
			case Global.oreGrade1Offset + 2:
				return Global.PLATINUM;
			case Global.oreGrade1Offset + 3:
				return Global.PIGIRON;
			case Global.oreGrade1Offset + 4:
				return Global.SILVER;
			case Global.oreGrade1Offset + 5:
				return Global.TIN;
			case Global.oreGrade1Offset + 6:
				return Global.LEAD;
			case Global.oreGrade1Offset + 7:
				return Global.BISMUTH;
			case Global.oreGrade1Offset + 8:
				return Global.NICKEL;
			case Global.oreGrade1Offset + 9:
				return Global.COPPER;
			case Global.oreGrade1Offset + 10:
				return Global.PIGIRON;
			case Global.oreGrade1Offset + 11:
				return Global.PIGIRON;
			case Global.oreGrade1Offset + 12:
				return Global.ZINC;
			case Global.oreGrade1Offset + 13:
				return Global.COPPER;
			case Global.oreGrade1Offset + 16:
				return Global.COPPER;
			case Global.oreGrade1Offset + 17:
				return Global.COPPER;
			// Poor Ores
			case Global.oreGrade2Offset + 0:
				return Global.COPPER;
			case Global.oreGrade2Offset + 1:
				return Global.GOLD;
			case Global.oreGrade2Offset + 2:
				return Global.PLATINUM;
			case Global.oreGrade2Offset + 3:
				return Global.PIGIRON;
			case Global.oreGrade2Offset + 4:
				return Global.SILVER;
			case Global.oreGrade2Offset + 5:
				return Global.TIN;
			case Global.oreGrade2Offset + 6:
				return Global.LEAD;
			case Global.oreGrade2Offset + 7:
				return Global.BISMUTH;
			case Global.oreGrade2Offset + 8:
				return Global.NICKEL;
			case Global.oreGrade2Offset + 9:
				return Global.COPPER;
			case Global.oreGrade2Offset + 10:
				return Global.PIGIRON;
			case Global.oreGrade2Offset + 11:
				return Global.PIGIRON;
			case Global.oreGrade2Offset + 12:
				return Global.ZINC;
			case Global.oreGrade2Offset + 13:
				return Global.COPPER;
			case Global.oreGrade2Offset + 16:
				return Global.COPPER;
			case Global.oreGrade2Offset + 17:
				return Global.COPPER;
			}
		}
		return null;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		if (is.getItem().equals(TFCItems.silica))
		{
			return (short) 50;
		}
		else if (is.getItem().equals(TFCItems.lime) || is.getItem().equals(TFCItems.soda))
		{
			return (short) 25;
		}
		else
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
				return (short) TFCOptions.normalOreUnits;
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
			case Global.oreGrade1Offset +16:
			case Global.oreGrade1Offset +17:
				return (short) TFCOptions.richOreUnits;
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
			case Global.oreGrade2Offset +16:
			case Global.oreGrade2Offset +17:
				return (short) TFCOptions.poorOreUnits;
			// case 63: return (short) 50;
			// case 64:
			// case 65: return (short) 25;
			}
		}
		return 0;
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		switch (is.getItemDamage())
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
		case Global.oreGrade1Offset +16:
		case Global.oreGrade1Offset +17:
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
		case Global.oreGrade2Offset +16:
		case  Global.oreGrade2Offset +17:
			return true;
		default:
			return false;
		}
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch (dam)
		{
		case 0:
			return EnumTier.TierI;
		case 1:
			return EnumTier.TierI;
		case 2:
			return EnumTier.TierIV;
		case 3:
			return EnumTier.TierIII;
		case 4:
			return EnumTier.TierI;
		case 5:
			return EnumTier.TierI;
		case 6:
			return EnumTier.TierI;
		case 7:
			return EnumTier.TierI;
		case 8:
			return EnumTier.TierIII;
		case 9:
			return EnumTier.TierI;
		case 10:
			return EnumTier.TierIII;
		case 11:
			return EnumTier.TierIII;
		case 12:
			return EnumTier.TierI;
		case 13:
			return EnumTier.TierI;
		// Roch Ores
		case Global.oreGrade1Offset + 0:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 1:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 2:
			return EnumTier.TierIV;
		case Global.oreGrade1Offset + 3:
			return EnumTier.TierIII;
		case Global.oreGrade1Offset + 4:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 5:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 6:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 7:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 8:
			return EnumTier.TierIII;
		case Global.oreGrade1Offset + 9:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 10:
			return EnumTier.TierIII;
		case Global.oreGrade1Offset + 11:
			return EnumTier.TierIII;
		case Global.oreGrade1Offset + 12:
			return EnumTier.TierI;
		case Global.oreGrade1Offset + 13:
			return EnumTier.TierI;
		// Poor Ores
		case Global.oreGrade2Offset + 0:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 1:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 2:
			return EnumTier.TierIV;
		case Global.oreGrade2Offset + 3:
			return EnumTier.TierIII;
		case Global.oreGrade2Offset + 4:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 5:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 6:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 7:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 8:
			return EnumTier.TierIII;
		case Global.oreGrade2Offset + 9:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 10:
			return EnumTier.TierIII;
		case Global.oreGrade2Offset + 11:
			return EnumTier.TierIII;
		case Global.oreGrade2Offset + 12:
			return EnumTier.TierI;
		case Global.oreGrade2Offset + 13:
			return EnumTier.TierI;
		}
		return EnumTier.TierX;
	}

}
