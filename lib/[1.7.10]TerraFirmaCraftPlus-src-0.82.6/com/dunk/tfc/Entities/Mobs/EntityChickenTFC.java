package com.dunk.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.dunk.tfc.Entities.AI.EntityAIAvoidRain;
import com.dunk.tfc.Entities.AI.EntityAIFindNest;
import com.dunk.tfc.Entities.AI.EntityAIFleeRain;
import com.dunk.tfc.Entities.AI.EntityAIMateTFC;
import com.dunk.tfc.Entities.AI.EntityAINearestRetaliateTarget;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemCustomNameTag;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityChickenTFC extends EntityChicken implements IAnimal
{
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is
	 * calculated by cube-square law from the square root of the ratio of female
	 * mass : male mass
	 */
	private static final float DIMORPHISM = 0.0606f;

	private static final int DEGREE_OF_DIVERSION = 2;
	protected static final int FAMILIARITY_CAP = 30;
	private static final int EGG_TIME = TFC_Time.DAY_LENGTH;

	protected boolean isDomesticated;
	protected boolean cantSleep = false;
	protected int sleepTimer = 0;
	protected boolean isSleeping = false;
	public boolean mating = false;

	protected int numberCuts, numberBleeding;
	protected ArrayList<Wound> cuts, bleeding;
	
	private final EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	private int sex;
	private int hunger;
	private float sizeMod; // How large the animal is
	private float strengthMod; // how strong the animal is
	private float aggressionMod = 1;// How aggressive / obstinate the animal is

	private float obedienceMod = 1; // How well the animal responds to commands.
	private boolean inLove;

	/** The time until the next egg is spawned. */
	private long nextEgg;
	private int familiarity;
	private long lastFamiliarityUpdate;

	private boolean familiarizedToday;

	public EntityChickenTFC(World par1World)
	{
		super(par1World);
		this.setSize(0.3F, 0.7F);
		this.timeUntilNextEgg = 9999;// Here we set the vanilla egg timer to
										// 9999
		this.nextEgg = TFC_Time.getTotalTicks() + EGG_TIME;
		hunger = 168000;
		sex = rand.nextInt(2);

		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		//this.tasks.addTask(1, new EntityAINearestRetaliateTarget(this, EntityLivingBase.class, 1, true));
		//this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.5f, true));
		this.tasks.addTask(1, new EntityAIAvoidRain(this));
		//this.tasks.addTask(2, new EntityAIMateTFC(this, worldObj, 1.0f));
		this.tasks.addTask(3, new EntityAIFleeRain(this, 1.0D));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityChickenTFC.class, 6.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(6, this.aiEatGrass);
		addAI();
		
		cuts = new ArrayList<Wound>();
		bleeding = new ArrayList<Wound>();

		sizeMod = (float) Math
				.sqrt((rand.nextInt(rand.nextInt((DEGREE_OF_DIVERSION + 1) * 10) + 1) * (rand.nextBoolean() ? 1
						: -1) * 0.01f + 1F) * (1.0F - DIMORPHISM * sex));
		strengthMod = (float) Math.sqrt((rand.nextInt(
				rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + sizeMod));
		aggressionMod = (float) Math.sqrt(
				(rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1) * 0.01f + 1));
		obedienceMod = (float) Math.sqrt((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean()
				? 1 : -1) * 0.01f + 1f / aggressionMod));

		/*
		 * We hijack the growingAge to hold the day of birth rather than the
		 * number of ticks to the next growth event. We want spawned animals to
		 * be adults, so we set their birthdays far enough back in time such
		 * that they reach adulthood now.
		 */
		this.setAge(TFC_Time.getTotalDays() - getNumberOfDaysToAdult());
		// For Testing Only(makes spawned animals into babies)
		// this.setGrowingAge((int) TFC_Time.getTotalDays());
	}

	// Chickens hatching from a nestbox
	public EntityChickenTFC(World world, double posX, double posY, double posZ, NBTTagCompound genes)
	{
		this(world);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		float motherSize = genes.getFloat("m_size");
		float fatherSize = genes.getFloat("f_size");
		sizeMod = (rand.nextInt(DEGREE_OF_DIVERSION + 1) * (rand.nextBoolean() ? 1
				: -1) / 10f + 1F) * (1.0F - 0.1F * sex) * (float) Math.sqrt((motherSize + fatherSize) / 1.9F);
		if (genes.getInteger("m_familiarity") > 99 || genes.getBoolean("domesticated"))
		{
			// this.isDomesticated = true;
		}
		// We hijack the growingAge to hold the day of birth rather than number
		// of ticks to next growth event.
		this.setAge(TFC_Time.getTotalDays());
	}

	public String getCommandSenderName()
	{
		if (isDomesticated)
		{
			return TFC_Core.translate("entity.chickenTFC.name");
		}
		return TFC_Core.translate("entity.jungleFowlTFC.name");
	}

	public void addAI()
	{
		if (sex == 0)
		{
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		}
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.wheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.ryeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.riceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.barleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.oatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.maizeEar, false));
		this.tasks.addTask(3, new EntityAIFindNest(this, 1.2F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50);// MaxHealth
	}

	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() && this.isAdult() && animal.isAdult() && animal
				.getAnimalTypeID() == this.getAnimalTypeID() && ((IAnimal) animal)
						.isDomesticated() == this.isDomesticated;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player)
	{
		boolean flag = false;
		switch (interaction)
		{
		case NAME:
			flag = familiarity > 50 || isDomesticated;
			break; // Set 5 higher than adult cap.
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
	public EntityChicken createChild(EntityAgeable entityAgeable)
	{
		return (EntityChicken) createChildTFC(entityAgeable);
	}

	// This should only be called when spawning baby chickens with a spawn egg,
	// so both size values come from the animal clicked on.
	@Override
	public EntityAgeable createChildTFC(EntityAgeable entityageable)
	{
		if (entityageable instanceof IAnimal)
		{
			IAnimal animal = (IAnimal) entityageable;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("m_size", animal.getSizeMod());
			nbt.setFloat("f_size", animal.getSizeMod());
			return new EntityChickenTFC(worldObj, posX, posY, posZ, nbt);
		}

		return null;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		this.dropItem(Items.feather, (int) (ageMod * this.sizeMod * (5 + this.rand.nextInt(10))));

		if (isAdult())
		{
			float foodWeight = ageMod * (this.sizeMod * 40);
			TFC_Core.animalDropMeat(this, TFCItems.chickenRaw, foodWeight);
			this.dropItem(TFCItems.bone, rand.nextInt(2) + 1);
			if (rand.nextInt(20) == 0)
			{
				this.dropItem(TFCItems.hollowBone, 1);
			}
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, Integer.valueOf(0)); // sex (1 or 0)
		this.dataWatcher.addObject(14, Integer.valueOf(0)); // injuries
		this.dataWatcher.addObject(15, Integer.valueOf(0)); // age
		this.dataWatcher.addObject(17, Integer.valueOf(0)); // sleeping
		this.dataWatcher.addObject(22, Integer.valueOf(0)); // Size, strength,
															// aggression,
															// obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); // familiarity,
															// familiarizedToday,
															// empty slot, empty
															// slot
	}

	@Override
	public void familiarize(EntityPlayer ep)
	{
		ItemStack stack = ep.getHeldItem();
		if (stack != null && isFood(stack) && !familiarizedToday && canFamiliarize())
		{
			if (!ep.capabilities.isCreativeMode)
			{
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem,
						((ItemFoodTFC) stack.getItem()).onConsumedByEntity(ep.getHeldItem(), worldObj, this));
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
		return Helper.stringToInt("chicken");
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

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Items.feather;
	}

	@Override
	public int getDueDay()
	{
		return 0; // Chickens don't get pregnant
	}

	public ItemStack getEggs()
	{
		if (TFC_Time.getTotalTicks() >= this.nextEgg)
		{
			this.nextEgg = TFC_Time.getTotalTicks() + EGG_TIME;
			return new ItemStack(TFCItems.egg, 1);
		}
		return null;
	}

	@Override
	public EntityLiving getEntity()
	{
		return this;
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

	@Override
	public boolean getInLove()
	{
		return inLove;
	}

	public long getLastFamiliarityUpdate()
	{
		return lastFamiliarityUpdate;
	}

	public long getNextEgg()
	{
		return nextEgg;
	}

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 4.14);
	}

	@Override
	public float getObedienceMod()
	{
		return obedienceMod;
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
	public float getStrengthMod()
	{
		return strengthMod;
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
				float familiarityChange = 6 * obedienceMod / aggressionMod;
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

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.getHeldItem();

		if (!worldObj.isRemote)
		{
			if (isAdult() && player.isSneaking() && !isFood(itemstack) && attackEntityFrom(DamageSource.generic, 5))
			{
				player.inventory.addItemStackToInventory(new ItemStack(Items.feather, 1));
				familiarity -= 4; // Plucking feathers decreases familiarity
				return true;
			}

			if (player.isSneaking() && !familiarizedToday && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}
		}

		if (itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack
				.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
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
	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public boolean isFood(ItemStack item)
	{
		return item != null && (item.getItem() == TFCItems.wheatGrain || item.getItem() == TFCItems.oatGrain || item
				.getItem() == TFCItems.riceGrain || item.getItem() == TFCItems.barleyGrain || item
						.getItem() == TFCItems.ryeGrain || item.getItem() == TFCItems.maizeEar);
	}

	@Override
	public boolean isPregnant()
	{
		return false;
	}

	@Override
	public void mate(IAnimal otherAnimal)
	{
		resetInLove();
		otherAnimal.setInLove(false);
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
		// Handle Hunger ticking
		if (hunger > 168000)
		{
			hunger = 168000;
		}
		if (hunger > 0)
		{
			hunger--;
		}

		if (!worldObj.isRemote && TFC_Core.isExposedToRain(worldObj, (int) posX, (int) posY, (int) posZ) || worldObj
				.getBlock((int) this.posX, (int) this.posY, (int) this.posZ).getMaterial().isLiquid())
		{
			this.cantSleep = true;
		}
		else if (!worldObj.isRemote && !TFC_Core.isExposedToRain(worldObj, (int) posX, (int) posY, (int) posZ) && this
				.getAITarget() == null)
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
			}		}
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

		if (!worldObj.isDaytime() && this.getAITarget() == null && !cantSleep && this.entityToAttack == null && this
				.getAttackTarget() == null && !isSleeping && !this.worldObj.isRemote)
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

		syncData();
		if (isAdult())
		{
			setGrowingAge(0);
		}
		else
		{
			setGrowingAge(-1);
		}

		roosterCrow();
		this.handleFamiliarityUpdate();

		// Make sure that the vanilla egg timer is never after to reach 0 but
		// always setting it back to 9999
		this.timeUntilNextEgg = 9999;
		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 */
		TFC_Core.preventEntityDataUpdate = true;
		if (getGender() == GenderEnum.MALE)
		{
			nextEgg = 10000;
		}

		super.onLivingUpdate();
		TFC_Core.preventEntityDataUpdate = false;

		if (hunger > 144000 && rand.nextInt(100) == 0 && getHealth() < TFC_Core.getEntityMaxHealth(this) && !isDead)
		{
			this.heal(1);
		}
		else if (hunger < 144000 && super.isInLove())
		{
			this.setInLove(false);
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
		this.dataWatcher.updateObject(17, (nbt.getBoolean("isSleeping") ? 1 : 0) + nbt.getInteger("sleepTimer") << 1);

		hunger = nbt.getInteger("Hunger");
		this.dataWatcher.updateObject(15, nbt.getInteger("Age"));
		nextEgg = nbt.getLong("nextEgg");
	}

	public void roosterCrow()
	{
		if ((TFC_Time
				.getTotalTicks() - 15) % TFC_Time.DAY_LENGTH == 0 && getGender() == GenderEnum.MALE && isAdult() && this.worldObj
						.canBlockSeeTheSky((int) this.posX, (int) this.posY, (int) this.posZ))
		{
			this.playSound(TFC_Sounds.ROOSTERCROW, 6, rand.nextFloat() / 2F + 0.75F);
		}
	}

	@Override
	public void setAge(int par1)
	{
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
	}

	@Override
	public void setAggressionMod(float aggressionMod)
	{
		this.aggressionMod = aggressionMod;
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
	public void setFamiliarity(int familiarity)
	{
		this.familiarity = familiarity;
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

	public void setNextEgg(long nextEgg)
	{
		this.nextEgg = nextEgg;
	}

	@Override
	public void setObedienceMod(float obedienceMod)
	{
		this.obedienceMod = obedienceMod;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	@Override
	public void setSizeMod(float sizeMod)
	{
		this.sizeMod = sizeMod;
	}

	@Override
	public void setStrengthMod(float strengthMod)
	{
		this.strengthMod = strengthMod;
	}

	public void syncData()
	{
		if (dataWatcher != null)
		{
			if (!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(sex + (isDomesticated ? 2 : 0)));
				this.dataWatcher.updateObject(14, Integer.valueOf(Math.min(this.getNumberBleeding(), 15) + (Math.min(this.getNumberCuts(), 15) << 4)));
				this.dataWatcher.updateObject(17, (sleepTimer << 1) + (isSleeping ? 1 : 0));
				byte[] values = { TFC_Core.getByteFromSmallFloat(sizeMod), TFC_Core.getByteFromSmallFloat(strengthMod),
						TFC_Core.getByteFromSmallFloat(aggressionMod), TFC_Core.getByteFromSmallFloat(obedienceMod),
						(byte) familiarity, (byte) (familiarizedToday ? 1 : 0), (byte) 0, // Empty
						(byte) 0 // Empty
				};
				ByteBuffer buf = ByteBuffer.wrap(values);
				this.dataWatcher.updateObject(22, buf.getInt());
				this.dataWatcher.updateObject(23, buf.getInt());
			}
			else
			{
				sex = this.dataWatcher.getWatchableObjectInt(13) & 1;
				this.numberBleeding = this.dataWatcher.getWatchableObjectInt(14) & 15;
				this.numberCuts = (this.dataWatcher.getWatchableObjectInt(14) >> 4) & 15;
				isDomesticated = ((this.dataWatcher.getWatchableObjectInt(13) & 2) == 2);
				this.isSleeping = ((this.dataWatcher.getWatchableObjectInt(17) & 1) == 1);
				this.sleepTimer = (this.dataWatcher.getWatchableObjectInt(17) >> 1);

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
		this.playSound(this.getHurtSound(), 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
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

		nbt.setBoolean("mating", mating);
		nbt.setBoolean("domesticated", isDomesticated);
		nbt.setBoolean("cantSleep", cantSleep);
		nbt.setInteger("sleepTimer", sleepTimer);
		nbt.setBoolean("isSleeping", isSleeping);

		nbt.setFloat("Strength Modifier", strengthMod);
		nbt.setFloat("Aggression Modifier", aggressionMod);
		nbt.setFloat("Obedience Modifier", obedienceMod);

		nbt.setInteger("Hunger", hunger);
		nbt.setInteger("Age", getBirthDay());
		nbt.setLong("nextEgg", nextEgg);
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
		this.dataWatcher.updateObject(17, sleepTimer << 1 + (isSleeping ? 1 : 0));
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
