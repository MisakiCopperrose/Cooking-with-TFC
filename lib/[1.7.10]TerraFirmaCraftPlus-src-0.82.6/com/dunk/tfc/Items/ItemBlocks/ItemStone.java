package com.dunk.tfc.Items.ItemBlocks;

import java.util.Arrays;
import java.util.List;

import com.dunk.tfc.Blocks.BlockChimney;
import com.dunk.tfc.Blocks.BlockMudBricks;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemStone extends ItemTerraBlock
{
	public ItemStone(Block b)
	{
		super(b);
		if(b instanceof BlockChimney || b instanceof BlockMudBricks)
		{
			if(b == TFCBlocks.chimneyBricks2 || b == TFCBlocks.chimneyLargeBricks2 || b == TFCBlocks.mudBricks2)
			{
				metaNames = Arrays.copyOfRange(Global.STONE_ALL, 16, Global.STONE_ALL.length);
			}
			else if(b == TFCBlocks.brickChimney)
			{
				metaNames = new String[] {"Brick"};
			}
			else
			{
				metaNames = Arrays.copyOfRange(Global.STONE_ALL, 0,16);
			}
		}
		else if(TFC_Core.isStoneIgEx(b)) metaNames = Global.STONE_IGEX;
		else if(TFC_Core.isStoneIgIn(b)) metaNames = Global.STONE_IGIN;
		else if(TFC_Core.isStoneSed(b)) metaNames = Global.STONE_SED;
		else if(TFC_Core.isStoneMM(b)) metaNames = Global.STONE_MM;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		
		Block b = Block.getBlockFromItem(is.getItem());
		if (!(b instanceof BlockMudBricks))
			return;

		int dam = is.getItemDamage();
		if (b == TFCBlocks.mudBricks2)
		{
			dam += 16;
		}

		if (dam < Global.STONE_ALL.length)
			arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[dam]);
		else
			arraylist.add(EnumChatFormatting.DARK_RED + "Unknown");
	}
	
	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}
}
