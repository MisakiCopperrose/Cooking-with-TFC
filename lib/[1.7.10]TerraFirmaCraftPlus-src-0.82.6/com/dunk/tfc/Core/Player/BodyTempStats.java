package com.dunk.tfc.Core.Player;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BodyTempStats
{
	public int heatResistance;
	public int coldResistance;
	public float ambientTemperature;
	public int discomfort;
	public int timeRemaining;
	
	public int temporaryHeatProtection;
	public int temporaryColdProtection;
	
	public long tempColdTimeRemaining;
	public long tempHeatTimeRemaining;


	public void readNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("tempCompound"))
		{
			NBTTagCompound tempCompound = nbt.getCompoundTag("tempCompound");
			this.heatResistance = tempCompound.getInteger("heatResistance");
			this.coldResistance = tempCompound.getInteger("coldResistance");
			this.ambientTemperature = tempCompound.getFloat("ambientTemp");
			this.discomfort = tempCompound.getInteger("discomfort");
			this.timeRemaining = tempCompound.getInteger("timeRemaining");
			
			this.temporaryColdProtection = tempCompound.getInteger("tempColdProtection");
			this.temporaryHeatProtection = tempCompound.getInteger("tempHeatProtection");
			this.tempColdTimeRemaining= tempCompound.getLong("tempColdTime");
			this.tempHeatTimeRemaining= tempCompound.getLong("tempHeatTime");
		}
	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound nbt)
	{
		NBTTagCompound tempCompound = new NBTTagCompound();
		tempCompound.setInteger("heatResistance",this.heatResistance);
		tempCompound.setInteger("coldResistance",this.coldResistance);
		tempCompound.setFloat("ambientTemp",this.ambientTemperature);
		tempCompound.setInteger("discomfort", discomfort);
		tempCompound.setInteger("timeRemaining", timeRemaining);
		tempCompound.setInteger("tempColdProtection", this.temporaryColdProtection);
		tempCompound.setInteger("tempHeatProtection", this.temporaryHeatProtection);
		tempCompound.setLong("tempColdTime", this.tempColdTimeRemaining);
		tempCompound.setLong("tempHeatTime", this.tempHeatTimeRemaining);
		nbt.setTag("tempCompound", tempCompound);
	}

}
