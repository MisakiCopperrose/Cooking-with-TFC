package com.dunk.tfc.Core.Player;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Render.EntityRendererTFC;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.FoodRegistry;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class FoodStatsTFC
{
	private boolean updateStats = !TFCOptions.enableDebugMode; // Replace with true to allow stat depleting with debug mode enabled

	/** The player's food level. This measures how much food the player can handle.*/
	public float stomachLevel = 48;
	private float stomachMax = 48.0f;
	private float prevFoodLevel = 48;

	private ResourceLocation drunkBlur = new ResourceLocation("shaders/post/blur.json");
	private ResourceLocation wastedBlur = new ResourceLocation("shaders/post/phosphor.json");//new ResourceLocation(Reference.MOD_ID + ":shaders/post/blurPhosphor.json");

	public float nutrFruit = 1.0f;
	public float nutrVeg = 1.0f;
	public float nutrGrain = 1.0f;
	public float nutrDairy = 1.0f;
	public float nutrProtein = 1.0f;
	private boolean sendUpdate = true;

	public long soberTime;
	
	public int pierceWoundTimer, slashWoundTimer;
	public int pierceWoundSeverity, slashWoundSeverity;
	public int crushWoundTimer, crushWoundSeverity, crushWoundTreatment;

	/**This is how full the player is from the food that they've eaten.
	 * It could also be how happy they are with what they've eaten*/
	private float satisfaction;

	private float foodExhaustionLevel;
	//private float waterExhaustionLevel;

	/** The player's food timer value. */
	public long foodTimer;
	public long foodHealTimer;
	public int temperatureDeathTimer = 768000;

	public float waterLevel = TFC_Time.DAY_LENGTH * 1.5F;
	public long waterTimer;

	public EntityPlayer player;
	private long nameSeed = Long.MIN_VALUE;
	private boolean satFruit;
	private boolean satVeg;
	private boolean satGrain;
	private boolean satProtein;
	private boolean satDairy;

	public FoodStatsTFC(EntityPlayer player)
	{
		this.player = player;
		waterTimer = Math.max(TFC_Time.getTotalTicks(),TFC_Time.startTime);
		foodTimer = Math.max(TFC_Time.getTotalTicks(),TFC_Time.startTime);
		foodHealTimer = Math.max(TFC_Time.getTotalTicks(),TFC_Time.startTime);
	}

	/**
	 * Handles the food game logic.
	 */
	public void onUpdate(EntityPlayer player)
	{
		if(!player.worldObj.isRemote)
		{
			BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
			float temp = TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);

			float tempWaterMod = temp;
			if(tempWaterMod >= 25)
				tempWaterMod = (tempWaterMod-25)*0.2f;
			else
				tempWaterMod = 0;

			if(this.pierceWoundTimer > 0)
			{
				this.pierceWoundTimer--;
			}
			if(this.slashWoundTimer > 0)
			{
				this.slashWoundTimer--;
			}
			if(this.crushWoundTimer > 0 && this.crushWoundTreatment > 0)
			{
				this.crushWoundTimer--;
			}
			if(this.pierceWoundTimer <= 0)
			{
				this.pierceWoundTimer = 0;
				this.pierceWoundSeverity = 0;
			}
			if(this.crushWoundTimer <= 0)
			{
				this.crushWoundTimer = 0;
				this.crushWoundSeverity = 0;
				this.crushWoundTreatment = 0;
			}
			if(this.slashWoundTimer <= 0)
			{
				this.slashWoundTimer = 0;
				this.slashWoundSeverity = 0;
			}
			if(this.pierceWoundTimer % ((10-this.pierceWoundSeverity)*6)==0 && this.pierceWoundTimer > 0)
			{
				this.player.attackEntityFrom(new DamageSource("wound").setDamageBypassesArmor().setDamageIsAbsolute(), 15 );
			}
			if(this.slashWoundTimer % ((10-this.slashWoundSeverity)*5)==0 && this.slashWoundTimer > 0)
			{
				this.player.attackEntityFrom(new DamageSource("wound").setDamageBypassesArmor().setDamageIsAbsolute(), 10 );
			}
			
			/*
			 * Standard filling reduction based upon time.
			 */
			if(this.foodTimer < TFC_Time.startTime)
			{
				this.foodTimer = TFC_Time.startTime;
				this.foodHealTimer = TFC_Time.startTime;
				this.waterTimer = TFC_Time.startTime;
			}
			float foodDrainMult = 1.0f - (player.experienceLevel/100F);
			float waterDrainMult = 1.0f - (player.experienceLevel/100F);
			//Can't switch :(
			float stomachPercent = this.stomachLevel/ this.getMaxStomach(player);
			
			if(bodyTemp.discomfort <= -1)
			{
				foodDrainMult*=1.25f;
			}
			if(bodyTemp.discomfort >=1)
			{
				waterDrainMult *= 1.25f;
			}
			//Hunger drains more slowly each day.
			if(stomachPercent > 0.5F)
			{
				//nothing
			}
			else if(stomachPercent > 0.25F)
			{
				foodDrainMult *= 0.5F;
			}
			else if(stomachPercent > 0.125F)
			{
				foodDrainMult *= 0.25F;
			}
			else if(stomachPercent > 0.0625F)
			{
				foodDrainMult *= 0.125F;
			}
			else if(stomachPercent > 0.03125F)
			{
				foodDrainMult *= 0.0625F;
			}
			else if(stomachPercent > 0.015625F)
			{
				foodDrainMult *= 0.0625F;
			}
			else
			{
				foodDrainMult *= 0.015625F;
			}
			
			//Same for water
			float waterPercent = this.waterLevel / this.getMaxWater(player);
			
			if(waterPercent > 0.333F){
				//Do nothing
			}
			else if(waterPercent > 0.111F){
				waterDrainMult *= 0.333F;
			}
			else
			{
				waterDrainMult *= 0.111F;
			}
			
			if(player.isPlayerSleeping())
			{
				foodDrainMult *= 0.50f;
				waterDrainMult *= 0.50f;
			}
			if (TFC_Time.getTotalTicks() - this.foodTimer >= TFC_Time.HOUR_LENGTH && !player.capabilities.isCreativeMode && updateStats)
			{
				this.foodTimer += TFC_Time.HOUR_LENGTH;
				//For each experience level, the player loses hunger and thirst more slowly.
				
				//Water
				if(player.isSprinting())
					waterLevel -= 5+(tempWaterMod)*waterDrainMult;
				//if (!player.capabilities.isCreativeMode && updateStats)
				//	waterLevel -= bodyTemp.getExtraWater()*waterDrainMult;

				//Food
				float hunger = (1 + foodExhaustionLevel /*+ bodyTemp.getExtraFood()*/) * foodDrainMult;
				if(this.satisfaction >= hunger)
				{
					satisfaction -= hunger;
					hunger = 0;
					foodExhaustionLevel = 0;
				}
				else
				{
					hunger -= satisfaction;
					satisfaction = 0;
					foodExhaustionLevel = 0;
				}
				this.stomachLevel = Math.max(this.stomachLevel - hunger, 0);

				if(satisfaction == 0)
				{
					satProtein = false; satFruit = false; satVeg = false; satDairy = false; satGrain = false;
				}

				if (this.stomachLevel <= 0.25)
				{
					reduceNutrition(0.0024F);//3x penalty for starving
				}
				else if(this.satisfaction <= 0)
				{
					reduceNutrition(0.0008F);
				}
				else
				{
					if(this.satProtein)
						this.addNutrition(EnumFoodGroup.Protein, this.satisfaction*((1-this.nutrProtein)/100), false);
					if(this.satGrain)
						this.addNutrition(EnumFoodGroup.Grain, this.satisfaction*((1-this.nutrGrain)/100), false);
					if(this.satVeg)
						this.addNutrition(EnumFoodGroup.Vegetable, this.satisfaction*((1-this.nutrVeg)/100), false);
					if(this.satFruit)
						this.addNutrition(EnumFoodGroup.Fruit, this.satisfaction*((1-this.nutrFruit)/100), false);
					if(this.satDairy)
						this.addNutrition(EnumFoodGroup.Dairy, this.satisfaction*((1-this.nutrDairy)/100), false);
				}
				sendUpdate = true;
			}

			boolean dead = false;
			//adjust the temperatureDeathTimer
			if(Math.abs(bodyTemp.discomfort) > 4 && !TFCOptions.enableDebugMode && !player.capabilities.isCreativeMode)
			{
				//50% faster for each additional level of discomfort
				temperatureDeathTimer -= 64 * Math.pow(1.5, Math.abs(bodyTemp.discomfort) - 4);
				if((int)(TFC_Time.getTotalTicks() % (100 * Math.pow(0.8f, Math.abs(bodyTemp.discomfort)-4))) == 0)
				{
					if(bodyTemp.discomfort > 0)
					{
						player.attackEntityFrom(new DamageSource("heatStroke").setDamageBypassesArmor().setDamageIsAbsolute(), 2);
					}
					else
					{
						player.attackEntityFrom(new DamageSource("frostBite").setDamageBypassesArmor().setDamageIsAbsolute(), 2);
					}
				}
				if(temperatureDeathTimer <= 0)
				{
					dead = true;
					if(bodyTemp.discomfort > 0)
					{
						player.attackEntityFrom(new DamageSource("heatStroke").setDamageBypassesArmor().setDamageIsAbsolute(), player.getMaxHealth() * 100);
					}
					else
					{
						player.attackEntityFrom(new DamageSource("frostBite").setDamageBypassesArmor().setDamageIsAbsolute(), player.getMaxHealth()* 100);
					}
				}
				//Calculate the time remaining
				bodyTemp.timeRemaining = (int)(temperatureDeathTimer / (20 * 64 * Math.pow(1.5, Math.abs(bodyTemp.discomfort) - 4)));
			}
			else if(bodyTemp.discomfort == 0)
			{
				temperatureDeathTimer = 768000;
				bodyTemp.timeRemaining = -1;
			}

			
			
			TFC_Core.setBodyTempStats(player, bodyTemp);
			
			//Heal or hurt the player based on hunger.
			if (TFC_Time.getTotalTicks() - this.foodHealTimer >= TFC_Time.HOUR_LENGTH/2 && !dead)
			{
				this.foodHealTimer += TFC_Time.HOUR_LENGTH/2;

				if (this.stomachLevel >= this.getMaxStomach(player)/2 && player.shouldHeal() && Math.abs(bodyTemp.discomfort) <= 4)
				{
					//Player heals 1% per 30 in game minutes
					
					player.heal((int) (player.getMaxHealth() * 0.01f  * (Math.abs(bodyTemp.discomfort)>=2?0.5f:1f)));
				}
				/*else if (this.stomachLevel <= 0 && getNutritionHealthModifier() < 0.85f && !TFC_Core.isPlayerInDebugMode(player) && player.getSleepTimer() == 0)
				{
					//Players loses health at a rate of 5% per 30 minutes if they are starving
					//Disabled so that the penalty for not eating is now entirely based upon nutrition.
					//player.attackEntityFrom(DamageSource.starve, Math.max((int) (player.getMaxHealth() * 0.05f), 10));
				}*/
			}

			if (!player.capabilities.isCreativeMode && updateStats)
			{
				for(;waterTimer < TFC_Time.getTotalTicks();  waterTimer++)
				{
					/**Reduce the player's water for normal living*/
					waterLevel -= (1+(tempWaterMod/2))*waterDrainMult;
					if(waterLevel < 0)
						waterLevel = 0;
					if(!TFC_Core.isPlayerInDebugMode(player) && waterLevel == 0 /*&& temp > 35*/)
						player.attackEntityFrom(new DamageSource("heatStroke").setDamageBypassesArmor().setDamageIsAbsolute(), 2);
					if(!TFC_Core.isPlayerInDebugMode(player) && this.stomachLevel == 0 /*&& temp > 35*/)
						player.attackEntityFrom(new DamageSource("starvation").setDamageBypassesArmor().setDamageIsAbsolute(), 2);
				}
				
			}
		}
	}
	
	public void drainScaledFood(float f)
	{
		BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
		
		float foodDrainMult = 1.0f - (player.experienceLevel/100F);
		//Can't switch :(
		float stomachPercent = this.stomachLevel/ this.getMaxStomach(player);
		
		if(bodyTemp.discomfort <= -1)
		{
			foodDrainMult*=1.25f;
		}
		
		//Hunger drains more slowly each day.
		if(stomachPercent > 0.5F)
		{
			//nothing
		}
		else if(stomachPercent > 0.25F)
		{
			foodDrainMult *= 0.5F;
		}
		else if(stomachPercent > 0.125F)
		{
			foodDrainMult *= 0.25F;
		}
		else if(stomachPercent > 0.0625F)
		{
			foodDrainMult *= 0.125F;
		}
		else if(stomachPercent > 0.03125F)
		{
			foodDrainMult *= 0.0625F;
		}
		else if(stomachPercent > 0.015625F)
		{
			foodDrainMult *= 0.0625F;
		}
		else
		{
			foodDrainMult *= 0.015625F;
		}
		
		if(player.isPlayerSleeping())
		{
			foodDrainMult *= 0.50f;
		}
		
		this.stomachLevel -= f * foodDrainMult;
	}
	
	public void drainScaledWater(float f)
	{
		BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
		
		float waterDrainMult = 1.0f - (player.experienceLevel/100F);
		//Can't switch :(
		float stomachPercent = this.stomachLevel/ this.getMaxStomach(player);
		
		if(bodyTemp.discomfort >=1)
		{
			waterDrainMult *= 1.25f;
		}
		//Same for water
		float waterPercent = this.waterLevel / this.getMaxWater(player);
		
		if(waterPercent > 0.333F){
			//Do nothing
		}
		else if(waterPercent > 0.111F){
			waterDrainMult *= 0.333F;
		}
		else
		{
			waterDrainMult *= 0.111F;
		}
		
		if(player.isPlayerSleeping())
		{
			waterDrainMult *= 0.50f;
		}
		this.waterLevel -= f * waterDrainMult;
	}

	public void clientUpdate()
	{
		if(Minecraft.getMinecraft().entityRenderer instanceof EntityRendererTFC)
		{
			EntityRendererTFC erTFC = (EntityRendererTFC) Minecraft.getMinecraft().entityRenderer;
			if((erTFC.getCurrentShaderLocation() == null || !erTFC.getCurrentShaderLocation().equals(wastedBlur )|| TFC_Time.getTotalTicks()%40==0) && soberTime > TFC_Time.getTotalTicks()+12000)
			{
				erTFC.setManualShader(wastedBlur);
			}
			else if((erTFC.getCurrentShaderLocation() == null || erTFC.getCurrentShaderLocation().equals(drunkBlur)|| TFC_Time.getTotalTicks()%40==0) && soberTime > TFC_Time.getTotalTicks()+8000 && soberTime <= TFC_Time.getTotalTicks()+12000)
			{
				erTFC.setManualShader(drunkBlur);
			}
			else if(erTFC.getManualShaderBeingUsed() && soberTime <= TFC_Time.getTotalTicks()+12000)
			{
				erTFC.deactivateManualShader();
			}
			
		}
	}

	protected void reduceNutrition(float amount)
	{
		nutrFruit = Math.max(this.nutrFruit - (amount + foodExhaustionLevel), 0);
		nutrVeg = Math.max(this.nutrVeg - (amount + foodExhaustionLevel), 0);
		nutrGrain = Math.max(this.nutrGrain - (amount + foodExhaustionLevel), 0);
		nutrProtein = Math.max(this.nutrProtein - (amount + foodExhaustionLevel), 0);
		nutrDairy = Math.max(this.nutrDairy - (amount + foodExhaustionLevel), 0);

		sendUpdate = true;
	}

	public int getMaxWater(EntityPlayer player)
	{
		return (int)(TFC_Time.DAY_LENGTH * 1.5F);
	}

	public float getMaxStomach(EntityPlayer player)
	{
		return this.stomachMax;
	}

	/**
	 * Get the player's food level.
	 */
	public float getFoodLevel()
	{
		return this.stomachLevel;
	}

	@SideOnly(Side.CLIENT)
	public float getPrevFoodLevel()
	{
		return this.prevFoodLevel ;
	}

	/**
	 * If foodLevel is not max.
	 */
	public boolean needFood()
	{
		return this.stomachLevel < getMaxStomach(this.player) && (getMaxStomach(this.player) - stomachLevel) > 0.1;
	}

	public boolean needDrink()
	{
		return this.waterLevel < getMaxWater(this.player) - 500;
	}

	/**
	 * Reads food stats from an NBT object.
	 */
	public void readNBT(NBTTagCompound par1NBTTagCompound)
	{
		if (par1NBTTagCompound.hasKey("foodCompound"))
		{
			NBTTagCompound foodCompound = par1NBTTagCompound.getCompoundTag("foodCompound");
			this.waterLevel = foodCompound.getFloat("waterLevel");
			this.stomachLevel = foodCompound.getFloat("foodLevel");
			this.foodTimer = foodCompound.getLong("foodTickTimer");
			this.temperatureDeathTimer = foodCompound.getInteger("temperatureDeathTimer");
			this.foodHealTimer = foodCompound.getLong("foodHealTimer");
			this.waterTimer = foodCompound.getLong("waterTimer");
			this.soberTime = foodCompound.getLong("soberTime");
			this.satisfaction = foodCompound.getFloat("foodSaturationLevel");
			this.foodExhaustionLevel = foodCompound.getFloat("foodExhaustionLevel");
			this.nutrFruit = foodCompound.getFloat("nutrFruit");
			this.nutrVeg = foodCompound.getFloat("nutrVeg");
			this.nutrGrain = foodCompound.getFloat("nutrGrain");
			this.nutrProtein = foodCompound.getFloat("nutrProtein");
			this.nutrDairy = foodCompound.getFloat("nutrDairy");
			this.sendUpdate = foodCompound.getBoolean("shouldSendUpdate");
			this.satFruit = foodCompound.getBoolean("satFruit");
			this.satVeg = foodCompound.getBoolean("satVeg");
			this.satGrain = foodCompound.getBoolean("satGrain");
			this.satProtein = foodCompound.getBoolean("satProtein");
			this.satDairy = foodCompound.getBoolean("satDairy");
			this.slashWoundSeverity = foodCompound.getInteger("slashSeverity");
			this.pierceWoundSeverity = foodCompound.getInteger("pierceSeverity");
			this.crushWoundSeverity = foodCompound.getInteger("crushSeverity");
			this.slashWoundTimer = foodCompound.getInteger("slashTimer");
			this.pierceWoundTimer = foodCompound.getInteger("pierceTimer");
			this.crushWoundTimer = foodCompound.getInteger("crushTimer");
			this.crushWoundTreatment = foodCompound.getInteger("crushTreatment");
		}
	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound nbt)
	{
		NBTTagCompound foodNBT = new NBTTagCompound();
		foodNBT.setFloat("waterLevel", this.waterLevel);
		foodNBT.setFloat("foodLevel", this.stomachLevel);
		foodNBT.setLong("foodTickTimer", this.foodTimer);
		foodNBT.setInteger("temperatureDeathTimer", this.temperatureDeathTimer);
		foodNBT.setLong("foodHealTimer", this.foodHealTimer);
		foodNBT.setLong("waterTimer", this.waterTimer);
		foodNBT.setLong("soberTime", this.soberTime);
		foodNBT.setFloat("foodSaturationLevel", this.satisfaction);
		foodNBT.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
		foodNBT.setFloat("nutrFruit", nutrFruit);
		foodNBT.setFloat("nutrVeg", nutrVeg);
		foodNBT.setFloat("nutrGrain", nutrGrain);
		foodNBT.setFloat("nutrProtein", nutrProtein);
		foodNBT.setFloat("nutrDairy", nutrDairy);
		foodNBT.setBoolean("shouldSendUpdate", sendUpdate);
		foodNBT.setBoolean("satFruit", satFruit);
		foodNBT.setBoolean("satVeg", satVeg);
		foodNBT.setBoolean("satGrain", satGrain);
		foodNBT.setBoolean("satProtein", satProtein);
		foodNBT.setBoolean("satDairy", satDairy);
		foodNBT.setInteger("slashTimer", this.slashWoundTimer);
		foodNBT.setInteger("pierceTimer", this.pierceWoundTimer);
		foodNBT.setInteger("slashSeverity", this.slashWoundSeverity);
		foodNBT.setInteger("pierceSeverity", this.pierceWoundSeverity);
		foodNBT.setInteger("crushSeverity", this.crushWoundSeverity);
		foodNBT.setInteger("crushTimer", this.crushWoundTimer);
		foodNBT.setInteger("crushTreatment", this.crushWoundTreatment);
		nbt.setTag("foodCompound", foodNBT);
	}

	public void addFoodExhaustion(float par1)
	{
		this.foodExhaustionLevel = par1;
	}

	/*public void addWaterExhaustion(float par1)
	{
		this.waterExhaustionLevel = par1;
	}*/

	public float getSatisfaction()
	{
		return this.satisfaction;
	}

	public void setFoodLevel(float par1)
	{
		if(par1 != this.stomachLevel)
			sendUpdate = true;
		this.stomachLevel = par1;

	}

	public void setSatisfaction(float par1, int[] fg)
	{
		this.satisfaction = Math.min(par1, 10);
		for(int i = 0; i < fg.length; i++)
		{
			if(fg[i] != -1)
			{
				EnumFoodGroup efg = FoodRegistry.getInstance().getFoodGroup(fg[i]);
				switch(efg)
				{
				case Protein:satProtein = true;break;
				case Grain:satGrain = true;break;
				case Fruit:satFruit = true;break;
				case Vegetable:satVeg = true;break;
				case Dairy:satDairy = true;break;
				default:
					break;
				}
			}
		}
	}

	public long getPlayerFoodSeed()
	{
		if(nameSeed == Long.MIN_VALUE)
		{
			long seed = 0;
			byte[] nameBytes = player.getCommandSenderName().getBytes();
			for(byte b : nameBytes)
				seed+=b;
			nameSeed = seed + player.worldObj.getSeed();
		}
		return nameSeed;
	}

	/***
	 * @return Returns an array containing taste preferences in the following order: Sweetness, Sourness, Saltiness, Bitterness, Savoriness
	 */
	public int[] getPrefTaste()
	{
		Random r = new Random(getPlayerFoodSeed());
		return new int[]
				{ 20 + r.nextInt(70), 20 + r.nextInt(70), 20 + r.nextInt(70), 20 + r.nextInt(70), 20 + r.nextInt(70) };
	}
	
	
	public int[] getPrefTaste(ItemStack food)
	{
		Random r = new Random(getPlayerFoodSeed());
		int[] tastePref = new int[]
				{ 20 + r.nextInt(70), 20 + r.nextInt(70), 20 + r.nextInt(70), 20 + r.nextInt(70), 20 + r.nextInt(70) };
		int[] tasteIdeals = new int[]{0,0,0,0,0};
		
		float totalTaste = ((IFood)food.getItem()).getTasteSweet(food) + ((IFood)food.getItem()).getTasteSour(food)
				+ ((IFood)food.getItem()).getTasteSalty(food) + ((IFood)food.getItem()).getTasteBitter(food)
			+((IFood)food.getItem()).getTasteSavory(food);
		
		
		
		int[] fgs = Food.getFoodGroups(food);
		int numFgs = 0;
		for(int i = 0; i < fgs.length;i++)
		{
			if(fgs[i] != -1)
			{
				EnumFoodGroup fg = FoodRegistry.getInstance().getFoodGroup(fgs[i]);
				for(int j = 0; j < 5; j++)
				{
					tasteIdeals[j] += fg.tastePreferences[j]*totalTaste;
				}
				numFgs++;
			}
		}
		for(int j = 0; j < 5; j++)
		{
			tastePref[j] = (tastePref[j] + tasteIdeals[j]*(3+numFgs))/(4+numFgs);
		}
		return tastePref;
	}

	public float getTasteFactor(ItemStack food)
	{
		//Random R = new Random(getPlayerFoodSeed());
		float tasteFactor = 0.85f;
		int[] tastePref = getPrefTaste(food);
		
		tasteFactor += getTasteDistanceFactor(tastePref[0], ((IFood)food.getItem()).getTasteSweet(food));
		tasteFactor += getTasteDistanceFactor(tastePref[1], ((IFood)food.getItem()).getTasteSour(food));
		tasteFactor += getTasteDistanceFactor(tastePref[2], ((IFood)food.getItem()).getTasteSalty(food));
		tasteFactor += getTasteDistanceFactor(tastePref[3], ((IFood)food.getItem()).getTasteBitter(food));
		tasteFactor += getTasteDistanceFactor(tastePref[4], ((IFood)food.getItem()).getTasteSavory(food));
		
		System.out.println("tasteFactor: "+tasteFactor);
		return tasteFactor;
	}
	
	public float getTasteDistanceSq(int pref, int actual)
	{
		float difSq = (float)Math.pow((pref-actual)/100f, 1);
		return difSq;
	}
	
	public float getTasteDistanceFactor(int pref, int val)
	{
		float abs = Math.abs(pref-val)/100f;
		abs *= abs;
		abs*=100f;
		if(abs < 10)
			return (10-abs)*0.005f;
		return 0;
	}

	public float getNutritionHealthModifier()
	{
		float nMod = 0.00f;
		nMod += 0.2f * nutrFruit;
		nMod += 0.2f * nutrVeg;
		nMod += 0.2f * nutrGrain;
		nMod += 0.2f * nutrProtein;
		nMod += 0.2f * nutrDairy;
		return Math.max(nMod, 0.05f);
	}

	public static int getMaxHealth(EntityPlayer player)
	{
		return (int)(Math.min(1000+(player.experienceLevel * TFCOptions.healthGainRate),
				TFCOptions.healthGainCap) * TFC_Core.getPlayerFoodStats(player).getNutritionHealthModifier());
	}

	/**
	 *
	 * @return return true if the itemstack should be consumed, else return false
	 */
	public static boolean reduceFood(ItemStack is, float amount)
	{
		if(is.hasTagCompound())
		{
			float weight = Food.getWeight(is);
			float decay = Food.getDecay(is);
			if(decay >= 0 && (weight - decay) - amount <= 0)
				return true;
			else if(decay <= 0 && weight - amount <= 0)
				return true;
			else
			{
				Food.setWeight(is, weight - amount);
			}
		}
		return false;
	}

	public void addNutrition(EnumFoodGroup fg, float foodAmt)
	{
		//foodAmt*=3;
		//foodAmt*=3;
		addNutrition(fg, foodAmt, true);
	}

	public void addNutrition(EnumFoodGroup fg, float foodAmt, boolean shouldDoMath)
	{
		float amount = foodAmt;
		if(shouldDoMath)
			amount = foodAmt/5f/50f;//converts it to 5% if it is 5oz of food
		amount*=5;
		switch(fg)
		{
		case Dairy:
			this.nutrDairy = Math.min(nutrDairy + amount, 1.0f);
			break;
		case Fruit:
			this.nutrFruit = Math.min(nutrFruit + amount, 1.0f);
			break;
		case Grain:
			this.nutrGrain = Math.min(nutrGrain + amount, 1.0f);
			break;
		case Protein:
			this.nutrProtein = Math.min(nutrProtein + amount, 1.0f);
			break;
		case Vegetable:
			this.nutrVeg = Math.min(nutrVeg + amount, 1.0f);
			break;
		default:
			break;
		}
	}

	public boolean shouldSendUpdate()
	{
		return sendUpdate;
	}

	public void restoreWater(EntityPlayer player, int w)
	{
		this.waterLevel = Math.min(this.waterLevel + w, this.getMaxWater(player));
		sendUpdate = true;
		this.writeNBT(player.getEntityData());
	}

	public void resetTimers()
	{
		waterTimer = TFC_Time.getTotalTicks();
		foodTimer = TFC_Time.getTotalTicks();
		foodHealTimer = TFC_Time.getTotalTicks();
		temperatureDeathTimer = 768000;
	}

	public void consumeAlcohol(float alcoholContent)
	{
		//TODO: Add a parameter for alcohol strength
		int drunkedness = (int) (30000 * alcoholContent);
		if(soberTime <= TFC_Time.getTotalTicks())
			soberTime = TFC_Time.getTotalTicks() + drunkedness;
		else
			soberTime += drunkedness;
		sendUpdate = true;
	}
}
