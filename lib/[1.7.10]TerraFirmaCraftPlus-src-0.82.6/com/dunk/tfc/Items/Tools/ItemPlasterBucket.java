package com.dunk.tfc.Items.Tools;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPlasterBucket extends ItemTerra
{

	public ItemPlasterBucket()
	{
		super();
		this.setFolder("tools/");
		this.setSize(EnumSize.MEDIUM);
	}

	public ItemPlasterBucket(Block contents, Item container)
	{
		this.setContainerItem(container);
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if (nbt != null && nbt.hasKey("TempTimer"))
		{
			long temp = nbt.getLong("TempTimer");
			if(TFC_Time.getTotalHours() - temp >= 11)
				is = new ItemStack(TFCItems.woodenBucketGypsum,1,is.getItemDamage());
		}
		return true;
	}
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int) (1000);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		return is;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return true;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
