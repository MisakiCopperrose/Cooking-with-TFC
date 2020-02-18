package com.dunk.tfc.Items.Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Entities.Mobs.EntityCowTFC;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Entities.IAnimal.GenderEnum;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemCustomBucket extends ItemTerra
{
	/** field for checking if the bucket has been filled. */
	private Block bucketContents;

	public ItemCustomBucket(Block contents)
	{
		super();
		this.bucketContents = contents;
		this.setFolder("tools/");
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public ItemStack getContainerItem(ItemStack is)
	{
		return super.getContainerItem(is);
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return 0;
	}

	public ItemCustomBucket(Block contents, Item container)
	{
		this(contents);
		this.setContainerItem(container);
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public boolean hasContainerItem(ItemStack is)
	{
		return !(is.hasTagCompound() && is.stackTagCompound.hasKey("plaster")) && (is
				.getItem() != TFCItems.woodenBucketEmpty && is.getItem() != TFCItems.clayBucketEmpty);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		boolean isEmpty = this.bucketContents == Blocks.air;
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, isEmpty);
		Random random = new Random();
		if (mop == null)
		{
			return is;
		}
		else
		{
			Item[] woodenBuckets = new Item[] { TFCItems.woodenBucketEmpty, TFCItems.woodenBucketWater,
					TFCItems.woodenBucketSaltWater, TFCItems.woodenBucketMilk };
			Item[] clayBuckets = new Item[] { TFCItems.clayBucketEmpty, TFCItems.clayBucketWater,
					TFCItems.clayBucketSaltWater, TFCItems.clayBucketMilk };
			Item[] buckets = is != null && (is.getItem().getContainerItem() == TFCItems.woodenBucketEmpty || is
					.getItem() == TFCItems.woodenBucketEmpty) ? woodenBuckets : clayBuckets;
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;

				if (!world.canMineBlock(player, x, y, z))
					return is;

				if (isEmpty)
				{
					if (!player.canPlayerEdit(x, y, z, mop.sideHit, is))
						return is;

					FillBucketEvent event = new FillBucketEvent(player, is, world, mop);
					if (MinecraftForge.EVENT_BUS.post(event) || event.isCanceled())
						return is;

					if (event.getResult() == Event.Result.ALLOW)
						return event.result;

					if (TFC_Core.isFreshWater(world.getBlock(x, y, z)))
					{
						world.setBlockToAir(x, y, z);
						if (player.capabilities.isCreativeMode)
							return is;
						if (buckets == clayBuckets && random.nextInt(40) == 0)
						{
							world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
									player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
							return new ItemStack(buckets[0], 0, 0);
						}
						return new ItemStack(buckets[1]);
					}
					else if (TFC_Core.isSaltWater(world.getBlock(x, y, z)))
					{
						world.setBlockToAir(x, y, z);
						if (player.capabilities.isCreativeMode)
							return is;
						if (buckets == clayBuckets && random.nextInt(40) == 0)
						{
							world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
									player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
							return new ItemStack(buckets[0], 0, 0);
						}
						return new ItemStack(buckets[2]);
					}

					// Handle flowing water
					int flowX = x;
					int flowY = y;
					int flowZ = z;
					switch (mop.sideHit)
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

					if (TFC_Core.isFreshWater(world.getBlock(flowX, flowY, flowZ)))
					{
						world.setBlockToAir(flowX, flowY, flowZ);
						if (player.capabilities.isCreativeMode)
							return is;
						if (buckets == clayBuckets && random.nextInt(40) == 0)
						{
							world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
									player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
							return new ItemStack(buckets[0], 0, 0);
						}
						return new ItemStack(buckets[1]);
					}
					else if (TFC_Core.isSaltWater(world.getBlock(flowX, flowY, flowZ)))
					{
						world.setBlockToAir(flowX, flowY, flowZ);
						if (player.capabilities.isCreativeMode)
							return is;
						if (buckets == clayBuckets && random.nextInt(40) == 0)
						{
							world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
									player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
							return new ItemStack(buckets[0], 0, 0);
						}
						return new ItemStack(buckets[2]);
					}
				}
				else
				{
					if (buckets == clayBuckets && random.nextInt(40) == 0)
					{
						world.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f,
								player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
						return new ItemStack(buckets[0], 0, 0);
					}
					return new ItemStack(buckets[0]);
				}
			}
			else if (this.bucketContents == Blocks.air && mop.entityHit instanceof EntityCowTFC && ((EntityCowTFC) mop.entityHit)
					.getGender() == GenderEnum.FEMALE)
			{
				return new ItemStack(buckets[3]);
			}
			return is;
		}
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
			float hitY, float hitZ)
	{

		boolean isEmpty = this.bucketContents == Blocks.air;
		int[][] map = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
		Random random = new Random();
		Item[] woodenBuckets = new Item[] { TFCItems.woodenBucketEmpty, TFCItems.woodenBucketWater,
				TFCItems.woodenBucketSaltWater, TFCItems.woodenBucketMilk };
		Item[] clayBuckets = new Item[] { TFCItems.clayBucketEmpty, TFCItems.clayBucketWater,
				TFCItems.clayBucketSaltWater, TFCItems.clayBucketMilk };
		Item[] buckets = is != null && (is.getItem().getContainerItem() == TFCItems.woodenBucketEmpty || is
				.getItem() == TFCItems.woodenBucketEmpty) ? woodenBuckets : clayBuckets;

		if (!isEmpty && world.getBlock(x, y, z) != Blocks.cauldron && world.isAirBlock(x + map[side][0],
				y + map[side][1], z + map[side][2]))
		{
			world.setBlock(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.freshWater, 2, 0x1);
			// Now we check for making mud
			List list = world.getEntitiesWithinAABB(EntityItem.class,
					AxisAlignedBB.getBoundingBox(x + map[side][0], y + map[side][1], z + map[side][2],
							x + 1 + map[side][0], y + 1 + map[side][1], z + 1 + map[side][2]));
			
			boolean[] straws = new boolean[list.size()];
			boolean[] dirts = new boolean[list.size()];
			boolean[] kills = new boolean[list.size()];
			boolean hasDirt, hasStraw;
			hasDirt = hasStraw = false;
			int counter = 0;
			int mudCreated = 0;

			if (!world.isRemote)
			{
				// Find where all the dirt and straw in the stacks are
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem) iterator.next();
					ItemStack eIs = entity.getEntityItem();
					if (eIs != null)
					{
						if (eIs.getItem() == Item.getItemFromBlock(TFCBlocks.dirt) || eIs.getItem() == Item
								.getItemFromBlock(TFCBlocks.dirt2))
						{
							dirts[counter] = true;
							hasDirt = true;
						}
						else if (eIs.getItem() == TFCItems.straw)
						{
							straws[counter] = true;
							hasStraw = true;
						}
					}
					counter++;
				}
				if (hasDirt && hasStraw)
				{
					for (counter = 0; counter < list.size(); counter++)
					{
						if (dirts[counter])
						{
							EntityItem entity = (EntityItem) list.get(counter);
							ItemStack dirt = entity.getEntityItem();
							for (int c2 = 0; c2 < list.size(); c2++)
							{
								if (straws[c2])
								{
									EntityItem entity2 = (EntityItem) list.get(c2);
									ItemStack straw = entity2.getEntityItem();
									if (straw.stackSize > 0 && dirt.stackSize > 0)
									{
										if (straw.stackSize > dirt.stackSize)
										{
											int numCreated = Math.min(64 - mudCreated, dirt.stackSize);
											dirt.stackSize -= numCreated;
											straw.stackSize -= numCreated;
											if (dirt.stackSize == 0)
											{
												kills[counter] = true;
												dirts[counter] = false;
											}
											int extraDam = TFCBlocks.dirt2 == Block.getBlockFromItem(dirt.getItem())
													? 16 : 0;
											ItemStack mud = new ItemStack(TFCItems.mud,numCreated * 2, extraDam + dirt.getItemDamage());
											world.spawnEntityInWorld(new EntityItem(world,x,y,z,mud));
											mudCreated += numCreated;
										}
										else if (dirt.stackSize > straw.stackSize)
										{
											int numCreated = Math.min(64 - mudCreated, straw.stackSize);
											dirt.stackSize -= numCreated;
											straw.stackSize -= numCreated;
											if (straw.stackSize == 0)
											{
												kills[c2] = true;
												straws[c2] = false;
											}
											int extraDam = TFCBlocks.dirt2 == Block.getBlockFromItem(dirt.getItem())
													? 16 : 0;
											ItemStack mud = new ItemStack(TFCItems.mud,numCreated * 2, extraDam + dirt.getItemDamage());
											world.spawnEntityInWorld(new EntityItem(world,x,y,z,mud));
											mudCreated += numCreated;
										}
										else if (dirt.stackSize == straw.stackSize)
										{
											int numCreated = Math.min(64 - mudCreated, straw.stackSize);
											dirt.stackSize -= numCreated;
											straw.stackSize -= numCreated;
											if (straw.stackSize == 0)
											{
												kills[c2] = true;
												straws[c2] = false;
											}
											if (dirt.stackSize == 0)
											{
												kills[counter] = true;
												dirts[counter] = false;
											}
											int extraDam = TFCBlocks.dirt2 == Block.getBlockFromItem(dirt.getItem())
													? 16 : 0;
											ItemStack mud = new ItemStack(TFCItems.mud,numCreated * 2, extraDam + dirt.getItemDamage());
											world.spawnEntityInWorld(new EntityItem(world,x,y,z,mud));
											mudCreated += numCreated;
										}
									}
								}
							}

						}
					}
					counter = 0;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem) iterator.next();
						if (kills[counter])
						{
							entity.setDead();
						}
						counter++;
					}
				}
			}
			if (buckets == clayBuckets && random.nextInt(40) == 0)
			{
				player.setCurrentItemOrArmor(0, new ItemStack(buckets[0], 0, 0));
				return true;
			}
			player.setCurrentItemOrArmor(0, new ItemStack(buckets[0]));
			return true;
		}

		if (!isEmpty && world.getBlock(x, y, z) == Blocks.cauldron)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (meta < 3)
			{
				world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(3, 0, 3), 2);
				world.func_147453_f(x, y, z, Blocks.cauldron);
				if (buckets == clayBuckets && random.nextInt(40) == 0)
				{
					player.setCurrentItemOrArmor(0, new ItemStack(buckets[0], 0, 0));
					return true;
				}
				if (!player.capabilities.isCreativeMode)
				{
					player.setCurrentItemOrArmor(0, new ItemStack(buckets[0]));
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
