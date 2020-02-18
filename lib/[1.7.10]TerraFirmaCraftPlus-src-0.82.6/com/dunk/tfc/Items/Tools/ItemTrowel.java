package com.dunk.tfc.Items.Tools;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.TileEntities.TEPlasteredBlock;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemTrowel extends ItemTerra
{
	protected int plasterDamage = 25;
	protected List<Block> plasterableBlocks;

	public ItemTrowel()
	{
		super();
		maxStackSize = 1;
		setCreativeTab(TFCTabs.TFC_TOOLS);
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
		plasterableBlocks = new ArrayList<Block>();
		plasterableBlocks.add(TFCBlocks.stoneIgExBrick);
		plasterableBlocks.add(TFCBlocks.stoneIgExLargeBrick);
		plasterableBlocks.add(TFCBlocks.stoneIgExSmooth);
		plasterableBlocks.add(TFCBlocks.stoneIgInBrick);
		plasterableBlocks.add(TFCBlocks.stoneIgInLargeBrick);
		plasterableBlocks.add(TFCBlocks.stoneIgInSmooth);
		plasterableBlocks.add(TFCBlocks.stoneSedBrick);
		plasterableBlocks.add(TFCBlocks.stoneSedLargeBrick);
		plasterableBlocks.add(TFCBlocks.stoneSedSmooth);
		plasterableBlocks.add(TFCBlocks.stoneMMBrick);
		plasterableBlocks.add(TFCBlocks.stoneMMLargeBrick);
		plasterableBlocks.add(TFCBlocks.stoneMMSmooth);
		plasterableBlocks.add(TFCBlocks.planks);
		plasterableBlocks.add(TFCBlocks.planks2);
		plasterableBlocks.add(TFCBlocks.mudBricks);
		plasterableBlocks.add(TFCBlocks.mudBricks2);
		plasterableBlocks.add(TFCBlocks.wattleDaub);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer
				.registerIcon(Reference.MOD_ID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (pass == 1 && nbt != null && nbt.hasKey("broken"))
			return TFC_Textures.brokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && plasterableBlocks.contains(world.getBlock(x, y, z)))
		{
			Block toPlaster = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			int plaster = -1;
			boolean wooden = false;
			for (int i = 0; i < 9; i++)
			{
				if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem()
						.equals(TFCItems.woodenBucketPlaster) && player.inventory.mainInventory[i]
								.getItemDamage() < player.inventory.mainInventory[i].getMaxDamage())
				{
					plaster = i;
					wooden = true;
				}
				else if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem()
						.equals(TFCItems.clayBucketPlaster) && player.inventory.mainInventory[i]
								.getItemDamage() < player.inventory.mainInventory[i].getMaxDamage())
				{
					plaster = i;
					wooden = false;
				}
			}

			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem()
					.getItem() instanceof ItemTrowel && plaster != -1 && player.inventory.mainInventory[plaster]
							.getItemDamage() < player.inventory.mainInventory[plaster].getMaxDamage())
			{
				player.inventory.mainInventory[plaster].damageItem(plasterDamage, player);
				player.inventory.setInventorySlotContents(plaster, player.inventory.mainInventory[plaster]);
				if (player.inventory.mainInventory[plaster]
						.getItemDamage() > player.inventory.mainInventory[plaster].getMaxDamage() - plasterDamage)
				{
					player.inventory.mainInventory[plaster] = new ItemStack(
							wooden ? TFCItems.woodenBucketEmpty : TFCItems.clayBucketEmpty, 1);
				}
				if (!world.isRemote)
				{
					player.getCurrentEquippedItem().damageItem(1, player);
					boolean b = world.setBlock(x, y, z, TFCBlocks.plasteredBlock, world.getBlockMetadata(x, y, z), 2);

					TEPlasteredBlock te = (TEPlasteredBlock) world.getTileEntity(x, y, z);
					te.block = toPlaster;
					te.meta = meta;
					te.markDirty();

					return b;
				}
				return true;
			}
		}
		
		/*
		 * Block block = world.getBlock(x, y, z); if (!world.isRemote) { //
		 * Negated the old condition and exiting the method here instead. if
		 * (block == TFCBlocks.toolRack) return true;
		 * 
		 * // Getting the meta data only when we actually need it. int meta =
		 * world.getBlockMetadata(x, y, z);
		 * 
		 * SkillRank rank =
		 * TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_PROSPECTING)
		 * ;
		 * 
		 * // If an ore block is targeted directly, it'll tell you what it is.
		 * if ((block == TFCBlocks.ore || block == TFCBlocks.ore2 || block ==
		 * TFCBlocks.ore3) && world.getTileEntity(x, y, z) instanceof TEOre) {
		 * TEOre te = (TEOre) world.getTileEntity(x, y, z); if (block ==
		 * TFCBlocks.ore && rank == SkillRank.Master) meta = ((BlockOre)
		 * block).getOreGrade(te, meta); if (block == TFCBlocks.ore2) meta =
		 * meta + Global.ORE_METAL.length; if (block == TFCBlocks.ore3) meta =
		 * meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length;
		 * tellResult(player, new ItemStack(TFCItems.oreChunk, 1, meta)); return
		 * true; } else if (!TFC_Core.isGround(block)) // Exclude ground blocks
		 * to help with performance { Iterator iter =
		 * WorldGenOre.oreList.values().iterator(); while (iter.hasNext()) {
		 * OreSpawnData osd = (OreSpawnData) iter.next(); if (osd != null &&
		 * block == osd.block) { tellResult(player, new ItemStack(block));
		 * return true; } } }
		 * 
		 * random = new Random(x * z + y); int chance = 60 +
		 * ((rank.ordinal()+1)*10);
		 * 
		 * results.clear(); // If random(100) is less than 60, it used to
		 * succeed. we don't need to // gather the blocks in a 25x25 area if it
		 * doesn't. if (random.nextInt(100) >= chance && rank !=
		 * SkillRank.Master) { tellNothingFound(player); return true; }
		 * 
		 * results.clear();
		 * 
		 * // Check all blocks in the 25x25 area, centered on the targeted
		 * block. for (int i = -12; i < 12; i++) { for (int j = -12; j < 12;
		 * j++) { for(int k = -12; k < 12; k++) { int blockX = x + i; int blockY
		 * = y + j; int blockZ = z + k;
		 * 
		 * block = world.getBlock(blockX, blockY, blockZ); meta =
		 * world.getBlockMetadata(blockX, blockY, blockZ); ItemStack ore = null;
		 * if (block == TFCBlocks.ore && world.getTileEntity(blockX, blockY,
		 * blockZ) instanceof TEOre) { TEOre te = (TEOre)
		 * world.getTileEntity(blockX, blockY, blockZ); if (rank ==
		 * SkillRank.Master) ore = new ItemStack(TFCItems.oreChunk, 1,
		 * ((BlockOre) block).getOreGrade(te, meta)); else ore = new
		 * ItemStack(TFCItems.oreChunk, 1, meta); } else if (block ==
		 * TFCBlocks.ore2) ore = new ItemStack(TFCItems.oreChunk, 1, meta +
		 * Global.ORE_METAL.length); else if (block == TFCBlocks.ore3) ore = new
		 * ItemStack(TFCItems.oreChunk, 1, meta + Global.ORE_METAL.length +
		 * Global.ORE_MINERAL.length); else if (!TFC_Core.isGround(block)) //
		 * Exclude ground blocks to help with performance { Iterator iter =
		 * WorldGenOre.oreList.values().iterator(); while (iter.hasNext()) {
		 * OreSpawnData osd = (OreSpawnData) iter.next(); if (osd != null &&
		 * block == osd.block) { ore = new ItemStack(block); break; } } } else
		 * continue;
		 * 
		 * if (ore != null) { String oreName = ore.getDisplayName();
		 * 
		 * if (results.containsKey(oreName)) results.get(oreName).count++; else
		 * results.put(oreName, new ProspectResult(ore, 1));
		 * 
		 * ore = null; oreName = null; } } } }
		 * 
		 * // Tell the player what was found. if (results.isEmpty()) {
		 * tellNothingFound(player); } else { tellResult(player); }
		 * 
		 * results.clear(); random = null;
		 * 
		 * // Damage the item on prospecting use. itemStack.damageItem(1,
		 * player); if (itemStack.getItemDamage() >= itemStack.getMaxDamage())
		 * player.destroyCurrentEquippedItem(); }
		 * 
		 * return true;
		 */
		return false;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int) (getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(stack)));
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		float digSpeed = super.getDigSpeed(stack, block, meta);

		if (ForgeHooks.isToolEffective(stack, block, meta))
		{
			return digSpeed + (digSpeed * AnvilManager.getDurabilityBuff(stack));
		}
		return digSpeed;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(TFC_Core.translate("gui." + this.getReach(is).getName()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
	}
}
