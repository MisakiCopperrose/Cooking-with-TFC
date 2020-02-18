package com.dunk.tfc.Handlers;

import com.dunk.tfc.Blocks.Devices.BlockTeepee;
import com.dunk.tfc.Blocks.Vanilla.BlockBed;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;

public class PlayerWakeListener
{
	@SubscribeEvent
	public void onPlayerWakeUpEvent(PlayerWakeUpEvent event)
	{
		//We're waking up. If we're in a proper bed, we can allow the spawn to be set. Otherwise, we should set a variable in the PlayerInfo to help us
		//Reset the spawn point afterwards.
		EntityPlayer player = event.entityPlayer;
		
		World worldObj = player.worldObj;
		ChunkCoordinates chunkcoordinates = player.playerLocation;
		ChunkCoordinates chunkcoordinates1 = player.playerLocation;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		Block block = (chunkcoordinates == null ? null
				: worldObj.getBlock(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));		
		if (chunkcoordinates != null && block.isBed(worldObj, chunkcoordinates.posX, chunkcoordinates.posY,
				chunkcoordinates.posZ, player) && event.setSpawn && pi!=null)
		{
			if(block instanceof BlockTeepee)
			{
				//If we're in a teepee, we want to remember what the old spawn location was
				
				pi.overrideSpawnCoordinates = player.getBedLocation(player.dimension);
				pi.hasOverrideSpawn = true;
			}
			else
			{
				pi.overrideSpawnCoordinates = null;			
				pi.hasOverrideSpawn = false;
			}
		}
	}
}
