package com.dunk.tfc.Render;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

@SideOnly(Side.CLIENT)
public class EntityRendererTFC extends EntityRenderer {

	private boolean allowShaderSwitching = true;
	private ResourceLocation currentShader;
	private Entity pointedEntity;

	public EntityRendererTFC(Minecraft minecraft, IResourceManager irm) {
		super(minecraft, irm);
	}

	@Override
	public void deactivateShader()
	{
		if(allowShaderSwitching){
			super.activateNextShader();
		}
	}

	public void setManualShader(ResourceLocation shaderLocation){
		deactivateManualShader();
		try{
			Minecraft mc = Minecraft.getMinecraft();
			this.theShaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shaderLocation);
			this.theShaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
			this.currentShader = shaderLocation;
		}
		catch (IOException ioexception)
		{
			LogManager.getLogger().warn("Failed to load shader: " + shaderLocation, ioexception);
		}
		allowShaderSwitching = false;
	}
	
	
	@Override
	 /**
     * Finds what block or object the mouse is over at the specified partial tick time. Args: partialTickTime
     */
    public void getMouseOver(float p_78473_1_)
    {
        if (Minecraft.getMinecraft().renderViewEntity != null)
        {
            if (Minecraft.getMinecraft().theWorld != null)
            {
                Minecraft.getMinecraft().pointedEntity = null;
                ItemStack heldItem = null;
                if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null)
                {
                	heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
                }
                double d0 = (double)Minecraft.getMinecraft().playerController.getBlockReachDistance();
                if(heldItem != null && heldItem.getItem() instanceof ISize)
                {
                	d0 *= ((ISize)heldItem.getItem()).getReach(heldItem).multiplier;
                }
                else
                {
                	d0 *= 0.75;
                }
                Minecraft.getMinecraft().objectMouseOver = Minecraft.getMinecraft().renderViewEntity.rayTrace(d0, p_78473_1_);
                double d1 = d0;
                Vec3 vec3 = Minecraft.getMinecraft().renderViewEntity.getPosition(p_78473_1_);

                /*
                if (Minecraft.getMinecraft().playerController.extendedReach())
                {
                    d0 = 6.0D;
                    d1 = 6.0D;
                }
                else
                {
                    if (d0 > 3.0D)
                    {
                        d1 = 3.0D;
                    }

                    d0 = d1;
                }
*/
                if (Minecraft.getMinecraft().objectMouseOver != null)
                {
                    d1 = Minecraft.getMinecraft().objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = Minecraft.getMinecraft().renderViewEntity.getLook(p_78473_1_);
                Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                this.pointedEntity = null;
                Vec3 vec33 = null;
                float f1 = 1.0F;
                List list = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABBExcludingEntity(Minecraft.getMinecraft().renderViewEntity, Minecraft.getMinecraft().renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
                double d2 = d1;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity = (Entity)list.get(i);

                    if (entity.canBeCollidedWith())
                    {
                        float f2 = entity.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.isVecInside(vec3))
                        {
                            if (0.0D < d2 || d2 == 0.0D)
                            {
                                this.pointedEntity = entity;
                                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                                d2 = 0.0D;
                            }
                        }
                        else if (movingobjectposition != null)
                        {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D)
                            {
                                if (entity == Minecraft.getMinecraft().renderViewEntity.ridingEntity && !entity.canRiderInteract())
                                {
                                    if (d2 == 0.0D)
                                    {
                                        this.pointedEntity = entity;
                                        vec33 = movingobjectposition.hitVec;
                                    }
                                }
                                else
                                {
                                    this.pointedEntity = entity;
                                    vec33 = movingobjectposition.hitVec;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (this.pointedEntity != null && (d2 < d1 || Minecraft.getMinecraft().objectMouseOver == null))
                {
                    Minecraft.getMinecraft().objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);

                    if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame)
                    {
                        Minecraft.getMinecraft().pointedEntity = this.pointedEntity;
                    }
                }
            }
        }
    }

	public void deactivateManualShader(){
		allowShaderSwitching = true;
		 this.currentShader = null;
		super.deactivateShader();
	}
	
	public ResourceLocation getCurrentShaderLocation(){
		return this.currentShader;
	}

	public boolean getManualShaderBeingUsed(){
		return !this.allowShaderSwitching;
	}

	@Override
	public void activateNextShader()
	{
		if(allowShaderSwitching){
			super.activateNextShader();
		}
	}
}
