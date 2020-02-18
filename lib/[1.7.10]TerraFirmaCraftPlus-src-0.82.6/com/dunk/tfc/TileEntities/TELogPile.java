package com.dunk.tfc.TileEntities;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.dunk.tfc.Blocks.BlockLogPile;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Vector3f;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

public class TELogPile extends TileEntity implements IInventory
{
	public ItemStack[] storage;
	private int logPileOpeners;
	public boolean isOnFire;
	public int fireTimer;
	private Queue<Vector3f> blocksToBeSetOnFire;

	public TELogPile()
	{
		storage = new ItemStack[4];
		logPileOpeners = 0;
		fireTimer = 100;
	}

	public void addContents(int index, ItemStack is)
	{
		if (storage[index] == null)
		{
			storage[index] = is;
		}
	}

	public ItemStack takeLog(int slot)
	{
		if (storage[slot] == null)
			return null;
		else
		{
			ItemStack is = storage[slot].copy();
			is.stackSize = 1;
			storage[slot].stackSize--;
			if (storage[slot].stackSize == 0)
				storage[slot] = null;
			if (this.getNumberOfLogs() == 0)
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			return is;
		}
	}

	public void clearContents()
	{
		storage[0] = null;
		storage[1] = null;
		storage[2] = null;
		storage[3] = null;
	}

	@Override
	public void closeInventory()
	{
		--logPileOpeners;
		if (logPileOpeners == 0 && storage[0] == null && storage[1] == null && storage[2] == null && storage[3] == null)
		{
			extinguishFire();
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	public boolean contentsMatch(int index, ItemStack is)
	{
		return storage[index] != null && storage[index].getItem() == is.getItem() && storage[index]
				.getItemDamage() == is.getItemDamage() && storage[index].stackSize < storage[index]
						.getMaxStackSize() && storage[index].stackSize + 1 <= this.getInventoryStackLimit();
	}

	public boolean contentsSame(int index, ItemStack is)
	{
		return storage[index] != null && storage[index].getItem() == is.getItem() && storage[index]
				.getItemDamage() == is.getItemDamage();
	}

	public int getNumberOfLogs()
	{
		int[] count = new int[4];
		count[0] = storage[0] != null ? storage[0].getItem() == TFCItems.thickLogs?4:storage[0].stackSize  : 0;
		count[1] = storage[1] != null ? storage[1].getItem() == TFCItems.thickLogs?4:storage[1].stackSize : 0;
		count[2] = storage[2] != null ? storage[2].getItem() == TFCItems.thickLogs?4:storage[2].stackSize : 0;
		count[3] = storage[3] != null ? storage[3].getItem() == TFCItems.thickLogs?4:storage[3].stackSize : 0;
		return count[0] + count[1] + count[2] + count[3];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (storage[slot] != null)
		{
			ItemStack is;
			if (storage[slot].stackSize <= amount)
			{
				is = storage[slot];
				storage[slot] = null;
				return is;
			}

			if (storage[slot].stackSize == 0)
				storage[slot] = null;

			is = storage[slot].splitStack(amount);
			return is;
		}
		else
			return null;
	}

	public void ejectContents()
	{
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (storage[i] != null)
			{
				worldObj.spawnEntityInWorld(
						new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, storage[i]));
			}
		}
		extinguishFire();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 4;
	}

	@Override
	public String getInventoryName()
	{
		return "Log Pile";
	}

	@Override
	public int getSizeInventory()
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return storage[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}

	public void injectContents(int index, int count)
	{
		if (storage[index] != null)
		{
			storage[index] = new ItemStack(storage[index].getItem(), storage[index].stackSize + count,
					storage[index].getItemDamage());
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
		++logPileOpeners;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack is)
	{
		storage[slot] = is;
		if (is != null && is.stackSize > getInventoryStackLimit())
			is.stackSize = getInventoryStackLimit();
	}

	@Override
	public boolean canUpdate()
	{
		return true;
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote && isOnFire && getNumberOfLogs() == 16 && !worldObj.getBlock(xCoord, yCoord, zCoord).isFlammable(worldObj,
				xCoord, yCoord, zCoord, ForgeDirection.UP))
		{
			if(TFC_Time.getTotalTicks()%20==0)
			{
				List list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord-1, yCoord+1, zCoord-1, xCoord+1, yCoord+3, zCoord+1));
				for(Iterator it = list.iterator(); it.hasNext();)
				{
					EntityLivingBase entity = (EntityLivingBase) it.next();
					entity.attackEntityFrom(DamageSource.inFire, 200);
					entity.setFire(10);
				}
			}
			TileEntity te = worldObj.getTileEntity(xCoord, yCoord + 4, zCoord);
			if (te instanceof TEChimney)
			{
				int f = ((TEChimney) te).onFire;
				if (f < 10)
				{
					((TEChimney) te).onFire += 10;
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		isOnFire = nbt.getBoolean("isOnFire");
		fireTimer = nbt.getInteger("fireTimer");
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("isOnFire", isOnFire);
		nbt.setInteger("fireTimer", fireTimer);
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
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
		// TileEntityLogPile pile = this;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is)
	{
		return false;
	}

	public void lightNeighbors()
	{
		if (!isOnFire)
			return;
		Block block;
		blocksToBeSetOnFire = new ArrayDeque<Vector3f>();

		block = worldObj.getBlock(xCoord + 1, yCoord, zCoord);
		if (!TFC_Core.isValidCharcoalPitCover(block))
			blocksToBeSetOnFire.add(new Vector3f(xCoord + 1, yCoord, zCoord));

		block = worldObj.getBlock(xCoord - 1, yCoord, zCoord);
		if (!TFC_Core.isValidCharcoalPitCover(block))
			blocksToBeSetOnFire.add(new Vector3f(xCoord - 1, yCoord, zCoord));

		block = worldObj.getBlock(xCoord, yCoord, zCoord + 1);
		if (!TFC_Core.isValidCharcoalPitCover(block))
			blocksToBeSetOnFire.add(new Vector3f(xCoord, yCoord, zCoord + 1));

		block = worldObj.getBlock(xCoord, yCoord, zCoord - 1);
		if (!TFC_Core.isValidCharcoalPitCover(block))
			blocksToBeSetOnFire.add(new Vector3f(xCoord, yCoord, zCoord - 1));

		block = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		if (!TFC_Core.isValidCharcoalPitCover(block))
			blocksToBeSetOnFire.add(new Vector3f(xCoord, yCoord + 1, zCoord));

		block = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		if (!TFC_Core.isValidCharcoalPitCover(block))
			blocksToBeSetOnFire.add(new Vector3f(xCoord, yCoord - 1, zCoord));

		setOnFire(blocksToBeSetOnFire);
	}

	private void setOnFire(Queue<Vector3f> blocksOnFire)
	{
		while (blocksOnFire.size() > 0)
		{
			Vector3f blockOnFire = blocksOnFire.poll();
			if (worldObj.getBlock((int) blockOnFire.x, (int) blockOnFire.y, (int) blockOnFire.z) != Blocks.fire &&
					worldObj.getBlock((int) blockOnFire.x, (int) blockOnFire.y-1, (int) blockOnFire.z) != Blocks.air)
			{
				worldObj.setBlock((int) blockOnFire.x, (int) blockOnFire.y, (int) blockOnFire.z, Blocks.fire);
				worldObj.markBlockForUpdate((int) blockOnFire.x, (int) blockOnFire.y, (int) blockOnFire.z);
			}
		}
	}

	public void extinguishFire()
	{
		if (isOnFire)
		{
			if (worldObj.getBlock(xCoord + 1, yCoord, zCoord) == Blocks.fire)
			{
				worldObj.setBlockToAir(xCoord + 1, yCoord, zCoord);
				worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord);
			}
			if (worldObj.getBlock(xCoord - 1, yCoord, zCoord) == Blocks.fire)
			{
				worldObj.setBlockToAir(xCoord - 1, yCoord, zCoord);
				worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord);
			}
			if (worldObj.getBlock(xCoord, yCoord, zCoord + 1) == Blocks.fire)
			{
				worldObj.setBlockToAir(xCoord, yCoord, zCoord + 1);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord + 1);
			}
			if (worldObj.getBlock(xCoord, yCoord, zCoord - 1) == Blocks.fire)
			{
				worldObj.setBlockToAir(xCoord + 1, yCoord, zCoord - 1);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord - 1);
			}
			if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) == Blocks.fire)
			{
				worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
				worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
			}
			if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.fire)
			{
				worldObj.setBlockToAir(xCoord, yCoord - 1, zCoord);
				worldObj.markBlockForUpdate(xCoord, yCoord - 1, zCoord);
			}
			isOnFire = false;
		}
	}

	public void activateCharcoal()
	{
		this.fireTimer = (int) TFC_Time.getTotalHours();
		this.isOnFire = true;

		// Activate the adjacent log piles
		spreadFire(xCoord + 1, yCoord, zCoord); // East
		spreadFire(xCoord - 1, yCoord, zCoord); // West
		spreadFire(xCoord, yCoord + 1, zCoord); // Up
		spreadFire(xCoord, yCoord - 1, zCoord); // Down
		spreadFire(xCoord, yCoord, zCoord + 1); // South
		spreadFire(xCoord, yCoord, zCoord - 1); // North

		lightNeighbors();
	}

	private void spreadFire(int x, int y, int z)
	{
		if (worldObj.getBlock(x, y, z) == TFCBlocks.logPile && worldObj.getTileEntity(x, y, z) instanceof TELogPile)
		{
			TELogPile te = (TELogPile) worldObj.getTileEntity(x, y, z);
			if (!te.isOnFire)
			{
				te.activateCharcoal();
			}
		}
	}

	public void createCharcoal(int x, int y, int z, boolean forceComplete)
	{
		if (worldObj.getBlock(x, y, z) == TFCBlocks.logPile)
		{
			TELogPile te = (TELogPile) worldObj.getTileEntity(x, y, z);

			if (te.isOnFire && (te.fireTimer + TFCOptions.charcoalPitBurnTime < TFC_Time
					.getTotalHours() || forceComplete))
			{
				if (TFCBlocks.logPile.isFlammable(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP))
				{

					int count = te.getNumberOfLogs();
					te.clearContents();
					float percent = 25 + worldObj.rand.nextInt(26);
					count = (int) (count * (percent / 100));
					worldObj.setBlock(x, y, z, TFCBlocks.charcoal, count, 0x2);

					// Activate the surrounding log piles
					createCharcoal(x + 1, y, z, forceComplete);
					createCharcoal(x - 1, y, z, forceComplete);
					createCharcoal(x, y + 1, z, forceComplete);
					createCharcoal(x, y - 1, z, forceComplete);
					createCharcoal(x, y, z + 1, forceComplete);
					createCharcoal(x, y, z - 1, forceComplete);

					worldObj.notifyBlockOfNeighborChange(x, y, z, TFCBlocks.charcoal);
				}
				else
				{

					for (int i = -1; i < 2; i++)
					{
						for (int j = -1; j < 2; j++)
						{
							if (worldObj.getBlock(x + i, y + 2, z + j) == TFCBlocks.pottery)
							{
								TEPottery teP = (TEPottery) worldObj.getTileEntity(x + i, y + 2, z + j);
								teP.cookItems();
							}
						}
					}
					clearContents();
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				}
			}
		}
	}
}