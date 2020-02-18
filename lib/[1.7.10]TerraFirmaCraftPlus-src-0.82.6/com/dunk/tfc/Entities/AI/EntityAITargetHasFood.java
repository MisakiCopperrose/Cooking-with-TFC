package com.dunk.tfc.Entities.AI;

import java.util.Collections;
import java.util.List;

import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Entities.IAnimal.InteractionEnum;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class EntityAITargetHasFood extends EntityAITargetNonTamed
{
	private EntityTameable entityTameable;
	private Class targetClass;
	private int targetChance;
	private IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;
	private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
	// private static final String __OBFID = "CL_00001623";

	public EntityAITargetHasFood(EntityTameable entity, Class targetClass, int targetChance, boolean shouldCheckSight)
	{
		super(entity, targetClass, targetChance, shouldCheckSight);
		this.targetClass = targetClass;
		this.targetChance = targetChance;
		this.entityTameable = entity;
		this.targetEntitySelector = new IEntitySelector()
		{
			private static final String __OBFID = "CL_00001621";

			/**
			 * Return whether the specified entity is applicable to this filter.
			 */
			public boolean isEntityApplicable(Entity p_82704_1_)
			{

				if (!(p_82704_1_ instanceof EntityLivingBase) ? false
						: (EntityAITargetHasFood.this.isSuitableTarget((EntityLivingBase) p_82704_1_, false)))
				{
					return false;
				}
				if (p_82704_1_ instanceof EntityPlayer && ((EntityPlayer) p_82704_1_).inventory != null)
				{
					InventoryPlayer inv = ((EntityPlayer) p_82704_1_).inventory;
					boolean hasFood = false;
					for (ItemStack i : inv.mainInventory)
					{
						if (i == null)
						{
							continue;
						}
						if (i.getItem() instanceof ItemFoodTFC)
						{
							hasFood = true;
							return true;
						}
					}
				}
				return false;
			}
		};
		this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(entity);
	}

	@Override
	public boolean continueExecuting()
	{
		EntityLivingBase target = this.taskOwner.getAttackTarget();
		if (target == null)
		{
			return false;
		}
		else if (target instanceof EntityPlayer && ((EntityPlayer) target).inventory != null)
		{
			InventoryPlayer inv = ((EntityPlayer) target).inventory;
			boolean hasFood = false;
			for (ItemStack i : inv.mainInventory)
			{
				if (i == null)
				{
					continue;
				}
				if (i.getItem() instanceof ItemFoodTFC)
				{
					hasFood = true;
					return super.continueExecuting();
				}
			}
		}
		this.taskOwner.setAttackTarget(null);
		this.taskOwner.setTarget(null);
		return false;
	}

	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (entityTameable instanceof IAnimal)
		{
			IAnimal animal = (IAnimal) entityTameable;
			int familiarity = animal.getFamiliarity();
			if (animal.getHunger() > 6 * 24000)
			{
				return false;
			}
			if (this.targetClass == EntityPlayer.class && animal.checkFamiliarity(InteractionEnum.TOLERATEPLAYER,
					null) || animal.getHunger() > 144000)
			{
				return false;
			}
			if (this.targetClass == EntityPlayer.class)
			{

			}
		}

		if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
		{
			return false;
		}
		else
		{
			double d0 = this.getTargetDistance();
			List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass,
					this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
			Collections.sort(list, this.theNearestAttackableTargetSorter);

			if (list.isEmpty())
			{
				return false;
			}
			else
			{
				this.targetEntity = (EntityLivingBase) list.get(0);
				return true;
			}
		}
	}
}
