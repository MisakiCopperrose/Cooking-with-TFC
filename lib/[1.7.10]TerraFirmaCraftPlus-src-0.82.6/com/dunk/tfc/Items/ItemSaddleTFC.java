package com.dunk.tfc.Items;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.api.Interfaces.ISewable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class ItemSaddleTFC extends ItemTerra implements ISewable
{

	boolean[][] clothingAlpha;
	ResourceLocation res;
	
	public ItemSaddleTFC()
	{
		this.maxStackSize = 1;
		this.textureFolder +="armor/clothing/";
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer
				.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));

/*		flatIcon = registerer.registerIcon(
				Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", "Flat "));
		*/
		res = new ResourceLocation(Reference.MOD_ID,
				Reference.ASSET_PATH_ITEM +this.textureFolder + this.getUnlocalizedName().replace("item.", "Flat ")+".png");
		clothingAlpha = TFC_Textures.loadClothingPattern(res);
		
	}
	
	@Override
	public ResourceLocation getFlatTexture()
	{
		return res;
	}

	@Override
	public boolean[][] getClothingAlpha()
	{
		return clothingAlpha;
	}
	
	@Override
	public Item setRepairCost(int i)
	{
		return this;
	}

	@Override
	public int getRepairCost()
	{
		return 1;
	}

}
