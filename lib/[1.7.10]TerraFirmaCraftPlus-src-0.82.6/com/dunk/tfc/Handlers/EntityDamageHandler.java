package com.dunk.tfc.Handlers;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_MobData;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Entities.EntityJavelin;
import com.dunk.tfc.Items.ItemTFCArmor;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Events.EntityArmorCalcEvent;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Interfaces.IDefensiveClothing;
import com.dunk.tfc.api.Interfaces.IInnateArmor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class EntityDamageHandler
{
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		if (entity instanceof EntityPlayer)
		{
			float curMaxHealth = (float) ((EntityPlayer) entity).getEntityAttribute(SharedMonsterAttributes.maxHealth)
					.getAttributeValue();
			float newMaxHealth = FoodStatsTFC.getMaxHealth((EntityPlayer) entity);
			float h = ((EntityPlayer) entity).getHealth();
			if (newMaxHealth != curMaxHealth)
				((EntityPlayer) entity).getEntityAttribute(SharedMonsterAttributes.maxHealth)
						.setBaseValue(newMaxHealth);
			if (newMaxHealth < h)
				((EntityPlayer) entity).setHealth(newMaxHealth);
		}
		
		if(entity instanceof IAnimal)
		{
			handleAnimalAttacked(entity,event.source,event);
		}

		if (event.source == DamageSource.onFire)
		{
			event.ammount = 50;
		}
		else if (event.source == DamageSource.fall)
		{
			float healthMod = TFC_Core.getEntityMaxHealth(entity) / 1000f;
			event.ammount *= 80 * healthMod;
			if(entity instanceof EntityPlayer && !entity.worldObj.isRemote)
			{
				handleWounds((EntityPlayer)entity,event.ammount,event.source);
			}
		}
		else if (event.source == DamageSource.drown)
		{
			event.ammount = 50;
		}
		else if (event.source == DamageSource.lava)
		{
			event.ammount = 100;
		}
		else if (event.source == DamageSource.inWall)
		{
			event.ammount = 100;
		}
		else if (event.source == DamageSource.fallingBlock)
		{
			event.ammount = 100;
		}
		else if (event.source.isExplosion())
		{
			event.ammount *= 30;
		}
		else if (event.source == DamageSource.magic && entity.getHealth() > 25)
		{
			event.ammount = 25;
		}
		else if (("player".equals(event.source.damageType) || "mob".equals(event.source.damageType) || "arrow"
				.equals(event.source.damageType)) && !event.source.isUnblockable())
		{
			event.ammount = applyArmorCalculations(entity, event.source, event.ammount);
			if ("arrow".equals(event.source.damageType))
			{
				Entity e = ((EntityDamageSourceIndirect) event.source).getSourceOfDamage();
				if (e instanceof EntityJavelin)
				{
					((EntityJavelin) e).setDamageTaken((short) (((EntityJavelin) e).damageTaken + 10));
					if (((EntityJavelin) e).damageTaken >= ((EntityJavelin) e).pickupItem.getMaxDamage())
					{
						e.setDead();
					}
				}
			}
		}
	}
	
	protected void handleAnimalAttacked(EntityLivingBase entity, DamageSource source, LivingHurtEvent event)
	{
		if(source.damageType.equals("mob"))
		{
			return;
		}
		if(event.ammount < 50)
		{
			return;
		}
		EnumDamageType damageType = getDamageType(source);
		IAnimal animal = (IAnimal) entity;
		boolean loseFamiliarity = source.getSourceOfDamage() instanceof EntityPlayer;
		if(loseFamiliarity)
		{
			int newFam = Math.max(Math.min(animal.getFamiliarity()/2,animal.getFamiliarity()-10),0);
			animal.setFamiliarity(newFam);
		}
		if (damageType == EnumDamageType.PIERCING)
		{
			animal.pierceAnimal(2000,source.getSourceOfDamage());
		}
		else if (damageType == EnumDamageType.SLASHING)
		{
			animal.slashAnimal(2000,source.getSourceOfDamage());
		}
	}

	protected int applyArmorCalculations(EntityLivingBase entity, DamageSource source, float originalDamage)
	{
		// Armor slots
		ItemStack[] armor = entity.getLastActiveItems();

		ItemStack[] clothing = null;
		if (entity instanceof EntityPlayer)
		{
			clothing = ((InventoryPlayerTFC) ((EntityPlayer) entity).inventory).extraEquipInventory;
		}
		int pierceRating = 0;
		int slashRating = 0;
		int crushRating = 0;

		EntityArmorCalcEvent eventPre = new EntityArmorCalcEvent(entity, originalDamage,
				EntityArmorCalcEvent.EventType.PRE);
		MinecraftForge.EVENT_BUS.post(eventPre);
		float damage = eventPre.incomingDamage;

		if (!source.isUnblockable() && (armor != null || clothing != null))
		{
			// 1. Get Random Hit Location
			int location = getRandomSlot(entity.getRNG());

			// 2. Get Armor Rating for armor in hit Location
			if (armor != null && armor[location] != null && armor[location].getItem() instanceof ItemTFCArmor)
			{
				pierceRating = ((ItemTFCArmor) armor[location].getItem()).armorTypeTFC.getPiercingAR();
				slashRating = ((ItemTFCArmor) armor[location].getItem()).armorTypeTFC.getSlashingAR();
				crushRating = ((ItemTFCArmor) armor[location].getItem()).armorTypeTFC.getCrushingAR();
				if (entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor) entity).getPierceArmor();
					slashRating += ((IInnateArmor) entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}
/*
				// 3. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);

				// 4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult, slashMult, crushMult);

				// 5. Damage the armor that was hit
				armor[location].damageItem((int) processArmorDamage(armor[location], damage), entity);*/
				
				// 5. Damage the armor that was hit
				armor[location].damageItem((int) processArmorDamage(armor[location], damage), entity);
			}
			else if ( armor != null && (armor[location] == null || (armor[location] != null) && !(armor[location]
					.getItem() instanceof ItemTFCArmor)&& !(armor[location].getItem() instanceof IDefensiveClothing)))
			{
				if (entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor) entity).getPierceArmor();
					slashRating += ((IInnateArmor) entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}
				/*
				// 1. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);
				// 4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult, slashMult, crushMult);*/

				
			}
			else if(armor != null && armor[location] != null && armor[location].getItem() instanceof IDefensiveClothing)
			{
				pierceRating = ((IDefensiveClothing) armor[location].getItem()).getArmorType().getPiercingAR();
				slashRating = ((IDefensiveClothing) armor[location].getItem()).getArmorType().getSlashingAR();
				crushRating = ((IDefensiveClothing) armor[location].getItem()).getArmorType().getCrushingAR();
				if (entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor) entity).getPierceArmor();
					slashRating += ((IInnateArmor) entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}
				// 5. Damage the armor that was hit
				armor[location].damageItem(1, entity);
				
			}
			int clothingLocation = 4-location;
			if(clothing != null && clothing[clothingLocation] != null && clothing[clothingLocation].getItem() instanceof IDefensiveClothing)
			{
				pierceRating += ((IDefensiveClothing) clothing[clothingLocation].getItem()).getArmorType().getPiercingAR();
				slashRating += ((IDefensiveClothing) clothing[clothingLocation].getItem()).getArmorType().getSlashingAR();
				crushRating += ((IDefensiveClothing) clothing[clothingLocation].getItem()).getArmorType().getCrushingAR();
				// 5. Damage the armor that was hit
				clothing[clothingLocation].damageItem(1, entity);
				
			}
			if(pierceRating != 0 || slashRating != 0 || crushRating != 0)
			{
				// 3. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);

				// 4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult, slashMult, crushMult);
				
			}
			if(false)
			{
				// a. If the attack hits an unprotected head, it does 75% more
				// damage
				// b. If the attack hits unprotected feet, it applies a slow to
				// the player
				if (location == 3)
					damage *= 1.75f;
				else if (location == 0)
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 100, 1));
			}
			if (entity instanceof EntityPlayer)
			{
				((InventoryPlayerTFC) ((EntityPlayer) entity).inventory).extraEquipInventory = clothing;
			}
			// 6. Apply the damage to the player
			EntityArmorCalcEvent eventPost = new EntityArmorCalcEvent(entity, damage,
					EntityArmorCalcEvent.EventType.POST);
			MinecraftForge.EVENT_BUS.post(eventPost);
			
			if(eventPost.incomingDamage > 0 && entity instanceof EntityPlayer)
			{
				handleWounds((EntityPlayer)entity,eventPost.incomingDamage,source);
			}
			
			// TerraFirmaCraft.log.info(entity.getClass()+",
			// "+eventPre.incomingDamage+", "+eventPost.incomingDamage);
			float hasHealth = entity.getHealth();
			entity.setHealth(entity.getHealth() - eventPost.incomingDamage);
			entity.func_110142_aN().func_94547_a(source, hasHealth, eventPost.incomingDamage);
		}
		return 0;
	}
	
	private void handleWounds(EntityPlayer player, float damage, DamageSource source)
	{
		if(player.worldObj.isRemote)
		{
			return;
		}
		
		
		EnumDamageType damageType = getDamageType(source);
		if(damageType != EnumDamageType.PIERCING && damageType != EnumDamageType.SLASHING && damageType != EnumDamageType.CRUSHING && source != DamageSource.fall)
		{
			//Only these kinds of wounds count
			return;
		}
		float entityMaxHealth = player.getMaxHealth();
		int severity = 0;
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
		//Wounds shouldn't happen every time.
		if(player.worldObj.rand.nextFloat() > (damage / entityMaxHealth))
		{
			return;
		}
		
		//We don't consider wounds for attacks that deal less than 5% of max health
		if(damage / entityMaxHealth > 0.2)
		{
			float damageTakenRatio = damage / entityMaxHealth;
			while(damageTakenRatio > 0.4f)
			{
				severity++;
				damageTakenRatio-=0.4f;
			}
			if(damageType == EnumDamageType.PIERCING)
			{
				//Set the timer for a pierce wound to 90.
				if(fs.pierceWoundTimer > 0)
				{
					fs.pierceWoundSeverity += severity + 1;
					if(fs.pierceWoundSeverity > 2)
					{
						fs.pierceWoundSeverity = 2;
					}
				}
				fs.pierceWoundTimer = 90 * 20;	
				System.out.println("hit!");
			}
			else if(damageType == EnumDamageType.SLASHING)
			{
				//Set the timer for a pierce wound to 90.
				if(fs.slashWoundTimer > 0)
				{
					fs.slashWoundSeverity += severity + 1;
					if(fs.slashWoundSeverity > 2)
					{
						fs.slashWoundSeverity = 2;
					}
				}
				fs.slashWoundTimer = 90 * 20;				
			}
			else if(damageType == EnumDamageType.CRUSHING || source == DamageSource.fall)
			{
				//Set the timer for a crush wound to 120.
				if(fs.crushWoundTimer > 0)
				{
					fs.crushWoundSeverity += severity + 1;
					fs.crushWoundSeverity = Math.min(Math.max(0, fs.crushWoundSeverity), 2);
					if(fs.crushWoundSeverity > 2)
					{
						fs.crushWoundSeverity = 2;
					}
					if(fs.crushWoundTreatment > 0)
					{
						fs.crushWoundTreatment -= severity + 1;
					}
					
				}
				fs.crushWoundTimer = 20 * 60 * 20;
			}
			TFC_Core.setPlayerFoodStats(player, fs);
		}
	}

	private float processDamageSource(DamageSource source, float damage, float pierceMult, float slashMult,
			float crushMult)
	{
		EnumDamageType damageType = getDamageType(source);
		// 4.2 Reduce the damage based upon the incoming Damage Type
		if (damageType == EnumDamageType.PIERCING)
		{
			damage *= pierceMult;
		}
		else if (damageType == EnumDamageType.SLASHING)
		{
			damage *= slashMult;
		}
		else if (damageType == EnumDamageType.CRUSHING)
		{
			damage *= crushMult;
		}
		else if (damageType == EnumDamageType.GENERIC)
		{
			damage *= (crushMult + slashMult + pierceMult) / 3;
		}
		return Math.max(0, damage);
	}

	private EnumDamageType getDamageType(DamageSource source)
	{
		// 4.1 Determine the source of the damage and get the appropriate Damage
		// Type
		if (source.getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem()
					.getItem() instanceof ICausesDamage)
			{
				return ((ICausesDamage) player.getCurrentEquippedItem().getItem()).getDamageType(player);
			}
		}

		if (source.getSourceOfDamage() instanceof EntityLiving)
		{
			EntityLiving el = (EntityLiving) source.getSourceOfDamage();
			if (el.getHeldItem() != null && el.getHeldItem().getItem() instanceof ICausesDamage)
			{
				return ((ICausesDamage) el.getHeldItem().getItem()).getDamageType(el);
			}
		}

		if (source.getSourceOfDamage() instanceof ICausesDamage)
		{
			if(source.getEntity() instanceof EntityLivingBase)
			{
				return ((ICausesDamage) source.getSourceOfDamage()).getDamageType((EntityLivingBase)source.getEntity());
			}
			return ((ICausesDamage) source.getSourceOfDamage()).getDamageType(null);
		}

		return EnumDamageType.GENERIC;
	}

	private int getRandomSlot(Random rand)
	{
		int chance = rand.nextInt(100);
		if (chance < 10)
			return 3;// Helm
		else if (chance < 20)
			return 0;// Feet
		else if (chance < 80)
			return 2;// Chest
		else
			return 1;// Legs
	}

	private float processArmorDamage(ItemStack armor, float baseDamage)
	{
		if (armor.hasTagCompound())
		{
			NBTTagCompound nbt = armor.getTagCompound();
			if (nbt.hasKey("armorReductionBuff"))
			{
				float reductBuff = nbt.getByte("armorReductionBuff") / 100f;
				return baseDamage - (baseDamage * reductBuff);
			}
		}
		return baseDamage;
	}

	/**
	 * @param armorRating
	 *            Armor Rating supplied by the armor
	 * @return Multiplier for damage reduction e.g. damage * multiplier = final
	 *         damage
	 */
	protected float getDamageReduction(int armorRating)
	{
		if (armorRating == -1000)
			armorRating = -999;
		return 1000f / (1000f + armorRating);
	}

	@SubscribeEvent
	public void onAttackEntity(AttackEntityEvent event)
	{
		if (event.entityLiving.worldObj.isRemote)
			return;

		EntityLivingBase attacker = event.entityLiving;
		EntityPlayer player = event.entityPlayer;
		Entity target = event.target;
		ItemStack stack = attacker.getEquipmentInSlot(0);
		if (stack != null && stack.getItem().onLeftClickEntity(stack, player, target))
			return;

		if (target.canAttackWithItem())
		{
			if (!target.hitByEntity(target))
			{
				float damageAmount = TFC_MobData.STEVE_DAMAGE;
				if (stack != null)
				{
					damageAmount = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage)
							.getAttributeValue();
					// player.addChatMessage("Damage: " + i);
					if (damageAmount == 1.0f)
					{
						damageAmount = TFC_MobData.STEVE_DAMAGE;
						// i =
						// player.inventory.getCurrentItem().getItem().getDamageVsEntity(target,
						// player.inventory.getCurrentItem());
					}
					if (stack.getItem() instanceof ItemTerra)
					{
						EnumItemReach r = ((ItemTerra) stack.getItem()).getReach(stack);
						if (r == EnumItemReach.FAR)
						{
							damageAmount *= 1.25;
						}
						else if (r == EnumItemReach.MEDIUM)
						{
							damageAmount *= 1.125;
						}
					}
				}

				if (player.isPotionActive(Potion.damageBoost))
					damageAmount += 3 << player.getActivePotionEffect(Potion.damageBoost).getAmplifier();

				if (player.isPotionActive(Potion.weakness))
					damageAmount -= 2 << player.getActivePotionEffect(Potion.weakness).getAmplifier();

				int knockback = 0;
				float enchantmentDamage = 0;

				if (target instanceof EntityLiving)
				{
					enchantmentDamage = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLiving) target);
					knockback += EnchantmentHelper.getKnockbackModifier(player, (EntityLiving) target);
				}

				if (player.isSprinting())
					++knockback;

				if (damageAmount > 0 || enchantmentDamage > 0)
				{
					boolean criticalHit = player.fallDistance > 0.0F && !player.onGround && !player
							.isOnLadder() && !player.isInWater() && !player.isPotionActive(
									Potion.blindness) && player.ridingEntity == null && target instanceof EntityLiving;

					if (criticalHit && damageAmount > 0)
						damageAmount += event.entity.worldObj.rand.nextInt((int) (damageAmount / 2 + 2));

					damageAmount += enchantmentDamage;
					boolean onFire = false;
					int fireAspect = EnchantmentHelper.getFireAspectModifier(player);

					if (target instanceof EntityLiving && fireAspect > 0 && !target.isBurning())
					{
						onFire = true;
						target.setFire(1);
					}

					boolean entityAttacked = target.attackEntityFrom(DamageSource.causePlayerDamage(player),
							damageAmount);
					// knockback = -2;
					if (entityAttacked)
					{
						if (knockback > 0)
						{
							target.addVelocity(
									-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F,
									0.1D,
									MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F);
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}

						if (criticalHit)
							player.onCriticalHit(target);

						if (enchantmentDamage > 0)
							player.onEnchantmentCritical(target);

						if (damageAmount >= 18)
							player.triggerAchievement(AchievementList.overkill);

						player.setLastAttacker(target);

						if (target instanceof EntityLiving)
							target.attackEntityFrom(DamageSource.causeThornsDamage(attacker), damageAmount);
					}

					ItemStack itemstack = player.getCurrentEquippedItem();
					Object object = target;

					if (target instanceof EntityDragonPart)
					{
						IEntityMultiPart ientitymultipart = ((EntityDragonPart) target).entityDragonObj;
						if (ientitymultipart instanceof EntityLiving)
							object = ientitymultipart;
					}

					if (itemstack != null && object instanceof EntityLiving)
					{
						itemstack.hitEntity((EntityLiving) object, player);
						if (itemstack.stackSize <= 0)
							player.destroyCurrentEquippedItem();
					}

					if (target instanceof EntityLivingBase)
					{
						player.addStat(StatList.damageDealtStat, Math.round(damageAmount * 10.0f));
						if (fireAspect > 0 && entityAttacked)
							target.setFire(fireAspect * 4);
						else if (onFire)
							target.extinguish();
					}

					player.addExhaustion(0.3F);
				}
			}
		}
		event.setCanceled(true);
	}
}
