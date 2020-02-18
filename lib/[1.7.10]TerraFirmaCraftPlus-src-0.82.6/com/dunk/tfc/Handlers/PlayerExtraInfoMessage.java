package com.dunk.tfc.Handlers;

import java.util.UUID;

import com.dunk.tfc.TerraFirmaCraft;
import com.mojang.util.UUIDTypeAdapter;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class PlayerExtraInfoMessage implements IMessage {

	
	private UUID uuid;
	private ItemStack i;
	private boolean shouldRemove;
	
	public PlayerExtraInfoMessage(){}
	//Initially, we'll just send the specific item
	public PlayerExtraInfoMessage(UUID uuid, ItemStack extraItem, boolean shouldRemove)
	{
		this.uuid = uuid;
		this.i = extraItem;
		this.shouldRemove = shouldRemove;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		uuid = UUIDTypeAdapter.fromString(ByteBufUtils.readUTF8String(buf));
		i = ByteBufUtils.readItemStack(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, UUIDTypeAdapter.fromUUID(uuid));
        ByteBufUtils.writeItemStack(buf, i);
	}

	public static class Handler implements IMessageHandler<PlayerExtraInfoMessage, IMessage> {

        @Override
        public IMessage onMessage(PlayerExtraInfoMessage message, MessageContext ctx) {
            if (message.shouldRemove) TerraFirmaCraft.proxy.removeItemFromPlayer(message.uuid);
            else{
                TerraFirmaCraft.proxy.updateExtraItemFromPlayer(message.uuid, message.i);
                //Tell other clients about the change
                if (ctx.side.isServer()) {
                    TerraFirmaCraft.netWrapper.sendToAll(new PlayerExtraInfoMessage(message.uuid, message.i, false));
                }
            }
            return null;
        }
    }
}
