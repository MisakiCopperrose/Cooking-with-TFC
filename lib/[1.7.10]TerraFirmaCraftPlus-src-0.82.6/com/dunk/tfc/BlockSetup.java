package com.dunk.tfc;

import com.dunk.tfc.Blocks.BlockBloom;
import com.dunk.tfc.Blocks.BlockCharcoal;
import com.dunk.tfc.Blocks.BlockChimney;
import com.dunk.tfc.Blocks.BlockCrop;
import com.dunk.tfc.Blocks.BlockDetailed;
import com.dunk.tfc.Blocks.BlockFarmland;
import com.dunk.tfc.Blocks.BlockFireBrick;
import com.dunk.tfc.Blocks.BlockIngotPile;
import com.dunk.tfc.Blocks.BlockInvisible;
import com.dunk.tfc.Blocks.BlockL;
import com.dunk.tfc.Blocks.BlockLogPile;
import com.dunk.tfc.Blocks.BlockMetalSheet;
import com.dunk.tfc.Blocks.BlockMetalTrapDoor;
import com.dunk.tfc.Blocks.BlockMolten;
import com.dunk.tfc.Blocks.BlockMudBricks;
import com.dunk.tfc.Blocks.BlockPlanks;
import com.dunk.tfc.Blocks.BlockPlanks2;
import com.dunk.tfc.Blocks.BlockPlasteredBlock;
import com.dunk.tfc.Blocks.BlockSlab;
import com.dunk.tfc.Blocks.BlockSmoke;
import com.dunk.tfc.Blocks.BlockSmokeRack;
import com.dunk.tfc.Blocks.BlockStair;
import com.dunk.tfc.Blocks.BlockStalactite;
import com.dunk.tfc.Blocks.BlockSulfur;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Blocks.BlockThatch;
import com.dunk.tfc.Blocks.BlockTileRoof;
import com.dunk.tfc.Blocks.BlockWattle;
import com.dunk.tfc.Blocks.BlockWattleDaub;
import com.dunk.tfc.Blocks.BlockWoodConstruct;
import com.dunk.tfc.Blocks.BlockWoodSupport;
import com.dunk.tfc.Blocks.BlockWoodSupport2;
import com.dunk.tfc.Blocks.BlockWoodenSpear;
import com.dunk.tfc.Blocks.BlockWorldItem;
import com.dunk.tfc.Blocks.Devices.BlockAnvil;
import com.dunk.tfc.Blocks.Devices.BlockBarrel;
import com.dunk.tfc.Blocks.Devices.BlockBasket;
import com.dunk.tfc.Blocks.Devices.BlockBeehive;
import com.dunk.tfc.Blocks.Devices.BlockBellows;
import com.dunk.tfc.Blocks.Devices.BlockBlastFurnace;
import com.dunk.tfc.Blocks.Devices.BlockChestTFC;
import com.dunk.tfc.Blocks.Devices.BlockCrucible;
import com.dunk.tfc.Blocks.Devices.BlockDrum;
import com.dunk.tfc.Blocks.Devices.BlockDryingBricks;
import com.dunk.tfc.Blocks.Devices.BlockEarlyBloomery;
import com.dunk.tfc.Blocks.Devices.BlockFirepit;
import com.dunk.tfc.Blocks.Devices.BlockFoodPrep;
import com.dunk.tfc.Blocks.Devices.BlockForge;
import com.dunk.tfc.Blocks.Devices.BlockGrill;
import com.dunk.tfc.Blocks.Devices.BlockHopper;
import com.dunk.tfc.Blocks.Devices.BlockLargeVessel;
import com.dunk.tfc.Blocks.Devices.BlockLeatherRack;
import com.dunk.tfc.Blocks.Devices.BlockLoom;
import com.dunk.tfc.Blocks.Devices.BlockNestBox;
import com.dunk.tfc.Blocks.Devices.BlockOilLamp;
import com.dunk.tfc.Blocks.Devices.BlockPottery;
import com.dunk.tfc.Blocks.Devices.BlockQuern;
import com.dunk.tfc.Blocks.Devices.BlockSluice;
import com.dunk.tfc.Blocks.Devices.BlockSpawnMeter;
import com.dunk.tfc.Blocks.Devices.BlockStand;
import com.dunk.tfc.Blocks.Devices.BlockStand2;
import com.dunk.tfc.Blocks.Devices.BlockTeepee;
import com.dunk.tfc.Blocks.Devices.BlockToolRack;
import com.dunk.tfc.Blocks.Devices.BlockWorkbench;
import com.dunk.tfc.Blocks.Flora.BlockBerryBush;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockBranch2;
import com.dunk.tfc.Blocks.Flora.BlockFauxPalm;
import com.dunk.tfc.Blocks.Flora.BlockFlora;
import com.dunk.tfc.Blocks.Flora.BlockFlower;
import com.dunk.tfc.Blocks.Flora.BlockFlower2;
import com.dunk.tfc.Blocks.Flora.BlockFruitLeaves;
import com.dunk.tfc.Blocks.Flora.BlockFruitWood;
import com.dunk.tfc.Blocks.Flora.BlockLeafLitter;
import com.dunk.tfc.Blocks.Flora.BlockLogHoriz;
import com.dunk.tfc.Blocks.Flora.BlockLogHoriz2;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural2;
import com.dunk.tfc.Blocks.Flora.BlockLogVert;
import com.dunk.tfc.Blocks.Flora.BlockLogVert2;
import com.dunk.tfc.Blocks.Flora.BlockSapling;
import com.dunk.tfc.Blocks.Flora.BlockSapling2;
import com.dunk.tfc.Blocks.Flora.BlockSkinnyLogNatural;
import com.dunk.tfc.Blocks.Flora.BlockSkinnyLogNatural2;
import com.dunk.tfc.Blocks.Flora.BlockStackedLogHoriz;
import com.dunk.tfc.Blocks.Flora.BlockStackedLogHoriz2;
import com.dunk.tfc.Blocks.Flora.BlockStackedLogVert;
import com.dunk.tfc.Blocks.Flora.BlockStackedLogVert2;
import com.dunk.tfc.Blocks.Flora.BlockUndergrowth;
import com.dunk.tfc.Blocks.Flora.BlockWaterPlant;
import com.dunk.tfc.Blocks.Liquids.BlockFreshWater;
import com.dunk.tfc.Blocks.Liquids.BlockHotWater;
import com.dunk.tfc.Blocks.Liquids.BlockHotWaterStatic;
import com.dunk.tfc.Blocks.Liquids.BlockLava;
import com.dunk.tfc.Blocks.Liquids.BlockLiquidStatic;
import com.dunk.tfc.Blocks.Liquids.BlockSaltWater;
import com.dunk.tfc.Blocks.Terrain.BlockClay;
import com.dunk.tfc.Blocks.Terrain.BlockClayGrass;
import com.dunk.tfc.Blocks.Terrain.BlockDirt;
import com.dunk.tfc.Blocks.Terrain.BlockDryGrass;
import com.dunk.tfc.Blocks.Terrain.BlockFungi;
import com.dunk.tfc.Blocks.Terrain.BlockGrass;
import com.dunk.tfc.Blocks.Terrain.BlockGravel;
import com.dunk.tfc.Blocks.Terrain.BlockIgEx;
import com.dunk.tfc.Blocks.Terrain.BlockIgExBrick;
import com.dunk.tfc.Blocks.Terrain.BlockIgExCobble;
import com.dunk.tfc.Blocks.Terrain.BlockIgExSmooth;
import com.dunk.tfc.Blocks.Terrain.BlockIgIn;
import com.dunk.tfc.Blocks.Terrain.BlockIgInBrick;
import com.dunk.tfc.Blocks.Terrain.BlockIgInCobble;
import com.dunk.tfc.Blocks.Terrain.BlockIgInSmooth;
import com.dunk.tfc.Blocks.Terrain.BlockMM;
import com.dunk.tfc.Blocks.Terrain.BlockMMBrick;
import com.dunk.tfc.Blocks.Terrain.BlockMMCobble;
import com.dunk.tfc.Blocks.Terrain.BlockMMSmooth;
import com.dunk.tfc.Blocks.Terrain.BlockMoss;
import com.dunk.tfc.Blocks.Terrain.BlockOre;
import com.dunk.tfc.Blocks.Terrain.BlockOre2;
import com.dunk.tfc.Blocks.Terrain.BlockOre3;
import com.dunk.tfc.Blocks.Terrain.BlockPeat;
import com.dunk.tfc.Blocks.Terrain.BlockPeatGrass;
import com.dunk.tfc.Blocks.Terrain.BlockSand;
import com.dunk.tfc.Blocks.Terrain.BlockSed;
import com.dunk.tfc.Blocks.Terrain.BlockSedBrick;
import com.dunk.tfc.Blocks.Terrain.BlockSedCobble;
import com.dunk.tfc.Blocks.Terrain.BlockSedSmooth;
import com.dunk.tfc.Blocks.Vanilla.BlockBed;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomBookshelf;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomButtonWood;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomCactus;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomDoor;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomFenceGate;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomFenceGate2;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomFlowerPot;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomIce;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves2;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLilyPad;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomMelon;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomPumpkin;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomReed;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomSnow;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomTallGrass;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomVine;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomWall;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomWattleDoor;
import com.dunk.tfc.Blocks.Vanilla.BlockTFCFence;
import com.dunk.tfc.Blocks.Vanilla.BlockTFCFence2;
import com.dunk.tfc.Blocks.Vanilla.BlockTorch;
import com.dunk.tfc.Blocks.Vanilla.BlockTorchOff;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemBlocks.ItemAnvil1;
import com.dunk.tfc.Items.ItemBlocks.ItemAnvil2;
import com.dunk.tfc.Items.ItemBlocks.ItemArmourStand;
import com.dunk.tfc.Items.ItemBlocks.ItemArmourStand2;
import com.dunk.tfc.Items.ItemBlocks.ItemBarrels;
import com.dunk.tfc.Items.ItemBlocks.ItemBellows;
import com.dunk.tfc.Items.ItemBlocks.ItemBerryBush;
import com.dunk.tfc.Items.ItemBlocks.ItemBigDrum;
import com.dunk.tfc.Items.ItemBlocks.ItemBranch;
import com.dunk.tfc.Items.ItemBlocks.ItemBranch2;
import com.dunk.tfc.Items.ItemBlocks.ItemChest;
import com.dunk.tfc.Items.ItemBlocks.ItemCrucible;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomLilyPad;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomTallGrass;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomWood;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomWood2;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomWoodH;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomWoodH2;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomWoodH3;
import com.dunk.tfc.Items.ItemBlocks.ItemCustomWoodH4;
import com.dunk.tfc.Items.ItemBlocks.ItemFence;
import com.dunk.tfc.Items.ItemBlocks.ItemFence2;
import com.dunk.tfc.Items.ItemBlocks.ItemFenceGate;
import com.dunk.tfc.Items.ItemBlocks.ItemFenceGate2;
import com.dunk.tfc.Items.ItemBlocks.ItemFlora;
import com.dunk.tfc.Items.ItemBlocks.ItemFlowers;
import com.dunk.tfc.Items.ItemBlocks.ItemFlowers2;
import com.dunk.tfc.Items.ItemBlocks.ItemFungi;
import com.dunk.tfc.Items.ItemBlocks.ItemGrill;
import com.dunk.tfc.Items.ItemBlocks.ItemHopper;
import com.dunk.tfc.Items.ItemBlocks.ItemLargeVessel;
import com.dunk.tfc.Items.ItemBlocks.ItemLittleDrum;
import com.dunk.tfc.Items.ItemBlocks.ItemLooms;
import com.dunk.tfc.Items.ItemBlocks.ItemMetalTrapDoor;
import com.dunk.tfc.Items.ItemBlocks.ItemOilLamp;
import com.dunk.tfc.Items.ItemBlocks.ItemPlasterWood;
import com.dunk.tfc.Items.ItemBlocks.ItemPlasterWood2;
import com.dunk.tfc.Items.ItemBlocks.ItemSapling;
import com.dunk.tfc.Items.ItemBlocks.ItemSapling2;
import com.dunk.tfc.Items.ItemBlocks.ItemSoil;
import com.dunk.tfc.Items.ItemBlocks.ItemStone;
import com.dunk.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.dunk.tfc.Items.ItemBlocks.ItemToolRack;
import com.dunk.tfc.Items.ItemBlocks.ItemTorch;
import com.dunk.tfc.Items.ItemBlocks.ItemVine;
import com.dunk.tfc.Items.ItemBlocks.ItemWoodSupport;
import com.dunk.tfc.Items.ItemBlocks.ItemWoodSupport2;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockSetup extends TFCBlocks
{
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(ore, "Ore1");
		GameRegistry.registerBlock(ore1b, "Ore1b");
		GameRegistry.registerBlock(ore2, "Ore2");
		GameRegistry.registerBlock(ore3, "Ore3");
		GameRegistry.registerBlock(stoneIgIn, ItemStone.class, "StoneIgIn");
		GameRegistry.registerBlock(stoneIgEx, ItemStone.class, "StoneIgEx");
		GameRegistry.registerBlock(stoneSed, ItemStone.class, "StoneSed");
		GameRegistry.registerBlock(stoneMM, ItemStone.class, "StoneMM");

		GameRegistry.registerBlock(fauxPalm, "Fake Palm");
		GameRegistry.registerBlock(cLights, "cLights");

		/*
		 * GameRegistry.registerBlock(branch_xyz, "Branch_xyz");
		 * GameRegistry.registerBlock(branch_xyZ, "Branch_xyZ");
		 * GameRegistry.registerBlock(branch_xYz, "Branch_xYz");
		 * GameRegistry.registerBlock(branch_xYZ, "Branch_xYZ");
		 * GameRegistry.registerBlock(branch_Xyz, "Branch_Xyz");
		 * GameRegistry.registerBlock(branch_XyZ, "Branch_XyZ");
		 * GameRegistry.registerBlock(branch_XYz, "Branch_XYz");
		 * GameRegistry.registerBlock(branch_XYZ, "Branch_XYZ");
		 * GameRegistry.registerBlock(branch_x_z, "Branch_x_z");
		 * GameRegistry.registerBlock(branch_x_Z, "Branch_x_Z");
		 * GameRegistry.registerBlock(branch_X_z, "Branch_X_z");
		 * GameRegistry.registerBlock(branch_X_Z, "Branch_X_Z");
		 * GameRegistry.registerBlock(branch__Yz, "Branch__Yz");
		 * GameRegistry.registerBlock(branch__YZ, "Branch__YZ");
		 * GameRegistry.registerBlock(branch__yz, "Branch__yz");
		 * GameRegistry.registerBlock(branch__yZ, "Branch__yZ");
		 * GameRegistry.registerBlock(branch___z, "Branch___z");
		 * GameRegistry.registerBlock(branch___Z, "Branch___Z");
		 * GameRegistry.registerBlock(branch_x__, "Branch_x__");
		 * GameRegistry.registerBlock(branch_X__, "Branch_X__");
		 * GameRegistry.registerBlock(branch_xy_, "Branch_xy_");
		 * GameRegistry.registerBlock(branch_Xy_, "Branch_Xy_");
		 * GameRegistry.registerBlock(branch_xY_, "Branch_xY_");
		 * GameRegistry.registerBlock(branch_XY_, "Branch_XY_");
		 * GameRegistry.registerBlock(branch__y_, "Branch__y_"); //2
		 * GameRegistry.registerBlock(branch2_xyz, "Branch2_xyz");
		 * GameRegistry.registerBlock(branch2_xyZ, "Branch2_xyZ");
		 * GameRegistry.registerBlock(branch2_xYz, "Branch2_xYz");
		 * GameRegistry.registerBlock(branch2_xYZ, "Branch2_xYZ");
		 * GameRegistry.registerBlock(branch2_Xyz, "Branch2_Xyz");
		 * GameRegistry.registerBlock(branch2_XyZ, "Branch2_XyZ");
		 * GameRegistry.registerBlock(branch2_XYz, "Branch2_XYz");
		 * GameRegistry.registerBlock(branch2_XYZ, "Branch2_XYZ");
		 * GameRegistry.registerBlock(branch2_x_z, "Branch2_x_z");
		 * GameRegistry.registerBlock(branch2_x_Z, "Branch2_x_Z");
		 * GameRegistry.registerBlock(branch2_X_z, "Branch2_X_z");
		 * GameRegistry.registerBlock(branch2_X_Z, "Branch2_X_Z");
		 * GameRegistry.registerBlock(branch2__Yz, "Branch2__Yz");
		 * GameRegistry.registerBlock(branch2__YZ, "Branch2__YZ");
		 * GameRegistry.registerBlock(branch2__yz, "Branch2__yz");
		 * GameRegistry.registerBlock(branch2__yZ, "Branch2__yZ");
		 * GameRegistry.registerBlock(branch2___z, "Branch2___z");
		 * GameRegistry.registerBlock(branch2___Z, "Branch2___Z");
		 * GameRegistry.registerBlock(branch2_x__, "Branch2_x__");
		 * GameRegistry.registerBlock(branch2_X__, "Branch2_X__");
		 * GameRegistry.registerBlock(branch2_xy_, "Branch2_xy_");
		 * GameRegistry.registerBlock(branch2_Xy_, "Branch2_Xy_");
		 * GameRegistry.registerBlock(branch2_xY_, "Branch2_xY_");
		 * GameRegistry.registerBlock(branch2_XY_, "Branch2_XY_");
		 * GameRegistry.registerBlock(branch2__y_, "Branch2__y_"); //Terminal
		 * branches GameRegistry.registerBlock(branchEnd_xyz, "BranchEnd_xyz");
		 * GameRegistry.registerBlock(branchEnd_xyZ, "BranchEnd_xyZ");
		 * GameRegistry.registerBlock(branchEnd_xYz, "BranchEnd_xYz");
		 * GameRegistry.registerBlock(branchEnd_xYZ, "BranchEnd_xYZ");
		 * GameRegistry.registerBlock(branchEnd_Xyz, "BranchEnd_Xyz");
		 * GameRegistry.registerBlock(branchEnd_XyZ, "BranchEnd_XyZ");
		 * GameRegistry.registerBlock(branchEnd_XYz, "BranchEnd_XYz");
		 * GameRegistry.registerBlock(branchEnd_XYZ, "BranchEnd_XYZ");
		 * GameRegistry.registerBlock(branchEnd_x_z, "BranchEnd_x_z");
		 * GameRegistry.registerBlock(branchEnd_x_Z, "BranchEnd_x_Z");
		 * GameRegistry.registerBlock(branchEnd_X_z, "BranchEnd_X_z");
		 * GameRegistry.registerBlock(branchEnd_X_Z, "BranchEnd_X_Z");
		 * GameRegistry.registerBlock(branchEnd__Yz, "BranchEnd__Yz");
		 * GameRegistry.registerBlock(branchEnd__YZ, "BranchEnd__YZ");
		 * GameRegistry.registerBlock(branchEnd__yz, "BranchEnd__yz");
		 * GameRegistry.registerBlock(branchEnd__yZ, "BranchEnd__yZ");
		 * GameRegistry.registerBlock(branchEnd___z, "BranchEnd___z");
		 * GameRegistry.registerBlock(branchEnd___Z, "BranchEnd___Z");
		 * GameRegistry.registerBlock(branchEnd_x__, "BranchEnd_x__");
		 * GameRegistry.registerBlock(branchEnd_X__, "BranchEnd_X__");
		 * GameRegistry.registerBlock(branchEnd_xy_, "BranchEnd_xy_");
		 * GameRegistry.registerBlock(branchEnd_Xy_, "BranchEnd_Xy_");
		 * GameRegistry.registerBlock(branchEnd_xY_, "BranchEnd_xY_");
		 * GameRegistry.registerBlock(branchEnd_XY_, "BranchEnd_XY_");
		 * GameRegistry.registerBlock(branchEnd__y_, "BranchEnd__y_"); //2
		 * GameRegistry.registerBlock(branchEnd2_xyz, "BranchEnd2_xyz");
		 * GameRegistry.registerBlock(branchEnd2_xyZ, "BranchEnd2_xyZ");
		 * GameRegistry.registerBlock(branchEnd2_xYz, "BranchEnd2_xYz");
		 * GameRegistry.registerBlock(branchEnd2_xYZ, "BranchEnd2_xYZ");
		 * GameRegistry.registerBlock(branchEnd2_Xyz, "BranchEnd2_Xyz");
		 * GameRegistry.registerBlock(branchEnd2_XyZ, "BranchEnd2_XyZ");
		 * GameRegistry.registerBlock(branchEnd2_XYz, "BranchEnd2_XYz");
		 * GameRegistry.registerBlock(branchEnd2_XYZ, "BranchEnd2_XYZ");
		 * GameRegistry.registerBlock(branchEnd2_x_z, "BranchEnd2_x_z");
		 * GameRegistry.registerBlock(branchEnd2_x_Z, "BranchEnd2_x_Z");
		 * GameRegistry.registerBlock(branchEnd2_X_z, "BranchEnd2_X_z");
		 * GameRegistry.registerBlock(branchEnd2_X_Z, "BranchEnd2_X_Z");
		 * GameRegistry.registerBlock(branchEnd2__Yz, "BranchEnd2__Yz");
		 * GameRegistry.registerBlock(branchEnd2__YZ, "BranchEnd2__YZ");
		 * GameRegistry.registerBlock(branchEnd2__yz, "BranchEnd2__yz");
		 * GameRegistry.registerBlock(branchEnd2__yZ, "BranchEnd2__yZ");
		 * GameRegistry.registerBlock(branchEnd2___z, "BranchEnd2___z");
		 * GameRegistry.registerBlock(branchEnd2___Z, "BranchEnd2___Z");
		 * GameRegistry.registerBlock(branchEnd2_x__, "BranchEnd2_x__");
		 * GameRegistry.registerBlock(branchEnd2_X__, "BranchEnd2_X__");
		 * GameRegistry.registerBlock(branchEnd2_xy_, "BranchEnd2_xy_");
		 * GameRegistry.registerBlock(branchEnd2_Xy_, "BranchEnd2_Xy_");
		 * GameRegistry.registerBlock(branchEnd2_xY_, "BranchEnd2_xY_");
		 * GameRegistry.registerBlock(branchEnd2_XY_, "BranchEnd2_XY_");
		 * GameRegistry.registerBlock(branchEnd2__y_, "BranchEnd2__y_");
		 */

		GameRegistry.registerBlock(branch_xyz, ItemBranch.class, "Branch_xyz");
		GameRegistry.registerBlock(branch_xyZ, ItemBranch.class, "Branch_xyZ");
		GameRegistry.registerBlock(branch_xYz, ItemBranch.class, "Branch_xYz");
		GameRegistry.registerBlock(branch_xYZ, ItemBranch.class, "Branch_xYZ");
		GameRegistry.registerBlock(branch_Xyz, ItemBranch.class, "Branch_Xyz");
		GameRegistry.registerBlock(branch_XyZ, ItemBranch.class, "Branch_XyZ");
		GameRegistry.registerBlock(branch_XYz, ItemBranch.class, "Branch_XYz");
		GameRegistry.registerBlock(branch_XYZ, ItemBranch.class, "Branch_XYZ");
		GameRegistry.registerBlock(branch_x_z, ItemBranch.class, "Branch_x_z");
		GameRegistry.registerBlock(branch_x_Z, ItemBranch.class, "Branch_x_Z");
		GameRegistry.registerBlock(branch_X_z, ItemBranch.class, "Branch_X_z");
		GameRegistry.registerBlock(branch_X_Z, ItemBranch.class, "Branch_X_Z");
		GameRegistry.registerBlock(branch__Yz, ItemBranch.class, "Branch__Yz");
		GameRegistry.registerBlock(branch__YZ, ItemBranch.class, "Branch__YZ");
		GameRegistry.registerBlock(branch__yz, ItemBranch.class, "Branch__yz");
		GameRegistry.registerBlock(branch__yZ, ItemBranch.class, "Branch__yZ");
		GameRegistry.registerBlock(branch___z, ItemBranch.class, "Branch___z");
		GameRegistry.registerBlock(branch___Z, ItemBranch.class, "Branch___Z");
		GameRegistry.registerBlock(branch_x__, ItemBranch.class, "Branch_x__");
		GameRegistry.registerBlock(branch_X__, ItemBranch.class, "Branch_X__");
		GameRegistry.registerBlock(branch_xy_, ItemBranch.class, "Branch_xy_");
		GameRegistry.registerBlock(branch_Xy_, ItemBranch.class, "Branch_Xy_");
		GameRegistry.registerBlock(branch_xY_, ItemBranch.class, "Branch_xY_");
		GameRegistry.registerBlock(branch_XY_, ItemBranch.class, "Branch_XY_");
		GameRegistry.registerBlock(branch__y_, ItemBranch.class, "Branch__y_");
		// 2
		GameRegistry.registerBlock(branch2_xyz, ItemBranch2.class, "Branch2_xyz");
		GameRegistry.registerBlock(branch2_xyZ, ItemBranch2.class, "Branch2_xyZ");
		GameRegistry.registerBlock(branch2_xYz, ItemBranch2.class, "Branch2_xYz");
		GameRegistry.registerBlock(branch2_xYZ, ItemBranch2.class, "Branch2_xYZ");
		GameRegistry.registerBlock(branch2_Xyz, ItemBranch2.class, "Branch2_Xyz");
		GameRegistry.registerBlock(branch2_XyZ, ItemBranch2.class, "Branch2_XyZ");
		GameRegistry.registerBlock(branch2_XYz, ItemBranch2.class, "Branch2_XYz");
		GameRegistry.registerBlock(branch2_XYZ, ItemBranch2.class, "Branch2_XYZ");
		GameRegistry.registerBlock(branch2_x_z, ItemBranch2.class, "Branch2_x_z");
		GameRegistry.registerBlock(branch2_x_Z, ItemBranch2.class, "Branch2_x_Z");
		GameRegistry.registerBlock(branch2_X_z, ItemBranch2.class, "Branch2_X_z");
		GameRegistry.registerBlock(branch2_X_Z, ItemBranch2.class, "Branch2_X_Z");
		GameRegistry.registerBlock(branch2__Yz, ItemBranch2.class, "Branch2__Yz");
		GameRegistry.registerBlock(branch2__YZ, ItemBranch2.class, "Branch2__YZ");
		GameRegistry.registerBlock(branch2__yz, ItemBranch2.class, "Branch2__yz");
		GameRegistry.registerBlock(branch2__yZ, ItemBranch2.class, "Branch2__yZ");
		GameRegistry.registerBlock(branch2___z, ItemBranch2.class, "Branch2___z");
		GameRegistry.registerBlock(branch2___Z, ItemBranch2.class, "Branch2___Z");
		GameRegistry.registerBlock(branch2_x__, ItemBranch2.class, "Branch2_x__");
		GameRegistry.registerBlock(branch2_X__, ItemBranch2.class, "Branch2_X__");
		GameRegistry.registerBlock(branch2_xy_, ItemBranch2.class, "Branch2_xy_");
		GameRegistry.registerBlock(branch2_Xy_, ItemBranch2.class, "Branch2_Xy_");
		GameRegistry.registerBlock(branch2_xY_, ItemBranch2.class, "Branch2_xY_");
		GameRegistry.registerBlock(branch2_XY_, ItemBranch2.class, "Branch2_XY_");
		GameRegistry.registerBlock(branch2__y_, ItemBranch2.class, "Branch2__y_");
		// Terminal branches
		GameRegistry.registerBlock(branchEnd_xyz, ItemBranch.class, "BranchEnd_xyz");
		GameRegistry.registerBlock(branchEnd_xyZ, ItemBranch.class, "BranchEnd_xyZ");
		GameRegistry.registerBlock(branchEnd_xYz, ItemBranch.class, "BranchEnd_xYz");
		GameRegistry.registerBlock(branchEnd_xYZ, ItemBranch.class, "BranchEnd_xYZ");
		GameRegistry.registerBlock(branchEnd_Xyz, ItemBranch.class, "BranchEnd_Xyz");
		GameRegistry.registerBlock(branchEnd_XyZ, ItemBranch.class, "BranchEnd_XyZ");
		GameRegistry.registerBlock(branchEnd_XYz, ItemBranch.class, "BranchEnd_XYz");
		GameRegistry.registerBlock(branchEnd_XYZ, ItemBranch.class, "BranchEnd_XYZ");
		GameRegistry.registerBlock(branchEnd_x_z, ItemBranch.class, "BranchEnd_x_z");
		GameRegistry.registerBlock(branchEnd_x_Z, ItemBranch.class, "BranchEnd_x_Z");
		GameRegistry.registerBlock(branchEnd_X_z, ItemBranch.class, "BranchEnd_X_z");
		GameRegistry.registerBlock(branchEnd_X_Z, ItemBranch.class, "BranchEnd_X_Z");
		GameRegistry.registerBlock(branchEnd__Yz, ItemBranch.class, "BranchEnd__Yz");
		GameRegistry.registerBlock(branchEnd__YZ, ItemBranch.class, "BranchEnd__YZ");
		GameRegistry.registerBlock(branchEnd__yz, ItemBranch.class, "BranchEnd__yz");
		GameRegistry.registerBlock(branchEnd__yZ, ItemBranch.class, "BranchEnd__yZ");
		GameRegistry.registerBlock(branchEnd___z, ItemBranch.class, "BranchEnd___z");
		GameRegistry.registerBlock(branchEnd___Z, ItemBranch.class, "BranchEnd___Z");
		GameRegistry.registerBlock(branchEnd_x__, ItemBranch.class, "BranchEnd_x__");
		GameRegistry.registerBlock(branchEnd_X__, ItemBranch.class, "BranchEnd_X__");
		GameRegistry.registerBlock(branchEnd_xy_, ItemBranch.class, "BranchEnd_xy_");
		GameRegistry.registerBlock(branchEnd_Xy_, ItemBranch.class, "BranchEnd_Xy_");
		GameRegistry.registerBlock(branchEnd_xY_, ItemBranch.class, "BranchEnd_xY_");
		GameRegistry.registerBlock(branchEnd_XY_, ItemBranch.class, "BranchEnd_XY_");
		GameRegistry.registerBlock(branchEnd__y_, ItemBranch.class, "BranchEnd__y_");
		// 2
		GameRegistry.registerBlock(branchEnd2_xyz, ItemBranch2.class, "BranchEnd2_xyz");
		GameRegistry.registerBlock(branchEnd2_xyZ, ItemBranch2.class, "BranchEnd2_xyZ");
		GameRegistry.registerBlock(branchEnd2_xYz, ItemBranch2.class, "BranchEnd2_xYz");
		GameRegistry.registerBlock(branchEnd2_xYZ, ItemBranch2.class, "BranchEnd2_xYZ");
		GameRegistry.registerBlock(branchEnd2_Xyz, ItemBranch2.class, "BranchEnd2_Xyz");
		GameRegistry.registerBlock(branchEnd2_XyZ, ItemBranch2.class, "BranchEnd2_XyZ");
		GameRegistry.registerBlock(branchEnd2_XYz, ItemBranch2.class, "BranchEnd2_XYz");
		GameRegistry.registerBlock(branchEnd2_XYZ, ItemBranch2.class, "BranchEnd2_XYZ");
		GameRegistry.registerBlock(branchEnd2_x_z, ItemBranch2.class, "BranchEnd2_x_z");
		GameRegistry.registerBlock(branchEnd2_x_Z, ItemBranch2.class, "BranchEnd2_x_Z");
		GameRegistry.registerBlock(branchEnd2_X_z, ItemBranch2.class, "BranchEnd2_X_z");
		GameRegistry.registerBlock(branchEnd2_X_Z, ItemBranch2.class, "BranchEnd2_X_Z");
		GameRegistry.registerBlock(branchEnd2__Yz, ItemBranch2.class, "BranchEnd2__Yz");
		GameRegistry.registerBlock(branchEnd2__YZ, ItemBranch2.class, "BranchEnd2__YZ");
		GameRegistry.registerBlock(branchEnd2__yz, ItemBranch2.class, "BranchEnd2__yz");
		GameRegistry.registerBlock(branchEnd2__yZ, ItemBranch2.class, "BranchEnd2__yZ");
		GameRegistry.registerBlock(branchEnd2___z, ItemBranch2.class, "BranchEnd2___z");
		GameRegistry.registerBlock(branchEnd2___Z, ItemBranch2.class, "BranchEnd2___Z");
		GameRegistry.registerBlock(branchEnd2_x__, ItemBranch2.class, "BranchEnd2_x__");
		GameRegistry.registerBlock(branchEnd2_X__, ItemBranch2.class, "BranchEnd2_X__");
		GameRegistry.registerBlock(branchEnd2_xy_, ItemBranch2.class, "BranchEnd2_xy_");
		GameRegistry.registerBlock(branchEnd2_Xy_, ItemBranch2.class, "BranchEnd2_Xy_");
		GameRegistry.registerBlock(branchEnd2_xY_, ItemBranch2.class, "BranchEnd2_xY_");
		GameRegistry.registerBlock(branchEnd2_XY_, ItemBranch2.class, "BranchEnd2_XY_");
		GameRegistry.registerBlock(branchEnd2__y_, ItemBranch2.class, "BranchEnd2__y_");

		GameRegistry.registerBlock(stoneIgInCobble, ItemStone.class, "StoneIgInCobble");
		GameRegistry.registerBlock(stoneIgExCobble, ItemStone.class, "StoneIgExCobble");
		GameRegistry.registerBlock(stoneSedCobble, ItemStone.class, "StoneSedCobble");
		GameRegistry.registerBlock(stoneMMCobble, ItemStone.class, "StoneMMCobble");
		GameRegistry.registerBlock(stoneIgInSmooth, ItemStone.class, "StoneIgInSmooth");
		GameRegistry.registerBlock(stoneIgExSmooth, ItemStone.class, "StoneIgExSmooth");
		GameRegistry.registerBlock(stoneSedSmooth, ItemStone.class, "StoneSedSmooth");
		GameRegistry.registerBlock(stoneMMSmooth, ItemStone.class, "StoneMMSmooth");
		GameRegistry.registerBlock(stoneIgInBrick, ItemStone.class, "StoneIgInBrick");
		GameRegistry.registerBlock(stoneIgExBrick, ItemStone.class, "StoneIgExBrick");
		GameRegistry.registerBlock(stoneSedBrick, ItemStone.class, "StoneSedBrick");
		GameRegistry.registerBlock(stoneMMBrick, ItemStone.class, "StoneMMBrick");
		GameRegistry.registerBlock(stoneIgInLargeBrick, ItemStone.class, "StoneIgInLargeBrick");
		GameRegistry.registerBlock(stoneIgExLargeBrick, ItemStone.class, "StoneIgExLargeBrick");
		GameRegistry.registerBlock(stoneSedLargeBrick, ItemStone.class, "StoneSedLargeBrick");
		GameRegistry.registerBlock(stoneMMLargeBrick, ItemStone.class, "StoneMMLargeBrick");

		GameRegistry.registerBlock(dirt, ItemSoil.class, "Dirt");
		GameRegistry.registerBlock(dirt2, ItemSoil.class, "Dirt2");
		GameRegistry.registerBlock(sand, ItemSoil.class, "Sand");
		GameRegistry.registerBlock(sand2, ItemSoil.class, "Sand2");
		GameRegistry.registerBlock(clay, ItemSoil.class, "Clay");
		GameRegistry.registerBlock(clay2, ItemSoil.class, "Clay2");
		GameRegistry.registerBlock(grass, ItemSoil.class, "Grass");
		GameRegistry.registerBlock(grass2, ItemSoil.class, "Grass2");
		GameRegistry.registerBlock(clayGrass, ItemSoil.class, "ClayGrass");
		GameRegistry.registerBlock(clayGrass2, ItemSoil.class, "ClayGrass2");
		GameRegistry.registerBlock(peatGrass, ItemSoil.class, "PeatGrass");
		GameRegistry.registerBlock(peat, ItemSoil.class, "Peat");
		GameRegistry.registerBlock(dryGrass, ItemSoil.class, "DryGrass");
		GameRegistry.registerBlock(dryGrass2, ItemSoil.class, "DryGrass2");
		GameRegistry.registerBlock(tallGrass, ItemCustomTallGrass.class, "TallGrass");
		GameRegistry.registerBlock(worldItem, "LooseRock");
		GameRegistry.registerBlock(logPile, "LogPile");
		GameRegistry.registerBlock(charcoal, "Charcoal");
		GameRegistry.registerBlock(detailed, "Detailed");

		GameRegistry.registerBlock(tilledSoil, ItemSoil.class, "tilledSoil");
		GameRegistry.registerBlock(tilledSoil2, ItemSoil.class, "tilledSoil2");

		GameRegistry.registerBlock(woodSupportV, ItemWoodSupport.class, "WoodSupportV");
		GameRegistry.registerBlock(woodSupportH, ItemWoodSupport.class, "WoodSupportH");
		GameRegistry.registerBlock(woodSupportV2, ItemWoodSupport2.class, "WoodSupportV2");
		GameRegistry.registerBlock(woodSupportH2, ItemWoodSupport2.class, "WoodSupportH2");
		GameRegistry.registerBlock(sulfur, "Sulfur");
		GameRegistry.registerBlock(logNatural, ItemCustomWood.class, "log");
		GameRegistry.registerBlock(logNatural2, ItemCustomWood2.class, "log2");
		GameRegistry.registerBlock(skinnyLogNatural, ItemCustomWood.class, "skinnyLog");
		GameRegistry.registerBlock(skinnyLogNatural2, ItemCustomWood2.class, "skinnyLog2");
		GameRegistry.registerBlock(leaves, ItemCustomWood.class, "leaves");
		GameRegistry.registerBlock(leaves2, ItemCustomWood2.class, "leaves2");
		GameRegistry.registerBlock(sapling, ItemSapling.class, "sapling");
		GameRegistry.registerBlock(sapling2, ItemSapling2.class, "sapling2");
		GameRegistry.registerBlock(mudBricks,ItemStone.class, "mudBricks");
		GameRegistry.registerBlock(mudBricks2,ItemStone.class, "mudBricks2");
		GameRegistry.registerBlock(dryingBricks, "dryingBricks");
		GameRegistry.registerBlock(brickChimney,ItemStone.class, "brickChimney");
		GameRegistry.registerBlock(chimneyBricks,ItemStone.class, "chimneyBricks");
		GameRegistry.registerBlock(chimneyBricks2,ItemStone.class, "chimneyBricks2");
		GameRegistry.registerBlock(chimneyLargeBricks,ItemStone.class, "chimneyLargeBricks");
		GameRegistry.registerBlock(chimneyLargeBricks2,ItemStone.class, "chimneyLargeBricks2");
		GameRegistry.registerBlock(leafLitter, "leafLitter");
		GameRegistry.registerBlock(undergrowth, "undergrowth");
		GameRegistry.registerBlock(shrub, "shrub");
		GameRegistry.registerBlock(undergrowthPalm, "undergrowthPalm");
		GameRegistry.registerBlock(lowUndergrowth, "lowUndergrowth");
		GameRegistry.registerBlock(fern, "fern");
		GameRegistry.registerBlock(planks, ItemCustomWood.class, "planks");
		GameRegistry.registerBlock(planks2, ItemCustomWood2.class, "planks2");
		GameRegistry.registerBlock(plasteredBlock, "plasteredBlock");

		GameRegistry.registerBlock(firepit, "Firepit");
		GameRegistry.registerBlock(bellows, ItemBellows.class, "Bellows");
		GameRegistry.registerBlock(anvil, ItemAnvil1.class, "Anvil");
		GameRegistry.registerBlock(anvil2, ItemAnvil2.class, "Anvil2");
		GameRegistry.registerBlock(forge, "Forge");

		GameRegistry.registerBlock(molten, "Molten");
		GameRegistry.registerBlock(blastFurnace, ItemTerraBlock.class, "Bloomery");
		GameRegistry.registerBlock(bloomery, ItemTerraBlock.class, "EarlyBloomery");
		GameRegistry.registerBlock(sluice, "Sluice");
		GameRegistry.registerBlock(bloom, "Bloom");

		GameRegistry.registerBlock(fruitTreeWood, "fruitTreeWood");
		GameRegistry.registerBlock(fruitTreeLeaves, "fruitTreeLeaves");
		GameRegistry.registerBlock(fruitTreeLeaves2, "fruitTreeLeaves2");

		GameRegistry.registerBlock(stoneStairs, "stoneStairs");
		GameRegistry.registerBlock(stoneSlabs, "stoneSlabs");
		GameRegistry.registerBlock(stoneStalac, "stoneStalac");

		GameRegistry.registerBlock(teepee, "Teepee");
		GameRegistry.registerBlock(wolfTeepee, "Wolf Teepee");
		GameRegistry.registerBlock(bearTeepee, "Bear Teepee");
		GameRegistry.registerBlock(woodSpear,"WoodenSpear");
		GameRegistry.registerBlock(invisibleBlock,"BlockInvisible");
		
		GameRegistry.registerBlock(woodConstruct, "WoodConstruct");
		GameRegistry.registerBlock(woodVert, ItemCustomWood.class, "WoodVert");
		GameRegistry.registerBlock(woodVert2, ItemCustomWood2.class, "WoodVert2");
		GameRegistry.registerBlock(woodHoriz, ItemCustomWoodH.class, "WoodHoriz");
		GameRegistry.registerBlock(woodHoriz2, ItemCustomWoodH2.class, "WoodHoriz2");
		GameRegistry.registerBlock(woodHoriz3, ItemCustomWoodH3.class, "WoodHoriz3");
		GameRegistry.registerBlock(woodHoriz4, ItemCustomWoodH4.class,"WoodHoriz4");
		GameRegistry.registerBlock(stackedWoodHoriz, ItemCustomWoodH.class, "StackedWoodHoriz");
		GameRegistry.registerBlock(stackedWoodHoriz2, ItemCustomWoodH2.class, "StackedWoodHoriz2");
		GameRegistry.registerBlock(stackedWoodHoriz3, ItemCustomWoodH3.class, "StackedWoodHoriz3");
		GameRegistry.registerBlock(stackedWoodHoriz4, ItemCustomWoodH4.class,"StackedWoodHoriz4");
		GameRegistry.registerBlock(stackedWoodVert, ItemCustomWood.class, "StackedWoodVert");
		GameRegistry.registerBlock(stackedWoodVert2, ItemCustomWood2.class, "StackedWoodVert2");

		GameRegistry.registerBlock(toolRack, ItemToolRack.class, "ToolRack");
		GameRegistry.registerBlock(spawnMeter, ItemTerraBlock.class, "SpawnMeter");
		GameRegistry.registerBlock(foodPrep, "FoodPrep");
		GameRegistry.registerBlock(quern, ItemTerraBlock.class, "Quern");
		GameRegistry.registerBlock(wallCobbleIgIn, ItemStone.class, "WallCobbleIgIn");
		GameRegistry.registerBlock(wallCobbleIgEx, ItemStone.class, "WallCobbleIgEx");
		GameRegistry.registerBlock(wallCobbleSed, ItemStone.class, "WallCobbleSed");
		GameRegistry.registerBlock(wallCobbleMM, ItemStone.class, "WallCobbleMM");
		GameRegistry.registerBlock(wallRawIgIn, ItemStone.class, "WallRawIgIn");
		GameRegistry.registerBlock(wallRawIgEx, ItemStone.class, "WallRawIgEx");
		GameRegistry.registerBlock(wallRawSed, ItemStone.class, "WallRawSed");
		GameRegistry.registerBlock(wallRawMM, ItemStone.class, "WallRawMM");
		GameRegistry.registerBlock(wallBrickIgIn, ItemStone.class, "WallBrickIgIn");
		GameRegistry.registerBlock(wallBrickIgEx, ItemStone.class, "WallBrickIgEx");
		GameRegistry.registerBlock(wallBrickSed, ItemStone.class, "WallBrickSed");
		GameRegistry.registerBlock(wallBrickMM, ItemStone.class, "WallBrickMM");
		GameRegistry.registerBlock(wallLargeBrickIgIn, ItemStone.class, "WallLargeBrickIgIn");
		GameRegistry.registerBlock(wallLargeBrickIgEx, ItemStone.class, "WallLargeBrickIgEx");
		GameRegistry.registerBlock(wallLargeBrickSed, ItemStone.class, "WallLargeBrickSed");
		GameRegistry.registerBlock(wallLargeBrickMM, ItemStone.class, "WallLargeBrickMM");
		GameRegistry.registerBlock(wallSmoothIgIn, ItemStone.class, "WallSmoothIgIn");
		GameRegistry.registerBlock(wallSmoothIgEx, ItemStone.class, "WallSmoothIgEx");
		GameRegistry.registerBlock(wallSmoothSed, ItemStone.class, "WallSmoothSed");
		GameRegistry.registerBlock(wallSmoothMM, ItemStone.class, "WallSmoothMM");

		GameRegistry.registerBlock(saltWater, "SaltWater");
		GameRegistry.registerBlock(saltWaterStationary, "SaltWaterStationary");
		GameRegistry.registerBlock(freshWater, "FreshWater");
		GameRegistry.registerBlock(freshWaterStationary, "FreshWaterStationary");
		GameRegistry.registerBlock(hotWater, "HotWater");
		GameRegistry.registerBlock(hotWaterStationary, "HotWaterStationary");
		GameRegistry.registerBlock(lava, "Lava");
		GameRegistry.registerBlock(lavaStationary, "LavaStationary");
		GameRegistry.registerBlock(ice, "Ice");

		GameRegistry.registerBlock(waterPlant, "SeaGrassStill");
		
		GameRegistry.registerBlock(tileRoof, "TileRoof");
		GameRegistry.registerBlock(thatchRoof, "thatchRoof");
		GameRegistry.registerBlock(slateRoof, "slateRoof");

		GameRegistry.registerBlock(fireBrick, "FireBrick");
		GameRegistry.registerBlock(bricks, "Bricks");
		GameRegistry.registerBlock(metalSheet, "MetalSheet");

		// Wooden Doors
		for (int i = 0; i < Global.WOOD_ALL.length; i++)
			GameRegistry.registerBlock(doors[i], "Door" + Global.WOOD_ALL[i].replaceAll(" ", ""));
		
		GameRegistry.registerBlock(wattleDoor, "DoorWattle");

		GameRegistry.registerBlock(ingotPile, "IngotPile");
		GameRegistry.registerBlock(barrel, ItemBarrels.class, "Barrel");
		GameRegistry.registerBlock(bigDrum, ItemBigDrum.class, "BigDrum");
		GameRegistry.registerBlock(littleDrum, ItemLittleDrum.class, "LittleDrum");
		GameRegistry.registerBlock(loom, ItemLooms.class, "Loom");
		GameRegistry.registerBlock(primitiveLoom, ItemLooms.class, "Primitive Loom");
		GameRegistry.registerBlock(moss, ItemTerraBlock.class, "Moss");

		GameRegistry.registerBlock(flora, ItemFlora.class, "Flora");
		GameRegistry.registerBlock(pottery, "ClayPottery");
		GameRegistry.registerBlock(thatch, ItemTerraBlock.class, "Thatch");
		GameRegistry.registerBlock(crucible, ItemCrucible.class, "Crucible");
		GameRegistry.registerBlock(nestBox, ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(wattle, "Wattle Wall");
		GameRegistry.registerBlock(wattleDaub, "Wattle and Daub");
		GameRegistry.registerBlock(fence, ItemFence.class, "Fence");
		GameRegistry.registerBlock(fence2, ItemFence2.class, "Fence2");
		GameRegistry.registerBlock(fenceGate, ItemFenceGate.class, "FenceGate");
		GameRegistry.registerBlock(fenceGate2, ItemFenceGate2.class, "FenceGate2");
		GameRegistry.registerBlock(strawHideBed, "StrawHideBed");
		GameRegistry.registerBlock(armorStand, ItemArmourStand.class, "ArmourStand");
		GameRegistry.registerBlock(armorStand2, ItemArmourStand2.class, "ArmourStand2");
		GameRegistry.registerBlock(berryBush, ItemBerryBush.class, "BerryBush");
		GameRegistry.registerBlock(crops, "Crops");
		GameRegistry.registerBlock(lilyPad, ItemCustomLilyPad.class, "LilyPad");
		GameRegistry.registerBlock(flowers, ItemFlowers.class, "Flowers");
		GameRegistry.registerBlock(flowers2, ItemFlowers2.class, "Flowers2");
		GameRegistry.registerBlock(fungi, ItemFungi.class, "Fungi");
		GameRegistry.registerBlock(bookshelf, ItemTerraBlock.class, "Bookshelf");
		GameRegistry.registerBlock(torch, ItemTorch.class, "Torch");
		GameRegistry.registerBlock(torchOff, "TorchOff");
		GameRegistry.registerBlock(chest, ItemChest.class, "Chest TFC");
		GameRegistry.registerBlock(workbench, ItemTerraBlock.class, "Workbench");
		GameRegistry.registerBlock(cactus, ItemTerraBlock.class, "Cactus");
		GameRegistry.registerBlock(reeds, "Reeds");
		GameRegistry.registerBlock(pumpkin, ItemTerraBlock.class, "Pumpkin");
		GameRegistry.registerBlock(melon, ItemTerraBlock.class, "Melon");
		GameRegistry.registerBlock(litPumpkin, ItemTerraBlock.class, "LitPumpkin");
		GameRegistry.registerBlock(buttonWood, "ButtonWood");
		GameRegistry.registerBlock(vine, ItemVine.class, "Vine");
		GameRegistry.registerBlock(leatherRack, "LeatherRack");
		GameRegistry.registerBlock(gravel, ItemSoil.class, "Gravel");
		GameRegistry.registerBlock(gravel2, ItemSoil.class, "Gravel2");

		GameRegistry.registerBlock(grill, ItemGrill.class, "Grill");
		GameRegistry.registerBlock(metalTrapDoor, ItemMetalTrapDoor.class, "MetalTrapDoor");
		GameRegistry.registerBlock(vessel, ItemLargeVessel.class, "Vessel");
		GameRegistry.registerBlock(basket, ItemLargeVessel.class, "Basket");
		GameRegistry.registerBlock(beehive, ItemTerraBlock.class, "Beehive");
		GameRegistry.registerBlock(wildTreehive,ItemTerraBlock.class, "Treehive");
		GameRegistry.registerBlock(wildBeehive,ItemTerraBlock.class, "WildBeehive");
		GameRegistry.registerBlock(smoke, "Smoke");
		GameRegistry.registerBlock(smokeRack, "SmokeRack");
		GameRegistry.registerBlock(snow, "Snow");
		GameRegistry.registerBlock(oilLamp, ItemOilLamp.class, "OilLamp");
		GameRegistry.registerBlock(hopper, "Hopper");
		GameRegistry.registerBlock(stoneHopper, ItemHopper.class, "StoneHopper");
		GameRegistry.registerBlock(flowerPot, "FlowerPot");
	}

	public static void loadBlocks()
	{
		TerraFirmaCraft.LOG.info(new StringBuilder().append("Loading Blocks").toString());

		// Remove Items from Creative Tabs
		Blocks.double_wooden_slab.setCreativeTab(null);
		Blocks.wooden_slab.setCreativeTab(null);
		Blocks.spruce_stairs.setCreativeTab(null);
		Blocks.birch_stairs.setCreativeTab(null);
		Blocks.jungle_stairs.setCreativeTab(null);
		Blocks.waterlily.setCreativeTab(null);
		Blocks.tallgrass.setCreativeTab(null);
		Blocks.yellow_flower.setCreativeTab(null);
		Blocks.red_flower.setCreativeTab(null);
		Blocks.brown_mushroom.setCreativeTab(null);
		Blocks.red_mushroom.setCreativeTab(null);
		Blocks.bookshelf.setCreativeTab(null);
		Blocks.torch.setCreativeTab(null);
		Blocks.chest.setCreativeTab(null);
		Blocks.planks.setCreativeTab(null);
		Blocks.crafting_table.setCreativeTab(null);
		Blocks.cactus.setCreativeTab(null);
		Blocks.reeds.setCreativeTab(null);
		Blocks.pumpkin.setCreativeTab(null);
		Blocks.lit_pumpkin.setCreativeTab(null);
		Blocks.wooden_button.setCreativeTab(null);
		Blocks.ice.setCreativeTab(null);
		Blocks.vine.setCreativeTab(null);
		Blocks.flower_pot.setCreativeTab(null);

		bookshelf = new BlockCustomBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood)
				.setBlockName("Bookshelf").setBlockTextureName("bookshelf");
		torch = new BlockTorch().setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("Torch")
				.setBlockTextureName("torch_on");
		torchOff = new BlockTorchOff().setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("TorchOff")
				.setBlockTextureName("torch_on");
		chest = new BlockChestTFC().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Chest");
		workbench = new BlockWorkbench().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Workbench")
				.setBlockTextureName("crafting_table");
		cactus = new BlockCustomCactus().setHardness(0.4F).setStepSound(Block.soundTypeCloth).setBlockName("Cactus")
				.setBlockTextureName("cactus");
		reeds = new BlockCustomReed().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Reeds")
				.setBlockTextureName("reeds");
		pumpkin = new BlockCustomPumpkin(false).setHardness(1.0F).setStepSound(Block.soundTypeWood)
				.setBlockName("Pumpkin").setBlockTextureName("pumpkin");
		melon = new BlockCustomMelon().setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("Melon")
				.setBlockTextureName("melon");
		litPumpkin = new BlockCustomPumpkin(true).setHardness(1.0F).setStepSound(Block.soundTypeWood)
				.setLightLevel(1.0F).setBlockName("LitPumpkin").setBlockTextureName("pumpkin");
		buttonWood = new BlockCustomButtonWood().setHardness(0.5F).setStepSound(Block.soundTypeWood)
				.setBlockName("ButtonWood");
		vine = new BlockCustomVine().setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("Vine")
				.setBlockTextureName("vine");

		// This is not used anywhere
		// Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.oak_stairs),
		// "oak_stairs", (new
		// BlockStair(Material.wood)).setBlockName("stairsWood"));

		/*
		 * Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.snow_layer)
		 * , "snow_layer", (new
		 * BlockCustomSnow()).setHardness(0.1F).setStepSound(Block.soundTypeSnow
		 * ).setBlockName("snow").setLightOpacity(1).setBlockTextureName("snow")
		 * );
		 */
		// Snow = (Block)Block.blockRegistry.getObject("snow_layer");
		snow = new BlockCustomSnow().setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow")
				.setLightOpacity(0).setBlockTextureName("snow");
		Blocks.snow_layer = snow;
		stoneIgInCobble = new BlockIgInCobble(Material.rock).setHardness(16F).setBlockName("IgInRockCobble");
		stoneIgIn = new BlockIgIn(Material.rock).setHardness(8F).setBlockName("IgInRock");
		stoneIgInSmooth = new BlockIgInSmooth().setHardness(16F).setBlockName("IgInRockSmooth");
		stoneIgInBrick = new BlockIgInBrick().setHardness(16F).setBlockName("IgInRockBrick");
		stoneIgInLargeBrick = new BlockIgInBrick().setHardness(16F).setBlockName("IgInRockLargeBrick");

		stoneSedCobble = new BlockSedCobble(Material.rock).setHardness(14F).setBlockName("SedRockCobble");
		stoneSed = new BlockSed(Material.rock).setHardness(7F).setBlockName("SedRock");
		stoneSedSmooth = new BlockSedSmooth().setHardness(14F).setBlockName("SedRockSmooth");
		stoneSedBrick = new BlockSedBrick().setHardness(14F).setBlockName("SedRockBrick");
		stoneSedLargeBrick = new BlockSedBrick().setHardness(14F).setBlockName("SedRockLargeBrick");

		stoneIgExCobble = new BlockIgExCobble(Material.rock).setHardness(16F).setBlockName("IgExRockCobble");
		stoneIgEx = new BlockIgEx(Material.rock).setHardness(8F).setBlockName("IgExRock");
		stoneIgExSmooth = new BlockIgExSmooth().setHardness(16F).setBlockName("IgExRockSmooth");
		stoneIgExBrick = new BlockIgExBrick().setHardness(16F).setBlockName("IgExRockBrick");
		stoneIgExLargeBrick = new BlockIgExBrick().setHardness(16F).setBlockName("IgExRockLargeBrick");

		stoneMMCobble = new BlockMMCobble(Material.rock).setHardness(15F).setBlockName("MMRockCobble");
		stoneMM = new BlockMM(Material.rock).setHardness(8F).setBlockName("MMRock");
		stoneMMSmooth = new BlockMMSmooth().setHardness(15F).setBlockName("MMRockSmooth");
		stoneMMBrick = new BlockMMBrick().setHardness(15F).setBlockName("MMRockBrick");
		stoneMMLargeBrick = new BlockMMBrick().setHardness(15F).setBlockName("MMRockLargeBrick");

		dirt = new BlockDirt(0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");

		dirt2 = new BlockDirt(16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		clay = new BlockClay(0).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		clay2 = new BlockClay(16).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		clayGrass = new BlockClayGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		clayGrass2 = new BlockClayGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass)
				.setBlockName("ClayGrass");
		grass = new BlockGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		grass2 = new BlockGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		peat = new BlockPeat().setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("Peat");
		peatGrass = new BlockPeatGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("PeatGrass");
		dryGrass = new BlockDryGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		dryGrass2 = new BlockDryGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		tallGrass = new BlockCustomTallGrass().setHardness(0.0F).setStepSound(Block.soundTypeGrass)
				.setBlockName("TallGrass");
		sand = new BlockSand(0).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");
		sand2 = new BlockSand(16).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");

		ore = new BlockOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		ore1b = new BlockOre(Material.rock).setDamageOffset(16).setHardness(10F).setResistance(10F).setBlockName("Ore");
		ore2 = new BlockOre2(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		ore3 = new BlockOre3(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		worldItem = new BlockWorldItem().setHardness(0.05F).setResistance(1F).setBlockName("LooseRock");
		sulfur = new BlockSulfur(Material.rock).setBlockName("Sulfur").setHardness(0.5F).setResistance(1F);

		logPile = new BlockLogPile().setHardness(10F).setResistance(1F).setBlockName("LogPile");
		woodSupportV = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportV").setHardness(0.5F)
				.setResistance(1F);
		woodSupportH = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportH").setHardness(0.5F)
				.setResistance(1F);
		woodSupportV2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportV2").setHardness(0.5F)
				.setResistance(1F);
		woodSupportH2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportH2").setHardness(0.5F)
				.setResistance(1F);

		tilledSoil = new BlockFarmland(TFCBlocks.dirt, 0).setHardness(2F).setStepSound(Block.soundTypeGravel)
				.setBlockName("tilledSoil");
		tilledSoil2 = new BlockFarmland(TFCBlocks.dirt2, 16).setHardness(2F).setStepSound(Block.soundTypeGravel)
				.setBlockName("tilledSoil");

		fruitTreeWood = new BlockFruitWood().setBlockName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
		fruitTreeLeaves = new BlockFruitLeaves(0).setBlockName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F)
				.setStepSound(Block.soundTypeGrass);
		fruitTreeLeaves2 = new BlockFruitLeaves(8).setBlockName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F)
				.setStepSound(Block.soundTypeGrass);

		woodSpear = new BlockWoodenSpear().setHardness(0f).setBlockName("Wooden Spear");
		teepee = new BlockTeepee().setHardness(0.5f).setBlockName("Teepee");
		wolfTeepee = new BlockTeepee().setHardness(0.5f).setBlockName("Wolf Teepee");
		bearTeepee = new BlockTeepee().setHardness(0.5f).setBlockName("Bear Teepee");
		
		invisibleBlock = new BlockInvisible(Material.glass).setHardness(0f).setBlockName("InvisibleBlock");
		
		woodConstruct = new BlockWoodConstruct().setHardness(4F).setStepSound(Block.soundTypeWood)
				.setBlockName("WoodConstruct");

		firepit = new BlockFirepit().setBlockName("Firepit").setHardness(1).setLightLevel(0F);
		bellows = new BlockBellows(Material.wood).setBlockName("Bellows").setHardness(2);
		forge = new BlockForge().setBlockName("Forge").setHardness(20).setLightLevel(0F);
		anvil = new BlockAnvil().setBlockName("Anvil").setHardness(3).setResistance(100F);
		anvil2 = new BlockAnvil(8).setBlockName("Anvil2").setHardness(3).setResistance(100F);

		molten = new BlockMolten().setBlockName("Molten").setHardness(20);
		blastFurnace = new BlockBlastFurnace().setBlockName("BlastFurnace").setHardness(20).setLightLevel(0F);
		bloomery = new BlockEarlyBloomery().setBlockName("EarlyBloomery").setHardness(20).setLightLevel(0F);
		bloom = new BlockBloom().setBlockName("Bloom").setHardness(20).setLightLevel(0F);
		sluice = new BlockSluice().setBlockName("Sluice").setHardness(2F).setResistance(20F);

		stoneStairs = new BlockStair(Material.rock).setBlockName("stoneStairs").setHardness(10);
		stoneSlabs = new BlockSlab().setBlockName("stoneSlabs").setHardness(10);
		stoneStalac = new BlockStalactite().setBlockName("stoneStalac").setHardness(5);

		charcoal = new BlockCharcoal().setHardness(3F).setResistance(10F).setBlockName("Charcoal");

		detailed = new BlockDetailed().setBlockName("StoneDetailed").setHardness(10);

		planks = new BlockPlanks(Material.wood).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood)
				.setBlockName("wood");
		planks2 = new BlockPlanks2(Material.wood).setHardness(4.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeWood).setBlockName("wood2");
		plasteredBlock = new BlockPlasteredBlock(Material.rock).setHardness(6.0F).setResistance(7.0F)
				.setStepSound(Block.soundTypeStone).setBlockName("plaster");
		leaves = new BlockCustomLeaves().setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass)
				.setBlockName("leaves").setCreativeTab(TFCTabs.TFC_DECORATION);
		leaves2 = new BlockCustomLeaves2().setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass)
				.setBlockName("leaves2");
		sapling = new BlockSapling().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling");
		sapling2 = new BlockSapling2().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling2");

		mudBricks = new BlockMudBricks(0).setDirt(TFCBlocks.dirt).setHardness(12.0F).setBlockName("mudBricks");
		mudBricks2 = new BlockMudBricks(16).setDirt(TFCBlocks.dirt2).setHardness(12.0F).setBlockName("mudBricks2");;
		
		cLights = new BlockL().setBlockName("Lights");
		
		dryingBricks = new BlockDryingBricks().setBlockName("dryingBricks");
		brickChimney = new BlockChimney().setHardness(8f).setBlockName("Brick Chimney");
		chimneyBricks = new BlockChimney(0).setHardness(12f).setBlockName("chimneyBricks");
		chimneyBricks2 = new BlockChimney(16).setHardness(12f).setBlockName("chimneyBricks2");
		chimneyLargeBricks = new BlockChimney(0).setHardness(12f).setBlockName("chimneyLargeBricks");
		chimneyLargeBricks2 = new BlockChimney(16).setHardness(12f).setBlockName("chimneyLargeBricks2");
		
		leafLitter = new BlockLeafLitter().setHardness(0.5F).setStepSound(Block.soundTypeGrass)
				.setBlockName("leafLitter");
		undergrowthPalm = new BlockUndergrowth().setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("undergrowth");
		undergrowth = new BlockUndergrowth().setHardness(1.0F).setStepSound(Block.soundTypeWood)
			.setBlockName("undergrowth");
		shrub = new BlockUndergrowth(Material.plants).setHardness(1.0F).setStepSound(Block.soundTypeWood)
			.setBlockName("shrub");
		lowUndergrowth = new BlockUndergrowth(Material.plants).setHardness(1.0F).setStepSound(Block.soundTypeGrass)
				.setBlockName("lowUndergrowth");
		fern = new BlockUndergrowth(Material.plants).setHardness(1.0F).setStepSound(Block.soundTypeGrass)
				.setBlockName("fern");

		logNatural = new BlockLogNatural().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log");
		logNatural2 = new BlockLogNatural2().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log2");
		skinnyLogNatural = new BlockSkinnyLogNatural().setHardness(30.0F).setStepSound(Block.soundTypeWood)
				.setBlockName("skinnyLog");
		skinnyLogNatural2 = new BlockSkinnyLogNatural2().setHardness(30.0F).setStepSound(Block.soundTypeWood)
				.setBlockName("skinnyLog2");
		woodVert = new BlockLogVert().setBlockName("WoodVert").setHardness(5).setResistance(15F)
				.setStepSound(Block.soundTypeWood);
		woodVert2 = new BlockLogVert2().setBlockName("WoodVert2").setHardness(5).setResistance(15F)
				.setStepSound(Block.soundTypeWood);
		woodHoriz = new BlockLogHoriz(0).setBlockName("WoodHoriz").setHardness(5).setResistance(15F)
				.setStepSound(Block.soundTypeWood);
		woodHoriz2 = new BlockLogHoriz(8).setBlockName("WoodHoriz2").setHardness(5).setResistance(15F)
				.setStepSound(Block.soundTypeWood);
		woodHoriz3 = new BlockLogHoriz2(0).setBlockName("WoodHoriz3").setHardness(5).setResistance(15F)
				.setStepSound(Block.soundTypeWood);
		// Use 8 instead of 0 if Global.WOOD_ALL.length > 24
		woodHoriz4 = new BlockLogHoriz2(8).setBlockName("WoodHoriz4").setHardness(5).setResistance(15F)
				.setStepSound(Block.soundTypeWood);
		stackedWoodVert = new BlockStackedLogVert().setBlockName("StackedWoodVert").setHardness(15).setResistance(15F)
		.setStepSound(Block.soundTypeWood);
		stackedWoodVert2 = new BlockStackedLogVert2().setBlockName("StackedWoodVert2").setHardness(15).setResistance(15F)
		.setStepSound(Block.soundTypeWood);
		stackedWoodHoriz = new BlockStackedLogHoriz(0).setBlockName("StackedWoodHoriz").setHardness(15).setResistance(15F)
		.setStepSound(Block.soundTypeWood);
		stackedWoodHoriz2 = new BlockStackedLogHoriz(8).setBlockName("StackedWoodHoriz2").setHardness(15).setResistance(15F)
		.setStepSound(Block.soundTypeWood);
		stackedWoodHoriz3 = new BlockStackedLogHoriz2(0).setBlockName("StackedWoodHoriz3").setHardness(15).setResistance(15F)
		.setStepSound(Block.soundTypeWood);
		stackedWoodHoriz4 = new BlockStackedLogHoriz2(8).setBlockName("StackedWoodHoriz4").setHardness(15).setResistance(15F)
		.setStepSound(Block.soundTypeWood);
		fauxPalm = new BlockFauxPalm().setBlockName("Fake Palm");

		/*
		 * branch_xyz = ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,-1,-1).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch_xyz"); branch_xyZ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,-1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_xyZ"); branch_xYz =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_xYz"); branch_xYZ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_xYZ"); branch_Xyz =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,-1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_Xyz"); branch_XyZ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,-1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_XyZ"); branch_XYz =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_XYz"); branch_XYZ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_XYZ"); branch_x_z =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,0,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_x_z"); branch_x_Z =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,0,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_x_Z"); branch_X_z =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,0,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_X_z"); branch_X_Z =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,0,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_X_Z"); branch__Yz =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch__Yz"); branch__YZ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch__YZ"); branch__yz =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,-1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch__yz"); branch__yZ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,-1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch__yZ"); branch___z =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,0,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch___z"); branch___Z =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,0,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch___Z"); branch_x__ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,0,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_x__"); branch_X__ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,0,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_X__"); branch_xy_ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,-1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_xy_"); branch_Xy_ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,-1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_Xy_"); branch_xY_ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(-1,1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_xY_"); branch_XY_ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(1,1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch_XY_"); branch__y_ =
		 * ((BlockBranch) new
		 * BlockBranch().setHardness(20.0f)).setSourceXYZ(0,-1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch__y_"); //2 branch2_xyz =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,-1,-1).
		 * setStepSound(Block.soundTypeWood).setBlockName("Branch2_xyz");
		 * branch2_xyZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,-1,1).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch2_xyZ"); branch2_xYz =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,1,-1).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch2_xYz"); branch2_xYZ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_xYZ"); branch2_Xyz =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,-1,-1).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch2_Xyz"); branch2_XyZ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,-1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_XyZ"); branch2_XYz =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_XYz"); branch2_XYZ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_XYZ"); branch2_x_z =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,0,-1).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch2_x_z"); branch2_x_Z =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,0,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_x_Z"); branch2_X_z =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,0,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_X_z"); branch2_X_Z =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,0,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_X_Z"); branch2__Yz =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,1,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2__Yz"); branch2__YZ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2__YZ"); branch2__yz =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,-1,-1).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch2__yz"); branch2__yZ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,-1,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2__yZ"); branch2___z =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,0,-1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2___z"); branch2___Z =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,0,1).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2___Z"); branch2_x__ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,0,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_x__"); branch2_X__ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,0,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_X__"); branch2_xy_ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,-1,0).setStepSound
		 * (Block.soundTypeWood).setBlockName("Branch2_xy_"); branch2_Xy_ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,-1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_Xy_"); branch2_xY_ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(-1,1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_xY_"); branch2_XY_ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(1,1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2_XY_"); branch2__y_ =
		 * ((BlockBranch) new
		 * BlockBranch2().setHardness(20.0f)).setSourceXYZ(0,-1,0).setStepSound(
		 * Block.soundTypeWood).setBlockName("Branch2__y_"); //Terminal branches
		 * branchEnd_xyz = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,-1,-1).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_xyz");
		 * branchEnd_xyZ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,-1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_xyZ");
		 * branchEnd_xYz = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_xYz");
		 * branchEnd_xYZ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_xYZ");
		 * branchEnd_Xyz = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,-1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_Xyz");
		 * branchEnd_XyZ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,-1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_XyZ");
		 * branchEnd_XYz = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_XYz");
		 * branchEnd_XYZ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_XYZ");
		 * branchEnd_x_z = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,0,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_x_z");
		 * branchEnd_x_Z = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,0,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_x_Z");
		 * branchEnd_X_z = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,0,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_X_z");
		 * branchEnd_X_Z = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,0,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_X_Z");
		 * branchEnd__Yz = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd__Yz");
		 * branchEnd__YZ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd__YZ");
		 * branchEnd__yz = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,-1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd__yz");
		 * branchEnd__yZ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,-1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd__yZ");
		 * branchEnd___z = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,0,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd___z");
		 * branchEnd___Z = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,0,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd___Z");
		 * branchEnd_x__ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,0,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_x__");
		 * branchEnd_X__ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,0,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_X__");
		 * branchEnd_xy_ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,-1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_xy_");
		 * branchEnd_Xy_ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,-1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_Xy_");
		 * branchEnd_xY_ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(-1,1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_xY_");
		 * branchEnd_XY_ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(1,1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd_XY_");
		 * branchEnd__y_ = ((BlockBranch) new
		 * BlockBranch().setHardness(10.0f)).setSourceXYZ(0,-1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd__y_"); //2
		 * branchEnd2_xyz = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,-1,-1).setEnd(true
		 * ).setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_xyz");
		 * branchEnd2_xyZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,-1,1).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_xyZ");
		 * branchEnd2_xYz = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,1,-1).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_xYz");
		 * branchEnd2_xYZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_xYZ");
		 * branchEnd2_Xyz = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,-1,-1).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_Xyz");
		 * branchEnd2_XyZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,-1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_XyZ");
		 * branchEnd2_XYz = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_XYz");
		 * branchEnd2_XYZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_XYZ");
		 * branchEnd2_x_z = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,0,-1).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_x_z");
		 * branchEnd2_x_Z = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,0,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_x_Z");
		 * branchEnd2_X_z = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,0,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_X_z");
		 * branchEnd2_X_Z = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,0,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_X_Z");
		 * branchEnd2__Yz = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,1,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2__Yz");
		 * branchEnd2__YZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2__YZ");
		 * branchEnd2__yz = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,-1,-1).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2__yz");
		 * branchEnd2__yZ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,-1,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2__yZ");
		 * branchEnd2___z = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,0,-1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2___z");
		 * branchEnd2___Z = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,0,1).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2___Z");
		 * branchEnd2_x__ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,0,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_x__");
		 * branchEnd2_X__ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,0,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_X__");
		 * branchEnd2_xy_ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,-1,0).setEnd(true)
		 * .setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_xy_");
		 * branchEnd2_Xy_ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,-1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_Xy_");
		 * branchEnd2_xY_ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(-1,1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_xY_");
		 * branchEnd2_XY_ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(1,1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2_XY_");
		 * branchEnd2__y_ = ((BlockBranch) new
		 * BlockBranch2().setHardness(10.0f)).setSourceXYZ(0,-1,0).setEnd(true).
		 * setStepSound(Block.soundTypeWood).setBlockName("BranchEnd2__y_");
		 */

		branch_xyz = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, -1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_xyZ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, -1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_xYz = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, 1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_xYZ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, 1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_Xyz = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, -1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_XyZ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, -1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_XYz = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, 1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_XYZ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, 1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_x_z = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, 0, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_x_Z = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, 0, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_X_z = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, 0, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_X_Z = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, 0, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch__Yz = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, 1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch__YZ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, 1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch__yz = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, -1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch__yZ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, -1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch___z = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, 0, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch___Z = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, 0, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_x__ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, 0, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_X__ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, 0, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_xy_ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, -1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_Xy_ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, -1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_xY_ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(-1, 1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch_XY_ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(1, 1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branch__y_ = ((BlockBranch) new BlockBranch().setHardness(30.0f)).setSourceXYZ(0, -1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		// 2
		branch2_xyz = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, -1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_xyZ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, -1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_xYz = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, 1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_xYZ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, 1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_Xyz = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, -1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_XyZ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, -1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_XYz = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, 1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_XYZ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, 1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_x_z = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, 0, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_x_Z = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, 0, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_X_z = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, 0, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_X_Z = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, 0, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2__Yz = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, 1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2__YZ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, 1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2__yz = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, -1, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2__yZ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, -1, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2___z = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, 0, -1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2___Z = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, 0, 1)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_x__ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, 0, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_X__ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, 0, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_xy_ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, -1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_Xy_ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, -1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_xY_ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(-1, 1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2_XY_ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(1, 1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branch2__y_ = ((BlockBranch) new BlockBranch2().setHardness(30.0f)).setSourceXYZ(0, -1, 0)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		// Terminal branches
		branchEnd_xyz = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, -1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_xyZ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, -1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_xYz = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, 1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_xYZ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, 1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_Xyz = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, -1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_XyZ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, -1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_XYz = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, 1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_XYZ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, 1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_x_z = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, 0, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_x_Z = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, 0, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_X_z = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, 0, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_X_Z = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, 0, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd__Yz = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, 1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd__YZ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, 1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd__yz = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, -1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd__yZ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, -1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd___z = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, 0, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd___Z = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, 0, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_x__ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, 0, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_X__ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, 0, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_xy_ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, -1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_Xy_ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, -1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_xY_ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(-1, 1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd_XY_ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(1, 1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		branchEnd__y_ = ((BlockBranch) new BlockBranch().setHardness(18.0f)).setSourceXYZ(0, -1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch");
		// 2
		branchEnd2_xyz = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, -1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_xyZ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, -1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_xYz = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, 1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_xYZ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, 1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_Xyz = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, -1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_XyZ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, -1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_XYz = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, 1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_XYZ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, 1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_x_z = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, 0, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_x_Z = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, 0, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_X_z = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, 0, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_X_Z = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, 0, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2__Yz = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, 1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2__YZ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, 1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2__yz = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, -1, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2__yZ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, -1, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2___z = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, 0, -1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2___Z = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, 0, 1).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_x__ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, 0, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_X__ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, 0, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_xy_ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, -1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_Xy_ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, -1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_xY_ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(-1, 1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2_XY_ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(1, 1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");
		branchEnd2__y_ = ((BlockBranch) new BlockBranch2().setHardness(18.0f)).setSourceXYZ(0, -1, 0).setEnd(true)
				.setStepSound(Block.soundTypeWood).setBlockName("Branch2");

		// The way this map works is that the X,Y,Z spaces are the directions in
		// which that branch is. N represents regular, 2, terminal, terminal 2
		// X, Y, Z, N
		TFC_Core.branchMap[0][0][0][0] = branch_xyz;
		TFC_Core.branchMap[0][0][2][0] = branch_xyZ;
		TFC_Core.branchMap[0][2][0][0] = branch_xYz;
		TFC_Core.branchMap[0][2][2][0] = branch_xYZ;
		TFC_Core.branchMap[2][0][0][0] = branch_Xyz;
		TFC_Core.branchMap[2][0][2][0] = branch_XyZ;
		TFC_Core.branchMap[2][2][0][0] = branch_XYz;
		TFC_Core.branchMap[2][2][2][0] = branch_XYZ;
		TFC_Core.branchMap[0][1][0][0] = branch_x_z;
		TFC_Core.branchMap[0][1][2][0] = branch_x_Z;
		TFC_Core.branchMap[2][1][0][0] = branch_X_z;
		TFC_Core.branchMap[2][1][2][0] = branch_X_Z;
		TFC_Core.branchMap[1][2][0][0] = branch__Yz;
		TFC_Core.branchMap[1][2][2][0] = branch__YZ;
		TFC_Core.branchMap[1][0][0][0] = branch__yz;
		TFC_Core.branchMap[1][0][2][0] = branch__yZ;
		TFC_Core.branchMap[1][1][0][0] = branch___z;
		TFC_Core.branchMap[1][1][2][0] = branch___Z;
		TFC_Core.branchMap[0][1][1][0] = branch_x__;
		TFC_Core.branchMap[2][1][1][0] = branch_X__;
		TFC_Core.branchMap[0][0][1][0] = branch_xy_;
		TFC_Core.branchMap[2][0][1][0] = branch_Xy_;
		TFC_Core.branchMap[0][2][1][0] = branch_xY_;
		TFC_Core.branchMap[2][2][1][0] = branch_XY_;
		TFC_Core.branchMap[1][0][1][0] = branch__y_;

		TFC_Core.branchMap[0][0][0][1] = branch2_xyz;
		TFC_Core.branchMap[0][0][2][1] = branch2_xyZ;
		TFC_Core.branchMap[0][2][0][1] = branch2_xYz;
		TFC_Core.branchMap[0][2][2][1] = branch2_xYZ;
		TFC_Core.branchMap[2][0][0][1] = branch2_Xyz;
		TFC_Core.branchMap[2][0][2][1] = branch2_XyZ;
		TFC_Core.branchMap[2][2][0][1] = branch2_XYz;
		TFC_Core.branchMap[2][2][2][1] = branch2_XYZ;
		TFC_Core.branchMap[0][1][0][1] = branch2_x_z;
		TFC_Core.branchMap[0][1][2][1] = branch2_x_Z;
		TFC_Core.branchMap[2][1][0][1] = branch2_X_z;
		TFC_Core.branchMap[2][1][2][1] = branch2_X_Z;
		TFC_Core.branchMap[1][2][0][1] = branch2__Yz;
		TFC_Core.branchMap[1][2][2][1] = branch2__YZ;
		TFC_Core.branchMap[1][0][0][1] = branch2__yz;
		TFC_Core.branchMap[1][0][2][1] = branch2__yZ;
		TFC_Core.branchMap[1][1][0][1] = branch2___z;
		TFC_Core.branchMap[1][1][2][1] = branch2___Z;
		TFC_Core.branchMap[0][1][1][1] = branch2_x__;
		TFC_Core.branchMap[2][1][1][1] = branch2_X__;
		TFC_Core.branchMap[0][0][1][1] = branch2_xy_;
		TFC_Core.branchMap[2][0][1][1] = branch2_Xy_;
		TFC_Core.branchMap[0][2][1][1] = branch2_xY_;
		TFC_Core.branchMap[2][2][1][1] = branch2_XY_;
		TFC_Core.branchMap[1][0][1][1] = branch2__y_;

		TFC_Core.branchMap[0][0][0][2] = branchEnd_xyz;
		TFC_Core.branchMap[0][0][2][2] = branchEnd_xyZ;
		TFC_Core.branchMap[0][2][0][2] = branchEnd_xYz;
		TFC_Core.branchMap[0][2][2][2] = branchEnd_xYZ;
		TFC_Core.branchMap[2][0][0][2] = branchEnd_Xyz;
		TFC_Core.branchMap[2][0][2][2] = branchEnd_XyZ;
		TFC_Core.branchMap[2][2][0][2] = branchEnd_XYz;
		TFC_Core.branchMap[2][2][2][2] = branchEnd_XYZ;
		TFC_Core.branchMap[0][1][0][2] = branchEnd_x_z;
		TFC_Core.branchMap[0][1][2][2] = branchEnd_x_Z;
		TFC_Core.branchMap[2][1][0][2] = branchEnd_X_z;
		TFC_Core.branchMap[2][1][2][2] = branchEnd_X_Z;
		TFC_Core.branchMap[1][2][0][2] = branchEnd__Yz;
		TFC_Core.branchMap[1][2][2][2] = branchEnd__YZ;
		TFC_Core.branchMap[1][0][0][2] = branchEnd__yz;
		TFC_Core.branchMap[1][0][2][2] = branchEnd__yZ;
		TFC_Core.branchMap[1][1][0][2] = branchEnd___z;
		TFC_Core.branchMap[1][1][2][2] = branchEnd___Z;
		TFC_Core.branchMap[0][1][1][2] = branchEnd_x__;
		TFC_Core.branchMap[2][1][1][2] = branchEnd_X__;
		TFC_Core.branchMap[0][0][1][2] = branchEnd_xy_;
		TFC_Core.branchMap[2][0][1][2] = branchEnd_Xy_;
		TFC_Core.branchMap[0][2][1][2] = branchEnd_xY_;
		TFC_Core.branchMap[2][2][1][2] = branchEnd_XY_;
		TFC_Core.branchMap[1][0][1][2] = branchEnd__y_;

		TFC_Core.branchMap[0][0][0][3] = branchEnd2_xyz;
		TFC_Core.branchMap[0][0][2][3] = branchEnd2_xyZ;
		TFC_Core.branchMap[0][2][0][3] = branchEnd2_xYz;
		TFC_Core.branchMap[0][2][2][3] = branchEnd2_xYZ;
		TFC_Core.branchMap[2][0][0][3] = branchEnd2_Xyz;
		TFC_Core.branchMap[2][0][2][3] = branchEnd2_XyZ;
		TFC_Core.branchMap[2][2][0][3] = branchEnd2_XYz;
		TFC_Core.branchMap[2][2][2][3] = branchEnd2_XYZ;
		TFC_Core.branchMap[0][1][0][3] = branchEnd2_x_z;
		TFC_Core.branchMap[0][1][2][3] = branchEnd2_x_Z;
		TFC_Core.branchMap[2][1][0][3] = branchEnd2_X_z;
		TFC_Core.branchMap[2][1][2][3] = branchEnd2_X_Z;
		TFC_Core.branchMap[1][2][0][3] = branchEnd2__Yz;
		TFC_Core.branchMap[1][2][2][3] = branchEnd2__YZ;
		TFC_Core.branchMap[1][0][0][3] = branchEnd2__yz;
		TFC_Core.branchMap[1][0][2][3] = branchEnd2__yZ;
		TFC_Core.branchMap[1][1][0][3] = branchEnd2___z;
		TFC_Core.branchMap[1][1][2][3] = branchEnd2___Z;
		TFC_Core.branchMap[0][1][1][3] = branchEnd2_x__;
		TFC_Core.branchMap[2][1][1][3] = branchEnd2_X__;
		TFC_Core.branchMap[0][0][1][3] = branchEnd2_xy_;
		TFC_Core.branchMap[2][0][1][3] = branchEnd2_Xy_;
		TFC_Core.branchMap[0][2][1][3] = branchEnd2_xY_;
		TFC_Core.branchMap[2][2][1][3] = branchEnd2_XY_;
		TFC_Core.branchMap[1][0][1][3] = branchEnd2__y_;

		toolRack = new BlockToolRack().setHardness(3F).setBlockName("Toolrack");
		spawnMeter = new BlockSpawnMeter().setHardness(3F).setBlockName("SpawnMeter");
		foodPrep = new BlockFoodPrep().setHardness(1F).setBlockName("FoodPrep");
		quern = new BlockQuern().setHardness(3F).setBlockName("Quern");

		wattle = new BlockWattle(Material.wood).setHardness(0.5f).setBlockName("Wattle Wall");
		wattleDaub = new BlockWattleDaub(Material.clay).setHardness(2f).setBlockName("Wattle and Daub");
		
		wallCobbleIgIn = new BlockCustomWall(stoneIgInCobble, 3).setBlockName("WallCobble");
		wallCobbleIgEx = new BlockCustomWall(stoneIgExCobble, 4).setBlockName("WallCobble");
		wallCobbleSed = new BlockCustomWall(stoneSedCobble, 8).setBlockName("WallCobble");
		wallCobbleMM = new BlockCustomWall(stoneMMCobble, 6).setBlockName("WallCobble");
		wallRawIgIn = new BlockCustomWall(stoneIgIn, 3).setBlockName("WallRaw");
		wallRawIgEx = new BlockCustomWall(stoneIgEx, 4).setBlockName("WallRaw");
		wallRawSed = new BlockCustomWall(stoneSed, 8).setBlockName("WallRaw");
		wallRawMM = new BlockCustomWall(stoneMM, 6).setBlockName("WallRaw");
		wallBrickIgIn = new BlockCustomWall(stoneIgInBrick, 3).setBlockName("WallBrick");
		wallBrickIgEx = new BlockCustomWall(stoneIgExBrick, 4).setBlockName("WallBrick");
		wallBrickSed = new BlockCustomWall(stoneSedBrick, 8).setBlockName("WallBrick");
		wallBrickMM = new BlockCustomWall(stoneMMBrick, 6).setBlockName("WallBrick");
		wallLargeBrickIgIn = new BlockCustomWall(stoneIgInLargeBrick, 3).setBlockName("WallBrick");
		wallLargeBrickIgEx = new BlockCustomWall(stoneIgExLargeBrick, 4).setBlockName("WallBrick");
		wallLargeBrickSed = new BlockCustomWall(stoneSedLargeBrick, 8).setBlockName("WallBrick");
		wallLargeBrickMM = new BlockCustomWall(stoneMMLargeBrick, 6).setBlockName("WallBrick");
		wallSmoothIgIn = new BlockCustomWall(stoneIgInSmooth, 3).setBlockName("WallSmooth");
		wallSmoothIgEx = new BlockCustomWall(stoneIgExSmooth, 4).setBlockName("WallSmooth");
		wallSmoothSed = new BlockCustomWall(stoneSedSmooth, 8).setBlockName("WallSmooth");
		wallSmoothMM = new BlockCustomWall(stoneMMSmooth, 6).setBlockName("WallSmooth");

		// Wooden Doors
		for (int i = 0; i < Global.WOOD_ALL.length; i++)
			doors[i] = new BlockCustomDoor(i * 2).setBlockName("Door " + Global.WOOD_ALL[i]);
		
		wattleDoor = new BlockCustomWattleDoor(0).setBlockName("Door Wattle");

		ingotPile = new BlockIngotPile().setBlockName("ingotpile").setHardness(3);

		bigDrum = new BlockDrum(true).setBlockName("BigDrum").setHardness(5);
		littleDrum = new BlockDrum().setBlockName("LittleDrum").setHardness(3);

		barrel = new BlockBarrel().setBlockName("Barrel").setHardness(2);
		loom = new BlockLoom().setBlockName("Loom").setHardness(2);
		primitiveLoom = new BlockLoom(true).setBlockName("Primitive Loom").setHardness(1);
		thatch = new BlockThatch().setBlockName("Thatch").setHardness(1).setStepSound(Block.soundTypeGrass)
				.setCreativeTab(TFCTabs.TFC_BUILDING);
		moss = new BlockMoss().setBlockName("Moss").setHardness(1).setStepSound(Block.soundTypeCloth);

		flora = new BlockFlora().setBlockName("Flora").setHardness(0.1f).setStepSound(Block.soundTypeGrass);
		pottery = new BlockPottery().setBlockName("Pottery").setHardness(1.0f);

		crucible = new BlockCrucible().setBlockName("Crucible").setHardness(4.0f);

		nestBox = new BlockNestBox().setBlockName("NestBox").setHardness(1);

		fence = new BlockTFCFence("Fence", Material.wood).setBlockName("FenceTFC").setHardness(2);
		fenceGate = new BlockCustomFenceGate().setBlockName("FenceGateTFC").setHardness(2);
		fence2 = new BlockTFCFence2("Fence2", Material.wood).setBlockName("FenceTFC").setHardness(2);
		fenceGate2 = new BlockCustomFenceGate2().setBlockName("FenceGateTFC").setHardness(2);
		strawHideBed = new BlockBed().setBlockName("StrawHideBed").setHardness(1).setCreativeTab(null);
		armorStand = new BlockStand().setBlockName("ArmourStand").setHardness(2);
		armorStand2 = new BlockStand2().setBlockName("ArmourStand").setHardness(2);

		berryBush = new BlockBerryBush().setBlockName("BerryBush").setHardness(0.3f).setStepSound(Block.soundTypeGrass);
		crops = new BlockCrop().setBlockName("crops").setHardness(0.3F).setStepSound(Block.soundTypeGrass);
		lilyPad = new BlockCustomLilyPad().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("LilyPad")
				.setBlockTextureName("waterlily");
		flowers = new BlockFlower().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers");
		flowers2 = new BlockFlower2().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers2");
		fungi = new BlockFungi().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Fungi");

		saltWater = new BlockSaltWater(TFCFluids.SALTWATER).setHardness(100.0F).setLightOpacity(3)
				.setBlockName("SaltWater");
		saltWaterStationary = new BlockLiquidStatic(TFCFluids.SALTWATER, Material.water, saltWater).setHardness(100.0F)
				.setLightOpacity(3).setBlockName("SaltWaterStationary");

		freshWater = new BlockFreshWater(TFCFluids.FRESHWATER).setHardness(100.0F).setLightOpacity(1)
				.setBlockName("FreshWater");
		freshWaterStationary = new BlockLiquidStatic(TFCFluids.FRESHWATER, Material.water, freshWater)
				.setHardness(100.0F).setLightOpacity(1).setBlockName("FreshWaterStationary");

		hotWater = new BlockHotWater(TFCFluids.HOTWATER).setHardness(100.0F).setLightOpacity(3)
				.setBlockName("HotWater");
		hotWaterStationary = new BlockHotWaterStatic(TFCFluids.HOTWATER, Material.water, hotWater).setHardness(100.0F)
				.setLightOpacity(3).setBlockName("HotWaterStationary");

		lava = new BlockLava().setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("Lava");
		lavaStationary = new BlockLiquidStatic(TFCFluids.LAVA, Material.lava, lava).setHardness(0.0F)
				.setLightLevel(1.0F).setLightOpacity(255).setBlockName("LavaStationary");
		ice = new BlockCustomIce().setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass)
				.setBlockName("Ice").setBlockTextureName("ice");

		waterPlant = new BlockWaterPlant(0).setBlockName("SeaGrassStill").setHardness(0.5f)
				.setStepSound(Block.soundTypeGravel);

		tileRoof = new BlockTileRoof().setBlockName("TileRoof").setHardness(8);
		thatchRoof = new BlockTileRoof(Material.grass).setBlockName("ThatchRoof").setHardness(2);
		slateRoof = new BlockTileRoof(Material.rock).setBlockName("SlateRoof").setHardness(12);
		
		fireBrick = new BlockFireBrick().setBlockName("FireBrick").setHardness(8);
		bricks = new BlockFireBrick().setBlockName("Brick").setHardness(8);
		metalSheet = new BlockMetalSheet().setBlockName("MetalSheet").setHardness(80);
		leatherRack = new BlockLeatherRack().setBlockName("LeatherRack").setHardness(1);

		gravel = new BlockGravel(0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("gravel");
		gravel2 = new BlockGravel(16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("gravel");

		grill = new BlockGrill().setHardness(2F).setBlockName("Grill");
		metalTrapDoor = new BlockMetalTrapDoor().setHardness(2F).setBlockName("MetalTrapDoor");
		vessel = new BlockLargeVessel().setHardness(1F).setBlockName("Vessel");
		basket = new BlockBasket().setHardness(0.7f).setStepSound(Block.soundTypeGrass).setBlockName("Basket");
		smoke = new BlockSmoke().setHardness(0F).setBlockName("Smoke");
		smokeRack = new BlockSmokeRack().setHardness(0F).setBlockName("SmokeRack");
		
		wildBeehive = new BlockBeehive(Material.grass).setHardness(2F).setBlockName("WildBeehive");
		wildTreehive = new BlockBeehive(Material.wood).setHardness(10f).setBlockName("WildTreehive");
		beehive = new BlockBeehive(Material.grass).setHardness(4F).setBlockName("Beehive");

		oilLamp = new BlockOilLamp().setHardness(1F).setBlockName("OilLamp");
		hopper = new BlockHopper().setHardness(2F).setBlockName("Hopper");
		stoneHopper = new BlockHopper().setHardness(2F).setBlockName("StoneHopper");
		flowerPot = new BlockCustomFlowerPot().setHardness(0.0F).setStepSound(Block.soundTypeStone)
				.setBlockName("FlowerPot").setBlockTextureName("flower_pot");

		stoneIgIn.setHarvestLevel("pickaxe", 0);
		stoneIgEx.setHarvestLevel("pickaxe", 0);
		stoneSed.setHarvestLevel("pickaxe", 0);
		stoneMM.setHarvestLevel("pickaxe", 0);
		stoneStalac.setHarvestLevel("pickaxe", 0);
		detailed.setHarvestLevel("pickaxe", 0);
		ore.setHarvestLevel("pickaxe", 1);
		ore2.setHarvestLevel("pickaxe", 1);
		ore3.setHarvestLevel("pickaxe", 1);

		dirt.setHarvestLevel("shovel", 0);
		dirt2.setHarvestLevel("shovel", 0);
		grass.setHarvestLevel("shovel", 0);
		grass2.setHarvestLevel("shovel", 0);
		dryGrass.setHarvestLevel("shovel", 0);
		dryGrass2.setHarvestLevel("shovel", 0);
		clay.setHarvestLevel("shovel", 0);
		clay2.setHarvestLevel("shovel", 0);
		clayGrass.setHarvestLevel("shovel", 0);
		clayGrass2.setHarvestLevel("shovel", 0);
		peat.setHarvestLevel("shovel", 0);
		peatGrass.setHarvestLevel("shovel", 0);
		sand.setHarvestLevel("shovel", 0);
		sand2.setHarvestLevel("shovel", 0);
		charcoal.setHarvestLevel("shovel", 0);
		gravel.setHarvestLevel("shovel", 0);
		gravel2.setHarvestLevel("shovel", 0);
		waterPlant.setHarvestLevel("shovel", 0);
		tilledSoil.setHarvestLevel("shovel", 0);
		tilledSoil2.setHarvestLevel("shovel", 0);

		detailed.setHarvestLevel("axe", 0);
		Blocks.oak_stairs.setHarvestLevel("axe", 0);
		woodConstruct.setHarvestLevel("axe", 0);

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 3; k++)
				{
					for (int m = 0; m < 4; m++)
					{
						if (TFC_Core.branchMap[i][j][k][m] != null)
						{
							TFC_Core.branchMap[i][j][k][m].setHarvestLevel("axe", 1);
						}
					}
				}
			}
		}

		logNatural.setHarvestLevel("axe", 1);
		logNatural2.setHarvestLevel("axe", 1);
		skinnyLogNatural.setHarvestLevel("axe", 1);
		skinnyLogNatural2.setHarvestLevel("axe", 1);
		woodHoriz.setHarvestLevel("axe", 1);
		woodHoriz2.setHarvestLevel("axe", 1);
		woodHoriz3.setHarvestLevel("axe", 1);
		woodHoriz4.setHarvestLevel("axe", 1);
		woodVert.setHarvestLevel("axe", 1);
		woodVert2.setHarvestLevel("axe", 1);

		skinnyLogNatural.setHarvestLevel("hammer", 1);
		skinnyLogNatural2.setHarvestLevel("hammer", 1);
		woodHoriz.setHarvestLevel("hammer", 1);
		woodHoriz2.setHarvestLevel("hammer", 1);
		woodHoriz3.setHarvestLevel("hammer", 1);
		woodHoriz4.setHarvestLevel("hammer", 1);
		woodVert.setHarvestLevel("hammer", 1);
		woodVert2.setHarvestLevel("hammer", 1);
	}

	public static void setupFire()
	{
		Blocks.fire.setFireInfo(logNatural, 5, 5);
		Blocks.fire.setFireInfo(logNatural2, 5, 5);
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 3; k++)
				{
					for (int m = 0; m < 4; m++)
					{
						if (TFC_Core.branchMap[i][j][k][m] != null)
						{
							Blocks.fire.setFireInfo(TFC_Core.branchMap[i][j][k][m], 5, 5);
						}
					}
				}
			}
		}
		Blocks.fire.setFireInfo(skinnyLogNatural, 5, 5);
		Blocks.fire.setFireInfo(skinnyLogNatural2, 5, 5);
		Blocks.fire.setFireInfo(woodSupportV, 5, 20);
		Blocks.fire.setFireInfo(woodSupportV2, 5, 20);
		Blocks.fire.setFireInfo(woodSupportH, 5, 20);
		Blocks.fire.setFireInfo(woodSupportH2, 5, 20);
		Blocks.fire.setFireInfo(leaves, 20, 20);
		Blocks.fire.setFireInfo(leaves2, 20, 20);
		Blocks.fire.setFireInfo(fruitTreeWood, 5, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves, 20, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves2, 20, 20);
		Blocks.fire.setFireInfo(fence, 5, 20);
		Blocks.fire.setFireInfo(wattle, 5, 20);
		Blocks.fire.setFireInfo(fence2, 5, 20);
		Blocks.fire.setFireInfo(fenceGate, 5, 20);
		Blocks.fire.setFireInfo(fenceGate2, 5, 20);
		Blocks.fire.setFireInfo(chest, 5, 20);
		Blocks.fire.setFireInfo(strawHideBed, 20, 20);
		Blocks.fire.setFireInfo(thatch, 20, 20);
		Blocks.fire.setFireInfo(thatchRoof, 20, 20);
		Blocks.fire.setFireInfo(woodVert, 5, 5);
		Blocks.fire.setFireInfo(woodVert2, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz2, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz3, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz4, 5, 5);
		Blocks.fire.setFireInfo(stackedWoodHoriz, 5, 5);
		Blocks.fire.setFireInfo(stackedWoodHoriz2, 5, 5);
		Blocks.fire.setFireInfo(stackedWoodHoriz3, 5, 5);
		Blocks.fire.setFireInfo(stackedWoodHoriz4, 5, 5);
		Blocks.fire.setFireInfo(planks, 5, 20);
		Blocks.fire.setFireInfo(planks2, 5, 20);
		Blocks.fire.setFireInfo(woodConstruct, 5, 20);
		Blocks.fire.setFireInfo(berryBush, 20, 20);
		Blocks.fire.setFireInfo(barrel, 5, 20);
		Blocks.fire.setFireInfo(bigDrum, 5, 20);
		Blocks.fire.setFireInfo(littleDrum, 5, 5);
		Blocks.fire.setFireInfo(crops, 20, 20);
		Blocks.fire.setFireInfo(logPile, 10, 10);
		Blocks.fire.setFireInfo(leafLitter, 30, 10);
		Blocks.fire.setFireInfo(shrub, 20, 20);
		// Blocks.fire.setFireInfo(Charcoal, 100, 20);
		for (int i = 0; i < Global.WOOD_ALL.length; i++)
			Blocks.fire.setFireInfo(doors[i], 5, 20);
		Blocks.fire.setFireInfo(wattleDoor, 20, 20);
	}
}
