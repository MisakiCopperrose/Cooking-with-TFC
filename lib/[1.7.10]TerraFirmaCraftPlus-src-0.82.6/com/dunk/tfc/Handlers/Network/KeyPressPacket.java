package com.dunk.tfc.Handlers.Network;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class KeyPressPacket extends AbstractPacket
{
	private int type;
	private int packetType;
	private static long keyTimer; // not sure what this is for??

	public KeyPressPacket(){}

	public KeyPressPacket(byte t, int packetType)
	{
		type = t;
		this.packetType = packetType;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(type);
		buffer.writeInt(packetType);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		type = buffer.readInt();
		packetType = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		if(keyTimer + 1 < TFC_Time.getTotalTicks())
		{
			keyTimer = TFC_Time.getTotalTicks();
			//Set the ChiselMode on the server.
			if(packetType == 0)
			{
				PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).setChiselMode((byte)type);
			}
			else if(packetType == 1)
			{
				PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).setKnifeMode((byte)type);
			}
		}
	}

}
