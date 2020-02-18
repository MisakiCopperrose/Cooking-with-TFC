package com.dunk.tfc.Handlers.Network;

import java.util.HashMap;
import java.util.Map;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.BodyTempStats;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Core.Player.SkillStats;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
//import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class PlayerUpdatePacket extends AbstractPacket
{
	private byte flag;
	private float stomachLevel;
	private float waterLevel;
	private long soberTime;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private SkillStats playerSkills;
	private String skillName;
	private int skillLevel;
	private boolean craftingTable;
	private Map<String, Integer> skillMap = new HashMap<String, Integer>();

	public int temporaryHeatProtection;
	public int temporaryColdProtection;
	
	public int pierceWoundTimer, slashWoundTimer, crushWoundTimer;
	public int pierceWoundSeverity, slashWoundSeverity, crushWoundSeverity;
	public int crushWoundTreatment;
	
	public long tempColdTimeRemaining;
	public long tempHeatTimeRemaining;
	
	private int heatResistance;
	private int coldResistance;
	private int discomfort;
	private float ambientTemperature;
	private int timeRemaining;
	private boolean wetLock;
	
	private boolean canAttack;

	private int noteX, noteY, noteZ;

	private int playerNote;
	private float notePitch;
	private long noteTickPlayed;

	private byte lyreByte;

	public PlayerUpdatePacket()
	{
	}

	public PlayerUpdatePacket(EntityPlayer p, int f)
	{
		this.flag = (byte) f;
		if (this.flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(p);
			this.stomachLevel = fs.stomachLevel;
			this.waterLevel = fs.waterLevel;
			this.soberTime = fs.soberTime;
			this.nutrFruit = fs.nutrFruit;
			this.nutrVeg = fs.nutrVeg;
			this.nutrGrain = fs.nutrGrain;
			this.nutrProtein = fs.nutrProtein;
			this.nutrDairy = fs.nutrDairy;
			
			this.pierceWoundSeverity = fs.pierceWoundSeverity;
			this.slashWoundSeverity = fs.slashWoundSeverity;
			this.slashWoundTimer = fs.slashWoundTimer;
			this.pierceWoundTimer  = fs.pierceWoundTimer;
			this.crushWoundTimer  = fs.crushWoundTimer;
			this.crushWoundTreatment  = fs.crushWoundTreatment;
			this.crushWoundSeverity  = fs.crushWoundSeverity;
		}
		else if (this.flag == 2)
		{
			this.craftingTable = p.getEntityData().getBoolean("craftingTable");
		}
		else if (this.flag == 3)
		{
			this.playerSkills = TFC_Core.getSkillStats(p);
		}
		// Temperature packet
		else if (this.flag == 5)
		{
			BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(p);
			this.tempColdTimeRemaining = bodyTemp.tempColdTimeRemaining;
			this.tempHeatTimeRemaining = bodyTemp.tempHeatTimeRemaining;
			this.temporaryColdProtection = bodyTemp.temporaryColdProtection;
			this.temporaryHeatProtection = bodyTemp.temporaryHeatProtection;
			
			
			
			this.coldResistance = bodyTemp.coldResistance;
			this.heatResistance = bodyTemp.heatResistance;
			this.ambientTemperature = bodyTemp.ambientTemperature;
			this.timeRemaining = bodyTemp.timeRemaining;
			this.discomfort = bodyTemp.discomfort;
			
			this.wetLock = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).clothingWetLock;
			
			this.canAttack = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).canAttack();
		}
		else if (this.flag == 6)
		{
			this.wetLock = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).clothingWetLock;
		}
		else if (this.flag == 7)
		{
			this.playerNote = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).notePlayed;
			this.notePitch = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).playedPitch;
			this.noteTickPlayed = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).tickPlayed;
			this.noteX = (int) p.posX;
			this.noteY = (int) p.posY;
			this.noteZ = (int) p.posZ;
		}
		else if (this.flag == 8)
		{
			this.playerNote = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).notePlayed;
			this.notePitch = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).playedPitch;
			this.noteX = (int) p.posX;
			this.noteY = (int) p.posY;
			this.noteZ = (int) p.posZ;
		}
		else if (this.flag == 9)
		{
			this.lyreByte = (byte) (PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).notePlayed
					% 128);
			this.noteX = (int) p.posX;
			this.noteY = (int) p.posY;
			this.noteZ = (int) p.posZ;
		}
		else if (this.flag == 10)
		{
			this.playerNote = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).notePlayed;
			this.notePitch = PlayerManagerTFC.getInstance().getPlayerInfoFromName(p.getDisplayName()).playedPitch;
			this.noteX = (int) p.posX;
			this.noteY = (int) p.posY;
			this.noteZ = (int) p.posZ;
		}
		/*
		 * else if(this.flag == 4) { // flag 4 -> Send a request to the server
		 * for the skills data. }
		 */
	}

	public PlayerUpdatePacket(int f, String name, int lvl)
	{
		this.flag = (byte) f;
		if (this.flag == 1)
		{
			this.skillName = name;
			this.skillLevel = lvl;
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeByte(this.flag);
		if (this.flag == 0)
		{
			buffer.writeFloat(this.stomachLevel);
			buffer.writeFloat(this.waterLevel);
			buffer.writeLong(this.soberTime);
			buffer.writeFloat(this.nutrFruit);
			buffer.writeFloat(this.nutrVeg);
			buffer.writeFloat(this.nutrGrain);
			buffer.writeFloat(this.nutrProtein);
			buffer.writeFloat(this.nutrDairy);
			buffer.writeInt(this.pierceWoundSeverity);
			buffer.writeInt(this.slashWoundSeverity);
			buffer.writeInt(this.crushWoundSeverity);
			buffer.writeInt(this.pierceWoundTimer);
			buffer.writeInt(this.slashWoundTimer);
			buffer.writeInt(this.crushWoundTimer);
			buffer.writeInt(this.crushWoundTreatment);
		}
		else if (this.flag == 1)
		{
			ByteBufUtils.writeUTF8String(buffer, this.skillName);
			buffer.writeInt(this.skillLevel);
		}
		else if (this.flag == 2)
		{
			buffer.writeBoolean(this.craftingTable);
		}
		else if (this.flag == 3)
		{
			this.playerSkills.toOutBuffer(buffer);
		}
		else if (this.flag == 5)
		{
			buffer.writeFloat(this.ambientTemperature);
			buffer.writeInt(this.coldResistance);
			buffer.writeInt(this.heatResistance);
			buffer.writeInt(this.timeRemaining);
			buffer.writeInt(this.discomfort);
			
			buffer.writeInt(this.temporaryColdProtection);
			buffer.writeInt(this.temporaryHeatProtection);
			buffer.writeLong(this.tempColdTimeRemaining);
			buffer.writeLong(this.tempHeatTimeRemaining);
			buffer.writeBoolean(this.canAttack);
			// buffer.writeBoolean(this.wetLock);
		}
		else if (this.flag == 6)
		{
			buffer.writeBoolean(this.wetLock);
		}
		else if (this.flag == 7)
		{
			buffer.writeInt(this.playerNote);
			buffer.writeFloat(this.notePitch);
			buffer.writeLong(this.noteTickPlayed);
			buffer.writeInt(this.noteX);
			buffer.writeInt(this.noteY);
			buffer.writeInt(this.noteZ);
		}
		else if (this.flag == 8)
		{
			buffer.writeInt(this.playerNote);
			buffer.writeFloat(this.notePitch);
			buffer.writeInt(this.noteX);
			buffer.writeInt(this.noteY);
			buffer.writeInt(this.noteZ);
		}
		else if (this.flag == 9)
		{
			buffer.writeByte(this.lyreByte);
			buffer.writeInt(this.noteX);
			buffer.writeInt(this.noteY);
			buffer.writeInt(this.noteZ);
		}
		else if (this.flag == 10)
		{
			buffer.writeInt(this.playerNote);
			buffer.writeFloat(this.notePitch);
			buffer.writeInt(this.noteX);
			buffer.writeInt(this.noteY);
			buffer.writeInt(this.noteZ);
		}
		/*
		 * else if(this.flag == 4) { // flag is all we need }
		 */
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.flag = buffer.readByte();
		if (this.flag == 0)
		{
			this.stomachLevel = buffer.readFloat();
			this.waterLevel = buffer.readFloat();
			this.soberTime = buffer.readLong();
			this.nutrFruit = buffer.readFloat();
			this.nutrVeg = buffer.readFloat();
			this.nutrGrain = buffer.readFloat();
			this.nutrProtein = buffer.readFloat();
			this.nutrDairy = buffer.readFloat();
			
			this.pierceWoundSeverity= buffer.readInt();
			this.slashWoundSeverity= buffer.readInt();
			this.crushWoundSeverity= buffer.readInt();
			this.pierceWoundTimer= buffer.readInt();
			this.slashWoundTimer= buffer.readInt();
			this.crushWoundTimer= buffer.readInt();
			this.crushWoundTreatment= buffer.readInt();
		}
		else if (this.flag == 1)
		{
			this.skillName = ByteBufUtils.readUTF8String(buffer);
			this.skillLevel = buffer.readInt();
		}
		else if (this.flag == 2)
		{
			this.craftingTable = buffer.readBoolean();
		}
		else if (this.flag == 3)
		{
			this.skillMap.clear();
			String name;
			int lvl;
			int size = buffer.readInt();
			for (int l = 0; l < size; l++)
			{
				name = ByteBufUtils.readUTF8String(buffer);
				lvl = buffer.readInt();
				this.skillMap.put(name, lvl);
			}
		}
		else if (this.flag == 5)
		{
			this.ambientTemperature = buffer.readFloat();
			this.coldResistance = buffer.readInt();
			this.heatResistance = buffer.readInt();
			this.timeRemaining = buffer.readInt();
			this.discomfort = buffer.readInt();
			
			this.temporaryColdProtection = buffer.readInt();
			this.temporaryHeatProtection = buffer.readInt();
			this.tempColdTimeRemaining = buffer.readLong();
			this.tempHeatTimeRemaining =  buffer.readLong();
			this.canAttack = buffer.readBoolean();
			// this.wetLock = buffer.readBoolean();
		}
		else if (this.flag == 6)
		{
			this.wetLock = buffer.readBoolean();
		}
		else if (this.flag == 7)
		{
			this.playerNote = buffer.readInt();
			this.notePitch = buffer.readFloat();
			this.noteTickPlayed = buffer.readLong();
			this.noteX = buffer.readInt();
			this.noteY = buffer.readInt();
			this.noteZ = buffer.readInt();
		}
		else if (this.flag == 8)
		{
			this.playerNote = buffer.readInt();
			this.notePitch = buffer.readFloat();
			this.noteX = buffer.readInt();
			this.noteY = buffer.readInt();
			this.noteZ = buffer.readInt();
		}
		else if (this.flag == 9)
		{
			this.lyreByte = buffer.readByte();
			this.noteX = buffer.readInt();
			this.noteY = buffer.readInt();
			this.noteZ = buffer.readInt();
		}
		else if (this.flag == 10)
		{
			this.playerNote = buffer.readInt();
			this.notePitch = buffer.readFloat();
			this.noteX = buffer.readInt();
			this.noteY = buffer.readInt();
			this.noteZ = buffer.readInt();
		}
		/*
		 * else if(this.flag == 4) { // flag is all we need }
		 */
	}

	@SideOnly(Side.CLIENT)
	private void clientFluteStuff(EntityPlayer player)
	{
		if (player.equals(Minecraft.getMinecraft().thePlayer))
		{
			// return;
		}
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName());
		if (pi != null)
		{
			String location = this.playerNote == 0 ? TFC_Sounds.JUGBLOWNOTE : TFC_Sounds.JUGBLOWNOTE2;
			if (pi != null && pi.playedNote != null && pi.lastPlayedPitch != notePitch
					&& Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(pi.playedNote))
			{
				Minecraft.getMinecraft().getSoundHandler().stopSound(pi.playedNote);
			}
			if (pi.playedPitch == 0)
			{
				pi.playedPitch = 0.2f;
			}
			if (pi != null && pi.playedNote != null && pi.lastPlayedPitch == notePitch
					&& (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(pi.playedNote)
							|| TFC_Time.getTotalTicks() > pi.tickPlayed + (7 / pi.playedPitch)))
			{

				net.minecraft.client.audio.PositionedSoundRecord fluteNote = new net.minecraft.client.audio.PositionedSoundRecord(
						new ResourceLocation(location), 0.7f, notePitch, noteX, noteY, noteZ);
				Minecraft.getMinecraft().getSoundHandler().playSound(fluteNote);
				pi.playedNote = fluteNote;
				pi.lastPlayedPitch = notePitch;
				pi.tickPlayed = TFC_Time.getTotalTicks();
			}
			else if (pi.lastPlayedPitch != notePitch)
			{
				net.minecraft.client.audio.PositionedSoundRecord fluteNote = new net.minecraft.client.audio.PositionedSoundRecord(
						new ResourceLocation(location), 0.7f, notePitch, noteX, noteY, noteZ);
				Minecraft.getMinecraft().getSoundHandler().playSound(fluteNote);
				pi.playedNote = fluteNote;
				pi.lastPlayedPitch = notePitch;
				pi.tickPlayed = TFC_Time.getTotalTicks();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void clientDrumStuff(EntityPlayer player)
	{
		if (player.equals(Minecraft.getMinecraft().thePlayer))
		{
			// return;
		}
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName());
		if (pi != null)
		{
			String location = this.playerNote == 2 ? TFC_Sounds.DRUMRIM
					: this.playerNote == 3 ? TFC_Sounds.DRUM : TFC_Sounds.BONGO;
			if (pi != null && pi.playedNote != null)
			{

				net.minecraft.client.audio.PositionedSoundRecord drumNote = new net.minecraft.client.audio.PositionedSoundRecord(
						new ResourceLocation(location), this.playerNote == 2 ? 0.8f : 0.6f, notePitch, noteX, noteY,
						noteZ);
				Minecraft.getMinecraft().getSoundHandler().playSound(drumNote);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void clientLyreStuff(EntityPlayer entityplayer)
	{
		if (entityplayer.equals(Minecraft.getMinecraft().thePlayer))
		{
			// return;
		}
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromName(entityplayer.getDisplayName());
		if (pi != null)
		{
			byte note = this.lyreByte;
			float volume = 0.6f;
			String location = TFC_Sounds.LYRE;
			PositionedSoundRecord DNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 16) == 16 ? 0 : volume), 0.793701f, noteX, noteY, noteZ);
			PositionedSoundRecord EflatNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 8) == 8 ? 0 : volume), 0.840896f, noteX, noteY, noteZ);
			PositionedSoundRecord GNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 4) == 4 ? 0 : volume), 1.059463f, noteX, noteY, noteZ);
			PositionedSoundRecord ANote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 2) == 2 ? 0 : volume), 1.189207f, noteX, noteY, noteZ);
			PositionedSoundRecord BflatNote = new PositionedSoundRecord(new ResourceLocation(location),
					((note & 1) == 1 ? 0 : volume), 1.259921f, noteX, noteY, noteZ);
			Minecraft.getMinecraft().getSoundHandler().playSound(DNote);
			Minecraft.getMinecraft().getSoundHandler().playSound(EflatNote);
			Minecraft.getMinecraft().getSoundHandler().playSound(ANote);
			Minecraft.getMinecraft().getSoundHandler().playSound(GNote);
			Minecraft.getMinecraft().getSoundHandler().playSound(BflatNote);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void clientHornStuff(EntityPlayer entityplayer)
	{
		if (entityplayer.equals(Minecraft.getMinecraft().thePlayer))
		{
			// return;
		}
		
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromName(entityplayer.getDisplayName());
		if (pi != null)
		{
			if(Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(pi.playedNote))
			{
				Minecraft.getMinecraft().getSoundHandler().stopSound(pi.playedNote);
			}
			String location = this.playerNote == 5 ? TFC_Sounds.HORN
					: this.playerNote == 6 ? TFC_Sounds.BRASSHORN : TFC_Sounds.CONCH;
			if(playerNote != 6)
			{
				this.notePitch*=2f;
			}
			//String location = TFC_Sounds.LYRE;			
			PositionedSoundRecord DNote = new PositionedSoundRecord(new ResourceLocation(location),
					2f, this.notePitch, noteX, noteY, noteZ);
			pi.playedNote = DNote;
			Minecraft.getMinecraft().getSoundHandler().playSound(DNote);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if (this.flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.stomachLevel = this.stomachLevel;
			fs.waterLevel = this.waterLevel;
			fs.soberTime = this.soberTime;
			fs.nutrFruit = this.nutrFruit;
			fs.nutrVeg = this.nutrVeg;
			fs.nutrGrain = this.nutrGrain;
			fs.nutrProtein = this.nutrProtein;
			fs.nutrDairy = this.nutrDairy;
			fs.pierceWoundSeverity = this.pierceWoundSeverity;
			fs.slashWoundSeverity = this.slashWoundSeverity;
			fs.pierceWoundTimer = this.pierceWoundTimer;
			fs.slashWoundTimer = this.slashWoundTimer;
			fs.crushWoundTimer = this.crushWoundTimer;
			fs.crushWoundSeverity = this.crushWoundSeverity;
			fs.crushWoundTreatment = this.crushWoundTreatment;
			TFC_Core.setPlayerFoodStats(player, fs);
		}
		else if (this.flag == 1)
		{
			this.playerSkills = TFC_Core.getSkillStats(player);
			this.playerSkills.setSkillSave(skillName, skillLevel);
		}
		else if (this.flag == 2)
		{
			if (this.craftingTable && !player.getEntityData().hasKey("craftingTable"))
			{
				player.getEntityData().setBoolean("craftingTable", this.craftingTable);
				PlayerInventory.upgradePlayerCrafting(player);
			}
		}
		else if (this.flag == 3)
		{
			this.playerSkills = TFC_Core.getSkillStats(player);
			for (String skill : skillMap.keySet())
			{
				playerSkills.setSkillSave(skill, skillMap.get(skill));
			}
			skillMap.clear();
		}
		else if (this.flag == 5)
		{
			BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
			bodyTemp.ambientTemperature = this.ambientTemperature;
			bodyTemp.coldResistance = this.coldResistance;
			bodyTemp.heatResistance = this.heatResistance;
			bodyTemp.timeRemaining = this.timeRemaining;
			bodyTemp.discomfort = this.discomfort;
			
			bodyTemp.tempColdTimeRemaining = this.tempColdTimeRemaining;
			bodyTemp.tempHeatTimeRemaining = this.tempHeatTimeRemaining;
			bodyTemp.temporaryColdProtection = this.temporaryColdProtection;
			bodyTemp.temporaryHeatProtection = this.temporaryHeatProtection;
			TFC_Core.setBodyTempStats(player, bodyTemp);
			PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).setCanAttack(this.canAttack);
			// PlayerManagerTFC.getInstance().getPlayerInfoFromName(player.getDisplayName()).clothingWetLock
			// = this.wetLock;
		}
		else if (this.flag == 6)
		{
			PlayerManagerTFC.getInstance()
					.getPlayerInfoFromName(player.getDisplayName()).clothingWetLock = this.wetLock;
		}
		else if (this.flag == 7)
		{
			clientFluteStuff(player);
		}
		else if (this.flag == 8)
		{
			clientDrumStuff(player);
		}
		else if (this.flag == 9)
		{
			clientLyreStuff(player);
		}
		else if (this.flag == 10)
		{
			clientHornStuff(player);
		}
		/*
		 * else if(this.flag == 4) { //NOOP on client }
		 */
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		if (this.flag == 4)
		{
			AbstractPacket pkt = new PlayerUpdatePacket(player, 3);
			TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
		}
		else if (this.flag == 7 || this.flag == 8 || this.flag == 9 || this.flag == 10)
		{
			// AbstractPacket pkt = new PlayerUpdatePacket(player, 7);
			TerraFirmaCraft.PACKET_PIPELINE.sendToAll(this);
		}
	}

}
