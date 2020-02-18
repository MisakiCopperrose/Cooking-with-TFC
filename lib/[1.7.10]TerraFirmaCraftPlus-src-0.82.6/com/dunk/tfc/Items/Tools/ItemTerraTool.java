package com.dunk.tfc.Items.Tools;

import java.util.List;
import java.util.Set;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IAttackSpeed;
import com.dunk.tfc.api.Interfaces.ICausesDamage;
import com.dunk.tfc.api.Interfaces.ISize;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.common.ForgeHooks;

public class ItemTerraTool extends ItemTool implements ISize, IAttackSpeed
{
	//private static boolean registeredGlobalTex = false;
	private int attackSpeed;
	
	public ItemTerraTool(float par2, ToolMaterial par3, Set<Block> par4)
	{
		super(par2, par3, par4);
		this.setCreativeTab(TFCTabs.TFC_TOOLS);
		setNoRepair();
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;

		ItemTerra.addSizeInformation(is, arraylist);
		
		if(is.getItem() instanceof IAttackSpeed && ((IAttackSpeed)is.getItem()).getAttackSpeed(player) > 0)
		{
			EnumChatFormatting color = TFC_Core.getChatFormatAttackSpeed(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			String speed = TFC_Core.getAttackSpeedString(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			String modifier = TFC_Core.getAttackSpeedModifierString(((IAttackSpeed)is.getItem()).getAttackSpeed(player));
			arraylist.add(color + TFC_Core.translate(speed) + TFC_Core.translate(modifier));
		}
		if(is.getItem() instanceof ICausesDamage)
			arraylist.add(EnumChatFormatting.AQUA + TFC_Core.translate(((ICausesDamage)this).getDamageType(player).toString()));
		ItemTerra.addHeatInformation(is, arraylist);
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
		arraylist.add(TFC_Core.translate("gui."+this.getReach(is).getName()));
		addExtraInformation(is, player, arraylist);
	}

	public static void addSmithingBonusInformation(ItemStack is, List<String> arraylist)
	{
		if (AnvilManager.getDurabilityBuff(is) > 0)
			arraylist.add(TFC_Core.translate("gui.SmithingBonus") + " : +" + Helper.roundNumber(AnvilManager.getDurabilityBuff(is) * 100, 10) + "%");
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
		if (TFC_Textures.brokenItem == null) TFC_Textures.brokenItem = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/Broken Item");
		if (TFC_Textures.wip == null) TFC_Textures.wip = registerer.registerIcon(Reference.MOD_ID + ":" + "wip");
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
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
	public int getMaxDamage(ItemStack stack)
	{
		return (int) (getMaxDamage()+(getMaxDamage() * AnvilManager.getDurabilityBuff(stack)));
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
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack is)
	{
		if (entity instanceof EntityPlayer && PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity)) != null &&  PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity)).canAttack())
		{
			if(!entity.worldObj.isRemote)
			{
				MovingObjectPosition mop = getMovingObjectPositionFromPlayer(entity.worldObj, (EntityPlayer)entity, false);
				if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
				{
					return false;
				}
				PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(((EntityPlayer)entity)).setAttackTimer(getAttackSpeed((EntityLivingBase)entity), this);
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (!player.worldObj.isRemote && entity instanceof EntityLivingBase)
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
	public boolean requiresMultipleRenderPasses()
	{
		return true;
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
