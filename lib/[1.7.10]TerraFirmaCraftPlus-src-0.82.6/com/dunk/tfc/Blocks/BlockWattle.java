package com.dunk.tfc.Blocks;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomWall;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWattle extends BlockTerra
{
	public BlockWattle(Material m)
	{
		super(m);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}
	
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		Random rand = new Random();
		drops.add(new ItemStack(TFCItems.stick,rand.nextInt(2)+1));
		return drops;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) 
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			TerraFirmaCraft.LOG.info("Meta=" + (new StringBuilder()).append(this.getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)  
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		if(!world.isRemote)
		{
			ItemStack stack = player.inventory.getCurrentItem();
			if(stack != null && stack.getItem() == TFCItems.mud)
			{
				world.setBlock(x, y, z, TFCBlocks.wattleDaub);
				stack.stackSize--;
				if(stack.stackSize == 0)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}
				else
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
				}
				return true;
			}
		}
		return false;
	}

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
    {
        return false;
    }
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wattle");
	}

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.wattleRenderId;
	}
	
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        this.maxY = 1.5D;
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean flag0 = this.canConnectWallTo(world, x, y, z - 1);
        boolean flag1 = this.canConnectWallTo(world, x, y, z + 1);
        boolean flag2 = this.canConnectWallTo(world, x - 1, y, z);
        boolean flag3 = this.canConnectWallTo(world, x + 1, y, z);
        //The up flags. These check if there is an adjacent block above us that would raise the height of the wall
        boolean flag0Up = this.canConnectWallTo(world, x, y, z - 1);
        boolean flag1Up = this.canConnectWallTo(world, x, y, z + 1);
        boolean flag2Up = this.canConnectWallTo(world, x - 1, y, z);
        boolean flag3Up = this.canConnectWallTo(world, x + 1, y, z);
        float f = 0.425F;
        float f1 = 0.575F;
        float f2 = 0.45F;
        float f3 = 0.575F;
        float f4 = 1.1F;

        if (flag0)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        if (flag2)
        {
            f = 0.0F;
        }

        if (flag3)
        {
            f1 = 1.0F;
        }

        if (flag0 && flag1 && !flag2 && !flag3)
        {
        	if(!(flag0Up && flag1Up)){
        		f4 = 1.1F;
        	}
            f = 0.425F;
            f1 = 0.575F;
        }
        else if (!flag0 && !flag1 && flag2 && flag3)
        {
        	if(!(flag2Up && flag3Up)){
        		f4 = 1.1F;
        	}
            f2 = 0.425F;
            f3 = 0.575F;
        }
        f4 = 1;
        this.setBlockBounds(f, 0.0F, f2, f1, f4, f3);
        
    }

	public boolean canConnectWallTo(IBlockAccess access, int i, int j, int k)
	{
		Block block = access.getBlock(i, j, k);
		if (block != this && block != Blocks.fence_gate && block != TFCBlocks.fenceGate && block != TFCBlocks.fenceGate2 && !(block instanceof BlockCustomWall))
			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
		else
			return true;
	}
	
	/**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return p_149646_5_ == 0 ? super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_) : true;
    }

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
}
