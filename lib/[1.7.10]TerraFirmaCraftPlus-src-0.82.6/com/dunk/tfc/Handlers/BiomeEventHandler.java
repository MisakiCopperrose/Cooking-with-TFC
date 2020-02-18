package com.dunk.tfc.Handlers;

import com.dunk.tfc.WorldGen.BiomeDecoratorTFC;
import com.dunk.tfc.WorldGen.TFCBiome;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;

public class BiomeEventHandler
{
	@SubscribeEvent
	public void onCreateDecorator(BiomeEvent.CreateDecorator event)
	{
		event.newBiomeDecorator = new BiomeDecoratorTFC((TFCBiome) event.biome);
	}
}
