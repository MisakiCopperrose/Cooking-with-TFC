package com.dunk.tfc.Items.ItemBlocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.ISmeltable;
import com.dunk.tfc.api.Interfaces.IEquipable.ClothingType;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ItemAnvil extends ItemTerraBlock implements ISmeltable, IEquipable
{
	public ItemAnvil(Block par1)
	{
		super(par1);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is) {

		return 1400;
	}

	@Override
	public boolean isSmeltable(ItemStack is) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}

	@Override
	public EquipType getEquipType(ItemStack is) {
		// TODO Auto-generated method stub
		return EquipType.BACK;
	}

	@Override
	public void onEquippedRender() 
	{
		GL11.glRotatef(180, 0, 1, 0);
		GL11.glRotatef(90F, 1F, 0F, 0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.3F);
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ClothingType getClothingType()
	{
		// TODO Auto-generated method stub
		return ClothingType.NULL;
	}
	
	@Override
	public ResourceLocation getClothingTexture(Entity entity, ItemStack itemstack, int num)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
