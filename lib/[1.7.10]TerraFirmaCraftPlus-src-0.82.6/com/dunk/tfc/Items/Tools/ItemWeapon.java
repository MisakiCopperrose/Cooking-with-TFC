package com.dunk.tfc.Items.Tools;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IAttackSpeed;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Interfaces.ISize;
import com.dunk.tfc.api.Util.Helper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemWeapon extends ItemSword implements ISize, ICausesDamage, IAttackSpeed
{
	private float weaponBaseDamage;
	private final ToolMaterial toolMat;
	private int attackSpeed;
	public EnumDamageType damageType = EnumDamageType.SLASHING;

	public ItemWeapon(ToolMaterial par2, float damage)
	{
		super(par2);
		this.setMaxDamage(par2.getMaxUses());
		weaponBaseDamage = damage;
		this.toolMat = par2;
		setCreativeTab(TFCTabs.TFC_WEAPONS);
		setNoRepair();
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack is)
	{
		if (entity instanceof EntityPlayer && PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity)) != null &&  PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity)).canAttack())
		{
			if(!entity.worldObj.isRemote)
			{
				MovingObjectPosition mop = getMovingObjectPositionFromPlayer(entity.worldObj, (EntityPlayer)entity, true);
				if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
				{
					return false;
				}
				PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity)).setAttackTimer(getAttackSpeed(entity), this);
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (!player.worldObj.isRemote)
		{
			PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)player)).setAttackTimer(getAttackSpeed((EntityLivingBase)player), this);
			if (PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player) != null &&  PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).canAttack())
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Item setAttackSpeed(int i)
	{
		this.attackSpeed = i;
		return this;
	}

	@Override
	public int getAttackSpeed(EntityLivingBase entity)
	{
		return attackSpeed;
	}

	@Override
	public float func_150931_i()
	{
		return this.toolMat.getDamageVsEntity();
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
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		// Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;

		ItemTerra.addSizeInformation(is, arraylist);
		
		if(is.getItem() instanceof IAttackSpeed && ((IAttackSpeed)is.getItem()).getAttackSpeed(player) > 0)
		{
			EnumChatFormatting color = TFC_Core.getChatFormatAttackSpeed(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			String speed = TFC_Core.getAttackSpeedString(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			String modifier = TFC_Core.getAttackSpeedModifierString(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			arraylist.add(color + TFC_Core.translate(speed) + TFC_Core.translate(modifier));
		}
		ItemTerra.addHeatInformation(is, arraylist);
		if (is.getItem() instanceof ICausesDamage)
			arraylist.add(EnumChatFormatting.AQUA + TFC_Core
					.translate(((ICausesDamage) this).getDamageType(player).toString()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
		arraylist.add(TFC_Core.translate("gui." + this.getReach(is).getName()));
		addExtraInformation(is, player, arraylist);
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer
				.registerIcon(Reference.MOD_ID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public int getItemStackLimit()
	{
		if (canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = Helper.getMouseOverObject(player, player.worldObj);

		if (mop != null && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == TFCBlocks.toolRack)
			return is;

		player.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public EnumDamageType getDamageType(EntityLivingBase entity)
	{
		return damageType;
	}

	public double getWeaponDamage(ItemStack is)
	{
		return Math.floor(weaponBaseDamage + (weaponBaseDamage * AnvilManager.getDamageBuff(is)));
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", getWeaponDamage(stack), 0));
		return multimap;
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return (int) Math.floor(getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(is)));
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
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.MEDIUM;
	}

	
}
