package com.dunk.tfc.Items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemLyre extends ItemTerra
{
	IIcon unstrungIcon;
	IIcon strungIcon;

	public ItemLyre()
	{
		this.hasSubtypes = true;
		this.metaNames = new String[] { "unstrung lyre", "lyre" };
		this.stackable = false;
		this.setFolder("devices/");
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.unstrungIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[0]);
		this.strungIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[1]);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		if (metaNames != null)
			for (int i = 0; i < metaNames.length; i++)
				list.add(new ItemStack(this, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if (damage == 0)
			return this.unstrungIcon;
		else
			return this.strungIcon;
	}

	@SideOnly(Side.CLIENT)
	private void playClientMusic(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		byte note = 0;
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);

		// If we haven't let go, we can't strum again.
		if (pi.strumLock)
		{
			return;
		}
		pi.strumLock = true;
		if (Keyboard.isKeyDown(Keyboard.KEY_B))
		{
			note |= 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			note |= 16;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_G))
		{
			note |= 8;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_H))
		{
			note |= 4;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_J))
		{
			note |= 2;
		}
		float pitch = 0.5f;
		float volume = 0.7f;
		String location = TFC_Sounds.LYRE;
		switch (note)
		{
		case 15 + 16:
			pitch = 0.5f;
			break;
		case 13 + 16:
			pitch = 0.529732f;
			break;
		case 7 + 16:
			pitch = 0.561231f;
			break;
		case 11 + 16:
			pitch = 0.594604f;
			break;
		case 3 + 16:
			pitch = 0.629961f;
			break;
		case 9 + 16:
			pitch = 0.667420f;
			break;
		case 5 + 16:
			pitch = 0.707107f;
			break;
		case 1 + 16:
			pitch = 0.749154f;
			break;
		case 0 + 16:
			pitch = 0.793701f;
			break;
		case 14 + 16:
			pitch = 0.840896f;
			break;
		case 6 + 16:
			pitch = 0.890899f;
			break; // E
		case 10 + 16:
			pitch = 0.943874f;
			break;
		case 12 + 16:
			pitch = 1f;
			break;
		case 8 + 16:
			pitch = 1.059463f;
			break; // G
		case 4 + 16:
			pitch = 1.122462f;
			break;
		case 2 + 16:
			pitch = 1.189207f;
			break; // A
		case 15:
			pitch = 1.259921f;
			break;
		case 13:
			pitch = 1.334840f;
			break; // B
		case 7:
			pitch = 1.414214f;
			break;
		case 11:
			pitch = 1.498307f;
			break;
		case 3:
			pitch = 1.587401f;
			break; // D
		case 9:
			pitch = 1.681793f;
			break;
		case 5:
			pitch = 1.781797f;
			break;
		case 1:
			pitch = 1.887749f;
			break;
		case 0:
			pitch = 2f;
			location = TFC_Sounds.LYRE;
			break;
		case 14:
			pitch = 0.529732f;
			location = TFC_Sounds.LYRE;
			break;
		case 6:
			pitch = 0.561231f;
			location = TFC_Sounds.LYRE;
			break;
		case 10:
			pitch = 0.594604f;
			location = TFC_Sounds.LYRE;
			break;
		case 12:
			pitch = 0.629961f;
			location = TFC_Sounds.LYRE;
			break;
		case 8:
			pitch = 0.667420f;
			location = TFC_Sounds.LYRE;
			break;
		case 4:
			pitch = 0.707107f;
			location = TFC_Sounds.LYRE;
			break;
		case 2:
			pitch = 0.749154f;
			location = TFC_Sounds.LYRE;
			break;
		default:
			volume = 0f;
			break;
		}
		// System.out.println(pitch);
		// world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0,
		// entityplayer.posZ + 0,
		// TFC_Sounds.JUGBLOWNOTE, volume, pitch);

		if (pi != null && pi.playedNote != null && pi.playedPitch != pitch)
		{
			Minecraft.getMinecraft().getSoundHandler().stopSound(pi.playedNote);
		}
		if (pi.playedPitch == 0)
		{
			pi.playedPitch = 0.2f;
		}
		if (pi != null && pi.playedNote != null && pi.playedPitch == pitch
				&& (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(pi.playedNote)
						|| TFC_Time.getTotalTicks() > pi.tickPlayed + (7 / pi.playedPitch)))
		{

			/*PositionedSoundRecord DNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 16) == 16 ? 0 : volume), 0.793701f, (int) entityplayer.posX, (int) entityplayer.posY,
					(int) entityplayer.posZ);
			PositionedSoundRecord EflatNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 8) == 8 ? 0 : volume), 0.840896f, (int) entityplayer.posX, (int) entityplayer.posY,
					(int) entityplayer.posZ);
			PositionedSoundRecord GNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 4) == 4 ? 0 : volume), 1.059463f, (int) entityplayer.posX, (int) entityplayer.posY,
					(int) entityplayer.posZ);
			PositionedSoundRecord ANote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 2) == 2 ? 0 : volume), 1.189207f, (int) entityplayer.posX, (int) entityplayer.posY,
					(int) entityplayer.posZ);			
			PositionedSoundRecord BflatNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 1) == 1 ? 0 : volume), 1.259921f, (int) entityplayer.posX, (int) entityplayer.posY,
					(int) entityplayer.posZ);
			Minecraft.getMinecraft().getSoundHandler().playSound(DNote);
			Minecraft.getMinecraft().getSoundHandler().playSound(EflatNote);
			Minecraft.getMinecraft().getSoundHandler().playSound(ANote);
			Minecraft.getMinecraft().getSoundHandler().playSound(GNote);
			Minecraft.getMinecraft().getSoundHandler().playSound(BflatNote);*/
			
			pi.notePlayed = note;
			
			PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,9);
			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			
			// pi.playedNote = fluteNote;
			// pi.playedPitch = pitch;
			// pi.tickPlayed = TFC_Time.getTotalTicks();
		}
		else if (pi.playedPitch != pitch)
		{

			/*PositionedSoundRecord DNote = new PositionedSoundRecord(new ResourceLocation(location),
			((note & 16) == 16 ? 0 : volume), 0.793701f, (int) entityplayer.posX, (int) entityplayer.posY,
			(int) entityplayer.posZ);
	PositionedSoundRecord EflatNote = new PositionedSoundRecord(new ResourceLocation(location),
			((note & 8) == 8 ? 0 : volume), 0.840896f, (int) entityplayer.posX, (int) entityplayer.posY,
			(int) entityplayer.posZ);
	PositionedSoundRecord GNote = new PositionedSoundRecord(new ResourceLocation(location),
			((note & 4) == 4 ? 0 : volume), 1.059463f, (int) entityplayer.posX, (int) entityplayer.posY,
			(int) entityplayer.posZ);
	PositionedSoundRecord ANote = new PositionedSoundRecord(new ResourceLocation(location),
			((note & 2) == 2 ? 0 : volume), 1.189207f, (int) entityplayer.posX, (int) entityplayer.posY,
			(int) entityplayer.posZ);			
	PositionedSoundRecord BflatNote = new PositionedSoundRecord(new ResourceLocation(location),
			((note & 1) == 1 ? 0 : volume), 1.259921f, (int) entityplayer.posX, (int) entityplayer.posY,
			(int) entityplayer.posZ);
	Minecraft.getMinecraft().getSoundHandler().playSound(DNote);
	Minecraft.getMinecraft().getSoundHandler().playSound(EflatNote);
	Minecraft.getMinecraft().getSoundHandler().playSound(ANote);
	Minecraft.getMinecraft().getSoundHandler().playSound(GNote);
	Minecraft.getMinecraft().getSoundHandler().playSound(BflatNote);*/
			
			pi.notePlayed = note;
			
			PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,9);
			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			
			// pi.playedNote = fluteNote;
			// pi.playedPitch = pitch;
			// pi.tickPlayed = TFC_Time.getTotalTicks();
		}
		// PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,7);
		// TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
		// else{

		// }
	}

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
		if (itemstack.getItemDamage() > 0)
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

}
