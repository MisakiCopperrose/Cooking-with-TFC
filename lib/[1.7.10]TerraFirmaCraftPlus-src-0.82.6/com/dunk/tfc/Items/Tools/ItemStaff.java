package com.dunk.tfc.Items.Tools;

import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.Render.Item.KnifeItemRenderer;
import com.dunk.tfc.Render.Item.PoleItemRenderer;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Interfaces.ICausesDamage;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemStaff extends ItemWeapon
{
	
	public ItemStaff(ToolMaterial par2EnumToolMaterial, float damage)
	{
		super(par2EnumToolMaterial, damage);
		this.damageType = EnumDamageType.CRUSHING;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		super.registerIcons(registerer);
		MinecraftForgeClient.registerItemRenderer(this, new PoleItemRenderer());
	}
	
	@Override
	public boolean onLeftClickEntity( ItemStack is, EntityPlayer player, Entity entity)
	{
		return super.onLeftClickEntity(is, player, entity);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack is)
	{
		return false;
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;

		ItemTerra.addSizeInformation(is, arraylist);
		ItemTerra.addHeatInformation(is, arraylist);

		if(is.getItem() instanceof ICausesDamage)
			arraylist.add(EnumChatFormatting.AQUA + TFC_Core.translate(((ICausesDamage) this).getDamageType(player).toString()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
		arraylist.add(TFC_Core.translate("gui."+this.getReach(is).getName()));
		arraylist.add(TFC_Core.translate("gui.default_walkable") + ":  " + (10)+"%");
		addExtraInformation(is, player, arraylist);
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}
}
