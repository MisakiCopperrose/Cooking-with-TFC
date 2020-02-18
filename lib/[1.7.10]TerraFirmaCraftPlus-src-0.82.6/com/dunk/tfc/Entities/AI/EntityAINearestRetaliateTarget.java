package com.dunk.tfc.Entities.AI;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dunk.tfc.api.Entities.IAnimal;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAINearestRetaliateTarget extends EntityAITarget
{
	private final Class targetClass;
	private final int targetChance;
	private double speedTowardsTarget = 1.5;
	PathEntity entityPathEntity;
	private int ticksRemaining;
	/**
	 * When true, the mob will continue chasing its target, even if it can't
	 * find a path to them right now.
	 */
	boolean longMemory = true;
	/**
	 * A number of decrementing ticks that allows the entity to attack once the
	 * tick reaches 0.
	 */
	int attackTick;
	/** Instance of EntityAINearestAttackableTargetSorter. */
	private final EntityAINearestRetaliateTarget.Sorter theNearestAttackableTargetSorter;
	private int field_75445_i;
	private double field_151497_i;
	private double field_151495_j;
	private double field_151496_k;

	private int failedPathFindingPenalty;
	/**
	 * This filter is applied to the Entity search. Only matching entities will
	 * be targetted. (null -> no restrictions)
	 */

	private final IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;
	private static final String __OBFID = "CL_00001620";

	public EntityAINearestRetaliateTarget(EntityCreature p_i1663_1_, Class p_i1663_2_, int p_i1663_3_, double speed,
			boolean p_i1663_4_)
	{
		this(p_i1663_1_, p_i1663_2_, p_i1663_3_,speed, p_i1663_4_, false);
	}

	public EntityAINearestRetaliateTarget(EntityCreature p_i1664_1_, Class p_i1664_2_, int p_i1664_3_, double speed,
			boolean p_i1664_4_, boolean p_i1664_5_)
	{
		this(p_i1664_1_, p_i1664_2_, p_i1664_3_, speed, p_i1664_4_, p_i1664_5_, (IEntitySelector) null);
	}

	public EntityAINearestRetaliateTarget(EntityCreature p_i1665_1_, Class p_i1665_2_, int p_i1665_3_, double speed,
			boolean p_i1665_4_, boolean p_i1665_5_, final IEntitySelector selector)
	{
		super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
		this.targetClass = p_i1665_2_;
		this.targetChance = p_i1665_3_;
		this.speedTowardsTarget = speed;
		this.theNearestAttackableTargetSorter = new EntityAINearestRetaliateTarget.Sorter(p_i1665_1_);
		this.setMutexBits(1);
		this.targetEntitySelector = new IEntitySelector()
		{
			private static final String __OBFID = "CL_00001621";

			/**
			 * Return whether the specified entity is applicable to this filter.
			 */
			public boolean isEntityApplicable(Entity entity)
			{
				return !(entity instanceof EntityLivingBase) ? false
						: (selector != null && !selector.isEntityApplicable(entity) ? false
								: EntityAINearestRetaliateTarget.this.isSuitableTarget((EntityLivingBase) entity,
										false));
			}
		};
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(taskOwner instanceof IAnimal && (((IAnimal)taskOwner).isDomesticated()||!((IAnimal)taskOwner).isAdult()||((IAnimal)taskOwner).getSleepTimer() > 0))
		{
			return false;
		}
		if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
		{
			return false;
		}
		else
		{
			//this.speedTowardsTarget = 1.5;
			double d0 = this.getTargetDistance();
			List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass,
					this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
			Collections.sort(list, this.theNearestAttackableTargetSorter);
			EntityLivingBase attacker = taskOwner.getAITarget();
			if (attacker != null && attacker.getDistanceToEntity(taskOwner) < 12)
			{
				this.targetEntity = attacker;
				return true;
			}
			return false;
			/*
			 * if (list.isEmpty()) { return false; } else { for
			 * (EntityLivingBase entity : (List<EntityLivingBase>) list) { if
			 * (entity.equals(taskOwner.getLastAttacker())) { this.targetEntity
			 * = (EntityLivingBase) list.get(0); return true; } } return false;
			 * }
			 */
		}
	}
	/*
	 * @Override public void resetTask() {
	 * this.taskOwner.setAttackTarget((EntityLivingBase)null); }
	 */

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	/*
	 * public void startExecuting() {
	 * this.taskOwner.setAttackTarget(this.targetEntity);
	 * super.startExecuting(); }
	 */

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		if(ticksRemaining == 0)
		{
			return false;
		}
		if(taskOwner instanceof IAnimal && (((IAnimal)taskOwner).isDomesticated()||((IAnimal)taskOwner).getSleepTimer() > 0||!((IAnimal)taskOwner).isAdult()))
		{
			return false;
		}
		EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
		return entitylivingbase == null ? false
				: (!entitylivingbase.isEntityAlive() ? false
						: (!this.longMemory ? !this.taskOwner.getNavigator().noPath()
								: this.taskOwner.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX),
										MathHelper.floor_double(entitylivingbase.posY),
										MathHelper.floor_double(entitylivingbase.posZ))));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		if(taskOwner instanceof IAnimal)
		{
			((IAnimal)taskOwner).setCantSleep(true);
		}
		ticksRemaining = 2000;
		this.taskOwner.setAttackTarget(this.targetEntity);
		this.taskOwner.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
		this.field_75445_i = 0;
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		if(taskOwner instanceof IAnimal)
		{
			((IAnimal)taskOwner).setCantSleep(false);
		}
		this.taskOwner.setAttackTarget(null);
		this.taskOwner.getNavigator().clearPathEntity();
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
		this.taskOwner.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
		double d0 = this.taskOwner.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY,
				entitylivingbase.posZ);
		double d1 = (double) (this.taskOwner.width * 2.0F * this.taskOwner.width * 2.0F + entitylivingbase.width);
		--this.field_75445_i;
		--ticksRemaining;

		boolean dontGetHit = false;
		//make it so that animals don't run into players who are holding weapons
		if(entitylivingbase instanceof EntityPlayer && entitylivingbase.getDistanceSqToEntity(taskOwner) < 20)
		{
			EntityPlayer ep = (EntityPlayer)entitylivingbase;
			Vec3 look = ep.getLookVec();
			double dist = Math.min(d0, 1);
			List list = ep.worldObj.getEntitiesWithinAABBExcludingEntity(ep, ep.boundingBox.addCoord(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist).expand((double)1, (double)1, (double)1));
            
			if(list.contains(taskOwner) && ep.getHeldItem() != null)
			{
				//we don't want to run into the spear
				dontGetHit = true;
			}
		}
		//dontGetHit = true;
		
		if (!dontGetHit && (this.longMemory || this.taskOwner.getEntitySenses()
				.canSee(entitylivingbase)) && this.field_75445_i <= 0 && ((this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D) || entitylivingbase
						.getDistanceSq(this.field_151497_i, this.field_151495_j,
								this.field_151496_k) >= 1.0D || this.taskOwner.getRNG().nextFloat() < 0.05F))
		{
			this.field_151497_i = entitylivingbase.posX;
			this.field_151495_j = entitylivingbase.boundingBox.minY;
			this.field_151496_k = entitylivingbase.posZ;
			//this.field_75445_i = failedPathFindingPenalty + 4 + this.taskOwner.getRNG().nextInt(7);
		}
		else if(dontGetHit)
		{
			double dist = entitylivingbase.getDistanceToEntity(taskOwner);
			dist = Math.max(Math.min(dist,6), 4);
			dist = 4;
			Vec3 sideStep = Vec3.createVectorHelper(dist, 0, 0);
			sideStep.rotateAroundY((float)(-entitylivingbase.rotationYawHead * (Math.PI/180d)));
			this.field_151497_i = entitylivingbase.posX + sideStep.xCoord;
			this.field_151495_j = entitylivingbase.boundingBox.minY + sideStep.yCoord;
			this.field_151496_k = entitylivingbase.posZ + + sideStep.zCoord;
		}
			if (this.taskOwner.getNavigator().getPath() != null)
			{
				PathPoint finalPathPoint = this.taskOwner.getNavigator().getPath().getFinalPathPoint();
				if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord,
						finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
				{
					failedPathFindingPenalty = 0;
				}
				else
				{
					failedPathFindingPenalty += 1;
				}
			}
			else
			{
				failedPathFindingPenalty += 1;
			}

			if (d0 > 1024.0D)
			{
				//this.field_75445_i += 10;
			}
			else if (d0 > 256.0D)
			{
				//this.field_75445_i += 5;
			}
		/*	Vec3 toTarget = Vec3.createVectorHelper(entitylivingbase.posX-taskOwner.posX, entitylivingbase.posY-taskOwner.posY,entitylivingbase.posZ-taskOwner.posZ);
			Vec3 directionToTarget = toTarget.normalize();
			Vec3 runTowards = toTarget.addVector(directionToTarget.xCoord * 5d, directionToTarget.yCoord * 5d, directionToTarget.zCoord * 5d);
			if (!this.taskOwner.getNavigator().tryMoveToXYZ(runTowards.xCoord, runTowards.yCoord, runTowards.zCoord, this.speedTowardsTarget))
			{
				//this.field_75445_i += 15;
			}*/
			if(!dontGetHit)
			{
				if (!this.taskOwner.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
				{
				}
			
			}
			else 
			{
				//System.out.println("going to " + (field_151497_i - entitylivingbase.posX) +", "+ (field_151495_j - entitylivingbase.posY)+", "+ (field_151496_k - entitylivingbase.posZ));
				if (!this.taskOwner.getNavigator().tryMoveToXYZ(field_151497_i, field_151495_j, field_151496_k,this.speedTowardsTarget * 0.75))
				{
					
				}
				//this.field_75445_i += 15;
			}
		

		this.attackTick = Math.max(this.attackTick - 1, 0);

		if (d0 <= d1 && this.attackTick <= 20)
		{
			this.attackTick = 20;

			if (this.taskOwner.getHeldItem() != null)
			{
				this.taskOwner.swingItem();
			}
			this.field_75445_i += 20;
			ticksRemaining = 2000;
			this.taskOwner.attackEntityAsMob(entitylivingbase);
		}
	}
	
	 protected MovingObjectPosition getMovingObjectPositionFromPlayer(World p_77621_1_, EntityPlayer p_77621_2_, boolean p_77621_3_)
	    {
	        float f = 1.0F;
	        float f1 = p_77621_2_.prevRotationPitch + (p_77621_2_.rotationPitch - p_77621_2_.prevRotationPitch) * f;
	        float f2 = p_77621_2_.prevRotationYaw + (p_77621_2_.rotationYaw - p_77621_2_.prevRotationYaw) * f;
	        double d0 = p_77621_2_.prevPosX + (p_77621_2_.posX - p_77621_2_.prevPosX) * (double)f;
	        double d1 = p_77621_2_.prevPosY + (p_77621_2_.posY - p_77621_2_.prevPosY) * (double)f + (double)(p_77621_1_.isRemote ? p_77621_2_.getEyeHeight() - p_77621_2_.getDefaultEyeHeight() : p_77621_2_.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
	        double d2 = p_77621_2_.prevPosZ + (p_77621_2_.posZ - p_77621_2_.prevPosZ) * (double)f;
	        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
	        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
	        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
	        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
	        float f6 = MathHelper.sin(-f1 * 0.017453292F);
	        float f7 = f4 * f5;
	        float f8 = f3 * f5;
	        double d3 = 5.0D;
	        if (p_77621_2_ instanceof EntityPlayerMP)
	        {
	            d3 = ((EntityPlayerMP)p_77621_2_).theItemInWorldManager.getBlockReachDistance();
	        }
	        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
	        return p_77621_1_.func_147447_a(vec3, vec31, p_77621_3_, !p_77621_3_, true);
	    }

	public static class Sorter implements Comparator
	{
		private final Entity theEntity;
		private static final String __OBFID = "CL_00001622";

		public Sorter(Entity p_i1662_1_)
		{
			this.theEntity = p_i1662_1_;
		}

		public int compare(Entity p_compare_1_, Entity p_compare_2_)
		{
			double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
			double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}

		public int compare(Object p_compare_1_, Object p_compare_2_)
		{
			return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
		}
	}
}