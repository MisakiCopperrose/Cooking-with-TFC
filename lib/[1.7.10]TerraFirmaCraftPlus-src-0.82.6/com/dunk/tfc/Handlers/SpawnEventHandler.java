package com.dunk.tfc.Handlers;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCOptions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class SpawnEventHandler
{
	@SubscribeEvent
	public void onPotentialSpawns(WorldEvent.PotentialSpawns event)
	{
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;
		EnumCreatureType type = event.type;
		//We're spawning a monster and we're using the new calcs
		if(type == EnumCreatureType.monster && !TFCOptions.enableDefaultCelestialAngle)
		{
			//calculate celestial angle
			float celestialLight = TFC_Core.getCelestialDaylight(world, x, y, z, TFC_Time.getTotalTicks(), 0);
			//if it's night time, we should allow the spawn to occur, otherwise not.
			if(celestialLight > 8)
			{
				event.setCanceled(true);
			}
		}
	}
}
