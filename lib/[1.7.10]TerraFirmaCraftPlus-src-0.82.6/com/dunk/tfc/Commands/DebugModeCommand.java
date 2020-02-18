package com.dunk.tfc.Commands;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.Handlers.Network.DebugModePacket;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class DebugModeCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "debugmode";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if(params.length == 0)
		{
			if (TFCOptions.enableDebugMode)
			{
				TFCOptions.enableDebugMode = false;
				TFC_Core.sendInfoMessage(player, new ChatComponentText("Debug Mode Disabled"));
			}
			else
			{
				TFCOptions.enableDebugMode = true;
				TFC_Core.sendInfoMessage(player, new ChatComponentText("Debug Mode Enabled"));
			}

			AbstractPacket pkt = new DebugModePacket(player);
			TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, player);
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return "";
	}

}
