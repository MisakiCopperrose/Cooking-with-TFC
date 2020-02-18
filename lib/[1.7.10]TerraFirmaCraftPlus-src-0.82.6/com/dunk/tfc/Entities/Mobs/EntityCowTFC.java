package com.dunk.tfc.Entities.Mobs;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_MobData;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.Entities.AI.AIEatGrass;
import com.dunk.tfc.Entities.AI.EntityAIAvoidEntityTFC;
import com.dunk.tfc.Entities.AI.EntityAIAvoidRain;
import com.dunk.tfc.Entities.AI.EntityAIFleeRain;
import com.dunk.tfc.Entities.AI.EntityAIMateTFC;
import com.dunk.tfc.Entities.AI.EntityAINearestRetaliateTarget;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemBandage;
import com.dunk.tfc.Items.ItemCustomNameTag;
import com.dunk.tfc.Items.Tools.ItemCustomBucketMilk;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Entities.IAnimal.GenderEnum;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityCowTFC extends EntityCow implements ICausesDamage, IAnimal
{
	protected static final float GESTATION_PERIOD = 9.0f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is
	 * calculated by cube-square law from the square root of the ratio of female
	 * mass : male mass
	 */
	protected static final float DIMORPHISM = 0.1822f;
	protected static final int DEGREE_OF_DIVERSION = 1;
	protected static final int FAMILIARITY_CAP = 30;
	protected final AIEatGrass aiEatGrass = new AIEatGrass(this);

	protected boolean isDomesticated;
	protected boolean cantSleep = false;
	protected int sleepTimer = 0;
	protected boolean isSleeping = false;
	public boolean mating = false;

	protected int numberCuts, numberBleeding;
	protected ArrayList<Wound> cuts, bleeding;

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected boolean canMilk;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long timeOfConception;
	protected float mateSizeMod;
	protected float mateStrengthMod;
	protected float mateAggroMod;
	protected float mateObedMod;
	protected float sizeMod; // How large the animal is
	protected float strengthMod; // how strong the animal is
	protected float aggressionMod = 1;// How aggressive / obstinate the animal is
	protected float obedienceMod = 1; // How well the animal responds to commands.
	protected boolean inLove;

	protected int familiarity;
	protected long lastFamiliarityUpdate;
	protected boolean familiarizedToday;

	public EntityCowTFC(World par1World)
	{
		super(par1World);
		animalID = TFC_Time.getTotalTicks() + getEntityId();
		hunger = 168000;
		pregnant = false;
		pregnancyRequiredTime = (int) (TFCOptions.animalTimeMultiplier * GESTATION_PERIOD * TFC_Time.ticksInMonth);
		timeOfConception = 0;
		mateSizeMod = 0;
		sex = rand.nextInt(2);

		cuts = new ArrayList<Wound>();
		bleeding = new ArrayList<Wound>();

		sizeMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt((DEGREE_OF_DIVERSION + 1) * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1F) * (1.0F - DIMORPHISM * sex));
		strengthMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + sizeMod));
		aggressionMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1));
		obedienceMod = (float) Math.sqrt(((rand.nextInt(rand.nextInt(DEGREE_OF_DIVERSION * 10) + 1) * (rand.nextBoolean() ? 1 : -1)) * 0.01f + 1f / aggressionMod));
		this.setSize(0.9F, 1.3F);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.getNavigator().setAvoidsWater(true);
		// this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(1, new EntityAINearestRetaliateTarget(this, EntityLivingBase.class, 1, 2, true));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.5f, true));
		this.tasks.addTask(1, new EntityAIAvoidRain(this));
		this.tasks.addTask(2, new EntityAIMateTFC(this, worldObj, 1.0f));
		this.tasks.addTask(3, new EntityAIFleeRain(this, 1.0D));
		this.tasks.addTask(3, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.wheatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.ryeGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.riceGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.barleyGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.oatGrain, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2F, TFCItems.maizeEar, false));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityWolfTFC.class, 8f, 0.5F, 0.7F));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
		this.tasks.addTask(5, new EntityAIWander(this, 0.9));
		this.tasks.addTask(6, this.aiEatGrass);
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityCowTFC.class, 6.0F));
		this.tasks.addTask(3, new EntityAIAvoidEntityTFC(this, EntityBear.class, 16f, 0.25F, 0.3F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

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

	public EntityCowTFC(World par1World, IAnimal mother, List<Float> data)
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
		if (mother.getFamiliarity() > 99 || ((IAnimal) mother).isDomesticated())
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
			return TFC_Core.translate("entity.cowTFC.name");
		}
		return TFC_Core.translate("entity.aurochsTFC.name");
	}

	@Override
	public boolean canAttackClass(Class p_70686_1_)
	{
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		float f = TFC_MobData.AUROCHS_DAMAGE;
		int i = 7;

		if (p_70652_1_ instanceof EntityLivingBase)
		{
			f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) p_70652_1_);
			i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) p_70652_1_);
		}

		boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);

		if (flag)
		{
			if (i > 0)
			{
				p_70652_1_.addVelocity((double) (-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D,
				(double) (MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0)
			{
				p_70652_1_.setFire(j * 4);
			}

			if (p_70652_1_ instanceof EntityLivingBase)
			{
				EnchantmentHelper.func_151384_a((EntityLivingBase) p_70652_1_, this);
			}

			EnchantmentHelper.func_151385_b(this, p_70652_1_);
		}
		this.setRevengeTarget(null);
		this.setAttackTarget(null);
		return flag;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.AUROCHS_HEALTH);// MaxHealth
	}

	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

	@Override
	public boolean canMateWith(IAnimal animal)
	{
		return animal.getGender() != this.getGender() && this.isAdult() && animal.isAdult() && animal instanceof EntityCowTFC
		&& ((IAnimal) animal).isDomesticated() == this.isDomesticated;
	}

	@Override
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player)
	{
		boolean flag = false;
		switch (interaction)
		{
		case BREED:
			flag = familiarity > 20 || isDomesticated;
			break;
		case MILK:
			flag = familiarity > 15 || isDomesticated;
			break;
		case NAME:
			flag = familiarity > 40 || isDomesticated;
			break; // 5 higher than adult cap
		default:
			break;
		}
		if (!flag && player != null && !player.worldObj.isRemote && !(player.getHeldItem()!=null && player.getHeldItem().getItem() instanceof ItemBandage))
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.notFamiliar"));
		}
		return flag;
	}

	@Override
	public EntityCow createChild(EntityAgeable entityageable)
	{
		return (EntityCow) createChildTFC(entityageable);
	}

	@Override
	public EntityAgeable createChildTFC(EntityAgeable eAgeable)
	{
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(eAgeable.getEntityData().getFloat("MateSize"));
		data.add(eAgeable.getEntityData().getFloat("MateStrength"));
		data.add(eAgeable.getEntityData().getFloat("MateAggro"));
		data.add(eAgeable.getEntityData().getFloat("MateObed"));
		return new EntityCowTFC(worldObj, this, data);
	}

	public EntityCorpse getCorpse()
	{
		NBTTagCompound nbt = this.getEntityData();
		this.writeEntityToNBT(nbt);
		nbt.setFloat("growth", TFC_Core.getPercentGrown(this));
		return new EntityCorpse(worldObj, nbt, 2);
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		float ageMod = TFC_Core.getPercentGrown(this);
		//captureDrops = false;
		this.entityDropItem(new ItemStack(TFCItems.hide, 1, Math.max(0, Math.min(2, (int) (ageMod * 3 - 1)))), 0);
		this.dropItem(TFCItems.bone, (int) ((rand.nextInt(6) + 6) * ageMod));
		this.dropItem(TFCItems.sinew, (int) ((rand.nextInt(4) + 3) * ageMod));
		if (ageMod >= 1 && this.sex == 0)
		{
			this.dropItem(TFCItems.horn, 2);
		}
		// EntityCorpse myCorpse = getCorpse();
		// myCorpse.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
		/// myCorpse.rotationYawHead = myCorpse.rotationYaw;
		// myCorpse.renderYawOffset = myCorpse.rotationYaw;
		// this.worldObj.spawnEntityInWorld(myCorpse);
		// System.out.println("spawned");
		float foodWeight = ageMod * (this.sizeMod * 4000);

		TFC_Core.animalDropMeat(this, TFCItems.beefRaw, foodWeight);
		captureDrops = false;
	}

	@Override
	public void eatGrassBonus()
	{
		hunger += 24000;
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
															// pregnant, canMilk
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
		if (stack != null && this.isFood(stack) && !familiarizedToday && canFamiliarize())
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
		else if(stack != null && this.getNumberCuts() + this.getNumberBleeding() > 0 && stack.getItem() instanceof ItemBandage)
		{
			familiarizedToday = true;
		}
	}

	@Override
	public float getAggressionMod()
	{
		return aggressionMod;
	}

	public long getAnimalID()
	{
		return animalID;
	}

	@Override
	public int getAnimalTypeID()
	{
		return Helper.stringToInt("cow");
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
		return Items.leather;
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

	public long getHasMilkTime()
	{
		return hasMilkTime;
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

	@Override
	public int getNumberOfDaysToAdult()
	{
		return (int) (TFCOptions.animalTimeMultiplier * TFC_Time.daysInMonth * 36);
	}

	@Override
	public float getObedienceMod()
	{
		return obedienceMod;
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
		if (!worldObj.isRemote)
		{
			if (player.isSneaking() && !familiarizedToday && canFamiliarize())
			{
				this.familiarize(player);
				return true;
			}

			if (getGender() == GenderEnum.FEMALE && pregnant)
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("entity.pregnant"));
			}

			if (getGender() == GenderEnum.FEMALE && isAdult() && hasMilkTime < TFC_Time.getTotalTicks() && this.checkFamiliarity(InteractionEnum.MILK, player))
			{
				ItemStack heldItem = player.inventory.getCurrentItem();
				if (heldItem != null && heldItem.getItem() == TFCItems.woodenBucketEmpty)
				{
					if (!familiarizedToday && this.familiarity <= FAMILIARITY_CAP)
					{
						familiarizedToday = true;
						this.getLookHelper().setLookPositionWithEntity(player, 0, 0);
						this.playLivingSound();
					}

					ItemStack milkBucket = new ItemStack(TFCItems.woodenBucketMilk);
					ItemCustomBucketMilk.createTag(milkBucket, 20f);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, milkBucket);
					hasMilkTime = TFC_Time.getTotalTicks() + TFC_Time.DAY_LENGTH;// Can
																					// be
																					// milked
																					// once
																					// every
																					// day
					return true;
				}
			}
		}

		ItemStack itemstack = player.inventory.getCurrentItem();
		if (itemstack != null && this.isBreedingItemTFC(itemstack) && this.checkFamiliarity(InteractionEnum.BREED, player) && this.getGrowingAge() == 0 && !super.isInLove()
		&& (this.familiarizedToday || !canFamiliarize()))
		{
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem,
				((ItemFoodTFC) itemstack.getItem()).onConsumedByEntity(player.getHeldItem(), worldObj, this));
			}
			this.hunger += 24000;
			this.func_146082_f(player);
			return true;
		}
		else if (itemstack != null && itemstack.getItem() instanceof ItemCustomNameTag && itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("ItemName"))
		{
			if (this.trySetName(itemstack.stackTagCompound.getString("ItemName"), player))
			{
				itemstack.stackSize--;
			}
			return true;
		}
		else
		{
			return super.interact(player);
		}
	}

	@Override
	public boolean isAdult()
	{
		return getBirthDay() + getNumberOfDaysToAdult() <= TFC_Time.getTotalDays();
	}

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean isBreedingItemTFC(ItemStack item)
	{
		return !pregnant && isFood(item);
	}

	@Override
	public boolean isChild()
	{
		return !isAdult();
	}

	@Override
	public boolean isFood(ItemStack item)
	{
		return item != null && (item.getItem() == TFCItems.wheatGrain || item.getItem() == TFCItems.oatGrain || item.getItem() == TFCItems.riceGrain
		|| item.getItem() == TFCItems.barleyGrain || item.getItem() == TFCItems.ryeGrain || item.getItem() == TFCItems.maizeEar);
	}

	public boolean isMilkable()
	{
		return canMilk;
	}

	@Override
	public boolean isPregnant()
	{
		return pregnant;
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
		mateAggroMod = otherAnimal.getAggressionMod();
		mateObedMod = otherAnimal.getObedienceMod();
		mateSizeMod = otherAnimal.getSizeMod();
		mateStrengthMod = otherAnimal.getStrengthMod();
	}

	@Override
	public boolean isMovementBlocked()
	{
		return sleepTimer > 0 || super.isMovementBlocked();
	}

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
				if (entityaitaskentry != null && entityaitaskentry.action instanceof EntityAIMateTFC)
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

		this.handleFamiliarityUpdate();

		if (getGender() == GenderEnum.FEMALE && isAdult() && hasMilkTime < TFC_Time.getTotalTicks() && this.checkFamiliarity(InteractionEnum.MILK, null))
			canMilk = true;
		else
			canMilk = false;

		syncData();
		if (isAdult())
		{
			setGrowingAge(0);
		}
		else
		{
			setGrowingAge(-1);
		}
		if (!this.worldObj.isRemote && isPregnant())
		{
			if (TFC_Time.getTotalTicks() >= timeOfConception + pregnancyRequiredTime)
			{
				EntityCowTFC baby = (EntityCowTFC) createChildTFC(this);
				baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
				baby.rotationYawHead = baby.rotationYaw;
				baby.renderYawOffset = baby.rotationYaw;
				worldObj.spawnEntityInWorld(baby);
				baby.setAge(TFC_Time.getTotalDays());
				pregnant = false;
			}
		}

		/**
		 * This Cancels out the changes made to growingAge by EntityAgeable
		 */
		TFC_Core.preventEntityDataUpdate = true;
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

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		animalID = nbt.getLong("Animal ID");
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
		pregnant = nbt.getBoolean("Pregnant");
		mateSizeMod = nbt.getFloat("MateSize");
		mateStrengthMod = nbt.getFloat("MateStrength");
		mateAggroMod = nbt.getFloat("MateAggro");
		mateObedMod = nbt.getFloat("MateObed");
		timeOfConception = nbt.getLong("ConceptionTime");
		hasMilkTime = nbt.getLong("HasMilkTime");
		canMilk = nbt.getBoolean("CanMilk");
		this.setAge(nbt.getInteger("Age"));
	}

	@Override
	public void setAge(int par1)
	{
		// if(!TFC_Core.PreventEntityDataUpdate) {
		this.dataWatcher.updateObject(15, Integer.valueOf(par1));
		// }
	}

	@Override
	public void setAggressionMod(float aggressionMod)
	{
		this.aggressionMod = aggressionMod;
	}

	public void setAnimalID(long animalID)
	{
		this.animalID = animalID;
	}

	@Override
	public void setAttackedVec(Vec3 attackedVec)
	{
		// None
	}

	@Override
	public void setBirthDay(int day)
	{
		this.dataWatcher.updateObject(15, day);
	}

	public void setCanMilk(boolean canMilk)
	{
		this.canMilk = canMilk;
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
		// No fear source
	}

	@Override
	public void setGrowingAge(int par1)
	{
		if (!TFC_Core.preventEntityDataUpdate)
		{
			this.dataWatcher.updateObject(12, Integer.valueOf(par1));
		}
	}

	public void setHasMilkTime(long hasMilkTime)
	{
		this.hasMilkTime = hasMilkTime;
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

	@Override
	public void setObedienceMod(float obedienceMod)
	{
		this.obedienceMod = obedienceMod;
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
	public void setSizeMod(float sizeMod)
	{
		this.sizeMod = sizeMod;
	}

	@Override
	public void setStrengthMod(float strengthMod)
	{
		this.strengthMod = strengthMod;
	}

	public void setTimeOfConception(long timeOfConception)
	{
		this.timeOfConception = timeOfConception;
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
				byte[] values = { TFC_Core.getByteFromSmallFloat(sizeMod), TFC_Core.getByteFromSmallFloat(strengthMod), TFC_Core.getByteFromSmallFloat(aggressionMod),
				TFC_Core.getByteFromSmallFloat(obedienceMod), (byte) familiarity, (byte) (familiarizedToday ? 1 : 0), (byte) (pregnant ? 1 : 0), (byte) (canMilk ? 1 : 0) };
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
				pregnant = values[6] == 1;
				canMilk = values[7] == 1;

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
			this.setAlwaysRenderNameTag(true);
			return true;
		}
		this.playSound(this.getHurtSound(), 6, rand.nextFloat() / 2F + (isChild() ? 1.25F : 0.75F));
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Sex", sex);
		nbt.setLong("Animal ID", animalID);
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

		NBTTagCompound nbt2 = nbt;
		nbt2.setFloat("Strength Modifier", strengthMod);
		nbt2.setFloat("Aggression Modifier", aggressionMod);
		nbt2.setFloat("Obedience Modifier", obedienceMod);

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
		nbt.setLong("HasMilkTime", hasMilkTime);
		nbt.setBoolean("CanMilk", canMilk);
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
	public EnumDamageType getDamageType(EntityLivingBase is)
	{
		if(is != null && is.worldObj != null &&  is instanceof IAnimal && is.worldObj.rand.nextInt(3)==0 && ((IAnimal)is).getGender() == GenderEnum.MALE)
		{
			return EnumDamageType.PIERCING;
		}
		return EnumDamageType.CRUSHING;
	}

	@Override
	public void healSlash()
	{
		if (this.cuts != null && this.getNumberCuts() > 0)
		{
			this.cuts.remove(this.getNumberCuts() - 1);
		}
	}

	@Override
	public void healPierce()
	{
		if (this.bleeding != null && this.getNumberBleeding() > 0)
		{
			this.bleeding.remove(this.getNumberBleeding() - 1);
		}
	}
}
