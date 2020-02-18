package com.dunk.tfc.Handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Achievements;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.BodyTempStats;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Core.Player.SkillStats;
import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Food.ItemMeal;
import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.Handlers.Network.ExtraItemsPacket;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;
import com.dunk.tfc.Items.ItemArrow;
import com.dunk.tfc.Items.ItemBloom;
import com.dunk.tfc.Items.ItemBoots;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemOreSmall;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.Items.ItemSocks;
import com.dunk.tfc.Items.ItemTFCArmor;
import com.dunk.tfc.Items.Tools.ItemCustomBow;
import com.dunk.tfc.Items.Tools.ItemJavelin;
import com.dunk.tfc.api.Armor;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCAttributes;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Interfaces.IBoots;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.IEquipable.ClothingType;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Util.Helper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class EntityLivingHandler
{
	List ores;

	@SubscribeEvent
	public void onEntityLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{

			EntityPlayer player = (EntityPlayer) event.entityLiving;
			// Set Max Health
			float newMaxHealth = FoodStatsTFC.getMaxHealth(player);
			float oldMaxHealth = (float) player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
			if (oldMaxHealth != newMaxHealth)
			{
				player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newMaxHealth);
			}
			if (!player.worldObj.isRemote)
			{
				/*
				 * if (TerraFirmaCraft.proxy.getExtraItemFromPlayer(player.
				 * getGameProfile() .getId()) != ((InventoryPlayerTFC)
				 * player.inventory).extraEquipInventory[0]) { //
				 * System.out.println("The list said " + //
				 * TerraFirmaCraft.proxy.getExtraItemFromPlayer(player.
				 * getGameProfile().getId()) // + ", but we think it should be "
				 * + //
				 * ((InventoryPlayerTFC)player.inventory).extraEquipInventory[0]
				 * ); TerraFirmaCraft.netWrapper.sendTo( new
				 * PlayerExtraInfoMessage(player.getUniqueID(),
				 * ((InventoryPlayerTFC)
				 * player.inventory).extraEquipInventory[0],
				 * TerraFirmaCraft.proxy
				 * .getExtraItemFromPlayer(player.getGameProfile().getId()) ==
				 * null), (EntityPlayerMP) player);
				 * TerraFirmaCraft.netWrapper.sendToServer(new
				 * PlayerExtraInfoMessage(player.getUniqueID(),
				 * ((InventoryPlayerTFC)
				 * player.inventory).extraEquipInventory[0],
				 * TerraFirmaCraft.proxy.getExtraItemFromPlayer(player.
				 * getGameProfile().getId()) == null)); }
				 */
				// Tick Decay
				if (PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) event.entityLiving) != null
				&& PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) event.entityLiving).hasOverrideSpawn)
				{
					PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) event.entityLiving);
					ChunkCoordinates cc = ((EntityPlayer) event.entityLiving).getBedLocation(event.entityLiving.dimension);
					if ((cc != null && !cc.equals(pi.overrideSpawnCoordinates)) || cc == null && cc != pi.overrideSpawnCoordinates)
					{
						((EntityPlayer) event.entityLiving).setSpawnChunk(pi.overrideSpawnCoordinates, false, event.entityLiving.dimension);
						pi.overrideSpawnCoordinates = null;
						pi.hasOverrideSpawn = false;
					}
				}

				if (PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) event.entityLiving) != null)
				{
					PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) event.entityLiving);
					pi.updateAttackTimer();
				}

				TFC_Core.handleItemTicking(player.inventory.mainInventory, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ,
				player.capabilities.isCreativeMode);

				// Handle clothing and temperature calculations
				boolean feetWet = TFC_Core.feetInAnyWater(player, player.worldObj);
				boolean headWet = TFC_Core.headInAnyWater(player, player.worldObj);
				boolean playerOnFire = player.isBurning();
				boolean isInRain = player.worldObj.isRaining() && player.worldObj.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ - 1)
				&& TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ) > 0;

				BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);

				// Get the ambient temperature and adjust it based on the local
				// area.

				boolean shouldWarm = TFC_Core.shouldUpdateLocalWarmthFromHeatSource(player, player.worldObj);
				if (shouldWarm && bodyTemp.tempColdTimeRemaining < 2401)
				{
					if (bodyTemp.temporaryColdProtection < 1)
					{
						bodyTemp.temporaryColdProtection++;
					}
					bodyTemp.tempColdTimeRemaining += 2;
				}
				if (bodyTemp.tempColdTimeRemaining > 0)
				{
					bodyTemp.tempColdTimeRemaining--;
					if (bodyTemp.tempColdTimeRemaining == 0)
					{
						bodyTemp.temporaryColdProtection = 0;
					}
				}
				if (bodyTemp.tempHeatTimeRemaining > 0)
				{
					bodyTemp.tempHeatTimeRemaining--;
					if (bodyTemp.tempHeatTimeRemaining == 0)
					{
						bodyTemp.temporaryHeatProtection = 0;
					}
				}

				int[] resistances =
				TFC_Core.getTemperatureResistanceFromClothes(player, player.worldObj, player.inventory.armorInventory, ((InventoryPlayerTFC) player.inventory).extraEquipInventory);

				resistances[1] += bodyTemp.temporaryColdProtection;
				resistances[0] += bodyTemp.temporaryHeatProtection;

				// Now that we have resistances, we should get the ambient
				// effects of the area.
				float tempBot = 20 - resistances[1] * 5;
				float tempTop = 25 + resistances[0] * 5;
				float tempMid = 0.5f * (tempBot + tempTop);

				int temp = (int) TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ - 1);
				float comfortDelta = tempMid - temp;
				if (comfortDelta > 0)
				{
					comfortDelta = Math.min(comfortDelta, 40 * TFC_Core.getWarmthTemperatureAdjustmentPercentage(player, player.worldObj));
				}
				else if (comfortDelta < 0)
				{
					comfortDelta = Math.max(comfortDelta, -40 * TFC_Core.getCoolthTemperatureAdjustmentPercentage(player, player.worldObj));
				}

				temp += comfortDelta;
				int upperDamp = 0;
				int upperSoaked = 0;
				int lowerDamp = 0;
				int lowerSoaked = 0;
				float waterMovementPenalty = 0;
				int wearFrequency = TFC_Core.getClothingUpdateFrequency();
				for (int i = 0; i < 5; i++)
				{
					// hopefully this goes helmet -> feet

					ItemStack armor = null;
					if (i > 0 && i < 5)
						armor = player.inventory.armorInventory[4 - i];
					ItemStack clothing = null;
					if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).myExtraItems != null)
						clothing = PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).myExtraItems[i];
					// upper body
					ItemTFCArmor g;
					if (armor != null && armor.getItem() instanceof ItemTFCArmor && (((ItemTFCArmor) (armor.getItem())).armorTypeTFC != Armor.leather))
					{
						if (feetWet && i >= 2)
						{
							waterMovementPenalty += 15;
						}
						else if (headWet && i < 2)
						{
							waterMovementPenalty += 15;
						}
					}
					else if (armor != null && armor.getItem() instanceof ItemTFCArmor)
					{
						if (feetWet && i >= 2)
						{
							waterMovementPenalty += 10;
						}
						else if (headWet && i < 2)
						{
							waterMovementPenalty += 10;
						}
					}
					else if (armor != null && armor.getItem() instanceof ItemClothing)
					{
						if (TFC_Time.getTotalTicks() % wearFrequency == 0)
						{
							armor = player.inventory.armorInventory[4 - i] = TFC_Core.handleClothingWear(armor, player, isInRain, (headWet && i <= 2) || (feetWet && i > 2), temp,
							playerOnFire, (armor.getItem() instanceof ItemClothing && ((ItemClothing) armor.getItem()).isStraw()), false, wearFrequency);
						}
						if (armor != null)
						{

							boolean soaked = TFC_Core.isClothingSoaked(armor, player);
							if (feetWet && i > 2)
							{
								waterMovementPenalty += 10;
							}
							else if (headWet && i <= 2)
							{
								waterMovementPenalty += 10;
							}
							if (!soaked && TFC_Core.isClothingDamp(armor, player))
							{
								if (i <= 2)
								{
									upperDamp++;
								}
								else
								{
									lowerDamp++;
								}
							}
							else if (soaked)
							{
								if (i <= 2)
								{
									upperSoaked++;
								}
								else
								{
									lowerSoaked++;
								}
							}
						}
					}
					if (clothing != null && clothing.getItem() instanceof ItemClothing)
					{
						if (TFC_Time.getTotalTicks() % wearFrequency == 0)
						{
							/* clothing = */
							/*
							 * PlayerManagerTFC.getInstance()
							 * .getPlayerInfoFromName(player.getDisplayName()).
							 * myExtraItems[i]
							 */

							((InventoryPlayerTFC) (player.inventory)).extraEquipInventory[i] = TFC_Core.handleClothingWear(clothing, player, isInRain,
							(headWet && i <= 2) || (feetWet && i > 2), temp, playerOnFire, false, armor != null, wearFrequency);
						}
						if (clothing != null)
						{
							boolean soaked = TFC_Core.isClothingSoaked(clothing, player);
							if (feetWet && i > 2)
							{
								waterMovementPenalty += 10;
							}
							else if (headWet && i <= 2)
							{
								waterMovementPenalty += 10;
							}
							if (!soaked && TFC_Core.isClothingDamp(clothing, player))
							{
								if (i <= 2)
								{
									upperDamp++;
								}
								else
								{
									lowerDamp++;
								}
							}
							else if (soaked)
							{
								if (i <= 2)
								{
									upperSoaked++;
								}
								else
								{
									lowerSoaked++;
								}
							}
						}
					}
				}

				if (TFC_Time.getTotalTicks() % wearFrequency == 0 && PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).clothingWetLock)
				{
					AbstractPacket pkt1 = new PlayerUpdatePacket(player, 6);
					TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt1, (EntityPlayerMP) player);
				}

				// ExtraItemsPacket pkt2 = new
				// ExtraItemsPacket(PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).myExtraItems,
				// player,true);

				// TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt2, (EntityPlayerMP)
				// player);

				temp += TFC_Core.getTemperatureChangeFromEnvironment(player, player.worldObj, upperDamp > 1, upperSoaked > 0, lowerDamp + lowerSoaked > 0);

				bodyTemp.ambientTemperature = temp;
				bodyTemp.coldResistance = resistances[1];
				bodyTemp.heatResistance = resistances[0];
				
				if(player.isSprinting() && !player.capabilities.isCreativeMode)
				{
					FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
					fs.drainScaledFood(0.0025f);
					fs.drainScaledWater(1.5f);
					TFC_Core.setPlayerFoodStats(player, fs);
				}

				int discomfort = 0;
				long drunkenness = TFC_Core.getPlayerFoodStats(player).soberTime - TFC_Time.getTotalTicks();
				if (drunkenness > 12000)
				{
					double vX, vZ;
					vX = player.field_71091_bM - player.field_71094_bP;
					vZ = player.field_71085_bR - player.field_71097_bO;
					double velocity = Math.sqrt(vX * vX + vZ * vZ);
					if (velocity < 0.1)
					{
						velocity = 0;
					}
					// System.out.println(velocity > 0 && player.onGround);
					if (velocity > 0 && velocity < 1 && player.onGround)
					{
						player.velocityChanged = true;

						player.moveEntityWithHeading((float) velocity * 1000f * (player.worldObj.rand.nextInt(15)==0?-1:1)* (float) (Math.sin((TFC_Time.getTotalTicks() / 10) % (2 * Math.PI))), 0);
					}
				}
				// System.out.println(velocity+", " + vX + ", " + vZ);
				if (temp < (20 + (-5 * bodyTemp.coldResistance)))
				{
					discomfort = (int) Math.ceil(((float) temp - (float) (20 + (-5 * bodyTemp.coldResistance))) / 5f);
				}
				else if (temp > (25 + (5 * bodyTemp.heatResistance)))
				{
					// If it's daytime, the sun is out and it's too hot, we
					// increase the temp if the player doesn't have a good hat
					if ((TFCOptions.enableDefaultCelestialAngle && player.worldObj.isDaytime()) || (!TFCOptions.enableDefaultCelestialAngle
					&& TFC_Core.getCelestialDaylight(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ, TFC_Time.getTotalTicks(), 0) >= 8))
					{
						if (TFC_Core.isExposed(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ)
						&& !TFC_Core.isExposedToRain(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ))
						{
							// If the player doesn't have a good hat
							if (player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].getItem() instanceof ItemClothing
							&& ((ItemClothing) player.inventory.armorInventory[3].getItem()).getProtectsFromSun())
							{
								// Do nothing
							}
							else
							{
								temp += 5;
								bodyTemp.ambientTemperature = temp;
							}
						}
					}
					discomfort = (int) Math.ceil(((float) temp - (float) (20 + (5 * bodyTemp.heatResistance))) / 5f);
				}
				bodyTemp.discomfort = discomfort;
				if (Math.abs(discomfort) > 3 && !player.capabilities.isCreativeMode && !TFCOptions.enableDebugMode)
				{
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
				}
				if (Math.abs(discomfort) > 5 && !player.capabilities.isCreativeMode && !TFCOptions.enableDebugMode)
				{
					player.addPotionEffect(new PotionEffect(Potion.confusion.id, 20, 1));
				}

				TFC_Core.setBodyTempStats(player, bodyTemp);
				AbstractPacket pkt1 = new PlayerUpdatePacket(player, 5);
				TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt1, (EntityPlayerMP) player);

				// Nullify the Old Food
				player.getFoodStats().addStats(20 - player.getFoodStats().getFoodLevel(), 0.0F);
				// Handle Food
				FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
				foodstats.onUpdate(player);
				TFC_Core.setPlayerFoodStats(player, foodstats);
				// Send update packet from Server to Client
				if (foodstats.shouldSendUpdate())
				{
					AbstractPacket pkt = new PlayerUpdatePacket(player, 0);
					TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
				}
				if (foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.333f)
				{
					setThirsty(player, true);
				}
				else if (foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.333f)
				{
					if (player.isSprinting())
						player.setSprinting(false);
				}
				else
				{
					setThirsty(player, false);
				}
				if (foodstats.stomachLevel / foodstats.getMaxStomach(player) <= 0.5f)
				{
					player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 1));
					player.addPotionEffect(new PotionEffect(Potion.weakness.id, 20, 1));
				}

				// Scan the players inventory for any items that are too heavy
				// to carry normally
				boolean isOverburdened = false;
				if (!player.capabilities.isCreativeMode)
				{
					for (int i = 0; i < player.inventory.mainInventory.length; i++)
					{
						ItemStack is = player.inventory.getStackInSlot(i);
						if (is != null && is.getItem() instanceof IEquipable)
						{
							isOverburdened = ((IEquipable) is.getItem()).getTooHeavyToCarry(is);
							if (isOverburdened)
								break;
						}
					}
				}

				setOverburdened(player, isOverburdened);
				boolean bootsBonus = false;
				float speedBonus = 0f;
				float sockBonus = 0f;
				// if(player.onGround)
				// {
				/*
				 * Block[] blocks = new Block[5]; for(int iii = 0; iii > -5;
				 * iii--) { blocks[4+iii] =
				 * player.worldObj.getBlock((int)player.posX,
				 * (int)player.posY+iii, (int)player.posZ); }
				 */

				Material m = player.worldObj.getBlock((int) player.posX, (int) player.posY - 1, (int) player.posZ - 1).getMaterial();
				ItemStack bootsI = player.getCurrentArmor(0); // hopefully the
				// boots
				ItemStack robeI = player.getCurrentArmor(2); // hopefully the
				// robes
				ItemStack socks = null;
				if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()) != null
				&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).myExtraItems != null)
				{
					socks = PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).myExtraItems[4];
				}

				if (bootsI != null)
				{
					Item boots = bootsI.getItem();
					if (boots != null && boots instanceof IBoots)
					{
						speedBonus = ((IBoots) boots).getSpeedBonus(m);
						bootsBonus = true;
						if (socks != null && ((IBoots) boots).areTrueBoots() && socks.getItem() instanceof ItemSocks)
						{
							float bonus = ((ItemSocks) (socks.getItem())).getWalkableBonus();
							// Add an extra 2%
							sockBonus += bonus;
						}
					}
				}
				if (robeI != null)
				{
					Item robes = robeI.getItem();
					if (robes != null && robes instanceof ItemClothing && ((ItemClothing) robes).getClothingType() == ClothingType.ROBE && player.isSprinting())
					{
						bootsBonus = true;
						speedBonus -= 0.1;
					}
				}
				// Handle broken bones
				FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
				int brokenBone = fs.crushWoundSeverity;
				boolean hasBreak = fs.crushWoundTimer > 0;
				int breakTreatment = fs.crushWoundTreatment;
				int netBreak = hasBreak ? 1 + brokenBone - breakTreatment : 0;

				if (player.getHeldItem() != null && player.getHeldItem().getItem() == TFCItems.woodenStaff && !player.isSprinting() && player.ridingEntity == null)
				{
					netBreak = Math.max(netBreak - 1, 0);
					bootsBonus = true;
					speedBonus += 0.15;
				}
				if (player.getHeldItem() != null && player.getHeldItem().getItem() == TFCItems.woodenStaff)
				{
					netBreak = Math.max(netBreak - 1, 0);
				}
				if(player.ridingEntity != null)
				{
					netBreak = 0;
					speedBonus = 0;
					sockBonus = 0;
					bootsBonus = false;
				}
				// }
				float woundSpeed = 0f - netBreak * 0.15f;
				setSpeedBonus(player, speedBonus, sockBonus, woundSpeed, bootsBonus);

				// Handle Spawn Protection
				NBTTagCompound nbt = player.getEntityData();
				long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.HOUR_LENGTH;
				if (spawnProtectionTimer < TFC_Time.getTotalTicks())
				{
					// Add protection time to the chunks
					for (int i = -2; i < 3; i++)
					{
						for (int k = -2; k < 3; k++)
						{
							int lastChunkX = ((int) Math.floor(player.posX)) >> 4;
							int lastChunkZ = ((int) Math.floor(player.posZ)) >> 4;
							TFC_Core.getCDM(player.worldObj).addProtection(lastChunkX + i, lastChunkZ + k, TFCOptions.protectionGain);
						}
					}

					spawnProtectionTimer += TFC_Time.HOUR_LENGTH;
					nbt.setLong("spawnProtectionTimer", spawnProtectionTimer);
				}
				for (int i = 0; player.worldObj.isRemote && i < player.inventory.mainInventory.length; i++)
				{
					ItemStack is = player.inventory.mainInventory[i];
					if (is != null && player.inventory.mainInventory[i].stackSize <= 0)
						player.inventory.mainInventory[i] = null;
				}
				for (int i = 0; player.worldObj.isRemote && i < player.inventory.armorInventory.length; i++)
				{
					ItemStack is = player.inventory.armorInventory[i];
					if (is != null && player.inventory.armorInventory[i].stackSize <= 0)
						player.inventory.armorInventory[i] = null;
				}
				for (int i = 0; player.worldObj.isRemote && player.inventory instanceof InventoryPlayerTFC
				&& i < ((InventoryPlayerTFC) player.inventory).extraEquipInventory.length; i++)
				{
					ItemStack is = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[i];
					if (is != null && ((InventoryPlayerTFC) player.inventory).extraEquipInventory[i].stackSize <= 0)
						((InventoryPlayerTFC) player.inventory).extraEquipInventory[i] = null;
				}
			}
			else
			{
				// player.prevSwingProgress
				// =(((TFC_Time.getTotalTicks())%20)/4f)/4f;
				// player.swingProgress = player.prevSwingProgress +
				// (1f/60f);//((TFC_Time.getTotalTicks()%20)/4f)/4f;
				// player.isSwingInProgress = true;
				if (TFC_Time.getTotalTicks() % 20 == 0)
				{
					// System.out.println(player.worldObj.getBlock((int)
					// player.posX, (int) player.posY - 2, (int) player.posZ -
					// 1));
				}
				for (int i = 0; player.worldObj.isRemote && i < player.inventory.mainInventory.length; i++)
				{
					ItemStack is = player.inventory.mainInventory[i];
					if (is != null && player.inventory.mainInventory[i].stackSize <= 0)
						player.inventory.mainInventory[i] = null;
				}
				for (int i = 0; player.worldObj.isRemote && i < player.inventory.armorInventory.length; i++)
				{
					ItemStack is = player.inventory.armorInventory[i];
					if (is != null && player.inventory.armorInventory[i].stackSize <= 0)
						player.inventory.armorInventory[i] = null;
				}
				for (int i = 0; player.worldObj.isRemote && player.inventory instanceof InventoryPlayerTFC
				&& i < ((InventoryPlayerTFC) player.inventory).extraEquipInventory.length; i++)
				{
					ItemStack is = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[i];
					if (is != null && ((InventoryPlayerTFC) player.inventory).extraEquipInventory[i].stackSize <= 0)
						((InventoryPlayerTFC) player.inventory).extraEquipInventory[i] = null;
				}
				PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
				FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
				foodstats.clientUpdate();
				/*
				 * if(!pi.canAttack()) { player.swingProgress =
				 * Math.min(player.swingProgress > 0 || player.prevSwingProgress
				 * == 0? player.swingProgress:0.5f, 0.5f);
				 * player.prevSwingProgress = Math.min(player.swingProgress > 0
				 * || player.prevSwingProgress == 0?
				 * player.prevSwingProgress:0.5f, 0.5f); player.swingProgressInt
				 * = (int) (player.swingProgress * 6);
				 * System.out.println(player.prevSwingProgress + ", " +
				 * player.swingProgress + ", " + player.swingProgressInt); }
				 */
				// System.out.println(PlayerManagerTFC.getInstance()
				// .getPlayerInfoFromName(player.getDisplayName()).clothingWetLock
				// + "");
				// Make sure that the server has received our information or a
				// suitable length of time has passed. We do this by seeing if
				// the difference between the current time and total time
				// exceeds 100 ticks or 5 seconds.
				// if (Math.abs(TFC_Time.getTotalTicks() -
				// pi.lastClothingPacket) > 100)
				// {
				/*
				 * int wearFrequency = TFC_Core.getClothingUpdateFrequency(); //
				 * Test if this should be done on the client. if
				 * (player.getDisplayName() ==
				 * Minecraft.getMinecraft().thePlayer.getDisplayName() &&
				 * TFC_Time.getTotalTicks() % wearFrequency == 0) { boolean
				 * feetWet = TFC_Core.feetInAnyWater(player, player.worldObj);
				 * boolean headWet = TFC_Core.headInAnyWater(player,
				 * player.worldObj); boolean playerOnFire = player.isBurning();
				 * boolean isInRain = player.worldObj.isRaining() &&
				 * player.worldObj .canBlockSeeTheSky((int) player.posX, (int)
				 * player.posY, (int) player.posZ - 1); int temp = (int)
				 * TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int)
				 * player.posX, (int) player.posY, (int) player.posZ - 1);
				 * 
				 * for (int i = 0; i < 4; i++) { // hopefully this goes helmet
				 * -> feet ItemStack armor = player.inventory.armorInventory[3 -
				 * i]; ItemStack clothing = null; if
				 * (PlayerManagerTFC.getInstance()
				 * .getPlayerInfoFromName(player.getDisplayName()).myExtraItems
				 * != null) clothing = PlayerManagerTFC.getInstance()
				 * .getPlayerInfoFromName(player.getDisplayName()).myExtraItems[
				 * i + 1]; // upper body ItemTFCArmor g;
				 * 
				 * if (armor != null && armor.getItem() instanceof ItemClothing)
				 * { armor = player.inventory.armorInventory[3 - i] = TFC_Core
				 * .handleClothingWear(armor, player, isInRain, (headWet && i <
				 * 2) || (feetWet && i >= 2), temp, playerOnFire,
				 * (armor.getItem() instanceof ItemClothing && ((ItemClothing)
				 * armor.getItem()).isStraw()), false, wearFrequency); } if
				 * (clothing != null && clothing.getItem() instanceof
				 * ItemClothing) { clothing = PlayerManagerTFC.getInstance()
				 * .getPlayerInfoFromName(player.getDisplayName()).myExtraItems[
				 * i + 1] = TFC_Core .handleClothingWear(clothing, player,
				 * isInRain, (headWet && i < 2) || (feetWet && i >= 2), temp,
				 * playerOnFire, false, armor == null, wearFrequency); } } }
				 */
				for (int i = 0; i < TFC_Core.getExtraEquipInventorySize(); i++)
				{
					if (((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory[i] != null
					&& ((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory[i].isItemStackDamageable()
					&& ((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory[i]
					.getItemDamage() == ((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory[i].getMaxDamage())
					{
						// ((InventoryPlayerTFC) ((EntityPlayer)
						// player).inventory).extraEquipInventory[i] = null;
					}
				}
				if (pi != null && player != null && player.inventory != null && pi.myExtraItems != null
				&& player.getDisplayName() == Minecraft.getMinecraft().thePlayer.getDisplayName())
				{
					if (((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory != null)
					{
						pi.myExtraItems = ((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory;
					}
					else
					{
						pi.myExtraItems = new ItemStack[TFC_Core.getExtraEquipInventorySize()];
					}
					// }
					ExtraItemsPacket pkt = new ExtraItemsPacket(pi.myExtraItems, player);

					// TerraFirmaCraft.PACKET_PIPELINE.sendToAll(pkt);
					TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);

				}
				else if (pi != null && pi.myExtraItems == null && player.getDisplayName() == Minecraft.getMinecraft().thePlayer.getDisplayName())
				{
					if (((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory != null)
					{
						pi.myExtraItems = ((InventoryPlayerTFC) ((EntityPlayer) player).inventory).extraEquipInventory;
					}
					else
					{
						pi.myExtraItems = new ItemStack[TFC_Core.getExtraEquipInventorySize()];
					}
					ExtraItemsPacket pkt = new ExtraItemsPacket(pi.myExtraItems, player);

					// TerraFirmaCraft.PACKET_PIPELINE.sendToAll(pkt);
					TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
				}
				// }
				// else
				// {
				// System.out.println("not yet.");
				// }
				// onUpdate(player) still has a !worldObj.isRemote check, but
				// this allows us to render drunkenness
				if (pi != null && pi.playerUUID.equals(player.getUniqueID()))
				{
					foodstats.onUpdate(player);
					if (player.inventory.getCurrentItem() != null)
					{
						if (player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
						{
							pi.guishowFoodRestoreAmount = true;
							pi.guiFoodRestoreAmount = Food.getWeight(player.inventory.getCurrentItem());
						}
						else if (player.inventory.getCurrentItem().getItem() instanceof ItemFoodTFC)
						{
							pi.guishowFoodRestoreAmount = true;
							pi.guiFoodRestoreAmount = Food.getWeight(player.inventory.getCurrentItem());
						}
						else
							pi.guishowFoodRestoreAmount = false;
					}
					else
						pi.guishowFoodRestoreAmount = false;
				}

			}
		}
	}

	public void setThirsty(EntityPlayer player, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.THIRSTY_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.THIRSTY);
		}

		if (b)
		{
			iattributeinstance.applyModifier(TFCAttributes.THIRSTY);
		}
	}

	public void setOverburdened(EntityPlayer player, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.OVERBURDENED_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.OVERBURDENED);
		}

		if (b)
		{
			iattributeinstance.applyModifier(TFCAttributes.OVERBURDENED);
		}
	}

	public void setClothingPenalties(EntityPlayer player, float penalty)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		UUID[] removals = { TFCAttributes.WET_10_UUID, TFCAttributes.WET_15_UUID, TFCAttributes.WET_20_UUID, TFCAttributes.WET_25_UUID, TFCAttributes.WET_30_UUID,
		TFCAttributes.WET_35_UUID, TFCAttributes.WET_40_UUID, TFCAttributes.WET_45_UUID, TFCAttributes.WET_50_UUID, TFCAttributes.WET_55_UUID, TFCAttributes.WET_60_UUID,
		TFCAttributes.WET_65_UUID, TFCAttributes.WET_70_UUID, TFCAttributes.WET_75_UUID, TFCAttributes.WET_80_UUID, TFCAttributes.WET_85_UUID, TFCAttributes.WET_90_UUID,
		TFCAttributes.WET_95_UUID, TFCAttributes.WET_100_UUID };
		AttributeModifier[] removalsA = { TFCAttributes.WET_10, TFCAttributes.WET_15, TFCAttributes.WET_20, TFCAttributes.WET_25, TFCAttributes.WET_30, TFCAttributes.WET_35,
		TFCAttributes.WET_40, TFCAttributes.WET_45, TFCAttributes.WET_50, TFCAttributes.WET_55, TFCAttributes.WET_60, TFCAttributes.WET_65, TFCAttributes.WET_70,
		TFCAttributes.WET_75, TFCAttributes.WET_80, TFCAttributes.WET_85, TFCAttributes.WET_90, TFCAttributes.WET_95, TFCAttributes.WET_100 };
		for (int i = 0; i < removals.length; i++)
		{
			if (iattributeinstance.getModifier(removals[i]) != null)
			{
				iattributeinstance.removeModifier(removalsA[i]);
			}
		}
		int indexOfPenalty = (int) (penalty / 5f) - 2;
		iattributeinstance.applyModifier(removalsA[indexOfPenalty]);
	}

	public void setSpeedBonus(EntityPlayer player, float speed, float sockSpeed, float woundSpeed, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.WOUND_15_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.WOUND_15);
		}
		if (iattributeinstance.getModifier(TFCAttributes.WOUND_30_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.WOUND_30);
		}
		if (iattributeinstance.getModifier(TFCAttributes.WOUND_45_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.WOUND_45);
		}
		if (iattributeinstance.getModifier(TFCAttributes.WOUND_60_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.WOUND_60);
		}
		if (iattributeinstance.getModifier(TFCAttributes.WOUND_75_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.WOUND_75);
		}
		if (iattributeinstance.getModifier(TFCAttributes.WOUND_90_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.WOUND_90);
		}
		if (iattributeinstance.getModifier(TFCAttributes.ROBE_NEGATIVE_3_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.ROBE_NEGATIVE_3);
		}
		if (iattributeinstance.getModifier(TFCAttributes.ROBE_NEGATIVE_5_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.ROBE_NEGATIVE_5);
		}
		if (iattributeinstance.getModifier(TFCAttributes.ROBE_NEGATIVE_8_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.ROBE_NEGATIVE_8);
		}
		if (iattributeinstance.getModifier(TFCAttributes.ROBE_NEGATIVE_10_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.ROBE_NEGATIVE_10);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_2_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_2);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_5_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_5);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_7_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_7);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_10_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_10);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_12_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_12);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_15_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_15);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_17_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_17);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_20_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_20);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_22_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_22);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_25_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_25);
		}
		if (iattributeinstance.getModifier(TFCAttributes.BOOTS_27_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.BOOTS_27);
		}
		if (iattributeinstance.getModifier(TFCAttributes.SOCKS_2_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.SOCKS_2);
		}
		if (b)
		{
			if (sockSpeed == 0.02f)
			{
				iattributeinstance.applyModifier(TFCAttributes.SOCKS_2);
			}
			if (speed == -0.03f)
			{
				iattributeinstance.applyModifier(TFCAttributes.ROBE_NEGATIVE_3);
			}
			else if (speed == -0.05)
			{
				iattributeinstance.applyModifier(TFCAttributes.ROBE_NEGATIVE_5);
			}
			else if (speed == -0.08f)
			{
				iattributeinstance.applyModifier(TFCAttributes.ROBE_NEGATIVE_8);
			}
			else if (speed == -0.1f)
			{
				iattributeinstance.applyModifier(TFCAttributes.ROBE_NEGATIVE_10);
			}
			else if (speed == 0.02f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_2);
			}
			else if (speed == 0.05f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_5);
			}
			else if (speed == 0.07f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_7);
			}
			else if (speed == 0.1f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_10);
			}
			else if (Math.abs(speed - 0.12f) < 0.0001f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_12);
			}
			else if (speed == 0.15f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_15);
			}
			else if (speed == 0.17f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_17);
			}
			else if (speed == 0.2f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_20);
			}
			else if (speed == 0.22f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_22);
			}
			else if (speed == 0.25f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_25);
			}
			else if (speed == 0.27f)
			{
				iattributeinstance.applyModifier(TFCAttributes.BOOTS_27);
			}
		}
		if (woundSpeed < -0.14f && woundSpeed > -0.16f)
		{
			iattributeinstance.applyModifier(TFCAttributes.WOUND_15);
		}
		else if (woundSpeed < -0.29f && woundSpeed > -0.31f)
		{
			iattributeinstance.applyModifier(TFCAttributes.WOUND_30);
		}
		else if (woundSpeed < -0.44f && woundSpeed > -0.46f)
		{
			iattributeinstance.applyModifier(TFCAttributes.WOUND_45);
		}
		else if (woundSpeed < -0.59f && woundSpeed > -0.61f)
		{
			iattributeinstance.applyModifier(TFCAttributes.WOUND_60);
		}
		else if (woundSpeed < -0.74f && woundSpeed > -0.76f)
		{
			iattributeinstance.applyModifier(TFCAttributes.WOUND_75);
		}
		else if (woundSpeed < -0.89f && woundSpeed > -0.91f)
		{
			iattributeinstance.applyModifier(TFCAttributes.WOUND_90);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleFOV(FOVUpdateEvent event)
	{
		EntityPlayer player = event.entity;

		// Force FOV to 1.0f if the player is overburdened to prevent the screen
		// from zooming in a lot.
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		if (iattributeinstance.getModifier(TFCAttributes.OVERBURDENED_UUID) != null)
		{
			event.newfov = 1.0f;
			return;
		}

		// Calculate FOV based on the variable draw speed of the bow depending
		// on player armor.
		if (player.isUsingItem() && player.getItemInUse().getItem() instanceof ItemCustomBow)
		{
			float fov = 1.0F;
			int duration = player.getItemInUseDuration();
			float speed = ((ItemCustomBow) player.getItemInUse().getItem()).getUseSpeed(player);
			float force = duration / speed;

			if (force > 1.0F * ((ItemCustomBow) player.getItemInUse().getItem()).getDamageMultiplier())
			{
				force = 1.0F * ((ItemCustomBow) player.getItemInUse().getItem()).getDamageMultiplier();
			}
			else
			{
				force *= force;
			}

			fov *= 1.0F - (force * 0.15F);
			event.newfov = fov;
		}
	}

	@SubscribeEvent
	public void handleItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack item = event.item.getEntityItem();
		if (player.inventory instanceof InventoryPlayerTFC)
		{
			ItemStack backItem = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

			// Back slot is empty, and the player is picking up an item that can
			// be equipped in the back slot.
			if (backItem == null && item.getItem() instanceof IEquipable)
			{
				IEquipable equipment = (IEquipable) item.getItem();
				if (equipment.getEquipType(item) == EquipType.BACK
				/*
				 * && TFC_Time.getTotalTicks() % TFC_Core
				 * .getClothingUpdateFrequency() != 1
				 */ && (equipment == TFCItems.quiver || equipment.getTooHeavyToCarry(item)))
				{
					player.inventory.setInventorySlotContents(36, item.copy());
					item.stackSize = 0;
					event.item.setEntityItemStack(item);
				}
			}
			// Back slot contains a quiver, handle picking up arrows and
			// javelins.
			else if (backItem != null && backItem.getItem() instanceof ItemQuiver)
			{
				ItemQuiver quiver = (ItemQuiver) backItem.getItem();

				// Attempt to add arrows that are picked up to the quiver
				// instead of standard inventory.
				if (item.getItem() instanceof ItemArrow)
				{
					ItemStack is = quiver.addItem(backItem, item);
					if (is != null)
						event.item.setEntityItemStack(is);
					else
					{
						is = item;
						is.stackSize = 0;
						event.item.setEntityItemStack(is);
						event.setResult(Result.DENY);
					}
				}
				else if (item.getItem() instanceof ItemJavelin)
				{
					// Check to see if the player has at least 1 javelin on
					// their hotbar.
					boolean foundJav = false;
					for (int i = 0; i < 9; i++)
					{
						if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemJavelin)
							foundJav = true;
					}

					// If there is already a javelin on the hotbar, attempt to
					// put the picked up javelin into the quiver.
					if (foundJav)
					{
						ItemStack is = quiver.addItem(backItem, item);
						if (is == null)
						{
							is = item;
							is.stackSize = 0;
							event.item.setEntityItemStack(is);
							event.setResult(Result.DENY);
						}
					}
				}
			}
		}
		if (ores == null)
		{
			ores = Arrays.asList(Global.ORE_METAL);
		}
		if (item.getItem() == TFCItems.looseRock)
			player.triggerAchievement(TFC_Achievements.achLooseRock);
		else if (item.getItem() instanceof ItemOreSmall)
			player.triggerAchievement(TFC_Achievements.achSmallOre);
		else if (item.getItem() instanceof ItemBloom)
			player.triggerAchievement(TFC_Achievements.achIronAge);
		else if (item.getItem().equals(TFCItems.gemDiamond))
			player.triggerAchievement(TFC_Achievements.achDiamond);
		else if (item.getItem().equals(TFCItems.onion) && TFCOptions.onionsAreGross)
			player.triggerAchievement(TFC_Achievements.achRutabaga);
		else if (item.getItem().equals(TFCItems.oreChunk) && (item.getItemDamage() == ores.indexOf("Limonite")
		|| item.getItemDamage() == Global.oreGrade1Offset + ores.indexOf("Limonite") || item.getItemDamage() == Global.oreGrade2Offset + ores.indexOf("Limonite")))
			player.triggerAchievement(TFC_Achievements.achLimonite);
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			SkillStats skills = TFC_Core.getSkillStats(player);
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			pi.tempSkills = skills;

			// Save the item in the back slot if keepInventory is set to true.
			if (entity.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory") && player.inventory instanceof InventoryPlayerTFC)
			{
				pi.tempEquipment = ((InventoryPlayerTFC) player.inventory).extraEquipInventory.clone();
			}
		}

		if (event.entity.dimension == 1)
			event.entity.travelToDimension(0);
	}

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event)
	{
		boolean processed = false;
		if (!event.entity.worldObj.isRemote /* && event.recentlyHit */ && !(event.entity instanceof EntityPlayer) && !(event.entity instanceof EntityZombie))
		{
			if ((event.source.getSourceOfDamage() instanceof EntityPlayer || event.source.getSourceOfDamage() instanceof IAnimal) || event.source.isProjectile())
			{
				boolean foundFood = false;
				processed = true;
				boolean noButchering = false;
				ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
				EntityPlayer p = null;
				if (event.source.getSourceOfDamage() instanceof EntityPlayer)
					p = (EntityPlayer) event.source.getSourceOfDamage();
				else if (event.source.getSourceOfDamage() instanceof EntityProjectileTFC)
				{
					EntityProjectileTFC proj = (EntityProjectileTFC) event.source.getSourceOfDamage();
					if (proj.shootingEntity instanceof EntityPlayer)
						p = (EntityPlayer) proj.shootingEntity;
				}
				else if (event.source.getSourceOfDamage() instanceof IAnimal)
				{
					noButchering = true;
				}
				for (EntityItem ei : event.drops)
				{
					ItemStack is = ei.getEntityItem();
					if (is.getItem() instanceof IFood)
					{
						if (p == null && !noButchering)
							continue;
						foundFood = true;

						int sweetMod = Food.getSweetMod(is);
						int sourMod = Food.getSourMod(is);
						int saltyMod = Food.getSaltyMod(is);
						int bitterMod = Food.getBitterMod(is);
						int umamiMod = Food.getSavoryMod(is);

						float oldWeight = Food.getWeight(is);
						Food.setWeight(is, 0);
						float newWeight;
						if (!noButchering)
						{
							newWeight = oldWeight * ((TFC_Core.getSkillStats(p).getSkillMultiplier(Global.SKILL_BUTCHERING) + 0.01f) * 0.5f + 0.5f);
						}
						else
						{
							newWeight = oldWeight * 0.25f;
						}
						while (newWeight >= Global.FOOD_MIN_DROP_WEIGHT)
						{
							float fw = Helper.roundNumber(Math.min(Global.FOOD_MAX_WEIGHT, newWeight), 10);
							if (fw < Global.FOOD_MAX_WEIGHT)
								newWeight = 0;
							newWeight -= fw;

							ItemStack result = ItemFoodTFC.createTag(new ItemStack(is.getItem(), 1), fw);

							if (sweetMod != 0)
								Food.setSweetMod(result, sweetMod);
							if (sourMod != 0)
								Food.setSourMod(result, sourMod);
							if (saltyMod != 0)
								Food.setSaltyMod(result, saltyMod);
							if (bitterMod != 0)
								Food.setBitterMod(result, bitterMod);
							if (umamiMod != 0)
								Food.setSavoryMod(result, umamiMod);

							drop.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, result));
						}
					}
					else
					{
						drop.add(ei);
					}
				}
				event.drops.clear();
				event.drops.addAll(drop);
				if (foundFood && p != null)
				{
					TFC_Core.getSkillStats(p).increaseSkill(Global.SKILL_BUTCHERING, 1);
				}
			}
		}

		if (!processed && !(event.entity instanceof EntityPlayer) && !(event.entity instanceof EntityZombie))
		{
			ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
			for (EntityItem ei : event.drops)
			{
				if (!(ei.getEntityItem().getItem() instanceof IFood))
				{
					drop.add(ei);
				}
			}
			event.drops.clear();
			event.drops.addAll(drop);
		}
	}
}
