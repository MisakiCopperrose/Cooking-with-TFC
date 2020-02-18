package com.dunk.tfc.Handlers.Client;

import com.dunk.tfc.Containers.ContainerVessel;
import com.dunk.tfc.Containers.ContainerBag;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Entities.Mobs.EntityHorseTFC;
import com.dunk.tfc.GUI.GuiAnvil;
import com.dunk.tfc.GUI.GuiBag;
import com.dunk.tfc.GUI.GuiBarrel;
import com.dunk.tfc.GUI.GuiBasket;
import com.dunk.tfc.GUI.GuiBeehive;
import com.dunk.tfc.GUI.GuiBlastFurnace;
import com.dunk.tfc.GUI.GuiBlueprint;
import com.dunk.tfc.GUI.GuiCalendar;
import com.dunk.tfc.GUI.GuiCarpentry;
import com.dunk.tfc.GUI.GuiChestTFC;
import com.dunk.tfc.GUI.GuiCrucible;
import com.dunk.tfc.GUI.GuiCustomNametag;
import com.dunk.tfc.GUI.GuiFirepit;
import com.dunk.tfc.GUI.GuiFoodPrep;
import com.dunk.tfc.GUI.GuiForge;
import com.dunk.tfc.GUI.GuiGrill;
import com.dunk.tfc.GUI.GuiHopper;
import com.dunk.tfc.GUI.GuiInventoryTFC;
import com.dunk.tfc.GUI.GuiKnapping;
import com.dunk.tfc.GUI.GuiLargeVessel;
import com.dunk.tfc.GUI.GuiLogPile;
import com.dunk.tfc.GUI.GuiMold;
import com.dunk.tfc.GUI.GuiNestBox;
import com.dunk.tfc.GUI.GuiPlanSelection;
import com.dunk.tfc.GUI.GuiQuern;
import com.dunk.tfc.GUI.GuiQuiver;
import com.dunk.tfc.GUI.GuiScreenHorseInventoryTFC;
import com.dunk.tfc.GUI.GuiSewing;
import com.dunk.tfc.GUI.GuiSluice;
import com.dunk.tfc.GUI.GuiVessel;
import com.dunk.tfc.GUI.GuiVesselLiquid;
import com.dunk.tfc.GUI.GuiWorkbench;
import com.dunk.tfc.TileEntities.TEAnvil;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.TileEntities.TEBasket;
import com.dunk.tfc.TileEntities.TEBeehive;
import com.dunk.tfc.TileEntities.TEBlastFurnace;
import com.dunk.tfc.TileEntities.TEChest;
import com.dunk.tfc.TileEntities.TECrucible;
import com.dunk.tfc.TileEntities.TEFirepit;
import com.dunk.tfc.TileEntities.TEFoodPrep;
import com.dunk.tfc.TileEntities.TEForge;
import com.dunk.tfc.TileEntities.TEGrill;
import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.TileEntities.TENestBox;
import com.dunk.tfc.TileEntities.TEQuern;
import com.dunk.tfc.TileEntities.TESluice;
import com.dunk.tfc.TileEntities.TEVessel;
import com.dunk.tfc.TileEntities.TEWorkbench;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;

public class GuiHandler extends com.dunk.tfc.Handlers.GuiHandler
{
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(id)
		{
		case 0:
			return new GuiLogPile(player.inventory, (TELogPile) te, world, x, y, z);
		case 1:
			return new GuiWorkbench(player.inventory, (TEWorkbench) te, world, x, y, z);
		case 19:
			return new GuiVesselLiquid(player.inventory, world, x, y, z);
		case 20:
			return new GuiFirepit(player.inventory, (TEFirepit) te, world, x, y, z);
		case 21:
			return new GuiAnvil(player.inventory, (TEAnvil) te, world, x, y, z);
		case 22:
			return null;//was scribing table
		case 23:
			return new GuiForge(player.inventory, (TEForge) te, world, x, y, z);
		case 24:
			return new GuiPlanSelection(player, (TEAnvil) te, world, x, y, z);//was metallurgy table
		case 25:
			return new GuiSluice(player.inventory, (TESluice) te, world, x, y, z);
		case 26:
			return new GuiBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		case 27:
			return new GuiCalendar(player);
		case 28:
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			return new GuiKnapping(player.inventory, pi.specialCraftingTypeAlternate == null ? pi.specialCraftingType : null, world, x, y, z);
		case 29:
			return new GuiChestTFC(player.inventory, ((TEChest) te), world, x, y, z);
		case 31:
			return new GuiInventoryTFC(player);
		case 32:
			//return new GuiFoodPrep(player.inventory, ((TEFoodPrep) te), world, x, y, z, 0);
		case 33:
			return new GuiQuern(player.inventory, ((TEQuern) te), world, x, y, z);
		case 34:
			return new GuiBlueprint(player, world, x, y, z);
		case 35:
			return new GuiBarrel(player.inventory,((TEBarrel)te),world,x,y,z, 0);
		case 36:
			return new GuiBarrel(player.inventory,((TEBarrel)te),world,x,y,z, 1);
		case 37:
			return new GuiCrucible(player.inventory,((TECrucible)te), world, x, y, z);
		case 38:
			return new GuiMold(player.inventory, world, x, y, z);
		case 39:
			return new GuiVessel(player.inventory, world, x, y, z,ContainerVessel.Types.CERAMIC);
		case 40:
			return new GuiQuiver(player.inventory, world, x, y, z);
		case 41:
			return new GuiNestBox(player.inventory, ((TENestBox)te), world, x, y, z);
		case 42:
		{
			if (player.ridingEntity instanceof EntityHorseTFC)
			{
				EntityHorseTFC horse = (EntityHorseTFC) player.ridingEntity;
				horse.updateChestSaddle();
				return new GuiScreenHorseInventoryTFC(player.inventory, horse.getHorseChest(), horse);
			}

			return null;
		}
		case 43:
			return new GuiGrill(player.inventory, ((TEGrill)te), world, x, y, z);
		case 44:
			return new GuiFoodPrep(player.inventory, ((TEFoodPrep) te), world, x, y, z, 0);
		case 45:
			return new GuiFoodPrep(player.inventory, ((TEFoodPrep) te), world, x, y, z, 1);
		case 46:
			return new GuiLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 0);
		case 47:
			return new GuiLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 1);
		case 48:
			return new GuiCustomNametag(player, world, x, y, z);
		case 49:
		{
			return new GuiHopper(player.inventory, ((TEHopper) te), world, x, y, z);
		}
		case 50:
			return new GuiVessel(player.inventory, world, x, y, z, ContainerVessel.Types.BURLAP);
		case 51:
			return new GuiSewing(player.inventory,world, x,y,z);
		case 52:
			return new GuiBasket(player.inventory, ((TEBasket) te), world, x, y, z, 1);
		case 53:
			return new GuiBag(player.inventory, world, x, y, z, ContainerBag.Types.HIDE);
		case 54:
			return new GuiCarpentry(player.inventory,world, x,y,z);
		case 55:
			return new GuiBeehive(player.inventory,((TEBeehive) te),world, x,y,z);
		default:
			return null;
		}
	}

	@SubscribeEvent
	public void openGuiHandler(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiInventory && !(event.gui instanceof GuiInventoryTFC))
			event.gui = new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer);
	}
}
