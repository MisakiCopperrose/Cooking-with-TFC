package com.dunk.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_MobData;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.Entities.AI.EntityAIAvoidRain;
import com.dunk.tfc.Entities.AI.EntityAIFleeRain;
import com.dunk.tfc.Entities.AI.EntityAIForage;
import com.dunk.tfc.Entities.AI.EntityAIGetFood;
import com.dunk.tfc.Entities.AI.EntityAIMateTFC;
import com.dunk.tfc.Entities.AI.EntityAINearestRetaliateTarget;
import com.dunk.tfc.Entities.AI.EntityAITargetHasFood;
import com.dunk.tfc.Entities.AI.EntityAITargetNonTamedTFC;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemCustomNameTag;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Interfaces.IInnateArmor;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBear extends EntityTameable implements ICausesDamage, IAnimal, IInnateArmor
{
	private static final float GESTATION_PERIOD = 7.0f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is
	 * calculated by cube-square law from the square root of the ratio of female
	 * mass : male mass
	 */
	private static final float DIMORPHISM = 0.2182f;

	protected boolean isDomesticated;
	protected boolean cantSleep = false;
	protected int sleepTimer = 0;
	protected boolean isSleeping = false;
	public boolean mating = false;

	private static final int DEGREE_OF_DIVERSION = 4;
	private static final int FAMILIARITY_CAP = 80; // Adult bears cap out at 80
													// since it is currently
													// impossible to get baby
													// bears.
	private final Random rand = new Random();
	private float moveSpeed;
	/** true is the wolf is wet else false */
	private boolean isWet;

	protected int numberCuts, numberBleeding;
	protected ArrayList<Wound> cuts, bleeding;

	private int sex;
	private int hunger;
	private boolean pregnant;
	private int pregnancyRequiredTime;
	private long timeOfConception;
	private float mateSizeMod;
	private float mateStrengthMod;
	private float mateAggroMod;
	private float mateObedMod;
	private float sizeMod; // How large the animal is
	private float strengthMod; // how strong the animal is
	private float aggressionMod = 1;// How aggressive / obstinate the animal is
	private float obedienceMod = 1; // How well the animal responds to commands.
	private boolean inLove;

	protected EntityAIAttackOnCollide attackAI;
	protected EntityAILeapAtTarget leapAI;
	protected EntityAITargetNonTamedTFC targetSheep;
	protected EntityAITargetNonTamedTFC targetDeer;
	protected EntityAITargetNonTamedTFC targetPig;
	protected EntityAITargetNonTamedTFC targetHorse;
	protected EntityAITargetHasFood targetPlayer;
	protected EntityAIHurtByTarget hurtAI;
	protected boolean isPeacefulAI;

	private boolean wasRoped;

	private int familiarity;
	private long lastFamiliarityUpdate;
	private boolean familiarizedToday;

	public EntityBear(World par1World)
	{
		super(par1World);
		setSize(1.2F, 1.2F);
		moveSpeed = 0.4F;
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAISwimming(this));
		sizeMod = (float) Math.sqrt((rand.nextInt(rand.nextInt((DEGREE_OF_DIVERSION + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1F) * (1.0F - DIMORPHISM * sex));
		strengthMod = (float) Math.sqrt((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + sizeMod));
		aggressionMod = (float) Math.sqrt((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1));
		obedienceMod = (float) Math.sqrt((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1f / aggressionMod));
		sex = rand.nextInt(2);
		if (getGender() == GenderEnum.MALE)
			tasks.addTask(6, new EntityAIMate(this, moveSpeed));
		tasks.addTask(7, new EntityAIWander(this, moveSpeed));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
		tasks.addTask(9, new EntityAILookIdle(this));
		// this.attackAI = new EntityAIAttackOnCollide(this, moveSpeed * 1.25F,
		// true);
		this.leapAI = new EntityAILeapAtTarget(this, 0.4F);
		
		cuts = new ArrayList<Wound>();
		bleeding = new ArrayList<Wound>();

		// this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(1, new EntityAINearestRetaliateTarget(this, EntityLivingBase.class, 1, 1, true));
		this.attackAI = /* this.tasks.addTask(1, */ new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.5f, true)/* ) */;
		this.tasks.addTask(1, new EntityAIAvoidRain(this));
		this.tasks.addTask(2, new EntityAIMateTFC(this, worldObj, 1.0f));
		this.tasks.addTask(3, new EntityAIFleeRain(this, 1.0D));
		this.targetTasks.addTask(3, new EntityAIGetFood(this, ItemFoodTFC.class, 214.3f, 2, 32, 1f));
		// TFC Targeting is affected by animal familiarity
		this.targetSheep = new EntityAITargetNonTamedTFC(this, EntitySheepTFC.class, 200, false);
		this.targetDeer = new EntityAITargetNonTamedTFC(this, EntityDeer.class, 200, false);
		this.targetPig = new EntityAITargetNonTamedTFC(this, EntityPigTFC.class, 200, false);
		this.targetHorse = new EntityAITargetNonTamedTFC(this, EntityHorseTFC.class, 200, false);
		this.targetPlayer = new EntityAITargetHasFood(this, EntityPlayer.class, 0, false);
		this.hurtAI = new EntityAIHurtByTarget(this, true);

		if (par1World.difficultySetting != EnumDifficulty.PEACEFUL)
		{
			isPeacefulAI = false;
			tasks.addTask(4, attackAI);
			// tasks.addTask(3, leapAI);
			targetTasks.addTask(4, targetSheep);
			targetTasks.addTask(4, targetDeer);
			targetTasks.addTask(4, targetPig);
			targetTasks.addTask(4, targetHorse);
			targetTasks.addTask(4, targetPlayer);
			targetTasks.addTask(3, hurtAI);
		}
		else
			isPeacefulAI = true;
		// targetTasks.addTask(2, new EntityAIPanic(this,moveSpeed*1.5F));

		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);

		/*
		 * We hijack the growingAge to hold the day of birth rather than the
		 * number of ticks to the next growth event. We want spawned animals to
		 * be adults, so we set their birthdays far enough back in time such
		 * that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
	}

	public EntityBear(World par1World, IAnimal mother, List<Float> data)
	{
		this(par1World);
		float fatherSize = 1;
		float fatherStr = 1;
		float fatherAggro = 1;
		float fatherObed = 1;
		for (int i = 0; i < data.size(); i++)
		{
			switch (i)
			{
			case 0:
				fatherSize = data.get(i);
				break;
			case 1:
				fatherStr = data.get(i);
				break;
			case 2:
				fatherAggro = data.get(i);
				break;
			case 3:
				fatherObed = data.get(i);
				break;
			default:
				break;
			}
		}
		this.posX = ((EntityLivingBase) mother).posX;
		this.posY = ((EntityLivingBase) mother).posY;
		this.posZ = ((EntityLivingBase) mother).posZ;
		float invSizeRatio = 1f / (2 - DIMORPHISM);
		sizeMod = (float) Math.sqrt(sizeMod * sizeMod * (float) Math.sqrt((mother.getSizeMod() + fatherSize) * invSizeRatio));
		strengthMod = (float) Math.sqrt(strengthMod * strengthMod * (float) Math.sqrt((mother.getStrengthMod() + fatherStr) * 0.5F));
		aggressionMod = (float) Math.sqrt(aggressionMod * aggressionMod * (float) Math.sqrt((mother.getAggressionMod() + fatherAggro) * 0.5F));
		obedienceMod = (float) Math.sqrt(obedienceMod * obedienceMod * (float) Math.sqrt((mother.getObedienceMod() + fatherObed) * 0.5F));

		this.familiarity = (int) (mother.getFamiliarity() < 90 ? mother.getFamiliarity() / 2 : mother.getFamiliarity() * 0.9f);

		// We hijack the growingAge to hold the day of birth rather than number
		// of ticks to next growth event.
		this.setAge(TFC_Time.getTotalDays());
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.BEAR_HEALTH);// MaxHealth
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		int dam = (int) (TFC_MobData.BEAR_DAMAGE * getStrengthMod() * getAggressionMod() * (getSizeMod() / 2 + 0.5F));
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), dam);
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn()
	{
		return !wasRoped && this.ticksExisted > 30000;
	}

	@Override
	public boolean canAttackClass(Class p_70686_1_)
	{
		return true;
	}

	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

	@Override
	public boolean canMateWith(EntityAnimal animal)
	{
		if (animal == this)
			return false;
		if (!(animal instanceof EntityBear))
			return false;
		EntityBear entitybear = (EntityBear) animal;
		return getInLove() && entitybear.getInLove() && ((IAnimal) animal).isDomesticated() == this.isDomesticated;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() && this.isAdult() && animal.isAdult() && animal instanceof EntityBear;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return true;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player)
	{
		boolean flag = false;
		switch (interaction)
		{
		case MOUNT:
			flag = familiarity > 75;
			break;
		case BREED:
			flag = familiarity > 75;
			break;
		case NAME:
			flag = familiarity > 45;
			break;
		case TOLERATEPLAYER:
			flag = familiarity > 75;
			break;
		default:
			break;
		}
		if (!flag && player != null && !player.worldObj.isRemote)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.notFamiliar"));
		}
		return flag;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityBear(worldObj, this, data);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);

		if (rand.nextBoolean())
		{
			this.entityDropItem(new ItemStack(TFCItems.bearFur, 1, Math.max(0, Math.min(2, (int) (ageMod * 3 - 1)))), 0);
		}
		else
		{
			this.entityDropItem(new ItemStack(TFCItems.bearFurScrap, 1, Math.max(0, Math.min(2, (int) (ageMod * 3 - 1)))), 0);
		}
		this.dropItem(TFCItems.bone, (int) ((rand.nextInt(6) + 2) * ageMod));
		this.dropItem(TFCItems.sinew, (int) ((rand.nextInt(3) + 2) * ageMod));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(18, getHealth());
		this.dataWatcher.addObject(13, Integer.valueOf(0)); // sex (1 or 0)
		this.dataWatcher.addObject(14, Integer.valueOf(0)); // injuries
		this.dataWatcher.addObject(15, Integer.valueOf(0)); // age
		this.dataWatcher.addObject(25, Integer.valueOf(0)); // sleeping, same
															// with wolf, this
															// used to be 17
		this.dataWatcher.addObject(22, Integer.valueOf(0)); // Size, strength,
															// aggression,
															// obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); // familiarity,
															// familiarizedToday,
															// pregnant, empty
															// slot
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of
																// conception,
																// stored as a
																// string since
																// we can't do
																// long
	}

	@Override
	public void familiarize(EntityPlayer ep)
	{
		ItemStack stack = ep.getHeldItem();
		if (stack != null && isFood(stack) && !familiarizedToday && canFamiliarize())
		{
			if (!ep.capabilities.isCreativeMode)
			{
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem, ((ItemFoodTFC) stack.getItem()).onConsumedByEntity(ep.getHeldItem(), worldObj, this));
			}
			else
			{
				worldObj.playSoundAtEntity(this, "random.burp", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			this.hunger += 24000;
			familiarizedToday = true;
			this.getLookHelper().setLookPositionWithEntity(ep, 0, 0);
			this.playLivingSound();
		}
	}

	@Override
	public float getAggressionMod()
	{
		return aggressionMod;
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("bear");
	}

	@Override
	public Vec3 getAttackedVec()
	{
		return null;
	}

	@Override
	public int getBirthDay()
	{
		return this.dataWatcher.getWatchableObjectInt(15);
	}

	@Override
	public int getCrushArmor()
	{
		return 0;
	}

	@Override
	public EnumDamageType getDamageType(EntityLivingBase is)
	{
		return EnumDamageType.SLASHING;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		if (!isChild())
			return TFC_Sounds.BEARDEATH;
		else
			return TFC_Sounds.BEARCUBCRY;
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Item.getItemById(0);
	}

	@Override
	public int getDueDay()
	{
		return TFC_Time.getDayFromTotalHours((timeOfConception + pregnancyRequiredTime) / 1000);
	}

	@Override
	public EntityLiving getEntity()
	{
		return this;
	}

	@Override
	public float getEyeHeight()
	{
		return height * 0.8F;
	}

	@Override
	public int getFamiliarity()
	{
		return familiarity;
	}

	@Override
	public boolean getFamiliarizedToday()
	{
		return familiarizedToday;
	}

	@Override
	public Entity getFearSource()
	{
		return null;
	}

	@Override
	public GenderEnum getGender()
	{
		return GenderEnum.GENDERS[dataWatcher.getWatchableObjectInt(13)];
	}

	@Override
	public int getHunger()
	{
		return hunger;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		if (!isChild())
			return TFC_Sounds.BEARHURT;
		else
			return TFC_Sounds.BEARCUBCRY;
	}

	@Override
	public boolean getInLove()
	{
		return inLove;
	}

	public long getLastFamiliarityUpdate()
	{
		return lastFamiliarityUpdate;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		if (isAdult() && worldObj.rand.nextInt(100) < 5)
			return TFC_Sounds.BEARCRY;
		else if (isChild() && worldObj.rand.nextInt(100) < 5)
			return TFC_Sounds.BEARCUBCRY;

		return isChild() ? null : TFC_Sounds.BEARSAY;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 2;
	}

	public float getMoveSpeed()
	{
		return moveSpeed;
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return TFC_Time.daysInMonth * 60;
	}

	@Override
	public float getObedienceMod()
	{
		return obedienceMod;
	}

	@Override
	public int getPierceArmor()
	{
		return -335;
	}

	public int getPregnancyRequiredTime()
	{
		return pregnancyRequiredTime;
	}

	public int getSex()
	{
		return sex;
	}

	@Override
	public float getSizeMod()
	{
		return sizeMod;
	}

	@Override
	public int getSlashArmor()
	{
		return 0;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	@Override
	public float getStrengthMod()
	{
		return strengthMod;
	}

	public long getTimeOfConception()
	{
		return timeOfConception;
	}

	@Override
	public void handleFamiliarityUpdate()
	{
		int totalDays = TFC_Time.getTotalDays();
		if (lastFamiliarityUpdate < totalDays)
		{
			if (familiarizedToday && familiarity < 100)
			{
				lastFamiliarityUpdate = totalDays;
				familiarizedToday = false;
				float familiarityChange = 3 * obedienceMod / aggressionMod; // Changed
																			// from
																			// 6
																			// to
																			// 3
																			// so
																			// bears
																			// are
																			// harder
																			// to
																			// tame
																			// by
																			// default.
																			// -Kitty
				if (this.isAdult() && familiarity <= FAMILIARITY_CAP)
				{
					familiarity += familiarityChange;
				}
				else if (!this.isAdult())
				{
					float ageMod = 2f / (1f + TFC_Core.getPercentGrown(this));
					familiarity += ageMod * familiarityChange;
					if (familiarity > 70)
					{
						obedienceMod *= 1.01f;
					}
				}
			}
			else if (familiarity < 30)
			{
				familiarity -= 2 * (TFC_Time.getTotalDays() - lastFamiliarityUpdate);
				lastFamiliarityUpdate = totalDays;
			}
		}
		if (familiarity > 100)
			familiarity = 100;
		if (familiarity < 0)
			familiarity = 0;
	}

	@Override
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 8)
		{
			isWet = true;
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if (!worldObj.isRemote)
		{
			if (player.isSneaking() && !familiarizedToday && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation(getGender() == GenderEnum.FEMALE ? "entity.female" : "entity.male"));
			if (getGender() == GenderEnum.FEMALE && pregnant)
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
			}
		}
		ItemStack itemstack = player.getHeldItem();
		if (itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
		{
			if (this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player))
			{
				itemstack.stackSize--;
			}
			return true;
		}
		return super.interact(player);
	}

	@Override
	public boolean isAdult()
	{
		return getBirthDay() + getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public boolean isFood(ItemStack item)
	{
		return item != null && item.getItem().equals(TFCItems.fishRaw);
	}

	@Override
	public boolean isPregnant()
	{
		return pregnant;
	}

	public boolean isWasRoped()
	{
		return wasRoped;
	}

	@Override
	public void mate(IAnimal otherAnimal)
	{
		if (getGender() == GenderEnum.MALE)
		{
			otherAnimal.mate(this);
			return;
		}
		timeOfConception = TFC_Time.getTotalTicks();
		pregnant = true;
		resetInLove();
		otherAnimal.setInLove(false);
		mateSizeMod = otherAnimal.getSizeMod();
		mateStrengthMod = otherAnimal.getStrengthMod();
		mateAggroMod = otherAnimal.getAggressionMod();
		mateObedMod = otherAnimal.getObedienceMod();
	}

	@Override
	public boolean isMovementBlocked()
	{
		return sleepTimer > 0 || super.isMovementBlocked();
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		if (!worldObj.isRemote && TFC_Core.isExposedToRain(worldObj, (int) posX, (int) posY, (int) posZ)
		|| worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ).getMaterial().isLiquid())
		{
			this.cantSleep = true;
		}
		else if (!worldObj.isRemote && !TFC_Core.isExposedToRain(worldObj, (int) posX, (int) posY, (int) posZ) && this.getAITarget() == null)
		{
			this.cantSleep = false;
		}

		if (!worldObj.isRemote)
		{
			for (int i = 0; i < cuts.size(); i++)
			{
				Wound w = cuts.get(i);
				int timeRemaining = w.timeRemaining;
				if (timeRemaining >0)
				{
					timeRemaining--;
					if (timeRemaining % 100 == 0)
					{
						this.attackEntityFrom(this.getHealth() <= 15?DamageSource.causeMobDamage((EntityLivingBase) w.sourceOfWound).setDamageBypassesArmor():DamageSource.generic, 15);
					}
					w.timeRemaining = timeRemaining;
					cuts.set(i, w);
				}
				else
				{
					cuts.remove(i);
					i--;
				}
			}
			for (int i = 0; i < bleeding.size(); i++)
			{
				Wound w = bleeding.get(i);
				int timeRemaining = w.timeRemaining;
				if (timeRemaining > 0)
				{
					timeRemaining--;
					if (timeRemaining % 100 == 0)
					{
						this.attackEntityFrom(this.getHealth() <= 15?DamageSource.causeMobDamage((EntityLivingBase) w.sourceOfWound).setDamageBypassesArmor():DamageSource.generic, 20);
					}
					w.timeRemaining = timeRemaining;
					bleeding.set(i, w);
				}
				else
				{
					bleeding.remove(i);
					i--;
				}
			}
		}
		if (isSleeping && mating)
		{
			Iterator iterator = this.tasks.taskEntries.iterator();
			EntityAITasks.EntityAITaskEntry entityaitaskentry;
			while (iterator.hasNext())
			{
				entityaitaskentry = (EntityAITasks.EntityAITaskEntry) iterator.next();
				if (entityaitaskentry.action instanceof EntityAIMateTFC)
				{
					entityaitaskentry.action.updateTask();
					if (!entityaitaskentry.action.continueExecuting())
					{
						mating = false;
					}

				}
			}
		}

		if (!worldObj.isDaytime() && this.getAITarget() == null && !cantSleep && this.entityToAttack == null && this.getAttackTarget() == null && !isSleeping
		&& !this.worldObj.isRemote)
		{
			goToSleep();
		}
		if ((worldObj.isDaytime() || cantSleep) && !mating && isSleeping && !this.worldObj.isRemote)
		{
			wakeUp();
		}
		else if (this.getAITarget() != null && !worldObj.isDaytime() && !this.worldObj.isRemote && isSleeping)
		{
			wakeUp();
			this.playSound(this.getDeathSound(), 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		}

		if (isSleeping && sleepTimer < 80 && !this.worldObj.isRemote)
		{
			sleepTimer++;
		}
		else if (!isSleeping && sleepTimer > 0 && !this.worldObj.isRemote)
		{
			if (this.getAITarget() != null)
			{
				sleepTimer = Math.max(sleepTimer - 5, 0);
			}
			else
			{
				sleepTimer--;
			}
		}
		if (sleepTimer == -50)
		{
			sleepTimer = 0;
		}

		if (super.isInLove())
		{
			super.resetInLove();
			setInLove(true);
		}

		TFC_Core.preventEntityDataUpdate = true;
		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (!worldObj.isRemote)
		{
			if (!isWet && !hasPath() && onGround)
			{
				isWet = true;
				worldObj.setEntityState(this, (byte) 8);
			}

			if (isPregnant())
			{
				if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
				{
					int i = rand.nextInt(3) + 1;
					for (int x = 0; x < i; x++)
					{
						EntityBear baby = (EntityBear) createChildTFC(this);// new
																			// EntityBear(worldObj,
																			// this,data);
						worldObj.spawnEntityInWorld(baby);
					}
					pregnant = false;
				}
			}
		}

		if (this.getLeashed() && !wasRoped)
			wasRoped = true;

		this.handleFamiliarityUpdate();
		this.syncData();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!this.worldObj.isRemote)
		{
			if (!isPeacefulAI && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
			{
				isPeacefulAI = true;
				tasks.removeTask(attackAI);
				tasks.removeTask(leapAI);
				targetTasks.removeTask(targetSheep);
				targetTasks.removeTask(targetDeer);
				targetTasks.removeTask(targetPig);
				targetTasks.removeTask(targetHorse);
				targetTasks.removeTask(targetPlayer);
				targetTasks.removeTask(hurtAI);
			}
			else if (isPeacefulAI && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL)
			{
				isPeacefulAI = false;
				tasks.addTask(4, attackAI);
				tasks.addTask(3, leapAI);
				targetTasks.addTask(4, targetSheep);
				targetTasks.addTask(4, targetDeer);
				targetTasks.addTask(4, targetPig);
				targetTasks.addTask(4, targetHorse);
				targetTasks.addTask(4, targetPlayer);
				targetTasks.addTask(3, hurtAI);
			}
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		sex = nbt.getInteger("Sex");
		sizeMod = nbt.getFloat("Size Modifier");

		this.numberCuts = nbt.getInteger("NumberOfCuts");
		this.numberBleeding = nbt.getInteger("NumberOfBleeding");

		if (!worldObj.isRemote)
		{
			this.cuts = new ArrayList<Wound>();
			this.bleeding = new ArrayList<Wound>();
			for (int i = 0; i < numberCuts; i++)
			{
				int timeRemaining = nbt.getInteger("Cut" + i+":T");
				String eID = nbt.getString("Cut" + i+":E");
				Wound w = new Wound();
				w.timeRemaining = timeRemaining;
				if(worldObj.getPlayerEntityByName(eID)!=null)
				{
					w.sourceOfWound = worldObj.getPlayerEntityByName(eID);
				}
				else
				{
					w.sourceOfWound = this;
				}
				cuts.add(w);
			}
			for (int i = 0; i < numberBleeding; i++)
			{
				int timeRemaining = nbt.getInteger("Bleeding" + i+":T");
				String eID = nbt.getString("Bleeding" + i+":E");
				Wound w = new Wound();
				w.timeRemaining = timeRemaining;
				if(worldObj.getPlayerEntityByName(eID)!=null)
				{
					w.sourceOfWound = worldObj.getPlayerEntityByName(eID);
				}
				else
				{
					w.sourceOfWound = this;
				}
				bleeding.add(w);
			}
		}

		familiarity = nbt.getInteger("Familiarity");
		lastFamiliarityUpdate = nbt.getLong("lastFamUpdate");
		familiarizedToday = nbt.getBoolean("Familiarized Today");

		strengthMod = nbt.getFloat("Strength Modifier");
		aggressionMod = nbt.getFloat("Aggression Modifier");
		obedienceMod = nbt.getFloat("Obedience Modifier");

		mating = nbt.getBoolean("mating");
		isDomesticated = nbt.getBoolean("domesticated");
		cantSleep = nbt.getBoolean("cantSleep");
		this.dataWatcher.updateObject(25, (nbt.getBoolean("isSleeping") ? 1 : 0) + nbt.getInteger("sleepTimer") << 1);

		wasRoped = nbt.getBoolean("wasRoped");

		hunger = nbt.getInteger("Hunger");
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		timeOfConception = nbt.getLong("ConceptionTime");
		this.dataWatcher.updateObject(15, nbt.getInteger("Age"));
	}

	@Override
	public void setAge(int par1)
	{
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}

	@Override
	public void setAggressionMod(float aggression)
	{
		this.aggressionMod = aggression;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec)
	{
	}

	@Override
	public void setBirthDay(int day)
	{
		this.dataWatcher.updateObject(15, day);
	}

	@Override
	public void setFamiliarity(int f)
	{
		this.familiarity = f;
	}

	public void setFamiliarizedToday(boolean familiarizedToday)
	{
		this.familiarizedToday = familiarizedToday;
	}

	@Override
	public void setFearSource(Entity fearSource)
	{
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if (!TFC_Core.preventEntityDataUpdate)
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

	@Override
	public void setHunger(int h)
	{
		hunger = h;
	}

	@Override
	public void setInLove(boolean b)
	{
		this.inLove = b;
	}

	public void setLastFamiliarityUpdate(long lastFamiliarityUpdate)
	{
		this.lastFamiliarityUpdate = lastFamiliarityUpdate;
	}

	public void setMoveSpeed(float moveSpeed)
	{
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void setObedienceMod(float obedience)
	{
		this.obedienceMod = obedience;
	}

	public void setPregnancyRequiredTime(int pregnancyRequiredTime)
	{
		this.pregnancyRequiredTime = pregnancyRequiredTime;
	}

	public void setPregnant(boolean pregnant)
	{
		this.pregnant = pregnant;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	@Override
	public void setSizeMod(float size)
	{
		this.sizeMod = size;
	}

	@Override
	public void setStrengthMod(float strength)
	{
		this.strengthMod = strength;
	}

	public void setTimeOfConception(long timeOfConception)
	{
		this.timeOfConception = timeOfConception;
	}

	public void setWasRoped(boolean wasRoped)
	{
		this.wasRoped = wasRoped;
	}

	public void syncData()
	{
		if (dataWatcher != null)
		{
			if (!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex + (isDomesticated ? 2 : 0)));
				this.dataWatcher.updateObject(14, Integer.valueOf(Math.min(this.getNumberBleeding(), 15) + (Math.min(this.getNumberCuts(), 15) << 4)));
				this.dataWatcher.updateObject(25, (sleepTimer << 1) + (isSleeping ? 1 : 0));
				byte[] values = { TFC_Core.getByteFromSmallFloat(sizeMod), TFC_Core.getByteFromSmallFloat(strengthMod), TFC_Core.getByteFromSmallFloat(aggressionMod),
				TFC_Core.getByteFromSmallFloat(obedienceMod), (byte) familiarity, (byte) (familiarizedToday ? 1 : 0), (byte) (pregnant ? 1 : 0), (byte) 0 // Empty
				};
				ByteBuffer buf = ByteBuffer.wrap(values);
				this.dataWatcher.updateObject(22, buf.getInt());
				this.dataWatcher.updateObject(23, buf.getInt());
				this.dataWatcher.updateObject(24, String.valueOf(timeOfConception));
			}
			else
			{
				sex = this.dataWatcher.getWatchableObjectInt(13) & 1;
				this.numberBleeding = this.dataWatcher.getWatchableObjectInt(14) & 15;
				this.numberCuts = (this.dataWatcher.getWatchableObjectInt(14) >> 4) & 15;
				isDomesticated = ((this.dataWatcher.getWatchableObjectInt(13) & 2) == 2);
				this.isSleeping = ((this.dataWatcher.getWatchableObjectInt(25) & 1) == 1);
				this.sleepTimer = (this.dataWatcher.getWatchableObjectInt(25) >> 1);

				ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
				buf.putInt(this.dataWatcher.getWatchableObjectInt(22));
				buf.putInt(this.dataWatcher.getWatchableObjectInt(23));
				byte[] values = buf.array();

				sizeMod = TFC_Core.getSmallFloatFromByte(values[0]);
				strengthMod = TFC_Core.getSmallFloatFromByte(values[1]);
				aggressionMod = TFC_Core.getSmallFloatFromByte(values[2]);
				obedienceMod = TFC_Core.getSmallFloatFromByte(values[3]);

				familiarity = values[4];
				familiarizedToday = values[5] == 1;
				pregnant = values[6] == 1;

				try
				{
					timeOfConception = Long.parseLong(this.dataWatcher.getWatchableObjectString(24));
				}
				catch (NumberFormatException e)
				{
				}
			}
		}
	}

	@Override
	public boolean trySetName(String name, EntityPlayer player)
	{
		if (this.checkFamiliarity(InteractionEnum.NAME, player))
		{
			this.setCustomNameTag(name);
			return true;
		}
		this.playSound((isChild() ? TFC_Sounds.BEARCUBCRY : TFC_Sounds.BEARCRY), 6, rand.nextFloat() / 2F + 0.75F);
		return false;
	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITick()
	{
		dataWatcher.updateObject(18, getHealth());
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Sex", sex);
		nbt.setFloat("Size Modifier", sizeMod);

		nbt.setInteger("NumberOfCuts", this.getNumberCuts());
		nbt.setInteger("NumberOfBleeding", this.getNumberBleeding());
		if (!worldObj.isRemote)
		{
			for (int i = 0; i < numberCuts; i++)
			{
				nbt.setInteger("Cut" + i+":T", this.cuts.get(i).timeRemaining);
				nbt.setString("Cut" + i+":E", (this.cuts.get(i).sourceOfWound instanceof EntityPlayer? ((EntityPlayer)this.cuts.get(i).sourceOfWound).getCommandSenderName():this.cuts.get(i).sourceOfWound.getPersistentID().toString()));
			}
			for (int i = 0; i < numberBleeding; i++)
			{
				nbt.setInteger("Bleeding" + i+":T", this.cuts.get(i).timeRemaining);
				nbt.setString("Bleeding" + i+":E", (this.cuts.get(i).sourceOfWound instanceof EntityPlayer? ((EntityPlayer)this.cuts.get(i).sourceOfWound).getCommandSenderName():this.cuts.get(i).sourceOfWound.getPersistentID().toString()));
			}
		}

		nbt.setInteger("Familiarity", familiarity);
		nbt.setLong("lastFamUpdate", lastFamiliarityUpdate);
		nbt.setBoolean("Familiarized Today", familiarizedToday);

		nbt.setFloat("Strength Modifier", strengthMod);
		nbt.setFloat("Aggression Modifier", aggressionMod);
		nbt.setFloat("Obedience Modifier", obedienceMod);

		nbt.setBoolean("wasRoped", wasRoped);

		nbt.setBoolean("mating", mating);
		nbt.setBoolean("domesticated", isDomesticated);
		nbt.setBoolean("cantSleep", cantSleep);
		nbt.setInteger("sleepTimer", sleepTimer);
		nbt.setBoolean("isSleeping", isSleeping);

		nbt.setInteger("Hunger", hunger);
		nbt.setBoolean("Pregnant", pregnant);
		nbt.setFloat("MateSize", mateSizeMod);
		nbt.setFloat("MateStrength", mateStrengthMod);
		nbt.setFloat("MateAggro", mateAggroMod);
		nbt.setFloat("MateObed", mateObedMod);
		nbt.setLong("ConceptionTime", timeOfConception);
		nbt.setInteger("Age", getBirthDay());
	}

	@Override
	public boolean isDomesticated()
	{
		return this.isDomesticated;
	}

	@Override
	public void setCantSleep(boolean b)
	{
		this.cantSleep = b;
	}

	@Override
	public int getSleepTimer()
	{
		return this.sleepTimer;
	}

	@Override
	public boolean isSleeping()
	{
		return this.isSleeping;
	}

	@Override
	public void setMating(boolean b)
	{
		this.mating = b;
	}

	@Override
	public void setSleeping()
	{
		this.dataWatcher.updateObject(25, sleepTimer << 1 + (isSleeping ? 1 : 0));
	}

	@Override
	public void goToSleep()
	{
		this.isSleeping = true;
	}

	@Override
	public void wakeUp()
	{
		if (!mating)
		{
			this.isSleeping = false;
		}
	}

	@Override
	public int getNumberCuts()
	{
		if (worldObj.isRemote)
		{
			return this.numberCuts;
		}
		if (this.cuts == null)
		{
			this.cuts = new ArrayList<Wound>();
		}
		return this.cuts.size();
	}

	@Override
	public int getNumberBleeding()
	{
		if (worldObj.isRemote)
		{
			return this.numberBleeding;
		}
		if (this.bleeding == null)
		{
			this.bleeding = new ArrayList<Wound>();
		}
		return this.bleeding.size();
	}

	@Override
	public void slashAnimal(int t, Entity s)
	{
		if (this.cuts == null)
		{
			this.cuts = new ArrayList<Wound>();
		}
		Wound w = new Wound(t,s);
		this.cuts.add(w );
	}

	@Override
	public void pierceAnimal(int t, Entity s)
	{
		if (this.bleeding == null)
		{
			this.bleeding = new ArrayList<Wound>();
		}
		if(s instanceof EntityProjectileTFC)
		{
			s = ((EntityProjectileTFC) s).shootingEntity;
		}
		Wound w = new Wound(t,s);
		this.bleeding.add(w);
	}

	@Override
	public void healSlash()
	{
		if(this.cuts != null && this.getNumberCuts() > 0)
		{
			this.cuts.remove(this.getNumberCuts()-1);
		}
	}

	@Override
	public void healPierce()
	{
		if(this.bleeding != null && this.getNumberBleeding() > 0)
		{
			this.bleeding.remove(this.getNumberBleeding()-1);
		}
	}
}
