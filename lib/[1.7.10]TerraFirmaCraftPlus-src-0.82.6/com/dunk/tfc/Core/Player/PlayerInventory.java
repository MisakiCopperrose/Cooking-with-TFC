package com.dunk.tfc.Core.Player;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Containers.Slots.SlotExtraEquipable;
import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.Containers.Slots.SlotTFC;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Interfaces.IEquipable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class PlayerInventory
{
	public static int invXSize = 176;
	public static int invYSize = 87;
	private static ResourceLocation invTexture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_inventory_lower.png");
	public static InventoryCrafting containerInv;
	private static int index;

	public static void buildInventoryLayout(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot, boolean toolBarAfterMainInv)
	{
		index = 0;
		if(!toolBarAfterMainInv)
			addToolbarSlots(container, inventory, x, y, freezeSlot);

		for(int i = 0; i < 3; ++i)
		{
			for(int k = 0; k < 9; ++k)
			{
				index =  k + (i+1) * 9;
				addSlotToContainer(container, new Slot(inventory, index, x + k * 18, y + i * 18));
			}
		}

		if(toolBarAfterMainInv)
			addToolbarSlots(container, inventory, x, y, freezeSlot);

		/*ItemStack is = getInventory(inventory.player).extraEquipInventory[0];
		if(is != null)
		{
			if(is.getItem() instanceof ItemQuiver)
			{
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 178, y));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 178, y+18));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 178, y+36));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 178, y+54));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 196, y));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 196, y+18));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 196, y+36));
				addSlotToContainer(container, new SlotQuiver(containerInv, index++, x + 196, y+54));
			}
			loadBagInventory(is, container);
		}*/
	}

	public static void loadBagInventory(ItemStack is, Container c)
	{
		if(is != null && is.hasTagCompound())
		{
			NBTTagList nbttaglist = is.getTagCompound().getTagList("Items", 10);
			containerInv = new InventoryCrafting(c, 4, 2);
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 8)
				{
					ItemStack is2 = ItemStack.loadItemStackFromNBT(nbttagcompound1);
					if(nbttagcompound1.hasKey("Stack"))
					{
						is2.stackSize = nbttagcompound1.getInteger("Stack");
					}
					containerInv.setInventorySlotContents(byte0, is2);
				}
			}
		}
	}

	public static void addExtraEquipables(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot){
		int index = 36; // Should be the correct index
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++, 8 /*- 18*/, 8 + 18, IEquipable.EquipType.BACK));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++,36+ 8+18 , 8, IEquipable.EquipType.HEAD2));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++,36+ 8+18 , 8+18, IEquipable.EquipType.BODY2));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++,36+ 8+18 , 8+36, IEquipable.EquipType.LEGS2));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++,36+ 8+18 , 8+54, IEquipable.EquipType.FEET2));
	}
	
	public static void addExtraEquipablesCreative(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot)
	{
		int index = 36; // Should be the correct index
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++, 8 - 18, 8 + 18, IEquipable.EquipType.BACK));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++, 8+18 , 8, IEquipable.EquipType.HEAD2));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++, 8+18 , 8+18, IEquipable.EquipType.BODY2));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++, 8+18 , 8+36, IEquipable.EquipType.LEGS2));
		addSlotToContainer(container, new SlotExtraEquipable(inventory, index++, 8+18 , 8+54, IEquipable.EquipType.FEET2));
	}

	private static void addToolbarSlots(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot) 
	{
		for(int j = 0; j < 9; ++j)
		{
			if(freezeSlot && j == inventory.currentItem)
				addSlotToContainer(container, new SlotForShowOnly(inventory, j, x + j * 18, y+58));
			else
				addSlotToContainer(container, new SlotTFC(inventory, j, x + j * 18, y+58));
		}
	}

	public static void buildInventoryLayout(Container container, InventoryPlayer inventory, int x, int y, boolean freezeSlot)
	{
		buildInventoryLayout(container, inventory, x, y, false, false);
	}

	public static void buildInventoryLayout(Container container, InventoryPlayer inventory, int x, int y)
	{
		buildInventoryLayout(container, inventory, x, y, false);
	}

	protected static Slot addSlotToContainer(Container container, Slot par1Slot)
	{
		par1Slot.slotNumber = container.inventorySlots.size();
		container.inventorySlots.add(par1Slot);
		container.inventoryItemStacks.add((Object)null);
		return par1Slot;
	}

	public static void drawInventory(GuiContainer container, int screenWidth, int screenHeight, int upperGuiHeight)
	{
		TFC_Core.bindTexture(invTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (screenWidth - invXSize) / 2;
		int i1 = (screenHeight - (upperGuiHeight+invYSize)) / 2 + upperGuiHeight;
		container.drawTexturedModalRect(l, i1, 0, 0, invXSize, invYSize);
		//container.drawTexturedModalRect(l+invXSize, i1+1, 0, 87, 83, 83);
		/*ItemStack is = getInventory(container.mc.thePlayer).extraEquipInventory[0];
		if(is != null)
		{
			if(is.getItem() instanceof ItemQuiver)
				container.drawTexturedModalRect(l+invXSize, i1+1, 84, 87, 47, 83);
		}*/
	}

	public static InventoryPlayerTFC getInventory(EntityPlayer p)
	{
		return (InventoryPlayerTFC)p.inventory;
	}

	public static void upgradePlayerCrafting(EntityPlayer player)
	{
		//These might be 4 higher?
		if(player.getEntityData().hasKey("craftingTable"))
		{
			player.inventoryContainer.getSlot(45).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(46).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(47).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(48).xDisplayPosition += 50000;
			player.inventoryContainer.getSlot(49).xDisplayPosition += 50000;
		}
	}


	public static ItemStack transferStackInSlot(EntityPlayer player, ItemStack stackToXfer)
	{
		return null;
	}
}
