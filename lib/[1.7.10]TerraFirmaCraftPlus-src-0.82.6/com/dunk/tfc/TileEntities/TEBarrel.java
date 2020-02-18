package com.dunk.tfc.TileEntities;

import java.util.Random;
import java.util.Stack;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.Flora.BlockBerryBush;
import com.dunk.tfc.Containers.ContainerBarrel;
import com.dunk.tfc.Containers.ContainerChestTFC;
import com.dunk.tfc.Containers.Slots.SlotChest;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.FloraIndex;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.Tools.ItemCustomBucketMilk;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Crafting.BarrelAlcoholRecipe;
import com.dunk.tfc.api.Crafting.BarrelBriningRecipe;
import com.dunk.tfc.api.Crafting.BarrelDyeRecipe;
import com.dunk.tfc.api.Crafting.BarrelFireRecipe;
import com.dunk.tfc.api.Crafting.BarrelLiquidToLiquidRecipe;
import com.dunk.tfc.api.Crafting.BarrelManager;
import com.dunk.tfc.api.Crafting.BarrelMultiItemRecipe;
import com.dunk.tfc.api.Crafting.BarrelPreservativeRecipe;
import com.dunk.tfc.api.Crafting.BarrelRecipe;
import com.dunk.tfc.api.Crafting.BarrelVinegarRecipe;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Interfaces.IFood;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.oredict.OreDictionary;

public class TEBarrel extends NetworkTileEntity implements IInventory, ISidedInventory
{
	public FluidStack fluid;
	public byte rotation;
	public int barrelType;
	public int mode;
	public ItemStack[] storage;
	private boolean sealed;
	public int sealtime;
	public int unsealtime;
	private int processTimer;
	
	private SlotChest testSlot1;
	private SlotChest testSlot2;

	private int fireHeatTicks = 0;

	public static final int MODE_IN = 0;
	public static final int MODE_OUT = 1;
	public static final int INPUT_SLOT = 0;
	public BarrelRecipe recipe;
	// temporary field. No need to save
	public boolean shouldDropItem = true;
	private BarrelRecipe oldRecipe;
	private boolean initiated = false;

	// -1 = none, 0 = +x, 1 = +z, 2 = -x, 3 = -z
	private int distillationMode = -1;
	// -1 = none, 0 = +x, 1 = +z, 2 = -x, 3 = -z
	private int distillationReceivingMode = -1;

	public TEBarrel()
	{
		storage = new ItemStack[12];
		testSlot1 = new SlotChest(null, 0, 0, 0).setSize(EnumSize.LARGE).addItemException(ContainerBarrel.getExceptions());
		testSlot2 = new SlotChest(null, 0, 0, 0).setSize(EnumSize.LARGE).addItemException(ContainerChestTFC.getExceptions());
	}

	public boolean getSealed()
	{
		return sealed;
	}

	public int getTechLevel()
	{
		return 1;
	}

	public void clearInventory()
	{
		storage = new ItemStack[12];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public void setSealed()
	{
		sealed = true;
	}

	public void setUnsealed(String reason)
	{
		if ("killing fuse".equals(reason))
			sealed = false;
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (storage[i] != null)
		{
			if (storage[i].stackSize <= j)
			{
				ItemStack is = storage[i];
				storage[i] = null;
				return is;
			}
			ItemStack isSplit = storage[i].splitStack(j);
			if (storage[i].stackSize == 0)
				storage[i] = null;
			return isSplit;
		}
		else
		{
			return null;
		}
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

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Barrel";
	}

	@Override
	public int getSizeInventory()
	{
		return 12;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return storage[i];
	}

	public int getInvCount()
	{
		int count = 0;
		for (ItemStack is : storage)
		{
			if (is != null)
				count++;
		}
		if (storage[INPUT_SLOT] != null && count == 1)
			return 0;
		return count;
	}

	public int getGunPowderCount()
	{
		int count = 0;
		for (ItemStack is : storage)
		{
			if (is != null && is.getItem() == Items.gunpowder)
				count += is.stackSize;
		}
		return count;
	}

	public boolean canAcceptLiquids()
	{
		return this.getInvCount() == 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is)
	{
		if (!ItemStack.areItemStacksEqual(storage[i], is))
		{
			storage[i] = is;
			if (i == 0)
			{
				processItems();
				// if (!getSealed())
				// this.unsealtime = (int) TFC_Time.getTotalHours();
			}
		}
	}

	public int getFluidLevel()
	{
		if (fluid != null)
			return fluid.amount;
		return 0;
	}

	public ItemStack getInputStack()
	{
		return storage[INPUT_SLOT];
	}

	public FluidStack getFluidStack()
	{
		return this.fluid;
	}

	public int getMaxLiquid()
	{
		return 10000;
	}

	public boolean addLiquid(FluidStack inFS)
	{
		if (inFS != null)
		{
			// We dont want very hot liquids stored here so if they are much
			// hotter than boiling water, we prevent it.
			if (inFS.getFluid() != null && inFS.getFluid().getTemperature(inFS) > Global.HOT_LIQUID_TEMP)
				return false;

			if (fluid == null)
			{
				fluid = inFS.copy();
				if (fluid.amount > this.getMaxLiquid())
				{
					fluid.amount = getMaxLiquid();
					inFS.amount = inFS.amount - this.getMaxLiquid();

				}
				else
					inFS.amount = 0;
			}
			else
			{
				// check if the barrel is full or if the fluid being added does
				// not match the barrel liquid
				if (fluid.amount == getMaxLiquid() || !fluid.isFluidEqual(inFS))
					return false;

				int a = fluid.amount + inFS.amount - getMaxLiquid();
				fluid.amount = Math.min(fluid.amount + inFS.amount, getMaxLiquid());
				if (a > 0)
					inFS.amount = a;
				else
					inFS.amount = 0;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}

		return false;
	}

	public ItemStack addLiquid(ItemStack is)
	{
		if (is == null || is.stackSize > 1)
			return is;
		if (FluidContainerRegistry.isFilledContainer(is))
		{
			FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(is);
			if (addLiquid(fs))
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return FluidContainerRegistry.drainFluidContainer(is);
			}
		}
		else if (is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if (isfs != null && addLiquid(isfs))
			{
				((IFluidContainerItem) is.getItem()).drain(is, is.getMaxDamage(), true);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		return is;
	}

	/**
	 * This attempts to remove a portion of the water in this container and put
	 * it into a valid Container Item
	 */
	public ItemStack removeLiquid(ItemStack is)
	{
		if (is == null || is.stackSize > 1)
			return is;
		if (FluidContainerRegistry.isEmptyContainer(is))
		{
			ItemStack out = FluidContainerRegistry.fillFluidContainer(fluid, is);
			if (out != null)
			{
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(out);
				fluid.amount -= fs.amount;
				is = null;
				if (fluid.amount == 0)
				{
					fluid = null;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return out;
			}
		}
		else if (fluid != null && is.getItem() instanceof IFluidContainerItem)
		{
			FluidStack isfs = ((IFluidContainerItem) is.getItem()).getFluid(is);
			if (isfs == null || fluid.isFluidEqual(isfs))
			{
				fluid.amount -= ((IFluidContainerItem) is.getItem()).fill(is, fluid, true);
				if (fluid.amount == 0)
					fluid = null;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		return is;
	}

	/**
	 * This removes a specified amount of liquid from the container and updates
	 * the block.
	 */
	public void drainLiquid(int amount)
	{
		if (!getSealed() && this.getFluidStack() != null)
		{
			this.getFluidStack().amount -= amount;
			if (this.getFluidStack().amount <= 0)
				this.actionEmpty();
			else
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public int getLiquidScaled(int i)
	{
		if (fluid != null)
			return fluid.amount * i / getMaxLiquid();
		return 0;
	}

	public boolean actionSeal(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", true);
		nbt.setByte("tab", (byte) tab);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = true;
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}

	public boolean actionUnSeal(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("seal", false);
		nbt.setByte("tab", (byte) tab);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
		sealed = false;
		this.unsealtime = (int) TFC_Time.getTotalHours();
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
		return true;
	}

	public void actionEmpty()
	{
		fluid = null;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("fluidID", (byte) -1);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	public void actionMode()
	{
		mode = mode == 0 ? 1 : 0;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("mode", (byte) mode);
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	public void actionSwitchTab(int tab, EntityPlayer player)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("tab", (byte) tab);
		nbt.setString("player", player.getCommandSenderName());
		this.broadcastPacketInRange(this.createDataPacket(nbt));
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if(this.sealed)
			return false;
		if(this.fluid != null)
		{
			if(this.storage != null && (this.storage[0] == null || (itemstack != null && this.storage[0] != null && this.storage[0].getItem() == itemstack.getItem() && this.storage[0].stackSize < this.storage[0].getMaxStackSize())))			
			{//Assume we're in liquid mode
			return this.testSlot1.isItemValid(itemstack);
			}
			return false;
		}
		return this.testSlot2.isItemValid(itemstack);
	}

	public int getHeatTicks()
	{
		return this.fireHeatTicks;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("Sealed", sealed);
		nbt.setInteger("SealTime", sealtime);
		nbt.setInteger("UnsealTime", unsealtime);
		nbt.setInteger("barrelType", barrelType);
		nbt.setInteger("fireHeatTicks", this.fireHeatTicks);
		nbt.setInteger("distillationMode", distillationMode);
		nbt.setInteger("distillationReceivingMode", distillationReceivingMode);
		// nbt.setInteger("mode", mode);
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if (fluid != null)
			fluid.writeToNBT(fluidNBT);
		nbt.setTag("fluidNBT", fluidNBT);
		nbt.setByte("rotation", rotation);
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		sealed = nbt.getBoolean("Sealed");
		sealtime = nbt.getInteger("SealTime");
		unsealtime = nbt.getInteger("UnsealTime");
		barrelType = nbt.getInteger("barrelType");
		fireHeatTicks = nbt.getInteger("fireHeatTicks");
		if (nbt.hasKey("distillationMode"))
		{
			setDistillationMode(nbt.getInteger("distillationMode"));
		}
		else
		{
			setDistillationMode(-1);
		}
		if (nbt.hasKey("distillationReceivingMode"))
		{
			setDistillationReceivingMode(nbt.getInteger("distillationReceivingMode"));
		}
		else
		{
			setDistillationReceivingMode(-1);
		}
		// mode = nbt.getInteger("mode");
		rotation = nbt.getByte("rotation");
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		storage = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < storage.length)
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		recipe = BarrelManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack(), getSealed(), getTechLevel(), isHeated(),this);
	}

	public void readFromItemNBT(NBTTagCompound nbt)
	{
		barrelType = nbt.getInteger("barrelType");
		fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluidNBT"));
		sealed = nbt.getBoolean("Sealed");
		sealtime = nbt.getInteger("SealTime");
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbt1.getByte("Slot");
			if (byte0 >= 0 && byte0 < storage.length)
				setInventorySlotContents(byte0, ItemStack.loadItemStackFromNBT(nbt1));
		}
	}

	public void updateGui()
	{
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		// validate();
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt)
	{
		this.rotation = nbt.getByte("rotation");
		this.sealed = nbt.getBoolean("sealed");
		this.sealtime = nbt.getInteger("SealTime");
		this.fireHeatTicks = nbt.getInteger("fireHeatTicks");
		if (nbt.hasKey("distillationMode"))
		{
			setDistillationMode(nbt.getInteger("distillationMode"));
		}
		else
		{
			setDistillationMode(-1);
		}
		if (nbt.hasKey("distillationReceivingMode"))
		{
			setDistillationReceivingMode(nbt.getInteger("distillationReceivingMode"));
		}
		else
		{
			setDistillationReceivingMode(-1);
		}
		barrelType = nbt.getInteger("barrelType");
		if (nbt.getInteger("fluid") != -1)
		{
			if (fluid != null)
				fluid.amount = nbt.getInteger("fluidAmount");
			else
				fluid = new FluidStack(nbt.getInteger("fluid"), nbt.getInteger("fluidAmount"));
		}
		else
		{
			fluid = null;
		}
		this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt)
	{
		nbt.setByte("rotation", rotation);
		nbt.setBoolean("sealed", sealed);
		nbt.setInteger("SealTime", sealtime);
		nbt.setInteger("fluid", fluid != null ? fluid.getFluidID() : -1);
		nbt.setInteger("fluidAmount", fluid != null ? fluid.amount : 0);
		nbt.setInteger("barrelType", barrelType);
		nbt.setInteger("fireHeatTicks", this.fireHeatTicks);
		nbt.setInteger("distillationMode", this.distillationMode);
		nbt.setInteger("distillationReceivingMode", this.distillationReceivingMode);
	}

	public int getDistillationMode()
	{
		return this.distillationMode;
	}

	public void setDistillationMode(int i)
	{
		this.distillationMode = i;
		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("distillationMode", distillationMode);
			this.broadcastPacketInRange(this.createDataPacket(nbt));
		}
	}

	public int getDistillationReceivingMode()
	{
		return this.distillationReceivingMode;
	}

	public void setDistillationReceivingMode(int i)
	{

		this.distillationReceivingMode = i;
		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("distillationReceivingMode", distillationReceivingMode);
			this.broadcastPacketInRange(this.createDataPacket(nbt));
		}
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt)
	{
		if (nbt.hasKey("fluidID"))
		{
			if (nbt.getByte("fluidID") == -1)
				fluid = null;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		if (!worldObj.isRemote)
		{
			if (nbt.hasKey("mode"))
			{
				mode = nbt.getByte("mode");
			}
			else if (nbt.hasKey("seal"))
			{
				sealed = nbt.getBoolean("seal");
				if (!sealed)
				{
					unsealtime = (int) TFC_Time.getTotalHours();
					sealtime = 0;
				}
				else
				{
					sealtime = (int) TFC_Time.getTotalHours();
					unsealtime = 0;
				}
				initiated = false;
				// Broadcast the seal time to update the client
				NBTTagCompound timeTag = new NBTTagCompound();
				timeTag.setInteger("SealTime", sealtime);
				timeTag.setInteger("UnsealTime", unsealtime);
				timeTag.setInteger("fireHeatTicks", fireHeatTicks);
				timeTag.setInteger("distillationMode", distillationMode);
				timeTag.setInteger("distillationReceivingMode", distillationReceivingMode);
				timeTag.setBoolean("hasRecipe", recipe != null);
				this.broadcastPacketInRange(this.createDataPacket(timeTag));

				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			if (nbt.hasKey("tab"))
			{
				int tab = nbt.getByte("tab");
				switchTab(worldObj.getPlayerEntityByName(nbt.getString("player")), tab);
			}
		}
		else
		{
			// Get the seal time for the client display
			if (nbt.hasKey("SealTime"))
				sealtime = nbt.getInteger("SealTime");
			if (nbt.hasKey("UnsealTime"))
				unsealtime = nbt.getInteger("UnsealTime");
			if (nbt.hasKey("fireHeatTicks"))
				fireHeatTicks = nbt.getInteger("fireHeatTicks");
			if (nbt.hasKey("distillationMode"))
				distillationMode = nbt.getInteger("distillationMode");
			if (nbt.hasKey("distillationReceivingMode"))
				distillationReceivingMode = nbt.getInteger("distillationReceivingMode");
			if (nbt.hasKey("hasRecipe") && !nbt.getBoolean("hasRecipe"))
				recipe = null;
		}
	}

	public TEBarrel getReceivingVessel()
	{
		// If we have a receiver, get it.
		if (this.getTechLevel() != 0 || worldObj == null)
		{
			return null;
		}
		int x1, z1;
		switch (distillationMode)
		{
		case 0:
			x1 = 1;
			z1 = 0;
			break;
		case 1:
			x1 = 0;
			z1 = 1;
			break;
		case 2:
			x1 = -1;
			z1 = 0;
			break;
		case 3:
			x1 = 0;
			z1 = -1;
			break;
		default:
			return null;
		}
		if (worldObj.getBlock(xCoord + x1, yCoord - 1, zCoord + z1) == TFCBlocks.vessel)
		{
			return (TEBarrel) worldObj.getTileEntity(xCoord + x1, yCoord - 1, zCoord + z1);
		}
		return null;
	}

	// Determines if the receiver could accept the fluid from this recipe
	public boolean isReceiverValidForRecipe(BarrelFireRecipe bfr)
	{
		TEBarrel receiver = getReceivingVessel();
		if (receiver == null || bfr == null || bfr.recipeOutFluid == null)
		{
			return false;
		}
		if (bfr.recipeOutFluid.isFluidEqual(receiver.fluid) || receiver.fluid == null)
		{
			return true;
		}
		return false;
	}

	protected void switchTab(EntityPlayer player, int tab)
	{
		if (player != null)
			if (tab == 0)
				player.openGui(TerraFirmaCraft.instance, 35, worldObj, xCoord, yCoord, zCoord);
			else if (tab == 1)
				player.openGui(TerraFirmaCraft.instance, 36, worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity()
	{
		if (this.fluid != null && this.fluid.amount == 0)
		{

			this.fluid = null;
			recipe = BarrelManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack(), this.sealed, getTechLevel(), isHeated(),this);

		}
		if (!worldObj.isRemote)
		{

			// Distillation
			if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == TFCBlocks.firepit && this.getTechLevel() == 0 && this.sealed && this.storage[INPUT_SLOT] != null
			&& this.storage[INPUT_SLOT].getItem() == TFCItems.clayBlowpipe && this.storage[INPUT_SLOT].getItemDamage() == 1)
			{
				if (this.distillationMode == -1)
				{
					TEBarrel otherTE;
					if (this.worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord) == TFCBlocks.vessel)
					{
						otherTE = (TEBarrel) this.worldObj.getTileEntity(xCoord + 1, yCoord - 1, zCoord);
						if (otherTE.getDistillationReceivingMode() == -1)
						{
							// Check for valid distillation
							otherTE.setDistillationReceivingMode(2);
							setDistillationMode(0);
						}
					}
					else if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord + 1) == TFCBlocks.vessel)
					{
						otherTE = (TEBarrel) this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 1);
						if (otherTE.getDistillationReceivingMode() == -1)
						{
							// Check for valid distillation
							otherTE.setDistillationReceivingMode(3);
							setDistillationMode(1);
						}
					}
					else if (this.worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord) == TFCBlocks.vessel)
					{
						otherTE = (TEBarrel) this.worldObj.getTileEntity(xCoord - 1, yCoord - 1, zCoord);
						if (otherTE.getDistillationReceivingMode() == -1)
						{
							// Check for valid distillation
							otherTE.setDistillationReceivingMode(0);
							setDistillationMode(2);
						}
					}
					else if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord - 1) == TFCBlocks.vessel)
					{
						otherTE = (TEBarrel) this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 1);
						if (otherTE.getDistillationReceivingMode() == -1)
						{
							// Check for valid distillation
							otherTE.setDistillationReceivingMode(1);
							setDistillationMode(3);
						}
					}
				}
				else
				{
					int x1, z1;
					switch (distillationMode)
					{
					case 0:
						x1 = 1;
						z1 = 0;
						break;
					case 1:
						x1 = 0;
						z1 = 1;
						break;
					case 2:
						x1 = -1;
						z1 = 0;
						break;
					case 3:
						x1 = 0;
						z1 = -1;
						break;
					default:
						x1 = 0;
						z1 = 0;
					}
					if (worldObj.getBlock(xCoord + x1, yCoord - 1, zCoord + z1) == TFCBlocks.vessel)
					{
						TEBarrel otherTE = (TEBarrel) worldObj.getTileEntity(xCoord + x1, yCoord - 1, zCoord + z1);
						if ((otherTE.getDistillationReceivingMode() + 2) % 4 != distillationMode)
						{
							setDistillationMode(-1);
						}
					}
					else
					{
						setDistillationMode(-1);
					}
				}
			}
			else if (distillationMode != -1)
			{
				TEBarrel otherTE;
				int x, z;
				switch (distillationMode)
				{
				case 0:
					x = 1;
					z = 0;
					break;
				case 1:
					x = 0;
					z = 1;
					break;
				case 2:
					x = -1;
					z = 0;
					break;
				case 3:
					x = 0;
					z = -1;
					break;
				default:
					x = 0;
					z = 0;
				}
				if (this.worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) == TFCBlocks.vessel)
				{
					otherTE = (TEBarrel) this.worldObj.getTileEntity(xCoord + x, yCoord - 1, zCoord + z);
					// If the other TE is receiving from us
					if ((otherTE.getDistillationReceivingMode() + 2) % 4 == distillationMode)
					{
						otherTE.setDistillationReceivingMode(-1);
					}
				}
				setDistillationMode(-1);
			}
			if (this.distillationReceivingMode != -1)
			{
				TEBarrel otherTE;
				int x, z;
				switch (distillationReceivingMode)
				{
				case 0:
					x = 1;
					z = 0;
					break;
				case 1:
					x = 0;
					z = 1;
					break;
				case 2:
					x = -1;
					z = 0;
					break;
				case 3:
					x = 0;
					z = -1;
					break;
				default:
					x = 0;
					z = 0;
				}
				if (this.worldObj.getBlock(xCoord + x, yCoord + 1, zCoord + z) == TFCBlocks.vessel)
				{
					otherTE = (TEBarrel) this.worldObj.getTileEntity(xCoord + x, yCoord + 1, zCoord + z);
					// If the other TE is receiving from us
					if ((otherTE.getDistillationMode() + 2) % 4 != distillationReceivingMode)
					{
						setDistillationReceivingMode(-1);
					}
				}
				else
				{
					setDistillationReceivingMode(-1);
				}
			}
			ItemStack itemstack = storage[INPUT_SLOT];
			BarrelPreservativeRecipe preservative = BarrelManager.getInstance().findMatchingPreservativeRepice(this, itemstack, fluid, sealed);

			// If we're doing a fire recipe and the fire isn't lit, we need to
			// kill it
			if (this instanceof TEVessel /* && */)
			{
				if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) != TFCBlocks.firepit)
				{
					this.fireHeatTicks = 0;
				}
				else
				{
					TEFirepit te = (TEFirepit) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
					if (te.fireTemp < 50)
					{
						this.fireHeatTicks = 0;
					}
					else if (this.recipe != null && this.recipe instanceof BarrelFireRecipe)
					{
						// fire heat ticks can't exceed 24000
						this.fireHeatTicks = Math.min(fireHeatTicks + 1, 24000);
					}
					else if (initiated && (this.recipe == null || (this.recipe != null && !(this.recipe instanceof BarrelFireRecipe))) && this.blockMetadata >= 0)
					{
						this.fireHeatTicks = 0;
					}
				}
			}

			if (this instanceof TEVessel && storage[4] != null && storage[4].getItem() instanceof ItemFoodTFC && !this.sealed)
			{
				boolean growFruitSapling = true;
				for (int i = 0; i < 9 && growFruitSapling; i++)
				{
					if (storage[i] != null)
					{
						if (i != 4)
						{
							if (!TFC_Core.isDirt(Block.getBlockFromItem(storage[i].getItem())) || storage[i].stackSize < 2)
							{
								growFruitSapling = false;
							}
						}
						else if (storage[i].getItem() instanceof ItemFoodTFC && FloraManager.getInstance().findMatchingIndex(storage[i].getItem()) == null)
						{
							growFruitSapling = false;
						}
					}
					else
					{
						growFruitSapling = false;
					}
				}
				float dec = Food.getDecay(storage[4]);
				boolean ignoreDecay = TFCOptions.decayMultiplier == 0 || TFCOptions.foodDecayRate <= 1;
				if (growFruitSapling && (((dec / Food.getWeight(storage[4])) * 100) >= 50 || ignoreDecay) && Food.getWeight(storage[4]) >= 40)
				{
					FloraIndex fi = FloraManager.getInstance().findMatchingIndex(storage[4].getItem());
					if (fi != null)
					{
						if (fi.sapling != null)
						{
							setInventorySlotContents(4, fi.sapling.copy());
						}
						else
						{
							setInventorySlotContents(4, new ItemStack(TFCBlocks.berryBush, 1, BlockBerryBush.getMetaFromString(fi.type)));
						}
						for (int i = 0; i < 9 && growFruitSapling; i++)
						{
							if (i != 4)
							{
								this.decrStackSize(i, 1);
							}
						}
					}
				}
			}
			if (itemstack != null && fluid != null && fluid.getFluid() == TFCFluids.FRESHWATER)
			{
				if (TFC_ItemHeat.hasTemp(itemstack))
				{
					float temp = TFC_ItemHeat.getTemp(itemstack);
					if (fluid.amount >= 1 && temp > 1)
					{
						temp -= 50;
						fluid.amount -= 1;
						TFC_ItemHeat.setTemp(itemstack, temp);
						TFC_ItemHeat.handleItemHeat(itemstack);
					}
				}
			}
			if (fluid != null && itemstack != null && itemstack.getItem() instanceof IFood)
			{
				float w = Food.getWeight(itemstack);
				if (fluid.getFluid() == TFCFluids.VINEGAR)
				{
					// If the food is brined then we attempt to pickle it
					if (Food.isBrined(itemstack) && !Food.isPickled(itemstack) && w / fluid.amount <= Global.FOOD_MAX_WEIGHT / this.getMaxLiquid() && this.getSealed()
					&& sealtime != 0 && TFC_Time.getTotalHours() - sealtime >= 4)
					{
						fluid.amount -= 1 * w;
						Food.setPickled(itemstack, true);
					}
				}
			}

			if (preservative == null)
			{
				// No preservative was matched - decay normally
				TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
			}
			else
			{
				float env = preservative.getEnvironmentalDecayFactor();
				float base = preservative.getBaseDecayModifier();
				if (Float.isNaN(env) || env < 0.0)
				{
					TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord);
				}
				else if (Float.isNaN(base) || base < 0.0)
				{
					TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, env);
				}
				else
				{
					TFC_Core.handleItemTicking(this, this.worldObj, xCoord, yCoord, zCoord, env, base);
				}
			}

			// Fill the barrel when its raining.
			if (!this.getSealed() && TFC_Core.isExposedToRain(worldObj, xCoord, yCoord, zCoord))
			{
				int count = getInvCount();
				if (count == 0 || count == 1 && this.getInputStack() != null)
				{
					if (this.fluid == null)
						fluid = new FluidStack(TFCFluids.FRESHWATER, 1);
					else if (this.fluid != null && fluid.getFluid() == TFCFluids.FRESHWATER)
						fluid.amount = Math.min(fluid.amount + 1, getMaxLiquid());
				}
			}

			// We only want to bother ticking food once per 5 seconds to keep
			// overhead low.
			processTimer++;
			if (processTimer > 100)
			{
				processItems();
				processTimer = 0;
			}

			// Here we handle item stacks that are too big for MC to handle such
			// as when making mortar.
			// If the stack is > its own max stack size then we split it and add
			// it to the invisible solid storage area or
			// spawn the item in the world if there is no room left.
			if (this.getFluidLevel() > 0 && getInputStack() != null)
			{
				int count = 1;
				while (this.getInputStack().stackSize > getInputStack().getMaxStackSize())
				{
					ItemStack is = getInputStack().splitStack(getInputStack().getMaxStackSize());
					if (count < this.storage.length && this.getStackInSlot(count) == null)
					{
						this.setInventorySlotContents(count, is);
					}
					else
					{
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, is));
					}
					count++;
				}
			}

			// Move any items in the solid storage slots to the main slot if
			// they exist and the barrel has liquid.
			else if (this.getFluidLevel() > 0 && getInputStack() == null && this.getInvCount() > 0)
			{
				for (int i = 0; i < storage.length; i++)
				{
					if (storage[i] != null)
					{
						storage[INPUT_SLOT] = storage[i].copy();
						storage[i] = null;
						break;
					}

				}
			}

			// Reset our fluid if all of the liquid is gone.
			if (fluid != null && fluid.amount == 0)
				fluid = null;

			// Handle adding fluids to the barrel if the barrel is currently in
			// input mode.
			if (mode == MODE_IN)
			{
				ItemStack container = getInputStack();
				FluidStack inLiquid = FluidContainerRegistry.getFluidForFilledItem(container);

				if (container != null && container.getItem() instanceof IFluidContainerItem)
				{
					FluidStack isfs = ((IFluidContainerItem) container.getItem()).getFluid(container);
					if (isfs != null && addLiquid(isfs))
					{
						((IFluidContainerItem) container.getItem()).drain(container, ((IFluidContainerItem) container.getItem()).getCapacity(container), true);
					}
				}
				else if (inLiquid != null && container != null && container.stackSize == 1)
				{
					if (addLiquid(inLiquid))
					{
						this.setInventorySlotContents(0, FluidContainerRegistry.drainFluidContainer(container));
					}
				}
			}
			// Drain liquid from the barrel to a container if the barrel is in
			// output mode.
			else if (mode == MODE_OUT)
			{
				ItemStack container = getInputStack();

				if (container != null && fluid != null && container.getItem() instanceof IFluidContainerItem)
				{
					FluidStack isfs = ((IFluidContainerItem) container.getItem()).getFluid(container);
					if (isfs == null || fluid.isFluidEqual(isfs))
					{
						fluid.amount -= ((IFluidContainerItem) container.getItem()).fill(container, fluid, true);
						if (fluid.amount == 0)
							fluid = null;
					}
				}
				else if (FluidContainerRegistry.isEmptyContainer(container))
				{
					ItemStack fullContainer = this.removeLiquid(getInputStack());
					if (fullContainer.getItem() == TFCItems.woodenBucketMilk)
					{
						ItemCustomBucketMilk.createTag(fullContainer, 20f);
					}
					this.setInventorySlotContents(0, fullContainer);
				}
			}
		}
	}

	public boolean isHeated()
	{
		if (worldObj == null || worldObj.getBlock(xCoord, yCoord - 1, zCoord) != TFCBlocks.firepit)
		{
			return false;
		}
		TEFirepit te = (TEFirepit) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		if (te.fireTemp < 50)
		{
			return false;
		}
		return true;
	}

	public void processItems()
	{
		if (this.getInvCount() == 0 || this.distillationMode > -1)
		{
			// Before we handle standard barrel processing we have to see if we
			// are handling cheese and run that code first
			// since it has to be handled specially.
			boolean isCheese = handleCheese();
			boolean heated = isHeated();
			if (getFluidStack() != null && !isCheese)
			{
				// oldRecipe = recipe;
				recipe = BarrelManager.getInstance().findMatchingRecipe(getInputStack(), getFluidStack(), this.sealed, getTechLevel(), heated,this);
				if (oldRecipe != recipe && initiated)
				{
					// System.out.println("seal time was: " + sealtime);
					// System.out.println("unseal time was: " + unsealtime);
					if (!sealed)
					{
						this.sealtime = 0;
						this.unsealtime = (int) TFC_Time.getTotalHours();
					}
					else
					{
						this.sealtime = (int) TFC_Time.getTotalHours();
						this.unsealtime = 0;
					}

					// System.out.println("resetting seal timer");
				}
				oldRecipe = recipe;
				initiated = true;
				if (recipe != null && !worldObj.isRemote)
				{
					int time = 0;
					if (sealtime > 0)
						time = (int) TFC_Time.getTotalHours() - sealtime;
					else if (unsealtime > 0)
						time = (int) TFC_Time.getTotalHours() - unsealtime;

					// Make sure that the recipe meets the time requirements
					if (recipe.isSealedRecipe() && time < recipe.sealTime)
						return;
					if (!recipe.isSealedRecipe() && time < recipe.sealTime)
						return;
					if (recipe instanceof BarrelFireRecipe && this.fireHeatTicks < ((BarrelFireRecipe) recipe).getFireTicksRequired(getFluidStack()))
						return;

					ItemStack origIS = getInputStack() != null ? getInputStack().copy() : null;
					FluidStack origFS = getFluidStack() != null ? getFluidStack().copy() : null;
					
					//If we're distilling, keep track of how much heat to lose through distillation
					int deltaFireTicks = 0;
					//If we're distilling, we want to handle it separately
					if (recipe instanceof BarrelFireRecipe && ((BarrelFireRecipe) recipe).isDistillationRecipe())
					{
						TEBarrel receiver = this.getReceivingVessel();
						if(receiver != null && this.isReceiverValidForRecipe(((BarrelFireRecipe) recipe)))
						{
							float remainingSpace = receiver.getMaxLiquid() - receiver.getFluidLevel();
							if(remainingSpace > 0)
							{
								FluidStack fluidIn = ((BarrelFireRecipe) recipe).recipeFluid.copy();
								FluidStack fluidOut = ((BarrelFireRecipe) recipe).recipeOutFluid.copy();
								//The percentage of the output that could be fit into the receiver
								float allowablePercent = fluidOut.amount > remainingSpace ? remainingSpace/fluidOut.amount : 1f;
								float amountIn = Math.min(fluidIn.amount * allowablePercent,this.getFluidLevel());
								float amountOut =  Math.min(fluidOut.amount * (amountIn / fluidIn.amount), remainingSpace);
								fluidIn.amount = (int)amountIn;
								fluidOut.amount = (int)amountOut;
								receiver.addLiquid(fluidOut);
								deltaFireTicks = Math.max(this.fireHeatTicks, ((BarrelFireRecipe) recipe).getFireTicksRequired(fluid));
								this.fluid.amount -= amountIn;
								if(this.getFluidLevel()==0)
								{
									this.fluid = null;
								}
							}
						}
					}
					else
					{
						if (fluid.isFluidEqual(recipe.getResultFluid(origIS, origFS, time, fireHeatTicks)) && recipe.removesLiquid)
						{
							if (fluid.getFluid() == TFCFluids.BRINE && origIS != null && origIS.getItem() instanceof IFood)
								fluid.amount -= recipe.getResultFluid(origIS, origFS, time, fireHeatTicks).amount * Food.getWeight(origIS);
							else
								fluid.amount -= recipe.getResultFluid(origIS, origFS, time, fireHeatTicks).amount;
						}
						else
						{
							this.fluid = recipe.getResultFluid(origIS, origFS, time, fireHeatTicks).copy();
							if (fluid != null && !(recipe instanceof BarrelLiquidToLiquidRecipe) && origFS != null)
								this.fluid.amount = origFS.amount;

							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
					}

					if (origFS != null && origFS.getFluid() != TFCFluids.MILKCURDLED && this.fluid != null && this.fluid.getFluid() == TFCFluids.MILKCURDLED)
						this.sealtime = (int) TFC_Time.getTotalHours();
					Stack<ItemStack> resultStacks = null;
					if (recipe instanceof BarrelFireRecipe)
					{
						resultStacks = ((BarrelFireRecipe) recipe).getResult(origIS, origFS, time, this.fireHeatTicks);
					}
					else
					{
						resultStacks = recipe.getResult(origIS, origFS, time);
					}
					this.fireHeatTicks -= deltaFireTicks;
					if (!resultStacks.isEmpty() && true)
					{
						ItemStack result = resultStacks.pop();
						if (fluid != null && fluid.getFluid() == TFCFluids.BRINE)
						{
							if (result == null && origIS != null)
								result = origIS.copy();
							if (result != null && result.getItem() instanceof IFood
							&& (result.getItem() == TFCItems.cheese || ((IFood) result.getItem()).getFoodGroup() != EnumFoodGroup.Grain))
							{
								if (!Food.isBrined(result))
									Food.setBrined(result, true);
							}
						}
						boolean setContents = false;
						if (storage[INPUT_SLOT] == null)
						{
							storage[INPUT_SLOT] = result;
							setContents = true;
						}
						else if (result != null && storage[INPUT_SLOT].getItem() == result.getItem() && recipe.recipeIS == null)
						{
							if (storage[INPUT_SLOT].stackSize < storage[INPUT_SLOT].getMaxStackSize())
							{
								result.stackSize += storage[INPUT_SLOT].stackSize;
								this.setInventorySlotContents(0, result);
							}
							else
							{
								resultStacks.push(result);
							}
							setContents = true;
						}
						if(recipe instanceof BarrelFireRecipe && ((BarrelFireRecipe)recipe).isDistillationRecipe())
						{
							resultStacks.push(result);
							setContents = true;
						}

						for (int i = 1; i < storage.length && !resultStacks.isEmpty(); i++)
						{
							if (!resultStacks.isEmpty() && (storage[i] == null || (storage[i] != null && storage[i].getItem() == resultStacks.get(resultStacks.size()-1).getItem() && storage[i].stackSize < storage[i].getMaxStackSize())))
							{
								ItemStack t = resultStacks.pop();								
								if(storage[i] != null)
								{
									ItemStack temp = storage[i].copy();
									if(temp.stackSize + t.stackSize > temp.getMaxStackSize())
									{
										int dif = temp.getMaxStackSize() - temp.stackSize;
										temp.stackSize+= dif;
										t.stackSize -= dif;
										this.setInventorySlotContents(i, temp);
										resultStacks.push(t);
									}
									else
									{
										temp.stackSize+=t.stackSize;
										this.setInventorySlotContents(i, temp);
									}
								}
								else
								{
									this.setInventorySlotContents(i, t);
								}
							}
						}

						while (!resultStacks.isEmpty())
							worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, resultStacks.pop()));

						if (!setContents)
						{
							this.setInventorySlotContents(0, result);
						}
					}
					if (recipe instanceof BarrelDyeRecipe)
					{
						sealtime = (int) sealtime + recipe.sealTime;
					}
				}
			}
			else if (getFluidStack() == null && !isCheese)
				recipe = null;
		}
	}

	/**
	 * We have to handle cheese by itself because the barrel recipe manager
	 * doesnt take kindly to null input items.
	 */
	private boolean handleCheese()
	{
		ItemStack inIS = this.getInputStack();
		if (this.getSealed() && this.fluid != null && this.fluid.getFluid() == TFCFluids.MILKCURDLED && (inIS == null || inIS.getItem() instanceof IFood
		&& ((IFood) inIS.getItem()).getFoodGroup() != EnumFoodGroup.Dairy && ((IFood) inIS.getItem()).isEdible(inIS) && Food.getWeight(inIS) <= 20.0f))
		{
			recipe = new BarrelRecipe(null, new FluidStack(TFCFluids.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.cheese, 1), 160), null).setMinTechLevel(0);
			if (!worldObj.isRemote)
			{
				int time = 0;
				if (sealtime > 0)
					time = (int) TFC_Time.getTotalHours() - sealtime;
				else if (unsealtime > 0)
					time = (int) TFC_Time.getTotalHours() - unsealtime;

				// Make sure that the recipe meets the time requirements
				if (time < recipe.sealTime)
					return true;
				float w = this.fluid.amount / 62.5f;

				ItemStack is = ItemFoodTFC.createTag(new ItemStack(TFCItems.cheese), w);

				if (inIS != null && inIS.getItem() instanceof IFood)
				{
					int[] profile = Food.getFoodTasteProfile(inIS);
					float ratio = Math.min((Food.getWeight(getInputStack()) - Food.getDecay(inIS)) / (Global.FOOD_MAX_WEIGHT / 8), 1.0f);
					Food.setSweetMod(is, (int) Math.floor(profile[INPUT_SLOT] * ratio));
					Food.setSourMod(is, (int) Math.floor(profile[1] * ratio));
					Food.setSaltyMod(is, (int) Math.floor(profile[2] * ratio));
					Food.setBitterMod(is, (int) Math.floor(profile[3] * ratio));
					Food.setSavoryMod(is, (int) Math.floor(profile[4] * ratio));
					Food.setInfusion(is, inIS.getItem().getUnlocalizedName());
					this.storage[INPUT_SLOT] = null;
				}

				this.actionEmpty();
				this.setInventorySlotContents(0, is);
			}
			return true;
		}
		return false;
	}

	public static ItemStack createFullBarrel(FluidStack f, ItemStack is)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());

		is.getTagCompound().setBoolean("Sealed", true);
		// nbt.setInteger("mode", mode);
		NBTTagCompound fluidNBT = new NBTTagCompound();
		if (f != null)
			f.writeToNBT(fluidNBT);
		is.getTagCompound().setTag("fluidNBT", fluidNBT);

		return is;
	}

	public static void registerRecipes()
	{
		System.out.println("registering recipes");
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.potato), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null,
		new FluidStack(TFCFluids.POTATOWINE, 10000)).setRequiresCooked(true).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.redApple), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.CIDER, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.greenApple), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.CIDER, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(
		new BarrelAlcoholRecipe(new ItemStack(TFCItems.agave, 1), new FluidStack(TFCFluids.FRESHWATER, 312), null, new FluidStack(TFCFluids.AGAVEWINE, 312)).setMinTechLevel(0));
		/*BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.grapes), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null,
		new FluidStack(TFCFluids.WINE, 10000)).setMinTechLevel(0));*/
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.strawberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.blackberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.blueberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.bunchberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.cranberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.elderberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.gooseberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.raspberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.snowberry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BERRYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.cherry), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.FRUITWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.plum), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.FRUITWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.peach), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.FRUITWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.date), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.FRUITWINE, 10000)).setMinTechLevel(0));
		/*BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.WHISKEY, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGround), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.RYEWHISKEY, 10000)).setMinTechLevel(0));*/
		/*BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGrain), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.FRESHWATER, 10000)).setMinTechLevel(0));*/
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGrain), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.SAKE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGrain), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.WHEATWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGrain), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.RYEWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGrain), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BARLEYWINE, 10000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.maizeEar), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.CORNWINE, 10000)).setMinTechLevel(0));
		//Beers
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGerm), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.RICEBEER, 10000)).setMinTechLevel(0).setRequiresCooked(true));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGerm), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.WHEATBEER, 10000)).setMinTechLevel(0).setRequiresCooked(true));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGerm), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.RYEBEER, 10000)).setMinTechLevel(0).setRequiresCooked(true));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGerm), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.BEER, 10000)).setMinTechLevel(0).setRequiresCooked(true));
		BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.cornGerm), 160), new FluidStack(TFCFluids.FRESHWATER, 10000),
		null, new FluidStack(TFCFluids.CORNBEER, 10000)).setMinTechLevel(0).setRequiresCooked(true));
		
		/*BarrelManager.getInstance().addRecipe(
		new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugarcane), 160), new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CANEWINE, 10000))
		.setMinTechLevel(0));*/
		/*BarrelManager.getInstance().addRecipe(new BarrelAlcoholRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.cornmealGround), 160),
		new FluidStack(TFCFluids.FRESHWATER, 10000), null, new FluidStack(TFCFluids.CORNWHISKEY, 10000)).setMinTechLevel(0));*/
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(null, new FluidStack(TFCFluids.MILKVINEGAR, 10000), null, new FluidStack(TFCFluids.MILKCURDLED, 10000))
		.setMinTechLevel(0).setRemovesLiquid(false).setMinTechLevel(0));
		// BarrelManager.getInstance().addRecipe(new BarrelRecipeNoItem(new
		// FluidStack(TFCFluid.MILKCURDLED, 10000), ItemFoodTFC.createTag(new
		// ItemStack(TFCItems.Cheese), 160), null).setMinTechLevel(0));
		for (int chopped = 0; chopped < 2; chopped++)
		{
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 0 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 2 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 3 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 4 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 5 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 6 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 9 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));

			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 16 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));

			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 19 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));

			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 21 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));

			BarrelManager.getInstance()
			.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.logs, 1, 2 * 22 + chopped), new FluidStack(TFCFluids.FRESHWATER, 1000), null, new FluidStack(TFCFluids.TANNIN, 1000))
			.setMinTechLevel(0));
		}

		// Add dye recipes
		BarrelManager.getInstance().addRecipe(
		new BarrelRecipe(new ItemStack(TFCItems.madderRoot, 1, 0), new FluidStack(TFCFluids.TANNIN, 1000), null, new FluidStack(TFCFluids.REDDYE, 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(
		new BarrelRecipe(new ItemStack(TFCItems.woadLeaves, 1, 0), new FluidStack(TFCFluids.TANNIN, 1000), null, new FluidStack(TFCFluids.BLUEDYE, 1000)).setMinTechLevel(0));
		// Green dye from malachite
		// BarrelManager.getInstance().addRecipe(new BarrelRecipe(new
		// ItemStack(TFCItems.powder,1,8), new FluidStack(TFCFluids.TANNIN,
		// 1000), null, new FluidStack(TFCFluids.GREENDYE,
		// 1000)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(
		new BarrelRecipe(new ItemStack(TFCItems.weldRoot, 1, 0), new FluidStack(TFCFluids.TANNIN, 1000), null, new FluidStack(TFCFluids.YELLOWDYE, 1000)).setMinTechLevel(0));
		// white dye requires... ash?
		BarrelManager.getInstance().addRecipe(
		new BarrelRecipe(new ItemStack(TFCItems.powder, 1, 13), new FluidStack(TFCFluids.TANNIN, 1000), null, new FluidStack(TFCFluids.WHITEDYE, 1000)).setMinTechLevel(0));
		// black dye requires ink
		BarrelManager.getInstance()
		.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.ink), new FluidStack(TFCFluids.TANNIN, 1000), null, new FluidStack(TFCFluids.BLACKDYE, 1000)).setMinTechLevel(0));
		// BarrelManager.getInstance().addRecipe(new BarrelRecipe(new
		// ItemStack(TFCItems.logs, 1, 2*9 + chopped), new
		// FluidStack(TFCFluids.TANNIN, 1000), null, new
		// FluidStack(TFCFluids.TANNIN, 1000)).setMinTechLevel(0));

		Item[] dyeableClothes = new Item[] { TFCItems.woolShirt, TFCItems.woolCoat, TFCItems.woolSocks, TFCItems.woolHat, TFCItems.woolPants, TFCItems.linenShirt,
		TFCItems.linenCoat, TFCItems.linenSocks, TFCItems.linenHat, TFCItems.linenPants, TFCItems.silkShirt, TFCItems.silkCoat, TFCItems.silkSocks, TFCItems.silkRobe,
		TFCItems.woolRobe, TFCItems.linenRobe, TFCItems.silkHat, TFCItems.silkPants };

		for (Item i : dyeableClothes)
		{
			BarrelManager.getInstance().addRecipe(new BarrelDyeRecipe(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), new FluidStack(TFCFluids.REDDYE, 250),
			new FluidStack(TFCFluids.REDDYE, 250), Global.RED_DYE_VALUE).setMinTechLevel(0));
			BarrelManager.getInstance().addRecipe(new BarrelDyeRecipe(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), new FluidStack(TFCFluids.WHITEDYE, 250),
			new FluidStack(TFCFluids.WHITEDYE, 250), Global.WHITE_DYE_VALUE).setWhite().setMinTechLevel(0));
			BarrelManager.getInstance().addRecipe(new BarrelDyeRecipe(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), new FluidStack(TFCFluids.BLACKDYE, 250),
			new FluidStack(TFCFluids.BLACKDYE, 250), Global.BLACK_DYE_VALUE).setMinTechLevel(0));
			BarrelManager.getInstance().addRecipe(new BarrelDyeRecipe(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), new FluidStack(TFCFluids.YELLOWDYE, 250),
			new FluidStack(TFCFluids.YELLOWDYE, 250), Global.YELLOW_DYE_VALUE).setMinTechLevel(0));
			BarrelManager.getInstance().addRecipe(new BarrelDyeRecipe(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE), new FluidStack(TFCFluids.BLUEDYE, 250),
			new FluidStack(TFCFluids.BLUEDYE, 250), Global.BLUE_DYE_VALUE).setMinTechLevel(0));

		}
		BarrelManager.getInstance()
		.addRecipe(new BarrelRecipe(new ItemStack(TFCItems.lime, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 500), null, new FluidStack(TFCFluids.LIMEWATER, 500), 0)
		.setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.scrapedHide, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 300),
		new ItemStack(TFCItems.prepHide, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 300)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.scrapedHide, 1, 1), new FluidStack(TFCFluids.FRESHWATER, 400),
		new ItemStack(TFCItems.prepHide, 1, 1), new FluidStack(TFCFluids.FRESHWATER, 400)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.scrapedHide, 1, 2), new FluidStack(TFCFluids.FRESHWATER, 500),
		new ItemStack(TFCItems.prepHide, 1, 2), new FluidStack(TFCFluids.FRESHWATER, 500)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.hide, 1, 0), new FluidStack(TFCFluids.LIMEWATER, 300),
		new ItemStack(TFCItems.soakedHide, 1, 0), new FluidStack(TFCFluids.LIMEWATER, 300)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.hide, 1, 1), new FluidStack(TFCFluids.LIMEWATER, 400),
		new ItemStack(TFCItems.soakedHide, 1, 1), new FluidStack(TFCFluids.LIMEWATER, 400)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.hide, 1, 2), new FluidStack(TFCFluids.LIMEWATER, 500),
		new ItemStack(TFCItems.soakedHide, 1, 2), new FluidStack(TFCFluids.LIMEWATER, 500)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.prepHide, 1, 0), new FluidStack(TFCFluids.TANNIN, 300),
		new ItemStack(TFCItems.leather, 1), new FluidStack(TFCFluids.TANNIN, 300)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.prepHide, 1, 1), new FluidStack(TFCFluids.TANNIN, 400),
		new ItemStack(TFCItems.leather, 2), new FluidStack(TFCFluids.TANNIN, 400)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.prepHide, 1, 2), new FluidStack(TFCFluids.TANNIN, 600),
		new ItemStack(TFCItems.leather, 4), new FluidStack(TFCFluids.TANNIN, 600)).setKeepStackSize(false).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCBlocks.sand, 1, 32767), new FluidStack(TFCFluids.LIMEWATER, 100),
		new ItemStack(TFCItems.mortar, 16), new FluidStack(TFCFluids.LIMEWATER, 100), 0).setMinTechLevel(0).setSealedRecipe(false));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCBlocks.sand2, 1, 32767), new FluidStack(TFCFluids.LIMEWATER, 100),
		new ItemStack(TFCItems.mortar, 16), new FluidStack(TFCFluids.LIMEWATER, 100), 0).setMinTechLevel(0).setSealedRecipe(false));
		//BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.VODKA, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.WINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.MEAD, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.HONEYWATER, 100), new FluidStack(TFCFluids.MEAD, 100)).setSealedRecipe(true).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CANEJUICE, 100), new FluidStack(TFCFluids.CANEWINE, 100)).setSealedRecipe(true).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.GRAPEJUICE, 100), new FluidStack(TFCFluids.WINE, 100)).setSealedRecipe(true).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.BERRYWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CIDER, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		//BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.WHISKEY, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		//BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RYEWHISKEY, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.BEER, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CORNBEER, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RICEBEER, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.WHEATBEER, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RYEBEER, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.SAKE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.WHEATWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.POTATOWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CORNWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RYEWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.AGAVEWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.BARLEYWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CANEWINE, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		//BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.RUM, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		//BarrelManager.getInstance().addRecipe(new BarrelVinegarRecipe(new FluidStack(TFCFluids.CORNWHISKEY, 100), new FluidStack(TFCFluids.VINEGAR, 100)).setMinTechLevel(0));
		BarrelManager.getInstance()
		.addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.SALTWATER, 9000), new FluidStack(TFCFluids.VINEGAR, 1000), new FluidStack(TFCFluids.BRINE, 10000))
		.setSealTime(0).setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance().addRecipe(new BarrelBriningRecipe(new FluidStack(TFCFluids.BRINE, 60), new FluidStack(TFCFluids.BRINE, 60)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.sugarcane), 1), new FluidStack(TFCFluids.FRESHWATER, 60),
		ItemFoodTFC.createTag(new ItemStack(TFCItems.sugar), 0.1f), new FluidStack(TFCFluids.FRESHWATER, 60)).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.jute, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 200),
		new ItemStack(TFCItems.juteFiber, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 200)).setMinTechLevel(0));
		/*
		 * BarrelManager.getInstance() .addRecipe(new BarrelMultiItemRecipe(new
		 * ItemStack(TFCItems.agave, 1, 0), new FluidStack(TFCFluids.FRESHWATER,
		 * 200), new ItemStack(TFCItems.sisalFiber, 1, 0), new
		 * FluidStack(TFCFluids.FRESHWATER, 200)).setMinTechLevel(0));
		 */
		BarrelManager.getInstance().addRecipe(new BarrelMultiItemRecipe(new ItemStack(TFCItems.flax, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 200),
		new ItemStack(TFCItems.flaxFiber, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 200)).setMinTechLevel(0));
		BarrelManager.getInstance()
		.addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.MILK, 9000), new FluidStack(TFCFluids.VINEGAR, 1000), new FluidStack(TFCFluids.MILKVINEGAR, 10000)).setSealTime(0)
		.setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance()
		.addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.MILKVINEGAR, 9000), new FluidStack(TFCFluids.MILK, 1000), new FluidStack(TFCFluids.MILKVINEGAR, 10000)).setSealTime(0)
		.setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance()
		.addRecipe(new BarrelLiquidToLiquidRecipe(new FluidStack(TFCFluids.FRESHWATER, 9000), new FluidStack(TFCFluids.HONEY, 1000), new FluidStack(TFCFluids.HONEYWATER, 10000)).setSealTime(0)
		.setSealedRecipe(false).setMinTechLevel(0).setRemovesLiquid(false));
		BarrelManager.getInstance().addRecipe(new BarrelFireRecipe(new ItemStack(TFCItems.hide, 1, 0), new FluidStack(TFCFluids.FRESHWATER, 800), new ItemStack(Items.slime_ball),
		new FluidStack(TFCFluids.FRESHWATER, 400), 4, 4000));
		BarrelManager.getInstance().addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.SALTWATER, 500), new ItemStack(TFCItems.powder, 1, 9),
		new FluidStack(TFCFluids.SALTWATER, 500), 750)).setFireTicksScale(true).setSealTime(1).setSealedRecipe(false));
		BarrelManager.getInstance().addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.SALTWATER, 500), new ItemStack(TFCItems.powder, 1, 9),
		new FluidStack(TFCFluids.FRESHWATER, 450), 500)).setDistillationRecipe(true).setSealTime(1).setSealedRecipe(true));
		
		//Wet Malting
		BarrelManager.getInstance().addRecipe(new BarrelFireRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGrain),5), new FluidStack(TFCFluids.FRESHWATER, 100),ItemFoodTFC.createTag(new ItemStack(TFCItems.barleyGerm),6),
		new FluidStack(TFCFluids.FRESHWATER, 400), 4, 4000));
		BarrelManager.getInstance().addRecipe(new BarrelFireRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGrain),5), new FluidStack(TFCFluids.FRESHWATER, 100), ItemFoodTFC.createTag(new ItemStack(TFCItems.wheatGerm),6),
		new FluidStack(TFCFluids.FRESHWATER, 400), 4, 4000));
		BarrelManager.getInstance().addRecipe(new BarrelFireRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGrain),5), new FluidStack(TFCFluids.FRESHWATER, 100), ItemFoodTFC.createTag(new ItemStack(TFCItems.riceGerm),6),
		new FluidStack(TFCFluids.FRESHWATER, 400), 4, 4000));
		BarrelManager.getInstance().addRecipe(new BarrelFireRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.maizeEar),5), new FluidStack(TFCFluids.FRESHWATER, 100), ItemFoodTFC.createTag(new ItemStack(TFCItems.cornGerm),6),
		new FluidStack(TFCFluids.FRESHWATER, 400), 4, 4000));
		BarrelManager.getInstance().addRecipe(new BarrelFireRecipe(ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGrain),5), new FluidStack(TFCFluids.FRESHWATER, 100), ItemFoodTFC.createTag(new ItemStack(TFCItems.ryeGerm),6),
		new FluidStack(TFCFluids.FRESHWATER, 400), 4, 4000));
		// Simple distillation
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.FRESHWATER, 100), null, new FluidStack(TFCFluids.FRESHWATER, 100), 100))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.BEER, 200), null, new FluidStack(TFCFluids.BARLEYWHISKEY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.WHEATBEER, 200), null, new FluidStack(TFCFluids.WHISKEY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.CORNBEER, 200), null, new FluidStack(TFCFluids.CORNWHISKEY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.SAKE, 200), null, new FluidStack(TFCFluids.SHOCHU, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.RYEBEER, 200), null, new FluidStack(TFCFluids.RYEWHISKEY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.RICEBEER, 200), null, new FluidStack(TFCFluids.RICEWHISKEY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.WINE, 200), null, new FluidStack(TFCFluids.BRANDY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.FRUITWINE, 200), null, new FluidStack(TFCFluids.FRUITBRANDY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.BERRYWINE, 200), null, new FluidStack(TFCFluids.BERRYBRANDY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.WHEATWINE, 200), null, new FluidStack(TFCFluids.VODKA, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.RYEWINE, 200), null, new FluidStack(TFCFluids.VODKA, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.CORNWINE, 200), null, new FluidStack(TFCFluids.VODKA, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.POTATOWINE, 200), null, new FluidStack(TFCFluids.VODKA, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.AGAVEWINE, 200), null, new FluidStack(TFCFluids.TEQUILA, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.BARLEYWINE, 200), null, new FluidStack(TFCFluids.VODKA, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.CANEWINE, 200), null, new FluidStack(TFCFluids.RUM, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.CIDER, 200), null, new FluidStack(TFCFluids.APPLEJACK, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		BarrelManager.getInstance()
		.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.MEAD, 200), null, new FluidStack(TFCFluids.HONEYBRANDY, 75), 200))
		.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
		
		//Bandages
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.WHISKEY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.WHISKEY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.BARLEYWHISKEY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.BARLEYWHISKEY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.RYEWHISKEY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.RYEWHISKEY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.CORNWHISKEY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.CORNWHISKEY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.RICEWHISKEY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.RICEWHISKEY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.SHOCHU,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.SHOCHU,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.BRANDY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.BRANDY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.BERRYBRANDY,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.BERRYBRANDY,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.APPLEJACK,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.APPLEJACK,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.VODKA,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.VODKA,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.RUM,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.RUM,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.bandage),new FluidStack(TFCFluids.TEQUILA,100),new ItemStack(TFCItems.sterileBandage),new FluidStack(TFCFluids.TEQUILA,100)).setSealedRecipe(false).setSealTime(0).setMinTechLevel(0));
		
		// 5000mb / 160oz = 31.25
		// 10000mb / 160oz = 62.5
		// FluidStack naturally only takes int so I rounded down
		BarrelPreservativeRecipe picklePreservative = new BarrelPreservativeRecipe(new FluidStack(TFCFluids.VINEGAR, 31), "gui.barrel.preserving").setAllowGrains(false)
		.setRequiresPickled(true).setEnvironmentalDecayFactor(0.25f).setBaseDecayModifier(0.1f).setRequiresSealed(true);
		BarrelPreservativeRecipe brineInBrinePreservative = new BarrelPreservativeRecipe(new FluidStack(TFCFluids.BRINE, 62), "gui.barrel.preserving").setAllowGrains(false)
		.setRequiresBrined(true).setEnvironmentalDecayFactor(0.75f).setRequiresSealed(true);
		BarrelPreservativeRecipe brineInVinegarPreservative = new BarrelPreservativeRecipe(new FluidStack(TFCFluids.VINEGAR, 62), "gui.barrel.preserving").setAllowGrains(false)
		.setRequiresBrined(true).setEnvironmentalDecayFactor(0.75f).setRequiresSealed(true);
		BarrelManager.getInstance().addPreservative(picklePreservative);
		BarrelManager.getInstance().addPreservative(brineInBrinePreservative);
		BarrelManager.getInstance().addPreservative(brineInVinegarPreservative);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
	{
		int[] access = new int[storage.length];
		for(int i = 0; i< access.length;i++)
		{
			access[i] = i;
		}
		return access;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
	{
		return !this.sealed;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
	{
		return !this.sealed;
	}
}
