package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.Player.SkillStats.SkillRank;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemHoneyBowl extends ItemTerra implements IFood
{

	@Override
	public EnumFoodGroup getFoodGroup()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
	}
	
	public static void addTasteInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		IFood food = (IFood) is.getItem();
		int sweet = food.getTasteSweet(is);
		int sour = food.getTasteSour(is);
		int salty = food.getTasteSalty(is);
		int bitter = food.getTasteBitter(is);
		int savory = food.getTasteSavory(is);
		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING);
		if(Food.hasMealSkill(is))
			rank = SkillRank.values()[Food.getMealSkill(is)];

		int[] prefs = TFC_Core.getPlayerFoodStats(player).getPrefTaste(is);

		String sSweet = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.sweet") + ": " + EnumChatFormatting.WHITE;
		String sSour = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.sour") + ": " + EnumChatFormatting.WHITE;
		String sSalty = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.salty") + ": " + EnumChatFormatting.WHITE;
		String sBitter = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.bitter") + ": " + EnumChatFormatting.WHITE;
		String sSavory = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.savory") + ": " + EnumChatFormatting.WHITE;

		if(rank == SkillRank.Novice)
		{
			sSweet += (sweet > prefs[0] ? TFC_Core.translate("gui.taste.novice.sweet1") : TFC_Core.translate("gui.taste.novice.sweet0"));
			sSour += (sour > prefs[1] ? TFC_Core.translate("gui.taste.novice.sour1") : TFC_Core.translate("gui.taste.novice.sour0"));
			sSalty += (salty > prefs[2] ? TFC_Core.translate("gui.taste.novice.salty1") : TFC_Core.translate("gui.taste.novice.salty0"));
			sBitter += (bitter > prefs[3] ? TFC_Core.translate("gui.taste.novice.bitter1") : TFC_Core.translate("gui.taste.novice.bitter0"));
			sSavory += (savory > prefs[4] ? TFC_Core.translate("gui.taste.novice.savory1") : TFC_Core.translate("gui.taste.novice.savory0"));
		}
		else if(rank == SkillRank.Adept)
		{
			sSweet += ItemFoodTFC.createBasicString(sweet, prefs[0], "sweet");
			sSour += ItemFoodTFC.createBasicString(sour, prefs[1], "sour");
			sSalty += ItemFoodTFC.createBasicString(salty, prefs[2], "salty");
			sBitter += ItemFoodTFC.createBasicString(bitter, prefs[3], "bitter");
			sSavory += ItemFoodTFC.createBasicString(savory, prefs[4], "savory");
		}
		else if(rank == SkillRank.Expert)
		{
			sSweet += ItemFoodTFC.createExpertString(sweet, prefs[0], "sweet");
			sSour += ItemFoodTFC.createExpertString(sour, prefs[1], "sour");
			sSalty += ItemFoodTFC.createExpertString(salty, prefs[2], "salty");
			sBitter += ItemFoodTFC.createExpertString(bitter, prefs[3], "bitter");
			sSavory += ItemFoodTFC.createExpertString(savory, prefs[4], "savory");
		}
		else if(rank == SkillRank.Master)
		{
			sSweet += ItemFoodTFC.createBasicString(sweet, prefs[0], "sweet") + " (" + (sweet - prefs[0]) + ")";
			sSour += ItemFoodTFC.createBasicString(sour, prefs[1], "sour") + " (" + (sour - prefs[1]) + ")";
			sSalty += ItemFoodTFC.createBasicString(salty, prefs[2], "salty") + " (" + (salty - prefs[2]) + ")";
			sBitter += ItemFoodTFC.createBasicString(bitter, prefs[3], "bitter") + " (" + (bitter - prefs[3]) + ")";
			sSavory += ItemFoodTFC.createBasicString(savory, prefs[4], "savory") + " (" + (savory - prefs[4]) + ")";
		}

		arraylist.add(sSweet);
		arraylist.add(sSour);
		arraylist.add(sSalty);
		arraylist.add(sBitter);
		arraylist.add(sSavory);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
	{
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entity);

		if (fs.needFood())
			entity.setItemInUse(is, this.getMaxItemUseDuration(is));

		return is;		
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if (!world.isRemote && foodstats.needFood())
		{
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

			if (is.hasTagCompound())
			{
				// NBTTagCompound nbt = is.getTagCompound();
				// float weight = ((IFood)(is.getItem())).getFoodWeight(is);
				// float decay =
				// Math.max(((IFood)(is.getItem())).getFoodDecay(is), 0);

				float tasteFactor = foodstats.getTasteFactor(is);
				foodstats.addNutrition(((IFood) (is.getItem())).getFoodGroup(), 20 * tasteFactor);
			}

			float eatAmount = 7.5f;
			float stomachDiff = foodstats.stomachLevel+eatAmount-foodstats.getMaxStomach(foodstats.player);
			if(stomachDiff > 0)
				eatAmount-=stomachDiff;
			foodstats.stomachLevel += eatAmount;
			TFC_Core.setPlayerFoodStats(player, foodstats);
			if (is.stackSize == 1)
			{
				if (player.worldObj.rand.nextInt(20) == 0)
					player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
							player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				else
					is = new ItemStack(TFCItems.potteryBowl, 1, 1);
			}
			else
			{
				is.stackSize--;
				if (player.worldObj.rand.nextInt(20) == 0)
					player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
							player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				else
					world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(TFCItems.potteryBowl, 1, 1)));
			}
			// is.stackTagCompound = null;
		}
		return is;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	@Override
	public int getFoodID()
	{
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public float getDecayRate(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFoodMaxWeight(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEdible(ItemStack is)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isUsable(ItemStack is)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTasteSweet(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 150;
	}

	@Override
	public int getTasteSour(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTasteSalty(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTasteBitter(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTasteSavory(ItemStack is)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean renderDecay()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renderWeight()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
