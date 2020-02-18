package com.dunk.tfc.Commands;

import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class SetPlayerStatsCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "sps";
	}

	@Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
	}
	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		if(!TFCOptions.enableDebugMode)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Debug Mode Required"));
			return;
		}
		double[] values = new double[3];

		if(params.length == 4 || params.length == 3){
			for(int i = 0;i<3;i++){
				try{
					String s =params[i+(params.length-3)];
					values[i] = Double.parseDouble(s);
				}catch(NumberFormatException e){
					throw new PlayerNotFoundException("Invalid");
				}
				if(values[i]<0||values[i]>100){
					throw new PlayerNotFoundException("Invalid");
				}
			}
		}
		//EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		if(params.length == 4){
			try{
				player = getPlayer(sender, params[0]);
			}catch(PlayerNotFoundException e){
				throw new PlayerNotFoundException("Unknown Player");
			}
		}
		if(player == null) {
			throw new PlayerNotFoundException("Invalid");
		}
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
		player.setHealth((int)((values[0]/100d)*player.getMaxHealth()));
		fs.setFoodLevel((int)((values[1]/100d) * fs.getMaxStomach(player)));
		fs.waterLevel = ((int)((values[2]/100d)*fs.getMaxWater(player)));
		TFC_Core.setPlayerFoodStats(player, fs);
		throw new PlayerNotFoundException(values[0]+" "+values[1]+" "+values[2]);

	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}

}
