package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;
import com.dunk.tfc.Entities.Mobs.EntitySheepTFC;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Entities.IAnimal.GenderEnum;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSheep2TFC extends ModelSheep2
{
	int hornSegments = 22;
	/*Placing rightEar at 0, 0
	Placing leftBackToe at 7, 0
	Placing leftToe at 0, 5
	Placing tail at 16, 0
	Placing head at 4, 5
	Placing horn at 25, 0
	Placing leftForeArm at 0, 13
	Placing leftWrist at 19, 7
	Placing leftHead at 9, 13
	Placing hornCentre at 38, 0
	Placing leftFoot at 28, 7
	Placing leftShoulder at 0, 23
	Placing leftUpperArm at 37, 7
	Placing leftCalf at 53, 0
	Placing jaw at 15, 18
	Placing snout at 66, 0
	Placing muzzle at 0, 33
	Placing upperMouth at 44, 11
	Placing neck at 77, 1
	Placing leftThigh at 21, 31
	Placing wildWool at 39, 26
	Placing butt at 0, 47
	Placing body at 104, 0
	Placing chest at 70, 18*/
	
	private ModelRenderer chest;
	private ModelRenderer bodyWool, chestWool, buttWool, leftThighWool, rightThighWool, neckWool, leftShoulderWool,
			rightShoulderWool, leftArmWool, rightArmWool, wildWool;
	private ModelRenderer base;
	private ModelRenderer base2;

	private ModelRenderer leftShoulder;
	private ModelRenderer leftUpperArm;
	private ModelRenderer leftForeArm;
	private ModelRenderer leftWrist;
	private ModelRenderer leftToe;

	private ModelRenderer rightShoulder;
	private ModelRenderer rightUpperArm;
	private ModelRenderer rightForeArm;
	private ModelRenderer rightWrist;
	private ModelRenderer rightToe;

	private ModelRenderer leftThigh;
	private ModelRenderer leftCalf;
	private ModelRenderer leftFoot;
	private ModelRenderer leftBackToe;

	private ModelRenderer rightThigh;
	private ModelRenderer rightCalf;
	private ModelRenderer rightFoot;
	private ModelRenderer rightBackToe;

	private ModelRenderer leftHead;
	private ModelRenderer rightHead;

	private ModelRenderer upperMouth;
	private ModelRenderer butt;
	private ModelRenderer tail;
	private ModelRenderer neck;
	private ModelRenderer beard;
	private ModelRenderer rightEar;
	private ModelRenderer leftEar;
	private ModelRenderer snout;
	private ModelRenderer muzzle;
	private ModelRenderer jaw;
	private ModelRendererHornPiece[] leftHorn;
	private ModelRendererHornPiece[] rightHorn;
	private ModelRenderer hornCentre;

	private ModelRendererHornPiece leftHornToRender;
	private ModelRendererHornPiece rightHornToRender;

	public ModelSheep2TFC()
	{
		super();
		this.textureWidth = 128;
		this.textureHeight = 64;
		float par1 = 0;

		this.rightEar = new ModelRenderer(this,0, 0);
		this.leftEar = new ModelRenderer(this,0, 0);
		this.leftBackToe = new ModelRenderer(this,6, 0);
		this.leftToe = new ModelRenderer(this,0, 4);
		this.rightBackToe = new ModelRenderer(this,6, 0);
		this.rightToe = new ModelRenderer(this,0, 4);
		this.tail = new ModelRenderer(this,14, 0);
		this.head = new ModelRendererAnimalHead(this,3, 4);
		this.leftForeArm = new ModelRenderer(this,0, 11);
		this.leftWrist = new ModelRenderer(this,17, 6);
		this.leftHead = new ModelRenderer(this,8, 11);
		this.rightForeArm = new ModelRenderer(this,0, 11);
		this.rightWrist = new ModelRenderer(this,17, 6);
		this.rightHead = new ModelRenderer(this,8, 11);
		this.hornCentre = new ModelRenderer(this,34, 0);
		this.leftFoot = new ModelRenderer(this,25, 6);
		this.leftShoulder = new ModelRenderer(this,0, 20);
		this.leftUpperArm = new ModelRenderer(this,33, 6);
		this.leftCalf = new ModelRenderer(this,48, 0);
		this.rightFoot = new ModelRenderer(this,25, 6);
		this.rightShoulder = new ModelRenderer(this,0, 20);
		this.rightUpperArm = new ModelRenderer(this,33, 6);
		this.rightCalf = new ModelRenderer(this,48, 0);
		this.jaw = new ModelRendererAnimalHead(this,4, 20);
		this.snout = new ModelRendererAnimalHead(this,22, 16);
		this.muzzle = new ModelRendererAnimalHead(this,60, 0);
		this.upperMouth = new ModelRendererAnimalHead(this,42, 10);
		this.neck = new ModelRenderer(this,0, 32);
		this.leftThigh = new ModelRenderer(this,25, 26);
		this.rightThigh = new ModelRenderer(this,25, 26);
		this.wildWool = new ModelRenderer(this,80, 0);
		this.butt = new ModelRenderer(this,68, 11);
		this.body = new ModelRendererAnimalHead(this,49, 25);
		this.chest = new ModelRendererAnimalHead(this,16, 41);
		
		/*
		 * Placing rightEar at 0, 0 Placing leftBackToe at 7, 0 Placing leftToe
		 * at 0, 5 Placing tail at 16, 0 Placing head at 4, 5 Placing horn at
		 * 25, 0 Placing leftForeArm at 0, 13 Placing leftWrist at 19, 7 Placing
		 * hornCentre at 38, 0 Placing leftFoot at 9, 13 Placing leftShoulder at
		 * 28, 7 Placing leftUpperArm at 0, 24 Placing leftCalf at 18, 16
		 * Placing jaw at 43, 0 Placing snout at 38, 13 Placing muzzle at 15, 27
		 * Placing upperMouth at 72, 0 Placing neck at 49, 14 Placing leftThigh
		 * at 0, 38 Placing wildWool at 31, 33 Placing butt at 99, 0 Placing
		 * body at 15, 45 Placing chest at 76, 15
		 */

		//this.head = new ModelRendererAnimalHead(this, 3, 4);
		this.head.addBox(-1F, -1F, -2.5F, 2, 2, 5, par1);
		this.head.setRotationPoint(0.0F, (float) (10 - par1), -2.0F);

		//this.leftHead = new ModelRenderer(this, 9, 13);
		//this.rightHead = new ModelRenderer(this, 9, 13);
		this.leftHead.mirror = true;
		this.leftHead.addBox(-0.5f, 0, 0, 1, 5, 4, 0.51f);
		
		this.rightHead.addBox(-0.5f, 0, 0, 1, 5, 4, 0.51f);

		//this.hornCentre = new ModelRenderer(this, 38, 0);
		this.hornCentre.addBox(-2, 0, 0, 4, 3, 3);
		this.head.addChild(hornCentre);

		//this.butt = new ModelRenderer(this, 0, 47);
		this.butt.addBox(-4, 0, 0, 8, 7, 7);
		this.buttWool = new ModelRenderer(this, 84, 43);
		this.buttWool.addBox(-4, 0, 0, 8, 7, 7, 1.5f);
		this.butt.addChild(buttWool);

		//this.tail = new ModelRenderer(this, 14, 0);
		this.tail.addBox(-1.5f, 0, -1, 3, 4, 1);
		this.butt.addChild(tail);

		//upperMouth = new ModelRendererAnimalHead(this, 44, 11);
		upperMouth.addBox(-2.5f, 0, 0, 5, 4, 8, -0.95f);

		//this.jaw = new ModelRendererAnimalHead(this, 15, 18);
		jaw.addBox(-2f, 0, -10, 4, 2, 10, -0.49f);
		this.head.addChild(jaw);

		this.head.addChild(upperMouth);

		this.head.addChild(leftHead);
		this.head.addChild(rightHead);

		//this.snout = new ModelRendererAnimalHead(this, 66, 0);
		this.snout.addBox(-2.5f, 0, 0, 5, 5, 5, -0.9f);
		this.head.addChild(snout);

		//this.muzzle = new ModelRendererAnimalHead(this, 0, 33);
		this.muzzle.addBox(-2.5f, 0, -4, 5, 5, 5, -1f);
		this.snout.addChild(muzzle);

		//this.rightEar = new ModelRenderer(this, 0, 0);
		//this.leftEar = new ModelRenderer(this, 0, 0);
		this.rightEar.addBox(-1f, -3, 0.5f, 2, 3, 1);
		this.leftEar.addBox(-1f, -3, 0.5f, 2, 3, 1);
		this.leftEar.mirror = true;
		this.head.addChild(leftEar);
		this.head.addChild(rightEar);

		//this.body = new ModelRendererAnimalHead(this, 104, 0);
		this.body.addBox(-5.0F, -8.0F, -7.0F, 10, 8, 10);
		this.bodyWool = new ModelRenderer(this, 84, 43);
		this.bodyWool.addBox(-5.0F, -8.0F, -7.0F, 10, 8, 10, 2f);
		this.body.addChild(bodyWool);
		//this.wildWool = new ModelRenderer(this, 39, 26);
		this.wildWool.addBox(-5, -6f, -2, 10, 6, 5, 0.2f);
		body.addChild(wildWool);

		//this.chest = new ModelRendererAnimalHead(this, 70, 18);
		this.chest.addBox(-5.0F, -10.0F, -7.0F, 10, 12, 10, -0.25f);
		this.chestWool = new ModelRendererAnimalHead(this, 84, 43);
		this.chestWool.addBox(-4.5F, -10.0F, -7.0F, 9, 11, 9, 1.25f);
		this.chest.addChild(chestWool);
		this.body.addChild(chest);

		//this.neck = new ModelRenderer(this, 77, 1);
		neck.addBox(-1.5f, -3, -10, 3, 6, 10);
		this.neckWool = new ModelRenderer(this, 84, 43);
		this.neckWool.addBox(-2f, -3, -8, 4, 6, 8, 1f);
		this.neck.addChild(neckWool);

	/*	horn1 = new ModelRenderer(this, 28, 2);
		horn1.addBox(0F, 0F, 0F, 2, 4, 2, 0F);
		horn1.setRotationPoint(0F, -10F, 0F);
		horn1.rotateAngleZ = (float) -Math.PI / 6;
		horn1.rotateAngleX = (float) -Math.PI / 6;
		horn1.rotateAngleY = (float) -Math.PI / 3;
		horn1.setRotationPoint(-5F, -6F, -1F);

		horn1b = new ModelRenderer(this, 38, 4);
		horn1b.addBox(0.5F, 1F, 0.5F, 1, 3, 1, 0.25F);
		horn1b.setRotationPoint(0F, -2F, 4F);
		horn1b.rotateAngleX = (float) -Math.PI / 3;

		horn2 = new ModelRenderer(this, 28, 2);
		horn2.addBox(0F, 0F, 0F, 2, 4, 2, 0F);
		horn2.setRotationPoint(0F, -10F, 0F);
		horn2.rotateAngleZ = (float) Math.PI / 6;
		horn2.rotateAngleX = (float) -Math.PI / 6;
		horn2.rotateAngleY = (float) Math.PI / 3;
		horn2.setRotationPoint(4F, -6.5F, 0.75F);

		horn2b = new ModelRenderer(this, 38, 4);
		horn2b.addBox(0.5F, 1F, 0.5F, 1, 3, 1, 0.25F);
		horn2b.setRotationPoint(0F, -2F, 4F);
		horn2b.rotateAngleX = (float) -Math.PI / 3;*/

		//leftThigh = new ModelRenderer(this, 21, 31);
		leftThigh.mirror = true;
		leftThigh.addBox(0F, -2F, -2F, 5, 8, 7);
		leftThigh.setRotationPoint(-3, 2, 8);
		leftThighWool = new ModelRenderer(this, 84, 43);
		leftThighWool.addBox(0F, -2F, -2F, 5, 8, 7, 1f);
		leftThigh.addChild(leftThighWool);

		//leftCalf = new ModelRenderer(this, 53, 0);
		leftCalf.mirror  =true;
		leftCalf.addBox(0f, 0f, 0f, 3, 7, 3);
		leftCalf.setRotationPoint(-0.01f, 8, 0);
		leftThigh.addChild(leftCalf);

		//leftFoot = new ModelRenderer(this, 28, 7);
		leftFoot.mirror  =true;
		leftFoot.addBox(0f, -1f, 0f, 2, 8, 2);
		leftFoot.setRotationPoint(0.01f, 4, 0);
		leftCalf.addChild(leftFoot);

		//leftBackToe = new ModelRenderer(this, 6, 0);
		leftBackToe.addBox(0, 0, 0, 2, 2, 2);
		leftBackToe.setRotationPoint(0, 4, 0);
		leftFoot.addChild(leftBackToe);

		//rightThigh = new ModelRenderer(this, 21, 31);
		rightThigh.addBox(0F, -2F, -2F, 5, 8, 7);
		rightThigh.setRotationPoint(3, 2, 8);
		rightThighWool = new ModelRenderer(this, 84, 43);
		rightThighWool.addBox(0F, -2F, -2F, 5, 8, 7, 1f);
		rightThigh.addChild(rightThighWool);

		//rightCalf = new ModelRenderer(this, 53, 0);
		rightCalf.addBox(0f, 0f, 0f, 3, 7, 3);
		rightCalf.setRotationPoint(0.01f, 8, 0);
		rightThigh.addChild(rightCalf);

		//rightFoot = new ModelRenderer(this, 28, 7);
		rightFoot.addBox(0f, -1f, 0f, 2, 8, 2);
		rightFoot.setRotationPoint(-0.01f, 4, 0);
		rightCalf.addChild(rightFoot);

		//rightBackToe = new ModelRenderer(this, 6, 0);
		rightBackToe.addBox(0, 0, 0, 2, 2, 2);
		rightBackToe.setRotationPoint(0, 4, 0);
		rightFoot.addChild(rightBackToe);
		rightFoot.mirror = true;

		//leftShoulder = new ModelRenderer(this, 0, 23);
		leftShoulder.mirror  =true;
		leftShoulder.addBox(0F, 0F, 0F, 3, 5, 4);
		leftShoulder.setRotationPoint(-3, 2, -3);
		leftShoulderWool = new ModelRenderer(this, 84, 43);
		leftShoulderWool.addBox(0.5F, 0F, 0F, 3, 5, 4, 1f);
		leftShoulder.addChild(leftShoulderWool);

		//leftUpperArm = new ModelRenderer(this, 37, 7);
		leftUpperArm.mirror  =true;
		leftUpperArm.addBox(0F, -1F, 0F, 3, 5, 4);
		leftShoulder.addChild(leftUpperArm);
		leftArmWool = new ModelRenderer(this, 84, 43);
		leftArmWool.addBox(0.5F, -1F, 0F, 3, 5, 4, 1f);
		leftUpperArm.addChild(leftArmWool);

		//leftForeArm = new ModelRenderer(this, 0, 11);
		leftForeArm.mirror = true;
		leftForeArm.addBox(0F, 0F, 0F, 2, 6, 2);
		leftUpperArm.addChild(leftForeArm);

		//leftWrist = new ModelRenderer(this, 19, 7);
		leftWrist.mirror = true;
		leftWrist.addBox(0f, 0f, 0f, 2, 6, 2);
		leftForeArm.addChild(leftWrist);

		//leftToe = new ModelRenderer(this, 0,4);
		leftToe.addBox(0, 0, 0, 2, 2, 2);
		leftWrist.addChild(leftToe);

		//rightShoulder = new ModelRenderer(this, 0, 20);
		rightShoulder.addBox(0F, 0F, 0F, 3, 5, 4);
		rightShoulder.setRotationPoint(3, 2, -3);
		rightShoulderWool = new ModelRenderer(this, 84, 43);
		rightShoulderWool.addBox(-0.5F, 0F, 0F, 3, 5, 4, 1f);
		rightShoulder.addChild(rightShoulderWool);

		//rightUpperArm = new ModelRenderer(this, 37, 7);
		rightUpperArm.addBox(0F, -1F, 0F, 3, 5, 4);
		rightShoulder.addChild(rightUpperArm);
		rightUpperArm.mirror = true;
		rightArmWool = new ModelRenderer(this, 84, 43);
		rightArmWool.addBox(-0.5F, -1F, 0F, 3, 5, 4, 1f);
		rightArmWool.mirror = true;
		rightUpperArm.addChild(rightArmWool);

		//rightForeArm = new ModelRenderer(this, 0, 11);
		rightForeArm.addBox(0F, 0F, 0F, 2, 6, 2);
		rightUpperArm.addChild(rightForeArm);
		rightForeArm.mirror = true;

		//rightWrist = new ModelRenderer(this, 17, 6);
		rightWrist.addBox(0f, 0f, 0f, 2, 6, 2);
		rightForeArm.addChild(rightWrist);
		rightWrist.mirror = true;

		//rightToe = new ModelRenderer(this, 0,4);
		rightToe.addBox(0, 0, 0, 2, 2, 2);
		rightWrist.addChild(rightToe);
		rightToe.mirror = true;

		setUpHorns(2.5f, 3f, hornSegments, true);

		base = new ModelRenderer(this, 0, 0);
		base2 = new ModelRenderer(this, 0, 0);
		base.addChild(base2);
		base2.offsetY = -1f;
		base2.addChild(body);
		base2.addChild(leftShoulder);
		base2.addChild(rightShoulder);
		neck.addChild(head);
		base2.addChild(leftThigh);
		base2.addChild(rightThigh);
		base2.addChild(butt);
		// base2.addChild(belly);
		// base2.addChild(butt);
		base2.addChild(neck);

		// horn1.addChild(horn1b);
		// horn2.addChild(horn2b);

		// head.addChild(horn1);
		// head.addChild(horn2);
	}

	private void setUpHorns(float curveRadius, float xStretch, int numSections, boolean init)
	{
		if (init)
		{
			if (hornCentre != null && hornCentre.childModels != null)
			{
				hornCentre.childModels.clear();
			}
			rightHorn = new ModelRendererHornPiece[numSections];
			leftHorn = new ModelRendererHornPiece[numSections];

			rightHornToRender = new ModelRendererHornPiece(this, 0, 0);
			leftHornToRender = new ModelRendererHornPiece(this, 0, 0);

		}

		int hornHeight = 3;
		float curvature = 0.225f;
		float offset = (hornHeight * 0.25f) / (float) numSections;
		float offset2 = (hornHeight * 0.34f) / (float) numSections;
		for (int i = 0; i < numSections; i++)
		{
			if (init)
			{
				rightHorn[i] = new ModelRendererHornPiece(this, 22, 0);
				rightHorn[i].addBox(-1.5f, 0f, 0F, 3, hornHeight, 3, -0.25f - offset2 * (float) i);
				leftHorn[i] = new ModelRendererHornPiece(this, 22, 0);
				leftHorn[i].addBox(-1.5f, 0f, 0F, 3, hornHeight, 3, -0.25f - offset2 * (float) i);
			}
			rightHorn[i].setRotationPoint(0,
					(hornHeight - (60f * offset * (float) (i * i) / (numSections * numSections))) * 0.3f, 0.25f);
			leftHorn[i].setRotationPoint(0,
					(hornHeight - (60f * offset * (float) (i * i) / (numSections * numSections))) * 0.3f, 0.25f);
			// The angle should be based on the radius and the height of each
			// section.
			//
			rightHorn[i].rotateAngleX = -curvature * 1.32f * (Math.max(0,
					(hornHeight - (2f * offset * (float) i)))) / curveRadius;
			rightHorn[i].rotateAngleY = 0.05f * curvature * ((float) i / (float) numSections);
			rightHorn[i].rotateAngleZ = 0.75f * curvature * (float) Math
					.sqrt(((float) Math.max(i - 3, 0) / (float) numSections));

			leftHorn[i].rotateAngleX = rightHorn[i].rotateAngleX;// -curvature*((hornHeight
			// - 2f*
			// offset*(float)i))
			// /
			// curveRadius;
			leftHorn[i].rotateAngleY = -0.05f * curvature * ((float) i / (float) numSections);
			leftHorn[i].rotateAngleZ = -rightHorn[i].rotateAngleZ;// -1f
																	// *
			// curvature
			// * (float)
			// Math.sqrt(((float)Math.max(i-4,0)
			// /
			// (float)numSections))
			// ;

		}
		for (int i = numSections - 1; i > 0; i--)
		{
			if (init)
			{
				// this.rightHorn[i - 1].addChild(rightHorn[i]);
				this.rightHorn[i - 1].hornSources = new ModelRendererHornPiece[] { rightHorn[i] };
				this.rightHorn[i - 1].originalPiece = 0;
				// this.leftHorn[i - 1].addChild(leftHorn[i]);
				this.leftHorn[i - 1].hornSources = new ModelRendererHornPiece[] { leftHorn[i] };
				this.leftHorn[i - 1].originalPiece = 0;
			}
			/*
			 * else if (init) { this.rightHornToRender[i -
			 * 1].addChild(rightHornToRender[i]); this.leftHornToRender[i -
			 * 1].addChild(leftHornToRender[i]); }
			 */
		}
		rightHornToRender.hornSources = rightHorn;
		rightHornToRender.originalPiece = 0;
		leftHornToRender.hornSources = leftHorn;
		leftHornToRender.originalPiece = 0;
		if (init)
		{
			this.hornCentre.addChild(rightHornToRender);
			this.hornCentre.addChild(leftHornToRender);
		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
	{

		EntitySheepTFC sheep = (EntitySheepTFC) entity;

		boolean noHorns = sheep.getSex() == 1;
		setUpHorns(2.75f, 3f, hornSegments, false);
		this.snout.setRotationPoint(0, 0.75f, -6.3f);
		this.snout.rotateAngleX = 0.5f;
		float growth = TFC_Core.getPercentGrownSheep(sheep);
		int hidden = (int) (growth > 0.5f && !noHorns
				? (hornSegments - 1) - (((growth - 0.5f) / 1.5f) * (float) (hornSegments - 1)) : hornSegments);
		if (hidden == hornSegments)
		{
			noHorns = true;
		}

		this.leftHead.setRotationPoint(1.45f, -0.31f, -2);
		this.rightHead.setRotationPoint(-1.45f, -0.31f, -2);
		this.leftHead.rotateAngleZ = 0.2f;
		this.rightHead.rotateAngleZ = -0.2f;
		
		
		this.hornCentre.setRotationPoint(0, -2, -2);

		this.muzzle.setRotationPoint(0, -0.1f, 0.75f);
		this.muzzle.rotateAngleX = 0.2f;

		this.upperMouth.setRotationPoint(0, 2.75f, -8f);
		this.upperMouth.rotateAngleX = 0.2f;

		this.leftEar.rotateAngleY = 0.5f;
		this.leftEar.rotateAngleZ = -1.5f;
		this.leftEar.rotateAngleX = -0.5f;
		this.rightEar.rotateAngleY = -0.5f;
		this.rightEar.rotateAngleZ = 1.5f;
		this.rightEar.rotateAngleX = -0.5f;
		this.leftEar.setRotationPoint(-2.5f, 0.5f, 1);
		this.rightEar.setRotationPoint(2.5f, 0.5f, 1);

		rightHornToRender.isHidden = noHorns;
		leftHornToRender.isHidden = noHorns;
		hornCentre.isHidden = noHorns;

		rightHornToRender.setRotationPoint(-1.75f + ((float) hidden / 5f), 2 - 1 - ((float) hidden / 12f) + 1.5f,
				3 + 1f - ((float) hidden / 12f));
		leftHornToRender.setRotationPoint(1.75f - ((float) hidden / 5f), 2 - 1 - ((float) hidden / 12f) + 1.5f,
				3 + 1f - ((float) hidden / 12f));

		rightHornToRender.rotateAngleX = (float) (Math.PI * 0.5f + 1.1f) + 0.5f;
		rightHornToRender.rotateAngleY = -0.65f + ((float) hidden / 10f);
		rightHornToRender.rotateAngleZ = -0.475f;
		leftHornToRender.rotateAngleX = (float) (Math.PI * 0.5f + 1.1f) + 0.5f;
		leftHornToRender.rotateAngleY = 0.65f - ((float) hidden / 10f);
		leftHornToRender.rotateAngleZ = 0.475f;
		rightHornToRender.pieceOffset = hidden;
		leftHornToRender.pieceOffset = hidden;

		this.jaw.setRotationPoint(0, 3.5f, 3.1f);
		this.jaw.rotateAngleX = 0.15f;
		base.rotateAngleX = 0;
		/*
		 * for(int i = 0; i < 18; i++) {
		 * 
		 * 
		 * if(i >= 18 - hidden) { rightHornToRender[i].isHidden = true;
		 * leftHornToRender[i].isHidden = true; } else {
		 * rightHornToRender[i].isHidden = false; leftHornToRender[i].isHidden =
		 * false; } }
		 */

		float offset = (float) Math.PI;
		float backOffset = -(float) Math.PI * 0.1f;
		float sleepOffset = 0;
		float thighOffset = 0;

		float sleeping = Math.max(((EntitySheepTFC) entity).getSleepTimer(), 0) * 1.25f;
		sleeping *= 2f;
		boolean gettingUp = sleeping > 0;
		par5 += (Math.min(sleeping*4f,360f)) * 0.15f;
		if (sleeping > 0)
		{
			par5 = Math.max(par5, -30);
		}
		if (gettingUp)
		{
			par1 %= (Math.PI * 2f / 0.6662F);
			if (par1 > (Math.PI * 1f / 0.6662F))
			{
				par1 += ((Math.PI * 2f / 0.6662F) - par1) * (Math.min(sleeping, 20) / 20f);
			}
			else
			{
				par1 *= (Math.max(20 - sleeping, 0) / 20f);
			}
			par2 *= (Math.max(20 - sleeping, 0) / 20f);
			par1 %= (Math.PI * 2f / 0.6662F);
			// if(par1 % (Math.PI * 2f / 0.6662F) ==0)
			if (par1 % (Math.PI * 2f / 0.6662F) > -0.01f && par1 % (Math.PI * 2f / 0.6662F) < 0.01f)
			{

				par1 = Math.max((float) (Math.PI * 2f / 0.6662F) - (float) Math.pow(sleeping * 0.1f, 1.5f),
						(float) (Math.PI * 0.4f / 0.6662F));
				/*
				 * par1 = (par1 * Math.max(20 - sleeping, 0) / 20f) +
				 * ((Math.min(sleeping, 20) / 20f) * ((6f) - (((float) Math
				 * .pow((float) Math.min(sleeping, 40), 2) / 40f) * 0.1f) * (1f
				 * - par6)));
				 */
				// par1 = sleeping - par1;
				par2 = 1f * (Math.min(sleeping, 10) / 10f);
				;
				offset *= Math.max(20 - sleeping, 0) / 20f;
				backOffset *= Math.max(20 - sleeping, 0) / 20f;
				// sleepOffset = 0; //+ (sleeping > 20 ? (float) Math.PI * 0.75f
				// * (sleeping - 20f) / 140f : 0f);
			}
			thighOffset = -Math.min(sleeping, 10) / 10f;
		}
		// par1 = (((float)TFC_Time.getTotalTicks()%1000)*0.75f) * (1f-par6);
		float deltaX = (float) (entity.prevPosX - entity.posX);
		float deltaZ = (float) (entity.prevPosZ - entity.posZ);
		float speed = (float) Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		this.neck.setRotationPoint(0, 5, -5);
		this.neck.rotateAngleX = ((par5 * 1.5f) / (180F / (float) Math.PI)) - 0.5f;
		boolean running = sheep.attackTime != 0;
		if (entity.isInWater())
		{

			par1 = (((float) TFC_Time.getTotalTicks() % 10000) * 0.5f) * (1f - par6);
			// System.out.println(par1);
			par2 = 0.5f;// 1.25f;
			base.rotateAngleX = 0f;// -0.05f;
			// neck.rotateAngleX = -0.45f;
		}
		else
		{
			par2 = 0.5f;
			par1 *= 2f;
			par2 *= 1.5f;
		}
		// par2 = 0.75f;

		// neck.setRotationPoint(0f, 7f, -6.5f);
		// mane.setRotationPoint(0, 0, 0);
		// mane1.setRotationPoint(-0.49f, 0, -5);
		leftShoulder.setRotationPoint(2.75f, 4, -7);

		leftUpperArm.setRotationPoint(0.1f, 6, 0);
		leftForeArm.setRotationPoint(0.9f, 2.5f, 1.5f);
		leftWrist.setRotationPoint(-0.1f, 6, 0);
		leftToe.setRotationPoint(0.1f, 5.5f, -0.5F);

		rightShoulder.setRotationPoint(-5.75f, 4, -7);
		rightUpperArm.setRotationPoint(-0.1f, 6, 0);
		rightForeArm.setRotationPoint(0.1f, 2.5f, 1.5f);
		rightWrist.setRotationPoint(0.1f, 6, 0);
		rightToe.setRotationPoint(-0.1f, 5.5f, -0.5F);

		this.head.setRotationPoint(0F, (float) (-1), -12F);
		this.head.rotateAngleX = (-Math.abs(par5) / (180F / (float) Math.PI)) + 1;
		this.neck.rotateAngleY = ((par4 / (180F / (float) Math.PI)) * 0.5f);

		this.butt.setRotationPoint(0, 1.5f, 10f);
		this.butt.rotateAngleX = -0.25f;

		this.chest.setRotationPoint(0, 8.25f, 2f);
		this.chest.rotateAngleX = 0.2f;

		this.tail.setRotationPoint(0, 0, 6);
		this.tail.rotateAngleX = 1f;

		wildWool.setRotationPoint(0, 0, 0);
		if (running)
		{
			this.neck.rotateAngleX += 1f;
			offset *= 0.25f;
			par2 *= 2.5f;
			backOffset = (float) Math.PI;
			base.rotateAngleX = (float) (MathHelper.sin(par1 * 0.6662F + backOffset + offset) * 0.05F * par2);
		}
		else
		{

		}
		/*
		 * tusk1.setRotationPoint(-1.25f, 2f, -7.5f); tusk1.rotateAngleZ =
		 * (float) (Math.PI * 0.4f); tusk1.rotateAngleX = (float) Math.PI / 7;
		 * 
		 * tusk2.setRotationPoint(1.25f, 2f, -7.5f); tusk2.rotateAngleZ =
		 * -(float) (Math.PI * 0.4f); tusk2.rotateAngleX = (float) Math.PI / 7;
		 * tusk2.rotateAngleY = (float) Math.PI * -0.1f;
		 * 
		 * jawTusk1a.setRotationPoint(0.5f, 0.8f, -8.5f);
		 * jawTusk2a.setRotationPoint(2.5f, 0.8f, -8.5f);
		 * 
		 * jawTusk1b.setRotationPoint(0, 1.7f, 0); jawTusk1b.rotateAngleX =
		 * (float) (Math.PI * 0.4f); jawTusk1b.rotateAngleY = (float) (Math.PI *
		 * 0.1f); jawTusk1b.rotateAngleZ = -(float) (Math.PI * 0.05f);
		 * 
		 * jawTusk2b.setRotationPoint(0, 1.7f, 0); jawTusk2b.rotateAngleX =
		 * (float) (Math.PI * 0.4f); jawTusk2b.rotateAngleY = (float) -(Math.PI
		 * * 0.1f); jawTusk2b.rotateAngleZ = (float) (Math.PI * 0.05f);
		 * 
		 * jawTusk2a.rotateAngleX = (float) (Math.PI * 0.4f);
		 * jawTusk2a.rotateAngleY = (float) (Math.PI * 0.4f) + 0.4f;
		 * jawTusk2a.rotateAngleZ = -(float) (Math.PI * 0.1f);
		 * 
		 * jawTusk1a.rotateAngleX = (float) (Math.PI * 0.4f);
		 * jawTusk1a.rotateAngleY = -(float) (Math.PI * 0.4f) - 0.4f;
		 * jawTusk1a.rotateAngleZ = (float) (Math.PI * 0.1f);
		 * 
		 * // jawTusk2a.rotateAngleZ =-(float)(Math.PI * 0.6f);
		 * mane.rotateAngleX = -((float) Math.PI * 0.4f);
		 * mane.setRotationPoint(-1, -4, -1); mane2.setRotationPoint(-0.5f, 0,
		 * 6); mane2.rotateAngleX = ((float) Math.PI * 0.55f);
		 */

		/*
		 * jaw.setRotationPoint(-2f, 1f, -3f); jaw.rotateAngleX = 0.0f - (float)
		 * (Math.PI * 0.2f)
		 */
		/*
		 * + Math . max ( 0, 0 . 5f * MathHelper . sin ( par1 * 0 . 06662f ) )
		 */;

		float percent = TFC_Core.getPercentGrown((IAnimal) entity);
		// percent = (TFC_Time.getTotalTicks()%1000)*0.003f;
		// percent = Math.min(1f, percent);
		/*
		 * leftEar.setRotationPoint(1, -2, -1); leftEar.rotateAngleX = (float)
		 * (Math.PI * 0.55f); leftEar.rotateAngleY = 0; leftEar.rotateAngleY =
		 * (float) (Math.PI * 0.25f) * (1f - percent); ; leftEar.rotateAngleZ =
		 * (float) (Math.PI * 0.35f);
		 * 
		 * rightEar.setRotationPoint(-2, -2, -1); rightEar.rotateAngleX =
		 * (float) (Math.PI * 0.55f); rightEar.rotateAngleY = 0;
		 * rightEar.rotateAngleY = -(float) (Math.PI * 0.25f) * (1f - percent);
		 * rightEar.rotateAngleZ = -(float) (Math.PI * 0.35f);
		 * 
		 * tail.rotateAngleX = -(float) (Math.PI * 0.2f);
		 * tail.setRotationPoint(2, 7.5f, 1);
		 */
		leftShoulder.rotateAngleX = -0.05f;
		leftUpperArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + sleepOffset) * 0.5F * par2) + 0.55f;
		leftForeArm.rotateAngleX = -MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 > 0 ? -0.45f
				: -MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 * 1.5f - 0.5f;
		leftWrist.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 > 0 ? -0f
				: -MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 * 1.5f - 0f);
		leftToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 < 0 ? -0.75f
				: -MathHelper.sin(par1 * 0.6662F + sleepOffset) * 0.75f * par2 - 0.25f);

		rightShoulder.rotateAngleX = -0.05f;
		rightUpperArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + offset) * 0.5F * par2) + 0.55f;
		rightForeArm.rotateAngleX = -MathHelper.sin(par1 * 0.6662F + offset) * par2 > 0 ? -0.45f
				: -MathHelper.sin(par1 * 0.6662F + offset) * par2 * 1.5f - 0.45f;
		rightWrist.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset) * par2 > 0 ? -0f
				: -MathHelper.sin(par1 * 0.6662F + offset) * par2 * 1.5f - 0f);
		rightToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset) * par2 < 0 ? -0.75f
				: -MathHelper.sin(par1 * 0.6662F + offset) * 0.75f * par2 - 0.25f);

		// float thighOffset = -0.75f *
		// ((float)((Math.min(40,sleeping)-40)*2f)/40f);

		leftThigh.setRotationPoint(0.05f, 6.5f, 12);
		leftThigh.rotateAngleX = (MathHelper
				.cos((par1 * 0.6662F) + offset + backOffset + thighOffset) * 0.45F * par2) - 0.25f;
		leftCalf.setRotationPoint(2f - 0.01f, 6, 0);
		leftCalf.rotateAngleX = -MathHelper.sin((par1 * 0.6662F) + offset + backOffset + sleepOffset) * par2 > 0 ? 0.5f
				: MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * 0.75f * par2 + 0.5f;
		leftFoot.setRotationPoint(0.01f, 5, 0.5f);
		leftFoot.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * par2 > 0
				? +0.25f : MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * 1.5f * par2 + 0.25f);
		leftBackToe.setRotationPoint(-0.01f, 6.5f, -0.5f);
		leftBackToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * par2 < 0
				? -0.75f : -MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * 0.75f * par2 - 0.25f);

		rightThigh.setRotationPoint(-5.05f, 6.5f, 12);
		rightThigh.rotateAngleX = (MathHelper.cos((par1 * 0.6662F) + backOffset + thighOffset) * 0.45F * par2) - 0.25f;
		rightCalf.setRotationPoint(0f + 0.01f, 6, 0);
		rightCalf.rotateAngleX = -MathHelper.sin((par1 * 0.6662F) + backOffset) * par2 > 0 ? 0.5f
				: MathHelper.sin(par1 * 0.6662F + backOffset) * 0.75f * par2 + 0.5f;
		rightFoot.setRotationPoint(-0.01f, 5, 0.5f);
		rightFoot.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + backOffset) * par2 > 0 ? +0.25f
				: MathHelper.sin(par1 * 0.6662F + backOffset) * 1.5f * par2 + 0.25f);
		rightBackToe.setRotationPoint(0.01f, 6.5f, -0.5f);
		rightBackToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + backOffset) * par2 < 0 ? -0.75f
				: -MathHelper.sin(par1 * 0.6662F + backOffset) * 0.75f * par2 - 0.25f);

		/*
		 * belly.setRotationPoint(-2, 14.5f, -2);
		 * 
		 * butt.setRotationPoint(-2.5f, 7.5f, 7f); butt.rotateAngleX = (float)
		 * (Math.PI * 0.3f);
		 */

		// leftToe.rotateAngleX = -(leftWrist.rotateAngleX +
		// leftForeArm.rotateAngleX + leftUpperArm.rotateAngleX
		// +leftShoulder.rotateAngleX);
		// leftToe.rotateAngleX = 0.5f;

		// this.neck.rotateAngleY = this.head.rotateAngleY;

		this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;

		float baseDis = par1 * 2f;
		base.setRotationPoint(0, (!gettingUp ? (MathHelper.cos(baseDis * 0.6662F) * 0.5F * par2) : 0) + 15f, 0);
		// We simulate where the left wrist is, so we can calculate how to
		// adjust the body.

		// base.rotateAngleX = (float) + (MathHelper.cos(par1 * 0.6662F) * 0.05F
		// * par2);
		head.rotateAngleZ = 0;
		// neck.rotateAngleZ = 0f;
		head.rotateAngleY = 0;
		leftThigh.rotateAngleZ = 0f;
		base.rotateAngleZ = (float) +(MathHelper.sin(par1 * 0.6662F) * 0.0525F * par2) - (sleeping > 40
				? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2.25f, 2)) / 1600f : 0);
		/*
		 * neck.rotateAngleZ += (sleeping > 40 ? (float) (Math.pow((Math.min(59,
		 * sleeping) - 40f) * 2f, 2)) / 1600f : 0) * 0.25f;
		 */
		head.rotateAngleZ += (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.25f;
		head.rotateAngleY -= (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.5f;
		head.rotateAngleX += (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.25f;
		float forageRotation = 0;
		/*
		 * forageRotation += 1f * (foraging > 105 ? 1f - ((foraging - 105f) /
		 * 15f) : 0f); forageRotation += 1f * (foraging <= 105 && foraging > 0 ?
		 * (Math.min(foraging, 15) / 15f) : 0f); neck.rotateAngleX +=
		 * forageRotation;
		 */
		head.rotateAngleX -= forageRotation * 0.5f;
		// neck.rotateAngleY += 0.1f * (foraging >= 15 && foraging <= 105 ?
		// MathHelper.sin((foraging - 15) * 0.1f) : 0f);

		// neck.rotateAngleZ = -0.5f;
		leftShoulder.rotateAngleZ = 0.1f;
		rightShoulder.rotateAngleZ = -0.1f - 0.35f * (sleeping > 40
				? (float) (Math.pow((Math.min(60, sleeping) - 40f) * 2f, 2)) / 1600f : 0);
		rightThigh.rotateAngleZ = -0.35f * (sleeping > 40
				? (float) (Math.pow((Math.min(60, sleeping) - 40f) * 2f, 2)) / 1600f : 0);
		// base.rotateAngleX = (float) + (MathHelper.cos(par1 * 0.6662F *2f) *
		// 0.5F * par2);
		this.body.setRotationPoint(0, 5.5f, 2.5f);
		this.body.rotateAngleY = (float) Math.PI;
		this.body.rotateAngleX = -(0.1f + (float) (3f * Math.PI) / 2f);
		// base.rotationPointY = 12;
		/*
		 * this.snout.setRotationPoint(-0.5f, -3.8f, -1.5f);
		 * this.snout.rotateAngleX = -(float) (Math.PI * 0.1f);
		 */

		base.rotationPointY += Math.pow(Math.max(Math.min(sleeping - 10, 30), 0), 2) / 200f;
		base.rotateAngleX -= Math.min(sleeping, 40) / 1000f;
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		EntitySheepTFC sheep = (EntitySheepTFC) entity;
		boolean wooly = !sheep.getSheared();
		boolean domesticated = sheep.isDomesticated();
		this.neckWool.isHidden = !wooly || !domesticated;
		this.chestWool.isHidden = !wooly || !domesticated;
		this.bodyWool.isHidden = !wooly || !domesticated;
		this.buttWool.isHidden = !wooly || !domesticated;
		this.leftShoulderWool.isHidden = !wooly || !domesticated;
		this.leftThighWool.isHidden = !wooly || !domesticated;
		this.rightShoulderWool.isHidden = !wooly || !domesticated;
		this.rightThighWool.isHidden = !wooly || !domesticated;
		this.rightArmWool.isHidden = !wooly || !domesticated;
		this.leftArmWool.isHidden = !wooly || !domesticated;
		this.wildWool.isHidden = !wooly || domesticated;

		this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

		float percent = TFC_Core.getPercentGrown((IAnimal) entity);
		float ageScale = 2.0F - percent;
		float ageHeadScale = (float) Math.pow(1 / ageScale, 0.66);
		// float offset = 1.4f - percent;

		/*
		 * if(((IAnimal)entity).isAdult()) { offset = 0; }
		 */
		base2.offsetY = -1f;
		base2.setRotationPoint(0, 0, 0);

		GL11.glPushMatrix();

		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);

		GL11.glScalef(ageHeadScale, ageHeadScale, ageHeadScale);
		GL11.glTranslatef(0.0F, (ageScale - 1) * -0.125f, 0.1875f - (0.1875f * percent));


		// this.head.render(par7);
		// this.horn1.render(par7);
		// this.horn2.render(par7);

		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.8f - (0.8f * (float) Math.pow(percent, 1.5f)), 0f);
		GL11.glScalef(1 / ageScale, 1 / ageScale, 1 / ageScale);
		GL11.glTranslatef(0.0F, 0.45f, 0f);
		GL11.glScalef(0.7f, 0.7f, 0.7f);
		if (head instanceof ModelRendererAnimalHead)
		{
			((ModelRendererAnimalHead) head).setScalesPercent(ageHeadScale, ageScale, percent);
			((ModelRendererAnimalHead) snout).setScalesPercent(ageHeadScale, ageScale / (1f + (0.5f * (1f - percent))),
					percent);
			((ModelRendererAnimalHead) muzzle).setScalesPercent(ageHeadScale, ageScale / (1f + (0.5f * (1f - percent))),
					percent);
			((ModelRendererAnimalHead) upperMouth).setScalesPercent(ageHeadScale,
					ageScale / (1f + (0.5f * (1f - percent))), percent);
			((ModelRendererAnimalHead) jaw).setScalesPercent(ageHeadScale, ageScale / (1f + (0.5f * (1f - percent))),
					percent);
			head.rotationPointZ -= 3f * (1f - percent);
			head.rotationPointY += 4f * (1f - percent);
			snout.rotationPointZ -= 3f * (1f - percent);
			snout.rotationPointY += 8f * (1f - percent);
			muzzle.rotationPointZ -= 3f * (1f - percent);
			muzzle.rotationPointY += 7.5f * (1f - percent);
			upperMouth.rotationPointZ -= 3f * (1f - percent);
			upperMouth.rotationPointY += 7.5f * (1f - percent);
			jaw.rotationPointZ -= 3f * (1f - percent);
			jaw.rotationPointY += 7.5f * (1f - percent);
		}
		this.base.render(par7);

		/*
		 * this.body.render(par7); this.leg1.render(par7);
		 * this.leg2.render(par7); this.leg3.render(par7);
		 * this.leg4.render(par7); horn1.isHidden = false; horn1b.isHidden =
		 * false; horn2.isHidden = false; horn2b.isHidden = false;
		 */
		GL11.glPopMatrix();
	}
}
