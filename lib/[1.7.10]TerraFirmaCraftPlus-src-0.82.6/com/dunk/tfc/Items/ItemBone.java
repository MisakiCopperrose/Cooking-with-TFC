package com.dunk.tfc.Items;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class ItemBone extends ItemTerra
{
	Random r = new Random();;

	public ItemBone()
	{
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack is)
	{
		boolean b = super.onEntitySwing(entityLiving, is);
		if (entityLiving.swingProgress == 0.5f && !entityLiving.worldObj.isRemote)
		{
			MovingObjectPosition mop = Helper.getMovingObjectPositionFromPlayer(entityLiving.worldObj, entityLiving,
					false);
			if (mop != null)
			{
				Block bl = entityLiving.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
				if (bl != null && bl.getMaterial() == Material.rock)
				{
					if (TFC_Time.getTotalTicks() % 60 < 5)
					{
						((EntityPlayer) entityLiving).inventory
								.decrStackSize(((EntityPlayer) entityLiving).inventory.currentItem, 1);
						((EntityPlayer) entityLiving).worldObj.playSoundAtEntity(((EntityPlayer) entityLiving),
								TFC_Sounds.BONEBREAK, 0.7f,
								((EntityPlayer) entityLiving).worldObj.rand.nextFloat() * 0.2F + 1.2F);
						((EntityPlayer) entityLiving).worldObj.playSoundAtEntity(((EntityPlayer) entityLiving),
								"random.break", 0.7f,
								((EntityPlayer) entityLiving).worldObj.rand.nextFloat() * 0.2F + 1.2F);
						if (r.nextInt(2) == 0)
						{
						/*	TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.boneNeedle, 1, 0),
									(EntityPlayer) entityLiving);*/
							entityLiving.dropItem(TFCItems.boneNeedle, 1);
						}
						entityLiving.dropItem(TFCItems.boneFragment, 1);
					}
					else if (TFC_Time.getTotalTicks() % 30 < 5)
					{
						((EntityPlayer) entityLiving).worldObj.playSoundAtEntity(((EntityPlayer) entityLiving),
								TFC_Sounds.BONEBREAK, 0.3f,
								((EntityPlayer) entityLiving).worldObj.rand.nextFloat() * 0.2F + 1.5F);
					}
				}
			}
		}
		return b;
	}

}
