package com.dunk.tfc.Handlers.Network;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;

public class ExtraItemsPacket extends AbstractPacket
{
	private NBTTagCompound[] tags;
	private List<String> tagNames;
	private List<String> removeNames;

	private int[] itemIDs;
	private int[] stackSizes;
	private int[] metaDatas;
	private UUID playerUUID;

	private boolean serverUpdate;

	private String username;
	private long sentTime;

	public ExtraItemsPacket()
	{

		// tags = new NBTTagCompound();
		tagNames = new LinkedList<String>();
		removeNames = new LinkedList<String>();

		tags = new NBTTagCompound[TFC_Core.getExtraEquipInventorySize()];// i.stackTagCompound;
		itemIDs = new int[TFC_Core.getExtraEquipInventorySize()];// Item.getIdFromItem(i.getItem());
		stackSizes = new int[TFC_Core.getExtraEquipInventorySize()];// i.stackSize;
		metaDatas = new int[TFC_Core.getExtraEquipInventorySize()];// i.getItemDamage();
		serverUpdate = false;
	}

	public ExtraItemsPacket(ItemStack[] i, EntityPlayer p, boolean serverUpdate)
	{
		this(i, p);
		this.serverUpdate = serverUpdate;
	}

	// Please only use this one?
	public ExtraItemsPacket(ItemStack[] i, EntityPlayer p)
	{
		this();
		if (i != null)
		{
			tags = new NBTTagCompound[TFC_Core.getExtraEquipInventorySize()];// i.stackTagCompound;
			itemIDs = new int[TFC_Core.getExtraEquipInventorySize()];// Item.getIdFromItem(i.getItem());
			stackSizes = new int[TFC_Core.getExtraEquipInventorySize()];// i.stackSize;
			metaDatas = new int[TFC_Core.getExtraEquipInventorySize()];// i.getItemDamage();
			for (int c = 0; c < TFC_Core.getExtraEquipInventorySize(); c++)
			{
				if (i[c] != null)
				{
					tags[c] = i[c].stackTagCompound;
					itemIDs[c] = Item.getIdFromItem(i[c].getItem());
					stackSizes[c] = i[c].stackSize;
					metaDatas[c] = i[c].getItemDamage();
				}
				else
				{
					itemIDs[c] = -1;
				}
			}
		}

		username = p.getDisplayName();
		playerUUID = p.getPersistentID();
	}

	// Please only use this one?
	public ExtraItemsPacket(ItemStack[] i, EntityPlayer p, long sentTime)
	{
		this();
		if (i != null)
		{
			tags = new NBTTagCompound[TFC_Core.getExtraEquipInventorySize()];// i.stackTagCompound;
			itemIDs = new int[TFC_Core.getExtraEquipInventorySize()];// Item.getIdFromItem(i.getItem());
			stackSizes = new int[TFC_Core.getExtraEquipInventorySize()];// i.stackSize;
			metaDatas = new int[TFC_Core.getExtraEquipInventorySize()];// i.getItemDamage();
			for (int c = 0; c < TFC_Core.getExtraEquipInventorySize(); c++)
			{
				if (i[c] != null)
				{
					tags[c] = i[c].stackTagCompound;
					itemIDs[c] = Item.getIdFromItem(i[c].getItem());
					stackSizes[c] = i[c].stackSize;
					metaDatas[c] = i[c].getItemDamage();
				}
				else
				{
					itemIDs[c] = -1;
				}
			}
		}

		username = p.getDisplayName();
		playerUUID = p.getPersistentID();
		this.sentTime = sentTime;
	}

	/**
	 * We don't use this anymore
	 * 
	 * @param nbt
	 * @param acceptedTagNames
	 */
	public ExtraItemsPacket(NBTTagCompound nbt, List<String> acceptedTagNames)
	{
		this();
		tagNames = acceptedTagNames;
		// for (String tagName : tagNames)
		// tags.setTag(tagName, nbt.getTag(tagName));
	}

	/**
	 * We don't use this anymore
	 * 
	 * @param nbt
	 * @param acceptedTagNames
	 * @param removeTagNames
	 */
	public ExtraItemsPacket(NBTTagCompound nbt, List<String> acceptedTagNames, List<String> removeTagNames)
	{
		this();
		tagNames = acceptedTagNames;
		// for (String tagName : tagNames)
		// tags.setTag(tagName, nbt.getTag(tagName));
		// removeNames = removeTagNames;
	}

	public ExtraItemsPacket addAcceptedTag(String name)
	{
		if (!removeNames.contains(name) && !tagNames.contains(name))
			tagNames.add(name);
		return this;
	}

	public ExtraItemsPacket removeAcceptedTag(String name)
	{
		tagNames.remove(name);
		return this;
	}

	public ExtraItemsPacket addRemoveTag(String name)
	{
		if (!removeNames.contains(name))
			removeNames.add(name);
		return this;
	}

	public ExtraItemsPacket removeRemoveTag(String name)
	{
		removeNames.remove(name);
		return this;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		PacketBuffer pb = new PacketBuffer(buffer);
		for (int i = 0; i < TFC_Core.getExtraEquipInventorySize(); i++)
		{
			NBTTagCompound tags = this.tags[i];

			for (String tagName : removeNames)
				tags.removeTag(tagName);

			try
			{
				pb.writeBoolean(serverUpdate);
				pb.writeInt(itemIDs[i]);
				pb.writeLong(sentTime);
				pb.writeInt(stackSizes[i]);
				pb.writeInt(metaDatas[i]);
				pb.writeInt(username.length());
				pb.writeStringToBuffer(username);
				pb.writeInt(playerUUID.toString().length());
				pb.writeStringToBuffer(playerUUID.toString());

				pb.writeNBTTagCompoundToBuffer(tags);

				pb.writeInt(tagNames.size());
				for (String tagName : tagNames)
					pb.writeStringToBuffer(tagName);

				pb.writeInt(removeNames.size());
				for (String tagName : removeNames)
					pb.writeStringToBuffer(tagName);
			}
			catch (Exception e)
			{
				TerraFirmaCraft.LOG.catching(e);
			}
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		PacketBuffer pb = new PacketBuffer(buffer);
		for (int i = 0; i < TFC_Core.getExtraEquipInventorySize(); i++)
		{
			try
			{
				serverUpdate = pb.readBoolean();
				itemIDs[i] = pb.readInt();
				sentTime = pb.readLong();
				stackSizes[i] = pb.readInt();
				metaDatas[i] = pb.readInt();
				int len = pb.readInt();
				username = pb.readStringFromBuffer(len);
				len = pb.readInt();
				playerUUID = UUID.fromString(pb.readStringFromBuffer(len));

				tags[i] = pb.readNBTTagCompoundFromBuffer();

				int size = pb.readInt();
				for (int j = 0; j < size; ++j)
					tagNames.add(pb.readStringFromBuffer(256));

				size = pb.readInt();
				for (int j = 0; j < size; ++j)
					removeNames.add(pb.readStringFromBuffer(256));
			}
			catch (Exception e)
			{
				TerraFirmaCraft.LOG.catching(e);
			}
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(username) == null)
		{
			PlayerManagerTFC.getInstance().players.add(new PlayerInfo(username, playerUUID));
		}
		// if (MinecraftServer.getServer() != null &&
		// MinecraftServer.getServer().getConfigurationManager() != null)
		// {

		EntityPlayer packetPlayer = player.worldObj.getPlayerEntityByName(username);
		if (packetPlayer != null)
		{
			int num = 0;
			// System.out.println(Minecraft.getMinecraft().thePlayer.getGameProfile().getName()
			// + " is being told that "
			// + username + "'s items are " + itemIDs[num++] + ", " +
			// itemIDs[num++] + ", " + itemIDs[num++] + ", "
			// + itemIDs[num++] + ", " + itemIDs[num++] + ".");

			// NBTTagCompound stackNBT;
			if (Minecraft.getMinecraft().thePlayer.getGameProfile().getName().equals(username))
			{
				return;
			}

			for (int i = 0; i < TFC_Core.getExtraEquipInventorySize(); i++)
			{

				ItemStack stack = null;
				if (itemIDs[i] != -1)
				{
					stack = new ItemStack(Item.getItemById(itemIDs[i]), stackSizes[i], metaDatas[i]);
					stack.stackTagCompound = tags[i];
				}
				if (username != null && stack != null
						&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(username) != null
						&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems != null)
				{
					if (stack.stackSize > 0)
						PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = stack;
					else
						PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = null;
				}
				else if (username != null && stack != null
						&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(username) != null
						&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems == null)
				{
					PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems = new ItemStack[TFC_Core
							.getExtraEquipInventorySize()];
					if (stack.stackSize > 0)
						PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = stack;
					else
						PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = null;

				}
				else if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(username) == null && stack != null
						&& stack.stackSize > 0)
				{
					PlayerManagerTFC.getInstance().players.add(new PlayerInfo(username, playerUUID));
				}
				else if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(username) != null && stack == null
						&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems != null)
				{
					PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = null;
				}
				else if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(username) != null && stack == null
						&& PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems == null)
				{
					PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems = new ItemStack[TFC_Core
							.getExtraEquipInventorySize()];
					PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = null;

				}

			}
		}
		else
		{
			// System.out.println("Player was null!");
		}
		// }
		/*
		 * if (stack != null) { if (stack.hasTagCompound()) { stackNBT =
		 * stack.stackTagCompound; } else {
		 * TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") +" " +
		 * stack.getUnlocalizedName() + " " + TFC_Core.translate("error.NBT") +
		 * " " + TFC_Core.translate("error.Contact")); stackNBT = new
		 * NBTTagCompound(); }
		 * 
		 * for (String tagName : tagNames) stackNBT.setTag(tagName,
		 * tags.getTag(tagName)); for (String tagName : removeNames)
		 * stackNBT.removeTag(tagName);
		 * player.inventory.getCurrentItem().setTagCompound(stackNBT); }
		 */
	}

	// what does this even do?
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		/*
		 * NBTTagCompound stackNBT; ItemStack stack =
		 * player.inventory.getCurrentItem();
		 * 
		 * if (stack != null) { if (stack.hasTagCompound()) { stackNBT =
		 * stack.stackTagCompound; } else {
		 * TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") +" " +
		 * stack.getUnlocalizedName() + " " + TFC_Core.translate("error.NBT") +
		 * " " + TFC_Core.translate("error.Contact")); stackNBT = new
		 * NBTTagCompound(); } for (String tagName : tagNames)
		 * stackNBT.setTag(tagName, tags.getTag(tagName)); for (String tagName :
		 * removeNames) stackNBT.removeTag(tagName);
		 * player.inventory.getCurrentItem().setTagCompound(stackNBT); }
		 */
		if (!player.getGameProfile().getName().equals(username))
		{
			return;
		}
		int num = 0;
		// System.out.println(player.getGameProfile().getName() + " claims that
		// their inventory is " + itemIDs[num++]
		// + ", " + itemIDs[num++] + ", " + itemIDs[num++] + ", " +
		// itemIDs[num++] + ", " + itemIDs[num++] + ".");

		EntityPlayer packetPlayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(username);

		// System.out.println("The server has been told that " + username +" has
		// the item ids " +
		// this.itemIDs[0] +", "+ this.itemIDs[1] +", "+ this.itemIDs[2] +", "+
		// this.itemIDs[3]+", " + this.itemIDs[4] +" by " +
		// player.getGameProfile().getName());

		if (PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems == null)
		{
			PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems = new ItemStack[TFC_Core
					.getExtraEquipInventorySize()];
		}
		if (packetPlayer != null)
		{
			for (int i = 0; i < TFC_Core.getExtraEquipInventorySize(); i++)
			{
				ItemStack stack = null;
				if (itemIDs[i] != -1)
				{
					stack = new ItemStack(Item.getItemById(itemIDs[i]), stackSizes[i], metaDatas[i]);
					stack.stackTagCompound = tags[i];
				}
				PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).myExtraItems[i] = stack;
				/*
				if (TFC_Time.getTotalTicks() % TFC_Core.getClothingUpdateFrequency() == 0 && false)
				{
					if (stack != null && stack.stackSize > 0)
					{
						((InventoryPlayerTFC) (packetPlayer.inventory)).extraEquipInventory[i] = stack;
					}
					else
					{
						((InventoryPlayerTFC) (packetPlayer.inventory)).extraEquipInventory[i] = null;
					}
				}*/
			}
			// if(username.equals("Kapellini"))
			// {
			// System.out.println("Packet from " + username);
			// System.out.println("Clothing reported as " +
			// tags[2].getInteger("wetness"));
			// }
			TerraFirmaCraft.PACKET_PIPELINE.sendToAll(this);
		}
		else
		{
			// System.out.println("The server thinks the player is null!");
		}
		/*
		 * for (int i = 0; i < TFC_Core.getExtraEquipInventorySize(); i++) {
		 * ItemStack stack = null; if (itemIDs[i] != -1) { stack = new
		 * ItemStack(Item.getItemById(itemIDs[i]), stackSizes[i], metaDatas[i]);
		 * stack.stackTagCompound = tags[i]; }
		 * PlayerManagerTFC.getInstance().getPlayerInfoFromName(username).
		 * myExtraItems[i] = stack; if (stack != null && stack.stackSize > 0) {
		 * ((InventoryPlayerTFC) (player.inventory)).extraEquipInventory[i] =
		 * stack; } else { ((InventoryPlayerTFC)
		 * (player.inventory)).extraEquipInventory[i] = null; } }
		 */
	}
}
