package com.dunk.tfc.Items;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemClothingPiece extends ItemTerra
{

	// ShirtBody has wool, linen, silk, leather and wolfFur
	// Shirt sleeves has wool, linen, silk, leather and wolfFur
	// Boot sole has leather, wolfFur, bearFur
	// leather strap has leather
	// Sock has linen, silk, wool
	// Coat has wolfFur, linen, silk, wool, leather
	// Coat also has two pieces? Dang.
	
	boolean[] pieceTypes = new boolean[10];
	int[] indices = new int[10];
	
	public enum ClothingEnum
	{
		WOOL, SILK, LEATHER, LINEN, WOLFFUR, BEARFUR, BURLAP, HIDE, COTTON, FUR, NONE
	};

	public ItemClothingPiece()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		for(int i =0;i<indices.length;i++)
		{
			indices[i] = -1;
		}
	}
	
	public int getIndexForClothingMaterial(ClothingEnum mat)
	{
		switch(mat)
		{
		case WOOL:
			return indices[0];
		case SILK:
			return indices[1];
		case LEATHER:
			return indices[2];
		case LINEN:
			return indices[3];
		case WOLFFUR:
			return indices[4];
		case BEARFUR:
			return indices[5];
		case BURLAP:
			return indices[6];
		case HIDE:
			return indices[7];
		case COTTON:
			return indices[8];
		case FUR:
			return indices[9];
			default:
				return -1;
		}
	}
	
	public ItemClothingPiece setPieceTypes(boolean[] pieces)
	{
		this.pieceTypes = pieces;
		int c=0;
		ArrayList<String> names = new ArrayList<String>();
		for(boolean b : pieceTypes)
		{
			if(b)
			{
				names.add(ClothingEnum.values()[c].toString());
				c++;
			}
		}
		this.metaNames = new String[c];
		metaIcons = new IIcon[c];
		c = 0;
		if(pieceTypes[0])
		{
			metaNames[c] = "wool";
			indices[0] = c;
			c++;
		}
		if(pieceTypes[1])
		{
			metaNames[c] = "silk";
			indices[1] = c;
			c++;
		}
		if(pieceTypes[2])
		{
			metaNames[c] = "leather";
			indices[2] = c;
			c++;
		}
		if(pieceTypes[3])
		{
			metaNames[c] = "linen";
			indices[3] = c;
			c++;
		}
		if(pieceTypes[4])
		{
			metaNames[c] = "wolf";
			indices[4] = c;
			c++;
		}
		if(pieceTypes[5])
		{
			metaNames[c] = "bear";
			indices[5] = c;
			c++;
		}
		if(pieceTypes.length > 6 && pieceTypes[6])
		{
			metaNames[c] = "burlap";
			indices[6] = c;
			c++;
		}
		if(pieceTypes.length > 7 && pieceTypes[7])
		{
			metaNames[c] = "hide";
			indices[7] = c;
			c++;
		}
		if(pieceTypes.length > 8 && pieceTypes[8])
		{
			metaNames[c] = "cotton";
			indices[8] = c;
			c++;
		}
		if(pieceTypes.length > 9 && pieceTypes[9])
		{
			metaNames[c] = "fur";
			indices[9] = c;
			c++;
		}
		return this;
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		int c = 0;
		for(boolean b : pieceTypes)
		{
			if(b)
			{
				list.add(new ItemStack(this,1,c));
				c++;
			}
		}
	}

	// This is how many materials we have
	

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		int c =0;
		if(pieceTypes[0])
		{
			metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Wool");
			c++;
		}
		if(pieceTypes[1])
		{
			metaIcons[c] = registerer
					.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Silk");
		
		c++;
		}
		if(pieceTypes[2])
		{
			metaIcons[c] = registerer.registerIcon(
					Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Leather");
		c++;
		}
		if(pieceTypes[3])
		{
		metaIcons[c] = registerer.registerIcon(
				Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Linen");
		c++;
		}
		if(pieceTypes[4])
		{
		metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Wolf");
		c++;
		}
		if(pieceTypes[5])
		{
			metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Bear");
			c++;
		}
		if(pieceTypes.length > 6 && pieceTypes[6])
		{
			metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Burlap");
			c++;
		}
		if(pieceTypes.length > 7 && pieceTypes[7])
		{
			metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Hide");
			c++;
		}
		if(pieceTypes.length > 8 && pieceTypes[8])
		{
			metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Cotton");
			c++;
		}
		if(pieceTypes.length > 9 && pieceTypes[9])
		{
			metaIcons[c] = registerer
				.registerIcon(Reference.MOD_ID + ":" + "armor/clothing/" + getUnlocalizedName().substring(5) + " Fur");
			c++;
		}
	}

	public ClothingEnum getMaterialFromClothingPiece(ItemStack i)
	{
		int dam = i.getItemDamage();
		for(int b = 0; b < indices.length;b++)
		{
			if(indices[b]==dam)
			{
				return ClothingEnum.values()[b];
			}
		}
		return ClothingEnum.NONE;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{

		// The needle has thread in this case, since even numbers mean no thread
		// and odd mean thread.
		if (itemstack != null)
		{
			entityplayer.openGui(TerraFirmaCraft.instance, 51, entityplayer.worldObj, (int) entityplayer.posX,
					(int) entityplayer.posY, (int) entityplayer.posZ);
		}
		return false;
	}
}
