package com.dunk.tfc.Items.Tools;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IAttackSpeed;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Interfaces.ISize;
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
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.common.ForgeHooks;

public class ItemCustomAxe extends ItemAxe implements ISize, ICausesDamage, IAttackSpeed
{
	private float toolDamage;
	private int attackSpeed;
	
	public ItemCustomAxe(ToolMaterial e, float damage)
	{
		super(e);
		this.setMaxDamage(e.getMaxUses());
		this.toolDamage = damage;
		setCreativeTab(TFCTabs.TFC_TOOLS);
		setNoRepair();
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		name = name.replace("IgIn ", "");
		name = name.replace("IgEx ", "");
		name = name.replace("Sed ", "");
		name = name.replace("MM ", "");
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/" + name);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(pass == 1 && nbt != null && nbt.hasKey("broken"))
			return TFC_Textures.brokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
		if(is.getItem() instanceof IAttackSpeed && ((IAttackSpeed)is.getItem()).getAttackSpeed(player) > 0)
		{
			EnumChatFormatting color = TFC_Core.getChatFormatAttackSpeed(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			String speed = TFC_Core.getAttackSpeedString(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			String modifier = TFC_Core.getAttackSpeedModifierString(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			arraylist.add(color + TFC_Core.translate(speed) + TFC_Core.translate(modifier));
		}
		arraylist.add(EnumChatFormatting.AQUA + TFC_Core.translate(getDamageType(player).toString()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
		arraylist.add(TFC_Core.translate("gui."+this.getReach(is).getName()));
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
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
	public EnumDamageType getDamageType(EntityLivingBase is)
	{
		return EnumDamageType.SLASHING;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", getWeaponDamage(is), 0));
		return multimap;
	}

	public double getWeaponDamage(ItemStack is)
	{
		return Math.floor(toolDamage + (toolDamage * AnvilManager.getDamageBuff(is)));
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
}
