package com.dunk.tfc.Blocks.Flora;

import com.dunk.tfc.api.TFCItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockSkinnyLogNatural extends BlockLogNatural {

	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{		
		//we need to make sure the player has the correct tool out
		boolean isAxe = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if(!world.isRemote)
		{
			if (equip != null)
			{
				int[] equipIDs = OreDictionary.getOreIDs(equip);
				for (int id : equipIDs)
				{
					String name = OreDictionary.getOreName(id);
					if (name.startsWith("itemAxe"))
					{
						isAxe = true;
						if (name.startsWith("itemAxeStone"))
						{
							isStone = true;
							break;
						}
					}
					else if (name.startsWith("itemHammer"))
					{
						isHammer = true;
						break;
					}
				}

				if (isAxe)
				{
					damage = -1;
					processTree(world,entityplayer, x, y, z, meta, equip);

					if (damage + equip.getItemDamage() > equip.getMaxDamage())
					{
						int ind = entityplayer.inventory.currentItem;
						entityplayer.inventory.setInventorySlotContents(ind, null);
						world.setBlock(x, y, z, this, meta, 0x2);
					}
					else
						equip.damageItem(damage, entityplayer);

					int smallStack = logs % 16;
					dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.logs, smallStack, damageDropped(meta)));
					logs -= smallStack;

					// In theory this section should never be triggered since the full stacks are dropped higher up the tree, but keeping it here just in case.
					while (logs > 0)
					{
						dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.logs, 16, damageDropped(meta)));
						logs -= 16;
					}

				}
				else if (isHammer)
				{
					EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.stick, 1 + world.rand.nextInt(3)));
					world.spawnEntityInWorld(item);
				}
			}
			else
				world.setBlock(x, y, z, this, meta, 0x2);
		}
	}
}
