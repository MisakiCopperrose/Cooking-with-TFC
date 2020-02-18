package com.dunk.tfc.Items;

import org.lwjgl.input.Keyboard;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemFlute extends ItemTerra
{

	public ItemFlute()
	{
		super();
		this.maxStackSize = 1;
		this.stackable = false;
	}
	
	@SideOnly(Side.CLIENT)
	private void playClientMusic(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		byte note = 0;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		if(Keyboard.isKeyDown(Keyboard.KEY_B))
		{
			note |=16;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			note |= 8;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_G))
		{
			note |= 4;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_H))
		{
			note |= 2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_J))
		{
			note |= 1;
		}
		float pitch = 0.5f;
		float volume = 0.7f;
		String location = TFC_Sounds.JUGBLOWNOTE;
		switch(note)
		{
		case 15+16: pitch = 0.5f;break;
		case 13+16: pitch = 0.529732f;break;
		case 7+16: pitch = 0.561231f;break;
		case 11+16: pitch = 0.594604f;break;
		case 3+16: pitch = 0.629961f;break;
		case 9+16: pitch = 0.667420f;break;
		case 5+16: pitch = 0.707107f;break;
		case 1+16: pitch = 0.749154f;break;
		case 0+16: pitch = 0.793701f;break;
		case 14+16: pitch = 0.840896f;break;
		case 6+16: pitch = 0.890899f;break;
		case 10+16: pitch = 0.943874f;break;
		case 12+16: pitch = 1f;break;
		case 8+16: pitch = 1.059463f;break;
		case 4+16: pitch = 1.122462f;break;
		case 2+16: pitch = 1.189207f;break;
		case 15: pitch = 1.259921f;break;
		case 13: pitch = 1.334840f;break;
		case 7: pitch = 1.414214f;break;
		case 11: pitch = 1.498307f;break;
		case 3: pitch = 1.587401f;break;
		case 9: pitch = 1.681793f;break;
		case 5: pitch = 1.781797f;break;
		case 1: pitch = 1.887749f;break;
		case 0: pitch = 2f; location = TFC_Sounds.JUGBLOWNOTE;break;
		case 14: pitch = 0.529732f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		case 6: pitch = 0.561231f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		case 10: pitch = 0.594604f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		case 12: pitch = 0.629961f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		case 8: pitch = 0.667420f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		case 4: pitch = 0.707107f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		case 2: pitch = 0.749154f; location = TFC_Sounds.JUGBLOWNOTE2;break;
		default: volume = 0f;break;
		}
		//System.out.println(pitch);
		//world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0, entityplayer.posZ + 0,
		//		TFC_Sounds.JUGBLOWNOTE, volume, pitch);
		
		if(pi != null && pi.playedNote != null && pi.playedPitch != pitch)
		{
			//Minecraft.getMinecraft().getSoundHandler().stopSound(pi.playedNote);
		}
		if(pi.playedPitch == 0)
		{
			pi.playedPitch = 0.2f;
		}
		if(pi != null && pi.playedNote != null && pi.playedPitch == pitch && (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(pi.playedNote) || TFC_Time.getTotalTicks() > pi.tickPlayed + (7/ pi.playedPitch)))
		{
			
			PositionedSoundRecord fluteNote = new PositionedSoundRecord(new ResourceLocation(location),volume,pitch,
					(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
			//Minecraft.getMinecraft().getSoundHandler().playSound(fluteNote);
			//pi.playedNote = fluteNote;
			pi.playedPitch = pitch;
			pi.tickPlayed = TFC_Time.getTotalTicks();
		}
		else if(pi.playedPitch != pitch)
		{
			PositionedSoundRecord fluteNote = new PositionedSoundRecord(new ResourceLocation(location),volume,pitch,
					(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
			//Minecraft.getMinecraft().getSoundHandler().playSound(fluteNote);
			//pi.playedNote = fluteNote;
			pi.playedPitch = pitch;
			pi.notePlayed = location.equals(TFC_Sounds.JUGBLOWNOTE)?0:1;
			pi.tickPlayed = TFC_Time.getTotalTicks();
		}
		PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,7);
		TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
	//	else{
		
		//}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		
		if (world.isRemote)
		{			
			playClientMusic(itemstack,world,entityplayer);
		}
		//entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
		return itemstack;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		if(pi != null && pi.playedNote != null)
		{
			Minecraft.getMinecraft().getSoundHandler().stopSound(pi.playedNote);
		}
	}
	
	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}
	
	/**
	 * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
	 * hands.
	 */
	public boolean shouldRotateAroundWhenRendering()
	{
		return false;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.bow;	
	}
	
}
