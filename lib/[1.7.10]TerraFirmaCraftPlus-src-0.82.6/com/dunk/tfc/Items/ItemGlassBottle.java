package com.dunk.tfc.Items;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemGlassBottle extends ItemTerra
{
	public ItemGlassBottle()
	{
		this.setCreativeTab(TFCTabs.TFC_FOODS);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer entity)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, entity, true);
		if(mop == null)
		{
			
		}
		else
		{
			if(mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				
				// Handle flowing water
				int flowX = x;
				int flowY = y;
				int flowZ = z;
				switch(mop.sideHit)
				{
				case 0:
					flowY = y - 1;
					break;
				case 1:
					flowY = y + 1;
					break;
				case 2:
					flowZ = z - 1;
					break;
				case 3:
					flowZ = z + 1;
					break;
				case 4:
					flowX = x - 1;
					break;
				case 5:
					flowX = x + 1;
					break;
				}

				if (!entity.isSneaking() && !world.isRemote && 
						TFC_Core.isFreshWater(world.getBlock(x, y, z)) || TFC_Core.isFreshWater(world.getBlock(flowX, flowY, flowZ)))
				{
					--is.stackSize;
					if (!entity.inventory.addItemStackToInventory(new ItemStack(TFCItems.waterBottle)))
					{
						// If we couldn't fit the empty bottle in the inventory, and we
						// drank the last alcohol bottle, put the empty bottle in the empty
						// held slot
						if (is.stackSize == 0)
							return new ItemStack(TFCItems.waterBottle);
						// If we couldn't fit the empty bottle in the inventory, and there
						// is more alcohol left in the stack, drop the bottle on the ground
						else
							entity.dropPlayerItemWithRandomChoice(new ItemStack(TFCItems.glassBottle), false);
					}
				}

				if(!world.canMineBlock(entity, x, y, z))
				{
					return is;
				}

				if(!entity.canPlayerEdit(x, y, z, mop.sideHit, is))
				{
					return is;
				}
				
			}
		}
		return is;
	}
}
