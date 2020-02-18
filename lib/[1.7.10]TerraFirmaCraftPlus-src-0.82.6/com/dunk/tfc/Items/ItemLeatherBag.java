package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Items.Pottery.ItemPotterySmallVessel;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IBag;
import com.dunk.tfc.api.Interfaces.ISewable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemLeatherBag extends ItemPotterySmallVessel implements IBag, ISewable
{
	boolean[][] clothingAlpha;
	ResourceLocation res;
	public ItemLeatherBag(String[] metas)
	{
		super();
		this.hasSubtypes = false;
		this.metaNames = metas;
		this.setMaxStackSize(1);
		this.setFolder("");
		setCreativeTab(TFCTabs.TFC_MISC);
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public ItemStack[] loadBagInventory(ItemStack is)
	{
		ItemStack[] bag = new ItemStack[6];
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("Items"))
		{
			NBTTagList nbttaglist = is.getTagCompound().getTagList("Items", 10);
			for (int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < 6)
				{
					ItemStack is2 = ItemStack.loadItemStackFromNBT(nbttagcompound1);
					if(nbttagcompound1.hasKey("Stack"))
					{
						is2.stackSize = nbttagcompound1.getInteger("Stack");
					}
					bag[byte0] = is2;
				}
			}
		}
		else
			return null;

		return bag;
	}
	
	@Override
	public int getItemStackLimit(ItemStack is)
	{
		return hasItems(is) ? 1 : 4;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + (this == TFCItems.hideBag?"Hide Bag":"Leather Bag"));
		res = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_ITEM + this.textureFolder +"armor/clothing/"+ this
				.getUnlocalizedName().replace("item.", "Flat ") + ".png");
		clothingAlpha = TFC_Textures.loadClothingPattern(res);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return itemIcon;
	}

	protected boolean hasItems(ItemStack is)
	{
		if (is != null && is.hasTagCompound() && is.stackTagCompound.hasKey("Items") && is.stackTagCompound
				.getTagList("Items", 10).tagCount() > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return hasItems(is) ? EnumSize.LARGE : size;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return hasItems(is) ? EnumWeight.HEAVY : weight;
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		// If the bag is hide, we make it rot
		if (!world.isRemote && is != null && is.stackTagCompound != null && is.stackTagCompound
						.hasKey("lastUpdate") && is.stackTagCompound.getInteger("lastUpdate") < TFC_Time.getTotalDays() && is.stackSize > 0)
		{
			if (hasItems(is))
			{
				is.setItemDamage(is.getItemDamage() + 1);
				if (is.getItemDamage() < is.getMaxDamage())
				{
					is.stackTagCompound.setInteger("lastUpdate", TFC_Time.getTotalDays());
				}
				else
				{
					ItemStack[] items = loadBagInventory(is);
					for (ItemStack item : items)
					{
						if (item != null)
						{
							EntityItem ei = new EntityItem(world, x, y, z, item);
							world.spawnEntityInWorld(ei);
						}
					}
					is.stackSize = 0;
				}
			}
			else
			{
				is.stackTagCompound.setInteger("lastUpdate", TFC_Time.getTotalDays());
			}
		}
		else if (!world.isRemote && is != null && is.stackTagCompound != null && !(is.stackTagCompound
						.hasKey("lastUpdate")) && hasItems(is))
		{
			is.stackTagCompound.setInteger("lastUpdate", TFC_Time.getTotalDays());
		}
		TFC_ItemHeat.handleItemHeat(is);
		ItemStack[] bag = loadBagInventory(is);
		if(bag != null)
		{
			TFC_Core.handleItemTicking(bag, world, x, y, z, 1f, false);
		}
		return onUpdateNotTemperature(is,world,x,y,z);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!entityplayer.isSneaking() && itemstack.stackSize == 1)
		{
			// if (itemstack.getItemDamage() == 0)
			// {
			entityplayer.openGui(TerraFirmaCraft.instance, 53, entityplayer.worldObj, (int) entityplayer.posX,
					(int) entityplayer.posY, (int) entityplayer.posZ);
			// }
		}
		return itemstack;
	}

	@Override
	public void saveContents(ItemStack vessel, ItemStack[] bag)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < 6; i++)
		{
			if (bag[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				bag[i].writeToNBT(nbttagcompound1);
				nbttagcompound1.setInteger("Stack", bag[i].stackSize);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		if (vessel != null)
		{
			if (!vessel.hasTagCompound())
				vessel.setTagCompound(new NBTTagCompound());
			vessel.getTagCompound().setTag("Items", nbttaglist);
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && entityplayer.isSneaking())
		{
			return false;
		}
		return false;
	}

	@Override
	public ResourceLocation getFlatTexture()
	{
		// TODO Auto-generated method stub
		return res;
	}

	@Override
	public boolean[][] getClothingAlpha()
	{
		// TODO Auto-generated method stub
		return this.clothingAlpha;
	}
	
	@Override
	public Item setRepairCost(int i)
	{
		return this;
	}

	@Override
	public int getRepairCost()
	{
		return 1;
	}
}
