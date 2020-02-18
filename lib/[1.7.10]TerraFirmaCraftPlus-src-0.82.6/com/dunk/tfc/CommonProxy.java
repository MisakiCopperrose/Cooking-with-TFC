package com.dunk.tfc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.dunk.tfc.Entities.EntityBarrel;
import com.dunk.tfc.Entities.EntityCustomMinecart;
import com.dunk.tfc.Entities.EntityFallingBlockTFC;
import com.dunk.tfc.Entities.EntityJavelin;
import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.Entities.EntityStand;
import com.dunk.tfc.Entities.Mobs.EntityBear;
import com.dunk.tfc.Entities.Mobs.EntityBlazeTFC;
import com.dunk.tfc.Entities.Mobs.EntityCaveSpiderTFC;
import com.dunk.tfc.Entities.Mobs.EntityChickenTFC;
import com.dunk.tfc.Entities.Mobs.EntityCorpse;
import com.dunk.tfc.Entities.Mobs.EntityCowTFC;
import com.dunk.tfc.Entities.Mobs.EntityCreeperTFC;
import com.dunk.tfc.Entities.Mobs.EntityDeer;
import com.dunk.tfc.Entities.Mobs.EntityEndermanTFC;
import com.dunk.tfc.Entities.Mobs.EntityFishTFC;
import com.dunk.tfc.Entities.Mobs.EntityGhastTFC;
import com.dunk.tfc.Entities.Mobs.EntityHorseTFC;
import com.dunk.tfc.Entities.Mobs.EntityIronGolemTFC;
import com.dunk.tfc.Entities.Mobs.EntityPheasantTFC;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;
import com.dunk.tfc.Entities.Mobs.EntityPigZombieTFC;
import com.dunk.tfc.Entities.Mobs.EntitySheepTFC;
import com.dunk.tfc.Entities.Mobs.EntitySilverfishTFC;
import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Entities.Mobs.EntitySlimeTFC;
import com.dunk.tfc.Entities.Mobs.EntitySpiderTFC;
import com.dunk.tfc.Entities.Mobs.EntitySquidTFC;
import com.dunk.tfc.Entities.Mobs.EntityWolfTFC;
import com.dunk.tfc.Entities.Mobs.EntityZombieTFC;
import com.dunk.tfc.Handlers.GuiHandler;
import com.dunk.tfc.Handlers.PlayerExtraInfoMessage;
import com.dunk.tfc.Handlers.ServerTickHandler;
import com.dunk.tfc.TileEntities.TEAnvil;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.TileEntities.TEBasket;
import com.dunk.tfc.TileEntities.TEBeehive;
import com.dunk.tfc.TileEntities.TEBellows;
import com.dunk.tfc.TileEntities.TEBerryBush;
import com.dunk.tfc.TileEntities.TEBlastFurnace;
import com.dunk.tfc.TileEntities.TEBloom;
import com.dunk.tfc.TileEntities.TEBloomery;
import com.dunk.tfc.TileEntities.TEChest;
import com.dunk.tfc.TileEntities.TEChimney;
import com.dunk.tfc.TileEntities.TECrop;
import com.dunk.tfc.TileEntities.TECrucible;
import com.dunk.tfc.TileEntities.TEDetailed;
import com.dunk.tfc.TileEntities.TEDryingBricks;
import com.dunk.tfc.TileEntities.TEFarmland;
import com.dunk.tfc.TileEntities.TEFenceGate;
import com.dunk.tfc.TileEntities.TEFirepit;
import com.dunk.tfc.TileEntities.TEFoodPrep;
import com.dunk.tfc.TileEntities.TEForge;
import com.dunk.tfc.TileEntities.TEFruitLeaves;
import com.dunk.tfc.TileEntities.TEFruitTreeWood;
import com.dunk.tfc.TileEntities.TEGrill;
import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.TileEntities.TEIngotPile;
import com.dunk.tfc.TileEntities.TELeatherRack;
import com.dunk.tfc.TileEntities.TELightEmitter;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.TileEntities.TELoom;
import com.dunk.tfc.TileEntities.TEMetalSheet;
import com.dunk.tfc.TileEntities.TEMetalTrapDoor;
import com.dunk.tfc.TileEntities.TENestBox;
import com.dunk.tfc.TileEntities.TEOilLamp;
import com.dunk.tfc.TileEntities.TEOre;
import com.dunk.tfc.TileEntities.TEPartial;
import com.dunk.tfc.TileEntities.TEPlasteredBlock;
import com.dunk.tfc.TileEntities.TEPottery;
import com.dunk.tfc.TileEntities.TEQuern;
import com.dunk.tfc.TileEntities.TESapling;
import com.dunk.tfc.TileEntities.TESluice;
import com.dunk.tfc.TileEntities.TESmokeRack;
import com.dunk.tfc.TileEntities.TESpawnMeter;
import com.dunk.tfc.TileEntities.TEStand;
import com.dunk.tfc.TileEntities.TEToolRack;
import com.dunk.tfc.TileEntities.TEVessel;
import com.dunk.tfc.TileEntities.TEWaterPlant;
import com.dunk.tfc.TileEntities.TEWoodConstruct;
import com.dunk.tfc.TileEntities.TEWorkbench;
import com.dunk.tfc.TileEntities.TEWorldItem;
import com.dunk.tfc.Tools.ChiselMode_Detailed;
import com.dunk.tfc.Tools.ChiselMode_Slab;
import com.dunk.tfc.Tools.ChiselMode_Smooth;
import com.dunk.tfc.Tools.ChiselMode_Stair;
import com.dunk.tfc.WorldGen.TFCProvider;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Tools.ChiselManager;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class CommonProxy
{
	
	private static Map<UUID,ItemStack> playerExtraItem = new HashMap<UUID,ItemStack>();
	public void registerFluidIcons()
	{

	}

	public void registerRenderInformation()
	{
		// NOOP on server
	}

	public void registerBiomeEventHandler()
	{
		// NOOP on server
	}

	public void registerPlayerRenderEventHandler()
	{
		// NOOP on server
	}

	public void setupGuiIngameForge()
	{
		// NOOP on server
	}

	public String getCurrentLanguage()
	{
		return null;
	}

	public void registerTileEntities(boolean b)
	{
		GameRegistry.registerTileEntity(TELogPile.class, "TerraLogPile");
		GameRegistry.registerTileEntity(TEWorkbench.class, "TerraWorkbench");
		GameRegistry.registerTileEntity(TEForge.class, "TerraForge");
		GameRegistry.registerTileEntity(TEBlastFurnace.class, "TerraBloomery");
		GameRegistry.registerTileEntity(TEBloomery.class, "TerraEarlyBloomery");
		GameRegistry.registerTileEntity(TESluice.class, "TerraSluice");
		GameRegistry.registerTileEntity(TEFarmland.class, "TileEntityFarmland");
		GameRegistry.registerTileEntity(TECrop.class, "TileEntityCrop");
		GameRegistry.registerTileEntity(TEPlasteredBlock.class, "TileEntityPlasteredBlock");
		GameRegistry.registerTileEntity(TEFruitTreeWood.class, "FruitTreeWood");
		GameRegistry.registerTileEntity(TEPartial.class, "Partial");
		GameRegistry.registerTileEntity(TEDetailed.class, "Detailed");
		GameRegistry.registerTileEntity(TESpawnMeter.class, "SpawnMeter");
		GameRegistry.registerTileEntity(TESapling.class, "Sapling");
		GameRegistry.registerTileEntity(TEWoodConstruct.class, "WoodConstruct");
		GameRegistry.registerTileEntity(TEBarrel.class, "Barrel");
		GameRegistry.registerTileEntity(TEBeehive.class, "Beehive");
		GameRegistry.registerTileEntity(TEBasket.class, "Basket");
		GameRegistry.registerTileEntity(TEFenceGate.class, "FenceGate");
		GameRegistry.registerTileEntity(TEBloom.class, "IronBloom");
		GameRegistry.registerTileEntity(TECrucible.class, "Crucible");
		GameRegistry.registerTileEntity(TENestBox.class, "Nest Box");
		GameRegistry.registerTileEntity(TEStand.class, "Armour Stand");
		GameRegistry.registerTileEntity(TEBerryBush.class, "Berry Bush");
		GameRegistry.registerTileEntity(TEFruitLeaves.class, "Fruit Leaves");
		GameRegistry.registerTileEntity(TEMetalSheet.class, "Metal Sheet");
		GameRegistry.registerTileEntity(TEOre.class, "ore");
		GameRegistry.registerTileEntity(TELeatherRack.class, "leatherRack");
		GameRegistry.registerTileEntity(TEMetalTrapDoor.class, "MetalTrapDoor");
		GameRegistry.registerTileEntity(TEWaterPlant.class, "Sea Weed");
		GameRegistry.registerTileEntity(TEVessel.class, "Vessel");
		GameRegistry.registerTileEntity(TELightEmitter.class, "LightEmitter");
		GameRegistry.registerTileEntity(TESmokeRack.class, "Smoke Rack");
		GameRegistry.registerTileEntity(TEOilLamp.class, "Oil Lamp");
		GameRegistry.registerTileEntity(TEDryingBricks.class, "Drying Bricks");
		GameRegistry.registerTileEntity(TEChimney.class, "Chimney");
		
		

		if(b)
		{
			GameRegistry.registerTileEntity(TEFirepit.class, "TerraFirepit");
			GameRegistry.registerTileEntity(TEIngotPile.class, "ingotPile");

			GameRegistry.registerTileEntity(TEPottery.class, "Pottery");
			GameRegistry.registerTileEntity(TEChest.class, "chest");
			GameRegistry.registerTileEntity(TEFoodPrep.class, "FoodPrep");
			GameRegistry.registerTileEntity(TEBellows.class, "Bellows");
			GameRegistry.registerTileEntity(TEToolRack.class, "ToolRack");
			GameRegistry.registerTileEntity(TEAnvil.class, "TerraAnvil");
			GameRegistry.registerTileEntity(TEWorldItem.class, "worldItem");
			GameRegistry.registerTileEntity(TEQuern.class, "Quern");
			GameRegistry.registerTileEntity(TELoom.class, "Loom");
			GameRegistry.registerTileEntity(TEGrill.class, "grill");
			GameRegistry.registerTileEntity(TEHopper.class, "HopperTFC");
		}

		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "squidTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x3c5466, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityFishTFC.class, "fishTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x535231, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "cowTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x3d2f23, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityWolfTFC.class, "wolfTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x938f8c, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "bearTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x5c4b3b, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "chickenTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xf3f45e, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "pigTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xe78786, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "deerTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x7c624c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntitySkeletonTFC.class, "skeletonTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x979797, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityZombieTFC.class, "zombieTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x426a33, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntitySpiderTFC.class, "spiderTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x322b24, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntitySlimeTFC.class, "slimeTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x6eb35c, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntitySilverfishTFC.class, "silverfishTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x858887, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityGhastTFC.class, "ghastTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xebebeb, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityCaveSpiderTFC.class, "caveSpiderTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x123236, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityBlazeTFC.class, "blazeTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xad6d0b, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityEndermanTFC.class, "endermanTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x0d0d0d, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityPigZombieTFC.class, "pigZombieTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xb6735f, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityIronGolemTFC.class, "irongolemTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xbfb99a, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityCreeperTFC.class, "creeperTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x66c55c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "sheepTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xcdbfb4, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityPheasantTFC.class, "pheasantTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x822c1c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntityHorseTFC.class, "horseTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x966936, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecartTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityProjectileTFC.class, "arrowTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityStand.class, "standTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityCorpse.class, "corpse", EntityRegistry.findGlobalUniqueEntityId(), 0x66c55c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntityFallingBlockTFC.class, "fallingBlock", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityBarrel.class, "barrel", EntityRegistry.findGlobalUniqueEntityId());

		EntityRegistry.registerModEntity(EntityJavelin.class, "javelin", 1, TerraFirmaCraft.instance, 64, 5, true);
		EntityRegistry.registerModEntity(EntitySquidTFC.class, "squidTFC", 2, TerraFirmaCraft.instance, 64, 5, true);
		EntityRegistry.registerModEntity(EntityCowTFC.class, "cowTFC", 6, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityWolfTFC.class, "wolfTFC", 7, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBear.class, "bearTFC", 8, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityChickenTFC.class, "chickenTFC", 9, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigTFC.class, "pigTFC", 10, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityDeer.class, "deerTFC", 11, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCustomMinecart.class, "minecartTFC", 12, TerraFirmaCraft.instance, 80, 5, true);
		EntityRegistry.registerModEntity(EntitySkeletonTFC.class, "skeletonTFC", 13, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityZombieTFC.class, "zombieTFC", 14, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySpiderTFC.class, "spiderTFC", 15, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySlimeTFC.class, "slimeTFC", 16, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySilverfishTFC.class, "silverFishTFC", 17, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityGhastTFC.class, "ghastTFC", 18, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCaveSpiderTFC.class, "caveSpiderTFC", 19, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBlazeTFC.class, "blazeTFC", 20, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityEndermanTFC.class, "endermanTFC", 21, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigZombieTFC.class, "pigZombieTFC", 22, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityIronGolemTFC.class, "irongolemTFC", 23, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCreeperTFC.class, "creeperTFC", 24, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityStand.class, "standTFC", 25, TerraFirmaCraft.instance, 64, 20, false);
		EntityRegistry.registerModEntity(EntityPheasantTFC.class, "PheasantTFC", 26, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityFishTFC.class, "fishTFC", 27, TerraFirmaCraft.instance, 64, 5, true);
		EntityRegistry.registerModEntity(EntityFallingBlockTFC.class, "fallingBlock", 28, TerraFirmaCraft.instance, 160, 20, true);
		EntityRegistry.registerModEntity(EntityBarrel.class, "barrel", 29, TerraFirmaCraft.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityCorpse.class, "corpse", 30, TerraFirmaCraft.instance, 160, 5, true);
		
		/*Function<EntitySpawnMessage, Entity> spawnFunction = new Function<EntitySpawnMessage, Entity>()
				{
			@Override
			public Entity apply(EntitySpawnMessage input) {
				return null;
			}};


			EntityRegistry.instance().lookupModSpawn(EntityFallingBlockTFC.class, false).setCustomSpawning(spawnFunction, false);
		 */
		//EntityRegistry.registerModEntity(EntityArrowTFC.class, "arrowTFC", 27, TerraFirmaCraft.instance, 160, 5, true);
	}
	
	public void registerMessages()
	{
		TerraFirmaCraft.netWrapper.registerMessage(PlayerExtraInfoMessage.Handler.class, PlayerExtraInfoMessage.class, 0, Side.SERVER);
	}

	public void registerFluids()
	{
		FluidRegistry.registerFluid(TFCFluids.LAVA);
		FluidRegistry.registerFluid(TFCFluids.SALTWATER);
		FluidRegistry.registerFluid(TFCFluids.FRESHWATER);
		FluidRegistry.registerFluid(TFCFluids.HOTWATER);
		FluidRegistry.registerFluid(TFCFluids.RUM);
		FluidRegistry.registerFluid(TFCFluids.BEER);
		FluidRegistry.registerFluid(TFCFluids.RYEBEER);
		FluidRegistry.registerFluid(TFCFluids.CORNBEER);
		FluidRegistry.registerFluid(TFCFluids.RICEBEER);
		FluidRegistry.registerFluid(TFCFluids.WHEATBEER);
		FluidRegistry.registerFluid(TFCFluids.BARLEYWINE);
		FluidRegistry.registerFluid(TFCFluids.RYEWINE);
		FluidRegistry.registerFluid(TFCFluids.CANEWINE);
		FluidRegistry.registerFluid(TFCFluids.AGAVEWINE);
		FluidRegistry.registerFluid(TFCFluids.POTATOWINE);
		FluidRegistry.registerFluid(TFCFluids.CORNWINE);
		FluidRegistry.registerFluid(TFCFluids.WHEATWINE);
		FluidRegistry.registerFluid(TFCFluids.BARLEYWHISKEY);
		FluidRegistry.registerFluid(TFCFluids.RICEWHISKEY);
		FluidRegistry.registerFluid(TFCFluids.RYEWHISKEY);
		FluidRegistry.registerFluid(TFCFluids.WHISKEY);
		FluidRegistry.registerFluid(TFCFluids.CORNWHISKEY);
		FluidRegistry.registerFluid(TFCFluids.SAKE);
		FluidRegistry.registerFluid(TFCFluids.SHOCHU);
		FluidRegistry.registerFluid(TFCFluids.CIDER);
		FluidRegistry.registerFluid(TFCFluids.APPLEJACK);
		FluidRegistry.registerFluid(TFCFluids.VODKA);
		FluidRegistry.registerFluid(TFCFluids.TEQUILA);
		FluidRegistry.registerFluid(TFCFluids.BERRYWINE);
		FluidRegistry.registerFluid(TFCFluids.FRUITWINE);
		FluidRegistry.registerFluid(TFCFluids.BRANDY);
		FluidRegistry.registerFluid(TFCFluids.BERRYBRANDY);
		FluidRegistry.registerFluid(TFCFluids.FRUITBRANDY);
		FluidRegistry.registerFluid(TFCFluids.WINE);
		FluidRegistry.registerFluid(TFCFluids.GRAPEJUICE);
		FluidRegistry.registerFluid(TFCFluids.CANEJUICE);
		FluidRegistry.registerFluid(TFCFluids.MILKCURDLED);
		FluidRegistry.registerFluid(TFCFluids.MILKVINEGAR);
		FluidRegistry.registerFluid(TFCFluids.OLIVEOIL);
		FluidRegistry.registerFluid(TFCFluids.TANNIN);
		FluidRegistry.registerFluid(TFCFluids.VINEGAR);
		FluidRegistry.registerFluid(TFCFluids.BRINE);
		FluidRegistry.registerFluid(TFCFluids.LIMEWATER);
		FluidRegistry.registerFluid(TFCFluids.MILK);
		FluidRegistry.registerFluid(TFCFluids.HONEY);
		FluidRegistry.registerFluid(TFCFluids.MEAD);
		FluidRegistry.registerFluid(TFCFluids.HONEYWATER);
		FluidRegistry.registerFluid(TFCFluids.HONEYBRANDY);

		FluidRegistry.registerFluid(TFCFluids.REDDYE);
		FluidRegistry.registerFluid(TFCFluids.BLUEDYE);
		FluidRegistry.registerFluid(TFCFluids.GREENDYE);
		FluidRegistry.registerFluid(TFCFluids.YELLOWDYE);
		FluidRegistry.registerFluid(TFCFluids.ORANGEDYE);
		FluidRegistry.registerFluid(TFCFluids.PURPLEDYE);
		FluidRegistry.registerFluid(TFCFluids.WHITEDYE);
		FluidRegistry.registerFluid(TFCFluids.BLACKDYE);
	}

	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.LAVA.getName()), new ItemStack(TFCItems.blueSteelBucketLava), new ItemStack(TFCItems.blueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.redSteelBucketWater), new ItemStack(TFCItems.redSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.redSteelBucketSaltWater), new ItemStack(TFCItems.redSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.woodenBucketWater), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.woodenBucketSaltWater), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.clayBucketWater), new ItemStack(TFCItems.clayBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.clayBucketSaltWater), new ItemStack(TFCItems.clayBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.FRESHWATER, 1000), new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(TFCItems.potteryJug,1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.FRESHWATER, 500), new ItemStack(TFCItems.waterBottle), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RUM, 250), new ItemStack(TFCItems.rum), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BEER, 250), new ItemStack(TFCItems.beer), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RYEBEER, 250), new ItemStack(TFCItems.ryeBeer), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CORNBEER, 250), new ItemStack(TFCItems.cornBeer), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RICEBEER, 250), new ItemStack(TFCItems.riceBeer), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.WHEATBEER, 250), new ItemStack(TFCItems.wheatBeer), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BARLEYWINE, 250), new ItemStack(TFCItems.barleyWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RYEWINE, 250), new ItemStack(TFCItems.ryeWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CANEWINE, 250), new ItemStack(TFCItems.caneWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.AGAVEWINE, 250), new ItemStack(TFCItems.agaveWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.POTATOWINE, 250), new ItemStack(TFCItems.potatoWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CORNWINE, 250), new ItemStack(TFCItems.cornWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.WHEATWINE, 250), new ItemStack(TFCItems.wheatWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BARLEYWHISKEY, 250), new ItemStack(TFCItems.barleyWhiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RICEWHISKEY, 250), new ItemStack(TFCItems.riceWhiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RYEWHISKEY, 250), new ItemStack(TFCItems.ryeWhiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.WHISKEY, 250), new ItemStack(TFCItems.whiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CORNWHISKEY, 250), new ItemStack(TFCItems.cornWhiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.SAKE, 250), new ItemStack(TFCItems.sake), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.SHOCHU, 250), new ItemStack(TFCItems.shochu), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CIDER, 250), new ItemStack(TFCItems.cider), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.APPLEJACK, 250), new ItemStack(TFCItems.appleJack), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VODKA, 250), new ItemStack(TFCItems.vodka), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.TEQUILA, 250), new ItemStack(TFCItems.tequila), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BERRYWINE, 250), new ItemStack(TFCItems.berryWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.FRUITWINE, 250), new ItemStack(TFCItems.fruitWine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BRANDY, 250), new ItemStack(TFCItems.brandy), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BERRYBRANDY, 250), new ItemStack(TFCItems.berryBrandy), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.FRUITBRANDY, 250), new ItemStack(TFCItems.fruitBrandy), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.WINE, 250), new ItemStack(TFCItems.wine), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.GRAPEJUICE, 250), new ItemStack(TFCItems.grapeJuice), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.MILK, 1000), new ItemStack(TFCItems.woodenBucketMilk), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VINEGAR, 1000), new ItemStack(TFCItems.woodenBucketVinegar), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.MILK, 1000), new ItemStack(TFCItems.clayBucketMilk), new ItemStack(TFCItems.clayBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VINEGAR, 1000), new ItemStack(TFCItems.clayBucketVinegar), new ItemStack(TFCItems.clayBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.HONEY, 250), new ItemStack(TFCItems.honeybowl), new ItemStack(TFCItems.potteryBowl,1,1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.HONEY, 1000), new ItemStack(TFCItems.clayBucketHoney), new ItemStack(TFCItems.clayBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.HONEY, 1000), new ItemStack(TFCItems.woodenBucketHoney), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.MEAD, 250), new ItemStack(TFCItems.mead), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.HONEYBRANDY, 250), new ItemStack(TFCItems.honeyBrandy), new ItemStack(TFCItems.glassBottle));
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(0), new ItemStack(TFCBlocks.OilLamp, 1, 0));//Gold
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(1), new ItemStack(TFCBlocks.OilLamp, 1, 1));//Platinum
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(2), new ItemStack(TFCBlocks.OilLamp, 1, 2));//RoseGold
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(3), new ItemStack(TFCBlocks.OilLamp, 1, 3));//Silver
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(4), new ItemStack(TFCBlocks.OilLamp, 1, 4));//Sterling Silver
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(5), new ItemStack(TFCBlocks.OilLamp, 1, 5));//BlueSteel
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.LAVA, 250), ItemOilLamp.GetFullLamp(5), new ItemStack(TFCBlocks.OilLamp, 1, 5));//BlueSteel
	}

	public void registerToolClasses()
	{
		//pickaxes
		TFCItems.bismuthBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.bismuthBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.blackBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.blackSteelPick.setHarvestLevel("pickaxe", 5);
		TFCItems.blueSteelPick.setHarvestLevel("pickaxe", 6);
		TFCItems.bronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.copperPick.setHarvestLevel("pickaxe", 1);
		TFCItems.wroughtIronPick.setHarvestLevel("pickaxe", 3);
		TFCItems.redSteelPick.setHarvestLevel("pickaxe", 6);
		TFCItems.steelPick.setHarvestLevel("pickaxe", 4);
		//shovels
		TFCItems.igInShovel.setHarvestLevel("shovel", 1);
		TFCItems.igExShovel.setHarvestLevel("shovel", 1);
		TFCItems.sedShovel.setHarvestLevel("shovel", 1);
		TFCItems.mMShovel.setHarvestLevel("shovel", 1);
		TFCItems.bismuthBronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.blackBronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.blackSteelShovel.setHarvestLevel("shovel", 5);
		TFCItems.blueSteelShovel.setHarvestLevel("shovel", 6);
		TFCItems.bronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.copperShovel.setHarvestLevel("shovel", 1);
		TFCItems.wroughtIronShovel.setHarvestLevel("shovel", 3);
		TFCItems.redSteelShovel.setHarvestLevel("shovel", 6);
		TFCItems.steelShovel.setHarvestLevel("shovel", 4);
		//Axes
		TFCItems.igInAxe.setHarvestLevel("axe", 1);
		TFCItems.igExAxe.setHarvestLevel("axe", 1);
		TFCItems.sedAxe.setHarvestLevel("axe", 1);
		TFCItems.mMAxe.setHarvestLevel("axe", 1);
		TFCItems.bismuthBronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.blackBronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.blackSteelAxe.setHarvestLevel("axe", 5);
		TFCItems.blueSteelAxe.setHarvestLevel("axe", 6);
		TFCItems.bronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.copperAxe.setHarvestLevel("axe", 1);
		TFCItems.wroughtIronAxe.setHarvestLevel("axe", 3);
		TFCItems.redSteelAxe.setHarvestLevel("axe", 6);
		TFCItems.steelAxe.setHarvestLevel("axe", 4);

		TFCItems.bismuthBronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.blackBronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.blackSteelSaw.setHarvestLevel("axe", 5);
		TFCItems.blueSteelSaw.setHarvestLevel("axe", 6);
		TFCItems.bronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.copperSaw.setHarvestLevel("axe", 1);
		TFCItems.wroughtIronSaw.setHarvestLevel("axe", 3);
		TFCItems.redSteelSaw.setHarvestLevel("axe", 6);
		TFCItems.steelSaw.setHarvestLevel("axe", 4);

		TFCItems.stoneHammer.setHarvestLevel("hammer", 1);
		TFCItems.bismuthBronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.blackBronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.blackSteelHammer.setHarvestLevel("hammer", 5);
		TFCItems.blueSteelHammer.setHarvestLevel("hammer", 6);
		TFCItems.bronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.copperHammer.setHarvestLevel("hammer", 1);
		TFCItems.wroughtIronHammer.setHarvestLevel("hammer", 3);
		TFCItems.redSteelHammer.setHarvestLevel("hammer", 6);
		TFCItems.steelHammer.setHarvestLevel("hammer", 4);
	}
	
	@SubscribeEvent
    public void onPlayerLogin(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		TerraFirmaCraft.netWrapper.sendTo(new PlayerExtraInfoMessage(event.player.getPersistentID(), playerExtraItem.get(event.player.getPersistentID()), false), (EntityPlayerMP) event.player);
	}
	
	@SubscribeEvent
	public void onPlayerLogout(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event)
	{
		removeItemFromPlayer(event.player.getPersistentID());
	}

	public void onClientLogin()
	{
	}

	public void registerSkyProvider(TFCProvider p)
	{
	}

	public boolean isRemote()
	{
		return false;
	}

	public void removeItemFromPlayer(UUID player)
	{
		playerExtraItem.remove(player);
	}
	
	public void updateExtraItemFromPlayer(UUID player, ItemStack item)
	{
		playerExtraItem.put(player,item);
	}
	
	public ItemStack getExtraItemFromPlayer(UUID player)
	{
		return playerExtraItem.get(player);
	}
	
	public World getCurrentWorld()
	{
		return MinecraftServer.getServer().getEntityWorld();
	}

	public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
	{
		FMLCommonHandler.instance().firePlayerCraftingEvent(entityplayer, itemstack, iinventory);
	}

	public int getArmorRenderID(String name)
	{
		return 0;
	}

	public boolean getGraphicsLevel()
	{
		return false;
	}

	public void registerKeys()
	{
	}

	public void registerKeyBindingHandler()
	{
	}

	public void uploadKeyBindingsToGame()
	{
	}

	public void registerHandlers()
	{
	}

	public void registerSoundHandler()
	{
	}

	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
	}

	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.instance, new GuiHandler());
	}

	public void registerWailaClasses()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.dunk.tfc.WAILA.WAILAData.callbackRegister"); // Blocks
		FMLInterModComms.sendMessage("Waila", "register", "com.dunk.tfc.WAILA.WMobs.callbackRegister"); // Entities
		FMLInterModComms.sendMessage("Waila", "register", "com.dunk.tfc.WAILA.WCrucible.callbackRegister"); // Crucible has its own file due to extra calculations.
	}

	public void registerChiselModes()
	{
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Smooth("Smooth"));
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Stair("Stairs"));
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Slab("Slabs"));
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Detailed("Detailed"));
	}

	public void hideNEIItems() {}
}
