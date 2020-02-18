package com.dunk.tfc;

import java.util.ArrayList;

import com.dunk.tfc.Core.Recipes;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.Metal.Alloy;
import com.dunk.tfc.Core.Metal.AlloyManager;
import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Food.ItemEgg;
import com.dunk.tfc.Food.ItemFoodMeat;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Food.ItemSalad;
import com.dunk.tfc.Food.ItemSandwich;
import com.dunk.tfc.Handlers.TFCFuelHandler;
import com.dunk.tfc.Items.ItemAlcohol;
import com.dunk.tfc.Items.ItemArrow;
import com.dunk.tfc.Items.ItemBandage;
import com.dunk.tfc.Items.ItemBasket;
import com.dunk.tfc.Items.ItemBloom;
import com.dunk.tfc.Items.ItemBlueprint;
import com.dunk.tfc.Items.ItemBone;
import com.dunk.tfc.Items.ItemBoots;
import com.dunk.tfc.Items.ItemBurlapSack;
import com.dunk.tfc.Items.ItemClay;
import com.dunk.tfc.Items.ItemCloak;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemClothingPiece;
import com.dunk.tfc.Items.ItemCoal;
import com.dunk.tfc.Items.ItemCoat;
import com.dunk.tfc.Items.ItemCustomLeash;
import com.dunk.tfc.Items.ItemCustomMinecart;
import com.dunk.tfc.Items.ItemCustomNameTag;
import com.dunk.tfc.Items.ItemCustomPotion;
import com.dunk.tfc.Items.ItemCustomSeeds;
import com.dunk.tfc.Items.ItemDrink;
import com.dunk.tfc.Items.ItemDyeCustom;
import com.dunk.tfc.Items.ItemFertilizer;
import com.dunk.tfc.Items.ItemFlatGeneric;
import com.dunk.tfc.Items.ItemFlute;
import com.dunk.tfc.Items.ItemFruitTreeSapling;
import com.dunk.tfc.Items.ItemGem;
import com.dunk.tfc.Items.ItemGlassBottle;
import com.dunk.tfc.Items.ItemGlassMaterial;
import com.dunk.tfc.Items.ItemHat;
import com.dunk.tfc.Items.ItemHoneyBowl;
import com.dunk.tfc.Items.ItemHoneycomb;
import com.dunk.tfc.Items.ItemHorn;
import com.dunk.tfc.Items.ItemIngot;
import com.dunk.tfc.Items.ItemLeather;
import com.dunk.tfc.Items.ItemLeatherBag;
import com.dunk.tfc.Items.ItemLogs;
import com.dunk.tfc.Items.ItemLooseRock;
import com.dunk.tfc.Items.ItemLyre;
import com.dunk.tfc.Items.ItemMeltedMetal;
import com.dunk.tfc.Items.ItemMetalSheet;
import com.dunk.tfc.Items.ItemMetalSheet2x;
import com.dunk.tfc.Items.ItemMud;
import com.dunk.tfc.Items.ItemMudBrick;
import com.dunk.tfc.Items.ItemOre;
import com.dunk.tfc.Items.ItemOreSmall;
import com.dunk.tfc.Items.ItemPants;
import com.dunk.tfc.Items.ItemPlank;
import com.dunk.tfc.Items.ItemPumpkinShell;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.Items.ItemRawHide;
import com.dunk.tfc.Items.ItemReeds;
import com.dunk.tfc.Items.ItemSaddleTFC;
import com.dunk.tfc.Items.ItemSeashell;
import com.dunk.tfc.Items.ItemShirt;
import com.dunk.tfc.Items.ItemSkirt;
import com.dunk.tfc.Items.ItemSluice;
import com.dunk.tfc.Items.ItemSocks;
import com.dunk.tfc.Items.ItemSoda;
import com.dunk.tfc.Items.ItemStick;
import com.dunk.tfc.Items.ItemStoneBrick;
import com.dunk.tfc.Items.ItemStraw;
import com.dunk.tfc.Items.ItemTFCArmor;
import com.dunk.tfc.Items.ItemTFCArmorBoots;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.Items.ItemThickLogs;
import com.dunk.tfc.Items.ItemTuyere;
import com.dunk.tfc.Items.ItemUnfinishedArmor;
import com.dunk.tfc.Items.ItemYarn;
import com.dunk.tfc.Items.ItemBlocks.ItemWoodDoor;
import com.dunk.tfc.Items.Pottery.ItemPotteryBase;
import com.dunk.tfc.Items.Pottery.ItemPotteryBlowpipe;
import com.dunk.tfc.Items.Pottery.ItemPotteryJug;
import com.dunk.tfc.Items.Pottery.ItemPotteryMold;
import com.dunk.tfc.Items.Pottery.ItemPotteryMoldBase;
import com.dunk.tfc.Items.Pottery.ItemPotterySheetMold;
import com.dunk.tfc.Items.Pottery.ItemPotterySmallVessel;
import com.dunk.tfc.Items.Tools.ItemChisel;
import com.dunk.tfc.Items.Tools.ItemCustomAxe;
import com.dunk.tfc.Items.Tools.ItemCustomBow;
import com.dunk.tfc.Items.Tools.ItemCustomBucket;
import com.dunk.tfc.Items.Tools.ItemCustomBucketHoney;
import com.dunk.tfc.Items.Tools.ItemCustomBucketMilk;
import com.dunk.tfc.Items.Tools.ItemCustomFishingRod;
import com.dunk.tfc.Items.Tools.ItemCustomHoe;
import com.dunk.tfc.Items.Tools.ItemCustomPickaxe;
import com.dunk.tfc.Items.Tools.ItemCustomSaw;
import com.dunk.tfc.Items.Tools.ItemCustomScythe;
import com.dunk.tfc.Items.Tools.ItemCustomShovel;
import com.dunk.tfc.Items.Tools.ItemCustomSword;
import com.dunk.tfc.Items.Tools.ItemFirestarter;
import com.dunk.tfc.Items.Tools.ItemFlintSteel;
import com.dunk.tfc.Items.Tools.ItemGoldPan;
import com.dunk.tfc.Items.Tools.ItemHammer;
import com.dunk.tfc.Items.Tools.ItemJavelin;
import com.dunk.tfc.Items.Tools.ItemKnife;
import com.dunk.tfc.Items.Tools.ItemMiscToolHead;
import com.dunk.tfc.Items.Tools.ItemNeedle;
import com.dunk.tfc.Items.Tools.ItemPlasterBucket;
import com.dunk.tfc.Items.Tools.ItemProPick;
import com.dunk.tfc.Items.Tools.ItemShears;
import com.dunk.tfc.Items.Tools.ItemSpindle;
import com.dunk.tfc.Items.Tools.ItemStaff;
import com.dunk.tfc.Items.Tools.ItemSteelBucketBlue;
import com.dunk.tfc.Items.Tools.ItemSteelBucketRed;
import com.dunk.tfc.Items.Tools.ItemTrowel;
import com.dunk.tfc.api.Armor;
import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSetup extends TFCItems
{

	public static void setup()
	{
		// Harvest Level Durability Mining Speed Damage Enchant
		// Tier 0
		igInToolMaterial = EnumHelper.addToolMaterial("IgIn", 1, igInStoneUses, igInStoneEff, 120, 5);
		sedToolMaterial = EnumHelper.addToolMaterial("Sed", 1, sedStoneUses, sedStoneEff, 100, 5);
		igExToolMaterial = EnumHelper.addToolMaterial("IgEx", 1, igExStoneUses, igExStoneEff, 115, 5);
		mMToolMaterial = EnumHelper.addToolMaterial("MM", 1, mMStoneUses, mMStoneEff, 110, 5);

		boneToolMaterial = EnumHelper.addToolMaterial("Bone", 0, boneUses, boneEff, 10, 3);
		woodToolMaterial = EnumHelper.addToolMaterial("Wood", 0, woodUses, boneEff, 75, 1);
		smallIronToolMaterial = EnumHelper.addToolMaterial("Small Iron", 0, wroughtIronUses, boneEff, 20, 3);

		// Tier 1
		copperToolMaterial = EnumHelper.addToolMaterial("Copper", 2, copperUses, copperEff, 200, 8);
		// Tier 2
		bronzeToolMaterial = EnumHelper.addToolMaterial("Bronze", 2, bronzeUses, bronzeEff, 400, 13);
		bismuthBronzeToolMaterial = EnumHelper.addToolMaterial("BismuthBronze", 2, bismuthBronzeUses, bismuthBronzeEff,
				375, 10);
		blackBronzeToolMaterial = EnumHelper.addToolMaterial("BlackBronze", 2, blackBronzeUses, blackBronzeEff, 372,
				10);
		// Tier 3
		ironToolMaterial = EnumHelper.addToolMaterial("Iron", 2, wroughtIronUses, wroughtIronEff, 500, 10);
		// Tier 4
		steelToolMaterial = EnumHelper.addToolMaterial("Steel", 2, steelUses, steelEff, 700, 10);
		// Tier 5
		blackSteelToolMaterial = EnumHelper.addToolMaterial("BlackSteel", 2, blackSteelUses, blackSteelEff, 800, 12);
		// Tier 6
		blueSteelToolMaterial = EnumHelper.addToolMaterial("BlueSteel", 3, blueSteelUses, blueSteelEff, 1000, 22);
		redSteelToolMaterial = EnumHelper.addToolMaterial("RedSteel", 3, redSteelUses, redSteelEff, 1000, 22);

		TerraFirmaCraft.LOG.info(new StringBuilder().append("Loading Items").toString());

		fishingRod = new ItemCustomFishingRod().setUnlocalizedName("fishingRod").setTextureName("tools/fishing_rod");
		coal = new ItemCoal().setUnlocalizedName("coal");
		stick = new ItemStick().setFull3D().setUnlocalizedName("stick");
		Items.stick = stick;
		bow = new ItemCustomBow().setUnlocalizedName("bow").setTextureName("tools/bow");
		compositeBow = new ItemCustomBow().setDamageMultiplier(1.5f).setSpeedMultiplier(0.8f).setUnlocalizedName("composite_bow").setTextureName("tools/composite_bow");
		unstrungBow = new ItemTerra().setUnlocalizedName("unstrung bow").setTextureName("tools/bow_unstrung");
		unstrungCompositeBow = new ItemTerra().setUnlocalizedName("unstrung comp bow").setTextureName("tools/comp_bow_unstrung");
		unstrungCompositeBow2 = new ItemTerra().setUnlocalizedName("unstrung comp bow2").setTextureName("tools/comp_bow_unstrung2");
		hornStrips = new ItemTerra().setUnlocalizedName("horn strips").setTextureName("hornStrips");
		pole = new ItemTerra().setReach(EnumItemReach.FAR).setUnlocalizedName("pole");
		
		
		stoneFlake = new ItemTerra().setUnlocalizedName("StoneFlake");
		fletching = new ItemTerra().setUnlocalizedName("Fletching");
		fletchedArrow = new ItemTerra().setUnlocalizedName("FletchedArrow");
		Items.bow = (ItemBow) bow;
		arrow = new ItemArrow().setUnlocalizedName("arrow").setCreativeTab(TFCTabs.TFC_WEAPONS);
		dye = new ItemDyeCustom().setUnlocalizedName("dyePowder").setTextureName("dye_powder")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		glassBottle = new ItemGlassBottle().setMetal(Global.GLASS).setUnlocalizedName("Glass Bottle");
		potion = new ItemCustomPotion().setUnlocalizedName("potion").setTextureName("potion");
		rope = new ItemCustomLeash().setUnlocalizedName("Rope").setCreativeTab(TFCTabs.TFC_TOOLS);
		Items.lead = rope;

		minecartCrate = new ItemCustomMinecart(1).setUnlocalizedName("minecartChest").setTextureName("minecart_chest");
		goldPan = new ItemGoldPan().setUnlocalizedName("GoldPan");
		sluiceItem = new ItemSluice().setFolder("devices/").setUnlocalizedName("SluiceItem")
				.setCreativeTab(TFCTabs.TFC_DEVICES);

		bone = new ItemBone().setReach(EnumItemReach.MEDIUM).setUnlocalizedName("Bone");

		boneFragment = new ItemTerra().setUnlocalizedName("Bone Fragment");

		shears = new ItemShears(0, ironToolMaterial).setUnlocalizedName("shears").setTextureName("shears");

		proPickBismuthBronze = new ItemProPick().setUnlocalizedName("Bismuth Bronze ProPick")
				.setMaxDamage(bismuthBronzeUses / 3);
		proPickBlackBronze = new ItemProPick().setUnlocalizedName("Black Bronze ProPick")
				.setMaxDamage(blackBronzeUses / 3);
		proPickBlackSteel = new ItemProPick().setUnlocalizedName("Black Steel ProPick")
				.setMaxDamage(blackSteelUses / 3);
		proPickBlueSteel = new ItemProPick().setUnlocalizedName("Blue Steel ProPick").setMaxDamage(blueSteelUses / 3);
		proPickBronze = new ItemProPick().setUnlocalizedName("Bronze ProPick").setMaxDamage(bronzeUses / 3);
		proPickCopper = new ItemProPick().setUnlocalizedName("Copper ProPick").setMaxDamage(copperUses / 3);
		proPickIron = new ItemProPick().setUnlocalizedName("Wrought Iron ProPick").setMaxDamage(wroughtIronUses / 3);
		proPickRedSteel = new ItemProPick().setUnlocalizedName("Red Steel ProPick").setMaxDamage(redSteelUses / 3);
		proPickSteel = new ItemProPick().setUnlocalizedName("Steel ProPick").setMaxDamage(steelUses / 3);

		trowelBismuthBronze = new ItemTrowel().setUnlocalizedName("Bismuth Bronze Trowel")
				.setMaxDamage(bismuthBronzeUses / 3);
		trowelBlackBronze = new ItemTrowel().setUnlocalizedName("Black Bronze Trowel")
				.setMaxDamage(blackBronzeUses / 3);
		trowelBlackSteel = new ItemTrowel().setUnlocalizedName("Black Steel Trowel").setMaxDamage(blackSteelUses / 3);
		trowelBlueSteel = new ItemTrowel().setUnlocalizedName("Blue Steel Trowel").setMaxDamage(blueSteelUses / 3);
		trowelBronze = new ItemTrowel().setUnlocalizedName("Bronze Trowel").setMaxDamage(bronzeUses / 3);
		trowelCopper = new ItemTrowel().setUnlocalizedName("Copper Trowel").setMaxDamage(copperUses / 3);
		trowelIron = new ItemTrowel().setUnlocalizedName("Wrought Iron Trowel").setMaxDamage(wroughtIronUses / 3);
		trowelRedSteel = new ItemTrowel().setUnlocalizedName("Red Steel Trowel").setMaxDamage(redSteelUses / 3);
		trowelSteel = new ItemTrowel().setUnlocalizedName("Steel Trowel").setMaxDamage(steelUses / 3);

		bismuthIngot = new ItemIngot().setUnlocalizedName("Bismuth Ingot");
		bismuthBronzeIngot = new ItemIngot().setUnlocalizedName("Bismuth Bronze Ingot");
		blackBronzeIngot = new ItemIngot().setUnlocalizedName("Black Bronze Ingot");
		blackSteelIngot = new ItemIngot().setUnlocalizedName("Black Steel Ingot");
		blueSteelIngot = new ItemIngot().setUnlocalizedName("Blue Steel Ingot");
		brassIngot = new ItemIngot().setUnlocalizedName("Brass Ingot");
		bronzeIngot = new ItemIngot().setUnlocalizedName("Bronze Ingot");
		copperIngot = new ItemIngot().setUnlocalizedName("Copper Ingot");
		goldIngot = new ItemIngot().setUnlocalizedName("Gold Ingot");
		wroughtIronIngot = new ItemIngot().setUnlocalizedName("Wrought Iron Ingot");
		leadIngot = new ItemIngot().setUnlocalizedName("Lead Ingot");
		nickelIngot = new ItemIngot().setUnlocalizedName("Nickel Ingot");
		pigIronIngot = new ItemIngot().setUnlocalizedName("Pig Iron Ingot");
		platinumIngot = new ItemIngot().setUnlocalizedName("Platinum Ingot");
		redSteelIngot = new ItemIngot().setUnlocalizedName("Red Steel Ingot");
		roseGoldIngot = new ItemIngot().setUnlocalizedName("Rose Gold Ingot");
		silverIngot = new ItemIngot().setUnlocalizedName("Silver Ingot");
		steelIngot = new ItemIngot().setUnlocalizedName("Steel Ingot");
		sterlingSilverIngot = new ItemIngot().setUnlocalizedName("Sterling Silver Ingot");
		tinIngot = new ItemIngot().setUnlocalizedName("Tin Ingot");
		zincIngot = new ItemIngot().setUnlocalizedName("Zinc Ingot");

		bismuthIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Bismuth Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Bismuth", 200);
		bismuthBronzeIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Bismuth Bronze Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Bismuth Bronze", 200);
		blackBronzeIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Black Bronze Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Black Bronze", 200);
		blackSteelIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Black Steel Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Black Steel", 200);
		blueSteelIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Blue Steel Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Blue Steel", 200);
		brassIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Brass Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Brass", 200);
		bronzeIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Bronze Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Bronze", 200);
		copperIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Copper Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Copper", 200);
		goldIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Gold Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Gold", 200);
		wroughtIronIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Wrought Iron Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Wrought Iron", 200);
		leadIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Lead Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Lead", 200);
		nickelIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Nickel Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Nickel", 200);
		pigIronIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Pig Iron Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Pig Iron", 200);
		platinumIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Platinum Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Platinum", 200);
		redSteelIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Red Steel Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Red Steel", 200);
		roseGoldIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Rose Gold Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Rose Gold", 200);
		silverIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Silver Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Silver", 200);
		steelIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Steel Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Steel", 200);
		sterlingSilverIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Sterling Silver Double Ingot"))
				.setSize(EnumSize.LARGE).setMetal("Sterling Silver", 200);
		tinIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Tin Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Tin", 200);
		zincIngot2x = ((ItemIngot) new ItemIngot().setUnlocalizedName("Zinc Double Ingot")).setSize(EnumSize.LARGE)
				.setMetal("Zinc", 200);

		gemRuby = new ItemGem().setUnlocalizedName("Ruby");
		gemSapphire = new ItemGem().setUnlocalizedName("Sapphire");
		gemEmerald = new ItemGem().setUnlocalizedName("Emerald");
		gemTopaz = new ItemGem().setUnlocalizedName("Topaz");
		gemTourmaline = new ItemGem().setUnlocalizedName("Tourmaline");
		gemJade = new ItemGem().setUnlocalizedName("Jade");
		gemBeryl = new ItemGem().setUnlocalizedName("Beryl");
		gemAgate = new ItemGem().setUnlocalizedName("Agate");
		gemOpal = new ItemGem().setUnlocalizedName("Opal");
		gemGarnet = new ItemGem().setUnlocalizedName("Garnet");
		gemJasper = new ItemGem().setUnlocalizedName("Jasper");
		gemAmethyst = new ItemGem().setUnlocalizedName("Amethyst");
		gemDiamond = new ItemGem().setUnlocalizedName("Diamond");

		// Tools
		igInShovel = new ItemCustomShovel(igInToolMaterial).setAttackSpeed(30).setUnlocalizedName("IgIn Stone Shovel")
				.setMaxDamage(igInStoneUses);
		igInAxe = new ItemCustomAxe(igInToolMaterial, igInToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("IgIn Stone Axe").setMaxDamage(igInStoneUses);
		igInHoe = new ItemCustomHoe(igInToolMaterial).setUnlocalizedName("IgIn Stone Hoe").setMaxDamage(igInStoneUses);

		sedShovel = new ItemCustomShovel(sedToolMaterial).setAttackSpeed(30).setUnlocalizedName("Sed Stone Shovel")
				.setMaxDamage(sedStoneUses);
		sedAxe = new ItemCustomAxe(sedToolMaterial, sedToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Sed Stone Axe").setMaxDamage(sedStoneUses);
		sedHoe = new ItemCustomHoe(sedToolMaterial).setUnlocalizedName("Sed Stone Hoe").setMaxDamage(sedStoneUses);

		igExShovel = new ItemCustomShovel(igExToolMaterial).setAttackSpeed(30).setUnlocalizedName("IgEx Stone Shovel")
				.setMaxDamage(igExStoneUses);
		igExAxe = new ItemCustomAxe(igExToolMaterial, igExToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("IgEx Stone Axe").setMaxDamage(igExStoneUses);
		igExHoe = new ItemCustomHoe(igExToolMaterial).setUnlocalizedName("IgEx Stone Hoe").setMaxDamage(igExStoneUses);

		mMShovel = new ItemCustomShovel(mMToolMaterial).setAttackSpeed(30).setUnlocalizedName("MM Stone Shovel").setMaxDamage(mMStoneUses);
		mMAxe = new ItemCustomAxe(mMToolMaterial, mMToolMaterial.getDamageVsEntity()).setAttackSpeed(17).setUnlocalizedName("MM Stone Axe")
				.setMaxDamage(mMStoneUses);
		mMHoe = new ItemCustomHoe(mMToolMaterial).setUnlocalizedName("MM Stone Hoe").setMaxDamage(mMStoneUses);

		bismuthBronzePick = new ItemCustomPickaxe(bismuthBronzeToolMaterial).setAttackSpeed(21).setUnlocalizedName("Bismuth Bronze Pick")
				.setMaxDamage(bismuthBronzeUses);
		bismuthBronzeShovel = new ItemCustomShovel(bismuthBronzeToolMaterial).setAttackSpeed(30)
				.setUnlocalizedName("Bismuth Bronze Shovel").setMaxDamage(bismuthBronzeUses);
		bismuthBronzeAxe = new ItemCustomAxe(bismuthBronzeToolMaterial, bismuthBronzeToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Bismuth Bronze Axe").setMaxDamage(bismuthBronzeUses);
		bismuthBronzeHoe = new ItemCustomHoe(bismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hoe")
				.setMaxDamage(bismuthBronzeUses);

		blackBronzePick = new ItemCustomPickaxe(blackBronzeToolMaterial).setAttackSpeed(21).setUnlocalizedName("Black Bronze Pick")
				.setMaxDamage(blackBronzeUses);
		blackBronzeShovel = new ItemCustomShovel(blackBronzeToolMaterial).setAttackSpeed(30).setUnlocalizedName("Black Bronze Shovel")
				.setMaxDamage(blackBronzeUses);
		blackBronzeAxe = new ItemCustomAxe(blackBronzeToolMaterial, blackBronzeToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Black Bronze Axe").setMaxDamage(blackBronzeUses);
		blackBronzeHoe = new ItemCustomHoe(blackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hoe")
				.setMaxDamage(blackBronzeUses);

		blackSteelPick = new ItemCustomPickaxe(blackSteelToolMaterial).setAttackSpeed(21).setUnlocalizedName("Black Steel Pick")
				.setMaxDamage(blackSteelUses);
		blackSteelShovel = new ItemCustomShovel(blackSteelToolMaterial).setAttackSpeed(30).setUnlocalizedName("Black Steel Shovel")
				.setMaxDamage(blackSteelUses);
		blackSteelAxe = new ItemCustomAxe(blackSteelToolMaterial, blackSteelToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Black Steel Axe").setMaxDamage(blackSteelUses);
		blackSteelHoe = new ItemCustomHoe(blackSteelToolMaterial).setUnlocalizedName("Black Steel Hoe")
				.setMaxDamage(blackSteelUses);

		blueSteelPick = new ItemCustomPickaxe(blueSteelToolMaterial).setAttackSpeed(21).setUnlocalizedName("Blue Steel Pick")
				.setMaxDamage(blueSteelUses);
		blueSteelShovel = new ItemCustomShovel(blueSteelToolMaterial).setAttackSpeed(30).setUnlocalizedName("Blue Steel Shovel")
				.setMaxDamage(blueSteelUses);
		blueSteelAxe = new ItemCustomAxe(blueSteelToolMaterial, blueSteelToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Blue Steel Axe").setMaxDamage(blueSteelUses);
		blueSteelHoe = new ItemCustomHoe(blueSteelToolMaterial).setUnlocalizedName("Blue Steel Hoe")
				.setMaxDamage(blueSteelUses);

		bronzePick = new ItemCustomPickaxe(bronzeToolMaterial).setAttackSpeed(21).setUnlocalizedName("Bronze Pick")
				.setMaxDamage(bronzeUses);
		bronzeShovel = new ItemCustomShovel(bronzeToolMaterial).setAttackSpeed(30).setUnlocalizedName("Bronze Shovel")
				.setMaxDamage(bronzeUses);
		bronzeAxe = new ItemCustomAxe(bronzeToolMaterial, bronzeToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Bronze Axe").setMaxDamage(bronzeUses);
		bronzeHoe = new ItemCustomHoe(bronzeToolMaterial).setUnlocalizedName("Bronze Hoe").setMaxDamage(bronzeUses);

		copperPick = new ItemCustomPickaxe(copperToolMaterial).setAttackSpeed(21).setUnlocalizedName("Copper Pick")
				.setMaxDamage(copperUses);
		copperShovel = new ItemCustomShovel(copperToolMaterial).setAttackSpeed(30).setUnlocalizedName("Copper Shovel")
				.setMaxDamage(copperUses);
		copperAxe = new ItemCustomAxe(copperToolMaterial, copperToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Copper Axe").setMaxDamage(copperUses);
		copperHoe = new ItemCustomHoe(copperToolMaterial).setUnlocalizedName("Copper Hoe").setMaxDamage(copperUses);

		wroughtIronPick = new ItemCustomPickaxe(ironToolMaterial).setAttackSpeed(21).setUnlocalizedName("Wrought Iron Pick")
				.setMaxDamage(wroughtIronUses);
		wroughtIronShovel = new ItemCustomShovel(ironToolMaterial).setAttackSpeed(30).setUnlocalizedName("Wrought Iron Shovel")
				.setMaxDamage(wroughtIronUses);
		wroughtIronAxe = new ItemCustomAxe(ironToolMaterial, ironToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Wrought Iron Axe").setMaxDamage(wroughtIronUses);
		wroughtIronHoe = new ItemCustomHoe(ironToolMaterial).setUnlocalizedName("Wrought Iron Hoe")
				.setMaxDamage(wroughtIronUses);

		redSteelPick = new ItemCustomPickaxe(redSteelToolMaterial).setAttackSpeed(21).setUnlocalizedName("Red Steel Pick")
				.setMaxDamage(redSteelUses);
		redSteelShovel = new ItemCustomShovel(redSteelToolMaterial).setAttackSpeed(30).setUnlocalizedName("Red Steel Shovel")
				.setMaxDamage(redSteelUses);
		redSteelAxe = new ItemCustomAxe(redSteelToolMaterial, redSteelToolMaterial.getDamageVsEntity()).setAttackSpeed(17).setUnlocalizedName("Red Steel Axe")
				.setMaxDamage(redSteelUses);
		redSteelHoe = new ItemCustomHoe(redSteelToolMaterial).setUnlocalizedName("Red Steel Hoe")
				.setMaxDamage(redSteelUses);

		steelPick = new ItemCustomPickaxe(steelToolMaterial).setAttackSpeed(21).setUnlocalizedName("Steel Pick").setMaxDamage(steelUses);
		steelShovel = new ItemCustomShovel(steelToolMaterial).setAttackSpeed(30).setUnlocalizedName("Steel Shovel")
				.setMaxDamage(steelUses);
		steelAxe = new ItemCustomAxe(steelToolMaterial, steelToolMaterial.getDamageVsEntity()).setAttackSpeed(17)
				.setUnlocalizedName("Steel Axe").setMaxDamage(steelUses);
		steelHoe = new ItemCustomHoe(steelToolMaterial).setUnlocalizedName("Steel Hoe").setMaxDamage(steelUses);

		// chisels
		bismuthBronzeChisel = new ItemChisel(bismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Chisel")
				.setMaxDamage(bismuthBronzeUses);
		blackBronzeChisel = new ItemChisel(blackBronzeToolMaterial).setUnlocalizedName("Black Bronze Chisel")
				.setMaxDamage(blackBronzeUses);
		blackSteelChisel = new ItemChisel(blackSteelToolMaterial).setUnlocalizedName("Black Steel Chisel")
				.setMaxDamage(blackSteelUses);
		blueSteelChisel = new ItemChisel(blueSteelToolMaterial).setUnlocalizedName("Blue Steel Chisel")
				.setMaxDamage(blueSteelUses);
		bronzeChisel = new ItemChisel(bronzeToolMaterial).setUnlocalizedName("Bronze Chisel").setMaxDamage(bronzeUses);
		copperChisel = new ItemChisel(copperToolMaterial).setUnlocalizedName("Copper Chisel").setMaxDamage(copperUses);
		wroughtIronChisel = new ItemChisel(ironToolMaterial).setUnlocalizedName("Wrought Iron Chisel")
				.setMaxDamage(wroughtIronUses);
		redSteelChisel = new ItemChisel(redSteelToolMaterial).setUnlocalizedName("Red Steel Chisel")
				.setMaxDamage(redSteelUses);
		steelChisel = new ItemChisel(steelToolMaterial).setUnlocalizedName("Steel Chisel").setMaxDamage(steelUses);

		bismuthBronzeSword = new ItemCustomSword(bismuthBronzeToolMaterial,
				bismuthBronzeToolMaterial.getDamageVsEntity()).setAttackSpeed(15).setUnlocalizedName("Bismuth Bronze Sword")
						.setMaxDamage(bismuthBronzeUses);
		blackBronzeSword = new ItemCustomSword(blackBronzeToolMaterial, blackBronzeToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Black Bronze Sword").setMaxDamage(blackBronzeUses);
		blackSteelSword = new ItemCustomSword(blackSteelToolMaterial, blackSteelToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Black Steel Sword").setMaxDamage(blackSteelUses);
		blueSteelSword = new ItemCustomSword(blueSteelToolMaterial, blueSteelToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Blue Steel Sword").setMaxDamage(blueSteelUses);
		bronzeSword = new ItemCustomSword(bronzeToolMaterial, bronzeToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Bronze Sword").setMaxDamage(bronzeUses);
		copperSword = new ItemCustomSword(copperToolMaterial, copperToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Copper Sword").setMaxDamage(copperUses);
		wroughtIronSword = new ItemCustomSword(ironToolMaterial, ironToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Wrought Iron Sword").setMaxDamage(wroughtIronUses);
		redSteelSword = new ItemCustomSword(redSteelToolMaterial, redSteelToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Red Steel Sword").setMaxDamage(redSteelUses);
		steelSword = new ItemCustomSword(steelToolMaterial, steelToolMaterial.getDamageVsEntity()).setAttackSpeed(15)
				.setUnlocalizedName("Steel Sword").setMaxDamage(steelUses);

		bismuthBronzeMace = new ItemCustomSword(bismuthBronzeToolMaterial,
				bismuthBronzeToolMaterial.getDamageVsEntity(), EnumDamageType.CRUSHING).setAttackSpeed(19)
						.setUnlocalizedName("Bismuth Bronze Mace").setMaxDamage(bismuthBronzeUses);
		blackBronzeMace = new ItemCustomSword(blackBronzeToolMaterial, blackBronzeToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(25).setUnlocalizedName("Black Bronze Mace").setMaxDamage(blackBronzeUses);
		blackSteelMace = new ItemCustomSword(blackSteelToolMaterial, blackSteelToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Black Steel Mace").setMaxDamage(blackSteelUses);
		blueSteelMace = new ItemCustomSword(blueSteelToolMaterial, blueSteelToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Blue Steel Mace").setMaxDamage(blueSteelUses);
		bronzeMace = new ItemCustomSword(bronzeToolMaterial, bronzeToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Bronze Mace").setMaxDamage(bronzeUses);
		copperMace = new ItemCustomSword(copperToolMaterial, copperToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Copper Mace").setMaxDamage(copperUses);
		wroughtIronMace = new ItemCustomSword(ironToolMaterial, ironToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Wrought Iron Mace").setMaxDamage(wroughtIronUses);
		redSteelMace = new ItemCustomSword(redSteelToolMaterial, redSteelToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Red Steel Mace").setMaxDamage(redSteelUses);
		steelMace = new ItemCustomSword(steelToolMaterial, steelToolMaterial.getDamageVsEntity(),
				EnumDamageType.CRUSHING).setAttackSpeed(19).setUnlocalizedName("Steel Mace").setMaxDamage(steelUses);

		bismuthBronzeSaw = new ItemCustomSaw(bismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Saw")
				.setMaxDamage(bismuthBronzeUses);
		blackBronzeSaw = new ItemCustomSaw(blackBronzeToolMaterial).setUnlocalizedName("Black Bronze Saw")
				.setMaxDamage(blackBronzeUses);
		blackSteelSaw = new ItemCustomSaw(blackSteelToolMaterial).setUnlocalizedName("Black Steel Saw")
				.setMaxDamage(blackSteelUses);
		blueSteelSaw = new ItemCustomSaw(blueSteelToolMaterial).setUnlocalizedName("Blue Steel Saw")
				.setMaxDamage(blueSteelUses);
		bronzeSaw = new ItemCustomSaw(bronzeToolMaterial).setUnlocalizedName("Bronze Saw").setMaxDamage(bronzeUses);
		copperSaw = new ItemCustomSaw(copperToolMaterial).setUnlocalizedName("Copper Saw").setMaxDamage(copperUses);
		wroughtIronSaw = new ItemCustomSaw(ironToolMaterial).setUnlocalizedName("Wrought Iron Saw")
				.setMaxDamage(wroughtIronUses);
		redSteelSaw = new ItemCustomSaw(redSteelToolMaterial).setUnlocalizedName("Red Steel Saw")
				.setMaxDamage(redSteelUses);
		steelSaw = new ItemCustomSaw(steelToolMaterial).setUnlocalizedName("Steel Saw").setMaxDamage(steelUses);

		highCarbonBlackSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Black Steel Ingot");
		weakBlueSteelIngot = new ItemIngot(false).setUnlocalizedName("Weak Blue Steel Ingot");
		weakRedSteelIngot = new ItemIngot(false).setUnlocalizedName("Weak Red Steel Ingot");
		weakSteelIngot = new ItemIngot(false).setUnlocalizedName("Weak Steel Ingot");
		highCarbonBlueSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Blue Steel Ingot");
		highCarbonRedSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Red Steel Ingot");
		highCarbonSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Steel Ingot");

		oreChunk = new ItemOre().setFolder("ore/").setUnlocalizedName("Ore");
		smallOreChunk = new ItemOreSmall().setUnlocalizedName("Small Ore");
		powder = new ItemTerra().setMetaNames(Global.POWDER).setUnlocalizedName("Powder")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);

		// Glassmaking
		moltenSilica = new ItemTerra().setMetal(Global.SILICA).setUnlocalizedName("Liquid Silica");
		moltenLime = new ItemTerra().setMetal(Global.LIME).setUnlocalizedName("Liquid Lime");
		moltenGlass = new ItemTerra().setMetal(Global.GLASS).setUnlocalizedName("Molten Glass");
		moltenSoda = new ItemTerra().setMetal(Global.SODA).setUnlocalizedName("Liquid Soda");
		solidifiedGlass = new ItemTerra().setMetal(Global.GLASS).setUnlocalizedName("Glass");
		lime = new ItemGlassMaterial().setMetal(Global.LIME).setUnlocalizedName("Lime");
		soda = new ItemGlassMaterial().setMetal(Global.SODA).setUnlocalizedName("Soda");
		silica = new ItemTerra().setMetal(Global.SILICA).setUnlocalizedName("Silica");

		seashell = new ItemSeashell().setUnlocalizedName("seashell");

		logs = new ItemLogs().setUnlocalizedName("Log");
		thickLogs = new ItemThickLogs().setUnlocalizedName("Thick Log");
		stackedLogs = new ItemThickLogs().setUnlocalizedName("Stacked Log");

		// javelins
		woodenSpear = new ItemJavelin(woodToolMaterial,40).setAttackSpeed(12).setUnlocalizedName("Wooden Spear");
		igInStoneJavelin = new ItemJavelin(igInToolMaterial, 260).setAttackSpeed(12).setUnlocalizedName("IgIn Stone Javelin");
		sedStoneJavelin = new ItemJavelin(sedToolMaterial, 260).setAttackSpeed(12).setUnlocalizedName("Sed Stone Javelin");
		igExStoneJavelin = new ItemJavelin(igExToolMaterial, 260).setAttackSpeed(12).setUnlocalizedName("IgEx Stone Javelin");
		mMStoneJavelin = new ItemJavelin(mMToolMaterial, 260).setAttackSpeed(12).setUnlocalizedName("MM Stone Javelin");
		copperJavelin = new ItemJavelin(copperToolMaterial, 280).setAttackSpeed(12).setUnlocalizedName("Copper Javelin");
		bismuthBronzeJavelin = new ItemJavelin(bismuthBronzeToolMaterial, 290).setAttackSpeed(12)
				.setUnlocalizedName("Bismuth Bronze Javelin");
		bronzeJavelin = new ItemJavelin(bronzeToolMaterial, 300).setAttackSpeed(12).setUnlocalizedName("Bronze Javelin");
		blackBronzeJavelin = new ItemJavelin(blackBronzeToolMaterial, 295).setAttackSpeed(12).setUnlocalizedName("Black Bronze Javelin");
		wroughtIronJavelin = new ItemJavelin(ironToolMaterial, 335).setAttackSpeed(12).setUnlocalizedName("Wrought Iron Javelin");
		steelJavelin = new ItemJavelin(steelToolMaterial, 370).setAttackSpeed(12).setUnlocalizedName("Steel Javelin");
		blackSteelJavelin = new ItemJavelin(blackSteelToolMaterial, 505).setAttackSpeed(12).setUnlocalizedName("Black Steel Javelin");
		blueSteelJavelin = new ItemJavelin(blueSteelToolMaterial, 540).setAttackSpeed(12).setUnlocalizedName("Blue Steel Javelin");
		redSteelJavelin = new ItemJavelin(redSteelToolMaterial, 540).setAttackSpeed(12).setUnlocalizedName("Red Steel Javelin");

		// javelin heads
		igInStoneJavelinHead = new ItemMiscToolHead(igInToolMaterial).setUnlocalizedName("IgIn Stone Javelin Head");
		sedStoneJavelinHead = new ItemMiscToolHead(sedToolMaterial).setUnlocalizedName("Sed Stone Javelin Head");
		igExStoneJavelinHead = new ItemMiscToolHead(igExToolMaterial).setUnlocalizedName("IgEx Stone Javelin Head");
		mMStoneJavelinHead = new ItemMiscToolHead(mMToolMaterial).setUnlocalizedName("MM Stone Javelin Head");
		copperJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Copper Javelin Head");
		bismuthBronzeJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Javelin Head");
		bronzeJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Javelin Head");
		blackBronzeJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Javelin Head");
		wroughtIronJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Javelin Head");
		steelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Steel Javelin Head");
		blackSteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Javelin Head");
		blueSteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Javelin Head");
		redSteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Javelin Head");

		bismuthUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Bismuth Unshaped");
		bismuthBronzeUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Bismuth Bronze Unshaped");
		blackBronzeUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Black Bronze Unshaped");
		blackSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Black Steel Unshaped");
		blueSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Blue Steel Unshaped");
		brassUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Brass Unshaped");
		bronzeUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Bronze Unshaped");
		copperUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Copper Unshaped");
		goldUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Gold Unshaped");
		wroughtIronUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Wrought Iron Unshaped");
		leadUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Lead Unshaped");
		nickelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Nickel Unshaped");
		pigIronUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Pig Iron Unshaped");
		platinumUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Platinum Unshaped");
		redSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Red Steel Unshaped");
		roseGoldUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Rose Gold Unshaped");
		silverUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Silver Unshaped");
		steelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Steel Unshaped");
		sterlingSilverUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Sterling Silver Unshaped");
		tinUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Tin Unshaped");
		zincUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Zinc Unshaped");

		woodenStaff = new ItemStaff(woodToolMaterial,60).setAttackSpeed(20).setUnlocalizedName("Wooden Staff");
		
		// Hammers
		stoneHammer = new ItemHammer(igInToolMaterial, 60).setAttackSpeed(30).setUnlocalizedName("Stone Hammer")
				.setMaxDamage(igInStoneUses);
		bismuthBronzeHammer = new ItemHammer(bismuthBronzeToolMaterial, 90).setAttackSpeed(30).setUnlocalizedName("Bismuth Bronze Hammer")
				.setMaxDamage(bismuthBronzeUses);
		blackBronzeHammer = new ItemHammer(blackBronzeToolMaterial, 95).setAttackSpeed(30).setUnlocalizedName("Black Bronze Hammer")
				.setMaxDamage(blackBronzeUses);
		blackSteelHammer = new ItemHammer(blackSteelToolMaterial, 205).setAttackSpeed(30).setUnlocalizedName("Black Steel Hammer")
				.setMaxDamage(blackSteelUses);
		blueSteelHammer = new ItemHammer(blueSteelToolMaterial, 240).setAttackSpeed(30).setUnlocalizedName("Blue Steel Hammer")
				.setMaxDamage(blueSteelUses);
		bronzeHammer = new ItemHammer(bronzeToolMaterial, 100).setAttackSpeed(30).setUnlocalizedName("Bronze Hammer")
				.setMaxDamage(bronzeUses);
		copperHammer = new ItemHammer(copperToolMaterial, 80).setAttackSpeed(30).setUnlocalizedName("Copper Hammer")
				.setMaxDamage(copperUses);
		wroughtIronHammer = new ItemHammer(ironToolMaterial, 135).setAttackSpeed(30).setUnlocalizedName("Wrought Iron Hammer")
				.setMaxDamage(wroughtIronUses);
		redSteelHammer = new ItemHammer(redSteelToolMaterial, 240).setAttackSpeed(30).setUnlocalizedName("Red Steel Hammer")
				.setMaxDamage(redSteelUses);
		steelHammer = new ItemHammer(steelToolMaterial, 170).setAttackSpeed(30).setUnlocalizedName("Steel Hammer").setMaxDamage(steelUses);

		ink = new ItemTerra().setUnlocalizedName("Ink").setCreativeTab(TFCTabs.TFC_MATERIALS);
		fireStarter = new ItemFirestarter().setFolder("tools/").setUnlocalizedName("Firestarter");
		bowFireStarter = new ItemFirestarter(50).setChance(2).setFolder("tools/").setUnlocalizedName("Bow Firestarter");

		// Tool heads
		bismuthBronzePickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Pick Head");
		blackBronzePickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Pick Head");
		blackSteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Pick Head");
		blueSteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Pick Head");
		bronzePickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Pick Head");
		copperPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Pick Head");
		wroughtIronPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Pick Head");
		redSteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Pick Head");
		steelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Pick Head");

		bismuthBronzeShovelHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Shovel Head");
		blackBronzeShovelHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Shovel Head");
		blackSteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Shovel Head");
		blueSteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Shovel Head");
		bronzeShovelHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Shovel Head");
		copperShovelHead = new ItemMiscToolHead().setUnlocalizedName("Copper Shovel Head");
		wroughtIronShovelHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Shovel Head");
		redSteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Shovel Head");
		steelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Steel Shovel Head");

		bismuthBronzeHoeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Hoe Head");
		blackBronzeHoeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Hoe Head");
		blackSteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Hoe Head");
		blueSteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Hoe Head");
		bronzeHoeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Hoe Head");
		copperHoeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Hoe Head");
		wroughtIronHoeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Hoe Head");
		redSteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Hoe Head");
		steelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Hoe Head");

		bismuthBronzeAxeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Axe Head");
		blackBronzeAxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Axe Head");
		blackSteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Axe Head");
		blueSteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Axe Head");
		bronzeAxeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Axe Head");
		copperAxeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Axe Head");
		wroughtIronAxeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Axe Head");
		redSteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Axe Head");
		steelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Axe Head");

		bismuthBronzeHammerHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Hammer Head");
		blackBronzeHammerHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Hammer Head");
		blackSteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Hammer Head");
		blueSteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Hammer Head");
		bronzeHammerHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Hammer Head");
		copperHammerHead = new ItemMiscToolHead().setUnlocalizedName("Copper Hammer Head");
		wroughtIronHammerHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Hammer Head");
		redSteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Hammer Head");
		steelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Steel Hammer Head");

		// chisel heads
		bismuthBronzeChiselHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Chisel Head");
		blackBronzeChiselHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Chisel Head");
		blackSteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Chisel Head");
		blueSteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Chisel Head");
		bronzeChiselHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Chisel Head");
		copperChiselHead = new ItemMiscToolHead().setUnlocalizedName("Copper Chisel Head");
		wroughtIronChiselHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Chisel Head");
		redSteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Chisel Head");
		steelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Steel Chisel Head");

		bismuthBronzeSwordHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Sword Blade");
		blackBronzeSwordHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Sword Blade");
		blackSteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Sword Blade");
		blueSteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Sword Blade");
		bronzeSwordHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Sword Blade");
		copperSwordHead = new ItemMiscToolHead().setUnlocalizedName("Copper Sword Blade");
		wroughtIronSwordHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Sword Blade");
		redSteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Sword Blade");
		steelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Steel Sword Blade");

		bismuthBronzeMaceHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Mace Head");
		blackBronzeMaceHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Mace Head");
		blackSteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Mace Head");
		blueSteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Mace Head");
		bronzeMaceHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Mace Head");
		copperMaceHead = new ItemMiscToolHead().setUnlocalizedName("Copper Mace Head");
		wroughtIronMaceHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Mace Head");
		redSteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Mace Head");
		steelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Steel Mace Head");

		bismuthBronzeSawHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Saw Blade");
		blackBronzeSawHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Saw Blade");
		blackSteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Saw Blade");
		blueSteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Saw Blade");
		bronzeSawHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Saw Blade");
		copperSawHead = new ItemMiscToolHead().setUnlocalizedName("Copper Saw Blade");
		wroughtIronSawHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Saw Blade");
		redSteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Saw Blade");
		steelSawHead = new ItemMiscToolHead().setUnlocalizedName("Steel Saw Blade");

		highCarbonBlackSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("HC Black Steel Unshaped");
		weakBlueSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Weak Blue Steel Unshaped");
		highCarbonBlueSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("HC Blue Steel Unshaped");
		weakRedSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Weak Red Steel Unshaped");
		highCarbonRedSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("HC Red Steel Unshaped");
		weakSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("Weak Steel Unshaped");
		highCarbonSteelUnshaped = new ItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(0).setUnlocalizedName("HC Steel Unshaped");
		// Coke = new
		// ItemTerra().setUnlocalizedName("Coke").setCreativeTab(null);

		bismuthBronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze ProPick Head");
		blackBronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze ProPick Head");
		blackSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel ProPick Head");
		blueSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel ProPick Head");
		bronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Bronze ProPick Head");
		copperProPickHead = new ItemMiscToolHead().setUnlocalizedName("Copper ProPick Head");
		wroughtIronProPickHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron ProPick Head");
		redSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel ProPick Head");
		steelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Steel ProPick Head");

		bismuthBronzeTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Trowel Head");
		blackBronzeTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Trowel Head");
		blackSteelTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Trowel Head");
		blueSteelTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Trowel Head");
		bronzeTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Trowel Head");
		copperTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Copper Trowel Head");
		wroughtIronTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Trowel Head");
		redSteelTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Trowel Head");
		steelTrowelHead = new ItemMiscToolHead().setUnlocalizedName("Steel Trowel Head");

		/**
		 * Scythe
		 */
		bismuthBronzeScythe = new ItemCustomScythe(bismuthBronzeToolMaterial).setAttackSpeed(22)
				.setUnlocalizedName("Bismuth Bronze Scythe").setMaxDamage(bismuthBronzeUses);
		blackBronzeScythe = new ItemCustomScythe(blackBronzeToolMaterial).setAttackSpeed(22).setUnlocalizedName("Black Bronze Scythe")
				.setMaxDamage(blackBronzeUses);
		blackSteelScythe = new ItemCustomScythe(blackSteelToolMaterial).setAttackSpeed(22).setUnlocalizedName("Black Steel Scythe")
				.setMaxDamage(blackSteelUses);
		blueSteelScythe = new ItemCustomScythe(blueSteelToolMaterial).setAttackSpeed(22).setUnlocalizedName("Blue Steel Scythe")
				.setMaxDamage(blueSteelUses);
		bronzeScythe = new ItemCustomScythe(bronzeToolMaterial).setAttackSpeed(22).setUnlocalizedName("Bronze Scythe")
				.setMaxDamage(bronzeUses);
		copperScythe = new ItemCustomScythe(copperToolMaterial).setAttackSpeed(22).setUnlocalizedName("Copper Scythe")
				.setMaxDamage(copperUses);
		wroughtIronScythe = new ItemCustomScythe(ironToolMaterial).setAttackSpeed(22).setUnlocalizedName("Wrought Iron Scythe")
				.setMaxDamage(wroughtIronUses);
		redSteelScythe = new ItemCustomScythe(redSteelToolMaterial).setAttackSpeed(22).setUnlocalizedName("Red Steel Scythe")
				.setMaxDamage(redSteelUses);
		steelScythe = new ItemCustomScythe(steelToolMaterial).setAttackSpeed(22).setUnlocalizedName("Steel Scythe")
				.setMaxDamage(steelUses);

		bismuthBronzeScytheHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Scythe Blade");
		blackBronzeScytheHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Scythe Blade");
		blackSteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Scythe Blade");
		blueSteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Scythe Blade");
		bronzeScytheHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Scythe Blade");
		copperScytheHead = new ItemMiscToolHead().setUnlocalizedName("Copper Scythe Blade");
		wroughtIronScytheHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Scythe Blade");
		redSteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Scythe Blade");
		steelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Steel Scythe Blade");

		mudBrick = new ItemMudBrick().setUnlocalizedName("Mud Brick");
		mud = new ItemMud().setUnlocalizedName("Mud").setHasSubtypes(true);
		
		woodenBucketEmpty = new ItemCustomBucket(Blocks.air).setUnlocalizedName("Wooden Bucket Empty");
		woodenBucketWater = new ItemCustomBucket(TFCBlocks.freshWater, woodenBucketEmpty)
				.setUnlocalizedName("Wooden Bucket Water");
		woodenBucketSaltWater = new ItemCustomBucket(TFCBlocks.saltWater, woodenBucketEmpty)
				.setUnlocalizedName("Wooden Bucket Salt Water");
		woodenBucketMilk = new ItemCustomBucketMilk().setUnlocalizedName("Wooden Bucket Milk")
			.setContainerItem(woodenBucketEmpty).setCreativeTab(TFCTabs.TFC_FOODS);
		woodenBucketPlaster = new ItemPlasterBucket().setUnlocalizedName("Wooden Bucket Plaster")
				.setContainerItem(woodenBucketEmpty);
		woodenBucketGypsum = new ItemPlasterBucket().setUnlocalizedName("Wooden Bucket Gypsum")
				.setContainerItem(woodenBucketEmpty);
		woodenBucketVinegar = new ItemCustomBucket(Blocks.air).setFolder("food/").setUnlocalizedName("Wooden Bucket Vinegar")
				.setContainerItem(woodenBucketEmpty).setCreativeTab(TFCTabs.TFC_FOODS);
		woodenBucketHoney = new ItemCustomBucketHoney().setUnlocalizedName("Wooden Bucket Honey")
			.setContainerItem(woodenBucketEmpty);
		
		clayBucketUnfired = new ItemPotteryBase().setMetaNames(new String[]{"Clay Bucket Unfired"}).setUnlocalizedName("Unfired Clay Bucket");
		clayBucketEmpty = new ItemCustomBucket(Blocks.air).setUnlocalizedName("Ceramic Bucket Empty");
		clayBucketWater = new ItemCustomBucket(TFCBlocks.freshWater, clayBucketEmpty)
				.setUnlocalizedName("Ceramic Bucket Water");
		clayBucketSaltWater = new ItemCustomBucket(TFCBlocks.saltWater, clayBucketEmpty)
				.setUnlocalizedName("Ceramic Bucket Salt Water");
		clayBucketMilk = new ItemCustomBucketMilk().setUnlocalizedName("Ceramic Bucket Milk")
				.setContainerItem(clayBucketEmpty).setCreativeTab(TFCTabs.TFC_FOODS);
		clayBucketPlaster = new ItemPlasterBucket().setUnlocalizedName("Ceramic Bucket Plaster")
				.setContainerItem(clayBucketEmpty);
		clayBucketGypsum = new ItemPlasterBucket().setUnlocalizedName("Ceramic Bucket Gypsum")
				.setContainerItem(clayBucketEmpty);
		clayBucketVinegar = new ItemCustomBucket(Blocks.air).setFolder("food/").setUnlocalizedName("Ceramic Bucket Vinegar")
				.setContainerItem(clayBucketEmpty).setCreativeTab(TFCTabs.TFC_FOODS);
		clayBucketHoney = new ItemCustomBucketHoney().setUnlocalizedName("Ceramic Bucket Honey")
				.setContainerItem(clayBucketEmpty);

		bismuthBronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Knife Blade");
		blackBronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Knife Blade");
		blackSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Knife Blade");
		blueSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Knife Blade");
		bronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Knife Blade");
		copperKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Knife Blade");
		wroughtIronKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Knife Blade");
		redSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Knife Blade");
		steelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Knife Blade");

		bismuthBronzeKnife = new ItemKnife(bismuthBronzeToolMaterial,
				bismuthBronzeToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7).setUnlocalizedName("Bismuth Bronze Knife")
						.setMaxDamage(bismuthBronzeUses);
		blackBronzeKnife = new ItemKnife(blackBronzeToolMaterial, blackBronzeToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Black Bronze Knife").setMaxDamage(blackBronzeUses);
		blackSteelKnife = new ItemKnife(blackSteelToolMaterial, blackSteelToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Black Steel Knife").setMaxDamage(blackSteelUses);
		blueSteelKnife = new ItemKnife(blueSteelToolMaterial, blueSteelToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Blue Steel Knife").setMaxDamage(blueSteelUses);
		bronzeKnife = new ItemKnife(bronzeToolMaterial, bronzeToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Bronze Knife").setMaxDamage(bronzeUses);
		copperKnife = new ItemKnife(copperToolMaterial, copperToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Copper Knife").setMaxDamage(copperUses);
		wroughtIronKnife = new ItemKnife(ironToolMaterial, ironToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Wrought Iron Knife").setMaxDamage(wroughtIronUses);
		redSteelKnife = new ItemKnife(redSteelToolMaterial, redSteelToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Red Steel Knife").setMaxDamage(redSteelUses);
		steelKnife = new ItemKnife(steelToolMaterial, steelToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(7)
				.setUnlocalizedName("Steel Knife").setMaxDamage(steelUses);

		flatRock = new ItemFlatGeneric().setFolder("rocks/flatrocks/").setMetaNames(Global.STONE_ALL)
				.setUnlocalizedName("FlatRock");
		looseRock = new ItemLooseRock().setSpecialCraftingType(flatRock).setFolder("rocks/")
				.setMetaNames(Global.STONE_ALL).setUnlocalizedName("LooseRock");

		igInStoneShovelHead = new ItemMiscToolHead(igInToolMaterial).setUnlocalizedName("IgIn Stone Shovel Head");
		sedStoneShovelHead = new ItemMiscToolHead(sedToolMaterial).setUnlocalizedName("Sed Stone Shovel Head");
		igExStoneShovelHead = new ItemMiscToolHead(igExToolMaterial).setUnlocalizedName("IgEx Stone Shovel Head");
		mMStoneShovelHead = new ItemMiscToolHead(mMToolMaterial).setUnlocalizedName("MM Stone Shovel Head");

		igInStoneAxeHead = new ItemMiscToolHead(igInToolMaterial).setUnlocalizedName("IgIn Stone Axe Head");
		sedStoneAxeHead = new ItemMiscToolHead(sedToolMaterial).setUnlocalizedName("Sed Stone Axe Head");
		igExStoneAxeHead = new ItemMiscToolHead(igExToolMaterial).setUnlocalizedName("IgEx Stone Axe Head");
		mMStoneAxeHead = new ItemMiscToolHead(mMToolMaterial).setUnlocalizedName("MM Stone Axe Head");

		igInStoneHoeHead = new ItemMiscToolHead(igInToolMaterial).setUnlocalizedName("IgIn Stone Hoe Head");
		sedStoneHoeHead = new ItemMiscToolHead(sedToolMaterial).setUnlocalizedName("Sed Stone Hoe Head");
		igExStoneHoeHead = new ItemMiscToolHead(igExToolMaterial).setUnlocalizedName("IgEx Stone Hoe Head");
		mMStoneHoeHead = new ItemMiscToolHead(mMToolMaterial).setUnlocalizedName("MM Stone Hoe Head");

		stoneKnifeHead = new ItemMiscToolHead(igInToolMaterial).setUnlocalizedName("Stone Knife Blade");
		stoneHammerHead = new ItemMiscToolHead(igInToolMaterial).setUnlocalizedName("Stone Hammer Head");

		stoneKnife = new ItemKnife(igExToolMaterial, igExToolMaterial.getDamageVsEntity() * 0.75f).setAttackSpeed(5)
				.setUnlocalizedName("Stone Knife").setMaxDamage(igExStoneUses);
		singlePlank = new ItemPlank().setUnlocalizedName("SinglePlank");

		redSteelBucketEmpty = new ItemSteelBucketRed(Blocks.air).setUnlocalizedName("Red Steel Bucket Empty");
		redSteelBucketWater = new ItemSteelBucketRed(TFCBlocks.freshWater).setUnlocalizedName("Red Steel Bucket Water")
				.setContainerItem(redSteelBucketEmpty);
		redSteelBucketSaltWater = new ItemSteelBucketRed(TFCBlocks.saltWater)
				.setUnlocalizedName("Red Steel Bucket Salt Water").setContainerItem(redSteelBucketEmpty);

		blueSteelBucketEmpty = new ItemSteelBucketBlue(Blocks.air).setUnlocalizedName("Blue Steel Bucket Empty");
		blueSteelBucketLava = new ItemSteelBucketBlue(TFCBlocks.lava).setUnlocalizedName("Blue Steel Bucket Lava")
				.setContainerItem(blueSteelBucketEmpty);

		quern = ((ItemTerra) new ItemTerra().setUnlocalizedName("Quern").setMaxDamage(250)).setSize(EnumSize.MEDIUM)
				.setWeight(EnumWeight.HEAVY);
		flintSteel = new ItemFlintSteel().setUnlocalizedName("flintAndSteel").setMaxDamage(200)
				.setTextureName("flint_and_steel");

		doorOak = new ItemWoodDoor(0).setUnlocalizedName("Oak Door");
		doorAspen = new ItemWoodDoor(1).setUnlocalizedName("Aspen Door");
		doorBirch = new ItemWoodDoor(2).setUnlocalizedName("Birch Door");
		doorChestnut = new ItemWoodDoor(3).setUnlocalizedName("Chestnut Door");
		doorDouglasFir = new ItemWoodDoor(4).setUnlocalizedName("Douglas Fir Door");
		doorHickory = new ItemWoodDoor(5).setUnlocalizedName("Hickory Door");
		doorMaple = new ItemWoodDoor(6).setUnlocalizedName("Maple Door");
		doorAsh = new ItemWoodDoor(7).setUnlocalizedName("Ash Door");
		doorPine = new ItemWoodDoor(8).setUnlocalizedName("Pine Door");
		doorSequoia = new ItemWoodDoor(9).setUnlocalizedName("Sequoia Door");
		doorSpruce = new ItemWoodDoor(10).setUnlocalizedName("Spruce Door");
		doorSycamore = new ItemWoodDoor(11).setUnlocalizedName("Sycamore Door");
		doorWhiteCedar = new ItemWoodDoor(12).setUnlocalizedName("White Cedar Door");
		doorWhiteElm = new ItemWoodDoor(13).setUnlocalizedName("White Elm Door");
		doorWillow = new ItemWoodDoor(14).setUnlocalizedName("Willow Door");
		doorKapok = new ItemWoodDoor(15).setUnlocalizedName("Kapok Door");
		doorAcacia = new ItemWoodDoor(16).setUnlocalizedName("Acacia Door");
		doorPalm = new ItemWoodDoor(17).setUnlocalizedName("Palm Door");
		doorEbony = new ItemWoodDoor(18).setUnlocalizedName("Ebony Door");
		doorFever = new ItemWoodDoor(19).setUnlocalizedName("Fever Door");
		doorBaobab = new ItemWoodDoor(20).setUnlocalizedName("Baobab Door");
		doorLimba = new ItemWoodDoor(21).setUnlocalizedName("Limba Door");
		doorMahogany = new ItemWoodDoor(22).setUnlocalizedName("Mahogany Door");
		doorTeak = new ItemWoodDoor(23).setUnlocalizedName("Teak Door");
		doorBamboo = new ItemWoodDoor(24).setUnlocalizedName("Bamboo Door");
		doorGingko = new ItemWoodDoor(25).setUnlocalizedName("Gingko Door");
		doorFruitwood = new ItemWoodDoor(26).setUnlocalizedName("Fruitwood Door");
		
		doorWattle = new ItemWoodDoor(-1).setUnlocalizedName("Wattle Door");
		
		waterBottle = new ItemDrink().setWaterRestore(12000).setUnlocalizedName("Water Bottle").setCreativeTab(TFCTabs.TFC_FOODS);
		grapeJuice = new ItemDrink().setWaterRestore(12000).setFoodGroup(EnumFoodGroup.Fruit).setUnlocalizedName("Grape Juice").setCreativeTab(TFCTabs.TFC_FOODS);
		beer = new ItemAlcohol().setAlcoholContent(0.05f).setFoodGroup(EnumFoodGroup.Grain).setTier(1).setWaterRestore(8000).setUnlocalizedName("Beer").setCreativeTab(TFCTabs.TFC_FOODS);
		wheatBeer = new ItemAlcohol().setAlcoholContent(0.05f).setFoodGroup(EnumFoodGroup.Grain).setTier(1).setWaterRestore(8000).setUnlocalizedName("Wheat Beer").setCreativeTab(TFCTabs.TFC_FOODS);
		ryeBeer = new ItemAlcohol().setAlcoholContent(0.05f).setFoodGroup(EnumFoodGroup.Grain).setTier(1).setWaterRestore(8000).setUnlocalizedName("Rye Beer").setCreativeTab(TFCTabs.TFC_FOODS);
		cornBeer = new ItemAlcohol().setAlcoholContent(0.05f).setFoodGroup(EnumFoodGroup.Grain).setTier(1).setWaterRestore(8000).setUnlocalizedName("Corn Beer").setCreativeTab(TFCTabs.TFC_FOODS);
		riceBeer = new ItemAlcohol().setAlcoholContent(0.05f).setFoodGroup(EnumFoodGroup.Grain).setTier(1).setWaterRestore(8000).setUnlocalizedName("Rice Beer").setCreativeTab(TFCTabs.TFC_FOODS);
		barleyWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Barley Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		wheatWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Wheat Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		ryeWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Rye Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		cornWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Corn Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		caneWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Cane Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		agaveWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Agave Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		potatoWine = new ItemAlcohol().setAlcoholContent(0.08f).setTier(1).setWaterRestore(6000).setUnlocalizedName("Potato Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		cider = new ItemAlcohol().setAlcoholContent(0.05f).setFoodGroup(EnumFoodGroup.Fruit).setTier(1).setWaterRestore(7000).setUnlocalizedName("Cider").setCreativeTab(TFCTabs.TFC_FOODS);
		appleJack = new ItemAlcohol().setAlcoholContent(0.2f).setTier(2).setUnlocalizedName("Applejack").setCreativeTab(TFCTabs.TFC_FOODS);
		rum = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("Rum").setCreativeTab(TFCTabs.TFC_FOODS);
		ryeWhiskey = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("RyeWhiskey").setCreativeTab(TFCTabs.TFC_FOODS);
		riceWhiskey = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("RiceWhiskey").setCreativeTab(TFCTabs.TFC_FOODS);
		barleyWhiskey = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("BarleyWhiskey").setCreativeTab(TFCTabs.TFC_FOODS);
		sake = new ItemAlcohol().setAlcoholContent(0.18f).setTier(1).setUnlocalizedName("Sake").setCreativeTab(TFCTabs.TFC_FOODS);
		shochu = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("Shochu").setCreativeTab(TFCTabs.TFC_FOODS);
		vodka = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("Vodka").setCreativeTab(TFCTabs.TFC_FOODS);
		tequila = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("Tequila").setCreativeTab(TFCTabs.TFC_FOODS);
		berryWine = new ItemAlcohol().setAlcoholContent(0.11f).setTier(1).setWaterRestore(4000).setUnlocalizedName("BerryWine").setCreativeTab(TFCTabs.TFC_FOODS);
		fruitWine = new ItemAlcohol().setAlcoholContent(0.12f).setTier(1).setWaterRestore(4000).setUnlocalizedName("FruitWine").setCreativeTab(TFCTabs.TFC_FOODS);
		wine = new ItemAlcohol().setAlcoholContent(0.14f).setTier(1).setWaterRestore(4000).setUnlocalizedName("Wine").setCreativeTab(TFCTabs.TFC_FOODS);
		berryBrandy = new ItemAlcohol().setAlcoholContent(0.35f).setTier(2).setUnlocalizedName("BerryBrandy").setCreativeTab(TFCTabs.TFC_FOODS);
		fruitBrandy = new ItemAlcohol().setAlcoholContent(0.35f).setTier(2).setUnlocalizedName("FruitBrandy").setCreativeTab(TFCTabs.TFC_FOODS);
		brandy = new ItemAlcohol().setAlcoholContent(0.35f).setTier(2).setUnlocalizedName("Brandy").setCreativeTab(TFCTabs.TFC_FOODS);
		whiskey = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("Whiskey").setCreativeTab(TFCTabs.TFC_FOODS);
		cornWhiskey = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("CornWhiskey").setCreativeTab(TFCTabs.TFC_FOODS);
		honeyBrandy = new ItemAlcohol().setAlcoholContent(0.4f).setTier(2).setUnlocalizedName("HoneyJack").setCreativeTab(TFCTabs.TFC_FOODS);
		mead = new ItemAlcohol().setAlcoholContent(0.13f).setTier(1).setUnlocalizedName("Mead").setCreativeTab(TFCTabs.TFC_FOODS);
		
		honeybowl = new ItemHoneyBowl().setUnlocalizedName("HoneyBowl");

		blueprint = new ItemBlueprint();
		nametag = new ItemCustomNameTag();
		// writabeBookTFC = new ItemWritableBookTFC("Fix Me I'm
		// Broken").setUnlocalizedName("book");

		flatCotton = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Cotton");
		flatWool = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Wool");
		flatSilk = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Silk");
		flatLinen = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Linen");
		flatWolfFur = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Wolf Fur");
		flatBearFur = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Bear Fur");
		flatFur = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Fur");
		flatStraw = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Straw");
		flatLeather = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Leather");
		flatHide = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Hide");
		flatBurlap = new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Burlap");

		boneNeedle = new ItemNeedle(10, boneToolMaterial, null).setStackable(true).setUnlocalizedName("Bone Needle");
		ironNeedle = new ItemNeedle(10, smallIronToolMaterial, null).setStackable(true)
				.setUnlocalizedName("Iron Needle");
		boneNeedleStrung = new ItemNeedle(10, boneToolMaterial, null).setUnlocalizedName("Bone Needle Thread")
				.setMaxDamage(stringUses);
		ironNeedleStrung = new ItemNeedle(10, smallIronToolMaterial, null).setUnlocalizedName("Iron Needle Thread")
				.setMaxDamage(stringUses);

		sackPiece = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, false, false, false, false, true })
				.setUnlocalizedName("Sack Piece");
		saddlePiece = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, true, false, false, false })
				.setUnlocalizedName("Saddle Piece");
		quiverPiece = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, true, false, false, false })
				.setUnlocalizedName("Quiver Piece");
		bagPiece = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, true, false, false, false, false, true})
				.setUnlocalizedName("Bag Piece");
		hatPiece = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, true, true, true, true, false, false, true,true })
				.setUnlocalizedName("Hat Piece");
		shirtSleeves = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, true, true, true, true, false, false, true,true })
				.setUnlocalizedName("Shirt Sleeves");
		shirtBody = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, true, true, false, false, false, false, true })
				.setUnlocalizedName("Shirt Body");
		pantsPiece = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, true, true, false, false, false, false, true })
				.setUnlocalizedName("Pants Piece");
		bootSole = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, true, false, true, true,false,false,false,true })
				.setUnlocalizedName("Boot Soles");
		leatherStrap = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, true, false, false, false })
				.setUnlocalizedName("Strap");
		sockCloth = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, false, true, false, false, false, false, true })
				.setUnlocalizedName("Sock Cloth");
		coatBodyFront = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, false, true, true, true, false, false, true,true })
				.setUnlocalizedName("Coat Front");
		coatBodyBack = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, false, true, true, true, false, false, true,true })
				.setUnlocalizedName("Coat Back");
		bootTop = new ItemClothingPiece().setPieceTypes(new boolean[] { false, false, true, false, true, true,false,false,false,true })
				.setUnlocalizedName("Boot Tops");
		repairPatch = new ItemClothingPiece().setPieceTypes(new boolean[] { true, true, false, true, false, false, false, false, true })
				.setUnlocalizedName("Repair Patch");
		
		splint = new ItemBandage().setSplint(true).setUnlocalizedName("Splint");
		cast = new ItemBandage().setCast(true).setUnlocalizedName("Cast");
		
		resin = new ItemTerra().setUnlocalizedName("Resin");
		primitiveBandage = new ItemBandage().setPrimitive(true).setUnlocalizedName("Primitive Bandage");
		bandage = new ItemBandage().setUnlocalizedName("Bandage");
		sterileBandage = new ItemBandage().setSterilized(true).setUnlocalizedName("Sterile Bandage");

		// Clothing
		// Wool
		woolSocks = new ItemSocks(IEquipable.ClothingType.SOCKS)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wool_socks_color.png"))
				.setColdResistance(1).setWool(true).setArmorType(Armor.woolCloth).setMaxDamage(woolUses).setUnlocalizedName("Wool Socks");
		((ItemClothing)woolSocks).setRepairCost(2);
		woolPants = new ItemPants(IEquipable.ClothingType.PANTS)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wool_pants_color.png"))
				.setColdResistance(1).setWool(true).setArmorType(Armor.woolCloth).setMaxDamage(woolUses).setUnlocalizedName("Wool Pants");
		((ItemClothing)woolPants).setRepairCost(2);
		woolShirt = new ItemShirt(IEquipable.ClothingType.SHIRT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wool_shirt_color.png"))
				.setColdResistance(1).setWool(true).setArmorType(Armor.woolCloth).setMaxDamage(woolUses).setUnlocalizedName("Wool Shirt");
		((ItemClothing)woolShirt).setRepairCost(3);
		woolCoat = new ItemCoat(IEquipable.ClothingType.COAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wool_coat_color.png"))
				.setColdResistance(3).setWool(true).setHeatResistance(-2).setArmorType(Armor.woolCloth).setMaxDamage(woolUses)
				.setUnlocalizedName("Wool Coat");
		((ItemClothing)woolCoat).setRepairCost(4);
		woolRobe = new ItemCoat(IEquipable.ClothingType.ROBE)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wool_robe_color.png"))
				.setColdResistance(4).setWool(true).setHeatResistance(-2).setArmorType(Armor.woolCloth).setMaxDamage(woolUses)
				.setUnlocalizedName("Wool Robe");
		((ItemClothing)woolRobe).setRepairCost(5);
		woolHat = new ItemHat(IEquipable.ClothingType.CLOTH_HAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wool_hat_color.png"))
				.setColdResistance(1).setWool(true).setArmorType(Armor.woolCloth).setMaxDamage(woolUses).setUnlocalizedName("Wool Hat");
		((ItemClothing)woolHat).setRepairCost(1);
		// Cotton
		cottonSocks = new ItemSocks(IEquipable.ClothingType.SOCKS)
						.setResourceLocation(
								new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/cotton_socks_color.png")).setArmorType(Armor.cottonCloth)
						.setMaxDamage(cottonUses).setUnlocalizedName("Cotton Socks"); //No cold resistance
		((ItemClothing)cottonSocks).setRepairCost(2);
		cottonPants = new ItemPants(IEquipable.ClothingType.PANTS)
						.setResourceLocation(
								new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/cotton_pants_color.png"))
						.setColdResistance(1).setArmorType(Armor.cottonCloth).setMaxDamage(cottonUses).setUnlocalizedName("Cotton Pants");
		((ItemClothing)cottonPants).setRepairCost(2);
		cottonShirt = new ItemShirt(IEquipable.ClothingType.SHIRT)
						.setResourceLocation(
								new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/cotton_shirt_color.png"))
						.setColdResistance(1).setArmorType(Armor.cottonCloth).setMaxDamage(cottonUses).setUnlocalizedName("Cotton Shirt");
		((ItemClothing)cottonShirt).setRepairCost(3);
		cottonCoat = new ItemCoat(IEquipable.ClothingType.COAT)
						.setResourceLocation(
								new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/cotton_coat_color.png"))
						.setColdResistance(2).setHeatResistance(-1).setArmorType(Armor.cottonCloth).setMaxDamage(cottonUses)
						.setUnlocalizedName("Cotton Coat");
		((ItemClothing)cottonCoat).setRepairCost(4);
		cottonRobe = new ItemCoat(IEquipable.ClothingType.ROBE)
						.setResourceLocation(
								new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/cotton_robe_color.png"))
						.setColdResistance(3).setHeatResistance(-2).setArmorType(Armor.cottonCloth).setMaxDamage(cottonUses)
						.setUnlocalizedName("Cotton Robe");
		((ItemClothing)cottonRobe).setRepairCost(5);
		cottonHat = new ItemHat(IEquipable.ClothingType.CLOTH_HAT)
						.setResourceLocation(
								new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/cotton_hat_color.png")).setArmorType(Armor.cottonCloth)
						.setMaxDamage(cottonUses).setUnlocalizedName("Cotton Hat"); //No cold resistance
		((ItemClothing)cottonHat).setRepairCost(1);

		// Linen
		linenSocks = new ItemSocks(IEquipable.ClothingType.SOCKS).setWalkableBonus(0.02f)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/linen_socks_color.png")).setArmorType(Armor.linenCloth)
				.setMaxDamage(linenUses).setUnlocalizedName("Linen Socks");
		((ItemClothing)linenSocks).setRepairCost(2);
		linenPants = new ItemPants(IEquipable.ClothingType.PANTS)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/linen_pants_color.png"))
				.setColdResistance(1).setArmorType(Armor.linenCloth).setMaxDamage(linenUses).setUnlocalizedName("Linen Pants");
		((ItemClothing)linenPants).setRepairCost(2);
		linenShirt = new ItemShirt(IEquipable.ClothingType.SHIRT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/linen_shirt_color.png"))
				.setHeatResistance(1).setArmorType(Armor.linenCloth).setMaxDamage(linenUses).setUnlocalizedName("Linen Shirt");
		((ItemClothing)linenShirt).setRepairCost(3);
		linenCoat = new ItemCoat(IEquipable.ClothingType.COAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/linen_coat_color.png"))
				.setColdResistance(1).setHeatResistance(-1).setArmorType(Armor.linenCloth).setMaxDamage(linenUses).setUnlocalizedName("Linen Coat");
		((ItemClothing)linenCoat).setRepairCost(4);
		linenRobe = new ItemCoat(IEquipable.ClothingType.ROBE)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/linen_robe_color.png"))
				.setColdResistance(2).setHeatResistance(-1).setArmorType(Armor.linenCloth).setMaxDamage(linenUses).setUnlocalizedName("Linen Robe");
		((ItemClothing)linenRobe).setRepairCost(5);
		linenHat = new ItemHat(IEquipable.ClothingType.CLOTH_HAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/linen_hat_color.png")).setHeatResistance(1).setArmorType(Armor.linenCloth)
				.setMaxDamage(linenUses).setUnlocalizedName("Linen Hat");
		((ItemClothing)linenHat).setRepairCost(1);

		// Silk
		silkSocks = new ItemSocks(IEquipable.ClothingType.SOCKS)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/silk_socks_color.png"))
				.setHeatResistance(1).setArmorType(Armor.silkCloth).setMaxDamage(silkUses).setUnlocalizedName("Silk Socks");
		((ItemClothing)silkSocks).setRepairCost(2);
		silkPants = new ItemPants(IEquipable.ClothingType.THINPANTS)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/silk_pants_color.png"))
				.setHeatResistance(1).setArmorType(Armor.silkCloth).setMaxDamage(silkUses).setUnlocalizedName("Silk Pants");
		((ItemClothing)silkPants).setRepairCost(2);
		silkShirt = new ItemShirt(IEquipable.ClothingType.THINSHIRT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/silk_shirt_color.png"))
				.setHeatResistance(1).setColdResistance(1).setArmorType(Armor.silkCloth).setMaxDamage(silkUses).setUnlocalizedName("Silk Shirt");
		((ItemClothing)silkShirt).setRepairCost(3);
		silkCoat = new ItemCoat(IEquipable.ClothingType.THINCOAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/silk_coat_color.png"))
				.setHeatResistance(2).setArmorType(Armor.silkCloth).setMaxDamage(silkUses).setUnlocalizedName("Silk Coat");
		((ItemClothing)silkCoat).setRepairCost(4);
		silkRobe = new ItemCoat(IEquipable.ClothingType.ROBE)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/silk_robe_color.png"))
				.setHeatResistance(2).setColdResistance(1).setArmorType(Armor.silkCloth).setMaxDamage(silkUses).setUnlocalizedName("Silk Robe");
		((ItemClothing)silkRobe).setRepairCost(5);
		silkHat = new ItemHat(IEquipable.ClothingType.CLOTH_HAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/silk_hat_color.png"))
				.setHeatResistance(1).setColdResistance(1).setArmorType(Armor.silkCloth).setMaxDamage(silkUses).setUnlocalizedName("Silk Hat");
		((ItemClothing)silkHat).setRepairCost(1);

		// Fur
		wolfFurBoots = ((ItemClothing) (new ItemBoots(IEquipable.ClothingType.FULLBOOTS).setDefaultWalkable(0.05f)
				.addWalkableSurface(Material.sand, 0.02f)).setTrueBoots(true)).setNoWearInCold(true).setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wolf_boots_color.png"))
						.setColdResistance(2).setArmorType(Armor.wolfFur).setMaxDamage(furUses).setUnlocalizedName("Wolf Fur Boots");
		wolfFurCoat = new ItemCoat(IEquipable.ClothingType.HEAVYCOAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wolf_coat_color.png"))
				.setColdResistance(4).setHeatResistance(-2).setNoWearInCold(true).setArmorType(Armor.wolfFur).setMaxDamage(furUses).setUnlocalizedName("Wolf Fur Coat");
		wolfFurHat = new ItemHat(EquipType.HEAD, new EquipType[] { EquipType.HEAD2 }, IEquipable.ClothingType.CLOTH_HAT)
				.setColdResistance(2).setNoWearInCold(true)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wolf_head_hat_color.png")).setArmorType(Armor.wolfFur)
				.setMaxDamage(furUses).setUnlocalizedName("Wolf Fur Hat");
		wolfFurGenericHat = new ItemHat(EquipType.HEAD2, new EquipType[] {}, IEquipable.ClothingType.CLOTH_HAT)
		.setColdResistance(2).setNoWearInCold(true)
		.setResourceLocation(
				new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/wolf_hat_color.png")).setArmorType(Armor.wolfFur)
		.setMaxDamage(furUses).setUnlocalizedName("Wolf Fur Generic Hat");
		
		furBoots = ((ItemClothing) (new ItemBoots(IEquipable.ClothingType.FULLBOOTS).setDefaultWalkable(0.05f)
		.addWalkableSurface(Material.sand, 0.02f)).setTrueBoots(true)).setNoWearInCold(true).setResourceLocation(
				new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/fur_boots_color.png"))
				.setColdResistance(1).setArmorType(Armor.fur).setMaxDamage(furUses).setUnlocalizedName("Fur Boots");
			furCoat = new ItemCoat(IEquipable.ClothingType.HEAVYCOAT)
		.setResourceLocation(
				new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/fur_coat_color.png"))
		.setColdResistance(3).setHeatResistance(-2).setNoWearInCold(true).setArmorType(Armor.fur).setMaxDamage(furUses).setUnlocalizedName("Fur Coat");
				furHat = new ItemHat(EquipType.HEAD2, new EquipType[] {}, IEquipable.ClothingType.CLOTH_HAT)
		.setColdResistance(1).setNoWearInCold(true)
		.setResourceLocation(
				new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/fur_hat_color.png")).setArmorType(Armor.fur)
		.setMaxDamage(furUses).setUnlocalizedName("Fur Hat");
		

		bearFurBoots = ((ItemClothing) (new ItemBoots(IEquipable.ClothingType.FULLBOOTS).setDefaultWalkable(0.05f)
				.addWalkableSurface(Material.sand, 0.02f)).setTrueBoots(true)).setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/bear_boots_color.png"))
						.setColdResistance(2).setNoWearInCold(true).setArmorType(Armor.bearFur).setMaxDamage(furUses).setUnlocalizedName("Bear Fur Boots");
		bearFurCoat = new ItemCoat(IEquipable.ClothingType.HEAVYCOAT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/bear_coat_color.png")).setNoWearInCold(true)
				.setColdResistance(5).setHeatResistance(-3).setArmorType(Armor.bearFur).setMaxDamage(furUses).setUnlocalizedName("Bear Fur Coat");
		bearFurHat = new ItemHat(EquipType.HEAD, new EquipType[] { EquipType.HEAD2 }, IEquipable.ClothingType.CLOTH_HAT).setNoWearInCold(true)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/bear_head_hat_color.png"))
				.setColdResistance(2).setArmorType(Armor.bearFur).setMaxDamage(furUses).setUnlocalizedName("Bear Fur Hat");
		bearFurGenericHat = new ItemHat(EquipType.HEAD2, new EquipType[] {}, IEquipable.ClothingType.CLOTH_HAT).setNoWearInCold(true)
		.setResourceLocation(
				new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/bear_hat_color.png"))
		.setColdResistance(2).setArmorType(Armor.bearFur).setMaxDamage(furUses).setUnlocalizedName("Bear Fur Generic Hat");
		
		// Straw
		strawSocks = new ItemSocks(IEquipable.ClothingType.SOCKS)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/grass_socks_color.png"))
				.setColdResistance(1).setHeatResistance(-1).setStraw(true).setArmorType(Armor.strawClothes).setMaxDamage(strawUses).setUnlocalizedName("Straw Socks");
		grassShirt = new ItemShirt(EquipType.BODY, new EquipType[] { EquipType.BODY2 }, IEquipable.ClothingType.SHIRT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/grass_shirt_color.png"))
				.setHeatResistance(1).setStraw(true).setArmorType(Armor.strawClothes).setMaxDamage(strawUses).setUnlocalizedName("Grass Shirt");
		strawHat = new ItemHat(EquipType.HEAD, new EquipType[] { EquipType.HEAD2 }, IEquipable.ClothingType.STRAW_HAT).setProtectsFromSun(true)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/straw_hat_color.png"))
				.setHeatResistance(1).setStraw(true).setArmorType(Armor.strawClothes).setMaxDamage(strawUses * 4).setUnlocalizedName("Straw Hat");
		strawHat2 = new ItemHat(EquipType.HEAD, new EquipType[] { EquipType.HEAD2 }, IEquipable.ClothingType.STRAW_HAT).setProtectsFromSun(true)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/straw_hat_color.png"))
				.setHeatResistance(1).setStraw(true).setArmorType(Armor.strawClothes).setMaxDamage(strawUses * 4).setUnlocalizedName("Straw Hat2");
		grassSkirt = new ItemSkirt(new EquipType[] { EquipType.LEGS2 }, IEquipable.ClothingType.SKIRT)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/grass_skirt_color.png"))
				.setHeatResistance(1).setStraw(true).setArmorType(Armor.strawClothes).setMaxDamage(strawUses).setUnlocalizedName("Grass Skirt");
		grassSandals = ((ItemBoots) (new ItemBoots(IEquipable.ClothingType.SANDALS).setDefaultWalkable(0.02f)
				.addWalkableSurface(Material.sand, 0.05f)))
						.setResourceLocation(new ResourceLocation(Reference.MOD_ID,
								"textures/models/armor/clothing/grass_sandals_color.png"))
						.setStraw(true).setArmorType(Armor.strawClothes).setMaxDamage(strawUses).setUnlocalizedName("Grass Sandals");
		grassCoat = new ItemCloak(IEquipable.ClothingType.CLOAK)
				.setResourceLocation(
						new ResourceLocation(Reference.MOD_ID, "textures/models/armor/clothing/grass_coat_color.png"))
				.setColdResistance(2).setHeatResistance(-2).setArmorType(Armor.strawClothes).setMaxDamage(strawUses).setUnlocalizedName("Grass Coat");

		// Leather
		leatherSandals = ((ItemClothing) (new ItemBoots(IEquipable.ClothingType.SANDALS).setDefaultWalkable(0.05f)
				.addWalkableSurface(Material.sand, 0.1f)))
						.setResourceLocation(new ResourceLocation(Reference.MOD_ID,
								"textures/models/armor/clothing/leather_sandals_color.png")).setArmorType(Armor.leather)
						.setMaxDamage(leatherUses).setUnlocalizedName("Leather Sandals");

		
		linenString = new ItemYarn().setUnlocalizedName("LinenString").setCreativeTab(TFCTabs.TFC_MATERIALS);
		silkString = new ItemYarn().setUnlocalizedName("SilkString").setCreativeTab(TFCTabs.TFC_MATERIALS);
		sinew = new ItemYarn().setUnlocalizedName("Sinew").setCreativeTab(TFCTabs.TFC_MATERIALS);
		grassCordage = new ItemTerra().setUnlocalizedName("StrawCordage").setCreativeTab(TFCTabs.TFC_MATERIALS);
		woolYarn = new ItemYarn().setUnlocalizedName("WoolYarn").setCreativeTab(TFCTabs.TFC_MATERIALS);
		wool = new ItemTerra().setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFC_MATERIALS);
		woolCloth = new ItemLeather().setSpecialCraftingType(flatWool).setUnlocalizedName("WoolCloth")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		cottonYarn = new ItemYarn().setUnlocalizedName("CottonYarn").setCreativeTab(TFCTabs.TFC_MATERIALS);
		cotton = new ItemTerra().setUnlocalizedName("Cotton").setCreativeTab(TFCTabs.TFC_MATERIALS);
		cottonCloth = new ItemLeather().setSpecialCraftingType(flatCotton).setUnlocalizedName("CottonCloth")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		silkCloth = new ItemLeather().setSpecialCraftingType(flatSilk).setUnlocalizedName("SilkCloth")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		linenCloth = new ItemLeather().setSpecialCraftingType(flatLinen).setUnlocalizedName("LinenCloth")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		wolfFur = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatWolfFur).setUnlocalizedName("Wolf Fur")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		wolfFurScrap = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatWolfFur).setUnlocalizedName("Wolf Fur Scrap")
			.setCreativeTab(TFCTabs.TFC_MATERIALS);
		bearFur = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatBearFur).setUnlocalizedName("Bear Fur")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		bearFurScrap = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatBearFur).setUnlocalizedName("Bear Fur Scrap")
			.setCreativeTab(TFCTabs.TFC_MATERIALS);
		fur = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatFur).setUnlocalizedName("Fur")
			.setCreativeTab(TFCTabs.TFC_MATERIALS);
		furScrap = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatFur).setUnlocalizedName("Fur Scrap")
			.setCreativeTab(TFCTabs.TFC_MATERIALS);
		burlapCloth =  new ItemLeather().setSpecialCraftingType(flatBurlap).setUnlocalizedName("BurlapCloth").setCreativeTab(TFCTabs.TFC_MATERIALS);
		spindle = new ItemSpindle().setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFC_POTTERY);

		spindleHead = new ItemPotteryBase().setMetaNames(new String[] { "Clay Spindle", "Spindle Head" })
				.setUnlocalizedName("Spindle Head").setCreativeTab(TFCTabs.TFC_POTTERY);
		stoneBrick = new ItemStoneBrick().setFolder("tools/").setUnlocalizedName("ItemStoneBrick");
		slateShingle = new ItemTerra().setFolder("rocks/").setUnlocalizedName("Slate Shingle");
		mortar = new ItemTerra().setFolder("tools/").setUnlocalizedName("Mortar").setCreativeTab(TFCTabs.TFC_MATERIALS);		
		hide = new ItemLeather().setHasSizes(true).setSpecialCraftingType(flatHide).setUnlocalizedName("Hide").setCreativeTab(TFCTabs.TFC_MATERIALS);
		soakedHide = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Soaked Hide")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		scrapedHide = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Scraped Hide")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		prepHide = new ItemRawHide().setFolder("tools/").setFolder("tools/").setUnlocalizedName("Prep Hide")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);

		sheepSkin = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Sheep Skin")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);		
		leather = new ItemLeather().setSpecialCraftingType(flatLeather).setFolder("tools/")
				.setUnlocalizedName("TFC Leather");

		lyre = new ItemLyre().setUnlocalizedName("Lyre");
		boneFlute = new ItemFlute().setUnlocalizedName("Bone Flute");
		hollowBone = new ItemTerra().setUnlocalizedName("Hollow Bone");
		
		straw = new ItemStraw().setSpecialCraftingType(flatStraw).setFolder("plants/").setUnlocalizedName("Straw")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		strawBasket = new ItemBasket().setUnlocalizedName("Straw Basket");
		
		emptyHoneycomb = new ItemHoneycomb().setUnlocalizedName("EmptyHoneycomb");
		honeycomb = new ItemHoneycomb().setUnlocalizedName("Honeycomb");
		fertileHoneycomb = new ItemHoneycomb().setUnlocalizedName("FertileHoneycomb");
		
		flatClay = new ItemFlatGeneric().setFolder("pottery/")
				.setMetaNames(
						new String[] { "clay flat light", "clay flat dark", "clay flat fire", "clay flat dark fire" })
				.setUnlocalizedName("Flat Clay");

		potteryJug = new ItemPotteryJug().setUnlocalizedName("Jug");
		potterySmallVessel = new ItemPotterySmallVessel().setUnlocalizedName("Small Vessel");
		burlapSack = new ItemBurlapSack().setUnlocalizedName("Burlap Sack");
		
		hideBag = new ItemLeatherBag(new String[]{"Hide Bag"}).setMaxDamage(20).setUnlocalizedName("Hide Bag");
		leatherBag = new ItemLeatherBag(new String[]{"Leather Bag"}).setMaxDamage(120).setUnlocalizedName("Leather Bag");
		// PotteryPot = new ItemPotteryPot().setUnlocalizedName("Pot");
		
		clayTile = new ItemPotteryBase().setMetaNames(new String[] {"Clay Tile","Ceramic Tile"}).setUnlocalizedName("Clay Tile");
		ceramicMold = new ItemPotteryMoldBase().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1)
				.setBaseDamage(2).setMetaNames(new String[] { "Clay Mold", "Ceramic Mold" }).setUnlocalizedName("Mold");
		potteryBowl = new ItemPotteryBase().setMetaNames(new String[] { "Clay Bowl", "Ceramic Bowl" })
				.setUnlocalizedName("ClayBowl");
		clayBall = new ItemClay().setSpecialCraftingType(flatClay, new ItemStack(flatClay, 1, 1))
				.setMetaNames(new String[] { "Clay", "Fire Clay" }).setUnlocalizedName("Clay");
		fireBrick = new ItemPotteryBase().setMetaNames(new String[] { "Clay Fire Brick", "Fire Brick" })
			.setUnlocalizedName("Fire Brick");
		brick = new ItemPotteryBase().setMetaNames(new String[] { "Clay Brick", "Brick" })
			.setUnlocalizedName("Brick");

		clayMoldSheet = new ItemPotterySheetMold().setMaxUnits(200).setCounter(5).setBaseDamage(2)
				.setHasSolidLiquidStates(true)
				.setMetaNames(new String[] { "Clay Mold Sheet", "Ceramic Mold Sheet", "Ceramic Mold Sheet Copper",
						"Ceramic Mold Sheet Bronze", "Ceramic Mold Sheet Bismuth Bronze",
						"Ceramic Mold Sheet Black Bronze", "Ceramic Mold Sheet Glass" })
				.setUnlocalizedName("Sheet Mold");

		clayBlowpipe = new ItemPotteryBlowpipe().setMaxUnits(100).setCounter(1).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Blowpipe", "Ceramic Blowpipe", "Ceramic Blowpipe Glass" })
				.setUnlocalizedName("Blowpipe");

		clayMoldAxe = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Axe", "Ceramic Mold Axe", "Ceramic Mold Axe Copper",
						"Ceramic Mold Axe Bronze", "Ceramic Mold Axe Bismuth Bronze", "Ceramic Mold Axe Black Bronze" })
				.setUnlocalizedName("Axe Mold");
		clayMoldChisel = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Chisel", "Ceramic Mold Chisel", "Ceramic Mold Chisel Copper",
						"Ceramic Mold Chisel Bronze", "Ceramic Mold Chisel Bismuth Bronze",
						"Ceramic Mold Chisel Black Bronze" })
				.setUnlocalizedName("Chisel Mold");
		clayMoldHammer = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Hammer", "Ceramic Mold Hammer", "Ceramic Mold Hammer Copper",
						"Ceramic Mold Hammer Bronze", "Ceramic Mold Hammer Bismuth Bronze",
						"Ceramic Mold Hammer Black Bronze" })
				.setUnlocalizedName("Hammer Mold");
		clayMoldHoe = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Hoe", "Ceramic Mold Hoe", "Ceramic Mold Hoe Copper",
						"Ceramic Mold Hoe Bronze", "Ceramic Mold Hoe Bismuth Bronze", "Ceramic Mold Hoe Black Bronze" })
				.setUnlocalizedName("Hoe Mold");
		clayMoldKnife = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Knife", "Ceramic Mold Knife", "Ceramic Mold Knife Copper",
						"Ceramic Mold Knife Bronze", "Ceramic Mold Knife Bismuth Bronze",
						"Ceramic Mold Knife Black Bronze" })
				.setUnlocalizedName("Knife Mold");
		clayMoldMace = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Mace", "Ceramic Mold Mace", "Ceramic Mold Mace Copper",
						"Ceramic Mold Mace Bronze", "Ceramic Mold Mace Bismuth Bronze",
						"Ceramic Mold Mace Black Bronze" })
				.setUnlocalizedName("Mace Mold");
		clayMoldPick = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Pick", "Ceramic Mold Pick", "Ceramic Mold Pick Copper",
						"Ceramic Mold Pick Bronze", "Ceramic Mold Pick Bismuth Bronze",
						"Ceramic Mold Pick Black Bronze" })
				.setUnlocalizedName("Pick Mold");
		clayMoldProPick = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold ProPick", "Ceramic Mold ProPick", "Ceramic Mold ProPick Copper",
						"Ceramic Mold ProPick Bronze", "Ceramic Mold ProPick Bismuth Bronze",
						"Ceramic Mold ProPick Black Bronze" })
				.setUnlocalizedName("ProPick Mold");
		clayMoldTrowel = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Trowel", "Ceramic Mold Trowel", "Ceramic Mold Trowel Copper",
						"Ceramic Mold Trowel Bronze", "Ceramic Mold Trowel Bismuth Bronze",
						"Ceramic Mold Trowel Black Bronze" })
				.setUnlocalizedName("Trowel Mold");
		clayMoldSaw = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Saw", "Ceramic Mold Saw", "Ceramic Mold Saw Copper",
						"Ceramic Mold Saw Bronze", "Ceramic Mold Saw Bismuth Bronze", "Ceramic Mold Saw Black Bronze" })
				.setUnlocalizedName("Saw Mold");
		clayMoldScythe = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Scythe", "Ceramic Mold Scythe", "Ceramic Mold Scythe Copper",
						"Ceramic Mold Scythe Bronze", "Ceramic Mold Scythe Bismuth Bronze",
						"Ceramic Mold Scythe Black Bronze" })
				.setUnlocalizedName("Scythe Mold");
		clayMoldShovel = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Shovel", "Ceramic Mold Shovel", "Ceramic Mold Shovel Copper",
						"Ceramic Mold Shovel Bronze", "Ceramic Mold Shovel Bismuth Bronze",
						"Ceramic Mold Shovel Black Bronze" })
				.setUnlocalizedName("Shovel Mold");
		clayMoldSword = new ItemPotteryMold().setMaxUnits(200).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Sword", "Ceramic Mold Sword", "Ceramic Mold Sword Copper",
						"Ceramic Mold Sword Bronze", "Ceramic Mold Sword Bismuth Bronze",
						"Ceramic Mold Sword Black Bronze" })
				.setUnlocalizedName("Sword Mold");
		clayMoldJavelin = new ItemPotteryMold().setMaxUnits(100).setCounter(4).setBaseDamage(2)
				.setMetaNames(new String[] { "Clay Mold Javelin", "Ceramic Mold Javelin", "Ceramic Mold Javelin Copper",
						"Ceramic Mold Javelin Bronze", "Ceramic Mold Javelin Bismuth Bronze",
						"Ceramic Mold Javelin Black Bronze" })
				.setUnlocalizedName("Javelin Mold");

		brassTube = new ItemTerra().setUnlocalizedName("Brass Tube");
		horn = new ItemTerra().setUnlocalizedName("horn");
		blowingHorn = new ItemHorn().setUnlocalizedName("blowing horn");
		brassHorn = new ItemHorn(true).setUnlocalizedName("bugle");
		conchHorn = new ItemHorn().setUnlocalizedName("conch horn");
		
		tuyereCopper = new ItemTuyere(40, 0).setUnlocalizedName("Copper Tuyere");
		tuyereBronze = new ItemTuyere(80, 1).setUnlocalizedName("Bronze Tuyere");
		tuyereBlackBronze = new ItemTuyere(80, 1).setUnlocalizedName("Black Bronze Tuyere");
		tuyereBismuthBronze = new ItemTuyere(80, 1).setUnlocalizedName("Bismuth Bronze Tuyere");
		tuyereWroughtIron = new ItemTuyere(120, 2).setUnlocalizedName("Wrought Iron Tuyere");
		tuyereSteel = new ItemTuyere(180, 3).setUnlocalizedName("Steel Tuyere");
		tuyereBlackSteel = new ItemTuyere(260, 4).setUnlocalizedName("Black Steel Tuyere");
		tuyereRedSteel = new ItemTuyere(400, 5).setUnlocalizedName("Red Steel Tuyere");
		tuyereBlueSteel = new ItemTuyere(500, 6).setUnlocalizedName("Blue Steel Tuyere");

		bloom = new ItemBloom().setFolder("ingots/").setUnlocalizedName("Iron Bloom");
		rawBloom = new ItemBloom().setFolder("ingots/").setUnlocalizedName("Raw Iron Bloom");

		unknownIngot = new ItemIngot().setUnlocalizedName("Unknown Ingot");
		unknownUnshaped = new ItemMeltedMetal().setMaxUnits(100).setCounter(1).setBaseDamage(0)
				.setUnlocalizedName("Unknown Unshaped");

		jute = new ItemTerra().setFolder("plants/").setUnlocalizedName("Jute").setCreativeTab(TFCTabs.TFC_MATERIALS);
		juteFiber = new ItemTerra().setFolder("plants/").setUnlocalizedName("Jute Fibre")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		agave = new ItemTerra().setFolder("plants/").setUnlocalizedName("Agave").setCreativeTab(TFCTabs.TFC_MATERIALS);
		sisalFiber = new ItemTerra().setFolder("plants/").setUnlocalizedName("Sisal Fibre")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);

		madderRoot = new ItemTerra().setFolder("plants/").setUnlocalizedName("Madder Root")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		weldRoot = new ItemTerra().setFolder("plants/").setUnlocalizedName("Weld Root")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);
		woadLeaves = new ItemTerra().setFolder("plants/").setUnlocalizedName("Woad Leaves")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);

		flax = new ItemTerra().setFolder("plants/").setUnlocalizedName("Flax").setCreativeTab(TFCTabs.TFC_MATERIALS);
		flaxFiber = new ItemTerra().setFolder("plants/").setUnlocalizedName("Flax Fibre")
				.setCreativeTab(TFCTabs.TFC_MATERIALS);

		Items.reeds.setCreativeTab(null);
		reeds = new ItemReeds().setUnlocalizedName("Reeds").setCreativeTab(TFCTabs.TFC_MATERIALS)
				.setTextureName("reeds");
		// MetalLock = new ItemTerra().setUnlocalizedName("Metal
		// Lock").setCreativeTab(TFCTabs.TFCMisc);
		// MudBrick = new ItemMudBrick().setUnlocalizedName("Mud
		// Brick").setTextureName("Mud Brick Base");

		// Food related items
		setupFood();

		fertilizer = new ItemFertilizer().setUnlocalizedName("Fertilizer").setCreativeTab(TFCTabs.TFC_MATERIALS);

		/** Armor Crafting related items */
		setupArmor();

		Recipes.doors = new Item[] { doorOak, doorAspen, doorBirch, doorChestnut, doorDouglasFir, doorHickory,
				doorMaple, doorAsh, doorPine, doorSequoia, doorSpruce, doorSycamore, doorWhiteCedar, doorWhiteElm,
				doorWillow, doorKapok, doorAcacia, doorPalm, doorEbony, doorFever, doorBaobab,doorLimba, doorMahogany,
				doorTeak,doorBamboo,doorGingko,doorFruitwood };

		Recipes.axes = new Item[] { sedAxe, igInAxe, igExAxe, mMAxe, bismuthBronzeAxe, blackBronzeAxe, blackSteelAxe,
				blueSteelAxe, bronzeAxe, copperAxe, wroughtIronAxe, redSteelAxe, steelAxe };

		Recipes.chisels = new Item[] { bismuthBronzeChisel, blackBronzeChisel, blackSteelChisel, blueSteelChisel,
				bronzeChisel, copperChisel, wroughtIronChisel, redSteelChisel, steelChisel };

		Recipes.saws = new Item[] { bismuthBronzeSaw, blackBronzeSaw, blackSteelSaw, blueSteelSaw, bronzeSaw, copperSaw,
				wroughtIronSaw, redSteelSaw, steelSaw };

		Recipes.knives = new Item[] { stoneKnife, bismuthBronzeKnife, blackBronzeKnife, blackSteelKnife, blueSteelKnife,
				bronzeKnife, copperKnife, wroughtIronKnife, redSteelKnife, steelKnife };

		Recipes.meltedMetal = new Item[] { bismuthUnshaped, bismuthBronzeUnshaped, blackBronzeUnshaped,
				blackSteelUnshaped, blueSteelUnshaped, brassUnshaped, bronzeUnshaped, copperUnshaped, goldUnshaped,
				wroughtIronUnshaped, leadUnshaped, nickelUnshaped, pigIronUnshaped, platinumUnshaped, redSteelUnshaped,
				roseGoldUnshaped, silverUnshaped, steelUnshaped, sterlingSilverUnshaped, tinUnshaped, zincUnshaped,
				highCarbonSteelUnshaped, weakSteelUnshaped, highCarbonBlackSteelUnshaped, highCarbonBlueSteelUnshaped,
				highCarbonRedSteelUnshaped, weakBlueSteelUnshaped, weakRedSteelUnshaped };

		Recipes.hammers = new Item[] { stoneHammer, bismuthBronzeHammer, blackBronzeHammer, blackSteelHammer,
				blueSteelHammer, bronzeHammer, copperHammer, wroughtIronHammer, redSteelHammer, steelHammer };

		Recipes.scythes = new Item[] { bismuthBronzeScythe, blackBronzeScythe, blackSteelScythe, blueSteelScythe,
				bronzeScythe, copperScythe, wroughtIronScythe, redSteelScythe, steelScythe };

		Recipes.picks = new Item[] { bismuthBronzePick, blackBronzePick, blackSteelPick, blueSteelPick, bronzePick,
				copperPick, wroughtIronPick, redSteelPick, steelPick };

		Recipes.proPicks = new Item[] { proPickBismuthBronze, proPickBlackBronze, proPickBlackSteel, proPickBlueSteel,
				proPickBronze, proPickCopper, proPickIron, proPickRedSteel, proPickSteel };

		Recipes.trowels = new Item[] { trowelBismuthBronze, trowelBlackBronze, trowelBlackSteel, trowelBlueSteel,
				trowelBronze, trowelCopper, trowelIron, trowelRedSteel, trowelSteel };

		Recipes.shovels = new Item[] { sedShovel, igInShovel, igExShovel, mMShovel, bismuthBronzeShovel,
				blackBronzeShovel, blackSteelShovel, blueSteelShovel, bronzeShovel, copperShovel, wroughtIronShovel,
				redSteelShovel, steelShovel };

		Recipes.hoes = new Item[] { sedHoe, igInHoe, igExHoe, mMHoe, bismuthBronzeHoe, blackBronzeHoe, blackSteelHoe,
				blueSteelHoe, bronzeHoe, copperHoe, wroughtIronHoe, redSteelHoe, steelHoe };

		Recipes.swords = new Item[] { bismuthBronzeSword, blackBronzeSword, blackSteelSword, blueSteelSword,
				bronzeSword, copperSword, wroughtIronSword, redSteelSword, steelSword };

		Recipes.maces = new Item[] { bismuthBronzeMace, blackBronzeMace, blackSteelMace, blueSteelMace, bronzeMace,
				copperMace, wroughtIronMace, redSteelMace, steelMace };

		Recipes.javelins = new Item[] { sedStoneJavelin, igInStoneJavelin, igExStoneJavelin, mMStoneJavelin,
				bismuthBronzeJavelin, blackBronzeJavelin, blackSteelJavelin, blueSteelJavelin, bronzeJavelin,
				copperJavelin, wroughtIronJavelin, redSteelJavelin, steelJavelin };

		Recipes.spindle = new Item[] { spindle };

		Recipes.gems = new Item[] { gemAgate, gemAmethyst, gemBeryl, gemDiamond, gemEmerald, gemGarnet, gemJade,
				gemJasper, gemOpal, gemRuby, gemSapphire, gemTopaz, gemTourmaline };

		Recipes.seeds = new Item[] { seedsBarley, seedsCabbage, seedsCarrot, seedsGarlic, seedsGreenbean, seedsJute,
				seedsMaize, seedsOat, seedsOnion, seedsPotato, seedsRedBellPepper, seedsRice, seedsRye, seedsSoybean,
				seedsSquash, seedsSugarcane, seedsTomato, seedsWheat, seedsYellowBellPepper, seedsFlax, seedsMadder,
				seedsWeld, seedsWoad, seedsPumpkin, seedsMelon,seedsGrapes,seedsBlackEyedPeas, seedsAgave, seedsCotton };

		((TFCTabs) TFCTabs.TFC_BUILDING).setTabIconItemStack(new ItemStack(TFCBlocks.stoneSedBrick));
		((TFCTabs) TFCTabs.TFC_DECORATION).setTabIconItemStack(new ItemStack(TFCBlocks.flora));
		((TFCTabs) TFCTabs.TFC_DEVICES).setTabIconItem(sluiceItem);
		((TFCTabs) TFCTabs.TFC_POTTERY).setTabIconItemStack(new ItemStack(potteryJug, 1, 1));
		((TFCTabs) TFCTabs.TFC_MISC).setTabIconItem(blueSteelBucketLava);
		((TFCTabs) TFCTabs.TFC_FOODS).setTabIconItem(redApple);
		((TFCTabs) TFCTabs.TFC_TOOLS).setTabIconItem(redSteelAxe);
		((TFCTabs) TFCTabs.TFC_WEAPONS).setTabIconItem(bismuthBronzeSword);
		((TFCTabs) TFCTabs.TFC_ARMOR).setTabIconItem(bronzeHelmet);
		((TFCTabs) TFCTabs.TFC_MATERIALS).setTabIconItem(blueSteelIngot);

		registerItems();
		registerMetals();

		TerraFirmaCraft.LOG.info(new StringBuilder().append("Finished Loading Items").toString());
	}

	/**
	 * 
	 */
	private static void setupFood()
	{
		foodList = new ArrayList<Item>();

		egg = new ItemEgg().setSize(EnumSize.SMALL).setUnlocalizedName("egg").setTextureName("egg")
				.setCreativeTab(TFCTabs.TFC_FOODS);

		// Tastes are Sweet, Sour, Salty, Bitter, Umami

		// Proteins
		porkchopRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true,true)
				.setDecayRate(2.5f).setCanSmoke().setWaterPercentage(0.73f).setHasCookedIcon().setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("Porkchop");
		fishRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true, true)
				.setDecayRate(2.5f).setCanSmoke().setWaterPercentage(0.7f).setHasCookedIcon().setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("Fish");
		beefRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, true, true, true)
				.setDecayRate(2.5f).setCanSmoke().setWaterPercentage(0.73f).setHasCookedIcon().setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("Beef");
		chickenRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true, true)
				.setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setWaterPercentage(0.69f).setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("Chicken");
		soybean = new ItemFoodTFC(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, true).setUnlocalizedName("Soybeans");
		eggCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 25).setDecayRate(2.5f)
				.setUnlocalizedName("Egg Cooked");
		calamariRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, true, true, true)
				.setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F))).setDecayRate(4.0f)
						.setUnlocalizedName("Calamari");
		muttonRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true,true)
				.setDecayRate(2.5f).setCanSmoke().setWaterPercentage(0.73f).setHasCookedIcon().setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("Mutton");
		venisonRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, true, true,true)
				.setDecayRate(2.5f).setCanSmoke().setWaterPercentage(0.73f).setHasCookedIcon().setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("Venison");
		horseMeatRaw = ((ItemFoodTFC) (new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, true, true,true)
				.setDecayRate(2.5f).setCanSmoke().setWaterPercentage(0.73f).setHasCookedIcon().setSmokeAbsorbMultiplier(1F)))
						.setUnlocalizedName("HorseMeat");

		// Dairy
		cheese = ((ItemFoodTFC) (new ItemFoodTFC(EnumFoodGroup.Dairy, 0, 10, 20, 0, 35).setWaterPercentage(0.6f).setDecayRate(0.5f).setCanSmoke()
				.setSmokeAbsorbMultiplier(1F))).setUnlocalizedName("Cheese");

		// Grains
		wheatGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.5f)
				.setUnlocalizedName("Wheat Grain");
		barleyGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 10, 20).setDecayRate(0.5f)
				.setUnlocalizedName("Barley Grain");
		oatGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.5f)
				.setUnlocalizedName("Oat Grain");
		ryeGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 5, 20).setDecayRate(0.5f)
				.setUnlocalizedName("Rye Grain");
		riceGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.5f)
				.setUnlocalizedName("Rice Grain");
		maizeEar = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 5, 20, true).setUnlocalizedName("Maize Ear");
		
		wheatGerm = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.55f)
		.setUnlocalizedName("Wheat Germ");
		barleyGerm = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 10, 20).setDecayRate(0.55f)
		.setUnlocalizedName("Barley Germ");
		ryeGerm = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 5, 20).setDecayRate(0.55f)
		.setUnlocalizedName("Rye Germ");
		riceGerm = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.55f)
		.setUnlocalizedName("Rice Germ");
		cornGerm = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 5, 20, true).setDecayRate(0.55f).setUnlocalizedName("Corn Germ");

		wheatWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Wheat Whole");
		barleyWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 10, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Barley Whole");
		oatWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Oat Whole");
		ryeWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 5, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Rye Whole");
		riceWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Rice Whole");

		wheatGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Wheat Ground");
		barleyGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Barley Ground");
		oatGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Oat Ground");
		ryeGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Rye Ground");
		riceGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Rice Ground");
		cornmealGround = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, false, false).setFolder("food/")
				.setUnlocalizedName("Cornmeal Ground");

		wheatDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setWaterPercentage(0.7f)
				.setUnlocalizedName("Wheat Dough");
		barleyDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setWaterPercentage(0.7f)
				.setUnlocalizedName("Barley Dough");
		oatDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setWaterPercentage(0.7f).setUnlocalizedName("Oat Dough");
		ryeDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, false, false).setWaterPercentage(0.7f).setUnlocalizedName("Rye Dough");
		riceDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setWaterPercentage(0.7f)
				.setUnlocalizedName("Rice Dough");
		cornmealDough = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, false, false).setWaterPercentage(0.7f)
				.setUnlocalizedName("Cornmeal Dough");

		wheatBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20).setWaterPercentage(0.5f).setNutritionAsIfCooked(true).setUnlocalizedName("Wheat Bread");
		barleyBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setWaterPercentage(0.5f).setNutritionAsIfCooked(true).setUnlocalizedName("Barley Bread");
		oatBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20).setWaterPercentage(0.5f).setNutritionAsIfCooked(true).setUnlocalizedName("Oat Bread");
		ryeBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 0, 20).setWaterPercentage(0.5f).setNutritionAsIfCooked(true).setUnlocalizedName("Rye Bread");
		riceBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20).setWaterPercentage(0.5f).setNutritionAsIfCooked(true).setUnlocalizedName("Rice Bread");
		cornBread = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 0, 20).setWaterPercentage(0.5f).setNutritionAsIfCooked(true).setUnlocalizedName("Corn Bread");

		// Vegetables
		tomato = new ItemFoodTFC(EnumFoodGroup.Vegetable, 30, 5, 0, 0, 50, true).setWaterPercentage(0.945f).setUnlocalizedName("Tomato");
		potato = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 10, 15, 20, true).setWaterPercentage(0.8f).setUnlocalizedName("Potato");
		onion = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 25, 0, 0, 20, true)
		{
			@Override
			public void registerIcons(IIconRegister registerer)
			{
				super.registerIcons(registerer);
				this.hasSubtypes = true;
				this.metaIcons = new IIcon[2];
				this.metaIcons[0] = registerer.registerIcon(
						Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
				this.metaIcons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + "Rutabaga");
			}

			@Override
			public IIcon getIconFromDamage(int i)
			{
				if (i == 1)
					return this.metaIcons[1];
				return super.getIconFromDamage(i);
			}
		}.setWaterPercentage(0.89f).setUnlocalizedName(TFCOptions.onionsAreGross ? "Rutabaga" : "Onion");
		cabbage = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 10, 30, true).setWaterPercentage(0.91f).setUnlocalizedName("Cabbage");
		mushroomFoodB = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 60, true)
				.setWaterPercentage(0.9f).setUnlocalizedName("Brown Mushroom");
		mushroomFoodR = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 60, true, true, true, true)
				.setWaterPercentage(0.9f).setUnlocalizedName("Red Mushroom");
		pumpkinGuts = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 30, true).setWaterPercentage(0.85f).setUnlocalizedName("Pumpkin");
		melonSlice = new ItemFoodTFC(EnumFoodGroup.Fruit, 50, 0, 0, 0, 0, true).setWaterPercentage(0.9145f).setWaterRestoreAmount(1200).setUnlocalizedName("Melon");
		pumpkinShell = new ItemPumpkinShell().setUnlocalizedName("Pumpkin Shell");
		garlic = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 0, 10, 20, true).setWaterPercentage(0.59f).setUnlocalizedName("Garlic");
		carrot = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setWaterPercentage(0.88f).setUnlocalizedName("Carrot");
		greenbeans = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setWaterPercentage(0.9f).setWaterPercentage(0.5f).setUnlocalizedName("Greenbeans");
		blackEyedPeas = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 25, true).setWaterPercentage(0.9f).setWaterPercentage(0.5f).setUnlocalizedName("Black-Eyed Peas");
		greenBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, true).setWaterPercentage(0.94f)
				.setUnlocalizedName("Green Bell Pepper");
		yellowBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 15, 0, 0, 0, 20, true).setWaterPercentage(0.94f)
				.setUnlocalizedName("Yellow Bell Pepper");
		redBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setWaterPercentage(0.94f)
				.setUnlocalizedName("Red Bell Pepper");
		squash = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setWaterPercentage(0.86f).setUnlocalizedName("Squash");
		seaWeed = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 80, 10, 10, true).setWaterPercentage(0.5f).setUnlocalizedName("Sea Weed");
		sugar = new ItemFoodTFC(EnumFoodGroup.None, 100, 0, 0, 0, 0, true).setWaterPercentage(0f).setDecayRate(0.01f)
				.setUnlocalizedName("Sugar");
		grapes = new ItemFoodTFC(EnumFoodGroup.Fruit, 50,30,0,0,0, true).setDecayRate(1.8f).setWaterPercentage(0.9f).setUnlocalizedName("Grapes");

		// Fruit are in the foodID range of 50,000
		redApple = new ItemFoodTFC(EnumFoodGroup.Fruit, 45, 5, 0, 5, 0, true).setDecayRate(2.0f).setWaterPercentage(0.86f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[0]);
		banana = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, true).setDecayRate(2.0f).setWaterPercentage(0.75f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[1]);
		orange = new ItemFoodTFC(EnumFoodGroup.Fruit, 50, 30, 0, 10, 0, true).setWaterPercentage(0.87f).setWaterRestoreAmount(900).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[2]);
		greenApple = new ItemFoodTFC(EnumFoodGroup.Fruit, 35, 30, 0, 10, 0, true).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[3]);
		lemon = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 100, 0, 10, 0, true).setWaterPercentage(0.9f).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[4]);
		olive = new ItemFoodTFC(EnumFoodGroup.Fruit, 10, 0, 3, 50, 0, true).setWaterPercentage(0.6f).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[5]);
		cherry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 15, 0, 0, 0, true).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[6]);
		peach = new ItemFoodTFC(EnumFoodGroup.Fruit, 55, 10, 0, 0, 5, true).setWaterPercentage(0.87f).setWaterRestoreAmount(900).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[7]);
		plum = new ItemFoodTFC(EnumFoodGroup.Fruit, 35, 15, 0, 0, 0, true).setWaterPercentage(0.87f).setDecayRate(2.0f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[8]);
		papaya = new ItemFoodTFC(EnumFoodGroup.Fruit, 55, 5, 0, 0, 0, true).setDecayRate(2.0f).setWaterPercentage(0.86f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[9]);
		date = new ItemFoodTFC(EnumFoodGroup.Fruit, 55, 20, 0, 0, 0, true).setDecayRate(2.0f).setWaterPercentage(0.75f)
				.setUnlocalizedName(Global.FRUIT_META_NAMES[10]);

		wintergreenBerry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 0, 0, 20, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Wintergreen Berry");
		blueberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 20, 0, 0, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Blueberry");
		raspberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 35, 15, 0, 5, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Raspberry");
		strawberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 35, 25, 0, 5, 0).setWaterPercentage(0.91f).setDecayRate(2.0f)
				.setUnlocalizedName("Strawberry");
		blackberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 30, 0, 0, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Blackberry");
		bunchberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 5, 0, 0, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Bunchberry");
		cranberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 45, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Cranberry");
		snowberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 10, 0, 0, 90, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Snowberry");
		elderberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 40, 0, 10, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Elderberry");
		gooseberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 40, 0, 0, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Gooseberry");
		cloudberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 40, 0, 30, 0).setWaterPercentage(0.86f).setDecayRate(2.0f)
				.setUnlocalizedName("Cloudberry");

		sandwich = new ItemSandwich().setUnlocalizedName("Sandwich");
		salad = new ItemSalad().setUnlocalizedName("Salad");
		// Soup = new ItemSoup().setUnlocalizedName("Soup");

		sugarcane = new ItemFoodTFC(EnumFoodGroup.None, 70, 0, 0, 0, 0, false, true).setDecayRate(0.75f)
				.setFolder("plants/").setUnlocalizedName("Sugarcane");
		// Hemp = new
		// ItemTerra().setFolder("plants/").setUnlocalizedName("Hemp").setCreativeTab(null);

		seedsWheat = new ItemCustomSeeds(0).setUnlocalizedName("Seeds Wheat");
		seedsMaize = new ItemCustomSeeds(1).setUnlocalizedName("Seeds Maize");
		seedsTomato = new ItemCustomSeeds(2).setUnlocalizedName("Seeds Tomato");
		seedsBarley = new ItemCustomSeeds(3).setUnlocalizedName("Seeds Barley");
		seedsRye = new ItemCustomSeeds(4).setUnlocalizedName("Seeds Rye");
		seedsOat = new ItemCustomSeeds(5).setUnlocalizedName("Seeds Oat");
		seedsRice = new ItemCustomSeeds(6).setUnlocalizedName("Seeds Rice");
		seedsPotato = new ItemCustomSeeds(7).setUnlocalizedName("Seeds Potato");
		seedsOnion = new ItemCustomSeeds(8)
				.setUnlocalizedName(TFCOptions.onionsAreGross ? "Seeds Rutabaga" : "Seeds Onion");
		seedsCabbage = new ItemCustomSeeds(9).setUnlocalizedName("Seeds Cabbage");
		seedsGarlic = new ItemCustomSeeds(10).setUnlocalizedName("Seeds Garlic");
		seedsCarrot = new ItemCustomSeeds(11).setUnlocalizedName("Seeds Carrot");
		seedsYellowBellPepper = new ItemCustomSeeds(12).setUnlocalizedName("Seeds Yellow Bell Pepper");
		seedsRedBellPepper = new ItemCustomSeeds(13).setUnlocalizedName("Seeds Red Bell Pepper");
		seedsSoybean = new ItemCustomSeeds(14).setUnlocalizedName("Seeds Soybean");
		seedsGreenbean = new ItemCustomSeeds(15).setUnlocalizedName("Seeds Greenbean");
		seedsSquash = new ItemCustomSeeds(16).setUnlocalizedName("Seeds Squash");
		seedsJute = new ItemCustomSeeds(17).setUnlocalizedName("Seeds Jute");
		seedsSugarcane = new ItemCustomSeeds(18).setUnlocalizedName("Seeds Sugarcane");
		seedsFlax = new ItemCustomSeeds(19).setUnlocalizedName("Seeds Flax");
		seedsMadder = new ItemCustomSeeds(20).setUnlocalizedName("Seeds Madder");
		seedsWeld = new ItemCustomSeeds(21).setUnlocalizedName("Seeds Weld");
		seedsWoad = new ItemCustomSeeds(22).setUnlocalizedName("Seeds Woad");
		seedsPumpkin= new ItemCustomSeeds(23).setUnlocalizedName("Seeds Pumpkin");
		seedsMelon = new ItemCustomSeeds(24).setUnlocalizedName("Seeds Melon");
		seedsGrapes = new ItemCustomSeeds(25).setUnlocalizedName("Seeds Grapes");
		seedsBlackEyedPeas = new ItemCustomSeeds(26).setUnlocalizedName("Seeds Black-Eyed Peas");
		seedsAgave = new ItemCustomSeeds(27).setUnlocalizedName("Seeds Agave");
		seedsCotton = new ItemCustomSeeds(28).setUnlocalizedName("Seeds Cotton");
		// SeedsHemp = new ItemCustomSeeds(22).setUnlocalizedName("Seeds Hemp");

		fruitTreeSapling = new ItemFruitTreeSapling().setUnlocalizedName("FruitSapling");

		// mushroom is a food now, with foodID 61
		// pumpkin is a food now, id = 61
		// melon is a food, not currently obtainable. id = 62. See ItemFoodBlock
		/*
		 * WintergreenLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName(
		 * "Wintergreen Leaf").setCreativeTab(null); BlueberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Blueberry Leaf"
		 * ).setCreativeTab(null); RaspberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Raspberry Leaf"
		 * ).setCreativeTab(null); StrawberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Strawberry Leaf"
		 * ).setCreativeTab(null); BlackberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Blackberry Leaf"
		 * ).setCreativeTab(null); BunchberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Bunchberry Leaf"
		 * ).setCreativeTab(null); CranberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Cranberry Leaf"
		 * ).setCreativeTab(null); SnowberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Snowberry Leaf"
		 * ).setCreativeTab(null); ElderberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Elderberry Leaf"
		 * ).setCreativeTab(null); GooseberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Gooseberry Leaf"
		 * ).setCreativeTab(null); CloudberryLeaf = new
		 * ItemTerra().setFolder("plants/").setUnlocalizedName("Cloudberry Leaf"
		 * ).setCreativeTab(null);
		 */
	}

	private static void registerMetals()
	{
		Global.BISMUTH = new Metal("Bismuth", TFCItems.bismuthIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.bismuthUnshaped)
				.addValidPartialMold(TFCItems.bismuthUnshaped, 2, TFCItems.bismuthUnshaped, 1, 2);
		Global.BISMUTHBRONZE = new Metal("Bismuth Bronze", TFCItems.bismuthBronzeIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.bismuthBronzeUnshaped)
				.addValidPartialMold(TFCItems.clayMoldSheet, 4, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.clayMoldAxe, 4, TFCItems.clayMoldAxe, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldKnife, 4, TFCItems.clayMoldKnife, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldSword, 4, TFCItems.clayMoldSword, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldMace,4, TFCItems.clayMoldMace,4,2)
				.addValidPartialMold(TFCItems.clayMoldChisel,4, TFCItems.clayMoldChisel,4,2)
				.addValidPartialMold(TFCItems.clayMoldScythe,4, TFCItems.clayMoldScythe,4,2)
				.addValidPartialMold(TFCItems.clayMoldHammer,4, TFCItems.clayMoldHammer,4,2)
				.addValidPartialMold(TFCItems.clayMoldJavelin,4, TFCItems.clayMoldJavelin,4,2)
				.addValidPartialMold(TFCItems.clayMoldTrowel,4, TFCItems.clayMoldTrowel,4,2)
				.addValidPartialMold(TFCItems.clayMoldPick,4, TFCItems.clayMoldPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldProPick,4, TFCItems.clayMoldProPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldSaw,4, TFCItems.clayMoldSaw,4,2)
				.addValidPartialMold(TFCItems.clayMoldShovel,4, TFCItems.clayMoldShovel,4,2)
				.addValidPartialMold(TFCItems.clayMoldHoe,4, TFCItems.clayMoldHoe,4,2)
				.addValidPartialMold(TFCItems.clayMoldSheet, 4, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.bismuthBronzeUnshaped, 2, TFCItems.bismuthBronzeUnshaped, 1, 2);
		;
		Global.BLACKBRONZE = new Metal("Black Bronze", TFCItems.blackBronzeIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.blackBronzeUnshaped)
				.addValidPartialMold(TFCItems.clayMoldSheet, 5, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.clayMoldAxe, 5, TFCItems.clayMoldAxe, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldKnife, 5, TFCItems.clayMoldKnife, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldSword, 5, TFCItems.clayMoldSword, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldMace,5, TFCItems.clayMoldMace,4,2)
				.addValidPartialMold(TFCItems.clayMoldChisel,5, TFCItems.clayMoldChisel,4,2)
				.addValidPartialMold(TFCItems.clayMoldScythe,5, TFCItems.clayMoldScythe,4,2)
				.addValidPartialMold(TFCItems.clayMoldHammer,5, TFCItems.clayMoldHammer,4,2)
				.addValidPartialMold(TFCItems.clayMoldJavelin,5, TFCItems.clayMoldJavelin,4,2)
				.addValidPartialMold(TFCItems.clayMoldTrowel,5, TFCItems.clayMoldTrowel,4,2)
				.addValidPartialMold(TFCItems.clayMoldPick,5, TFCItems.clayMoldPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldProPick,5, TFCItems.clayMoldProPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldSaw,5, TFCItems.clayMoldSaw,4,2)
				.addValidPartialMold(TFCItems.clayMoldShovel,5, TFCItems.clayMoldShovel,4,2)
				.addValidPartialMold(TFCItems.clayMoldHoe,5, TFCItems.clayMoldHoe,4,2)
				.addValidPartialMold(TFCItems.clayMoldSheet, 5, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.blackBronzeUnshaped, 2, TFCItems.blackBronzeUnshaped, 1, 2);
		;
		Global.BLACKSTEEL = new Metal("Black Steel", TFCItems.blackSteelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.blackSteelUnshaped)
				.addValidPartialMold(TFCItems.blackSteelUnshaped, 2, TFCItems.blackSteelUnshaped, 1, 2);
		Global.BLUESTEEL = new Metal("Blue Steel", TFCItems.blueSteelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.blueSteelUnshaped)
				.addValidPartialMold(TFCItems.blueSteelUnshaped, 2, TFCItems.blueSteelUnshaped, 1, 2);
		Global.BRASS = new Metal("Brass", TFCItems.brassIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.brassUnshaped)
				.addValidPartialMold(TFCItems.brassUnshaped, 2, TFCItems.brassUnshaped, 1, 2);
		Global.BRONZE = new Metal("Bronze", TFCItems.bronzeIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.bronzeUnshaped)
				.addValidPartialMold(TFCItems.clayMoldSheet, 3, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.clayMoldAxe, 3, TFCItems.clayMoldAxe, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldKnife, 3, TFCItems.clayMoldKnife, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldSword, 3, TFCItems.clayMoldSword, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldMace,3, TFCItems.clayMoldMace,4,2)
				.addValidPartialMold(TFCItems.clayMoldChisel,3, TFCItems.clayMoldChisel,4,2)
				.addValidPartialMold(TFCItems.clayMoldScythe,3, TFCItems.clayMoldScythe,4,2)
				.addValidPartialMold(TFCItems.clayMoldHammer,3, TFCItems.clayMoldHammer,4,2)
				.addValidPartialMold(TFCItems.clayMoldJavelin,3, TFCItems.clayMoldJavelin,4,2)
				.addValidPartialMold(TFCItems.clayMoldTrowel,3, TFCItems.clayMoldTrowel,4,2)
				.addValidPartialMold(TFCItems.clayMoldPick,3, TFCItems.clayMoldPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldProPick,3, TFCItems.clayMoldProPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldSaw,3, TFCItems.clayMoldSaw,4,2)
				.addValidPartialMold(TFCItems.clayMoldShovel,3, TFCItems.clayMoldShovel,4,2)
				.addValidPartialMold(TFCItems.clayMoldHoe,3, TFCItems.clayMoldHoe,4,2)
				.addValidPartialMold(TFCItems.clayMoldSheet, 3, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.bronzeUnshaped, 2, TFCItems.bronzeUnshaped, 1, 2);
		Global.COPPER = new Metal("Copper", TFCItems.copperIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.copperUnshaped)
				.addValidPartialMold(TFCItems.clayMoldSheet, 2, TFCItems.clayMoldSheet, 5, 2)
				.addValidPartialMold(TFCItems.clayMoldAxe, 2, TFCItems.clayMoldAxe, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldKnife, 2, TFCItems.clayMoldKnife, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldSword, 2, TFCItems.clayMoldSword, 4, 2)
				.addValidPartialMold(TFCItems.clayMoldMace,2,TFCItems.clayMoldMace,4,2)
				.addValidPartialMold(TFCItems.clayMoldChisel,2,TFCItems.clayMoldChisel,4,2)
				.addValidPartialMold(TFCItems.clayMoldScythe,2,TFCItems.clayMoldScythe,4,2)
				.addValidPartialMold(TFCItems.clayMoldHammer,2,TFCItems.clayMoldHammer,4,2)
				.addValidPartialMold(TFCItems.clayMoldJavelin,2,TFCItems.clayMoldJavelin,4,2)
				.addValidPartialMold(TFCItems.clayMoldTrowel,2,TFCItems.clayMoldTrowel,4,2)
				.addValidPartialMold(TFCItems.clayMoldPick,2,TFCItems.clayMoldPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldProPick,2,TFCItems.clayMoldProPick,4,2)
				.addValidPartialMold(TFCItems.clayMoldSaw,2,TFCItems.clayMoldSaw,4,2)
				.addValidPartialMold(TFCItems.clayMoldShovel,2,TFCItems.clayMoldShovel,4,2)
				.addValidPartialMold(TFCItems.clayMoldHoe,2,TFCItems.clayMoldHoe,4,2)
				.addValidPartialMold(TFCItems.copperUnshaped, 2, TFCItems.copperUnshaped, 1, 2);
		Global.GOLD = new Metal("Gold", TFCItems.goldIngot).addValidMold(TFCItems.ceramicMold, TFCItems.goldUnshaped)
				.addValidPartialMold(TFCItems.goldUnshaped, 2, TFCItems.goldUnshaped, 1, 2);
		Global.WROUGHTIRON = new Metal("Wrought Iron", TFCItems.wroughtIronIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.wroughtIronUnshaped)
				.addValidPartialMold(TFCItems.wroughtIronUnshaped, 2, TFCItems.wroughtIronUnshaped, 1, 2);
		Global.LEAD = new Metal("Lead", TFCItems.leadIngot).addValidMold(TFCItems.ceramicMold, TFCItems.leadUnshaped)
				.addValidPartialMold(TFCItems.leadUnshaped, 2, TFCItems.leadUnshaped, 1, 2);
		Global.NICKEL = new Metal("Nickel", TFCItems.nickelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.nickelUnshaped)
				.addValidPartialMold(TFCItems.nickelUnshaped, 2, TFCItems.nickelUnshaped, 1, 2);
		Global.PIGIRON = new Metal("Pig Iron", TFCItems.pigIronIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.pigIronUnshaped)
				.addValidPartialMold(TFCItems.pigIronUnshaped, 2, TFCItems.pigIronUnshaped, 1, 2);
		Global.PLATINUM = new Metal("Platinum", TFCItems.platinumIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.platinumUnshaped)
				.addValidPartialMold(TFCItems.platinumUnshaped, 2, TFCItems.platinumUnshaped, 1, 2);
		Global.REDSTEEL = new Metal("Red Steel", TFCItems.redSteelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.redSteelUnshaped)
				.addValidPartialMold(TFCItems.redSteelUnshaped, 2, TFCItems.redSteelUnshaped, 1, 2);
		Global.ROSEGOLD = new Metal("Rose Gold", TFCItems.roseGoldIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.roseGoldUnshaped)
				.addValidPartialMold(TFCItems.roseGoldUnshaped, 2, TFCItems.roseGoldUnshaped, 1, 2);
		Global.SILVER = new Metal("Silver", TFCItems.silverIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.silverUnshaped)
				.addValidPartialMold(TFCItems.silverUnshaped, 2, TFCItems.silverUnshaped, 1, 2);
		Global.STEEL = new Metal("Steel", TFCItems.steelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.steelUnshaped)
				.addValidPartialMold(TFCItems.steelUnshaped, 2, TFCItems.steelUnshaped, 1, 2);
		Global.STERLINGSILVER = new Metal("Sterling Silver", TFCItems.sterlingSilverIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.sterlingSilverUnshaped)
				.addValidPartialMold(TFCItems.sterlingSilverUnshaped, 2, TFCItems.sterlingSilverUnshaped, 1, 2);
		Global.TIN = new Metal("Tin", TFCItems.tinIngot).addValidMold(TFCItems.ceramicMold, TFCItems.tinUnshaped)
				.addValidPartialMold(TFCItems.tinUnshaped, 2, TFCItems.tinUnshaped, 1, 2);
		Global.ZINC = new Metal("Zinc", TFCItems.zincIngot).addValidMold(TFCItems.ceramicMold, TFCItems.zincUnshaped)
				.addValidPartialMold(TFCItems.zincUnshaped, 2, TFCItems.zincUnshaped, 1, 2);
		Global.WEAKSTEEL = new Metal("Weak Steel", TFCItems.weakSteelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.weakSteelUnshaped)
				.addValidPartialMold(TFCItems.weakSteelUnshaped, 2, TFCItems.weakSteelUnshaped, 1, 2);
		Global.HCBLACKSTEEL = new Metal("HC Black Steel", TFCItems.highCarbonBlackSteelIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.highCarbonBlackSteelUnshaped)
				.addValidPartialMold(TFCItems.highCarbonBlackSteelUnshaped, 2, TFCItems.highCarbonBlackSteelUnshaped, 1, 2);
		Global.WEAKREDSTEEL = new Metal("Weak Red Steel", TFCItems.weakRedSteelIngot).addValidMold(TFCItems.ceramicMold,
				TFCItems.weakRedSteelUnshaped)
				.addValidPartialMold(TFCItems.weakRedSteelUnshaped, 2, TFCItems.weakRedSteelUnshaped, 1, 2);
		Global.HCREDSTEEL = new Metal("HC Red Steel", TFCItems.highCarbonRedSteelIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.highCarbonRedSteelUnshaped)
				.addValidPartialMold(TFCItems.highCarbonRedSteelUnshaped, 2, TFCItems.highCarbonRedSteelUnshaped, 1, 2);
		Global.WEAKBLUESTEEL = new Metal("Weak Blue Steel", TFCItems.weakBlueSteelIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.weakBlueSteelUnshaped)
				.addValidPartialMold(TFCItems.weakBlueSteelUnshaped, 2, TFCItems.weakBlueSteelUnshaped, 1, 2);
		Global.HCBLUESTEEL = new Metal("HC Blue Steel", TFCItems.highCarbonBlueSteelIngot)
				.addValidMold(TFCItems.ceramicMold, TFCItems.highCarbonBlueSteelUnshaped)
				.addValidPartialMold(TFCItems.highCarbonBlueSteelUnshaped, 2, TFCItems.highCarbonBlueSteelUnshaped, 1, 2);
		Global.UNKNOWN = new Metal("Unknown", TFCItems.unknownIngot, false).addValidMold(TFCItems.ceramicMold,
				TFCItems.unknownUnshaped)
				.addValidPartialMold(TFCItems.unknownUnshaped, 2, TFCItems.unknownUnshaped, 1, 2);

		// Non-Metals
		Global.SILICA = new Metal("Silica", TFCItems.silica, false);
		Global.LIME = new Metal("Lime", TFCItems.lime, false);
		Global.GLASS = new Metal("Glass", TFCItems.solidifiedGlass, true).addValidPartialMold(TFCItems.clayMoldSheet, 6,
				TFCItems.clayMoldSheet, 5, 2).addValidPartialMold(TFCItems.clayBlowpipe, 2, TFCItems.clayBlowpipe, 1, 2);
		Global.SODA = new Metal("Soda", TFCItems.soda, false);

		// Garbage
		Global.GARBAGE = new Metal("Garbage", TFCItems.unknownIngot, false).addValidMold(TFCItems.ceramicMold,
				TFCItems.unknownUnshaped);

		MetalRegistry.instance.addMetal(Global.BISMUTH, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BLACKBRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BLACKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.BLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.BRASS, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.COPPER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.GOLD, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.WROUGHTIRON, Alloy.EnumTier.TierIII);
		MetalRegistry.instance.addMetal(Global.LEAD, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.NICKEL, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.PIGIRON, Alloy.EnumTier.TierIV);
		MetalRegistry.instance.addMetal(Global.PLATINUM, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.REDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.ROSEGOLD, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.SILVER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.STEEL, Alloy.EnumTier.TierIV);
		MetalRegistry.instance.addMetal(Global.STERLINGSILVER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.TIN, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.ZINC, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.WEAKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCBLACKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.WEAKREDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCREDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCBLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.UNKNOWN, Alloy.EnumTier.TierI);

		// Non Metals
		MetalRegistry.instance.addMetal(Global.SILICA, Alloy.EnumTier.Tier0);
		MetalRegistry.instance.addMetal(Global.GLASS, Alloy.EnumTier.Tier0);
		MetalRegistry.instance.addMetal(Global.LIME, Alloy.EnumTier.Tier0);
		MetalRegistry.instance.addMetal(Global.SODA, Alloy.EnumTier.Tier0);

		/**
		 * Added the +-0.01 tolerance to hopefully negate most rounding errors
		 */
		Alloy bronze = new Alloy(Global.BRONZE, Alloy.EnumTier.TierI);
		bronze.addIngred(Global.COPPER, 87.99f, 92.01f);
		bronze.addIngred(Global.TIN, 7.99f, 12.01f);
		AlloyManager.INSTANCE.addAlloy(bronze);

		Alloy brass = new Alloy(Global.BRASS, Alloy.EnumTier.TierI);
		brass.addIngred(Global.COPPER, 69.99f, 80.01f);
		brass.addIngred(Global.ZINC, 19.99f, 30.01f);
		AlloyManager.INSTANCE.addAlloy(brass);

		Alloy roseGold = new Alloy(Global.ROSEGOLD, Alloy.EnumTier.TierI);
		roseGold.addIngred(Global.GOLD, 69.99f, 85.01f);
		roseGold.addIngred(Global.COPPER, 14.99f, 30.01f);
		AlloyManager.INSTANCE.addAlloy(roseGold);

		Alloy blackBronze = new Alloy(Global.BLACKBRONZE, Alloy.EnumTier.TierI);
		blackBronze.addIngred(Global.GOLD, 9.99f, 25.01f);
		blackBronze.addIngred(Global.COPPER, 49.99f, 70.01f);
		blackBronze.addIngred(Global.SILVER, 9.99f, 25.01f);
		AlloyManager.INSTANCE.addAlloy(blackBronze);

		Alloy bismuthBronze = new Alloy(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		bismuthBronze.addIngred(Global.ZINC, 19.99f, 30.01f);
		bismuthBronze.addIngred(Global.COPPER, 49.99f, 65.01f);
		bismuthBronze.addIngred(Global.BISMUTH, 9.99f, 20.01f);
		AlloyManager.INSTANCE.addAlloy(bismuthBronze);

		Alloy sterlingSilver = new Alloy(Global.STERLINGSILVER, Alloy.EnumTier.TierI);
		sterlingSilver.addIngred(Global.SILVER, 59.99f, 80.01f);
		sterlingSilver.addIngred(Global.COPPER, 19.99f, 40.01f);
		AlloyManager.INSTANCE.addAlloy(sterlingSilver);

		Alloy weakSteel = new Alloy(Global.WEAKSTEEL, Alloy.EnumTier.TierIII);
		weakSteel.addIngred(Global.STEEL, 49.99f, 70.01f);
		weakSteel.addIngred(Global.NICKEL, 14.99f, 25.01f);
		weakSteel.addIngred(Global.BLACKBRONZE, 14.99f, 25.01f);
		AlloyManager.INSTANCE.addAlloy(weakSteel);

		Alloy weakRedSteel = new Alloy(Global.WEAKREDSTEEL, Alloy.EnumTier.TierIII);
		weakRedSteel.addIngred(Global.BLACKSTEEL, 49.99f, 55.01f);
		weakRedSteel.addIngred(Global.ROSEGOLD, 9.99f, 15.01f);
		weakRedSteel.addIngred(Global.BRASS, 9.99f, 15.01f);
		weakRedSteel.addIngred(Global.STEEL, 19.99f, 25.01f);
		AlloyManager.INSTANCE.addAlloy(weakRedSteel);

		Alloy weakBlueSteel = new Alloy(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierIII);
		weakBlueSteel.addIngred(Global.BLACKSTEEL, 49.99f, 55.01f);
		weakBlueSteel.addIngred(Global.BISMUTHBRONZE, 9.99f, 15.01f);
		weakBlueSteel.addIngred(Global.STERLINGSILVER, 9.99f, 15.01f);
		weakBlueSteel.addIngred(Global.STEEL, 19.99f, 25.01f);
		AlloyManager.INSTANCE.addAlloy(weakBlueSteel);

		Alloy silicaGlass = new Alloy(Global.GLASS, Alloy.EnumTier.Tier0);
		silicaGlass.addIngred(Global.SILICA, 69.99f, 75.01f);
		silicaGlass.addIngred(Global.LIME, 9.99f, 15.01f);
		silicaGlass.addIngred(Global.SODA, 9.99f, 15.01f);
		AlloyManager.INSTANCE.addAlloy(silicaGlass);

		/*
		 * Alloy silicaGlass2 = new Alloy(Global.GLASS, Alloy.EnumTier.Tier0);
		 * silicaGlass.addIngred(Global.GLASS,99.99f,100.01f);
		 * AlloyManager.INSTANCE.addAlloy(silicaGlass2);
		 */
	}

	public static void setupArmor()
	{
		String[] names = { "Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Bronze", "Copper",
				"Wrought Iron", "Red Steel", "Steel" };
		String[] namesNSO = { "Brass", "Gold", "Lead", "Nickel", "Pig Iron", "Platinum", "Silver", "Sterling Silver" };
		CommonProxy proxy = TerraFirmaCraft.proxy;
		int i = 0;

		bismuthSheet = ((ItemMetalSheet) new ItemMetalSheet(0).setUnlocalizedName("Bismuth Sheet")).setMetal("Bismuth",
				200);
		bismuthBronzeSheet = ((ItemMetalSheet) new ItemMetalSheet(1).setUnlocalizedName("Bismuth Bronze Sheet"))
				.setMetal("Bismuth Bronze", 200);
		blackBronzeSheet = ((ItemMetalSheet) new ItemMetalSheet(2).setUnlocalizedName("Black Bronze Sheet"))
				.setMetal("Black Bronze", 200);
		blackSteelSheet = ((ItemMetalSheet) new ItemMetalSheet(3).setUnlocalizedName("Black Steel Sheet"))
				.setMetal("Black Steel", 200);
		blueSteelSheet = ((ItemMetalSheet) new ItemMetalSheet(4).setUnlocalizedName("Blue Steel Sheet"))
				.setMetal("Blue Steel", 200);
		bronzeSheet = ((ItemMetalSheet) new ItemMetalSheet(6).setUnlocalizedName("Bronze Sheet")).setMetal("Bronze",
				200);
		copperSheet = ((ItemMetalSheet) new ItemMetalSheet(7).setUnlocalizedName("Copper Sheet")).setMetal("Copper",
				200);
		wroughtIronSheet = ((ItemMetalSheet) new ItemMetalSheet(9).setUnlocalizedName("Wrought Iron Sheet"))
				.setMetal("Wrought Iron", 200);
		redSteelSheet = ((ItemMetalSheet) new ItemMetalSheet(14).setUnlocalizedName("Red Steel Sheet"))
				.setMetal("Red Steel", 200);
		roseGoldSheet = ((ItemMetalSheet) new ItemMetalSheet(15).setUnlocalizedName("Rose Gold Sheet"))
				.setMetal("Rose Gold", 200);
		steelSheet = ((ItemMetalSheet) new ItemMetalSheet(17).setUnlocalizedName("Steel Sheet")).setMetal("Steel", 200);
		tinSheet = ((ItemMetalSheet) new ItemMetalSheet(19).setUnlocalizedName("Tin Sheet")).setMetal("Tin", 200);
		zincSheet = ((ItemMetalSheet) new ItemMetalSheet(20).setUnlocalizedName("Zinc Sheet")).setMetal("Zinc", 200);

		bismuthSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(0).setUnlocalizedName("Bismuth Double Sheet"))
				.setMetal("Bismuth", 400);
		bismuthBronzeSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(1)
				.setUnlocalizedName("Bismuth Bronze Double Sheet")).setMetal("Bismuth Bronze", 400);
		blackBronzeSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(2)
				.setUnlocalizedName("Black Bronze Double Sheet")).setMetal("Black Bronze", 400);
		blackSteelSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(3).setUnlocalizedName("Black Steel Double Sheet"))
				.setMetal("Black Steel", 400);
		blueSteelSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(4).setUnlocalizedName("Blue Steel Double Sheet"))
				.setMetal("Blue Steel", 400);
		bronzeSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(6).setUnlocalizedName("Bronze Double Sheet"))
				.setMetal("Bronze", 400);
		copperSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(7).setUnlocalizedName("Copper Double Sheet"))
				.setMetal("Copper", 400);
		wroughtIronSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(9)
				.setUnlocalizedName("Wrought Iron Double Sheet")).setMetal("Wrought Iron", 400);
		redSteelSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(14).setUnlocalizedName("Red Steel Double Sheet"))
				.setMetal("Red Steel", 400);
		roseGoldSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(15).setUnlocalizedName("Rose Gold Double Sheet"))
				.setMetal("Rose Gold", 400);
		steelSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(17).setUnlocalizedName("Steel Double Sheet"))
				.setMetal("Steel", 400);
		tinSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(19).setUnlocalizedName("Tin Double Sheet"))
				.setMetal("Tin", 400);
		zincSheet2x = ((ItemMetalSheet2x) new ItemMetalSheet2x(20).setUnlocalizedName("Zinc Double Sheet"))
				.setMetal("Zinc", 400);

		i = 0;
		brassSheet = new ItemMetalSheet(5).setMetal("Brass", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		goldSheet = new ItemMetalSheet(8).setMetal("Gold", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		leadSheet = new ItemMetalSheet(10).setMetal("Lead", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		nickelSheet = new ItemMetalSheet(11).setMetal("Nickel", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		pigIronSheet = new ItemMetalSheet(12).setMetal("Pig Iron", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		platinumSheet = new ItemMetalSheet(13).setMetal("Platinum", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		silverSheet = new ItemMetalSheet(16).setMetal("Silver", 200).setUnlocalizedName(namesNSO[i++] + " Sheet");
		sterlingSilverSheet = new ItemMetalSheet(18).setMetal("Sterling Silver", 200)
				.setUnlocalizedName(namesNSO[i++] + " Sheet");

		i = 0;
		brassSheet2x = new ItemMetalSheet2x(5).setMetal("Brass", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		goldSheet2x = new ItemMetalSheet2x(8).setMetal("Gold", 400).setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		leadSheet2x = new ItemMetalSheet2x(10).setMetal("Lead", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		nickelSheet2x = new ItemMetalSheet2x(1).setMetal("Nickel", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		pigIronSheet2x = new ItemMetalSheet2x(12).setMetal("Pig Iron", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		platinumSheet2x = new ItemMetalSheet2x(13).setMetal("Platinum", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		silverSheet2x = new ItemMetalSheet2x(16).setMetal("Silver", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");
		sterlingSilverSheet2x = new ItemMetalSheet2x(18).setMetal("Sterling Silver", 400)
				.setUnlocalizedName(namesNSO[i++] + " Double Sheet");

		i = 0;
		bismuthBronzeUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		blackBronzeUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Black Bronze", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		blackSteelUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Black Steel", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		blueSteelUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Blue Steel", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		bronzeUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Bronze", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		copperUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Copper", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		wroughtIronUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Wrought Iron", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		redSteelUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Red Steel", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");
		i++;
		steelUnfinishedBoots = new ItemUnfinishedArmor().setMetal("Steel", 3)
				.setUnlocalizedName(names[i] + " Unfinished Boots");

		i = 0;
		bismuthBronzeBoots = new ItemTFCArmorBoots(Armor.bismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 3,
				50, 0).setUnlocalizedName(names[i] + " Boots");
		i++;
		blackBronzeBoots = new ItemTFCArmorBoots(Armor.blackBronzePlate, proxy.getArmorRenderID("blackbronze"), 3, 50,
				0).setUnlocalizedName(names[i] + " Boots");
		i++;
		blackSteelBoots = new ItemTFCArmorBoots(Armor.blackSteelPlate, proxy.getArmorRenderID("blacksteel"), 3, 50, 0)
				.setUnlocalizedName(names[i] + " Boots");
		i++;
		blueSteelBoots = new ItemTFCArmorBoots(Armor.blueSteelPlate, proxy.getArmorRenderID("bluesteel"), 3, 50, 0)
				.setUnlocalizedName(names[i] + " Boots");
		i++;
		bronzeBoots = new ItemTFCArmorBoots(Armor.bronzePlate, proxy.getArmorRenderID("bronze"), 3, 50, 0)
				.setUnlocalizedName(names[i] + " Boots");
		i++;
		copperBoots = new ItemTFCArmorBoots(Armor.copperPlate, proxy.getArmorRenderID("copper"), 3, 50, 0)
				.setUnlocalizedName(names[i] + " Boots");
		i++;
		wroughtIronBoots = new ItemTFCArmorBoots(Armor.wroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 3, 50,
				0).setUnlocalizedName(names[i] + " Boots");
		i++;
		redSteelBoots = new ItemTFCArmorBoots(Armor.redSteelPlate, proxy.getArmorRenderID("redsteel"), 3, 50, 0)
				.setUnlocalizedName(names[i] + " Boots");
		i++;
		steelBoots = new ItemTFCArmorBoots(Armor.steelPlate, proxy.getArmorRenderID("steel"), 3, 50, 0)
				.setUnlocalizedName(names[i] + " Boots");

		i = 0;
		bismuthBronzeUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		blackBronzeUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Black Bronze", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		blackSteelUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Black Steel", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		blueSteelUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Blue Steel", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		bronzeUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Bronze", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		copperUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Copper", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		wroughtIronUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Wrought Iron", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		redSteelUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Red Steel", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");
		i++;
		steelUnfinishedGreaves = new ItemUnfinishedArmor().setMetal("Steel", 2)
				.setUnlocalizedName(names[i] + " Unfinished Greaves");

		i = 0;
		bismuthBronzeGreaves = new ItemTFCArmor(Armor.bismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 2,
				50, 1).setUnlocalizedName(names[i] + " Greaves");
		i++;
		blackBronzeGreaves = new ItemTFCArmor(Armor.blackBronzePlate, proxy.getArmorRenderID("blackbronze"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		blackSteelGreaves = new ItemTFCArmor(Armor.blackSteelPlate, proxy.getArmorRenderID("blacksteel"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		blueSteelGreaves = new ItemTFCArmor(Armor.blueSteelPlate, proxy.getArmorRenderID("bluesteel"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		bronzeGreaves = new ItemTFCArmor(Armor.bronzePlate, proxy.getArmorRenderID("bronze"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		copperGreaves = new ItemTFCArmor(Armor.copperPlate, proxy.getArmorRenderID("copper"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		wroughtIronGreaves = new ItemTFCArmor(Armor.wroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		redSteelGreaves = new ItemTFCArmor(Armor.redSteelPlate, proxy.getArmorRenderID("redsteel"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");
		i++;
		steelGreaves = new ItemTFCArmor(Armor.steelPlate, proxy.getArmorRenderID("steel"), 2, 50, 1)
				.setUnlocalizedName(names[i] + " Greaves");

		i = 0;
		bismuthBronzeUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		blackBronzeUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Black Bronze", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		blackSteelUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Black Steel", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		blueSteelUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Blue Steel", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		bronzeUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Bronze", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		copperUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Copper", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		wroughtIronUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Wrought Iron", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		redSteelUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Red Steel", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");
		i++;
		steelUnfinishedChestplate = new ItemUnfinishedArmor().setMetal("Steel", 1)
				.setUnlocalizedName(names[i] + " Unfinished Chestplate");

		i = 0;
		bismuthBronzeChestplate = new ItemTFCArmor(Armor.bismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 1,
				50, 2).setUnlocalizedName(names[i] + " Chestplate");
		i++;
		blackBronzeChestplate = new ItemTFCArmor(Armor.blackBronzePlate, proxy.getArmorRenderID("blackbronze"), 1, 50,
				2).setUnlocalizedName(names[i] + " Chestplate");
		i++;
		blackSteelChestplate = new ItemTFCArmor(Armor.blackSteelPlate, proxy.getArmorRenderID("blacksteel"), 1, 50, 2)
				.setUnlocalizedName(names[i] + " Chestplate");
		i++;
		blueSteelChestplate = new ItemTFCArmor(Armor.blueSteelPlate, proxy.getArmorRenderID("bluesteel"), 1, 50, 2)
				.setUnlocalizedName(names[i] + " Chestplate");
		i++;
		bronzeChestplate = new ItemTFCArmor(Armor.bronzePlate, proxy.getArmorRenderID("bronze"), 1, 50, 2)
				.setUnlocalizedName(names[i] + " Chestplate");
		i++;
		copperChestplate = new ItemTFCArmor(Armor.copperPlate, proxy.getArmorRenderID("copper"), 1, 50, 2)
				.setUnlocalizedName(names[i] + " Chestplate");
		i++;
		wroughtIronChestplate = new ItemTFCArmor(Armor.wroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 1, 50,
				2).setUnlocalizedName(names[i] + " Chestplate");
		i++;
		redSteelChestplate = new ItemTFCArmor(Armor.redSteelPlate, proxy.getArmorRenderID("redsteel"), 1, 50, 2)
				.setUnlocalizedName(names[i] + " Chestplate");
		i++;
		steelChestplate = new ItemTFCArmor(Armor.steelPlate, proxy.getArmorRenderID("steel"), 1, 50, 2)
				.setUnlocalizedName(names[i] + " Chestplate");

		i = 0;
		bismuthBronzeUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		blackBronzeUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Black Bronze", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		blackSteelUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Black Steel", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		blueSteelUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Blue Steel", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		bronzeUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Bronze", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		copperUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Copper", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		wroughtIronUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Wrought Iron", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		redSteelUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Red Steel", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");
		i++;
		steelUnfinishedHelmet = new ItemUnfinishedArmor().setMetal("Steel", 0)
				.setUnlocalizedName(names[i] + " Unfinished Helmet");

		i = 0;
		bismuthBronzeHelmet = new ItemTFCArmor(Armor.bismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 0, 50,
				3).setUnlocalizedName(names[i] + " Helmet");
		i++;
		blackBronzeHelmet = new ItemTFCArmor(Armor.blackBronzePlate, proxy.getArmorRenderID("blackbronze"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		blackSteelHelmet = new ItemTFCArmor(Armor.blackSteelPlate, proxy.getArmorRenderID("blacksteel"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		blueSteelHelmet = new ItemTFCArmor(Armor.blueSteelPlate, proxy.getArmorRenderID("bluesteel"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		bronzeHelmet = new ItemTFCArmor(Armor.bronzePlate, proxy.getArmorRenderID("bronze"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		copperHelmet = new ItemTFCArmor(Armor.copperPlate, proxy.getArmorRenderID("copper"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		wroughtIronHelmet = new ItemTFCArmor(Armor.wroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		redSteelHelmet = new ItemTFCArmor(Armor.redSteelPlate, proxy.getArmorRenderID("redsteel"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");
		i++;
		steelHelmet = new ItemTFCArmor(Armor.steelPlate, proxy.getArmorRenderID("steel"), 0, 50, 3)
				.setUnlocalizedName(names[i] + " Helmet");

		leatherHelmet = new ItemTFCArmor(Armor.leather, proxy.getArmorRenderID("leather"), 0, ArmorMaterial.CLOTH, 100,
				3).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet");
		leatherChestplate = new ItemTFCArmor(Armor.leather, proxy.getArmorRenderID("leather"), 1, ArmorMaterial.CLOTH,
				100, 2).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate");
		leatherLeggings = new ItemTFCArmor(Armor.leather, proxy.getArmorRenderID("leather"), 2, ArmorMaterial.CLOTH,
				100, 1).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings");

		saddleTFC = new ItemSaddleTFC().setUnlocalizedName("saddleTFC");

		leatherBoots = ((ItemTFCArmorBoots) (new ItemTFCArmorBoots(Armor.leather, proxy.getArmorRenderID("leather"), 3,
				ArmorMaterial.CLOTH, 100, 0).setDefaultWalkable(0.10f).addWalkableSurface(Material.sand, 0.02f).setTrueBoots(true)))
						.setUnlocalizedName("bootsCloth").setTextureName("leather_boots");

		quiver = new ItemQuiver().setUnlocalizedName("Quiver");
	}

	public static void registerFurnaceFuel()
	{
		// 1 sec = 20 burn time value
		TFCFuelHandler.registerFuel(blueSteelBucketLava, 20000);
		TFCFuelHandler.registerFuel(singlePlank, 400);
		TFCFuelHandler.registerFuel(woodenBucketEmpty, 300);
		TFCFuelHandler.registerFuel(fireStarter, 100);
		TFCFuelHandler.registerFuel(bowFireStarter, 200);
		TFCFuelHandler.registerFuel(logs, 800);
		TFCFuelHandler.registerFuel(thickLogs, 2400);
		TFCFuelHandler.registerFuel(stackedLogs, 2400);
		TFCFuelHandler.registerFuel(sluiceItem, 300);
		TFCFuelHandler.registerFuel(rope, 50);
		TFCFuelHandler.registerFuel(arrow, 20);
		TFCFuelHandler.registerFuel(fletchedArrow, 20);
		TFCFuelHandler.registerFuel(bow, 100);
		TFCFuelHandler.registerFuel(unstrungBow, 100);
		TFCFuelHandler.registerFuel(fishingRod, 100);
		TFCFuelHandler.registerFuel(stick, 100);
		TFCFuelHandler.registerFuel(pole, 180);
		TFCFuelHandler.registerFuel(woodenSpear, 150);
		TFCFuelHandler.registerFuel(coal, 1600);
		TFCFuelHandler.registerFuel(woolCloth, 20);
		TFCFuelHandler.registerFuel(silkCloth, 20);
		TFCFuelHandler.registerFuel(burlapCloth, 20);
		TFCFuelHandler.registerFuel(straw, 20);

		for (int l = 0; l < Recipes.doors.length; l++)
			TFCFuelHandler.registerFuel(Recipes.doors[l], 300);

		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.woodSupportV), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.woodSupportV2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.woodSupportH), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.woodSupportH2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.fence), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.fence2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.fenceGate), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.fenceGate2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.chest), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.strawHideBed), 200);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.thatch), 200);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.planks), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.planks2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.barrel), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.torch), 100);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.sapling), 100);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.sapling2), 100);
	}
}
