package com.dunk.tfc.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TEPlasteredBlock extends NetworkTileEntity
{
	public Block block;
	public int meta;
	
	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		block = Block.getBlockById(nbt.getInteger("block"));
		meta = nbt.getInteger("meta");
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("block", Block.getIdFromBlock(block));
		nbt.setInteger("meta", meta);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		block = Block.getBlockById(nbt.getInteger("block"));
		meta = nbt.getInteger("meta");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("block", Block.getIdFromBlock(block));
		nbt.setInteger("meta", meta);
	}

}
