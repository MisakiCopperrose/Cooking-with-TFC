package com.dunk.tfc.Core.Player;

import java.util.UUID;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.SkillStats.SkillRank;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Tools.ChiselManager;

import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

public class PlayerInfo
{
	public String playerName;
	public UUID playerUUID;
	public byte chiselMode;
	public int hoeMode;
	public int knifeMode;

	public int lockX = -9999999;
	public int lockY = -9999999;
	public int lockZ = -9999999;

	public ItemStack specialCraftingType;
	public ItemStack specialCraftingTypeAlternate;
	public ISound playedNote;
	public float playedPitch;
	public float lastPlayedPitch;
	public long tickPlayed;
	public int notePlayed;
	private long lastChange;

	public ItemStack[] myExtraItems;

	public short moldTransferTimer = 1000;

	// Clientside only variables
	public boolean guishowFoodRestoreAmount;
	public float guiFoodRestoreAmount;
	public boolean[] knappingInterface;

	public boolean clothingWetLock = false;
	private boolean canAttack = true;
	private int attackTimer = 0;

	public boolean strumLock = false;

	public SkillStats tempSkills;
	public ItemStack[] tempEquipment = new ItemStack[TFC_Core.getExtraEquipInventorySize()];
	
	public ChunkCoordinates overrideSpawnCoordinates = null;
	public boolean hasOverrideSpawn = false;

	public PlayerInfo(String name, UUID uuid)
	{
		playerName = name;
		playerUUID = uuid;
		chiselMode = 0;
		specialCraftingType = null;
		specialCraftingTypeAlternate = null;
		lastChange = 0;
		knifeMode = 0;
		hoeMode = 0;
		knappingInterface = new boolean[25];
	}

	public void switchKnifeMode(EntityPlayer player)
	{
		final int MODE_SLASH = 0;
		final int MODE_STAB = 1;
		if (lastChange + 3 < TFC_Time.getTotalTicks())
		{
			knifeMode = (knifeMode + 1) % 2;
			lastChange = TFC_Time.getTotalTicks();
		}
	}

	public void switchHoeMode(EntityPlayer player)
	{
		// final int MODE_NORMAL = 0;
		final int MODE_NUTRIENT = 1;
		// final int MODE_WATER= 2; final int MODE_HARVEST = 3;
		SkillRank agRank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_AGRICULTURE);
		/*
		 * if(Agrank != SkillRank.Expert && Agrank != SkillRank.Master) return;
		 */
		if (lastChange + 3 < TFC_Time.getTotalTicks())
		{
			boolean isMetalHoe = true;

			if (player.getCurrentEquippedItem() != null && (player.getCurrentEquippedItem()
					.getItem() == TFCItems.igInHoe || player.getCurrentEquippedItem()
							.getItem() == TFCItems.igExHoe || player.getCurrentEquippedItem()
									.getItem() == TFCItems.sedHoe || player.getCurrentEquippedItem()
											.getItem() == TFCItems.mMHoe))
			{
				isMetalHoe = false;
			}

			hoeMode = hoeMode == 3 ? 0 : ++hoeMode;
			if (hoeMode == MODE_NUTRIENT && (!isMetalHoe || isMetalHoe && agRank != SkillRank.Expert && agRank != SkillRank.Master))
				hoeMode++;

			lastChange = TFC_Time.getTotalTicks();
		}
	}

	public void switchChiselMode()
	{
		if (lastChange + 3 < TFC_Time.getTotalTicks())
		{
			// Bump ChiselMode on switchChiselMode,
			// reset to zero when the last mode is reached.
			if (chiselMode == ChiselManager.getInstance().getSize() - 1)
			{
				chiselMode = 0;
			}
			else
			{
				chiselMode++;
			}
			lastChange = TFC_Time.getTotalTicks();
		}
	}
	
	//For client-side use to set when an update is received
	public void setCanAttack(boolean b)
	{
		this.canAttack = b;
	}
	
	public boolean canAttack()
	{
		return this.canAttack;
	}
	
	
	//Pass the item and the amount to set it by. This allows us to do anything like modify the time based on the player's abilities
	public void setAttackTimer(int i, Item theItem)
	{
		this.attackTimer = i;
	}
	
	public void updateAttackTimer()
	{
		if(attackTimer < 0)
		{
			attackTimer = 0;
		}
		if(attackTimer > 0)
		{
			attackTimer--;
		}
		if(attackTimer > 0)
		{
			canAttack = false;
		}
		else
		{
			canAttack = true;
		}
	}

	// Set the ChiselMode directly on the server side.
	public void setChiselMode(byte mode)
	{
		chiselMode = mode;
	}

	// Set the ChiselMode directly on the server side.
	public void setKnifeMode(byte mode)
	{
		knifeMode = mode;
	}

	public boolean lockMatches(int x, int y, int z)
	{
		return (lockX == -9999999 || lockX == x) && (lockY == -9999999 || lockY == y) && (lockZ == -9999999 || lockZ == z);
	}
}
