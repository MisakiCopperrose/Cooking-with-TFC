package com.dunk.tfc.Render.Blocks;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockBranch2;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves2;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.FloraIndex;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.Render.TFC_CoreRender;
import com.dunk.tfc.TileEntities.TEFruitTreeWood;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RenderBranch implements ISimpleBlockRenderingHandler
{

	private static float rot45 = (float) (-45d * (Math.PI / 180d));
	private static double sqrt2 = Math.sqrt(2);
	private static double sqrt3 = Math.sqrt(3);
	private static float diagRot = (float) -(Math.atan(sqrt2));

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		boolean hasSnow = world.getBlock(x, y + 1, z) == TFCBlocks.snow;
		boolean hasMoss_Y, hasMoss_X, hasMoss_x, hasMoss_z, hasMoss_Z, hasMoss_y, hasMoss_Y2, hasMoss_X2, hasMoss_x2, hasMoss_z2, hasMoss_Z2, hasMoss_y2;

		float snowThickness = 0.125f + (0.125f * world.getBlockMetadata(x, y + 1, z));
		float mossThickness = 0.0125F;
		boolean handled = false;
		BlockBranch branch = (BlockBranch) block;
		int meta = world.getBlockMetadata(x, y, z);
		float width = 0.2f;
		float n = ((BlockBranch) block).getDistanceToTrunk(world, x, y, z, 0);
		if (n < 0)
		{
			n = 20;
		}
		width = 0.3f - (n * 0.001f);
		if (branch.damageDropped(meta) == 26)
		{
			width *= 0.75f;
		}
		if (branch.damageDropped(meta) == 24)// bamboo
		{
			width = 0.15f - (n * 0.005f);
			if (((BlockBranch) block).isEnd())
			{
				renderblocks.setRenderBounds(0, 0, 0, 1, 1, 1);
				renderblocks.renderStandardBlock(TFCBlocks.leaves2, x, y, z);
				if (hasSnow)
				{
					renderblocks.setRenderBounds(0, 0, 0, 1, snowThickness, 1);
					renderblocks.renderStandardBlock(Blocks.snow, x, y + 1, z);
					hasSnow = false;
				}
			}
			Block b2 = world.getBlock(x, y - 1, z);
			if (b2 instanceof BlockBranch && branch.isEnd() && !((BlockBranch) b2).isEnd())
			{
				renderblocks.setRenderBounds(0, 0, 0, 1, 1, 1);
				renderblocks.renderStandardBlock(TFCBlocks.leaves2, x, y - 1, z);
				if (hasSnow)
				{
					renderblocks.setRenderBounds(0, 0, 0, 1, snowThickness, 1);
					renderblocks.renderStandardBlock(Blocks.snow, x, y + 1, z);
					hasSnow = false;
				}
			}
		}
		else if (branch.damageDropped(meta) == 26 && branch == TFCBlocks.branchEnd2__y_)
		{
			Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
			TEFruitTreeWood te = (TEFruitTreeWood) world.getTileEntity(x, y, z);
			if (te != null && te.fruitType == 1)
			{
				renderblocks = new RenderBlocksWithRotation(renderblocks);
				// This is the Banana.
				int dist = branch.getDistanceToTrunk(world, x, y, z, 0);
				Random rand = new Random(x + y + z);
				rand = new Random(rand.nextInt(100000));
				if (dist == 1)
				{
					width *= 0.75f;
					Vec3 frondDist = Vec3.createVectorHelper(0, 0.175, -0.2);
					// Make the fronds and arms tiny
					renderblocks.setRenderBounds(0.475, 0.5, 0.375, 0.525, 0.7, 0.425);
					float bananaRot = ((RenderBlocksWithRotation) renderblocks).rot45;
					double bananaAngle = -3;
					((RenderBlocksWithRotation) renderblocks).rotation = ((RenderBlocksWithRotation) renderblocks).rot30 / 2f;
					((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI, bananaRot, 0.15, 1,
					bananaAngle, 0.8, true);
					// ((RenderBlocksWithRotation)renderblocks).yRotation =
					// ((RenderBlocksWithRotation)renderblocks).rot30;
					renderblocks.renderStandardBlock(branch, x, y, z);
					for (int i = 0; i < 7; i++)
					{

						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot45;
						frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot45);
						((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI, bananaRot, 0.15, 1,
						bananaAngle, 0.8, true);
						renderblocks.renderStandardBlock(branch, x, y, z);
					}

				}
				else if (dist == 2)
				{
					// Make the fronds and arms a little bigger
					// width *= 0.9f;
					width *= 1.25f;
					Vec3 frondDist = Vec3.createVectorHelper(0, 0.26, -0.3);
					// Make the fronds and arms tiny
					renderblocks.setRenderBounds(0.45, 0.5, 0.35, 0.525, 0.8, 0.425);
					float bananaRot = ((RenderBlocksWithRotation) renderblocks).rot30;
					double bananaAngle = -1;
					double bananaDroop = 1.5;
					((RenderBlocksWithRotation) renderblocks).rotation = ((RenderBlocksWithRotation) renderblocks).rot30 / 2f;
					((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI,
					bananaRot + (rand.nextDouble() - 0.5) * 0.25, 0.4, 1, bananaAngle, bananaDroop, true);
					// ((RenderBlocksWithRotation)renderblocks).yRotation =
					// ((RenderBlocksWithRotation)renderblocks).rot30;
					renderblocks.renderStandardBlock(branch, x, y, z);
					for (int i = 0; i < 7; i++)
					{
						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot45;
						frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot45);
						((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI,
						bananaRot + (rand.nextDouble() - 0.5) * 0.25, 0.4, 1, bananaAngle, bananaDroop, true);
						renderblocks.renderStandardBlock(branch, x, y, z);
					}
				}
				else if (dist >= 3)
				{
					// Make the fronds full size and have a place for bananas to
					// grow.
					width *= 1.5f;
					Vec3 frondDist = Vec3.createVectorHelper(0, 0.42, -0.175);
					// Make the fronds and arms tiny
					renderblocks.setRenderBounds(0.45, 0.5, 0.35, 0.525, 1, 0.425);
					float bananaRot = 0;
					double bananaAngle = 2;
					double bananaDroop = 0.5;
					double bananaScale = 0.5 + (dist > 3 ? 0.1 : 0);
					((RenderBlocksWithRotation) renderblocks).rotation = ((RenderBlocksWithRotation) renderblocks).rot30 / 2f;
					((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI,
					bananaRot + rand.nextDouble() - 0.5, bananaScale, 1, bananaAngle, bananaDroop, true);
					// ((RenderBlocksWithRotation)renderblocks).yRotation =
					// ((RenderBlocksWithRotation)renderblocks).rot30;
					renderblocks.renderStandardBlock(branch, x, y, z);
					for (int i = 0; i < 7; i++)
					{
						double randomRot = (rand.nextDouble() - 0.5);
						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot45 + randomRot;
						frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot45);
						((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI,
						bananaRot + rand.nextDouble() - 0.5, bananaScale, 1, bananaAngle, bananaDroop, true);
						renderblocks.renderStandardBlock(branch, x, y, z);
						((RenderBlocksWithRotation) renderblocks).yRotation -= randomRot;
					}

					FloraIndex fi = FloraManager.getInstance().findMatchingIndex(Global.FRUIT_META_NAMES[1]);
					if (TFC_Time.getSeasonAdjustedMonth(z) >= fi.bloomStart && TFC_Time.getSeasonAdjustedMonth(z) <= fi.harvestFinish)
					{
						((RenderBlocksWithRotation) renderblocks).yRotation = (float) Math.PI * (2f * rand.nextFloat());
						// Make the Banana spot
						renderblocks.setRenderBounds(0.425, 0.5, 0.425, 0.575, 1, 0.575);
						((RenderBlocksWithRotation) renderblocks).rotation = (float) (Math.PI / 3d);
						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot22_5;
						renderblocks.renderStandardBlock(branch, x, y, z);
						renderblocks.setRenderBounds(0.4475, 0.7, 0.8475, 0.5725, 1.1625, 0.9725);
						((RenderBlocksWithRotation) renderblocks).rotation = (float) (-4 * Math.PI / 3d);
						// ((RenderBlocksWithRotation) renderblocks).yRotation =
						// ((RenderBlocksWithRotation) renderblocks).rot22_5;
						renderblocks.renderStandardBlock(branch, x, y, z);
						renderblocks.setRenderBounds(0.45, 0.5, 1.2, 0.55, 1.2, 1.3);
						((RenderBlocksWithRotation) renderblocks).rotation = (float) (Math.PI);
						// ((RenderBlocksWithRotation) renderblocks).yRotation =
						// ((RenderBlocksWithRotation) renderblocks).rot22_5;
						renderblocks.renderStandardBlock(branch, x, y, z);

						Vec3 bunchLocation = Vec3.createVectorHelper(0, 0.75, -0.75);
						bunchLocation.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

						if (TFC_Time.getSeasonAdjustedMonth(z) > fi.bloomFinish && (te.dayHarvested / TFC_Time.daysInYear) != TFC_Time.getTotalDays() / TFC_Time.daysInYear)
						{
							IIcon bananas = te.hasFruit ? BlockCustomLeaves2.iconsFruit[1] : BlockCustomLeaves2.iconsFlowers[1];
							for (int i = 0; i < 8; i++)
							{
								((RenderBlocksWithRotation) renderblocks).renderDiagonalQuad(bananas, x + bunchLocation.xCoord, y + bunchLocation.yCoord, z + bunchLocation.zCoord,
								0, 0, 0, 0.75, 2, ((float) i) * (float) (Math.PI / 4), false);
							}
						}
					}
				}

			}
			else if (te != null && te.fruitType == 10)
			{
				Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
				renderblocks = new RenderBlocksWithRotation(renderblocks);
				// This is the date.
				int dist = branch.getDistanceToTrunk(world, x, y, z, 0);
				Random rand = new Random(x + y + z);
				rand = new Random(rand.nextInt(100000));
				if (dist == 1)
				{
					width *= 0.75f;
					Vec3 frondDist = Vec3.createVectorHelper(0, 0, -0.15);
					// Make the fronds and arms tiny
					renderblocks.setRenderBounds(0.475, 0.5, 0.375, 0.525, 0.7, 0.425);
					float dateRot = ((RenderBlocksWithRotation) renderblocks).rot45;
					double dateAngle = -3;
					((RenderBlocksWithRotation) renderblocks).rotation = ((RenderBlocksWithRotation) renderblocks).rot30 / 2f;
					((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI, dateRot, 0.15, 0.5,
					dateAngle, 0.8, false);
					// ((RenderBlocksWithRotation)renderblocks).yRotation =
					// ((RenderBlocksWithRotation)renderblocks).rot30;
					// renderblocks.renderStandardBlock(branch, x, y, z);
					for (int i = 0; i < 7; i++)
					{

						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot45;
						frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot45);
						((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI, dateRot, 0.15, 0.5,
						dateAngle, 0.8, false);
						// renderblocks.renderStandardBlock(branch, x, y, z);
					}

				}
				else if (dist == 2)
				{
					// Make the fronds and arms a little bigger
					// width *= 0.9f;
					width *= 1.25f;
					Vec3 frondDist = Vec3.createVectorHelper(0, 0, -0.2);
					// Make the fronds and arms tiny
					renderblocks.setRenderBounds(0.45, 0.5, 0.35, 0.525, 0.8, 0.425);
					float dateRot = ((RenderBlocksWithRotation) renderblocks).rot30;
					double dateAngle = -1;
					double dateDroop = 1.5;
					((RenderBlocksWithRotation) renderblocks).rotation = ((RenderBlocksWithRotation) renderblocks).rot30 / 2f;
					((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI,
					dateRot + (rand.nextDouble() - 0.5) * 0.25, 0.4, 0.5, dateAngle, dateDroop, false);
					// ((RenderBlocksWithRotation)renderblocks).yRotation =
					// ((RenderBlocksWithRotation)renderblocks).rot30;
					// renderblocks.renderStandardBlock(branch, x, y, z);
					for (int i = 0; i < 7; i++)
					{
						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot45;
						frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot45);
						((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord, y + frondDist.yCoord, z + +frondDist.zCoord, Math.PI,
						dateRot + (rand.nextDouble() - 0.5) * 0.25, 0.4, 0.5, dateAngle, dateDroop, false);
						// renderblocks.renderStandardBlock(branch, x, y, z);
					}
				}
				else if (dist >= 3)
				{
					// Make the fronds full size and have a place for dates to
					// grow.
					width *= 1.5f;
					Vec3 frondDist = Vec3.createVectorHelper(0, 0, -0.2);
					// Make the fronds and arms tiny
					renderblocks.setRenderBounds(0.45, 0.5, 0.35, 0.525, 1, 0.425);
					float dateRot = -((RenderBlocksWithRotation) renderblocks).rot45 * 1.5f;
					double dateAngle = -5;
					double dateDroop = 0.5;
					double dateScale = 0.4 + ((dist - 3) / 20d);
					((RenderBlocksWithRotation) renderblocks).rotation = ((RenderBlocksWithRotation) renderblocks).rot30 / 2f;
					((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord * -1, y + frondDist.yCoord, z + +frondDist.zCoord * -1, Math.PI, dateRot,
					dateScale, 0.5, dateAngle, dateDroop, false);
					// ((RenderBlocksWithRotation)renderblocks).yRotation =
					// ((RenderBlocksWithRotation)renderblocks).rot30;
					// renderblocks.renderStandardBlock(branch, x, y, z);
					for (int i = 0; i < 32; i++)
					{
						((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot45;
						frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot45);
						((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x + frondDist.xCoord * ((i * 0.075)), y + frondDist.yCoord, z + +frondDist.zCoord * ((i * 0.075)),
						Math.PI, dateRot + (i * 0.075), dateScale, 0.5, dateAngle, dateDroop, false);
						if (i % 8 == 0)
						{
							((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot1 * 15;
							frondDist.rotateAroundY(((RenderBlocksWithRotation) renderblocks).rot1 * 15);
						}
						// renderblocks.renderStandardBlock(branch, x, y, z);
					}
					if (dist > 7)
					{
						FloraIndex fi = FloraManager.getInstance().findMatchingIndex(Global.FRUIT_META_NAMES[10]);
						if (fi != null && TFC_Time.getSeasonAdjustedMonth(z) >= fi.bloomStart && TFC_Time.getSeasonAdjustedMonth(z) <= fi.harvestFinish)
						{
							((RenderBlocksWithRotation) renderblocks).yRotation = (float) Math.PI * (2f * rand.nextFloat());
							// Make the date spot
							renderblocks.setRenderBounds(0.425, 0.5, 0.425, 0.575, 1, 0.575);
							((RenderBlocksWithRotation) renderblocks).rotation = (float) (Math.PI / 3d);
							((RenderBlocksWithRotation) renderblocks).yRotation += ((RenderBlocksWithRotation) renderblocks).rot22_5;
							renderblocks.renderStandardBlock(branch, x, y, z);
							renderblocks.setRenderBounds(0.4475, 0.7, 0.8475, 0.5725, 1.1625, 0.9725);
							((RenderBlocksWithRotation) renderblocks).rotation = (float) (-4 * Math.PI / 3d);
							// ((RenderBlocksWithRotation)
							// renderblocks).yRotation =
							// ((RenderBlocksWithRotation)
							// renderblocks).rot22_5;
							renderblocks.renderStandardBlock(branch, x, y, z);
							renderblocks.setRenderBounds(0.45, 0.5, 1.2, 0.55, 1.2, 1.3);
							((RenderBlocksWithRotation) renderblocks).rotation = (float) (Math.PI);
							// ((RenderBlocksWithRotation)
							// renderblocks).yRotation =
							// ((RenderBlocksWithRotation)
							// renderblocks).rot22_5;
							renderblocks.renderStandardBlock(branch, x, y, z);

							Vec3 bunchLocation = Vec3.createVectorHelper(0, 0, -0.75);
							bunchLocation.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

							if (TFC_Time.getSeasonAdjustedMonth(z) > fi.bloomFinish && (te.dayHarvested / TFC_Time.daysInYear) != TFC_Time.getTotalDays() / TFC_Time.daysInYear)
							{
								IIcon dates = te.hasFruit ? BlockCustomLeaves2.iconsFruit[10] : BlockCustomLeaves2.iconsFlowers[10];
								for (int i = 0; i < 8; i++)
								{
									((RenderBlocksWithRotation) renderblocks).renderDiagonalQuad(dates, x + bunchLocation.xCoord, y + bunchLocation.yCoord,
									z + bunchLocation.zCoord, 0, 0, 0, 0.75, 2, ((float) i) * (float) (Math.PI / 4), false);
								}
							}
						}
					}
				}

			}
		}
		else if (BlockBranch.renderSmallConiferBranch(meta, branch instanceof BlockBranch2 ? 1 : 0))
		{
			float yOffset = 0f;
			float xOffset = 0.15f * (float) branch.getSourceX();
			float zOffset = 0.15f * (float) branch.getSourceZ();
			Random rand = new Random((x % 31) + (y % 17) + (z % 11));
			rand = new Random(rand.nextInt(100000));
			yOffset = rand.nextFloat() * 0.4f;
			// xOffset += rand.nextFloat()*0.4f;
			// zOffset += rand.nextFloat()*0.4f;
			if (((BlockBranch) block).isEnd() && block != TFCBlocks.branchEnd__y_)
			{
				renderblocks.setRenderBounds(-0.1 + xOffset - (width * 0.3f), 0 + yOffset, -0.1 + zOffset - (width * 0.3f), 0.7 + xOffset + (width * 0.3f), 0.6 + yOffset,
				0.7 + zOffset + (width * 0.3f));
				renderblocks.renderStandardBlock(branch instanceof BlockBranch2 ? TFCBlocks.leaves2 : TFCBlocks.leaves, x, y, z);

				
				if (hasSnow)
				{
					renderblocks.setRenderBounds(-0.1 + xOffset - (width * 0.3f), 0.6 + yOffset, -0.1 + zOffset - (width * 0.3f), 0.7 + xOffset + (width * 0.3f),
					0.6 + yOffset + snowThickness, 0.7 + zOffset + (width * 0.3f));
					renderblocks.renderStandardBlock(Blocks.snow, x, y, z);
					hasSnow = false;
				}
			}
			else if (((BlockBranch) block).isEnd())
			{
				renderblocks.setRenderBounds(0.2, 0, 0.2, 0.8, 0.9, 0.8);
				renderblocks.renderStandardBlock(branch instanceof BlockBranch2 ? TFCBlocks.leaves2 : TFCBlocks.leaves, x, y, z);
				if (hasSnow)
				{
					renderblocks.setRenderBounds(0.2, 0.9, 0.2, 0.8, 0.9 + snowThickness, 0.8);
					renderblocks.renderStandardBlock(Blocks.snow, x, y, z);
					hasSnow = false;
				}
			}
			if(((BlockBranch) block).isEnd())
			{
			boolean night = false;
			boolean morning = false;
			if (TFCOptions.enableDefaultCelestialAngle)
			{
				night = TFC_Time.getHour() > 18;
				morning = TFC_Time.getHour() < 6;
			}
			else
			{
				boolean dark = TFC_Core.getCelestialDaylight(world, x, y, z, TFC_Time.getTotalTicks(), 0.0125f) < 5;
				night = TFC_Time.getHour() >= 12 && dark;
				morning = TFC_Time.getHour() < 12 && dark;
			}
			boolean inGameChristmas = ((TFC_Time.getDayOfMonth() == 24 && night) || (TFC_Time.getDayOfMonth() == 25 && morning)) && TFC_Time.getMonth() == TFC_Time.DECEMBER;
			if ((Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH) == 11
			&& ((Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH) == 24 )
			|| (Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH) == 25 ))) || inGameChristmas)
			{

				if(block != TFCBlocks.branchEnd__y_)
				{
				renderblocks.setRenderBounds(-0.1 + xOffset - (width * 0.3f) - 0.01, 0 + yOffset - 0.01, -0.1 + zOffset - (width * 0.3f) - 0.01,
				0.7 + xOffset + (width * 0.3f) + 0.01, 0.6 + yOffset + 0.01, 0.7 + zOffset + (width * 0.3f) + 0.01);
				}
				else
				{
					renderblocks.setRenderBounds(0.2 -0.01, 0-0.01, 0.2-0.01, 0.8+0.01, 0.9+0.01, 0.8+0.01);
				}
				renderblocks.renderStandardBlock(TFCBlocks.cLights, x, y, z);
			}
			}
		}

		renderblocks = new RenderBlocksWithRotation(renderblocks);

		// ((RenderBlocksWithRotation)renderblocks).yRotation =
		// (float)(((x%4)*90) * (Math.PI)/180f);
		// We're going to make this work.
		// The thinnest branch has 0.2 width
		// The medium has 0.4?
		// The thickest has 0.5?

		// This means the branch points down but not vertical

		width = Math.max(width, 0.1f);
		/*
		 * if (((BlockBranch) block).getSourceBlock(world, x, y, z,1) instanceof
		 * BlockLogNatural || TFC_Core.isSoil(((BlockBranch)
		 * block).getSourceBlock(world, x, y, z,3)) ||
		 * TFC_Core.isSand(((BlockBranch) block).getSourceBlock(world, x, y,
		 * z,3))) { width += 0.05f; }
		 */
		if (((BlockBranch) block).isEnd() || (BlockBranch.renderSmallConiferBranch(meta, branch instanceof BlockBranch2 ? 1 : 0) && branch.getSourceY() == 0))
		{
			width *= 0.5f;
		}
		else if ((!((BlockBranch) block).isEnd())
		&& (!BlockBranch.renderSmallConiferBranch(meta, branch instanceof BlockBranch2 ? 1 : 0) && !(branch.getSourceX() == 0 && branch.getSourceZ() == 0)))
		{
			width *= 0.75f;
		}
		if ((block == TFCBlocks.branchEnd2__y_ || block == TFCBlocks.branchEnd2_x__ || block == TFCBlocks.branchEnd2___z || block == TFCBlocks.branchEnd2_Xy_
		|| block == TFCBlocks.branchEnd2_X__ || block == TFCBlocks.branchEnd2_X_z || block == TFCBlocks.branchEnd2__yZ || block == TFCBlocks.branchEnd2_x_Z
		|| block == TFCBlocks.branchEnd2___Z || block == TFCBlocks.branchEnd2_xyz || block == TFCBlocks.branchEnd2_x_z || block == TFCBlocks.branchEnd2_X_Z
		|| block == TFCBlocks.branchEnd2_XyZ || block == TFCBlocks.branchEnd2_xyZ || block == TFCBlocks.branchEnd2_xy_ || block == TFCBlocks.branchEnd2__yz
		|| block == TFCBlocks.branchEnd2_Xy_) && world.getBlockMetadata(x, y, z) == 1)
		{
			((RenderBlocksWithRotation) renderblocks).renderStandardBlock(TFCBlocks.fauxPalm, x, y, z);
		}

		if (block == TFCBlocks.branch2__yz || block == TFCBlocks.branch2_xy_ || block == TFCBlocks.branch2__yZ || block == TFCBlocks.branch2_Xy_ || block == TFCBlocks.branch2_xyz
		|| block == TFCBlocks.branch2_xyZ || block == TFCBlocks.branch2_XyZ || block == TFCBlocks.branch2_Xyz || block == TFCBlocks.branch__yz || block == TFCBlocks.branch_xy_
		|| block == TFCBlocks.branch__yZ || block == TFCBlocks.branch_Xy_ || block == TFCBlocks.branch_xyz || block == TFCBlocks.branch_xyZ || block == TFCBlocks.branch_XyZ
		|| block == TFCBlocks.branch_Xyz || block == TFCBlocks.branchEnd2__yz || block == TFCBlocks.branchEnd2_xy_ || block == TFCBlocks.branchEnd2__yZ
		|| block == TFCBlocks.branchEnd2_Xy_ || block == TFCBlocks.branchEnd2_xyz || block == TFCBlocks.branchEnd2_xyZ || block == TFCBlocks.branchEnd2_XyZ
		|| block == TFCBlocks.branchEnd2_Xyz || block == TFCBlocks.branchEnd__yz || block == TFCBlocks.branchEnd_xy_ || block == TFCBlocks.branchEnd__yZ
		|| block == TFCBlocks.branchEnd_Xy_ || block == TFCBlocks.branchEnd_xyz || block == TFCBlocks.branchEnd_xyZ || block == TFCBlocks.branchEnd_XyZ
		|| block == TFCBlocks.branchEnd_Xyz)
		{
			((RenderBlocksWithRotation) renderblocks).rotation = ((BlockBranch) block).isLongDiagonal() ? diagRot : rot45;
			renderblocks.setRenderBounds(0.5 - width + 0.025, 0.5 - (((BlockBranch) block).isLongDiagonal() ? sqrt3 : sqrt2), 0.5 - width + 0.025, 0.5 + width - 0.025, 0.5,
			0.5 + width - 0.025);
			handled = true;
		}
		else if (block == TFCBlocks.branch2__Yz || block == TFCBlocks.branch2_xY_ || block == TFCBlocks.branch2__YZ || block == TFCBlocks.branch2_XY_
		|| block == TFCBlocks.branch2_xYz || block == TFCBlocks.branch2_xYZ || block == TFCBlocks.branch2_XYZ || block == TFCBlocks.branch2_XYz || block == TFCBlocks.branch__Yz
		|| block == TFCBlocks.branch_xY_ || block == TFCBlocks.branch__YZ || block == TFCBlocks.branch_XY_ || block == TFCBlocks.branch_xYz || block == TFCBlocks.branch_xYZ
		|| block == TFCBlocks.branch_XYZ || block == TFCBlocks.branch_XYz || block == TFCBlocks.branchEnd2__Yz || block == TFCBlocks.branchEnd2_xY_
		|| block == TFCBlocks.branchEnd2__YZ || block == TFCBlocks.branchEnd2_XY_ || block == TFCBlocks.branchEnd2_xYz || block == TFCBlocks.branchEnd2_xYZ
		|| block == TFCBlocks.branchEnd2_XYZ || block == TFCBlocks.branchEnd2_XYz || block == TFCBlocks.branchEnd__Yz || block == TFCBlocks.branchEnd_xY_
		|| block == TFCBlocks.branchEnd__YZ || block == TFCBlocks.branchEnd_XY_ || block == TFCBlocks.branchEnd_xYz || block == TFCBlocks.branchEnd_xYZ
		|| block == TFCBlocks.branchEnd_XYZ || block == TFCBlocks.branchEnd_XYz)
		{
			((RenderBlocksWithRotation) renderblocks).rotation = ((BlockBranch) block).isLongDiagonal() ? diagRot : rot45;

			renderblocks.setRenderBounds(0.5 - width + 0.025, 0.5, 0.5 - width + 0.025, 0.5 + width - 0.025, 0.5 + (((BlockBranch) block).isLongDiagonal() ? sqrt3 : sqrt2),
			0.5 + width - 0.025);
			handled = true;
		}
		if (block == TFCBlocks.branch2___z || block == TFCBlocks.branch2_x__ || block == TFCBlocks.branch2___Z || block == TFCBlocks.branch2_X__ || block == TFCBlocks.branch2_x_z
		|| block == TFCBlocks.branch2_x_Z || block == TFCBlocks.branch2_X_Z || block == TFCBlocks.branch2_X_z || block == TFCBlocks.branch___z || block == TFCBlocks.branch_x__
		|| block == TFCBlocks.branch___Z || block == TFCBlocks.branch_X__ || block == TFCBlocks.branch_x_z || block == TFCBlocks.branch_x_Z || block == TFCBlocks.branch_X_Z
		|| block == TFCBlocks.branch_X_z || block == TFCBlocks.branchEnd2___z || block == TFCBlocks.branchEnd2_x__ || block == TFCBlocks.branchEnd2___Z
		|| block == TFCBlocks.branchEnd2_X__ || block == TFCBlocks.branchEnd2_x_z || block == TFCBlocks.branchEnd2_x_Z || block == TFCBlocks.branchEnd2_X_Z
		|| block == TFCBlocks.branchEnd2_X_z || block == TFCBlocks.branchEnd___z || block == TFCBlocks.branchEnd_x__ || block == TFCBlocks.branchEnd___Z
		|| block == TFCBlocks.branchEnd_X__ || block == TFCBlocks.branchEnd_x_z || block == TFCBlocks.branchEnd_x_Z || block == TFCBlocks.branchEnd_X_Z
		|| block == TFCBlocks.branchEnd_X_z)
		{
			((RenderBlocksWithRotation) renderblocks).rotation = (float) (-90 * (Math.PI) / 180f);

			renderblocks.setRenderBounds(0.5 - width, 0.5 - (((BlockBranch) block).isShortDiagonal() ? sqrt2 : 1), 0.5 - width, 0.5 + width, 0.5, 0.5 + width);
			handled = true;
		}
		if (block == TFCBlocks.branch2__y_ || block == TFCBlocks.branch__y_ || block == TFCBlocks.branchEnd2__y_ || block == TFCBlocks.branchEnd__y_)
		{
			((RenderBlocksWithRotation) renderblocks).rotation = 0;
			renderblocks.setRenderBounds(0.5 - width, -0.5, 0.5 - width, 0.5 + width, 0.5, 0.5 + width);
			handled = true;
		}
		// +x-z
		if (block == TFCBlocks.branch2_X_z || block == TFCBlocks.branch2_Xyz || block == TFCBlocks.branch2_XYz || block == TFCBlocks.branch_X_z || block == TFCBlocks.branch_Xyz
		|| block == TFCBlocks.branch_XYz || block == TFCBlocks.branchEnd2_X_z || block == TFCBlocks.branchEnd2_Xyz || block == TFCBlocks.branchEnd2_XYz
		|| block == TFCBlocks.branchEnd_X_z || block == TFCBlocks.branchEnd_Xyz || block == TFCBlocks.branchEnd_XYz)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((135 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// -z
		if (block == TFCBlocks.branch2___z || block == TFCBlocks.branch2__yz || block == TFCBlocks.branch2__Yz || block == TFCBlocks.branch___z || block == TFCBlocks.branch__yz
		|| block == TFCBlocks.branch__Yz || block == TFCBlocks.branchEnd2___z || block == TFCBlocks.branchEnd2__yz || block == TFCBlocks.branchEnd2__Yz
		|| block == TFCBlocks.branchEnd___z || block == TFCBlocks.branchEnd__yz || block == TFCBlocks.branchEnd__Yz)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((180 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// -x-z
		if (block == TFCBlocks.branch2_x_z || block == TFCBlocks.branch2_xyz || block == TFCBlocks.branch2_xYz || block == TFCBlocks.branch_x_z || block == TFCBlocks.branch_xyz
		|| block == TFCBlocks.branch_xYz || block == TFCBlocks.branchEnd2_x_z || block == TFCBlocks.branchEnd2_xyz || block == TFCBlocks.branchEnd2_xYz
		|| block == TFCBlocks.branchEnd_x_z || block == TFCBlocks.branchEnd_xyz || block == TFCBlocks.branchEnd_xYz)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((225 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// +z
		if (block == TFCBlocks.branch2___Z || block == TFCBlocks.branch2__yZ || block == TFCBlocks.branch2__YZ || block == TFCBlocks.branch___Z || block == TFCBlocks.branch__yZ
		|| block == TFCBlocks.branch__YZ || block == TFCBlocks.branchEnd2___Z || block == TFCBlocks.branchEnd2__yZ || block == TFCBlocks.branchEnd2__YZ
		|| block == TFCBlocks.branchEnd___Z || block == TFCBlocks.branchEnd__yZ || block == TFCBlocks.branchEnd__YZ)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((0 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// -x+z
		if (block == TFCBlocks.branch2_x_Z || block == TFCBlocks.branch2_xyZ || block == TFCBlocks.branch2_xYZ || block == TFCBlocks.branch_x_Z || block == TFCBlocks.branch_xyZ
		|| block == TFCBlocks.branch_xYZ || block == TFCBlocks.branchEnd2_x_Z || block == TFCBlocks.branchEnd2_xyZ || block == TFCBlocks.branchEnd2_xYZ
		|| block == TFCBlocks.branchEnd_x_Z || block == TFCBlocks.branchEnd_xyZ || block == TFCBlocks.branchEnd_xYZ)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((315 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// +x+z
		if (block == TFCBlocks.branch2_X_Z || block == TFCBlocks.branch2_XyZ || block == TFCBlocks.branch2_XYZ || block == TFCBlocks.branch_X_Z || block == TFCBlocks.branch_XyZ
		|| block == TFCBlocks.branch_XYZ || block == TFCBlocks.branchEnd2_X_Z || block == TFCBlocks.branchEnd2_XyZ || block == TFCBlocks.branchEnd2_XYZ
		|| block == TFCBlocks.branchEnd_X_Z || block == TFCBlocks.branchEnd_XyZ || block == TFCBlocks.branchEnd_XYZ)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((45 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// +x
		if (block == TFCBlocks.branch2_X__ || block == TFCBlocks.branch2_Xy_ || block == TFCBlocks.branch2_XY_ || block == TFCBlocks.branch_X__ || block == TFCBlocks.branch_Xy_
		|| block == TFCBlocks.branch_XY_ || block == TFCBlocks.branchEnd2_X__ || block == TFCBlocks.branchEnd2_Xy_ || block == TFCBlocks.branchEnd2_XY_
		|| block == TFCBlocks.branchEnd_X__ || block == TFCBlocks.branchEnd_Xy_ || block == TFCBlocks.branchEnd_XY_)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((90 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
		}
		// -x
		if (block == TFCBlocks.branch2_x__ || block == TFCBlocks.branch2_xy_ || block == TFCBlocks.branch2_xY_ || block == TFCBlocks.branch_x__ || block == TFCBlocks.branch_xy_
		|| block == TFCBlocks.branch_xY_ || block == TFCBlocks.branchEnd2_x__ || block == TFCBlocks.branchEnd2_xy_ || block == TFCBlocks.branchEnd2_xY_
		|| block == TFCBlocks.branchEnd_x__ || block == TFCBlocks.branchEnd_xy_ || block == TFCBlocks.branchEnd_xY_)
		{
			((RenderBlocksWithRotation) renderblocks).yRotation = (float) ((270 + (((BlockBranch) block).getSourceY() == 1 ? 0 : 180)) * (Math.PI) / 180f);
			;
		}
		if (!handled)
		{
			System.out.println("not handled");
		}
		Vec3 mossDirection_Y = Vec3.createVectorHelper(0, 1, 0);
		Vec3 mossDirection_y = Vec3.createVectorHelper(0, -1, 0);
		Vec3 mossDirection_X = Vec3.createVectorHelper(1, 0, 0);
		Vec3 mossDirection_x = Vec3.createVectorHelper(-1, 0, 0);
		Vec3 mossDirection_Z = Vec3.createVectorHelper(0, 0, 1);
		Vec3 mossDirection_z = Vec3.createVectorHelper(0, 0, -1);

		Vec3 mossDirection_Xy = Vec3.createVectorHelper(1, -1, 0);
		Vec3 mossDirection_xy = Vec3.createVectorHelper(-1, -1, 0);
		Vec3 mossDirection_Zy = Vec3.createVectorHelper(0, -1, 1);
		Vec3 mossDirection_zy = Vec3.createVectorHelper(0, -1, -1);

		mossDirection_Y.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_Y.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);
		mossDirection_y.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_y.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

		mossDirection_X.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_X.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);
		mossDirection_x.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_x.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

		mossDirection_Z.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_Z.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);
		mossDirection_z.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_z.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

		mossDirection_Xy.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_Xy.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);
		mossDirection_xy.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_xy.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

		mossDirection_Zy.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_Zy.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);
		mossDirection_zy.rotateAroundX(((RenderBlocksWithRotation) renderblocks).rotation);
		mossDirection_zy.rotateAroundY(((RenderBlocksWithRotation) renderblocks).yRotation);

		hasMoss_Y =
		world.getBlock((int) Math.round(x + mossDirection_Y.xCoord), (int) Math.round(y + mossDirection_Y.yCoord), (int) Math.round(z + mossDirection_Y.zCoord)) == TFCBlocks.moss;
		hasMoss_y =
		world.getBlock((int) Math.round(x + mossDirection_y.xCoord), (int) Math.round(y + mossDirection_y.yCoord), (int) Math.round(z + mossDirection_y.zCoord)) == TFCBlocks.moss;

		hasMoss_x =
		world.getBlock((int) Math.round(x + mossDirection_x.xCoord), (int) Math.round(y + mossDirection_x.yCoord), (int) Math.round(z + mossDirection_x.zCoord)) == TFCBlocks.moss;

		hasMoss_x2 = world.getBlock((int) Math.round(x + mossDirection_xy.xCoord), (int) Math.round(y + mossDirection_xy.yCoord),
		(int) Math.round(z + mossDirection_xy.zCoord)) == TFCBlocks.moss;

		hasMoss_X =
		world.getBlock((int) Math.round(x + mossDirection_X.xCoord), (int) Math.round(y + mossDirection_X.yCoord), (int) Math.round(z + mossDirection_X.zCoord)) == TFCBlocks.moss;

		hasMoss_X2 = world.getBlock((int) Math.round(x + mossDirection_Xy.xCoord), (int) Math.round(y + mossDirection_Xy.yCoord),
		(int) Math.round(z + mossDirection_Xy.zCoord)) == TFCBlocks.moss;

		hasMoss_z =
		world.getBlock((int) Math.round(x + mossDirection_z.xCoord), (int) Math.round(y + mossDirection_z.yCoord), (int) Math.round(z + mossDirection_z.zCoord)) == TFCBlocks.moss;

		hasMoss_z2 = world.getBlock((int) Math.round(x + mossDirection_zy.xCoord), (int) Math.round(y + mossDirection_zy.yCoord),
		(int) Math.round(z + mossDirection_zy.zCoord)) == TFCBlocks.moss;

		hasMoss_Z =
		world.getBlock((int) Math.round(x + mossDirection_Z.xCoord), (int) Math.round(y + mossDirection_Z.yCoord), (int) Math.round(z + mossDirection_Z.zCoord)) == TFCBlocks.moss;

		hasMoss_Z2 = world.getBlock((int) Math.round(x + mossDirection_Zy.xCoord), (int) Math.round(y + mossDirection_Zy.yCoord),
		(int) Math.round(z + mossDirection_Zy.zCoord)) == TFCBlocks.moss;

		Tessellator.instance.setColorOpaque(200, 200, 200);
		Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
		// GL11.glDisable(GL11.GL_CULL_FACE);
		double minZ, maxZ, minX, maxX, minY, maxY;

		if (hasMoss_Y)
		{
			minY = renderblocks.renderMinY;
			maxY = renderblocks.renderMaxY;
			renderblocks.renderMinY = maxY;
			renderblocks.renderMaxY = maxY + mossThickness;
			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX - mossThickness, renderblocks.renderMinY, renderblocks.renderMinZ - mossThickness,
			renderblocks.renderMaxX + mossThickness, renderblocks.renderMaxY, renderblocks.renderMaxZ + mossThickness, 1, 0);

			boolean[][][] mosses = new boolean[3][3][3];
			for (int xx = -1; xx < 2; xx++)
			{
				for (int yy = -1; yy < 2; yy++)
				{
					for (int zz = -1; zz < 2; zz++)
					{
						int X = xx;// (int) (xx * mossDirection_X.xCoord + yy *
									// mossDirection_Y.xCoord + zz *
									// mossDirection_Z.xCoord);
						int Y = yy;// (int) (xx * mossDirection_X.yCoord + yy *
									// mossDirection_Y.yCoord + zz *
									// mossDirection_Z.yCoord);
						int Z = zz;// (int) (xx * mossDirection_X.zCoord + yy *
									// mossDirection_Y.zCoord + zz *
									// mossDirection_Z.zCoord);
						mosses[xx + 1][yy + 1][zz + 1] = renderblocks.blockAccess.getBlock(x + X + (int) Math.round(mossDirection_Y.xCoord),
						y + Y + (int) Math.round(mossDirection_Y.yCoord), z + Z + (int) Math.round(mossDirection_Y.zCoord)) == TFCBlocks.moss;
					}
				}
			}
			int rot = 0;
			float adjX = -(float) Math.PI / 2f;
			float adj = 0;
			if (mossDirection_Y.yCoord == 1)
			{
				adjX = 0;
			}
			else if (mossDirection_Y.xCoord * mossDirection_Y.xCoord == 1)
			{
				adj = (float) (mossDirection_Y.xCoord * Math.PI / 2d);
				rot = 1 + (int) (mossDirection_Y.xCoord * 1);
			}
			int[] idTexRot = TFC_CoreRender.handleMossFace((RenderBlocksWithRotation) renderblocks, x + (int) Math.round(mossDirection_Y.xCoord),
			y + (int) Math.round(mossDirection_Y.yCoord), z + (int) Math.round(mossDirection_Y.zCoord), mosses, -((RenderBlocksWithRotation) renderblocks).rotation + adj,
			-((RenderBlocksWithRotation) renderblocks).yRotation + adj, 0, 1, mossDirection_X, mossDirection_Y, mossDirection_Z, rot, 0);

			((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], x, y, z, 1, 0);
			// ((RenderBlocksWithRotation) renderblocks).renderMoss(0,x,y,z,
			// true, false);
			renderblocks.renderMinY = minY;
			renderblocks.renderMaxY = maxY;
		}
		if (hasMoss_y)
		{
			minY = renderblocks.renderMinY;
			maxY = renderblocks.renderMaxY;
			renderblocks.renderMinY = minY - mossThickness;
			renderblocks.renderMaxY = minY;
			// ((RenderBlocksWithRotation)
			// renderblocks).renderStandardBlock(TFCBlocks.moss, x, y, z);

			boolean[][][] mosses = new boolean[3][3][3];
			for (int xx = -1; xx < 2; xx++)
			{
				for (int yy = -1; yy < 2; yy++)
				{
					for (int zz = -1; zz < 2; zz++)
					{
						int X = xx;// (int) (xx * mossDirection_X.xCoord + yy *
									// mossDirection_Y.xCoord + zz *
									// mossDirection_Z.xCoord);
						int Y = yy;// (int) (xx * mossDirection_X.yCoord + yy *
									// mossDirection_Y.yCoord + zz *
									// mossDirection_Z.yCoord);
						int Z = zz;// (int) (xx * mossDirection_X.zCoord + yy *
									// mossDirection_Y.zCoord + zz *
									// mossDirection_Z.zCoord);
						mosses[xx + 1][yy + 1][zz + 1] = renderblocks.blockAccess.getBlock(x + X + (int) Math.round(mossDirection_y.xCoord),
						y + Y + (int) Math.round(mossDirection_y.yCoord), z + Z + (int) Math.round(mossDirection_y.zCoord)) == TFCBlocks.moss;
					}
				}
			}
			int rot = 0;
			float adj = (float) Math.PI;
			if (mossDirection_Y.yCoord == 1)
			{
				adj = (float) Math.PI / 2f;
			}
			int[] idTexRot = TFC_CoreRender.handleMossFace((RenderBlocksWithRotation) renderblocks, x + (int) Math.round(mossDirection_y.xCoord),
			y + (int) Math.round(mossDirection_y.yCoord), z + (int) Math.round(mossDirection_y.zCoord), mosses, -((RenderBlocksWithRotation) renderblocks).rotation,
			-((RenderBlocksWithRotation) renderblocks).yRotation + adj, 0, 0, mossDirection_X, mossDirection_Y, mossDirection_Z, rot, 0);

			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX - mossThickness, renderblocks.renderMinY, renderblocks.renderMinZ - mossThickness,
			renderblocks.renderMaxX + mossThickness, renderblocks.renderMaxY, renderblocks.renderMaxZ + mossThickness, 0, 0);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], x, y, z, 0, 0);
			// ((RenderBlocksWithRotation) renderblocks).renderMoss(0,x,y,z,
			// true,false);
			renderblocks.renderMinY = minY;
			renderblocks.renderMaxY = maxY;
		}
		if (hasMoss_x)
		{
			minX = renderblocks.renderMinX;
			maxX = renderblocks.renderMaxX;
			renderblocks.renderMinX = minX - mossThickness;
			renderblocks.renderMaxX = minX;

			boolean[][][] mosses = new boolean[3][3][3];
			for (int xx = -1; xx < 2; xx++)
			{
				for (int yy = -1; yy < 2; yy++)
				{
					for (int zz = -1; zz < 2; zz++)
					{
						int X = xx;// (int) (xx * mossDirection_X.xCoord + yy *
									// mossDirection_Y.xCoord + zz *
									// mossDirection_Z.xCoord);
						int Y = yy;// (int) (xx * mossDirection_X.yCoord + yy *
									// mossDirection_Y.yCoord + zz *
									// mossDirection_Z.yCoord);
						int Z = zz;// (int) (xx * mossDirection_X.zCoord + yy *
									// mossDirection_Y.zCoord + zz *
									// mossDirection_Z.zCoord);
						mosses[xx + 1][yy + 1][zz + 1] = renderblocks.blockAccess.getBlock(x + X + (int) Math.round(mossDirection_x.xCoord),
						y + Y + (int) Math.round(mossDirection_x.yCoord), z + Z + (int) Math.round(mossDirection_x.zCoord)) == TFCBlocks.moss;
					}
				}
			}
			int rot = 1;
			float adj = (float) -Math.PI;
			float adjZ = (float) (-Math.PI / 2d);
			float adjX = 0;
			if (mossDirection_Y.yCoord == 1)
			{
				adj = (float) -Math.PI / 2f;
			}
			else if (mossDirection_Y.xCoord * mossDirection_Y.xCoord == 1)
			{
				adjZ = (float) -Math.PI;
				adjX = mossDirection_Y.xCoord == -1 ? 0 : (float) -Math.PI;
				adj = mossDirection_Y.xCoord == 1 ? 0 : (float) Math.PI;
			}
			int[] idTexRot = TFC_CoreRender.handleMossFace((RenderBlocksWithRotation) renderblocks, x + (int) Math.round(mossDirection_x.xCoord),
			y + (int) Math.round(mossDirection_x.yCoord), z + (int) Math.round(mossDirection_x.zCoord), mosses, -((RenderBlocksWithRotation) renderblocks).rotation + adjX,
			-((RenderBlocksWithRotation) renderblocks).yRotation + adj, adjZ, 5, mossDirection_X, mossDirection_Y, mossDirection_Z, (int) rot, 1);

			// ((RenderBlocksWithRotation)
			// renderblocks).renderStandardBlock(TFCBlocks.moss, x, y, z);
			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX, 0 - mossThickness, renderblocks.renderMinZ - mossThickness, renderblocks.renderMaxX,
			renderblocks.renderMaxY + mossThickness, renderblocks.renderMaxZ + mossThickness, 5, 0);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], x, y, z, 5, 0);
			// ((RenderBlocksWithRotation) renderblocks).renderMoss(0,x,y,z,
			// false,false);
			renderblocks.renderMinX = minX;
			renderblocks.renderMaxX = maxX;
		}
		if (hasMoss_x2)
		{
			minX = renderblocks.renderMinX;
			maxX = renderblocks.renderMaxX;
			renderblocks.renderMinX = minX - mossThickness;
			renderblocks.renderMaxX = minX;

			boolean[][][] mosses = new boolean[3][3][3];
			for (int xx = -1; xx < 2; xx++)
			{
				for (int yy = -1; yy < 2; yy++)
				{
					for (int zz = -1; zz < 2; zz++)
					{
						int X = xx;// (int) (xx * mossDirection_X.xCoord + yy *
									// mossDirection_Y.xCoord + zz *
									// mossDirection_Z.xCoord);
						int Y = yy;// (int) (xx * mossDirection_X.yCoord + yy *
									// mossDirection_Y.yCoord + zz *
									// mossDirection_Z.yCoord);
						int Z = zz;// (int) (xx * mossDirection_X.zCoord + yy *
									// mossDirection_Y.zCoord + zz *
									// mossDirection_Z.zCoord);
						mosses[xx + 1][yy + 1][zz + 1] = renderblocks.blockAccess.getBlock(x + X + (int) Math.round(mossDirection_xy.xCoord),
						y + Y + (int) Math.round(mossDirection_xy.yCoord), z + Z + (int) Math.round(mossDirection_xy.zCoord)) == TFCBlocks.moss;
					}
				}
			}
			int rot = 1;
			float adj = (float) -Math.PI;
			float adjZ = (float) (-Math.PI / 2d);
			float adjX = 0;
			if (mossDirection_Y.yCoord == 1)
			{
				adj = (float) -Math.PI / 2f;
			}
			else if (mossDirection_Y.xCoord * mossDirection_Y.xCoord == 1)
			{
				adjZ = (float) -Math.PI;
				adjX = mossDirection_Y.xCoord == -1 ? 0 : (float) -Math.PI;
				adj = mossDirection_Y.xCoord == 1 ? 0 : (float) Math.PI;
			}

			int[] idTexRot = TFC_CoreRender.handleMossFace((RenderBlocksWithRotation) renderblocks, x + (int) Math.round(mossDirection_xy.xCoord),
			y + (int) Math.round(mossDirection_xy.yCoord), z + (int) Math.round(mossDirection_xy.zCoord), mosses, -((RenderBlocksWithRotation) renderblocks).rotation + adjX,
			-((RenderBlocksWithRotation) renderblocks).yRotation + adj, adjZ, 5, mossDirection_X, mossDirection_Y, mossDirection_Z, (int) rot, 2);

			// ((RenderBlocksWithRotation)renderblocks).renderStandardBlock(TFCBlocks.moss,
			// x, y, z);
			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX, renderblocks.renderMinY - mossThickness, renderblocks.renderMinZ - mossThickness,
			renderblocks.renderMaxX, 0 + mossThickness, renderblocks.renderMaxZ + mossThickness, 5, 1);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], x, y, z, 5, 1);
			// ((RenderBlocksWithRotation)renderblocks).renderMoss(0,x,y,z,
			// false,false);

			renderblocks.renderMinX = minX;
			renderblocks.renderMaxX = maxX;
		}
		if (hasMoss_X)
		{
			minX = renderblocks.renderMinX;
			maxX = renderblocks.renderMaxX;
			renderblocks.renderMinX = maxX;
			renderblocks.renderMaxX = maxX + mossThickness;

			boolean[][][] mosses = new boolean[3][3][3];
			for (int xx = -1; xx < 2; xx++)
			{
				for (int yy = -1; yy < 2; yy++)
				{
					for (int zz = -1; zz < 2; zz++)
					{
						int X = xx;// (int) (xx * mossDirection_X.xCoord + yy *
									// mossDirection_Y.xCoord + zz *
									// mossDirection_Z.xCoord);
						int Y = yy;// (int) (xx * mossDirection_X.yCoord + yy *
									// mossDirection_Y.yCoord + zz *
									// mossDirection_Z.yCoord);
						int Z = zz;// (int) (xx * mossDirection_X.zCoord + yy *
									// mossDirection_Y.zCoord + zz *
									// mossDirection_Z.zCoord);
						mosses[xx + 1][yy + 1][zz + 1] = renderblocks.blockAccess.getBlock(x + X + (int) Math.round(mossDirection_X.xCoord),
						y + Y + (int) Math.round(mossDirection_X.yCoord), z + Z + (int) Math.round(mossDirection_X.zCoord)) == TFCBlocks.moss;
					}
				}
			}
			int rot = 1;
			float adj = (float) Math.PI;
			float adjZ = (float) (Math.PI / 2d);
			float adjX = 0;
			if (mossDirection_Y.yCoord == 1)
			{
				adj = (float) Math.PI / 2f;
			}
			else if (mossDirection_Y.xCoord * mossDirection_Y.xCoord == 1)
			{
				adjZ = 0;// (float) -Math.PI;
				adjX = mossDirection_Y.xCoord == -1 ? 0 : (float) -Math.PI;
				adj = mossDirection_Y.xCoord == -1 ? 0 : (float) Math.PI;
			}
			int[] idTexRot = TFC_CoreRender.handleMossFace((RenderBlocksWithRotation) renderblocks, x + (int) Math.round(mossDirection_X.xCoord),
			y + (int) Math.round(mossDirection_X.yCoord), z + (int) Math.round(mossDirection_X.zCoord), mosses, -((RenderBlocksWithRotation) renderblocks).rotation + adjX,
			-((RenderBlocksWithRotation) renderblocks).yRotation + adj, adjZ, 4, mossDirection_X, mossDirection_Y, mossDirection_Z, rot, hasMoss_X2 ? 1 : 0);

			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX, 0 - mossThickness, renderblocks.renderMinZ - mossThickness, renderblocks.renderMaxX,
			renderblocks.renderMaxY + mossThickness, renderblocks.renderMaxZ + mossThickness, 4, 0);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], x, y, z, 4, 0);
			renderblocks.renderMinX = minX;
			renderblocks.renderMaxX = maxX;
		}
		if (hasMoss_X2)
		{
			minX = renderblocks.renderMinX;
			maxX = renderblocks.renderMaxX;
			renderblocks.renderMinX = maxX;
			renderblocks.renderMaxX = maxX + mossThickness;

			boolean[][][] mosses = new boolean[3][3][3];
			for (int xx = -1; xx < 2; xx++)
			{
				for (int yy = -1; yy < 2; yy++)
				{
					for (int zz = -1; zz < 2; zz++)
					{
						int X = xx;// (int) (xx * mossDirection_X.xCoord + yy *
									// mossDirection_Y.xCoord + zz *
									// mossDirection_Z.xCoord);
						int Y = yy;// (int) (xx * mossDirection_X.yCoord + yy *
									// mossDirection_Y.yCoord + zz *
									// mossDirection_Z.yCoord);
						int Z = zz;// (int) (xx * mossDirection_X.zCoord + yy *
									// mossDirection_Y.zCoord + zz *
									// mossDirection_Z.zCoord);
						mosses[xx + 1][yy + 1][zz + 1] = renderblocks.blockAccess.getBlock(x + X + (int) Math.round(mossDirection_Xy.xCoord),
						y + Y + (int) Math.round(mossDirection_Xy.yCoord), z + Z + (int) Math.round(mossDirection_Xy.zCoord)) == TFCBlocks.moss;
					}
				}
			}
			int rot = 1;
			float adj = (float) Math.PI;
			float adjZ = (float) (Math.PI / 2d);
			float adjX = 0;
			if (mossDirection_Y.yCoord == 1)
			{
				adj = (float) Math.PI / 2f;
			}
			else if (mossDirection_Y.xCoord * mossDirection_Y.xCoord == 1)
			{
				adjZ = 0;// (float) -Math.PI;
				adjX = mossDirection_Y.xCoord == -1 ? 0 : (float) -Math.PI;
				adj = mossDirection_Y.xCoord == -1 ? 0 : (float) Math.PI;
			}
			int[] idTexRot = TFC_CoreRender.handleMossFace((RenderBlocksWithRotation) renderblocks, x + (int) Math.round(mossDirection_Xy.xCoord),
			y + (int) Math.round(mossDirection_Xy.yCoord), z + (int) Math.round(mossDirection_Xy.zCoord), mosses, -((RenderBlocksWithRotation) renderblocks).rotation + adjX,
			-((RenderBlocksWithRotation) renderblocks).yRotation + adj, adjZ, 4, mossDirection_X, mossDirection_Y, mossDirection_Z, rot, 2);

			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX, renderblocks.renderMinY - mossThickness, renderblocks.renderMinZ - mossThickness,
			renderblocks.renderMaxX, 0 + mossThickness, renderblocks.renderMaxZ + mossThickness, 4, 1);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], x, y, z, 4, 1);
			renderblocks.renderMinX = minX;
			renderblocks.renderMaxX = maxX;
		}
		if (hasMoss_z)
		{
			minZ = renderblocks.renderMinZ;
			maxZ = renderblocks.renderMaxZ;
			renderblocks.renderMinZ = minZ - mossThickness;
			renderblocks.renderMaxZ = minZ;
			// ((RenderBlocksWithRotation)
			// renderblocks).renderStandardBlock(TFCBlocks.moss, x, y, z);
			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX - mossThickness, renderblocks.renderMinY - mossThickness, renderblocks.renderMinZ,
			renderblocks.renderMaxX + mossThickness, renderblocks.renderMaxY + mossThickness, renderblocks.renderMaxZ, 3, 0);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(0, 0, x, y, z, 3, 0);
			renderblocks.renderMinZ = minZ;
			renderblocks.renderMaxZ = maxZ;
		}
		if (hasMoss_Z)
		{
			minZ = renderblocks.renderMinZ;
			maxZ = renderblocks.renderMaxZ;
			renderblocks.renderMinZ = maxZ;
			renderblocks.renderMaxZ = maxZ + mossThickness;
			// ((RenderBlocksWithRotation)
			// renderblocks).renderStandardBlock(TFCBlocks.moss, x, y, z);
			((RenderBlocksWithRotation) renderblocks).setRenderBounds(renderblocks.renderMinX - mossThickness, renderblocks.renderMinY - mossThickness, renderblocks.renderMinZ,
			renderblocks.renderMaxX + mossThickness, renderblocks.renderMaxY + mossThickness, renderblocks.renderMaxZ, 2, 0);
			((RenderBlocksWithRotation) renderblocks).renderMossFace(0, 0, x, y, z, 2, 0);
			renderblocks.renderMinZ = minZ;
			renderblocks.renderMaxZ = maxZ;
		}
		if (hasMoss_Z || hasMoss_z || hasMoss_Y || hasMoss_y || hasMoss_X || hasMoss_x || hasMoss_Z2 || hasMoss_z2 || hasMoss_X2 || hasMoss_x2)
		{
			((RenderBlocksWithRotation) renderblocks).renderStandardBlock(TFCBlocks.moss, x, y, z);
		}
		if (hasSnow && !(((BlockBranch) block).getSourceX() == 0 && ((BlockBranch) block).getSourceZ() == 0))
		{
			minZ = renderblocks.renderMinZ;
			maxZ = renderblocks.renderMaxZ;
			renderblocks.renderMinZ = minZ - snowThickness;
			renderblocks.renderMaxZ = minZ;
			((RenderBlocksWithRotation) renderblocks).renderStandardBlock(Blocks.snow, x, y, z);
			renderblocks.renderMinZ = minZ;
			renderblocks.renderMaxZ = maxZ;
		}
		// GL11.glEnable(GL11.GL_CULL_FACE);
		((RenderBlocksWithRotation) renderblocks).renderStandardBlock(branch, x, y, z);
		((RenderBlocksWithRotation) renderblocks).yRotation = 0;
		((RenderBlocksWithRotation) renderblocks).rotation = 0;
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
