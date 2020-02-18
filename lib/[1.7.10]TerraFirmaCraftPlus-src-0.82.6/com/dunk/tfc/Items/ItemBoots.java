package com.dunk.tfc.Items;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Interfaces.IBoots;
import com.dunk.tfc.api.Interfaces.IEquipable.ClothingType;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBoots extends ItemClothing implements IBoots
{
	HashMap<Material,Float> walkableBonus;
	HashMap<Material,String> matNames;
	boolean defaultWalkable = false;
	float defaultSpeed = 0f;
	boolean trueBoots = false;
	
	public ItemBoots(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType = c;
		walkableBonus = new HashMap<Material,Float>();
		matNames = new HashMap<Material,String>();
		matNames.put(Material.sand, "sand");
	}
	
	
	
	public ItemBoots(EquipType[] ex, ClothingType c)
	{
		super(ex);
		this.textureFolder +="armor/clothing/";
		myClothingType = c;
		walkableBonus = new HashMap<Material,Float>();
	}
	
	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		super.addExtraInformation(is, player, arraylist);
		//Extra information for boots or something.
		if (TFC_Core.showShiftInformation())
		{
			//Show specific material informations
			for(Material m : walkableBonus.keySet())
			{
				arraylist.add(TFC_Core.translate("gui.walkable_bonus."+matNames.get(m)) + ":  " + (walkableBonus.get(m)*100)+"%");
			}
			//Show default speed
			if(defaultWalkable)
			{
				arraylist.add(TFC_Core.translate("gui.default_walkable") + ":  " + (defaultSpeed*100)+"%");
			}			
			if(this == TFCItems.bearFurBoots || this == TFCItems.wolfFurBoots)
			{
				arraylist.add(TFC_Core.translate("gui.snow_walkable"));
			}
		}
	}
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EquipType.FEET;
	}

	//Add a material that these boots let you walk faster on
	public IBoots addWalkableSurface(Material surface, float speedBonus)
	{
		walkableBonus.put(surface, new Float(speedBonus));
		return this;
	}
	
	//Implies that you can walk more quickly on all surfaces by default, unless otherwise specified.
	public IBoots setDefaultWalkable(float defaultSpeed)
	{
		defaultWalkable = true;
		this.defaultSpeed = defaultSpeed;
		return this;
	}
	
	//gets the speed bonus on a surface
	public float getSpeedBonus(Material walkingOn)
	{
		if(walkableBonus.containsKey(walkingOn))
		{
			return walkableBonus.get(walkingOn);
		}
		else if(defaultWalkable)
		{
			return defaultSpeed;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public void onEquippedRender()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
		if(stack.getItem() == this)
		{
			return armorType==3;
		}
		return false;
    }
		

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areTrueBoots()
	{
		return trueBoots;
	}

	@Override
	public IBoots setTrueBoots(boolean b)
	{
		this.trueBoots = b;
		return this;
	}

}
