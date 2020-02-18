package com.dunk.tfc.Render.Models;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class ModelClothingBase extends ModelBase
{

	//This code would make smart moving work correctly. It would require SM to be enabled.
	protected void fixRotationsForSmartMoving(RenderPlayer renderer)
	{
	/*	if (Loader.isModLoaded("SmartMoving"))
		{
			net.smart.render.SmartRenderModel baseModel = net.smart.moving.render.playerapi.SmartMoving.getPlayerBase(renderer).getRenderModel().modelBipedMain.imp.getMovingModel().md;
			renderer.modelBipedMain.bipedLeftLeg.rotationPointX = baseModel.bipedPelvic.rotationPointX + baseModel.bipedLeftLeg.rotationPointX;
			renderer.modelBipedMain.bipedLeftLeg.rotationPointY = baseModel.bipedPelvic.rotationPointY + baseModel.bipedLeftLeg.rotationPointY;
			renderer.modelBipedMain.bipedLeftLeg.rotationPointZ = baseModel.bipedPelvic.rotationPointZ + baseModel.bipedLeftLeg.rotationPointZ;
			
			renderer.modelBipedMain.bipedRightLeg.rotationPointX = baseModel.bipedPelvic.rotationPointX + baseModel.bipedRightLeg.rotationPointX;
			renderer.modelBipedMain.bipedRightLeg.rotationPointY = baseModel.bipedPelvic.rotationPointY + baseModel.bipedRightLeg.rotationPointY;
			renderer.modelBipedMain.bipedRightLeg.rotationPointZ = baseModel.bipedPelvic.rotationPointZ + baseModel.bipedRightLeg.rotationPointZ;
			
			renderer.modelBipedMain.bipedRightArm.rotationPointX = baseModel.bipedRightShoulder.rotationPointX + baseModel.bipedRightArm.rotationPointX;
			renderer.modelBipedMain.bipedRightArm.rotationPointY = baseModel.bipedRightShoulder.rotationPointY + baseModel.bipedRightArm.rotationPointY;
			renderer.modelBipedMain.bipedRightArm.rotationPointZ = baseModel.bipedRightShoulder.rotationPointZ + baseModel.bipedRightArm.rotationPointZ;
			
			renderer.modelBipedMain.bipedLeftArm.rotationPointX = baseModel.bipedLeftShoulder.rotationPointX + baseModel.bipedLeftArm.rotationPointX;
			renderer.modelBipedMain.bipedLeftArm.rotationPointY = baseModel.bipedLeftShoulder.rotationPointY + baseModel.bipedLeftArm.rotationPointY;
			renderer.modelBipedMain.bipedLeftArm.rotationPointZ = baseModel.bipedLeftShoulder.rotationPointZ + baseModel.bipedLeftArm.rotationPointZ;
		}*/
		return;
	}
	
}
