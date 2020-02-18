package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.Flora.BlockLogHoriz;
import com.dunk.tfc.Blocks.Flora.BlockStackedLogHoriz;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.TELeatherRack;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemRawHide extends ItemLooseRock
{
	public ItemRawHide()
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = new String[]{"small","medium","large"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.MEDIUM);
	}


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			Block b = world.getBlock(x,y,z);
			//We're making a teepee. 
			if(this == TFCItems.hide && b != TFCBlocks.thatch)
			{
				return ItemRawHide.createTeepee(this, itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ);
			}
			else if(itemstack.getItem() == TFCItems.hide && itemstack.getItemDamage() >= 2){
				int d = (int) ((45 + (entityplayer.rotationYaw % 360 + 360f) % 360) / 90) % 4; //direction
				int x2 = x + (d == 1 ? -1 : d == 3 ? 1 : 0); // the x-coord of the second block
				int z2 = z + (d == 2 ? -1 : d == 0 ? 1 : 0);
				if(world.getBlock(x, y, z)==TFCBlocks.thatch && side == 1 && world.getBlock(x2,y,z2)==TFCBlocks.thatch
						&& world.isAirBlock(x, y+1, z) && world.isAirBlock(x2, y+1, z2)){
					world.func_147480_a/*destroyBlock*/(x, y, z, false);
					world.func_147480_a/*destroyBlock*/(x2, y, z2, false);
					world.setBlock(x, y, z, TFCBlocks.strawHideBed, d, 2);
					world.setBlock(x2, y, z2, TFCBlocks.strawHideBed, d+8, 2);
					itemstack.stackSize--;
				}
			}
			else if(itemstack.getItem() == TFCItems.soakedHide && side == ForgeDirection.UP.ordinal() )
			{
				if((world.getBlock(x, y, z) instanceof BlockLogHoriz || world.getBlock(x, y, z) instanceof BlockStackedLogHoriz) && world.isAirBlock( x, y + 1, z ) && world.setBlock(x, y+1, z, TFCBlocks.leatherRack))
				{
					TELeatherRack te = (TELeatherRack)world.getTileEntity(x, y+1, z);
					te.setLeather(itemstack);
				}

			}
		}
		return true;
	}
	
	protected static Block getTeepeeFromSkin(Item skin)
	{
		if(skin ==  TFCItems.bearFur || skin ==  TFCItems.bearFurScrap)
		{
			return TFCBlocks.bearTeepee;
		}
		else if(skin == TFCItems.wolfFur || skin == TFCItems.wolfFurScrap)
		{
			return TFCBlocks.wolfTeepee;
		}
		return TFCBlocks.teepee;
	}
	
	public static boolean createTeepee(Item skin, ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block b = world.getBlock(x,y,z);
		boolean teepeeAreaClear = true;
		for(int i = -1; i <2 && teepeeAreaClear;i++)
		{
			for(int j = 2; j < 5 && teepeeAreaClear; j++)
			{
				for(int k = -1; k < 2 && teepeeAreaClear; k++)
				{
					if(!(world.getBlock(x+i, y+j, z+k).isReplaceable(world, x+i, y+j, z+k)))
					{
						teepeeAreaClear = false;
					}
				}
			}
		}
		if((TFC_Core.isGrass(b) || TFC_Core.isDirt(b) || TFC_Core.isSand(b) || TFC_Core.isGravel(b)) && side == 1  && !world.isRemote
				&& world.getBlock(x-1, y+1, z-1) == TFCBlocks.woodSpear && world.getBlock(x+1, y+1, z-1) == TFCBlocks.woodSpear
				&& world.getBlock(x-1, y+1, z+1) == TFCBlocks.woodSpear && world.getBlock(x+1, y+1, z+1) == TFCBlocks.woodSpear
				&& world.getBlockMetadata(x-1, y+1, z-1) == 0 && world.getBlockMetadata(x+1, y+1, z-1) == 0
				&& world.getBlockMetadata(x-1, y+1, z+1) == 0 && world.getBlockMetadata(x+1, y+1, z+1) == 0 
				&& ((itemstack.getItemDamage() == 2 && itemstack.stackSize >=2)||(itemstack.getItemDamage() == 1 && itemstack.stackSize >=4))
				&& world.getBlock(x-1, y+1, z).isReplaceable(world,x-1, y+1, z) && world.getBlock(x+1, y+1, z).isReplaceable(world,x+1, y+1, z)
				&& world.getBlock(x, y+1, z-1).isReplaceable(world,x, y+1, z-1) && world.getBlock(x, y+1, z+1).isReplaceable(world,x, y+1, z+1)
				&& world.getBlock(x, y+1, z).isReplaceable(world, x, y+1, z)
				&& teepeeAreaClear)
		{
			int d = (int) ((45 + (entityplayer.rotationYaw % 360 + 360f) % 360) / 90) % 4; //direction
			world.setBlock(x, y+1, z, getTeepeeFromSkin(skin),(itemstack.getItemDamage()-1) * (4) + d,2);
			world.setBlockMetadataWithNotify(x-1, y+1, z-1, 1, 2);
			world.setBlock(x-1, y+2, z-1,TFCBlocks.invisibleBlock,0,2);
			world.setBlockMetadataWithNotify(x-1, y+1, z+1, 2, 2);
			world.setBlock(x-1, y+2, z+1,TFCBlocks.invisibleBlock,0,2);
			world.setBlockMetadataWithNotify(x+1, y+1, z+1, 3, 2);
			world.setBlock(x+1, y+2, z+1,TFCBlocks.invisibleBlock,0,2);
			world.setBlockMetadataWithNotify(x+1, y+1, z-1, 4, 2);
			world.setBlock(x+1, y+2, z-1,TFCBlocks.invisibleBlock,0,2);
			
			if(d!=3)
			{
				//world.setBlock(x-1, y+1, z,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x-1, y+2, z,TFCBlocks.invisibleBlock,0,2);
			}
			else
			{
				world.setBlock(x+1, y+1, z,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x-1, y+1, z,TFCBlocks.invisibleBlock,1,2);
			}
			
			if(d!=1)
			{
				//world.setBlock(x+1, y+1, z,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x+1, y+2, z,TFCBlocks.invisibleBlock,0,2);
			}
			else
			{
				world.setBlock(x-1, y+1, z,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x+1, y+1, z,TFCBlocks.invisibleBlock,1,2);
			}
			
			if(d!=2)
			{
			//world.setBlock(x, y+1, z+1,TFCBlocks.invisibleBlock,0,2);
			world.setBlock(x, y+2, z+1,TFCBlocks.invisibleBlock,0,2);
			}
			else
			{
				world.setBlock(x, y+1, z-1,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x, y+1, z+1,TFCBlocks.invisibleBlock,1,2);
			}
			
			if(d!=0)
			{
				//world.setBlock(x, y+1, z-1,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x, y+2, z-1,TFCBlocks.invisibleBlock,0,2);
			}
			else
			{
				world.setBlock(x, y+1, z+1,TFCBlocks.invisibleBlock,0,2);
				world.setBlock(x, y+1, z-1,TFCBlocks.invisibleBlock,1,2);
			}
			
			world.setBlock(x, y+4, z,TFCBlocks.invisibleBlock,0,2);
			
			if(itemstack.getItemDamage()==2 && !entityplayer.capabilities.isCreativeMode)
			{
				itemstack.stackSize-=2;
			}
			else if(itemstack.getItemDamage()==1 && !entityplayer.capabilities.isCreativeMode)
			{
				itemstack.stackSize-=4;
			}
			if(itemstack.stackSize == 0)
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
			else
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, itemstack);
			}
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return this.itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		// Blank to override ItemLooseRock's help tooltip.
	}
}
