package com.dunk.tfc.Handlers.Client;

import java.util.Iterator;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockDetailed;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.dunk.tfc.Handlers.Network.KeyPressPacket;
import com.dunk.tfc.Items.Tools.ItemChisel;
import com.dunk.tfc.Items.Tools.ItemCustomHoe;
import com.dunk.tfc.Items.Tools.ItemKnife;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindingHandler
{
	//public static KeyBinding Key_Calendar = new KeyBinding("key.Calendar", Keyboard.KEY_N/*49*/, Reference.ModName);
	public static KeyBinding keyToolMode = new KeyBinding("key.ToolMode", Keyboard.KEY_M/*50*/, Reference.MOD_NAME);
	public static KeyBinding keyLockTool = new KeyBinding("key.LockTool", Keyboard.KEY_L/*38*/, Reference.MOD_NAME);
	public KeyBinding[] keys = new KeyBinding[4];
	boolean[] secretPress = new boolean[4];

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
		EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(FMLClientHandler.instance().getClient().thePlayer);
		long drunkenness = fs.soberTime - TFC_Time.getTotalTicks();
		if(FMLClientHandler.instance().getClient().inGameHasFocus)
		{
			/*for(int i = 0; i < 4; i++)
			{
				if(secretPress[i] && keys[i] != null)
				{
					KeyBinding.setKeyBindState(keys[i].getKeyCode(), false);
					secretPress[i] = false;
				}
			}*/
		}
		if(FMLClientHandler.instance().getClient().inGameHasFocus && drunkenness  > 12000)
		{
			/*System.out.println(Keyboard.getEventKey());
			Keyboard.enableRepeatEvents(false);			
			
			keys[0] = Minecraft.getMinecraft().gameSettings.keyBindRight;
			keys[1] = Minecraft.getMinecraft().gameSettings.keyBindLeft;
			keys[2] = Minecraft.getMinecraft().gameSettings.keyBindForward;
			keys[3] = Minecraft.getMinecraft().gameSettings.keyBindBack;
			
			Random r = new Random();
			if(r.nextInt(4)==0 && (keys[0].isPressed() || keys[1].isPressed() || keys[2].isPressed() || keys[3].isPressed()))
			{
				if(keys[0].isPressed())
				{
					KeyBinding.setKeyBindState(keys[0].getKeyCode(), false);
				}
				if(keys[1].isPressed())
				{
					KeyBinding.setKeyBindState(keys[1].getKeyCode(), false);
				}
				if(keys[2].isPressed())
				{
					KeyBinding.setKeyBindState(keys[2].getKeyCode(), false);
				}
				if(keys[3].isPressed())
				{
					KeyBinding.setKeyBindState(keys[3].getKeyCode(), false);
				}
				int n = r.nextInt(4);
				KeyBinding.setKeyBindState(keys[n].getKeyCode(), true);
				secretPress[n] = true;
			}*/
		}
		
		if(FMLClientHandler.instance().getClient().inGameHasFocus &&
				FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem() != null &&
				FMLClientHandler.instance().getClient().currentScreen == null)
		{
			if(keyToolMode.isPressed())
			{
				if(player.getCurrentEquippedItem().getItem() instanceof ItemChisel)
				{
					pi.switchChiselMode();
					//Let's send the actual ChiselMode so the server/client does not
					//come out of sync.
					AbstractPacket pkt = new KeyPressPacket(pi.chiselMode,0);
					TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
				}
				else if(player.getCurrentEquippedItem().getItem() instanceof ItemCustomHoe)
				{
					pi.switchHoeMode(player);
				}
				else if(player.getCurrentEquippedItem().getItem() instanceof ItemKnife)
				{
					pi.switchKnifeMode(player);
					AbstractPacket pkt = new KeyPressPacket((byte)pi.knifeMode,1);
					TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
				}
			}
			else if (keyLockTool.isPressed() && pi != null)
			{
				if(pi.lockX == -9999999)
				{
					pi.lockX = BlockDetailed.lockX;
					pi.lockY = BlockDetailed.lockY;
					pi.lockZ = BlockDetailed.lockZ;
				}
				else
				{
					pi.lockX = -9999999;
					pi.lockY = -9999999;
					pi.lockZ = -9999999;
				}
			}
		}
	}
}
