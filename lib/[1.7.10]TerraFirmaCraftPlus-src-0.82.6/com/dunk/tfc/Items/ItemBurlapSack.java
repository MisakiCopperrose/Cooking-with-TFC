package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Items.Pottery.ItemPotterySmallVessel;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IBag;
import com.dunk.tfc.api.Interfaces.ISewable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemBurlapSack extends ItemPotterySmallVessel implements IBag, ISewable
{
	boolean[][] clothingAlpha;
	ResourceLocation res;

	public ItemBurlapSack()
	{
		super();
		this.hasSubtypes = false;
		this.metaNames = new String[] { "Burlap Sack", "Burlap Sack", "Burlap Sack" };
		this.setMaxStackSize(1);
		this.setFolder("");
		setCreativeTab(TFCTabs.TFC_MISC);
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public ItemStack[] loadBagInventory(ItemStack is)
	{
		ItemStack[] bag = new ItemStack[4];
		if (is != null && is.hasTagCompound() && is.getTagCompound().hasKey("Items"))
		{
			NBTTagList nbttaglist = is.getTagCompound().getTagList("Items", 10);
			for (int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < 4)
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
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + "burlapSack");

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

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!entityplayer.isSneaking() && itemstack.stackSize == 1)
		{
			if (itemstack.getItemDamage() == 0)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 50, entityplayer.worldObj, (int) entityplayer.posX,
						(int) entityplayer.posY, (int) entityplayer.posZ);
			}
		}
		return itemstack;
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
		return this.res;
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
