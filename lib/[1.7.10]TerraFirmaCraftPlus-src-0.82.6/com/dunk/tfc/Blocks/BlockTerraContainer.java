package com.dunk.tfc.Blocks;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTerraContainer extends BlockContainer
{
	public BlockTerraContainer()
	{
		super(Material.rock);
	}

	public BlockTerraContainer(Material material)
	{
		super(material);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		// TODO: Debug Message should go here if debug is toggled on
		if (TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			TerraFirmaCraft.LOG.info("Meta=" + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}

	protected boolean drunkChanceInteract(EntityPlayer player)
	{
		// If the player is too drunk, maybe we don't let them interact with the
		// block
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
		if (fs != null)
		{
			int drunk = (int) (fs.soberTime - TFC_Time.getTotalTicks());
			if (drunk > 10000)
			{
				if (player.worldObj.rand.nextInt(((drunk - 10000) / 1000)+1) == 0)
				{
					// Success!
					return true;
				}
				else
				{
					player.addChatComponentMessage(new ChatComponentTranslation("gui.drunk.oops"));
					return false;
				}
			}
		}
		return true;
	}

	protected boolean drunkDenyInteract(EntityPlayer player)
	{
		// If the player is too drunk, we don't let the try
		// If the player is too drunk, maybe we don't let them interact with the
		// block
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
		if (fs != null)
		{
			int drunk = (int) (fs.soberTime - TFC_Time.getTotalTicks());
			if (drunk > 10000)
			{
				player.addChatComponentMessage(new ChatComponentTranslation("gui.drunk.deny"));
				return false;

			}
		}
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess w, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			if (te instanceof IInventory)
			{
				for (int i = 0; i < ((IInventory) te).getSizeInventory(); i++)
				{
					if (((IInventory) te).getStackInSlot(i) != null)
					{
						EntityItem ei = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, ((IInventory) te).getStackInSlot(i));
						ei.motionX = 0;
						ei.motionY = 0;
						ei.motionZ = 0;
						world.spawnEntityInWorld(ei);
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
}
