package com.dunk.tfc.Items;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemAlcohol extends ItemDrink
{
	float alcoholContent = 0;
	
	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		is = super.drinking(is, world, player);
		if (!world.isRemote)
		{

			Random rand = new Random();
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			//fs.restoreWater(player, 800);
			int time = 400+rand.nextInt(1000);
			fs.consumeAlcohol(alcoholContent);
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(8,time,4));
			}
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(5,time,4));
			}
			if(rand.nextInt(100)==0){
				player.addPotionEffect(new PotionEffect(8,time,4));
			}
			if(rand.nextInt(200)==0){
				player.addPotionEffect(new PotionEffect(10,time,4));
			}
			if(rand.nextInt(150)==0){
				player.addPotionEffect(new PotionEffect(12,time,4));
			}
			if(rand.nextInt(180)==0){
				player.addPotionEffect(new PotionEffect(13,time,4));
			}
			int levelMod = 250*player.experienceLevel;
			if(fs.soberTime >TFC_Time.getTotalTicks()+6000+levelMod){
				/*if(rand.nextInt(4)==0){
					//player.addPotionEffect(new PotionEffect(9,time,4));
				}*/
				if(fs.soberTime >TFC_Time.getTotalTicks()+10000+levelMod){
					if(rand.nextInt(4)==0){
						player.addPotionEffect(new PotionEffect(18,time,4));
					}
					if(fs.soberTime >TFC_Time.getTotalTicks()+14000+levelMod){
						if(rand.nextInt(2)==0){
							player.addPotionEffect(new PotionEffect(15,time,4));
						}
						if(fs.soberTime >TFC_Time.getTotalTicks()+16000+levelMod){
							if(rand.nextInt(10)==0){
								player.addPotionEffect(new PotionEffect(20,time,4));
							}
						}
						if(fs.soberTime > TFC_Time.getTotalTicks()+24000+levelMod && !player.capabilities.isCreativeMode){
							fs.soberTime = 0;

							player.attackEntityFrom(new DamageSource("alcohol").setDamageBypassesArmor().setDamageIsAbsolute(), player.getMaxHealth());
						}
					}

				}
			}
			TFC_Core.setPlayerFoodStats(player, fs);
		}
		return super.postDrink(is, world, player);
	}

	public ItemAlcohol setAlcoholContent(float f)
	{
		this.alcoholContent = f;
		return this;
	}
}
