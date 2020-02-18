package com.dunk.tfc.Items;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBandage extends ItemTerra
{
	// This means it also heals you when you use it
	boolean sterilized;
	boolean primitive;
	boolean splint;
	boolean cast;

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if (!player.isUsingItem() && (!splint && !cast && (TFC_Core.getPlayerFoodStats(player).pierceWoundTimer > 0 || TFC_Core.getPlayerFoodStats(player).slashWoundTimer > 0)
		|| ((this.cast && TFC_Core.getPlayerFoodStats(player).crushWoundTimer > 0) || (this.splint && TFC_Core.getPlayerFoodStats(player).crushWoundTimer > 0 && TFC_Core.getPlayerFoodStats(player).crushWoundTreatment < 1))
		|| (this.sterilized && player.getHealth() < player.getMaxHealth())))
		{
			player.setItemInUse(is, this.getMaxItemUseDuration(is));
		}
		return is;
	}

	public ItemBandage setSterilized(boolean b)
	{
		this.sterilized = b;
		return this;
	}
	
	public ItemBandage setSplint(boolean b)
	{
		this.splint = b;
		return this;
	}
	
	public ItemBandage setCast(boolean b)
	{
		this.cast = b;
		return this;
	}

	public ItemBandage setPrimitive(boolean b)
	{
		this.primitive = b;
		return this;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 40;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		if (!player.worldObj.isRemote)
		{
			if (count % 5 == 1)
			{
				if(!this.primitive)
				{
					player.worldObj.playSoundAtEntity(player, "step.cloth", 0.5F, 1f);
				}
				else
				{
					player.worldObj.playSoundAtEntity(player, "step.grass", 0.5F, 1f);
				}
			}
			if (count <= 1 && player.isUsingItem() && (((TFC_Core.getPlayerFoodStats(player).pierceWoundTimer > 0 || TFC_Core.getPlayerFoodStats(player).slashWoundTimer > 0)&&!this.primitive && !cast && !splint)
			|| ((this.cast && TFC_Core.getPlayerFoodStats(player).crushWoundTimer > 0) || (this.splint && TFC_Core.getPlayerFoodStats(player).crushWoundTimer > 0 && TFC_Core.getPlayerFoodStats(player).crushWoundTreatment <1))
			||(this.sterilized && player.getHealth() < player.getMaxHealth()) || (this.primitive && !cast && !splint && (TFC_Core.getPlayerFoodStats(player).pierceWoundTimer > 30*20 || TFC_Core.getPlayerFoodStats(player).slashWoundTimer > 30*20 || TFC_Core.getPlayerFoodStats(player).pierceWoundSeverity > 0 || TFC_Core.getPlayerFoodStats(player).slashWoundSeverity > 0) )))
			{
				if (!this.primitive && !cast && !splint)
				{
					if (TFC_Core.getPlayerFoodStats(player).pierceWoundTimer > 0)
					{
						FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
						fs.pierceWoundTimer = 0;
						fs.pierceWoundSeverity = 0;
						TFC_Core.setPlayerFoodStats(player, fs);
					}
					else if (TFC_Core.getPlayerFoodStats(player).slashWoundTimer > 0)
					{
						FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
						fs.slashWoundTimer = 0;
						fs.slashWoundSeverity = 0;
						TFC_Core.setPlayerFoodStats(player, fs);
					}
					if (this.sterilized && player.getHealth() < player.getMaxHealth())
					{
						player.heal(100);
					}
				}
				else if(this.primitive  && !cast && !splint)
				{
					FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
					if (TFC_Core.getPlayerFoodStats(player).pierceWoundTimer > 30 * 20)
					{					
						fs.pierceWoundTimer = 30 * 20;											
					}
					else if (TFC_Core.getPlayerFoodStats(player).slashWoundTimer > 30 * 20)
					{
						fs.slashWoundTimer = 30 * 20;
						
					}
					if(fs.pierceWoundSeverity > 0)
					{
						fs.pierceWoundSeverity = Math.max(fs.pierceWoundSeverity-1,0);
					}
					else if(fs.slashWoundSeverity > 0)
					{
						fs.slashWoundSeverity = Math.max(fs.slashWoundSeverity-1,0);
					}
					TFC_Core.setPlayerFoodStats(player, fs);
				}
				else if(this.cast)
				{
					if (TFC_Core.getPlayerFoodStats(player).crushWoundTimer > 0)
					{
						FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
						fs.crushWoundTimer = 0;
						fs.crushWoundSeverity = 0;
						fs.crushWoundTreatment = 0;
						TFC_Core.setPlayerFoodStats(player, fs);
					}
				}
				else if(this.splint)
				{
					if (TFC_Core.getPlayerFoodStats(player).crushWoundTimer > 0 && TFC_Core.getPlayerFoodStats(player).crushWoundTreatment < 1)
					{
						FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
						fs.crushWoundTreatment++;
						TFC_Core.setPlayerFoodStats(player, fs);
					}
				}
				stack.stackSize--;
				player.stopUsingItem();
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.block;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount)
	{
		// System.out.println("stop");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
	{
		if (entity instanceof IAnimal && !this.primitive && !splint && !cast)
		{
			int useTick = player.getItemInUseCount();
			IAnimal animal = (IAnimal) entity;
			if (!player.isUsingItem() && (animal.getNumberCuts() > 0 || animal.getNumberBleeding() > 0 || entity.getHealth() < entity.getMaxHealth()))
				player.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
			if (!player.worldObj.isRemote)
			{
				if (useTick == 0 && player.isUsingItem())
				{
					player.worldObj.playSoundAtEntity(player, "step.cloth", 0.5F, 1f);
					boolean used = false;
					if (this.sterilized && entity.getHealth() < entity.getMaxHealth())
					{
						entity.heal(150);
						used = true;
					}
					if (animal.getNumberCuts() > 0)
					{
						animal.familiarize(player);
						animal.healSlash();
						used = true;
					}
					else if (animal.getNumberBleeding() > 0)
					{
						animal.familiarize(player);
						animal.healPierce();
						used = true;
					}
					if (used)
					{
						itemstack.stackSize--;
						player.stopUsingItem();
						return true;
					}

				}
				return false;
			}
			return false;
		}
		return false;
	}
}
