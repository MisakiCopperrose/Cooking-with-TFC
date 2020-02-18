package com.dunk.tfc.Items.Tools;

import java.util.Set;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemCustomScythe extends ItemTerraTool implements ICausesDamage
{
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[]
	{ TFCBlocks.leaves, TFCBlocks.leaves2 });

	public ItemCustomScythe(ToolMaterial e)
	{
		super((int)-(e.getDamageVsEntity()*0.3f),e, BLOCKS);
		this.setMaxDamage(e.getMaxUses()*3);
//		this.damageVsEntity = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
		setCreativeTab(TFCTabs.TFC_TOOLS);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}

	@Override
	public EnumDamageType getDamageType(EntityLivingBase entity)
	{
		// TODO Auto-generated method stub
		return EnumDamageType.SLASHING;
	}
}