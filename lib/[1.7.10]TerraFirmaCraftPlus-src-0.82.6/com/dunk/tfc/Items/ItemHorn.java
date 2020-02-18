package com.dunk.tfc.Items;

import org.lwjgl.input.Keyboard;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemHorn extends ItemTerra
{
	boolean hasTones;
	
	public ItemHorn()
	{
		super();
	}
	
	public ItemHorn(boolean t)
	{
		super();
		this.hasTones = t;
	}
	
	public boolean isFull3D()
	{
		return true;
	}
	
	
	@SideOnly(Side.CLIENT)
	protected void playClientMusic(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		byte note = 0;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);

		// If we haven't let go, we can't strum again.
		if (pi.strumLock)
		{
			return;
		}
		pi.strumLock = true;
		float pitch = 0.5f;
		if(hasTones)
		{
			
			//if (Keyboard.isKeyDown(Keyboard.KEY_F))
			//{
			//	note |= 16;
			//}
			if (Keyboard.isKeyDown(Keyboard.KEY_F))
			{
				pitch = 0.749154f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_G))
			{
				pitch = 1f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_H))
			{
				pitch = 1.259921f; // E
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_J))
			{
				pitch = 1.498307f; //High G
			}
		}
		
		float volume = 1f;
		
		pi.notePlayed = itemstack.getItem().equals(TFCItems.blowingHorn)?5:(itemstack.getItem().equals(TFCItems.brassHorn))?6:7;
		pi.playedPitch = pitch;
		PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,10);
		TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
	}
	
	//Stolen from the lyre
	@SideOnly(Side.CLIENT)
	protected void enableStrumming(EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.strumLock = false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer entityplayer, int inUseCount)
	{
		enableStrumming(entityplayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (itemstack.getItemDamage() == 0)
		{
			entityplayer.setItemInUse(itemstack, 1000000);
			if (world.isRemote)
			{
				playClientMusic(itemstack, world, entityplayer);
			}
			// entityplayer.setItemInUse(itemstack,
			// this.getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}
	

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.none;	
	}
	
}
