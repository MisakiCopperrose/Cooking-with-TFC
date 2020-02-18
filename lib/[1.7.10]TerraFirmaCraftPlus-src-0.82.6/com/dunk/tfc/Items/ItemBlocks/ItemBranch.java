package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBranch extends ItemTerraBlock
{
	public ItemBranch(Block b)
	{
		super(b);
		this.metaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, this.metaNames, 0, 16);
		this.icons = new IIcon[metaNames.length];
	}

	@Override
	public IIcon getIconFromDamage(int index)
	{
		return icons[index%16];
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < this.metaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + this.metaNames[i] + " Log");
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
}
