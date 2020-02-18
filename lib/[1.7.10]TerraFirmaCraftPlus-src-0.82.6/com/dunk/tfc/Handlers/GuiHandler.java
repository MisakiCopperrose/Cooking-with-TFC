package com.dunk.tfc.Handlers;

import com.dunk.tfc.Containers.ContainerAnvil;
import com.dunk.tfc.Containers.ContainerBag;
import com.dunk.tfc.Containers.ContainerBarrel;
import com.dunk.tfc.Containers.ContainerBasket;
import com.dunk.tfc.Containers.ContainerBeehive;
import com.dunk.tfc.Containers.ContainerBlastFurnace;
import com.dunk.tfc.Containers.ContainerCarpentry;
import com.dunk.tfc.Containers.ContainerChestTFC;
import com.dunk.tfc.Containers.ContainerCrucible;
import com.dunk.tfc.Containers.ContainerFirepit;
import com.dunk.tfc.Containers.ContainerFoodPrep;
import com.dunk.tfc.Containers.ContainerForge;
import com.dunk.tfc.Containers.ContainerGrill;
import com.dunk.tfc.Containers.ContainerHopper;
import com.dunk.tfc.Containers.ContainerHorseInventoryTFC;
import com.dunk.tfc.Containers.ContainerLargeVessel;
import com.dunk.tfc.Containers.ContainerLiquidVessel;
import com.dunk.tfc.Containers.ContainerLogPile;
import com.dunk.tfc.Containers.ContainerMold;
import com.dunk.tfc.Containers.ContainerNestBox;
import com.dunk.tfc.Containers.ContainerPlanSelection;
import com.dunk.tfc.Containers.ContainerPlayerTFC;
import com.dunk.tfc.Containers.ContainerQuern;
import com.dunk.tfc.Containers.ContainerQuiver;
import com.dunk.tfc.Containers.ContainerSewing;
import com.dunk.tfc.Containers.ContainerSluice;
import com.dunk.tfc.Containers.ContainerSpecialCrafting;
import com.dunk.tfc.Containers.ContainerVessel;
import com.dunk.tfc.Containers.ContainerWorkbench;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Entities.Mobs.EntityHorseTFC;
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

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);

		switch(id)
		{
		case 0:
		{
			return new ContainerLogPile(player.inventory, (TELogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new ContainerWorkbench(player.inventory, (TEWorkbench) te, world, x, y, z);
		}
		case 19:
		{
			return new ContainerLiquidVessel(player.inventory, world, x, y, z);
		}
		case 20:
		{
			return new ContainerFirepit(player.inventory, (TEFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new ContainerAnvil(player.inventory, (TEAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return null;//was scribing table
		}
		case 23:
		{
			return new ContainerForge(player.inventory, (TEForge) te, world, x, y, z);
		}
		case 24:
		{
			return new ContainerPlanSelection(player, (TEAnvil) te, world, x, y, z);//was metallurgy table
		}
		case 25:
		{
			return new ContainerSluice(player.inventory, (TESluice) te, world, x, y, z);
		}
		case 26:
		{
			return new ContainerBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		}
		case 28:
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			return new ContainerSpecialCrafting(player.inventory, pi.specialCraftingTypeAlternate == null ? pi.specialCraftingType : null, world, x, y, z);
		}
		case 29:
		{
			return new ContainerChestTFC(player.inventory, (TEChest) te, world, x, y, z);
		}
		case 31:
		{
			return new ContainerPlayerTFC(player.inventory, false, player);
		}
		case 33:
		{
			return new ContainerQuern(player.inventory, (TEQuern) te, world, x, y, z);
		}
		case 34:
		{
			return null;
		}
		case 35:
		{
			return new ContainerBarrel(player.inventory, ((TEBarrel) te), world, x, y, z, 0);
		}
		case 36:
		{
			return new ContainerBarrel(player.inventory, ((TEBarrel) te), world, x, y, z, 1);
		}
		case 37:
		{
			return new ContainerCrucible(player.inventory, ((TECrucible) te), world, x, y, z);
		}
		case 38:
		{
			return new ContainerMold(player.inventory, world, x, y, z);
		}
		case 39:
		{
			return new ContainerVessel(player.inventory, world, x, y, z,ContainerVessel.Types.CERAMIC);
		}
		case 40:
		{
			return new ContainerQuiver(player.inventory, world, x, y, z);
		}
		case 41:
		{
			return new ContainerNestBox(player.inventory, ((TENestBox) te), world, x, y, z);
		}
		case 42:
		{
			if (player.ridingEntity instanceof EntityHorseTFC)
			{
				EntityHorseTFC horse = (EntityHorseTFC) player.ridingEntity;
				return new ContainerHorseInventoryTFC(player.inventory, horse.getHorseChest(), horse);
			}

			return null;
		}
		case 43:
		{
			return new ContainerGrill(player.inventory, ((TEGrill) te), world, x, y, z);
		}
		case 44:
			return new ContainerFoodPrep(player.inventory, (TEFoodPrep) te, world, x, y, z, 0);
		case 45:
			return new ContainerFoodPrep(player.inventory, (TEFoodPrep) te, world, x, y, z, 1);
		case 46:
			return new ContainerLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 0);
		case 47:
			return new ContainerLargeVessel(player.inventory, ((TEVessel) te), world, x, y, z, 1);
		case 48:
		{
			return null;//guinametag
		}
		case 49:
		{
			return new ContainerHopper(player.inventory, ((TEHopper) te));
		}
		case 50:
		{
			return new ContainerVessel(player.inventory, world, x, y, z, ContainerVessel.Types.BURLAP);
		}
		case 51:
		{
			return new ContainerSewing(player.inventory,world,x,y,z);
		}
		case 52:
			return new ContainerBasket(player.inventory, ((TEBasket) te), world, x, y, z, 1);
		case 53:
			return new ContainerBag(player.inventory, world, x, y, z, ContainerBag.Types.HIDE);
		case 54:
		{
			return new ContainerCarpentry(player.inventory,world,x,y,z);
		}
		case 55:
			return new ContainerBeehive(player.inventory, ((TEBeehive) te), world, x, y, z);
		default:
		{
			return null;
		}
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
