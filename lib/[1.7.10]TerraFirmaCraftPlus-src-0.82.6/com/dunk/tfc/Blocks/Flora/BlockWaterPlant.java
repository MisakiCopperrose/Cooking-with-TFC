package com.dunk.tfc.Blocks.Flora;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.Terrain.BlockSand;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.Tools.ItemKnife;
import com.dunk.tfc.TileEntities.TEWaterPlant;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWaterPlant extends BlockSand implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon pondWeed;
	private IIcon seaWeed;
	private IIcon catTails;

	private static String seaweed = Reference.MOD_ID + ":" + "plants/seaweed";
	private static String pondweed = Reference.MOD_ID + ":" + "plants/pondweed";
	private static String cattails = Reference.MOD_ID + ":" + "plants/Cat Tails";


	public BlockWaterPlant(int texOff)
	{
		super(texOff);
		float var3 = 0.5F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Don't
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.waterPlantRenderId;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random r)
	{
		return 1 + r.nextInt(i * 2 + 1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		if(!TFC_Core.isSaltWater(world.getBlock(x, y+1, z)))
			return new ArrayList<ItemStack>();

		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(getSeaWeed(world.rand));
		TEWaterPlant te = (TEWaterPlant)world.getTileEntity(x, y, z);
		if(te != null)
			ret.add(new ItemStack(te.getBlockFromType(),1, metadata));
		return ret;
	}

	/* Left-Click Harvest */
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		if (!world.isRemote)
		{
			if (TFC_Core.isSaltWater(world.getBlock(x, y+1, z)) && entityplayer.inventory.getCurrentItem() != null && 
					entityplayer.inventory.getCurrentItem().getItem() instanceof ItemKnife)
			{
				dropBlockAsItem(world, x, y, z, getSeaWeed(world.rand));
				doBeforeFall(world, x, y, z);
			}
		}
	}

	/* Right-Click Harvest */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (TFC_Core.isSaltWater(world.getBlock(x, y + 1, z)) && entityplayer.inventory.getCurrentItem() != null && 
					entityplayer.inventory.getCurrentItem().getItem() instanceof ItemKnife)
			{
				//dropBlockAsItem(world, x, y, z, getSeaWeed(world.rand));
				EntityItem ei = new EntityItem(world,x,y+1,z,getSeaWeed(world.rand));
				ei.motionX = ei.motionY = ei.motionZ = 0;
				world.spawnEntityInWorld(ei);
				doBeforeFall(world, x, y, z);
				return true;
			}
		}
		return false;
	}

	private ItemStack getSeaWeed(Random r)
	{
		return ItemFoodTFC.createTag(new ItemStack(TFCItems.seaWeed, 1, 0), Helper.roundNumber(2 + r.nextFloat() * 5, 10));
	}

	protected boolean canThisPlantGrowUnderThisBlock(Block par1)
	{
		return true;//TFC_Core.isSaltWater(par1)|| TFC_Core.isFreshWater(par1);
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if(world.getBlock(x, y, z).getMaterial() != Material.water)
			{
				this.doBeforeFall(world, x, y, z);
			}
		}
		else super.onNeighborBlockChange(world, x, y, z, block);

	}

	protected void doBeforeFall(World world, int x, int y, int z){
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TEWaterPlant){
			Block block = ((TEWaterPlant)te).getBlockFromType();
			int meta = world.getBlockMetadata(x,y,z);
			if(block != null){
				world.setBlock(x, y, z, block, meta,0);
			}
			else{
				world.setBlockToAir(x,y,z);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		seaWeed = registerer.registerIcon(seaweed);//registerer.registerIcon(Reference.ModID + ":" + "plants/Sea Grass");
		pondWeed = registerer.registerIcon(pondweed);//registerer.registerIcon(Reference.ModID + ":" + "plants/Pond Grass");
		catTails = registerer.registerIcon(cattails);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		switch(par2){
		case 0: return seaWeed;
		case 1: return pondWeed;
		case 2: return catTails;
		default: return catTails;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEWaterPlant();
	}
}
