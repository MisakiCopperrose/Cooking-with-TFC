package com.dunk.tfc.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.Pottery.ItemPotterySheetMold;
import com.dunk.tfc.TileEntities.TEAnvil;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.HeatIndex;
import com.dunk.tfc.api.HeatRegistry;
import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.ISize;
import com.dunk.tfc.api.Interfaces.ISmeltable;
import com.dunk.tfc.api.Util.Helper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemTerra extends Item implements ISize, ISmeltable
{
	protected boolean stackable = true;
	protected EnumSize size = EnumSize.SMALL;
	protected EnumWeight weight = EnumWeight.LIGHT;
	
	protected EnumItemReach reach;

	public String[] metaNames;
	public IIcon[] metaIcons;
	public String textureFolder;

	private Metal myMetal;

	private int craftingXP = 1;

	public ItemTerra()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_MISC);
		textureFolder = "";
		reach = EnumItemReach.SHORT;
		setNoRepair();
	}

	public ItemTerra setMetaNames(String[] metanames)
	{
		metaNames = metanames.clone();
		this.hasSubtypes = true;
		return this;
	}

	public ItemTerra setCraftingXP(int m)
	{
		craftingXP = m;
		return this;
	}

	public int getCraftingXP()
	{
		return this.craftingXP;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		if (metaNames != null)
		{
			for (int i = 0; i < metaNames.length; i++)
			{
				list.add(new ItemStack(this, 1, i));
			}
		}
		else
		{
			list.add(new ItemStack(this, 1));
		}
	}

	public ItemTerra setMetal(Metal m)
	{
		myMetal = m;
		return this;
	}

	public Metal getMetal(Metal m)
	{
		return myMetal;
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		if (canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier <= 256
					? this.getSize(null).stackSize * getWeight(null).multiplier : 64;
		else
			return 1;
	}

	public ItemTerra setFolder(String s)
	{
		this.textureFolder = s;
		return this;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if (this.metaNames == null)
		{
			if (this.iconString != null)
				this.itemIcon = registerer
						.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + this.getIconString());
			else
				this.itemIcon = registerer.registerIcon(
						Reference.MOD_ID + ":" + this.textureFolder + this.getUnlocalizedName().replace("item.", ""));
		}
		else
		{
			metaIcons = new IIcon[metaNames.length];
			for (int i = 0; i < metaNames.length; i++)
			{
				metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + metaNames[i]);
			}

			// This will prevent NullPointerException errors with other mods
			// like NEI
			this.itemIcon = metaIcons[0];
		}
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		if (metaNames != null && i < metaNames.length)
		{
			return metaIcons[i];
		}
		else
			return this.itemIcon;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if (metaNames != null && itemstack.getItemDamage() < metaNames.length)
			return getUnlocalizedName().concat("." + metaNames[itemstack.getItemDamage()]);
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	/**
	 * This is called by inventories in the world to tick things such as
	 * temperature and food decay. Override this and return true if you want the
	 * item to be handled differently than the standard code. True will stop he
	 * standard TFC code from running.
	 */
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		return false;
	}

	public static void addSizeInformation(ItemStack object, List<String> arraylist)
	{

		if (((ISize) object.getItem()).getSize(object) != null && ((ISize) object.getItem()).getWeight(object) != null
				&& ((ISize) object.getItem()).getReach(object) != null)
			arraylist.add("\u2696"
					+ TFC_Core.translate("gui.Weight." + ((ISize) object.getItem()).getWeight(object).getName())
					+ " \u21F2" + TFC_Core.translate(
							"gui.Size." + ((ISize) object.getItem()).getSize(object).getName().replace(" ", "")));
		if (object.getItem() instanceof IEquipable)
		{
			if (((IEquipable) object.getItem()).getEquipType(object) == IEquipable.EquipType.BACK)
			{
				arraylist.add(EnumChatFormatting.LIGHT_PURPLE.toString() + TFC_Core.translate("gui.slot")
						+ EnumChatFormatting.GRAY.toString() + ": " + EnumChatFormatting.WHITE.toString()
						+ TFC_Core.translate("gui.slot.back"));
			}
		}
	}
	
	@Override
	 protected MovingObjectPosition getMovingObjectPositionFromPlayer(World p_77621_1_, EntityPlayer p_77621_2_, boolean p_77621_3_)
    {
        return Helper.getMovingObjectPositionFromPlayer(p_77621_1_, p_77621_2_, p_77621_3_, 4);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		// Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(is, arraylist);

		ItemTerra.addHeatInformation(is, arraylist);
		if (is.stackTagCompound != null && TFCOptions.enableDebugMode)
		{
			Iterator<String> keys = (is.stackTagCompound.func_150296_c()).iterator();
			String key = keys.next();
			//arraylist.add(key + " : " + is.stackTagCompound.getString(key));
			while (keys.hasNext() && (key = keys.next()) != null)
			{
				//if(key.equals("Items"))
				//{
				//	continue;
				//}
				arraylist.add(key + " : " + is.stackTagCompound.getString(key));
			}
		}
		if (is.hasTagCompound())
		{
			NBTTagCompound tag = is.getTagCompound();
			// Use either tag as a failsafe to display the tooltip
			if (tag.hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || tag.hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
				arraylist.add(TFC_Core.translate("gui.ItemWorked"));
		}
		arraylist.add(TFC_Core.translate("gui."+this.getReach(is).getName()));
		addItemInformation(is, player, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (is.getItem() instanceof ItemIngot || is.getItem() instanceof ItemMetalSheet
				|| is.getItem() instanceof ItemUnfinishedArmor || is.getItem() instanceof ItemBloom
				|| is.getItem() == TFCItems.wroughtIronKnifeHead)
		{
			if (TFC_ItemHeat.hasTemp(is))
			{
				String s = "";
				if (HeatRegistry.getInstance().isTemperatureDanger(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.danger") + " | ";
				}

				if (HeatRegistry.getInstance().isTemperatureWeldable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.weldable") + " | ";
				}

				if (HeatRegistry.getInstance().isTemperatureWorkable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.workable");
				}

				if (!"".equals(s))
					arraylist.add(s);
			}
		}
	}

	public static void addHeatInformation(ItemStack is, List<String> arraylist)
	{
		if (is.hasTagCompound())
		{
			if (TFC_ItemHeat.hasTemp(is))
			{
				float temp = TFC_ItemHeat.getTemp(is);
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if (hi != null)
					meltTemp = hi.meltTemp;
				else if(is.getItem() instanceof ItemPotterySheetMold)
				{
					meltTemp = ((ItemPotterySheetMold)is.getItem()).getMeltTemp(is);
				}
				if (meltTemp != -1)
				{
					if (is.getItem() == TFCItems.stick)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
			}
		}
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}

	@Override
	public boolean canStack()
	{
		return stackable;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return size;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return weight;
	}

	public ItemTerra setSize(EnumSize e)
	{
		size = e;
		return this;
	}

	public ItemTerra setWeight(EnumWeight e)
	{
		weight = e;
		return this;
	}
	
	public ItemTerra setReach(EnumItemReach r)
	{
		this.reach = r;
		return this;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return reach;
	}

	public boolean isActualMetal(ItemStack is)
	{

		return getMetalType(is) != Global.GARBAGE;
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		// By default, the metal is utter garbage
		if (is.getItem().equals(TFCItems.silica) || is.getItem().equals(TFCItems.moltenSilica))
		{
			return Global.SILICA;
		}
		if (is.getItem().equals(TFCItems.lime) || is.getItem().equals(TFCItems.moltenLime))
		{
			return Global.LIME;
		}
		if (is.getItem().equals(TFCItems.solidifiedGlass) || is.getItem().equals(TFCItems.moltenGlass) || is.getItem().equals(TFCItems.glassBottle))
		{
			return Global.GLASS;
		}
		if (is.getItem().equals(TFCItems.soda) || is.getItem().equals(TFCItems.moltenSoda) || (is.getItem().equals(TFCItems.powder) && is.getItemDamage()==13))
		{
			return Global.SODA;
		}
		return Global.GARBAGE;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		// TODO Auto-generated method stub
		if(is != null && is.getItem().equals(TFCItems.glassBottle))
		{
			return 80;
		}
		else if( (is.getItem().equals(TFCItems.powder) && is.getItemDamage()==14))
		{
			return 5;
		}
		return 10;
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		if (getMetalType(is).equals(Global.GARBAGE))
			return false;
		return true;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		// TODO Auto-generated method stub
		if (getMetalType(is).equals(Global.GARBAGE))
			return EnumTier.TierX;
		return EnumTier.Tier0;
	}
}
