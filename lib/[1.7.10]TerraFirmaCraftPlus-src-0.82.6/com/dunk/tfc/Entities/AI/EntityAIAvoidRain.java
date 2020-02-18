package com.dunk.tfc.Entities.AI;

import com.dunk.tfc.Entities.Mobs.EntityPigTFC;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIAvoidRain extends EntityAIBase
{
    private EntityCreature theEntity;
    private static final String __OBFID = "CL_00001611";

    public EntityAIAvoidRain(EntityCreature p_i1652_1_)
    {
        this.theEntity = p_i1652_1_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(theEntity.getAITarget() != null)
    	{
    		return false;
    	}
        return this.theEntity.worldObj.isRaining() ;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theEntity.getNavigator().setAvoidSun(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theEntity.getNavigator().setAvoidSun(false);
    }
}