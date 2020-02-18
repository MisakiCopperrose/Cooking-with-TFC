package com.dunk.tfc.Items;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Render.Item.PoleItemRenderer;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemStick extends ItemTerra
{
	public ItemStick()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		MinecraftForgeClient.registerItemRenderer(this, new PoleItemRenderer());
		super.registerIcons(registerer);
	}
	
	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.SMALL;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.LIGHT;
	}
}
