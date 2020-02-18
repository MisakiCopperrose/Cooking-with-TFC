package com.dunk.tfc.Commands;

import com.dunk.tfc.Chunkdata.ChunkData;
import com.dunk.tfc.Core.TFC_Core;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;

public class GetSpawnProtectionCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gsp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(sender.canCommandSenderUseCommand(0, sender.getCommandSenderName()))
		{
			EntityPlayerMP player = getCommandSenderAsPlayer(sender);

			int x;
			int z;
			if (params.length >= 2)
			{
				try
				{
					x = Integer.parseInt(params[0]);
					z = Integer.parseInt(params[1]);
				} 
				catch (NumberFormatException ex)
				{
					throw new WrongUsageException("invalid chunk coords: %d x %d", params[0], params[1]);
				}
			}
			else
			{
				x = (int)player.posX >> 4;
				z = (int)player.posZ >> 4;				
			}

			ChunkData d = TFC_Core.getCDM(player.worldObj).getData(x, z);

			if(d != null)
				throw new PlayerNotFoundException("SP (" + x + "," + z + "): " + d.getSpawnProtectionWithUpdate());
			else
				throw new PlayerNotFoundException("Unable to find ChunkData for "+x+","+z);
		}

	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}

}
