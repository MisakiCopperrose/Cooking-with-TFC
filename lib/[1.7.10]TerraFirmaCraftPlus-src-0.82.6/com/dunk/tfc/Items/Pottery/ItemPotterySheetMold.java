package com.dunk.tfc.Items.Pottery;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Interfaces.ISmeltable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemPotterySheetMold extends ItemPotteryMold implements ISmeltable {

	protected IIcon glassIcon;
	public ItemPotterySheetMold()
	{
		super();
		this.setMaxDamage(1006);
	}

	@Override
	public boolean isDamageable()
	{
		return this.getMaxDamage() > 0;
	}
	
	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{		
		if (is.getItemDamage() > 11)
		{
			int units = 201 - ((is.getItemDamage() - 2) / 5);
			arraylist.add(TFC_Core.translate("gui.units") + ": " + units + " / 200");
		}
	}
	
	@Override
	public Metal getMetalType(ItemStack stack)
	{
		int dmg = (stack.getItemDamage()-2)%5;
		switch(dmg)
		{
		case 0: return Global.COPPER;
		case 1: return Global.BRONZE;
		case 2: return Global.BISMUTHBRONZE;
		case 3: return Global.BLACKBRONZE;
		case 4: return Global.GLASS;
			default:
				return Global.GARBAGE;
		}

	}
	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		short units = 0;
		if(is.getItemDamage()>1 && is.getItemDamage()<=6)
		{
			return 200;
		}
		else
		{
			units = (short)(201 - ((is.getItemDamage() - 2) / 5));
		}
		return units;
	}
	
	@Override
	public boolean isSmeltable(ItemStack is)
	{
		return is.getItemDamage()>1;
	}
	
	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		//We control whether the sheet can be taken out by changing the damage value.
		super.onUpdate(is,world,entity,i,isSelected);
		if (is.hasTagCompound())
		{
			if(TFC_ItemHeat.hasTemp(is) && TFC_ItemHeat.getTemp(is) >= getMeltTemp(is) && is.getItemDamage() > 1 && is.getItemDamage() <= 6)
			{
				is.setItemDamage(is.getItemDamage()+5);
			}
			else if(is.getItemDamage() > 6 && is.getItemDamage() <= 11 && TFC_ItemHeat.getTemp(is) < getMeltTemp(is)){
				is.setItemDamage(is.getItemDamage()-5);
			}

		}
		else if(is.getItemDamage() > 6 && is.getItemDamage() <= 11){
			is.setItemDamage(is.getItemDamage()-5);
		}
	}
	
	public float getMeltTemp(ItemStack stack)
	{
		//if there's a metal
		Metal m = getMetalType(stack);
		if(m != null && m != Global.GARBAGE)
		{
			return m.getMeltingPoint();
		}
		return 5000;
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getItemDamage() > 11;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.clayIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[0]);
		this.ceramicIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[1]);
		if(metaNames.length > 2)
		{
			copperIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[2]);
			bronzeIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[3]);
			bismuthBronzeIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[4]);
			blackBronzeIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[5]);
			glassIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[6]);
		}
	}
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if(par1ItemStack !=null && par1ItemStack.getItemDamage() > 6)
		{
			int damage = (par1ItemStack.getItemDamage() - 2) % 5 + 2;
			return super.getUnlocalizedName(par1ItemStack) + "." + metaNames[damage];
		}
		return super.getUnlocalizedName(par1ItemStack);
	}

	@Override
	public IIcon getIconFromDamage(int damage)
	{
		if(damage > 6)
		{
			damage = (damage - 2) % 5 + 2;
		}
		if(damage == 0) return this.clayIcon;
		else if(damage == 1) return this.ceramicIcon;
		else if(damage == 2) return this.copperIcon;
		else if(damage == 3) return this.bronzeIcon;
		else if(damage == 4) return this.bismuthBronzeIcon;
		else if(damage == 5) return this.blackBronzeIcon;
		else if(damage == 6) return this.glassIcon;
		return this.clayIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
}
