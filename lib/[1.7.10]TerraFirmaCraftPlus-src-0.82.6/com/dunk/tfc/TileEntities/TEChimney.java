package com.dunk.tfc.TileEntities;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.dunk.tfc.Blocks.BlockChimney;
import com.dunk.tfc.api.TileEntities.TEFireEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TEChimney extends NetworkTileEntity
{
	public int smoking = 0;
	public int onFire = 0;

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			Random rand = new Random();
			// if below us is a firentity
			boolean foundSmoke = smoking > 0;
			for (int i = -1; i < 2 && !foundSmoke; i++)
			{
				for (int k = -1; k < 2 && !foundSmoke; k++)
				{
					Block below = worldObj.getBlock(xCoord + i, yCoord - 1, zCoord + k);
					TileEntity belowTe = worldObj.getTileEntity(xCoord + i, yCoord - 1, zCoord + k);
					if (belowTe != null && belowTe instanceof TEFireEntity)
					{
						if (((TEFireEntity) belowTe).fireTemp > 300 && smoking == 0)
						{
							this.smoking += rand.nextInt(50) + 5;
							foundSmoke = true;
						//	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
					}
				}
			}
			if (!worldObj.isRemote && onFire > 0 && !worldObj.getBlock(xCoord, yCoord + 1, zCoord).isOpaqueCube())
			{
				List list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
						AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 3, zCoord + 1));
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityLivingBase e = (EntityLivingBase) iterator.next();
					e.setFire(2);
					
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			if (yCoord < 255)
			{
				TileEntity aboveTe = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
				if (aboveTe instanceof TEChimney && ((TEChimney) aboveTe).smoking < 5 && smoking > 0)
				{
					((TEChimney) aboveTe).smoking += smoking;
					smoking = 0;
					if(smoking == 0)
					{
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
				if (aboveTe instanceof TEChimney && ((TEChimney) aboveTe).onFire < 10 && onFire > 0)
				{
					((TEChimney) aboveTe).onFire += onFire - 1;
					onFire = 0;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			if (onFire > 0)
			{
				onFire--;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else if (onFire < 0)
			{
				onFire = 0;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			if (smoking > 0)
			{
				
				smoking--;
				if(smoking == 0)
				{
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			
		}
	}

	public boolean canChimneySeeSky()
	{
		return BlockChimney.canChimneySeeSky(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("smoking", smoking);
		nbttagcompound.setInteger("onFire", onFire);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		smoking = nbttagcompound.getInteger("smoking");
		onFire = nbttagcompound.getInteger("onFire");
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		this.readFromNBT(nbt);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		this.writeToNBT(nbt);
	}

}
