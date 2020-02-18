package com.dunk.tfc.Entities.Mobs;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_MobData;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Interfaces.IInnateArmor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEndermanTFC extends EntityEnderman implements ICausesDamage, IInnateArmor
{
	public static boolean[] carriableBlocks = new boolean[256];

	public EntityEndermanTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.ENDERMAN_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.ENDERMAN_HEALTH);// MaxHealth
	}

	@Override
	public void onLivingUpdate()
	{
		if ((this.worldObj.isDaytime()
		|| (!TFCOptions.enableDefaultCelestialAngle && TFC_Core.getCelestialDaylight(worldObj, (int) posX, (int) posY, (int) posZ, TFC_Time.getTotalTicks(), 0) > 3))
		&& !this.worldObj.isRemote)
		{
			float f = this.getBrightness(1.0F);

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
			&& this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean flag = true;
				ItemStack itemstack = this.getEquipmentInSlot(4);
				if (itemstack != null)
				{
					if (itemstack.isItemStackDamageable())
					{
						itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));
						if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage())
						{
							this.renderBrokenItemStack(itemstack);
							this.setCurrentItemOrArmor(4, (ItemStack) null);
						}
					}
					flag = false;
				}

				if (flag)
					this.setFire(8);
			}
		}
		super.onLivingUpdate();
	}

	@Override
	public float getBrightness(float p_70013_1_)
	{
		// default
		if (TFCOptions.enableDefaultCelestialAngle)
		{
			return super.getBrightness(p_70013_1_);
		}
		float celestialDaylight = TFC_Core.getCelestialDaylight(worldObj, (int) posX, (int) posY, (int) posZ, TFC_Time.getTotalTicks(), 0);

		float brightness = super.getBrightness(p_70013_1_);
		if (worldObj.canBlockSeeTheSky((int) posX, (int) posY, (int) posZ))
		{
			brightness *= celestialDaylight;
		}
		return brightness;
	}

	@Override
	public EnumDamageType getDamageType(EntityLivingBase is)
	{
		// TODO Auto-generated method stub
		return EnumDamageType.GENERIC;
	}

	@Override
	public int getCrushArmor()
	{
		return -335;
	}

	@Override
	public int getSlashArmor()
	{
		return -335;
	}

	@Override
	public int getPierceArmor()
	{
		return -335;
	}

}
