package com.dunk.tfc.Entities.AI;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIForage extends EntityAIBase
{
	EntityPigTFC taskOwner;
	private double foodX;
	private double foodY;
	private double foodZ;
	private double movementSpeed;
	private World theWorld;
	private int numAttempts = 0;
	private Block foodSource;
	private int timer = 0;

	public EntityAIForage(EntityPigTFC theEntity, Block food)
	{
		super();
		this.taskOwner = theEntity;
		this.movementSpeed = 0.5;
		this.foodSource = food;
		this.theWorld = taskOwner.worldObj;
	}
	
	@Override
	public void resetTask()
	{
		timer = 0;
		foodX = 0;
		foodY = 0;
		foodZ = 0;
	}
	
	//Allows an Entity to see whether or not it is foraging
	public int getTimer()
	{
		return this.timer;
	}

	@Override
	public boolean shouldExecute()
	{
		if (taskOwner.getHunger() < 144000 && !taskOwner.isDomesticated() && taskOwner.isAdult())
		{
			Vec3 v = findPossibleFood();
			if(v != null)
			{
				this.foodX = v.xCoord;
				this.foodY = v.yCoord;
				this.foodZ = v.zCoord;
				return true;
			}
		}
		return false;
	}

	public void startExecuting()
	{
		this.taskOwner.getNavigator().tryMoveToXYZ(this.foodX, this.foodY, this.foodZ, this.movementSpeed);
	}

	public boolean continueExecuting()
	{
		return timer > 0 && taskOwner.getAITarget() == null; /*|| (numAttempts < 10 && taskOwner
				.getHunger() < 144000);*/
	}

	public void updateTask()
	{
		int i = MathHelper.floor_double(this.taskOwner.posX);
		int j = MathHelper.floor_double(this.taskOwner.posY);
		int k = MathHelper.floor_double(this.taskOwner.posZ);
		
		if (this.theWorld.getBlock(i, j, k) == foodSource && timer == 0)
		{
			this.timer = 120;
			this.theWorld.setEntityState(this.taskOwner, (byte)10);
	        this.taskOwner.getNavigator().clearPathEntity();
		}
		else if(this.theWorld.getBlock(i, j, k) == foodSource && timer > 0)
		{
	      //  this.taskOwner.getNavigator().clearPathEntity();
		}
		this.timer = Math.max(0, this.timer - 1);

		if (this.timer == 30)
		{

			if (this.theWorld.getBlock(i, j, k) == foodSource)
			{
				this.theWorld.func_147480_a(i, j, k, false);
				this.taskOwner.eatGrassBonus();
			}
			/*
			 * else if (this.theWorld.getBlock(i, j - 1, k) == Blocks.grass) {
			 * if (this.theWorld.getGameRules().getGameRuleBooleanValue(
			 * "mobGriefing")) { this.theWorld.playAuxSFX(2001, i, j - 1, k,
			 * Block.getIdFromBlock(Blocks.grass)); this.theWorld.setBlock(i, j
			 * - 1, k, Blocks.dirt, 0, 2); }
			 * 
			 * this.taskOwner.eatGrassBonus(); }
			 */
		}
	}

	private Vec3 findPossibleFood()
	{
		Random random = this.taskOwner.getRNG();

		for (int i = 0; i < 10; ++i)
		{
			int j = MathHelper.floor_double(this.taskOwner.posX + (double) random.nextInt(20) - 10.0D);
			int k = MathHelper.floor_double(this.taskOwner.boundingBox.minY + (double) random.nextInt(6) - 3.0D);
			int l = MathHelper.floor_double(this.taskOwner.posZ + (double) random.nextInt(20) - 10.0D);

			if (theWorld.getBlock(j, k, l) == foodSource && this.taskOwner.getBlockPathWeight(j, k, l) < 0.0F)
			{
				return Vec3.createVectorHelper((double) j, (double) k, (double) l);
			}
		}

		return null;
	}

}
