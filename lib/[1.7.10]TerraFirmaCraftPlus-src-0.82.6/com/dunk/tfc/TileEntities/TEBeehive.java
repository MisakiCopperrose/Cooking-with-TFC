package com.dunk.tfc.TileEntities;

import java.util.Random;

import com.dunk.tfc.Blocks.Devices.BlockBeehive;
import com.dunk.tfc.Blocks.Flora.BlockFlower;
import com.dunk.tfc.Blocks.Flora.BlockFruitLeaves;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TEBeehive extends NetworkTileEntity implements IInventory
{
	ItemStack[] storage = new ItemStack[6];
	public int numberInteractingPlayers = 0;
	int lastHiveUpdate;

	@Override
	public void updateEntity()
	{
		// check storage
		if (!this.worldObj.isRemote)
		{
			Block b = this.worldObj.getBlock(xCoord, yCoord, zCoord);
			if (b instanceof BlockBeehive)
			{
				// Wild hives need to check if they're empty. If they are, they
				// have to be deleted
				int fertileCombCount = 0;
				int emptySlotCount = 0;
				for (int i = 0; i < storage.length; i++)
				{
					if (storage[i] != null && storage[i].getItem() == TFCItems.fertileHoneycomb)
					{
						fertileCombCount++;
					}
					else if(storage[i]==null)
					{
						emptySlotCount++;
					}
				}
				if (fertileCombCount == 0 && this.numberInteractingPlayers == 0 && b != TFCBlocks.beehive)
				{
					// Delete the block
					this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				}

				// Handle when we have fertile combs
				if (TFC_Time.getTotalDays() > this.lastHiveUpdate && worldObj.isDaytime() && b == TFCBlocks.beehive && fertileCombCount > 0)
				{
					float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(worldObj, this.lastHiveUpdate + 1, xCoord, yCoord, zCoord);
					if (temp > 10 && emptySlotCount > 0)
					{
						int flowerCount = 0;
						// Search for flowers
						for (int x = -8; x <= 8; x++)
						{
							for (int y = -3; y <= 4; y++)
							{
								for (int z = -8; z <= 8; z++)
								{
									if (worldObj.checkChunksExist(xCoord + x, yCoord + y, zCoord + z, xCoord + x, yCoord + y, zCoord + z))
									{
										Block f = worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
										if (f instanceof BlockFlower)
										{
											flowerCount++;
										}
										else if (f instanceof BlockFruitLeaves)
										{
											// Check if in season
											if (((BlockFruitLeaves) f).isFlowering(worldObj, xCoord + x, yCoord + y, zCoord + z))
											{
												((BlockFruitLeaves) f).Pollinate(worldObj, xCoord + x, yCoord + y, zCoord + z);
												flowerCount += 2;
											}
										}
									}
								}
							}
						}
						//Now we know how many flowers we have, we can calculate our chances of making honey
						float honeyChance = (Math.min(flowerCount,90) / 90f) * 0.5f;
						if(this.worldObj.rand.nextFloat() < honeyChance)
						{
							boolean done = false;
							for(int i = 0; i < storage.length && !done; i++)
							{
								if(storage[i] == null)
								{
									this.setInventorySlotContents(i, new ItemStack(TFCItems.honeycomb));
									done = true;
								}
							}
						}
						//Convert honeycomb to fertilized honeycomb
						for(int i = 0; i < storage.length; i++)
						{
							if(storage[i] != null && storage[i].getItem() == TFCItems.honeycomb && this.worldObj.rand.nextFloat() < 0.05f)
							{
								this.setInventorySlotContents(i, new ItemStack(TFCItems.fertileHoneycomb));
							}
						}
					}
					this.lastHiveUpdate++;
				}
			}
		}
	}
	
	public boolean hasFertileComb()
	{
		if(storage != null)
		{
			for(int i = 0; i < storage.length; i++)
			{
				if(storage[i] != null && storage[i].getItem()== TFCItems.fertileHoneycomb)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
		readFromNBT(nbt);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub
		writeToNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
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
		nbt.setTag("Items", nbttaglist);
		nbt.setInteger("lastUpdate", this.lastHiveUpdate);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if (byte0 >= 0 && byte0 < storage.length)
				setInventorySlotContents(byte0, ItemStack.loadItemStackFromNBT(nbt1));
		}
		lastHiveUpdate = nbt.getInteger("lastUpdate");
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is)
	{
		if (!ItemStack.areItemStacksEqual(storage[i], is))
		{
			storage[i] = is;
			if (i == 0)
			{
				// processItems();
				// if (!getSealed())
				// this.unsealtime = (int) TFC_Time.getTotalHours();
			}
		}
	}

	public void updateGui()
	{

	}

	@Override
	public int getSizeInventory()
	{
		return 6;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if (storage.length > i)
		{
			return storage[i];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.storage[i] != null)
		{
			ItemStack var3;
			if (this.storage[i].stackSize <= j)
			{
				var3 = this.storage[i];
				this.storage[i] = null;
				this.markDirty();
				return var3;
			}
			else
			{
				var3 = this.storage[i].splitStack(j);
				if (this.storage[i].stackSize == 0)
					this.storage[i] = null;
				this.markDirty();
				return var3;
			}
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if (this.storage[i] != null)
		{
			ItemStack var2 = this.storage[i];
			this.storage[i] = null;
			return var2;
		}
		else
			return null;
	}

	@Override
	public String getInventoryName()
	{
		return TFC_Core.translate("gui.beehive");
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

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.3F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.3F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
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
}
