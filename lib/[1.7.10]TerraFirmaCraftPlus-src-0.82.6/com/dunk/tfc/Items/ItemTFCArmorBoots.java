package com.dunk.tfc.Items;

import java.util.HashMap;
import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Armor;
import com.dunk.tfc.api.Interfaces.IBoots;
import com.dunk.tfc.api.Interfaces.IEquipable.ClothingType;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemTFCArmorBoots extends ItemTFCArmor implements IBoots
{

	HashMap<Material,Float> walkableBonus;
	HashMap<Material,String> matNames;
	boolean defaultWalkable = false;
	float defaultSpeed = 0f;
	boolean trueBoots = false;
	
	public ItemTFCArmorBoots(Armor armor, int renderIndex, int armorSlot, int thermal, int type)
	{
		super(armor,renderIndex,armorSlot,thermal,type);
		walkableBonus = new HashMap<Material,Float>();
		matNames = new HashMap<Material,String>();
		matNames.put(Material.sand, "sand");
	}
	
	public ItemTFCArmorBoots(Armor armor, int renderIndex, int armorSlot, ArmorMaterial m, int thermal, int type)
	{
		super(armor, renderIndex, armorSlot, m, thermal, type);
		walkableBonus = new HashMap<Material,Float>();
		matNames = new HashMap<Material,String>();
		matNames.put(Material.sand, "sand");
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
			}
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
		public ClothingType getClothingType()
		{
			// TODO Auto-generated method stub
			return ClothingType.BOOTS;
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
