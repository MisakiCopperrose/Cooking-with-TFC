package com.dunk.tfc.Entities.AI;

import java.util.List;

import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Entities.IAnimal;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAIGetFood extends EntityAIBase
{
	
	EntityCreature theEntity;
	float hungerPerOunce;
	Item desiredFood;
	Class desiredFoodClass;
	EntityItem foundFood;
	float speed;
	float range;
	int foodTimer = 0;
	float distance;
	PathEntity path;
	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;
	IEntitySelector selector;
	
	public EntityAIGetFood(EntityCreature c, Item food, float ratio, float distance, float range, float speed)
	{
		this.theEntity = c;
		this.hungerPerOunce = ratio;
		this.desiredFood = food;
		this.range = range;
		this.speed = speed;
		this.distance  =distance;
		this.entityPathNavigate = c.getNavigator();
		this.foodTimer = 0;
		selector = new IEntitySelector(){

			@Override
			public boolean isEntityApplicable(Entity entity)
			{
				EntityItem ie = (EntityItem) entity;
				if(ie.getEntityItem().getItem() == desiredFood)
				{
					return true;
				}
				return false;
			}		
		};
	}
	
	public EntityAIGetFood(EntityCreature c, Class food, float ratio, float distance, float range, float speed)
	{
		this.theEntity = c;
		this.hungerPerOunce = ratio;
		this.desiredFoodClass = food;
		this.range = range;
		this.speed = speed;
		this.distance  =distance;
		this.entityPathNavigate = c.getNavigator();
		this.foodTimer = 0;
		selector = new IEntitySelector(){

			@Override
			public boolean isEntityApplicable(Entity entity)
			{
				EntityItem ie = (EntityItem) entity;
				Class cl = ie.getEntityItem().getItem().getClass();
				if(cl == desiredFoodClass || desiredFoodClass.isAssignableFrom(cl))
				{
					return true;
				}
				return false;
			}		
		};
	}

	@Override
	public boolean shouldExecute()
	{
		if(((IAnimal)theEntity).getHunger() >= 160000 || ((IAnimal)theEntity).getFamiliarity() * ((IAnimal)theEntity).getObedienceMod() > 50)
		{
			return false;
		}
		List list = this.theEntity.worldObj.selectEntitiesWithinAABB(EntityItem.class, this.theEntity.boundingBox.expand(this.range, 3.0D, this.range), this.selector);

		if (list.isEmpty())
			return false;

		this.foundFood = (EntityItem)list.get(0);
		if(this.foundFood != null)
		{
			this.path = this.entityPathNavigate.getPathToXYZ(foundFood.posX, foundFood.posY, foundFood.posZ);
			return true;
		}
		return false;
	}
	
	@Override
	public void startExecuting()
	{
		this.entityPathNavigate.setPath(this.path, speed);
		
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		this.entityPathNavigate.clearPathEntity();
		this.path = null;
		this.foundFood = null;
		
	}
	
	@Override
	public boolean continueExecuting()
	{
		return !this.entityPathNavigate.noPath() && foundFood != null && ((IAnimal)theEntity).getHunger() < 160000;
	}

	@Override
	public void updateTask()
	{
		if (this.theEntity.getDistanceSqToEntity(this.foundFood) < distance  && ((IAnimal)theEntity).getHunger() < 168000)
		{
			this.foodTimer++;
	//		System.out.println(theEntity +": " + foodTimer);
			if(foodTimer >50)
			{
				ItemStack is = foundFood.getEntityItem();
				float weight = Food.getWeight(is);
				float hungerToRestore = weight * this.hungerPerOunce;
				if(((IAnimal)theEntity).getHunger()+(int)hungerToRestore <= 168000)
				{
					((IAnimal)theEntity).setHunger(((IAnimal)theEntity).getHunger()+(int)hungerToRestore);
					is = null;
					foundFood.setDead();
				}
				else if(((IAnimal)theEntity).getHunger()+(int)hungerToRestore > 168000)
				{
					int remainingHunger = 168000 - ((IAnimal)theEntity).getHunger();
					float ouncesEaten = remainingHunger / this.hungerPerOunce;
					weight -= ouncesEaten;
					ItemStack is2 = ItemStack.copyItemStack(is);
					Food.setWeight(is2, weight);
					((IAnimal)theEntity).setHunger(168000);
					theEntity.entityDropItem(is2, 0);
					is = null;
					foundFood.setDead();
					foundFood = null;
				}
				foodTimer = 0;
			}
		}
	}
}
