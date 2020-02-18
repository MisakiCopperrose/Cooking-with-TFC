package com.dunk.tfc.Items;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.BodyTempStats;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemDrink extends ItemTerra
{
	int waterRestore = 0;
	int tier = 0;
	private long heatProtectionDuration = 1800;
	private EnumFoodGroup foodGroup;

	public ItemDrink()
	{
		super();
		this.setFolder("food/");
		this.setContainerItem(TFCItems.glassBottle);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	public ItemDrink setWaterRestore(int i)
	{
		waterRestore = i;
		return this;
	}
	
	public ItemDrink setTier(int i)
	{
		tier = i;
		return this;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (waterRestore > 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(par3EntityPlayer);
			Boolean canDrink = fs.getMaxWater(par3EntityPlayer) - 250 > fs.waterLevel || this instanceof ItemAlcohol;
			if (canDrink)
			{
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			}
		}
		else
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		return par1ItemStack;
	}
	
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":Glass Bottle Overlay");
	}
	
	public ItemDrink setFoodGroup(EnumFoodGroup group)
	{
		this.foodGroup = group;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		if(this.foodGroup != null)
		{
			arraylist.add(ItemFoodTFC.getFoodGroupName(foodGroup));	
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return pass == 1 ? this.itemIcon : this.getContainerItem().getIcon(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass)
	{
		return pass == 1 ? FluidContainerRegistry.getFluidForFilledItem(is).getFluid().getColor()
				: super.getColorFromItemStack(is, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	protected ItemStack drinking(ItemStack is, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--is.stackSize;
		}
		if (waterRestore > 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			Boolean canDrink = fs.getMaxWater(player) - 500 > fs.waterLevel;
			if (canDrink)
			{
				TFC_Core.getPlayerFoodStats(player).restoreWater(player, waterRestore);
				if(foodGroup != null)
				{
					fs.addNutrition(foodGroup, 5);
				}
				BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
				if(bodyTemp.temporaryHeatProtection < 1)
				{
					bodyTemp.temporaryHeatProtection++;
				}
				if(bodyTemp.tempHeatTimeRemaining < heatProtectionDuration)
				{
					bodyTemp.tempHeatTimeRemaining = heatProtectionDuration;
				}
				TFC_Core.setBodyTempStats(player, bodyTemp);
			}
		}
		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		is = drinking(is, world, player);
		return postDrink(is, world, player);
	}

	protected ItemStack postDrink(ItemStack is, World world, EntityPlayer player)
	{
		// First try to add the empty bottle to an existing stack of bottles in
		// the inventory
		if (!player.capabilities.isCreativeMode
				&& !player.inventory.addItemStackToInventory(new ItemStack(TFCItems.glassBottle)))
		{
			// If we couldn't fit the empty bottle in the inventory, and we
			// drank the last alcohol bottle, put the empty bottle in the empty
			// held slot
			if (is.stackSize == 0)
				return new ItemStack(TFCItems.glassBottle);
			// If we couldn't fit the empty bottle in the inventory, and there
			// is more alcohol left in the stack, drop the bottle on the ground
			else
				player.dropPlayerItemWithRandomChoice(new ItemStack(TFCItems.glassBottle), false);
		}
		//Give experience
		if(tier > 0 && !world.isRemote)
		{
			int i = 2 * tier*tier;

            while (i > 0)
            {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                world.spawnEntityInWorld(new EntityXPOrb(world, player.posX, player.posY, player.posZ, j));
            }
		}
		return is;
	}
}
