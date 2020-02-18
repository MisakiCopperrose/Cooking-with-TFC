package com.dunk.tfc.Items.Pottery;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TileEntities.TEFireEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemPotteryBlowpipe extends ItemPotteryMold
{
	protected IIcon glassIcon;

	public ItemPotteryBlowpipe()
	{
		super();
		this.stackable = false;
		this.setMaxDamage(101);
	}

	@Override
	public boolean isDamageable()
	{
		return this.getMaxDamage() > 0;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		// return false;
		if (itemstack != null && itemstack.getItemDamage() == 0)
		{
			return super.onItemUse(itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ);
		}
		if (!world.isRemote)
		{
			if (entityplayer.isSneaking() && itemstack.getItemDamage() == 1)
			{
				Block b2 = world.getBlock(x, y, z);
				/*
				 * int dist = 0; int dir =
				 * MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F
				 * + 0.5D) & 3; // side 0 means bottom if (side == 0) --y; // 1
				 * means top else if (side == 1) ++y; // 2 means front else if
				 * (side == 2) --z; // 3 is back else if (side == 3) ++z; // 4
				 * is left else if (side == 4) --x; // 5 is right else if (side
				 * == 5) ++x;
				 */

				if (world.getTileEntity(x, y, z) instanceof TEFireEntity)
				{
					TEFireEntity te = (TEFireEntity) world.getTileEntity(x, y, z);
					if (te.canBlowWithPipe())
					{
						te.receiveAirFromPipe();
						world.playSoundEffect(entityplayer.posX, entityplayer.posY, entityplayer.posZ,
								TFC_Sounds.BELLOWS, 0.4F, 1);
						return true;
					}
				}

			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		
		if (itemstack.getItemDamage() == 2 && !world.isRemote)
		{
			float f3 = 0.05F;
			EntityItem entityitem;
			Random rand = new Random();
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 2.0F + 0.4F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			entityitem = new EntityItem(world, entityplayer.posX + f, entityplayer.posY + f1, entityplayer.posZ + f2,
					new ItemStack(TFCItems.glassBottle, 1));
			entityitem.motionX = (float) rand.nextGaussian() * f3;
			entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) rand.nextGaussian() * f3;
			world.spawnEntityInWorld(entityitem);
			itemstack.setItemDamage(1);
			world.playSoundEffect(entityplayer.posX + f, entityplayer.posY + f1, entityplayer.posZ + f2,
					TFC_Sounds.BELLOWS, 0.4F, 1);
		}
		return itemstack;
	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (is.getItemDamage() > 1)
		{
			int units = 100 - (is.getItemDamage() - 2);
			arraylist.add(TFC_Core.translate("gui.units") + ": " + units + " / 100");
		}
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getItemDamage() > 2;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.clayIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[0]);
		this.ceramicIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[1]);
		if (metaNames.length > 2)
		{
			glassIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[2]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if (par1ItemStack != null && par1ItemStack.getItemDamage() > 2)
		{
			int damage = par1ItemStack.getItemDamage();
			return super.getUnlocalizedName(par1ItemStack) + "." + metaNames[2];
		}
		return super.getUnlocalizedName(par1ItemStack);
	}

	@Override
	public IIcon getIconFromDamage(int damage)
	{
		if (damage == 0)
			return this.clayIcon;
		else if (damage == 1)
			return this.ceramicIcon;
		else if (damage >= 2)
			return this.glassIcon;

		return this.clayIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
}
