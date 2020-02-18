package com.dunk.tfc.Effects;

import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.world.World;

public class FastFlameFX extends EntityFlameFX
{
	protected int delay;

	public FastFlameFX(World p_i1209_1_, double p_i1209_2_, double p_i1209_4_, double p_i1209_6_, double p_i1209_8_,
			double p_i1209_10_, double p_i1209_12_)
	{
		super(p_i1209_1_, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
	}

	public FastFlameFX(World p_i1209_1_, double p_i1209_2_, double p_i1209_4_, double p_i1209_6_, double p_i1209_8_,
			double p_i1209_10_, double p_i1209_12_, int particleMaxAge)
	{
		this(p_i1209_1_, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
		this.particleMaxAge = particleMaxAge;
	}
	
	public FastFlameFX(World p_i1209_1_, double p_i1209_2_, double p_i1209_4_, double p_i1209_6_, double p_i1209_8_,
			double p_i1209_10_, double p_i1209_12_, int particleMaxAge, int delay)
	{
		this(p_i1209_1_, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_, particleMaxAge);
		this.delay = delay;
		this.particleMaxAge += delay;
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}
		if (this.particleAge > delay)
		{
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.9599999785423279D;
			this.motionY *= 0.9599999785423279D;
			this.motionZ *= 0.9599999785423279D;

			if (this.onGround)
			{
				this.motionX *= 0.699999988079071D;
				this.motionZ *= 0.699999988079071D;
			}
		}
	}
}
