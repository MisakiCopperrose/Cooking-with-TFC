package com.dunk.tfc.Render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Blocks.BlockTileRoof;
import com.dunk.tfc.Blocks.Devices.BlockSluice;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockFruitLeaves;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves2;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.FloraIndex;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Render.Blocks.RenderFlora;
import com.dunk.tfc.TileEntities.TEFruitLeaves;
import com.dunk.tfc.TileEntities.TEFruitTreeWood;
import com.dunk.tfc.TileEntities.TEPartial;
import com.dunk.tfc.TileEntities.TEWaterPlant;
import com.dunk.tfc.TileEntities.TEWorldItem;
import com.dunk.tfc.WorldGen.DataLayer;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TFC_CoreRender
{

	public static boolean renderBlockSlab(Block block, int x, int y, int z, RenderBlocks renderblocks)
	{
		TEPartial te = (TEPartial) renderblocks.blockAccess.getTileEntity(x, y, z);
		// int md = renderblocks.blockAccess.getBlockMetadata(x, y, z);

		if (te.typeID <= 0)
			return false;

		int type = te.typeID;
		int meta = te.metaID;
		Block b = Block.getBlockById(type);
		IIcon tex = b.getIcon(0, meta);

		// if(!breaking)
		// ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(),
		// ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));

		long extraX = (te.extraData) & 0xf;
		long extraY = (te.extraData >> 4) & 0xf;
		long extraZ = (te.extraData >> 8) & 0xf;
		long extraX2 = (te.extraData >> 12) & 0xf;
		long extraY2 = (te.extraData >> 16) & 0xf;
		long extraZ2 = (te.extraData >> 20) & 0xf;

		float div = 1f / 8;

		renderblocks.setRenderBounds(0.0F + (div * extraX), 0.0F + (div * extraY), 0.0F + (div * extraZ),
				1.0F - (div * extraX2), 1 - (div * extraY2), 1.0F - (div * extraZ2));

		// This is the old ore code that I experimented with
		boolean breaking = renderblocks.overrideBlockTexture != null;
		IIcon over = renderblocks.overrideBlockTexture;
		if (!breaking && (b == TFCBlocks.ore || b == TFCBlocks.ore1b || b == TFCBlocks.ore2 || b == TFCBlocks.ore3))
		{
			// TFCBiome biome = (TFCBiome)
			// renderblocks.blockAccess.getBiomeGenForCoords(par2, par4);
			renderblocks.overrideBlockTexture = getRockTexture(Minecraft.getMinecraft().theWorld, x, y, z);
			renderblocks.renderStandardBlock(block, x, y, z);
			renderblocks.overrideBlockTexture = over;
		}

		if (!breaking)
			renderblocks.overrideBlockTexture = tex;

		renderblocks.renderStandardBlock(block, x, y, z);
		renderblocks.overrideBlockTexture = over;

		return true;
	}

	private static RenderBlocksLightCache renderer;

	public static boolean renderBlockStairs(Block block, int x, int y, int z, RenderBlocks renderblocks)
	{
		if (renderer == null)
			renderer = new RenderBlocksLightCache(renderblocks);
		else
			renderer.update(renderblocks);

		// Capture full block lighting data...
		renderer.disableRender();
		renderer.setRenderAllFaces(true);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderAllFaces(false);
		renderer.enableRender();

		/*
		 * int meta = renderblocks.blockAccess.getBlockMetadata(x, y, z); long
		 * rvmeta = meta & 7; float var7 = 0.0F; float var8 = 0.5F; float var9 =
		 * 0.5F; float var10 = 1.0F;
		 * 
		 * if ((meta & 8) != 0) { var7 = 0.5F; var8 = 1.0F; var9 = 0.0F; var10 =
		 * 0.5F; }
		 */

		TEPartial te = (TEPartial) renderblocks.blockAccess.getTileEntity(x, y, z);
		if (te.typeID <= 0)
			return false;

		long rvmeta = te.extraData;
		int type = te.typeID;
		int temeta = te.metaID;
		IIcon myTexture = renderblocks.overrideBlockTexture == null ? Block.getBlockById(type).getIcon(0, temeta)
				: renderblocks.overrideBlockTexture;

		if ((rvmeta & 1) == 0)
		{
			renderer.setRenderBounds(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}
		if ((rvmeta & 2) == 0)
		{
			renderer.setRenderBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}
		if ((rvmeta & 4) == 0)
		{
			renderer.setRenderBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}
		if ((rvmeta & 8) == 0)
		{
			renderer.setRenderBounds(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}

		if ((rvmeta & 16) == 0)
		{
			renderer.setRenderBounds(0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 1.0F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}
		if ((rvmeta & 32) == 0)
		{
			renderer.setRenderBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}
		if ((rvmeta & 64) == 0)
		{
			renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}
		if ((rvmeta & 128) == 0)
		{
			renderer.setRenderBounds(0.5F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			renderer.renderCachedBlock(block, x, y, z, myTexture);
		}

		renderer.clearOverrideBlockTexture();
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return true;
	}

	public static boolean renderSulfur(Block block, int x, int y, int z, RenderBlocks renderblocks)
	{
		IBlockAccess world = renderblocks.blockAccess;
		if (world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH))
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		if (world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH))
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		if (world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.EAST))
		{
			renderblocks.setRenderBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		if (world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.WEST))
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		if (world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN))
		{
			renderblocks.setRenderBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP))
		{
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			renderblocks.renderStandardBlock(block, x, y, z);
		}

		return true;
	}

	private static boolean isSnow(IBlockAccess access, int x, int y, int z)
	{
		if(access.getBlock(x, y-1, z) instanceof BlockTileRoof || access.getBlock(x, y-1, z) instanceof BlockBranch)
		{
			return false;
		}
		Material material = access.getBlock(x, y, z).getMaterial();
		// Also, world items could be snowy. So we want that to be possible.
		return material == Material.snow || material == Material.craftedSnow || (access.getBlock(x, y, z)
				.equals(TFCBlocks.worldItem) && ((TEWorldItem) (access.getTileEntity(x, y, z))).snowLevel > 0);
	}

	private static int getDrift(IBlockAccess access, int x, int y, int z)
	{
		
		if (isSnow(access, x, y, z))
		{
			if (access.getBlock(x, y, z).equals(TFCBlocks.snow))
			{
				return access.getBlockMetadata(x, y, z);
			}
			else if (access.getBlock(x, y, z).equals(TFCBlocks.worldItem))
			{
				return ((TEWorldItem) (access.getTileEntity(x, y, z))).snowLevel - 1;
			}
		}
		return -1;
	}

	public static boolean renderSnow(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		if (renderblocks.blockAccess.getBlock(i, j - 1, k) instanceof BlockBranch)
		{
			return true;
		}
		if (isSnow(renderblocks.blockAccess, i, j, k))
		{
			int meta = getDrift(renderblocks.blockAccess, i, j, k);
			if (meta == -1)
			{
				return false;
			}

			float drift = 0.125F + (meta * 0.125F);

			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, drift, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);

			float adjHeight = 0f;
			boolean extra = false;
			boolean renderFaces = renderblocks.renderAllFaces;
			renderblocks.renderAllFaces = true;

			// That means it's something else, like a stick or a rock.
			if (block != TFCBlocks.snow)
			{
				// We want to render something to make it stand out as something
				// else.
				renderblocks.setRenderBounds(0.3F, drift, 0.3F, 0.7F, drift + 0.0667f, 0.7F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.4F, drift + 0.0667f, 0.4F, 0.6F, drift + 0.1f, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);

			}
			// -Z
			if (isSnow(renderblocks.blockAccess, i, j + 1, k - 1))
			{
				extra = (isSnow(renderblocks.blockAccess, i + 1, j, k - 1) && isSnow(renderblocks.blockAccess, i + 1, j,
						k));

				adjHeight = 0.125F + ((renderblocks.blockAccess.getBlockMetadata(i, j + 1, k - 1) & 7) * 0.125F);
				// renderblocks.setRenderBounds(1.0F, 1.0F, 0.000F, 0.0F, 0.0,
				// 0.0625F);
				renderblocks.setRenderBounds(0.0F, 0.0, 0.000F, 1.0F + (extra ? 0.0625F : 0F), 1.0F + adjHeight,
						0.0625F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			// +Z
			if (isSnow(renderblocks.blockAccess, i, j + 1, k + 1))
			{
				extra = (isSnow(renderblocks.blockAccess, i - 1, j, k + 1) && isSnow(renderblocks.blockAccess, i - 1, j,
						k));

				adjHeight = 0.125F + ((renderblocks.blockAccess.getBlockMetadata(i, j + 1, k + 1) & 7) * 0.125F);
				// renderblocks.setRenderBounds(1.0F, 1.0F, 1.0F, 0.0F, 0.0F,
				// 0.9375F);
				renderblocks.setRenderBounds(0.0F - (extra ? 0.0625F : 0F), 0.0F, 0.9375F, 1.0F, 1.0F + adjHeight,
						1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			// +X
			if (isSnow(renderblocks.blockAccess, i + 1, j + 1, k))
			{
				extra = (isSnow(renderblocks.blockAccess, i + 1, j, k + 1) && isSnow(renderblocks.blockAccess, i, j,
						k + 1));

				adjHeight = 0.125F + (0.125F * (renderblocks.blockAccess.getBlockMetadata(i + 1, j + 1, k) & 7));
				// renderblocks.setRenderBounds(0.9375F, 1.0F, 1.0F, 1.0F, 0.0F,
				// 0.0F);
				renderblocks.setRenderBounds(0.9375F, 0.0F, 0.0F, 1.0F, 1.0F + adjHeight,
						1.0F + (extra ? 0.0625F : 0F));
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			// -X
			if (isSnow(renderblocks.blockAccess, i - 1, j + 1, k))
			{
				extra = (isSnow(renderblocks.blockAccess, i - 1, j, k - 1) && isSnow(renderblocks.blockAccess, i, j,
						k - 1));

				adjHeight = 0.125F + ((renderblocks.blockAccess.getBlockMetadata(i - 1, j + 1, k) & 7) * 0.125F);
				// renderblocks.setRenderBounds(0.0625F, 1.0F, 1.0F, 0.0F, 0.0F,
				// 0.0F);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F - (extra ? 0.0625F : 0F), 0.0625F, 1.0F + adjHeight,
						1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}

			float snowStartX = 0F;
			float snowStartZ = 0F;

			GL11.glPushMatrix();

			// TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID,
			// "textures/blocks/snow.png"));

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);

			GL11.glTranslatef(i, j, k);
			// Tessellator.instance.draw();
			// TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID,
			// "textures/blocks/snow.png"));

			renderblocks.setOverrideBlockTexture(block.getBlockTextureFromSide(1));

			/*
			 * Tessellator.instance.addVertexWithUV(i, j, k, 0, 0);
			 * Tessellator.instance.addVertexWithUV(i, j, k+1, 0, 8);
			 * Tessellator.instance.addVertexWithUV(i, j+1, k, 8, 0);
			 * Tessellator.instance.addVertexWithUV(i, j+1, k+1, 8, 8);
			 */
			renderblocks.clearOverrideBlockTexture();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();

			GL11.glPopMatrix();
			// TFC_Core.bindTexture(new ResourceLocation(null));
			renderblocks.renderAllFaces = renderFaces;
			return true;
		}
		return false;
	}

	public static int[] getMossId(int i, int j, int k, boolean localTop, boolean localRight, boolean localBottom,
			boolean localLeft, boolean TR, boolean BR, boolean BL, boolean TL)
	{
		int id = 0;
		int texRot = 0;
		if (!localBottom && !localLeft && !localRight && !localTop)
		{
			id = 9;
			texRot = Math.abs(i + j + k) % 8;
		}
		else if (!localBottom && !localLeft && !localRight && localTop)
		{
			id = 1;
			texRot = 0;
		}
		else if (!localBottom && !localLeft && localRight && !localTop)
		{
			id = 1;
			texRot = 1;
		}
		else if (localBottom && !localLeft && !localRight && !localTop)
		{
			id = 1;
			texRot = 2;
		}
		else if (!localBottom && localLeft && !localRight && !localTop)
		{
			id = 1;
			texRot = 3;
		}
		else if (localBottom && localTop && !localRight && !localLeft)
		{
			id = 2;
		}
		else if (!localBottom && !localTop && localRight && localLeft)
		{
			id = 2;
			texRot = 1;
		}
		else if (!localBottom && !localLeft && localRight && localTop)
		{
			texRot = 3;
			if (TR)
			{
				id = 10;

			}
			else
			{
				id = 12;
			}
		}
		else if (localBottom && !localLeft && !localTop && localRight)
		{
			texRot = 0;
			if (BR)
			{
				id = 10;

			}
			else
			{
				id = 12;
			}
		}
		else if (localBottom && localLeft && !localTop && !localRight)
		{
			texRot = 1;
			if (BL)
			{
				id = 10;

			}
			else
			{
				id = 12;
			}
		}
		else if (!localBottom && localLeft && localTop && !localRight)
		{
			texRot = 2;
			if (TL)
			{
				id = 10;

			}
			else
			{
				id = 12;
			}
		}
		else if (localBottom && localRight && localLeft && !localTop)
		{
			if (!BL && !BR)
			{
				id = 8;
				texRot = 2;
			}
			else if (BL && !BR)
			{
				id = 13;
				texRot = 5;
			}
			else if (!BL && BR)
			{
				id = 13;
				texRot = 1;
			}
			else if (BL && BR)
			{
				id = 3;
				texRot = 1;
			}
		}
		else if (localBottom && localRight && localTop && !localLeft)
		{
			if (!TR && !BR)
			{
				id = 8;
				texRot = 1;
			}
			else if (TR && !BR)
			{
				id = 13;
				texRot = 0;
			}
			else if (!TR && BR)
			{
				id = 13;
				texRot = 4;
			}
			else if (TR && BR)
			{
				id = 3;
				texRot = 0;
			}
		}
		else if (!localBottom && localRight && localTop && localLeft)
		{
			if (!TL && !TR)
			{
				id = 8;
				texRot = 0;
			}
			else if (TL && !TR)
			{
				id = 13;
				texRot = 3;
			}
			else if (!TL && TR)
			{
				id = 13;
				texRot = 7;
			}
			else if (TL && TR)
			{
				id = 3;
				texRot = 3;
			}
		}
		else if (localBottom && !localRight && localTop && localLeft)
		{
			if (!BL && !TL)
			{
				id = 8;
				texRot = 3;
			}
			else if (BL && !TL)
			{
				id = 13;
				texRot = 2;
			}
			else if (!BL && TL)
			{
				id = 13;
				texRot = 6;
			}
			else if (BL && TL)
			{
				id = 3;
				texRot = 2;
			}
		}
		else if (localTop && localBottom && localLeft && localRight)
		{
			if (!TR && !TL && !BL && !BR)
			{
				id = 7;
			}
			else if (TR && !TL && BL && !BR)
			{
				id = 5;
				texRot = 0;
			}
			else if (!TR && TL && !BL && BR)
			{
				id = 5;
				texRot = 1;
			}
			else if (TR && !TL && !BL && !BR)
			{
				id = 6;
				texRot = 2;
			}
			else if (!TR && TL && !BL && !BR)
			{
				id = 6;
				texRot = 1;
			}
			else if (!TR && !TL && BL && !BR)
			{
				id = 6;
				texRot = 0;
			}
			else if (!TR && !TL && !BL && BR)
			{
				id = 6;
				texRot = 3;
			}
			else if (TR && !TL && BL && BR)
			{
				id = 4;
				texRot = 0;
			}
			else if (TR && TL && !BL && BR)
			{
				id = 4;
				texRot = 3;
			}
			else if (TR && TL && BL && !BR)
			{
				id = 4;
				texRot = 2;
			}
			else if (!TR && TL && BL && BR)
			{
				id = 4;
				texRot = 1;
			}
			else if (TL && TR && !BR && !BL)
			{
				id = 11;
				texRot = 2;
			}
			else if (!TL && TR && BR && !BL)
			{
				id = 11;
				texRot = 3;
			}
			else if (!TL && !TR && BR && BL)
			{
				id = 11;
				texRot = 0;
			}
			else if (TL && !TR && !BR && BL)
			{
				id = 11;
				texRot = 1;
			}
		}
		return new int[] { id, texRot };
	}

	public static boolean ssol(RenderBlocks renderblocks, double i, double j, double k, Vec3 v, ForgeDirection d)
	{
		return ssol(renderblocks, (int) i + v.xCoord, (int) j + v.yCoord, (int) k + v.zCoord, d);
	}

	public static boolean ssol(RenderBlocks renderblocks, double i, double j, double k, ForgeDirection d)
	{
		return ssol(renderblocks, (int) i, (int) j, (int) k, d);
	}

	public static boolean ssol(RenderBlocks renderblocks, int i, int j, int k, ForgeDirection d)
	{
		return renderblocks.blockAccess.isSideSolid(i, j, k, d,
				true) || renderblocks.blockAccess.getBlock(i, j, k) instanceof BlockBranch;
	}

	public static boolean air(RenderBlocks renderblocks, int i, int j, int k)
	{
		return renderblocks.blockAccess.isAirBlock(i, j, k);
	}

	public static Vec3 round(Vec3 v)
	{
		v.xCoord = Math.round(v.xCoord);
		v.yCoord = Math.round(v.yCoord);
		v.zCoord = Math.round(v.zCoord);
		return v;
	}

	public static ForgeDirection fd(Vec3 dir)
	{
		if (dir.xCoord == 1 && dir.yCoord == 0 && dir.zCoord == 0)
		{
			return ForgeDirection.EAST;
		}
		if (dir.xCoord == -1 && dir.yCoord == 0 && dir.zCoord == 0)
		{
			return ForgeDirection.WEST;
		}
		if (dir.xCoord == 0 && dir.yCoord == 1 && dir.zCoord == 0)
		{
			return ForgeDirection.UP;
		}
		if (dir.xCoord == 0 && dir.yCoord == -1 && dir.zCoord == 0)
		{
			return ForgeDirection.DOWN;
		}
		if (dir.xCoord == 0 && dir.yCoord == 0 && dir.zCoord == 1)
		{
			return ForgeDirection.SOUTH;
		}
		if (dir.xCoord == 0 && dir.yCoord == 0 && dir.zCoord == -1)
		{
			return ForgeDirection.NORTH;
		}
		return ForgeDirection.UNKNOWN;
	}

	public static boolean ms(boolean[][][] mosses, Vec3 v)
	{
		if (v.xCoord > 1 || v.xCoord < -1 || v.yCoord > 1 || v.yCoord < -1 || v.zCoord > 1 || v.zCoord < -1)
		{
			return false;
		}
		return mosses[(int) (v.xCoord + 1)][(int) (v.yCoord + 1)][(int) (v.zCoord + 1)];
	}

	public static boolean ms(boolean[][][] mosses, int x, int y, int z)
	{
		return mosses[(int) (x + 1)][(int) (y + 1)][(int) (z + 1)];
	}

	private static Vec3 add(Vec3 a, Vec3 b)
	{
		return a.addVector(b.xCoord, b.yCoord, b.zCoord);
	}

	public static int[] handleMossFace(RenderBlocksWithRotation renderblocks, int i, int j, int k, boolean[][][] mosses,
			float rotX, float rotY, float rotZ, int faceId, Vec3 xUnit, Vec3 yUnit, Vec3 zUnit, int mossRotation,
			int branch)
	{

		// We need to establish what kind of connectedness we're going for.
		boolean localLeft = false, localRight = false, localTop = false, localBottom = false;
		boolean localMidLeft = false, localMidRight = false, localMidTop = false, localMidBottom = false;
		boolean localUpLeft = false, localUpRight = false, localUpTop = false, localUpBottom = false;
		boolean localDownLeft = false, localDownRight = false, localDownTop = false, localDownBottom = false;
		boolean localTR = false, localTL = false, localBL = false, localBR = false;
		// We establish what kind of connected ness we have
		// Connected Left = renderblocks.blockAccess.isSideSolid(i, j -

		// We are connected to the left in a few circumstances: a) the
		// adjacent block is moss. This is the easiest case
		// b) the block below the adjacent block is moss and the
		// adjacent block is not solid on the bottom or right and the
		// below block is not solid on the left
		// c) the block above the adjacent block is moss, the adjacent
		// block is solid on the right and the above block is not solid
		// on the bottom or left.
		Vec3 zero = Vec3.createVectorHelper(0, 0, 0);
		Vec3 dirW = Vec3.createVectorHelper(-xUnit.xCoord, -xUnit.yCoord, -xUnit.zCoord);
		Vec3 dirU = Vec3.createVectorHelper(yUnit.xCoord, yUnit.yCoord, yUnit.zCoord);
		Vec3 dirD = Vec3.createVectorHelper(-yUnit.xCoord, -yUnit.yCoord, -yUnit.zCoord);
		Vec3 dirE = Vec3.createVectorHelper(xUnit.xCoord, xUnit.yCoord, xUnit.zCoord);
		Vec3 dirN = Vec3.createVectorHelper(-zUnit.xCoord, -zUnit.yCoord, -zUnit.zCoord);
		Vec3 dirS = Vec3.createVectorHelper(zUnit.xCoord, zUnit.yCoord, zUnit.zCoord);
		Vec3 dirDW = add(dirD, dirW);// Vec3.createVectorHelper(-1, -1, 0);
		Vec3 dirUW = add(dirU, dirW);// Vec3.createVectorHelper(-1, 1, 0);
		Vec3 dirDE = add(dirD, dirE);// Vec3.createVectorHelper(1, -1, 0);
		Vec3 dirUE = add(dirU, dirE);// Vec3.createVectorHelper(1, 1, 0);
		Vec3 dirDN = add(dirD, dirN);// Vec3.createVectorHelper(0, -1, -1);
		Vec3 dirDS = add(dirD, dirS);// Vec3.createVectorHelper(0, -1, 1);
		Vec3 dirUN = add(dirU, dirN);// Vec3.createVectorHelper(0, 1, -1);
		Vec3 dirUS = add(dirU, dirS);// Vec3.createVectorHelper(0, 1, 1);
		Vec3 dirNW = add(dirN, dirW);// Vec3.createVectorHelper(-1, 0, -1);
		Vec3 dirNE = add(dirN, dirE);// Vec3.createVectorHelper(1, 0, -1);
		Vec3 dirSW = add(dirS, dirW);// Vec3.createVectorHelper(-1, 0, 1);
		Vec3 dirSE = add(dirS, dirE);// Vec3.createVectorHelper(1, 0, 1);
		Vec3 dirDNW = add(dirD, dirNW);// Vec3.createVectorHelper(-1, -1, -1);
		Vec3 dirDNE = add(dirD, dirNE);// Vec3.createVectorHelper(1, -1, -1);
		Vec3 dirDSW = add(dirD, dirSW);// Vec3.createVectorHelper(-1, -1, 1);
		Vec3 dirDSE = add(dirD, dirSE);// Vec3.createVectorHelper(1, -1, 1);
		Vec3 dirDDNW = add(dirD, dirDNW);// Vec3.createVectorHelper(-1, -2, -1);
		Vec3 dirDDNE = add(dirD, dirDNE);// Vec3.createVectorHelper(1, -2, -1);
		Vec3 dirDDSW = add(dirD, dirDSW);// Vec3.createVectorHelper(-1, -2, 1);
		Vec3 dirDDSE = add(dirD, dirDSE);// Vec3.createVectorHelper(1, -2, 1);
		Vec3 dirUNW = add(dirU, dirNW);// Vec3.createVectorHelper(-1, 1, -1);
		Vec3 dirUNE = add(dirU, dirNE);// Vec3.createVectorHelper(1, 1, -1);
		Vec3 dirUSW = add(dirU, dirSW);// Vec3.createVectorHelper(-1, 1, 1);
		Vec3 dirUSE = add(dirU, dirSE);// Vec3.createVectorHelper(1, 1, 1);

		dirW.rotateAroundX(rotX);
		dirU.rotateAroundX(rotX);
		dirDW.rotateAroundX(rotX);
		dirD.rotateAroundX(rotX);
		dirUW.rotateAroundX(rotX);
		dirE.rotateAroundX(rotX);
		dirDE.rotateAroundX(rotX);
		dirUE.rotateAroundX(rotX);
		dirN.rotateAroundX(rotX);
		dirS.rotateAroundX(rotX);
		dirDN.rotateAroundX(rotX);
		dirDS.rotateAroundX(rotX);
		dirUN.rotateAroundX(rotX);
		dirUS.rotateAroundX(rotX);
		dirNW.rotateAroundX(rotX);
		dirNE.rotateAroundX(rotX);
		dirSW.rotateAroundX(rotX);
		dirSE.rotateAroundX(rotX);
		dirDNW.rotateAroundX(rotX);
		dirDNE.rotateAroundX(rotX);
		dirDSW.rotateAroundX(rotX);
		dirDSE.rotateAroundX(rotX);
		dirDDNW.rotateAroundX(rotX);
		dirDDNE.rotateAroundX(rotX);
		dirDDSW.rotateAroundX(rotX);
		dirDDSE.rotateAroundX(rotX);
		dirUNW.rotateAroundX(rotX);
		dirUNE.rotateAroundX(rotX);
		dirUSW.rotateAroundX(rotX);
		dirUSE.rotateAroundX(rotX);

		dirW.rotateAroundY(rotY);
		dirU.rotateAroundY(rotY);
		dirDW.rotateAroundY(rotY);
		dirD.rotateAroundY(rotY);
		dirUW.rotateAroundY(rotY);
		dirE.rotateAroundY(rotY);
		dirDE.rotateAroundY(rotY);
		dirUE.rotateAroundY(rotY);
		dirN.rotateAroundY(rotY);
		dirS.rotateAroundY(rotY);
		dirDN.rotateAroundY(rotY);
		dirDS.rotateAroundY(rotY);
		dirUN.rotateAroundY(rotY);
		dirUS.rotateAroundY(rotY);
		dirNW.rotateAroundY(rotY);
		dirNE.rotateAroundY(rotY);
		dirSW.rotateAroundY(rotY);
		dirSE.rotateAroundY(rotY);
		dirDNW.rotateAroundY(rotY);
		dirDNE.rotateAroundY(rotY);
		dirDSW.rotateAroundY(rotY);
		dirDSE.rotateAroundY(rotY);
		dirDDNW.rotateAroundY(rotY);
		dirDDNE.rotateAroundY(rotY);
		dirDDSW.rotateAroundY(rotY);
		dirDDSE.rotateAroundY(rotY);
		dirUNW.rotateAroundY(rotY);
		dirUNE.rotateAroundY(rotY);
		dirUSW.rotateAroundY(rotY);
		dirUSE.rotateAroundY(rotY);

		dirW.rotateAroundZ(rotZ);
		dirU.rotateAroundZ(rotZ);
		dirDW.rotateAroundZ(rotZ);
		dirD.rotateAroundZ(rotZ);
		dirUW.rotateAroundZ(rotZ);
		dirE.rotateAroundZ(rotZ);
		dirDE.rotateAroundZ(rotZ);
		dirUE.rotateAroundZ(rotZ);
		dirN.rotateAroundZ(rotZ);
		dirS.rotateAroundZ(rotZ);
		dirDN.rotateAroundZ(rotZ);
		dirDS.rotateAroundZ(rotZ);
		dirUN.rotateAroundZ(rotZ);
		dirUS.rotateAroundZ(rotZ);
		dirNW.rotateAroundZ(rotZ);
		dirNE.rotateAroundZ(rotZ);
		dirSW.rotateAroundZ(rotZ);
		dirSE.rotateAroundZ(rotZ);
		dirDNW.rotateAroundZ(rotZ);
		dirDNE.rotateAroundZ(rotZ);
		dirDSW.rotateAroundZ(rotZ);
		dirDSE.rotateAroundZ(rotZ);
		dirDDNW.rotateAroundZ(rotZ);
		dirDDNE.rotateAroundZ(rotZ);
		dirDDSW.rotateAroundZ(rotZ);
		dirDDSE.rotateAroundZ(rotZ);
		dirUNW.rotateAroundZ(rotZ);
		dirUNE.rotateAroundZ(rotZ);
		dirUSW.rotateAroundZ(rotZ);
		dirUSE.rotateAroundZ(rotZ);

		dirW = round(dirW);
		dirU = round(dirU);
		dirDW = round(dirDW);
		dirD = round(dirD);
		dirUW = round(dirUW);
		dirE = round(dirE);
		dirDE = round(dirDE);
		dirUE = round(dirUE);
		dirN = round(dirN);
		dirS = round(dirS);
		dirDN = round(dirDN);
		dirDS = round(dirDS);
		dirUN = round(dirUN);
		dirUS = round(dirUS);
		dirNW = round(dirNW);
		dirNE = round(dirNE);
		dirSW = round(dirSW);
		dirSE = round(dirSE);
		dirDNW = round(dirDNW);
		dirDNE = round(dirDNE);
		dirDSW = round(dirDSW);
		dirDSE = round(dirDSE);
		dirDDNW = round(dirDDNW);
		dirDDNE = round(dirDDNE);
		dirDDSW = round(dirDDSW);
		dirDDSE = round(dirDDSE);
		dirUNW = round(dirUNW);
		dirUNE = round(dirUNE);
		dirUSW = round(dirUSW);
		dirUSE = round(dirUSE);

		// System.out.println("N: " + dirN);
		// System.out.println("U: " + dirU);

		boolean upFace = ssol(renderblocks, i + dirD.xCoord, j + dirD.yCoord, k + dirD.zCoord,
				fd(dirU)) && fd(dirU) != ForgeDirection.DOWN;
		boolean southFace = ssol(renderblocks, i + dirS.xCoord, j + dirS.yCoord, k + dirS.zCoord,
				fd(dirN)) && fd(dirN) != ForgeDirection.DOWN;
		boolean northFace = ssol(renderblocks, i + dirN.xCoord, j + dirN.yCoord, k + dirN.zCoord,
				fd(dirS)) && fd(dirS) != ForgeDirection.DOWN;
		boolean westFace = ssol(renderblocks, i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
				fd(dirE)) && fd(dirE) != ForgeDirection.DOWN;
		boolean eastFace = ssol(renderblocks, i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
				fd(dirW)) && fd(dirW) != ForgeDirection.DOWN;
		int count = 0;

		if (count > 0)
		{
			i += dirS.xCoord;
			j += dirS.yCoord;
			k += dirS.zCoord;
		}

		localMidLeft |= ms(mosses, (count > 0 ? dirSW : dirW)) && ssol(renderblocks, i + dirDW.xCoord, j + dirDW.yCoord,
				k + dirDW.zCoord, fd(dirU));
		localDownLeft |= (ms(mosses,
				(count > 0 ? dirDSW : dirDW)) && !ssol(renderblocks, i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
						fd(dirD)) && !westFace && ssol(renderblocks, i + dirD.xCoord, j + dirD.yCoord, k + dirD.zCoord,
								fd(dirW)) && fd(dirW) != ForgeDirection.DOWN);
		localUpLeft |= (ms(mosses,
				(count > 0 ? dirUSW : dirUW)) && westFace && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
						k + dirU.zCoord, fd(dirD)) && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
								k + dirU.zCoord, fd(dirW)));
		localLeft |= localMidLeft || localDownLeft || westFace;

		localMidRight |= ms(mosses, (count > 0 ? dirSE : dirE)) && ssol(renderblocks, i + dirDE.xCoord,
				j + dirDE.yCoord, k + dirDE.zCoord, fd(dirU));
		localDownRight |= (ms(mosses,
				(count > 0 ? dirDSE : dirDE)) && !ssol(renderblocks, i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
						fd(dirD)) && !eastFace && ssol(renderblocks, i + dirD.xCoord, j + dirD.yCoord, k + dirD.zCoord,
								fd(dirE)) && fd(dirE) != ForgeDirection.DOWN);
		localUpRight |= (ms(mosses,
				(count > 0 ? dirUSE : dirUE)) && eastFace && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
						k + dirU.zCoord, fd(dirD)) && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
								k + dirU.zCoord, fd(dirE)));
		localRight |= localMidRight || localDownRight || eastFace;

		localMidTop |= (ms(mosses,
				(count > 0 ? zero
						: dirN)) || (branch == 1 && renderblocks.blockAccess.getBlock(i + (int) dirDN.xCoord,
								j + (int) dirDN.yCoord, k + (int) dirDN.zCoord) instanceof BlockBranch)) && ssol(
										renderblocks, i + dirDN.xCoord, j + dirDN.yCoord, k + dirDN.zCoord, fd(dirU));
		localDownTop |= (ms(mosses,
				(count > 0 ? dirD : dirDN)) && !ssol(renderblocks, i + dirN.xCoord, j + dirN.yCoord, k + dirN.zCoord,
						fd(dirD)) && !northFace && ssol(renderblocks, i + dirD.xCoord, j + dirD.yCoord, k + dirD.zCoord,
								fd(dirN)) && fd(dirN) != ForgeDirection.DOWN);
		localUpTop |= (ms(mosses,
				(count > 0 ? dirU : dirUN)) && northFace && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
						k + dirU.zCoord, fd(dirD)) && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
								k + dirU.zCoord, fd(dirN)));
		localTop |= localMidTop || localDownTop || northFace;

		localMidBottom |= (ms(mosses, dirS) || (branch == 2)) && ssol(renderblocks, i + dirDS.xCoord, j + dirDS.yCoord,
				k + dirDS.zCoord, fd(dirU));
		localDownBottom |= (ms(mosses,
				dirDS) && !ssol(renderblocks, i + dirS.xCoord, j + dirS.yCoord, k + dirS.zCoord,
						fd(dirD)) && !southFace && ssol(renderblocks, i + dirD.xCoord, j + dirD.yCoord, k + dirD.zCoord,
								fd(dirS)) && fd(dirS) != ForgeDirection.DOWN);
		localUpBottom |= (ms(mosses, dirUS) && southFace && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord,
				k + dirU.zCoord,
				fd(dirD)) && !ssol(renderblocks, i + dirU.xCoord, j + dirU.yCoord, k + dirU.zCoord, fd(dirS)));
		localBottom |= localMidBottom || localDownBottom || southFace;

		// This is complicated!!!
		// If the NW block is in the middle, we care about all levels, so we
		// check if the adjacents are down and valid, mid, or up and valid
		localTL |= ((ms(mosses, dirNW) && ssol(renderblocks, i + dirDNW.xCoord, j + dirDNW.yCoord, k + dirDNW.zCoord,
				fd(dirU))) && (localMidLeft || (localDownLeft && ssol(renderblocks, i + dirDNW.xCoord,
						j + dirDNW.yCoord, k + dirDNW.zCoord, fd(dirS))) || (localUpLeft && ssol(renderblocks,
								i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
								fd(dirN)))) && (localMidTop || (localDownTop && ssol(renderblocks, i + dirDNW.xCoord,
										j + dirDNW.yCoord, k + dirDNW.zCoord,
										fd(dirE))) || (localUpTop && ssol(renderblocks, i + dirN.xCoord,
												j + dirN.yCoord, k + dirN.zCoord, fd(dirW)))));
		// If the NW block is down and there's nothing obstructing it above
		// and
		// the adjacents are either level or below
		localTL |= ((ms(mosses,
				dirDNW) && !ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord, fd(dirD)) && !ssol(
						renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord, fd(dirE)) && !ssol(
								renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord,
								fd(dirS))) && ((localMidLeft && ssol(renderblocks, i + dirDW.xCoord, j + dirDW.yCoord,
										k + dirDW.zCoord, fd(dirN))) || (localDownLeft && ssol(renderblocks,
												i + dirDDNW.xCoord, j + dirDDNW.yCoord, k + dirDDNW.zCoord,
												fd(dirU)))) && ((localMidTop && ssol(renderblocks, i + dirDN.xCoord,
														j + dirDN.yCoord, k + dirDN.zCoord,
														fd(dirW))) || (localDownTop)));
		// If the NW block is up and there's nothing obstructing it and the
		// adjacents are either level or above
		localTL |= ((ms(mosses, dirUNW) && ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord,
				fd(dirU))) && ((localMidLeft && !ssol(renderblocks, i + dirUW.xCoord, j + dirUW.yCoord,
						k + dirUW.zCoord, fd(dirN)) && !ssol(renderblocks, i + dirUW.xCoord, j + dirUW.yCoord,
								k + dirUW.zCoord, fd(dirD)) && ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord,
										k + dirNW.zCoord,
										fd(dirS))) || localUpLeft) && ((localMidTop && !ssol(renderblocks,
												i + dirUN.xCoord, j + dirUN.yCoord, k + dirUN.zCoord,
												fd(dirW)) && !ssol(renderblocks, i + dirUN.xCoord, j + dirUN.yCoord,
														k + dirUN.zCoord, fd(dirD)) && ssol(renderblocks,
																i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord,
																fd(dirE))) || localUpTop));
		localTL |= (ms(mosses, dirW)) && ssol(renderblocks, i + dirN.xCoord, j + dirN.yCoord, k + dirN.zCoord,
				fd(dirS)) && ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord, fd(dirS));
		localTL |= localDownTop && localDownLeft && !ssol(renderblocks, i + dirDNW.xCoord, j + dirDNW.yCoord,
				k + dirDNW.zCoord,
				fd(dirS)) && !ssol(renderblocks, i + dirDNW.xCoord, j + dirDNW.yCoord, k + dirDNW.zCoord, fd(dirE));
		localTL |= localTop && ssol(renderblocks, i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
				fd(dirE)) && ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord, fd(dirE));
		localTL |= localMidTop && localDownLeft && (ms(mosses, dirDNW)) && ssol(renderblocks, i + dirDN.xCoord,
				j + dirDN.yCoord, k + dirDN.zCoord, fd(dirW));
		localTL |= localMidLeft && localDownTop && (ms(mosses, dirDNW)) && ssol(renderblocks, i + dirDW.xCoord,
				j + dirDW.yCoord, k + dirDW.zCoord, fd(dirN));
		localTL |= (westFace || localMidLeft) && (northFace || localMidTop) && (ms(mosses,
				dirNW)) && (ssol(renderblocks, i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
						fd(dirN)) || localMidLeft) && (ssol(renderblocks, i + dirN.xCoord, j + dirN.yCoord,
								k + dirN.zCoord, fd(dirW)) || localMidTop);
		localTL |= localMidLeft && localMidTop && ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord,
				k + dirNW.zCoord,
				fd(dirW)) && ssol(renderblocks, i + dirNW.xCoord, j + dirNW.yCoord, k + dirNW.zCoord, fd(dirS));
		localTL |= westFace && localDownTop && ms(mosses, dirDNW) && ms(mosses, dirNW) && ssol(renderblocks,
				i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
				fd(dirN)) && ssol(renderblocks, i + dirDW.xCoord, j + dirDW.yCoord, k + dirDW.zCoord, fd(dirN));

		// This is complicated!!!
		// If the NE block is in the middle, we care about all levels, so we
		// check if the adjacents are down and valid, mid, or up and valid
		localTR |= ((ms(mosses, dirNE) && ssol(renderblocks, i + dirDNE.xCoord, j + dirDNE.yCoord, k + dirDNE.zCoord,
				fd(dirU))) && (localMidRight || (localDownRight && ssol(renderblocks, i + dirDNE.xCoord,
						j + dirDNE.yCoord, k + dirDNE.zCoord, fd(dirS))) || (localUpRight && ssol(renderblocks,
								i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
								fd(dirN)))) && (localMidTop || (localDownTop && ssol(renderblocks, i + dirDNE.xCoord,
										j + dirDNE.yCoord, k + dirDNE.zCoord,
										fd(dirW))) || (localUpTop && ssol(renderblocks, i + dirN.xCoord,
												j + dirN.yCoord, k + dirN.zCoord, fd(dirE)))));
		// If the NE block is down and there's nothing obstructing it above
		// and
		// the adjacents are either level or below
		localTR |= ((ms(mosses,
				dirDNE) && !ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord, fd(dirD)) && !ssol(
						renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord, fd(dirW)) && !ssol(
								renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord,
								fd(dirS))) && ((localMidRight && ssol(renderblocks, i + dirDE.xCoord, j + dirDE.yCoord,
										k + dirDE.zCoord, fd(dirN))) || (localDownRight && ssol(renderblocks,
												i + dirDDNE.xCoord, j + dirDDNE.yCoord, k + dirDDNE.zCoord,
												fd(dirU)))) && ((localMidTop && ssol(renderblocks, i + dirDN.xCoord,
														j + dirDN.yCoord, k + dirDN.zCoord,
														fd(dirE))) || (localDownTop)));
		// If the NE block is up and there's nothing obstructing it and the
		// adjacents are either level or above
		localTR |= ((ms(mosses, dirUNE) && ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord,
				fd(dirU))) && ((localMidRight && !ssol(renderblocks, i + dirUE.xCoord, j + dirUE.yCoord,
						k + dirUE.zCoord, fd(dirN)) && !ssol(renderblocks, i + dirUE.xCoord, j + dirUE.yCoord,
								k + dirUE.zCoord, fd(dirD)) && ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord,
										k + dirNE.zCoord,
										fd(dirS))) || localUpRight) && ((localMidTop && !ssol(renderblocks,
												i + dirUN.xCoord, j + dirUN.yCoord, k + dirUN.zCoord,
												fd(dirE)) && !ssol(renderblocks, i + dirUN.xCoord, j + dirUN.yCoord,
														k + dirUN.zCoord, fd(dirD)) && ssol(renderblocks,
																i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord,
																fd(dirW))) || localUpTop));
		localTR |= (ms(mosses, dirE)) && ssol(renderblocks, i + dirN.xCoord, j + dirN.yCoord, k + dirN.zCoord,
				fd(dirS)) && ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord, fd(dirS));
		localTR |= localDownTop && localDownRight && !ssol(renderblocks, i + dirDNE.xCoord, j + dirDNE.yCoord,
				k + dirDNE.zCoord,
				fd(dirS)) && !ssol(renderblocks, i + dirDNE.xCoord, j + dirDNE.yCoord, k + dirDNE.zCoord, fd(dirW));
		localTR |= localTop && ssol(renderblocks, i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
				fd(dirW)) && ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord, fd(dirW));
		localTR |= localMidTop && localDownRight && (ms(mosses, dirDNE)) && ssol(renderblocks, i + dirDN.xCoord,
				j + dirDN.yCoord, k + dirDN.zCoord, fd(dirE));
		localTR |= localMidRight && localDownTop && (ms(mosses, dirDNE)) && ssol(renderblocks, i + dirDE.xCoord,
				j + dirDE.yCoord, k + dirDE.zCoord, fd(dirN));
		localTR |= (eastFace || localMidRight) && (northFace || localMidTop) && (ms(mosses,
				dirNE)) && (ssol(renderblocks, i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
						fd(dirN)) || localMidRight) && (ssol(renderblocks, i + dirN.xCoord, j + dirN.yCoord,
								k + dirN.zCoord, fd(dirE)) || localMidTop);
		localTR |= localMidRight && localMidTop && ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord,
				k + dirNE.zCoord,
				fd(dirE)) && ssol(renderblocks, i + dirNE.xCoord, j + dirNE.yCoord, k + dirNE.zCoord, fd(dirS));
		localTR |= eastFace && localDownTop && ms(mosses, dirDNE) && ms(mosses, dirNE) && ssol(renderblocks,
				i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
				fd(dirN)) && ssol(renderblocks, i + dirDE.xCoord, j + dirDE.yCoord, k + dirDE.zCoord, fd(dirN));

		// This is complicated!!!
		// If the SW block is in the middle, we care about all levels, so we
		// check if the adjacents are down and valid, mid, or up and valid
		localBL |= ((ms(mosses, dirSW) && ssol(renderblocks, i + dirDSW.xCoord, j + dirDSW.yCoord, k + dirDSW.zCoord,
				fd(dirU))) && (localMidLeft || (localDownLeft && ssol(renderblocks, i + dirDSW.xCoord,
						j + dirDSW.yCoord, k + dirDSW.zCoord, fd(dirN))) || (localUpLeft && ssol(renderblocks,
								i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
								fd(dirS)))) && (localMidBottom || (localDownBottom && ssol(renderblocks,
										i + dirDSW.xCoord, j + dirDSW.yCoord, k + dirDSW.zCoord,
										fd(dirE))) || (localUpBottom && ssol(renderblocks, i + dirS.xCoord,
												j + dirS.yCoord, k + dirS.zCoord, fd(dirW)))));
		// If the SW block is down and there's nothing obstructing it above
		// and
		// the adjacents are either level or below
		localBL |= ((ms(mosses, dirDSW) && !ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord, fd(
				dirD)) && !ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord, fd(dirE)) && !ssol(
						renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord,
						fd(dirN))) && ((localMidLeft && ssol(renderblocks, i + dirDW.xCoord, j + dirDW.yCoord,
								k + dirDW.zCoord, fd(dirS))) || (localDownLeft && ssol(renderblocks, i + dirDDSW.xCoord,
										j + dirDDSW.yCoord, k + dirDDSW.zCoord, fd(dirU)))) && ((localMidBottom && ssol(
												renderblocks, i + dirDS.xCoord, j + dirDS.yCoord, k + dirDS.zCoord,
												fd(dirW))) || (localDownBottom && ssol(renderblocks, i + dirDDSW.xCoord,
														j + dirDDSW.yCoord, k + dirDDSW.zCoord, fd(dirU)))));
		// If the SW block is up and there's nothing obstructing it and the
		// adjacents are either level or above
		localBL |= ((ms(mosses, dirUSW) && ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord,
				fd(dirU))) && ((localMidLeft && !ssol(renderblocks, i + dirUW.xCoord, j + dirUW.yCoord,
						k + dirUW.zCoord, fd(dirS)) && !ssol(renderblocks, i + dirUW.xCoord, j + dirUW.yCoord,
								k + dirUW.zCoord, fd(dirD)) && ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord,
										k + dirSW.zCoord,
										fd(dirN))) || localUpLeft) && ((localMidBottom && !ssol(renderblocks,
												i + dirUS.xCoord, j + dirUS.yCoord, k + dirUS.zCoord,
												fd(dirW)) && !ssol(renderblocks, i + dirUS.xCoord, j + dirUS.yCoord,
														k + dirUS.zCoord, fd(dirD)) && ssol(renderblocks,
																i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord,
																fd(dirE))) || localUpBottom));
		localBL |= (ms(mosses, dirW)) && ssol(renderblocks, i + dirS.xCoord, j + dirS.yCoord, k + dirS.zCoord,
				fd(dirN)) && ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord, fd(dirN));
		localBL |= localDownBottom && localDownLeft && !ssol(renderblocks, i + dirDSW.xCoord, j + dirDSW.yCoord,
				k + dirDSW.zCoord,
				fd(dirN)) && !ssol(renderblocks, i + dirDSW.xCoord, j + dirDSW.yCoord, k + dirDSW.zCoord, fd(dirE));
		localBL |= localBottom && ssol(renderblocks, i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
				fd(dirE)) && ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord, fd(dirE));
		localBL |= localMidBottom && localDownLeft && (ms(mosses, dirDSW)) && ssol(renderblocks, i + dirDS.xCoord,
				j + dirDS.yCoord, k + dirDS.zCoord, fd(dirW));
		localBL |= localMidLeft && localDownBottom && (ms(mosses, dirDSW)) && ssol(renderblocks, i + dirDW.xCoord,
				j + dirDW.yCoord, k + dirDW.zCoord, fd(dirS));
		localBL |= (westFace || localMidLeft) && (southFace || localMidBottom) && (ms(mosses,
				dirSW)) && (ssol(renderblocks, i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
						fd(dirS)) || localMidLeft) && (ssol(renderblocks, i + dirS.xCoord, j + dirS.yCoord,
								k + dirS.zCoord, fd(dirW)) || localMidBottom);
		localBL |= localMidLeft && localMidBottom && ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord,
				k + dirSW.zCoord,
				fd(dirW)) && ssol(renderblocks, i + dirSW.xCoord, j + dirSW.yCoord, k + dirSW.zCoord, fd(dirN));
		localBL |= westFace && localDownBottom && ms(mosses, dirDSW) && ms(mosses, dirSW) && ssol(renderblocks,
				i + dirW.xCoord, j + dirW.yCoord, k + dirW.zCoord,
				fd(dirS)) && ssol(renderblocks, i + dirDW.xCoord, j + dirDW.yCoord, k + dirDW.zCoord, fd(dirS));

		// This is complicated!!!
		// If the SE block is in the middle, we care about all levels, so we
		// check if the adjacents are down and valid, mid, or up and valid
		localBR |= ((ms(mosses, dirSE) && ssol(renderblocks, i + dirDSE.xCoord, j + dirDSE.yCoord, k + dirDSE.zCoord,
				fd(dirU))) && (localMidRight || (localDownRight && ssol(renderblocks, i + dirDSE.xCoord,
						j + dirDSE.yCoord, k + dirDSE.zCoord, fd(dirN))) || (localUpRight && ssol(renderblocks,
								i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
								fd(dirS)))) && (localMidBottom || (localDownBottom && ssol(renderblocks,
										i + dirDSE.xCoord, j + dirDSE.yCoord, k + dirDSE.zCoord,
										fd(dirW))) || (localUpBottom && ssol(renderblocks, i + dirS.xCoord,
												j + dirS.yCoord, k + dirS.zCoord, fd(dirE)))));
		// If the SE block is down and there's nothing obstructing it above
		// and
		// the adjacents are either level or below
		localBR |= ((ms(mosses,
				dirDSE) && !ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord, fd(dirD)) && !ssol(
						renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord, fd(dirW)) && !ssol(
								renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord,
								fd(dirN))) && ((localMidRight && ssol(renderblocks, i + dirDE.xCoord, j + dirDE.yCoord,
										k + dirDE.zCoord, fd(dirS))) || (localDownRight && ssol(renderblocks,
												i + dirDDSE.xCoord, j + dirDDSE.yCoord, k + dirDDSE.zCoord,
												fd(dirU)))) && ((localMidBottom && ssol(renderblocks, i + dirDS.xCoord,
														j + dirDS.yCoord, k + dirDS.zCoord,
														fd(dirE))) || (localDownBottom && ssol(renderblocks,
																i + dirDDSE.xCoord, j + dirDDSE.yCoord,
																k + dirDDSE.zCoord, fd(dirU)))));
		// If the SE block is up and there's nothing obstructing it and the
		// adjacents are either level or above
		localBR |= ((ms(mosses, dirUSE) && ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord,
				fd(dirU))) && ((localMidRight && !ssol(renderblocks, i + dirUE.xCoord, j + dirUE.yCoord,
						k + dirUE.zCoord, fd(dirS)) && !ssol(renderblocks, i + dirUE.xCoord, j + dirUE.yCoord,
								k + dirUE.zCoord, fd(dirD)) && ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord,
										k + dirSE.zCoord,
										fd(dirN))) || localUpRight) && ((localMidBottom && !ssol(renderblocks,
												i + dirUS.xCoord, j + dirUS.yCoord, k + dirUS.zCoord,
												fd(dirE)) && !ssol(renderblocks, i + dirUS.xCoord, j + dirUS.yCoord,
														k + dirUS.zCoord, fd(dirD)) && ssol(renderblocks,
																i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord,
																fd(dirW))) || localUpBottom));
		localBR |= (ms(mosses, dirE)) && ssol(renderblocks, i + dirS.xCoord, j + dirS.yCoord, k + dirS.zCoord,
				fd(dirN)) && ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord, fd(dirN));
		localBR |= localDownBottom && localDownRight && !ssol(renderblocks, i + dirDSE.xCoord, j + dirDSE.yCoord,
				k + dirDSE.zCoord,
				fd(dirN)) && !ssol(renderblocks, i + dirDSE.xCoord, j + dirDSE.yCoord, k + dirDSE.zCoord, fd(dirW));
		localBR |= localBottom && ssol(renderblocks, i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
				fd(dirW)) && ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord, fd(dirW));
		localBR |= localMidBottom && localDownRight && (ms(mosses, dirDSE)) && ssol(renderblocks, i + dirDS.xCoord,
				j + dirDS.yCoord, k + dirDS.zCoord, fd(dirE));
		localBR |= localMidRight && localDownBottom && (ms(mosses, dirDSE)) && ssol(renderblocks, i + dirDE.xCoord,
				j + dirDE.yCoord, k + dirDE.zCoord, fd(dirS));
		localBR |= (eastFace || localMidRight) && (southFace || localMidBottom) && (ms(mosses,
				dirSE)) && (ssol(renderblocks, i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
						fd(dirS)) || localMidRight) && (ssol(renderblocks, i + dirS.xCoord, j + dirS.yCoord,
								k + dirS.zCoord, fd(dirE)) || localMidBottom);
		localBR |= localMidRight && localMidBottom && ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord,
				k + dirSE.zCoord,
				fd(dirE)) && ssol(renderblocks, i + dirSE.xCoord, j + dirSE.yCoord, k + dirSE.zCoord, fd(dirN));
		localBR |= eastFace && localDownBottom && ms(mosses, dirDSE) && ms(mosses, dirSE) && ssol(renderblocks,
				i + dirE.xCoord, j + dirE.yCoord, k + dirE.zCoord,
				fd(dirS)) && ssol(renderblocks, i + dirDE.xCoord, j + dirDE.yCoord, k + dirDE.zCoord, fd(dirS));

		if (count > 0)
		{
			i -= dirD.xCoord;
			j -= dirD.yCoord;
			k -= dirD.zCoord;
		}

		int[] idTexRot = getMossId(i, j, k, localTop, localRight, localBottom, localLeft, localTR, localBR, localBL,
				localTL);

		return new int[] { idTexRot[0], (idTexRot[1] + mossRotation) % 4, localDownLeft ? 1 : 0, localDownTop ? 1 : 0,
				localDownRight ? 1 : 0, localDownBottom ? 1 : 0, westFace ? 1 : 0, northFace ? 1 : 0, eastFace ? 1 : 0,
				southFace ? 1 : 0 };

	}

	public static boolean renderMoss(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		if (renderblocks.blockAccess.getBlock(i, j - 1, k) instanceof BlockBranch)
		{
			return true;
		}
		if (block == TFCBlocks.moss)
		{

			float extraDistance = 0.0625f;
			float drift = extraDistance;
			boolean fancy = RenderManager.instance != null && RenderManager.instance.options != null
					? RenderManager.instance.options.fancyGraphics : false;
			renderblocks = new RenderBlocksWithRotation(renderblocks);
			((RenderBlocksWithRotation) renderblocks).rotation = 0;
			((RenderBlocksWithRotation) renderblocks).clearMossRenderBounds();
			if (fancy)
			{
				int meta = 0;
				if (meta == -1)
				{
					return false;
				}

				boolean[][][] mosses = new boolean[3][3][3];
				for (int x = -1; x < 2; x++)
				{
					for (int y = -1; y < 2; y++)
					{
						for (int z = -1; z < 2; z++)
						{
							mosses[x + 1][y + 1][z + 1] = renderblocks.blockAccess.getBlock(i + x, j + y,
									k + z) == TFCBlocks.moss;
						}
					}
				}

				Vec3 unitX = Vec3.createVectorHelper(1, 0, 0);
				Vec3 unitY = Vec3.createVectorHelper(0, 1, 0);
				Vec3 unitZ = Vec3.createVectorHelper(0, 0, 1);

				boolean upFace = ssol(renderblocks, i, j - 1, k,
						ForgeDirection.UP) && !(renderblocks.blockAccess.getBlock(i, j - 1, k) instanceof BlockBranch);
				boolean southFace = ssol(renderblocks, i, j, k - 1, ForgeDirection.SOUTH) && !(renderblocks.blockAccess
						.getBlock(i, j, k - 1) instanceof BlockBranch);
				boolean northFace = ssol(renderblocks, i, j, k + 1, ForgeDirection.NORTH) && !(renderblocks.blockAccess
						.getBlock(i, j, k + 1) instanceof BlockBranch);
				boolean eastFace = ssol(renderblocks, i - 1, j, k, ForgeDirection.EAST) && !(renderblocks.blockAccess
						.getBlock(i - 1, j, k) instanceof BlockBranch);
				boolean westFace = ssol(renderblocks, i + 1, j, k, ForgeDirection.WEST) && !(renderblocks.blockAccess
						.getBlock(i + 1, j, k) instanceof BlockBranch);

				if (upFace)
				{
					int[] idTexRot = handleMossFace((RenderBlocksWithRotation) renderblocks, i, j, k, mosses, 0, 0, 0,
							1, unitX, unitY, unitZ, 0, 0);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(
							0.0F - idTexRot[2] * extraDistance + idTexRot[6] * extraDistance,
							0.0F + (upFace ? extraDistance : 0),
							0.0F - idTexRot[3] * extraDistance + idTexRot[7] * extraDistance,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance, drift,
							1.0F + idTexRot[5] * extraDistance - idTexRot[9] * extraDistance, 1, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 1, 0);
				}

				float adjHeight = 0f;

				// -Z
				if (southFace)
				{
					int[] idTexRot = handleMossFace((RenderBlocksWithRotation) renderblocks, i, j, k, mosses,
							(float) (-Math.PI / 2d), 0, 0, 2, unitX, unitY, unitZ, 0, 0);

					((RenderBlocksWithRotation) renderblocks).setRenderBounds(
							0.0F - idTexRot[2] * extraDistance + idTexRot[6] * extraDistance,
							0.0 + (upFace ? extraDistance : 0), 0.000F,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1.0f + idTexRot[3] * extraDistance - idTexRot[7] * extraDistance, extraDistance, 2, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 2, 0);

					// ((RenderBlocksWithRotation)
					// renderblocks).renderMossFace(0,
					// 0, i, j, k, 2);
				}
				// +Z
				if (northFace)
				{
					int[] idTexRot = handleMossFace((RenderBlocksWithRotation) renderblocks, i, j, k, mosses,
							(float) (Math.PI / 2d), 0, 0, 3, unitX, unitY, unitZ, 0, 0);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(
							0.0F - idTexRot[2] * extraDistance + idTexRot[6] * extraDistance,
							0.0 + (upFace ? extraDistance : 0), 1.000F - extraDistance,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1.0F + idTexRot[5] * extraDistance - idTexRot[9] * extraDistance, 1F, 3, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 3, 0);
				}
				// +X
				if (westFace)
				{
					int[] idTexRot = handleMossFace((RenderBlocksWithRotation) renderblocks, i, j, k, mosses, 0, 0,
							(float) (-Math.PI / 2d), 5, unitX, unitY, unitZ, 0, 0);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(1.000F - extraDistance,
							0.0 + (upFace ? extraDistance : 0),
							0.0F - idTexRot[3] * extraDistance + idTexRot[7] * extraDistance, 1.0F,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1F + idTexRot[5] * extraDistance - idTexRot[9] * extraDistance, 5, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 5, 0);
				}
				// -X
				if (eastFace)
				{
					int[] idTexRot = handleMossFace((RenderBlocksWithRotation) renderblocks, i, j, k, mosses, 0,
							(float) (-Math.PI), (float) (Math.PI / 2d), 4, unitX, unitY, unitZ, 0, 0);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(0F, 0.0 + (upFace ? extraDistance : 0),
							0.0F - idTexRot[5] * extraDistance + idTexRot[9] * extraDistance, extraDistance,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1F + idTexRot[3] * extraDistance - idTexRot[7] * extraDistance, 4, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 4, 0);
				}
				// Finally, render the damn thing.
				((RenderBlocksWithRotation) renderblocks).renderStandardBlock(TFCBlocks.moss, i, j, k);
			}
			else
			{
				boolean upFace = ssol(renderblocks, i, j - 1, k, ForgeDirection.UP) && !(renderblocks.blockAccess.getBlock(i, j - 1, k) instanceof BlockBranch);
				boolean southFace = ssol(renderblocks, i, j, k - 1, ForgeDirection.SOUTH) && !(renderblocks.blockAccess.getBlock(i, j, k-1) instanceof BlockBranch);
				boolean northFace = ssol(renderblocks, i, j, k + 1, ForgeDirection.NORTH)&& !(renderblocks.blockAccess.getBlock(i, j , k+1) instanceof BlockBranch);
				boolean eastFace = ssol(renderblocks, i - 1, j, k, ForgeDirection.EAST)&& !(renderblocks.blockAccess.getBlock(i-1, j, k) instanceof BlockBranch);
				boolean westFace = ssol(renderblocks, i + 1, j, k, ForgeDirection.WEST)&& !(renderblocks.blockAccess.getBlock(i+1, j, k) instanceof BlockBranch);

				if (upFace)
				{
					int[] idTexRot = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(
							0.0F - idTexRot[2] * extraDistance + idTexRot[6] * extraDistance, 0.0F,
							0.0F - idTexRot[3] * extraDistance + idTexRot[7] * extraDistance,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance, drift,
							1.0F + idTexRot[5] * extraDistance - idTexRot[9] * extraDistance, 1, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 1, 0);
				}

				float adjHeight = 0f;

				// -Z
				if (southFace)
				{
					int[] idTexRot = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };

					((RenderBlocksWithRotation) renderblocks).setRenderBounds(
							0.0F - idTexRot[2] * extraDistance + idTexRot[6] * extraDistance, 0.0, 0.000F,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1.0f + idTexRot[3] * extraDistance - idTexRot[7] * extraDistance, 0.0625F, 2, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 2, 0);

					// ((RenderBlocksWithRotation)
					// renderblocks).renderMossFace(0,
					// 0, i, j, k, 2);
				}
				// +Z
				if (northFace)
				{
					int[] idTexRot = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(
							0.0F - idTexRot[2] * extraDistance + idTexRot[6] * extraDistance, 0.0, 1.000F - 0.0625F,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1.0F + idTexRot[5] * extraDistance - idTexRot[9] * extraDistance, 1F, 3, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 3, 0);
				}
				// +X
				if (westFace)
				{
					int[] idTexRot = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(1.000F - 0.0625F, 0.0,
							0.0F - idTexRot[3] * extraDistance + idTexRot[7] * extraDistance, 1.0F,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1F + idTexRot[5] * extraDistance - idTexRot[9] * extraDistance, 5, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 5, 0);
				}
				// -X
				if (eastFace)
				{
					int[] idTexRot = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 };
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(0F, 0.0,
							0.0F - idTexRot[5] * extraDistance + idTexRot[9] * extraDistance, 0.0625F,
							1.0F + idTexRot[4] * extraDistance - idTexRot[8] * extraDistance,
							1F + idTexRot[3] * extraDistance - idTexRot[7] * extraDistance, 4, 0);
					((RenderBlocksWithRotation) renderblocks).renderMossFace(idTexRot[0], idTexRot[1], i, j, k, 4, 0);
				}
				// Finally, render the damn thing.
				((RenderBlocksWithRotation) renderblocks).renderStandardBlock(TFCBlocks.moss, i, j, k);
			}
			return true;
		}
		return true;
	}

	public static boolean renderWoodTrunk(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		/*
		 * if(blockAccess.getBlockMaterial(i, j+1, k) == Material.leaves ||
		 * blockAccess.getBlockMaterial(i, j-1, k) == Material.leaves ||
		 * blockAccess.getBlock(i, j+1, k) == mod_TFC_Core.fruitTreeWood ||
		 * blockAccess.getBlock(i, j-1, k) == mod_TFC_Core.fruitTreeWood)
		 */
		if (blockAccess.getTileEntity(i, j,
				k) != null && (blockAccess.getBlock(i, j - 1, k) == TFCBlocks.fruitTreeWood || blockAccess
						.getBlock(i, j - 1, k).isOpaqueCube()))
		{
			renderblocks.setRenderBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (blockAccess.getBlock(i - 1, j, k).getMaterial() == Material.leaves || blockAccess.getBlock(i - 1, j,
				k) == TFCBlocks.fruitTreeWood)
		{
			renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (blockAccess.getBlock(i + 1, j, k).getMaterial() == Material.leaves || blockAccess.getBlock(i + 1, j,
				k) == TFCBlocks.fruitTreeWood)
		{
			renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (blockAccess.getBlock(i, j, k - 1).getMaterial() == Material.leaves || blockAccess.getBlock(i, j,
				k - 1) == TFCBlocks.fruitTreeWood)
		{
			renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if (blockAccess.getBlock(i, j, k + 1).getMaterial() == Material.leaves || blockAccess.getBlock(i, j,
				k + 1) == TFCBlocks.fruitTreeWood)
		{
			renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}

		if (!((TEFruitTreeWood) blockAccess.getTileEntity(i, j, k)).isTrunk && blockAccess.getBlock(i, j - 1,
				k) != TFCBlocks.fruitTreeWood && !blockAccess.getBlock(i, j - 1, k).isOpaqueCube())
		{
			renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
			renderblocks.renderStandardBlock(block, i, j, k);

			renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
			renderblocks.renderStandardBlock(block, i, j, k);

			renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
			renderblocks.renderStandardBlock(block, i, j, k);

			renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}

		// renderblocks.func_83020_a(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
		return true;
	}

	public static Random renderRandom = new Random();

	public static boolean renderLooseRock(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		boolean breaking = false;
		/*
		 * if(renderblocks.overrideBlockTexture >= 240) { breaking = true; }
		 */

		// int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);
		World w = Minecraft.getMinecraft().theWorld;
		// TFCWorldChunkManager wcm =
		// ((TFCWorldChunkManager)w.getWorldChunkManager());
		renderblocks.renderAllFaces = true;

		DataLayer rockLayer1 = TFC_Climate.getCacheManager(w).getRockLayerAt(i, k, 0);
		if (rockLayer1 != null && rockLayer1.block != null && !breaking)
			renderblocks.overrideBlockTexture = rockLayer1.block.getIcon(0, rockLayer1.data2);

		int seed = i * k + j;
		renderRandom.setSeed(seed);

		float xOffset = (renderRandom.nextInt(5) - 2) * 0.05f;
		float zOffset = (renderRandom.nextInt(5) - 2) * 0.05f;

		float xOffset2 = (renderRandom.nextInt(5) - 2) * 0.05f;
		float yOffset2 = (renderRandom.nextInt(5) - 2) * 0.05f;
		float zOffset2 = (renderRandom.nextInt(5) - 2) * 0.05f;

		renderblocks.setRenderBounds(0.35F + xOffset, 0.00F, 0.35F + zOffset, 0.65F + xOffset2, 0.15F + yOffset2,
				0.65F + zOffset2);
		renderblocks.renderStandardBlock(block, i, j, k);
		// renderblocks.func_83020_a(0.20F, 0.00F, 0.2F, 0.8F, 0.25F, 0.8F);
		renderblocks.clearOverrideBlockTexture();

		return true;
	}

	public static boolean renderOre(Block block, int xCoord, int yCoord, int zCoord, float par5, float par6, float par7,
			RenderBlocks renderblocks, IBlockAccess iblockaccess)
	{
		/*
		 * boolean breaking = false; if(renderblocks.overrideBlockTexture >=
		 * 240) { breaking = true; }
		 * 
		 * if(!breaking) { //render the background rock
		 * renderblocks.overrideBlockTexture =
		 * getRockTexture(ModLoader.getMinecraftInstance().theWorld, xCoord,
		 * yCoord, zCoord); renderblocks.renderStandardBlock(block, xCoord,
		 * yCoord, zCoord); renderblocks.clearOverrideBlockTexture();
		 * 
		 * //render the ore overlay renderblocks.renderStandardBlock(block,
		 * xCoord, yCoord, zCoord); }
		 * 
		 * //renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
		 */
		return true;
	}

	public static IIcon getRockTexture(World world, int xCoord, int yCoord, int zCoord)
	{
		IIcon var27;
		DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(xCoord, zCoord, 0);
		DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(xCoord, zCoord, 1);
		DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(xCoord, zCoord, 2);

		if (yCoord <= TFCOptions.rockLayer3Height)
			var27 = rockLayer3.block.getIcon(5, rockLayer3.data2);
		else if (yCoord <= TFCOptions.rockLayer2Height)
			var27 = rockLayer2.block.getIcon(5, rockLayer2.data2);
		else
			var27 = rockLayer1.block.getIcon(5, rockLayer1.data2);
		return var27;
	}

	public static boolean renderMolten(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);
		// renderblocks.func_83020_a(0.0F, 0.0F, 0.0F, 0.001F, 0.001F, 0.001F);
		return true;
	}

	public static boolean renderFirepit(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		// IBlockAccess blockAccess = renderblocks.blockAccess;
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
		return true;
	}

	public static boolean renderForge(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		// IBlockAccess blockAccess = renderblocks.blockAccess;
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
		return true;
	}

	public static boolean renderSluice(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		// Tessellator tessellator = Tessellator.instance;
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int dir = BlockSluice.getDirectionFromMetadata(meta);

		// render ramp
		if (!BlockSluice.isBlockFootOfBed(meta))
		{
			if (dir == 0)
			{
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0, 0.0F, 0.0F + (0.25 * count), 1, 1.0F - (0.125 * count),
							0.05F + (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0, 0.0F, 0.05F + (0.25 * count), 1, 0.8125 - (0.125 * count),
							0.25F + (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
			else if (dir == 1)
			{
				if ((meta & 4) != 0)
					renderblocks.uvRotateTop = 1;
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0.95F - (0.25 * count), 0.0F, 0, 1.0F - (0.25 * count),
							1.0F - (0.125 * count), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0.75F - (0.25 * count), 0.0F, 0, 0.95F - (0.25 * count),
							0.8125 - (0.125 * count), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
			else if (dir == 2)
			{
				if ((meta & 4) != 0)
					renderblocks.uvRotateTop = 3;
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0.0F, 0.0F, 0.95F - (0.25 * count), 1F, 1.0F - (0.125 * count),
							1.0F - (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0.0F, 0.0F, 0.75F - (0.25 * count), 1F, 0.8125F - (0.125 * count),
							0.95F - (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
			else if (dir == 3)
			{
				if ((meta & 4) != 0)
					renderblocks.uvRotateTop = 2;
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0.0F + (0.25 * count), 0.0F, 0, 0.05F + (0.25 * count),
							1.0F - (0.125 * count), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0.05F + (0.25 * count), 0.0F, 0, 0.25F + (0.25 * count),
							0.8125 - (0.125 * count), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
		}
		else
		{
			if (dir == 0)
			{
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0, 0.0F, 0.0F + (0.25 * count), 1, 0.5F - (0.125 * count),
							0.05F + (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0, 0.0F, 0.05F + (0.25 * count), 1,
							Math.max(0.3125 - (0.125 * count), 0.01), 0.25F + (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
			if (dir == 1)
			{
				if ((meta & 4) != 0)
					renderblocks.uvRotateTop = 1;
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0.95F - (0.25 * count), 0.0F, 0, 1.0F - (0.25 * count),
							0.5F - (0.125 * count), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0.75F - (0.25 * count), 0.0F, 0, 0.95F - (0.25 * count),
							Math.max(0.3125 - (0.125 * count), 0.01), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
			if (dir == 2)
			{
				if ((meta & 4) != 0)
					renderblocks.uvRotateTop = 3;
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0.0F, 0.0F, 0.95F - (0.25 * count), 1F, 0.5F - (0.125 * count),
							1.0F - (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0.0F, 0.0F, 0.75F - (0.25 * count), 1F,
							Math.max(0.3125 - (0.125 * count), 0.01), 0.95F - (0.25 * count));
					renderblocks.renderStandardBlock(block, i, j, k);
				}

			}
			if (dir == 3)
			{
				if ((meta & 4) != 0)
					renderblocks.uvRotateTop = 2;
				for (int count = 0; count < 4; count++)
				{
					// ribs
					renderblocks.setRenderBounds(0.0F + (0.25 * count), 0.0F, 0, 0.05F + (0.25 * count),
							0.5F - (0.125 * count), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
					// body
					renderblocks.setRenderBounds(0.05F + (0.25 * count), 0.0F, 0, 0.25F + (0.25 * count),
							Math.max(0.3125 - (0.125 * count), 0.01), 1);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
		}
		renderblocks.uvRotateTop = 0;
		// set the block collision box
		renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		return true;
	}

	public static boolean renderBlockWithCustomColorMultiplier(Block block, RenderBlocks renderBlocks, int xCoord,
			int yCoord, int zCoord, int colorMultiplier)
	{
		int l = colorMultiplier;
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}

		return Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0
				? (renderBlocks.partialRenderBounds
						? renderBlocks.renderStandardBlockWithAmbientOcclusion(block, xCoord, yCoord, zCoord, f, f1, f2)
						: renderBlocks.renderStandardBlockWithAmbientOcclusion(block, xCoord, yCoord, zCoord, f, f1,
								f2))
				: renderBlocks.renderStandardBlockWithColorMultiplier(block, xCoord, yCoord, zCoord, f, f1, f2);
	}

	public static boolean renderFruitLeaves(Block block, int xCoord, int yCoord, int zCoord, RenderBlocks renderblocks)
	{
		int meta = renderblocks.blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
		if (meta >= 8)
			meta -= 8;

		FloraManager manager = FloraManager.getInstance();
		FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(block, meta));

		renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
		if (index != null && (index.inBloom(TFC_Time.getSeasonAdjustedMonth(zCoord)) || index
				.inHarvest(TFC_Time.getSeasonAdjustedMonth(zCoord))))
		{
			renderblocks.overrideBlockTexture = getFruitTreeOverlay(renderblocks.blockAccess, xCoord, yCoord, zCoord);
			if (renderblocks.overrideBlockTexture != null)
				renderBlockWithCustomColorMultiplier(block, renderblocks, xCoord, yCoord, zCoord, 16777215);
			renderblocks.clearOverrideBlockTexture();
		}
		return true;
	}

	public static boolean renderNewFruitLeaves(Block block, int xCoord, int yCoord, int zCoord,
			RenderBlocks renderblocks)
	{
		if (renderblocks.blockAccess.getBlockMetadata(xCoord, yCoord, zCoord) != 10)
		{
			renderblocks.setRenderBounds(0, 0, 0, 1, 1, 1);
			renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
			return true;
		}
		int meta = ((TEFruitLeaves) renderblocks.blockAccess.getTileEntity(xCoord, yCoord, zCoord)).fruitType;

		FloraManager manager = FloraManager.getInstance();
		FloraIndex index = manager.findMatchingIndex(BlockCustomLeaves2.getType(block, meta));

		renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
		if (index != null && (index.inBloom(TFC_Time.getSeasonAdjustedMonth(zCoord)) || index
				.inHarvest(TFC_Time.getSeasonAdjustedMonth(zCoord))))
		{
			renderblocks.overrideBlockTexture = getNewFruitTreeOverlay(renderblocks.blockAccess, xCoord, yCoord,
					zCoord);
			if (renderblocks.overrideBlockTexture != null)
				renderBlockWithCustomColorMultiplier(block, renderblocks, xCoord, yCoord, zCoord, 16777215);
			renderblocks.clearOverrideBlockTexture();
		}
		return true;
	}

	public static boolean renderSeaPlant(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
	{
		boolean substrateRender = false;
		boolean plantRender = false;
		TileEntity te = renderblocks.blockAccess.getTileEntity(par2, par3, par4);
		if (te instanceof TEWaterPlant)
		{
			TEWaterPlant wp = (TEWaterPlant) te;
			if (wp.getBlockFromType() != null)
			{
				substrateRender = renderblocks.renderStandardBlockWithColorMultiplier(wp.getBlockFromType(), par2, par3,
						par4, 1, 1, 1);
				plantRender = RenderFlora.render(par1Block, par2, par3, par4, renderblocks);
			}
		}
		return substrateRender && plantRender;
	}

	public static IIcon getFruitTreeOverlay(IBlockAccess world, int x, int y, int z)
	{
		IIcon out = null;
		int meta = world.getBlockMetadata(x, y, z);
		Block id = world.getBlock(x, y, z);
		int offset = id == TFCBlocks.fruitTreeLeaves ? 0 : 8;

		FloraManager manager = FloraManager.getInstance();
		FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(id, meta & 7));
		if (index != null)
		{
			if (index.inBloom(TFC_Time.getSeasonAdjustedMonth(z)))// blooming
				out = BlockFruitLeaves.iconsFlowers[(meta & 7) + offset]; // NOPMD
			else if (meta >= 8)// fruit
				out = BlockFruitLeaves.iconsFruit[(meta & 7) + offset]; // NOPMD
		}
		return out;
	}

	public static IIcon getNewFruitTreeOverlay(IBlockAccess world, int x, int y, int z)
	{
		IIcon out = null;
		int meta = ((TEFruitLeaves) world.getTileEntity(x, y, z)).fruitType;
		Block id = world.getBlock(x, y, z);
		TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
		FloraManager manager = FloraManager.getInstance();
		FloraIndex index = manager.findMatchingIndex(BlockCustomLeaves2.getType(id, meta));
		if (index != null)
		{
			if (index.inBloom(TFC_Time.getSeasonAdjustedMonth(z)))// blooming
				out = Minecraft.getMinecraft().gameSettings.fancyGraphics?BlockCustomLeaves2.iconsFancyFlowers[meta]:BlockCustomLeaves2.iconsFlowers[meta]; // NOPMD
			else if(te.hasFruit)
				out = BlockCustomLeaves2.iconsFruit[meta]; // NOPMD
		}
		return out;
	}

	/*
	 * private static void drawCrossedSquares(Block block, int x, int y, int z,
	 * RenderBlocks renderblocks) { Tessellator var9 = Tessellator.instance;
	 * 
	 * var9.setColorOpaque_F(1.0f, 1.0f, 1.0f); GL11.glColor3f(1, 1, 1);
	 * 
	 * IIcon index = block.getIcon(renderblocks.blockAccess, x, y, z, 0);
	 * 
	 * double minX = index.getMinU(); double maxX = index.getMaxU(); double minY
	 * = index.getMinV(); double maxY = index.getMaxV();
	 * 
	 * double xMin = x + 0.5D - 0.45D; double xMax = x + 0.5D + 0.45D; double
	 * zMin = z + 0.5D - 0.45D; double zMax = z + 0.5D + 0.45D;
	 * 
	 * var9.addVertexWithUV(xMin, y + 0, zMin, minX, minY);
	 * var9.addVertexWithUV(xMin, y + 0.0D, zMin, minX, maxY);
	 * var9.addVertexWithUV(xMax, y + 0.0D, zMax, maxX, maxY);
	 * var9.addVertexWithUV(xMax, y + 0, zMax, maxX, minY);
	 * 
	 * var9.addVertexWithUV(xMax, y + 0, zMax, minX, minY);
	 * var9.addVertexWithUV(xMax, y + 0.0D, zMax, minX, maxY);
	 * var9.addVertexWithUV(xMin, y + 0.0D, zMin, maxX, maxY);
	 * var9.addVertexWithUV(xMin, y + 0, zMin, maxX, minY);
	 * 
	 * var9.addVertexWithUV(xMin, y + 0, zMax, minX, minY);
	 * var9.addVertexWithUV(xMin, y + 0.0D, zMax, minX, maxY);
	 * var9.addVertexWithUV(xMax, y + 0.0D, zMin, maxX, maxY);
	 * var9.addVertexWithUV(xMax, y + 0, zMin, maxX, minY);
	 * 
	 * var9.addVertexWithUV(xMax, y + 0, zMin, minX, minY);
	 * var9.addVertexWithUV(xMax, y + 0.0D, zMin, minX, maxY);
	 * var9.addVertexWithUV(xMin, y + 0.0D, zMax, maxX, maxY);
	 * var9.addVertexWithUV(xMin, y + 0, zMax, maxX, minY); }
	 */
}
