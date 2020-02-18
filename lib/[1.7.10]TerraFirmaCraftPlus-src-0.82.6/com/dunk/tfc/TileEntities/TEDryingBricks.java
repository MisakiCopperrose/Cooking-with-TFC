package com.dunk.tfc.TileEntities;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TEDryingBricks extends NetworkTileEntity implements IInventory
{
	public ItemStack[] storage;
	public int[] remainingTicks;
	public final int defaultRemainingTicks = 6000;

	public TEDryingBricks()
	{
		storage = new ItemStack[4];
		remainingTicks = new int[4];
	}

	public void clearInventory()
	{
		storage = new ItemStack[4];
		for (int i = 0; i < remainingTicks.length; i++)
		{
			remainingTicks[i] = defaultRemainingTicks;
		}
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			
			if(storage == null || storage.length == 0)
			{
				storage = new ItemStack[4];
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			for (int i = 0; i < remainingTicks.length; i++)
			{
				if (storage[i] == null)
				{
					remainingTicks[i] = defaultRemainingTicks;
				}
//				if (storage[i] != null && remainingTicks[i] > 1000)
		//		{
		//			remainingTicks[i] = 1000;
		//		}
			}
			if (TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord) && TFC_Time.getTotalTicks() % 24000 < 12000)
			{
				for (int i = 0; i < remainingTicks.length; i++)
				{
					remainingTicks[i] = defaultRemainingTicks;
					if(storage[i] != null && storage[i].getItemDamage()<32)
					{
						storage[i].setItemDamage(storage[i].getItemDamage()+32);
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
					
				}
			}
			else if (TFC_Core.isExposed(worldObj, xCoord, yCoord, zCoord) && !worldObj.isRaining())
			{
				for (int i = 0; i < remainingTicks.length; i++)
				{
					if (storage[i] != null)
					{
						if (remainingTicks[i] >= 0)
						{
							remainingTicks[i]--;
						}
						if (remainingTicks[i] == 0)
						{
							storage[i].setItemDamage(storage[i].getItemDamage() % 32);
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
					}
				}
			}
		}
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
	}

	public void ejectItem(int index)
	{
		EntityItem entityitem;
		// new Random();

		if (storage[index] != null)
		{
			entityitem = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, storage[index]);
			entityitem.lifespan = 48000;
			worldObj.spawnEntityInWorld(entityitem);
			storage[index] = null;
		}

		if (storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null)
		{
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < storage.length; i++)
		{
			nbttagcompound.setInteger("ticksRemaining" + i, remainingTicks[i]);
		}
		for (int i = 0; i < storage.length; i++)
		{
			if (storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		for (int i = 0; i < storage.length; i++)
		{
			remainingTicks[i] = nbttagcompound.getInteger("ticksRemaining" + i);
		}
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
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

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.3F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.3F + 0.1F;

		for (int i = 0; i < 4; i++)
		{
			if (storage[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, storage[i]);
				entityitem.motionX = (float) rand.nextGaussian() * f3;
				entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public int getSizeInventory()
	{
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getInventoryName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
