package com.dunk.tfc.Core.Player;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryPlayerTFC extends InventoryPlayer
{

	public ItemStack[] extraEquipInventory = new ItemStack[TFC_Core.getExtraEquipInventorySize()];

	public InventoryPlayerTFC(EntityPlayer par1EntityPlayer)
	{
		super(par1EntityPlayer);
		this.player = par1EntityPlayer;
	}

	@Override
	public void damageArmor(float par1)
	{
		par1 /= 4.0F;
		if (par1 < 1.0F)
			par1 = 1.0F;

		for (int i = 0; i < this.armorInventory.length; ++i)
		{
			if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor)
			{
				this.armorInventory[i].damageItem((int) par1, this.player);
				if (this.armorInventory[i].stackSize == 0)
					this.armorInventory[i] = null;
			}
		}
	}

	@Override
	public int getSizeInventory()
	{
		return this.mainInventory.length + armorInventory.length + this.extraEquipInventory.length;
	}

	@Override
	public void readFromNBT(NBTTagList par1NBTTagList)
	{
		super.readFromNBT(par1NBTTagList);
		this.extraEquipInventory = new ItemStack[TFC_Core.getExtraEquipInventorySize()];

		NBTTagList extraList = player.getEntityData().getTagList("ExtraInventory", 10);

		for (int i = 0; i < extraList.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = extraList.getCompoundTagAt(i);
			byte slot = nbttagcompound.getByte("Slot");
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
			if(nbttagcompound.hasKey("Stack"))
			{
				itemstack.stackSize  = nbttagcompound.getInteger("Stack");
			}
			if (itemstack != null)
			{
				extraEquipInventory[slot] = itemstack;
			}
		}
	}

	@Override
	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		ItemStack[] aitemstack = this.mainInventory;
		if (par1 >= this.mainInventory.length + this.extraEquipInventory.length)
		{
			par1 -= this.mainInventory.length + this.extraEquipInventory.length;
			aitemstack = this.armorInventory;
		}
		else if (par1 >= this.mainInventory.length)
		{
			par1 -= aitemstack.length;
			aitemstack = this.extraEquipInventory;
		}
		return aitemstack[par1];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		ItemStack[] aitemstack = this.mainInventory;

		if (par1 >= this.mainInventory.length + this.extraEquipInventory.length)
		{
			aitemstack = this.armorInventory;
			par1 -= this.mainInventory.length + this.extraEquipInventory.length;
		}
		else if (par1 >= this.mainInventory.length)
		{
			par1 -= aitemstack.length;
			aitemstack = this.extraEquipInventory;
		}
		if (aitemstack[par1] != null)
		{
			ItemStack itemstack = aitemstack[par1];
			aitemstack[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public int clearInventory(Item item, int meta)
	{
		for (int i = 0; i < this.extraEquipInventory.length; i++)
		{
			if (extraEquipInventory[i] != null && (item == null || extraEquipInventory[i].getItem() == item)
					&& (meta <= -1 || extraEquipInventory[i].getItemDamage() == meta))
			{
				this.extraEquipInventory[i] = null;
			}
		}
		return super.clearInventory(item, meta);
	}

	@Override
	public void decrementAnimations()
	{
		for (int i = 0; i < this.extraEquipInventory.length; ++i)
		{
			if (this.extraEquipInventory[i] != null)
			{
				this.extraEquipInventory[i].updateAnimation(this.player.worldObj, this.player, i,
						this.currentItem == i);
			}
		}
		super.decrementAnimations();
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		
		if ( !this.player.worldObj.isRemote)
		{
		//	System.out.println("now decreasing");
		}
		ItemStack[] aitemstack = this.mainInventory;
		int originalSlot = par1;
		if (par1 >= this.mainInventory.length + this.extraEquipInventory.length)
		{
			aitemstack = this.armorInventory;
			par1 -= this.mainInventory.length + this.extraEquipInventory.length;
		}
		else if (par1 >= this.mainInventory.length)
		{
			par1 -= aitemstack.length;
			aitemstack = this.extraEquipInventory;
		}

		if (aitemstack[par1] != null)
		{
			ItemStack itemstack;
			//now we know that the item must not be null
		//	System.out.println("Removing " + par2 + " items from " + aitemstack[par1] + " in slot " + originalSlot
		//			+ " at " + TFC_Time.getTotalTicks());
			if (aitemstack[par1].stackSize <= par2)
			{
				itemstack = aitemstack[par1];
				aitemstack[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = aitemstack[par1].splitStack(par2);

				if (aitemstack[par1].stackSize == 0)
				{
					aitemstack[par1] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public void dropAllItems()
	{
		int i;

		for (i = 0; i < this.extraEquipInventory.length; ++i)
		{
			if (this.extraEquipInventory[i] != null)
			{
				this.player.func_146097_a(this.extraEquipInventory[i], true, false);
				this.extraEquipInventory[i] = null;
			}
		}
		super.dropAllItems();
	}

	@Override
	public boolean hasItemStack(ItemStack par1ItemStack)
	{
		int i;

		for (i = 0; i < this.extraEquipInventory.length; ++i)
		{
			if (this.extraEquipInventory[i] != null && this.extraEquipInventory[i].isItemEqual(par1ItemStack))
			{
				return true;
			}
		}
		return super.hasItemStack(par1ItemStack);
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		int originalSlot = par1;
		// For some reason, we're being told to set an itemstack in a slot with
		// a size of 0. This is bad, because that is not how itemstacks should
		// be removed.
		// The decrStackSize method should be used for that.
		// Thus, we set it to 1, because it was probably set to 0 erroneously.

		//if (par2ItemStack != null && par2ItemStack.stackSize == 0)
		//{
		//	System.out.println("Correcting a null item in slot " + par1);
		//	par2ItemStack.stackSize = 1;
		//}
		// We probably shouldn't be setting things to null here, either, right?
		//if (par2ItemStack == null)
		//{
		//	return;
	//	}
	//	if(this.player.worldObj.isRemote)
		//	System.out.println("updating the client");
		//Try this out, where we only unlock once we've received an update about all of the clothes.
		if(PlayerManagerTFC.getInstance()
				.getPlayerInfoFromName(player.getDisplayName()) != null && PlayerManagerTFC.getInstance()
				.getPlayerInfoFromName(player.getDisplayName()).clothingWetLock)
		{			
			PlayerManagerTFC.getInstance()
			.getPlayerInfoFromName(player.getDisplayName()).clothingWetLock = false;
		//	System.out.println("trying to set to false");
			AbstractPacket pkt1 = new PlayerUpdatePacket(player, 6);
			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt1);
		}
		else if(PlayerManagerTFC.getInstance()
				.getPlayerInfoFromName(player.getDisplayName()) != null)
		{
//		System.out.println("the lock was " + PlayerManagerTFC.getInstance()
		//		.getPlayerInfoFromName(player.getDisplayName()).clothingWetLock);
		}
		else
		{
		//	System.out.println("was it null? " + PlayerManagerTFC.getInstance()
		//		.getPlayerInfoFromName(player.getDisplayName()));
		}
		ItemStack[] aitemstack = this.mainInventory;

		if (par1 >= this.mainInventory.length + this.extraEquipInventory.length)
		{
			par1 -= this.mainInventory.length + this.extraEquipInventory.length;
			aitemstack = this.armorInventory;
		}
		else if (par1 >= this.mainInventory.length)
		{
			par1 -= aitemstack.length;
			aitemstack = this.extraEquipInventory;
		}
		if (aitemstack == this.extraEquipInventory && !this.player.worldObj.isRemote)
		{
		//	System.out.println("now");
		//	System.out.println(aitemstack[par1] + ", " + par2ItemStack);
			if (par2ItemStack == null || par2ItemStack.stackSize == 0)
			{
				return;
			}
		}
		if (aitemstack == this.extraEquipInventory)
		{
		//	System.out.println("The extra inventory slot is being set! It was " + aitemstack[par1] + " and  It is now "
		//			+ par2ItemStack + " at " + TFC_Time.getTotalTicks());
			if (aitemstack[par1] != null && (par2ItemStack == null || par2ItemStack.stackSize == 0))
			{
			//	System.out.println("It might be deleted!");
				// return;
			}
		}
		// If the server doesn't know what we're talking about, ignore it?
		// if(aitemstack == this.extraEquipInventory &&
		// PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(this.player)
		// != null && this.player.worldObj.isRemote == true &&
		// Math.abs(TFC_Time.getTotalTicks() -
		// PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(this.player).lastClothingPacket)
		// <= 100)
		// {
		// return;
		// }
		//System.out.println("Setting " + par2ItemStack + " on top of " + aitemstack[par1] + " in slot " + originalSlot
		//		+ " at " + TFC_Time.getTotalTicks());
		if (par2ItemStack != null && aitemstack[par1] == null)
		{
		//	System.out.println("bad");
		}
		aitemstack[par1] = par2ItemStack;

	}

	/*
	 * This method is currently never being called properly. The copying of the
	 * extraEquipment is being handled with
	 * com.dunk.tfc.Core.Player.PlayerInfo.tempEquipment
	 * com.dunk.tfc.Core.Player.PlayerTracker.onPlayerRespawn(
	 * PlayerRespawnEvent) and
	 * com.dunk.tfc.Handlers.EntityLivingHandler.onEntityDeath(LivingDeathEvent)
	 */
	@Override
	public void copyInventory(InventoryPlayer par1InventoryPlayer)
	{
		if (par1InventoryPlayer instanceof InventoryPlayerTFC)
		{
			this.copyInventoryTFC((InventoryPlayerTFC) par1InventoryPlayer);
		}
		else
		{
			super.copyInventory(par1InventoryPlayer);
		}
	}

	public void copyInventoryTFC(InventoryPlayerTFC par1InventoryPlayer)
	{
		int i;

		for (i = 0; i < this.extraEquipInventory.length; ++i)
		{
			this.extraEquipInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.extraEquipInventory[i]);
		}
		super.copyInventory(par1InventoryPlayer);
	}

	@Override
	public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
	{
		super.writeToNBT(par1NBTTagList);

		int i;
		NBTTagCompound nbt;
		NBTTagList tagList = new NBTTagList();
		for (i = 0; i < extraEquipInventory.length; i++)
		{
			ItemStack is = extraEquipInventory[i];
			if (is != null)
			{
				nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) i);
				is.writeToNBT(nbt);
				nbt.setInteger("Stack", is.stackSize);
				tagList.appendTag(nbt);
			}
		}
		player.getEntityData().setTag("ExtraInventory", tagList);
		return par1NBTTagList;
	}
}
