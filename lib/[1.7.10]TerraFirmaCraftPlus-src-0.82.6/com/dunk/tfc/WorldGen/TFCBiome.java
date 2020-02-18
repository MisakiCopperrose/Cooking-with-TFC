package com.dunk.tfc.WorldGen;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Entities.Mobs.EntityCreeperTFC;
import com.dunk.tfc.Entities.Mobs.EntityDeer;
import com.dunk.tfc.Entities.Mobs.EntityEndermanTFC;
import com.dunk.tfc.Entities.Mobs.EntityFishTFC;
import com.dunk.tfc.Entities.Mobs.EntityPheasantTFC;
import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Entities.Mobs.EntitySlimeTFC;
import com.dunk.tfc.Entities.Mobs.EntitySpiderTFC;
import com.dunk.tfc.Entities.Mobs.EntitySquidTFC;
import com.dunk.tfc.Entities.Mobs.EntityZombieTFC;
import com.dunk.tfc.WorldGen.Generators.WorldGenForestPlants;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenAcaciaKoaTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenBamboo;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenBaobabTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenBirchTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomBigTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomCedarTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomMapleShortTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomMapleTallTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomPalmTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomShortTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomTallTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomWillowTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenDouglasFir;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenFeverTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenFruitTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenLimbaTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenPineShort;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenRedwoodXL;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenUmbrellaAcaciaTrees;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Enums.EnumTree;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class TFCBiome extends BiomeGenBase
{
	public static float riverDepthMin = -0.3F;
	public static float riverDepthMax = -0.2F;
	public float temperatureTFC;

	public BiomeDecoratorTFC theBiomeDecorator;

	public static TFCBiome[] biomeList = new TFCBiome[256];

	/** An array of all the biomes, indexed by biome id. */
	public static final TFCBiome OCEAN = new TFCBiome(0).setBiomeName("Ocean").setMinMaxHeight(-0.9F, 0.00001F)
			.setBiomeColor(0x0000ff);
	public static final TFCBiome RIVER = new TFCBiome(7).setBiomeName("River")
			.setMinMaxHeight(riverDepthMin, riverDepthMax).setBiomeColor(0xffffff);
	public static final TFCBiome HELL = new TFCBiome(8).setColor(16711680).setBiomeName("Hell").setDisableRain()
			.setTemperatureRainfall(2.0F, 0.0F);
	public static final TFCBiome BEACH = new TFCBiome(16).setColor(0xfade55).setBiomeName("Beach")
			.setMinMaxHeight(0.01F, 0.02F).setBiomeColor(0xffb873);
	public static final TFCBiome GRAVEL_BEACH = new TFCBiome(17).setColor(0xfade55).setBiomeName("Gravel Beach")
			.setMinMaxHeight(0.01F, 0.02F).setBiomeColor(0x8f7963);
	public static final TFCBiome HIGH_HILLS = new TFCBiome(3).setBiomeName("High Hills").setMinMaxHeight(0.8F, 0.6F)
			.setBiomeColor(0x044f27);
	public static final TFCBiome PLAINS = new TFCBiome(1).setBiomeName("Plains").setMinMaxHeight(0.1F, 0.16F)
			.setBiomeColor(0x69dfa0);
	public static final TFCBiome SWAMPLAND = new TFCBiome(6).setBiomeName("Swamp").setMinMaxHeight(-0.1F, 0.1F)
			.setBiomeColor(0x1f392b).setLilyPads(8).setWaterPlants(45);
	public static final TFCBiome HIGH_HILLS_EDGE = new TFCBiome(20).setBiomeName("High Hills Edge")
			.setMinMaxHeight(0.2F, 0.4F).setBiomeColor(0x30a767);
	public static final TFCBiome ROLLING_HILLS = new TFCBiome(30).setBiomeName("Rolling Hills")
			.setMinMaxHeight(0.1F, 0.4F).setBiomeColor(0x87b434);
	public static final TFCBiome MOUNTAINS = new TFCBiome(31).setBiomeName("Mountains").setMinMaxHeight(0.8F, 1.0F)
			.setBiomeColor(0x707960);
	
	public static final TFCBiome MOUNTAINS_EDGE = new TFCBiome(32).setBiomeName("Mountains Edge")
			.setMinMaxHeight(0.4F, 0.6F).setBiomeColor(0xb2bc9f);
	public static final TFCBiome HIGH_PLAINS = new TFCBiome(35).setBiomeName("High Plains").setMinMaxHeight(0.4F, 0.43F)
			.setBiomeColor(0xa6a41c);
	public static final TFCBiome DEEP_OCEAN = new TFCBiome(36).setBiomeName("Deep Ocean")
			.setMinMaxHeight(-3.5F, 0.00001F).setBiomeColor(0x0e055a);
	public static final TFCBiome LAKE = new TFCBiome(2).setBiomeName("Lake").setMinMaxHeight(-0.5F, 0.1F)
			.setBiomeColor(0x4a8e9e).setLilyPads(2);
	public static final TFCBiome MOUNTAIN_RANGE = new TFCBiome(37).setBiomeName("Mountain Range").setMinMaxHeight(2.5F, 1.5F)
			.setBiomeColor(0xFF7960);
	public static final TFCBiome MOUNTAIN_RANGE_EDGE = new TFCBiome(38).setBiomeName("Mountain Range Edge").setMinMaxHeight(1.25F, 1.0F)
			.setBiomeColor(0xFF00BE);
	public static final TFCBiome FOOTHILLS = new TFCBiome(39).setBiomeName("Foothills").setMinMaxHeight(1.0F, 0.9F)
			.setBiomeColor(0xAA00BE);

	protected static WorldGenCustomTallTrees worldGenAcaciaKoaTrees;
	protected static WorldGenCustomTallTrees worldGenAshTallTrees;
	protected static WorldGenCustomTallTrees worldGenTeakTrees;
	//protected static WorldGenCustomTallTrees worldGenAspenTallTrees;
	//protected static WorldGenCustomTallTrees worldGenBirchTallTrees;
	protected static WorldGenCustomTallTrees worldGenChestnutTallTrees;
	protected static WorldGenDouglasFir worldGenDouglasFirTallTrees;
	protected static WorldGenCustomTallTrees worldGenHickoryTallTrees;
	protected static WorldGenCustomTallTrees worldGenMapleTallTrees;
	protected static WorldGenCustomTallTrees worldGenOakTallTrees;
	protected static WorldGenRedwoodXL worldGenRedwoodTallTrees;
	protected static WorldGenCustomTallTrees worldGenSycamoreTallTrees;
	protected static WorldGenCustomCedarTrees worldGenWhiteCedarTallTrees;
	protected static WorldGenCustomTallTrees worldGenWhiteElmTallTrees;
	protected static WorldGenCustomShortTrees worldGenGingkoTrees;
	protected static WorldGenCustomTallTrees worldGenTallGingkoTrees;
	protected static WorldGenCustomBigTree worldGenBigGingkoTrees;
	protected static WorldGenCustomShortTrees worldGenGingkoTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenTallGingkoTreesFromSapling;
	protected static WorldGenCustomBigTree worldGenBigGingkoTreesFromSapling;

	protected static WorldGenCustomShortTrees worldGenAshShortTrees;
	protected static WorldGenBirchTree worldGenAspenShortTrees;
	protected static WorldGenBirchTree worldGenBirchShortTrees;
	protected static WorldGenCustomShortTrees worldGenChestnutShortTrees;
	protected static WorldGenDouglasFir worldGenDouglasFirShortTrees;
	protected static WorldGenCustomShortTrees worldGenHickoryShortTrees;
	protected static WorldGenCustomMapleShortTrees worldGenMapleShortTrees;
	protected static WorldGenCustomPalmTrees worldGenPalmTrees;
	protected static WorldGenCustomShortTrees worldGenOakShortTrees;
	protected static WorldGenPineShort worldGenPineShortTrees;
	protected static WorldGenDouglasFir worldGenRedwoodShortTrees;
	protected static WorldGenPineShort worldGenSpruceShortTrees;
	protected static WorldGenDouglasFir worldGenPineTall;
	protected static WorldGenDouglasFir worldGenSpruceTall;
	protected static WorldGenDouglasFir worldGenCedarTall;
	protected static WorldGenCustomShortTrees worldGenSycamoreShortTrees;
	protected static WorldGenCustomShortTrees worldGenWhiteElmShortTrees;
	protected static WorldGenCustomWillowTrees worldGenWillowShortTrees;
	protected static WorldGenBamboo worldGenBamboo;
	
	protected static WorldGenCustomTallTrees worldGenEbonyTallTrees, worldGenMahoganyTallTrees;
	protected static WorldGenCustomBigTree worldGenEbonyBigTrees,worldGenMahoganyBigTrees;
	protected static WorldGenCustomBigTree worldGenAcaciaKoaBigTreesFromSapling,worldGenAcaciaKoaBigTrees;
	
	protected static WorldGenCustomTallTrees worldGenAcaciaKoaTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenAshTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenTeakTreesFromSapling;
	//protected static WorldGenCustomTallTrees worldGenAspenTallTreesFromSapling;
	//protected static WorldGenCustomTallTrees worldGenBirchTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenChestnutTallTreesFromSapling;
	protected static WorldGenDouglasFir worldGenDouglasFirTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenHickoryTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenMapleTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenOakTallTreesFromSapling;
	protected static WorldGenRedwoodXL worldGenRedwoodTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenSycamoreTallTreesFromSapling;
	protected static WorldGenCustomCedarTrees worldGenWhiteCedarTallTreesFromSapling;
	protected static WorldGenCustomTallTrees worldGenWhiteElmTallTreesFromSapling;

	protected static WorldGenDouglasFir worldGenPineTallFromSapling;
	protected static WorldGenDouglasFir worldGenSpruceTallFromSapling;
	protected static WorldGenDouglasFir worldGenCedarTallFromSapling;
	
	protected static WorldGenCustomShortTrees worldGenAshShortTreesFromSapling;
	protected static WorldGenBirchTree worldGenAspenShortTreesFromSapling;
	protected static WorldGenBirchTree worldGenBirchShortTreesFromSapling;
	protected static WorldGenCustomShortTrees worldGenChestnutShortTreesFromSapling;
	protected static WorldGenDouglasFir worldGenDouglasFirShortTreesFromSapling;
	protected static WorldGenCustomShortTrees worldGenHickoryShortTreesFromSapling;
	protected static WorldGenCustomMapleShortTrees worldGenMapleShortTreesFromSapling;
	protected static WorldGenCustomPalmTrees worldGenPalmTreesFromSapling;
	protected static WorldGenCustomShortTrees worldGenOakShortTreesFromSapling;
	protected static WorldGenPineShort worldGenPineShortTreesFromSapling;
	protected static WorldGenDouglasFir worldGenRedwoodShortTreesFromSapling;
	protected static WorldGenPineShort worldGenSpruceShortTreesFromSapling;
	protected static WorldGenCustomShortTrees worldGenSycamoreShortTreesFromSapling;
	protected static WorldGenCustomShortTrees worldGenWhiteElmShortTreesFromSapling;
	protected static WorldGenCustomWillowTrees worldGenWillowShortTreesFromSapling;
	protected static WorldGenBamboo worldGenBambooFromSapling;

	protected static WorldGenCustomTallTrees worldGenEbonyTallTreesFromSapling, worldGenMahoganyTallTreesFromSapling;
	protected static WorldGenCustomBigTree worldGenEbonyBigTreesFromSapling,worldGenMahoganyBigTreesFromSapling;
	
	protected static WorldGenCustomBigTree worldGenBigAsh;
	protected static WorldGenCustomBigTree worldGenBigAshFromSapling;
	protected static WorldGenCustomBigTree worldGenBigAspenFromSapling;
	protected static WorldGenCustomBigTree worldGenBigAspen;
	protected static WorldGenCustomBigTree worldGenBigBirchFromSapling;
	protected static WorldGenCustomBigTree worldGenBigBirch;
	protected static WorldGenCustomBigTree worldGenBigChestnutFromSapling;
	protected static WorldGenCustomBigTree worldGenBigChestnut;
	protected static WorldGenCustomBigTree worldGenBigHickoryFromSapling;
	protected static WorldGenCustomBigTree worldGenBigHickory;
	protected static WorldGenCustomBigTree worldGenBigMapleFromSapling;
	protected static WorldGenCustomBigTree worldGenBigMaple;
	protected static WorldGenCustomBigTree worldGenBigOakFromSapling;
	protected static WorldGenCustomBigTree worldGenBigOak;
	protected static WorldGenCustomBigTree worldGenBigSycamoreFromSapling;
	protected static WorldGenCustomBigTree worldGenBigSycamore;
	protected static WorldGenCustomBigTree worldGenBigWhiteElmFromSapling;
	protected static WorldGenCustomBigTree worldGenBigWhiteElm;
	protected static WorldGenCustomBigTree worldGenBigTeakFromSapling;
	protected static WorldGenCustomBigTree worldGenBigTeak;
	protected static WorldGenCustomBigTree worldGenBigKapok;
	protected static WorldGenCustomTallTrees worldGenTallKapok;
	
	protected static WorldGenLimbaTree worldGenLimba;
	protected static WorldGenLimbaTree worldGenLimbaFromSapling;
	
	
	protected static WorldGenFeverTree worldGenFeverTree;
	protected static WorldGenFeverTree worldGenFeverTreeFromSapling;

	protected static WorldGenUmbrellaAcaciaTrees worldGenUmbrellaAcaciaTrees;

	protected static WorldGenForestPlants worldGenFern;
	protected static WorldGenForestPlants worldGenMoss;
	protected static WorldGenForestPlants worldGenUnderGrowthTall;
	protected static WorldGenForestPlants worldGenUnderGrowthShrub;
	protected static WorldGenForestPlants worldGenUnderGrowthShort;
	protected static WorldGenForestPlants worldGenUnderGrowthPalm;
	
	protected static WorldGenBaobabTree worldGenBaobabTree;
	protected static WorldGenBaobabTree worldGenBaobabTreeFromSapling;
	
	public static final WorldGenFruitTree appleTreeFromSapling = new WorldGenFruitTree(false,26,true,0);
	public static final WorldGenFruitTree appleTree = new WorldGenFruitTree(false,26,false,0);
	
	public static final WorldGenFruitTree bananaTreeFromSapling = new WorldGenFruitTree(false,26,true,1);
	public static final WorldGenFruitTree bananaTree = new WorldGenFruitTree(false,26,false,1);
	
	public static final WorldGenFruitTree orangeTreeFromSapling = new WorldGenFruitTree(false,26,true,2);
	public static final WorldGenFruitTree orangeTree = new WorldGenFruitTree(false,26,false,2);
	
	public static final WorldGenFruitTree grappleTreeFromSapling = new WorldGenFruitTree(false,26,true,3);
	public static final WorldGenFruitTree grappleTree = new WorldGenFruitTree(false,26,false,3);
	
	public static final WorldGenFruitTree lemonTreeFromSapling = new WorldGenFruitTree(false,26,true,4);
	public static final WorldGenFruitTree lemonTree = new WorldGenFruitTree(false,26,false,4);
	
	public static final WorldGenFruitTree oliveTreeFromSapling = new WorldGenFruitTree(false,26,true,5);
	public static final WorldGenFruitTree oliveTree = new WorldGenFruitTree(false,26,false,5);
	
	public static final WorldGenFruitTree cherryTreeFromSapling = new WorldGenFruitTree(false,26,true,6);
	public static final WorldGenFruitTree cherryTree = new WorldGenFruitTree(false,26,false,6);
	
	public static final WorldGenFruitTree peachTreeFromSapling = new WorldGenFruitTree(false,26,true,7);
	public static final WorldGenFruitTree peachTree = new WorldGenFruitTree(false,26,false,7);
	
	public static final WorldGenFruitTree plumTreeFromSapling = new WorldGenFruitTree(false,26,true,8);
	public static final WorldGenFruitTree plumTree = new WorldGenFruitTree(false,26,false,8);
	
	public static final WorldGenFruitTree papayaTreeFromSapling = new WorldGenFruitTree(false,26,true,9);
	public static final WorldGenFruitTree papayaTree = new WorldGenFruitTree(false,26,false,9);
	
	public static final WorldGenFruitTree datePalmFromSapling = new WorldGenFruitTree(false,26,true,10);
	public static final WorldGenFruitTree datePalm = new WorldGenFruitTree(false,26,false,10);
	
	public static final WorldGenFruitTree[] fruitTreesFromSapling = {appleTreeFromSapling,bananaTreeFromSapling,
			orangeTreeFromSapling,grappleTreeFromSapling,lemonTreeFromSapling,oliveTreeFromSapling,cherryTreeFromSapling,
			peachTreeFromSapling,plumTreeFromSapling,papayaTreeFromSapling,datePalmFromSapling};

	protected int biomeColor;

	@SuppressWarnings("unchecked")
	public TFCBiome(int par1)
	{
		super(par1);

		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.dirt;
		this.rootHeight = 0.1F;
		this.heightVariation = 0.3F;
		temperatureTFC = 0.5F;
		this.rainfall = 0.5F;
		this.spawnableMonsterList = new ArrayList<SpawnListEntry>();
		this.spawnableCreatureList = new ArrayList<SpawnListEntry>();
		this.spawnableWaterCreatureList = new ArrayList<SpawnListEntry>();

		worldGenTeakTrees = new WorldGenCustomTallTrees(false, 23, false);
		worldGenAcaciaKoaTrees = new WorldGenCustomTallTrees(false, 16,false);
		worldGenAshTallTrees = new WorldGenCustomTallTrees(false, 7, false);
		//worldGenAspenTallTrees = new WorldGenCustomTallTrees(false, 1, false);
		//worldGenBirchTallTrees = new WorldGenCustomTallTrees(false, 2, false);
		worldGenChestnutTallTrees = new WorldGenCustomTallTrees(false, 3, false);
		worldGenDouglasFirTallTrees = new WorldGenDouglasFir(false, 4, true,false);
		worldGenHickoryTallTrees = new WorldGenCustomTallTrees(false, 5, false);
		worldGenMapleTallTrees = new WorldGenCustomTallTrees(false, 6, false);// new
																				// WorldGenCustomMapleTallTrees(false,
																				// 6);
		
		worldGenGingkoTrees = new WorldGenCustomShortTrees(false,25,false);
		worldGenTallGingkoTrees = new WorldGenCustomTallTrees(false,25,false);
		worldGenBigGingkoTrees = new WorldGenCustomBigTree(false,25,false);
		
		worldGenGingkoTreesFromSapling = new WorldGenCustomShortTrees(false,25,true);
		worldGenTallGingkoTreesFromSapling = new WorldGenCustomTallTrees(false,25,true);
		worldGenBigGingkoTreesFromSapling = new WorldGenCustomBigTree(false,25,true);
		
		worldGenBaobabTree = new WorldGenBaobabTree(false,20,false);
		worldGenBaobabTreeFromSapling = new WorldGenBaobabTree(false,20,true);
		
		worldGenOakTallTrees = new WorldGenCustomTallTrees(false, 0, false);
		worldGenRedwoodTallTrees = new WorldGenRedwoodXL(false,9,false);
		worldGenSycamoreTallTrees = new WorldGenCustomTallTrees(false, 11, false);
		worldGenWhiteCedarTallTrees = new WorldGenCustomCedarTrees(false, 12, false);
		worldGenWhiteElmTallTrees = new WorldGenCustomTallTrees(false, 13, false);

		worldGenAshShortTrees = new WorldGenCustomShortTrees(false, 7, false);
		worldGenAspenShortTrees = new WorldGenBirchTree(false, 1, false);
		worldGenBirchShortTrees = new WorldGenBirchTree(false, 2, false);
		worldGenChestnutShortTrees = new WorldGenCustomShortTrees(false, 3, false);
		worldGenDouglasFirShortTrees = new WorldGenDouglasFir(false, 4, false,false);
		worldGenHickoryShortTrees = new WorldGenCustomShortTrees(false, 5, false);
		worldGenMapleShortTrees = new WorldGenCustomMapleShortTrees(false, 6, false);
		worldGenPalmTrees = new WorldGenCustomPalmTrees(false, 1);
		worldGenOakShortTrees = new WorldGenCustomShortTrees(false, 0, false);
		worldGenPineShortTrees = new WorldGenPineShort(false, 8, false);
		
		worldGenRedwoodShortTrees = new WorldGenDouglasFir(false,9,false,false);
		worldGenSpruceShortTrees = new WorldGenPineShort(false, 10, false);
		worldGenPineTall = new WorldGenDouglasFir(false, 8, false,false);
		worldGenSpruceTall = new WorldGenDouglasFir(false, 10, false,false);
		worldGenCedarTall = new WorldGenDouglasFir(false, 12, false,false);
		worldGenSycamoreShortTrees = new WorldGenCustomShortTrees(false, 11, false);
		worldGenWhiteElmShortTrees = new WorldGenCustomShortTrees(false, 13, false);
		worldGenWillowShortTrees = new WorldGenCustomWillowTrees(false, 14,false);
		
		worldGenEbonyTallTrees = new WorldGenCustomTallTrees(false,18,false);
		worldGenMahoganyTallTrees = new WorldGenCustomTallTrees(false,22,false);
		
		worldGenLimba = new WorldGenLimbaTree(false,21,false);
		
		worldGenEbonyBigTrees = new WorldGenCustomBigTree(false,18,false);
		worldGenMahoganyBigTrees = new WorldGenCustomBigTree(false,22,false);
		
		worldGenAcaciaKoaBigTrees = new WorldGenCustomBigTree(false,16,false);
		worldGenAcaciaKoaBigTreesFromSapling = new WorldGenCustomBigTree(false,16,true);

		worldGenTeakTreesFromSapling = new WorldGenCustomTallTrees(false, 23, true);
		worldGenAcaciaKoaTreesFromSapling = new WorldGenCustomTallTrees(false, 16,true);
		worldGenAshTallTreesFromSapling = new WorldGenCustomTallTrees(false, 7, true);
		//worldGenAspenTallTreesFromSapling = new WorldGenCustomTallTrees(false, 1, true);
	//	worldGenBirchTallTreesFromSapling = new WorldGenCustomTallTrees(false, 2, true);
		worldGenChestnutTallTreesFromSapling = new WorldGenCustomTallTrees(false, 3, true);
		worldGenDouglasFirTallTreesFromSapling = new WorldGenDouglasFir(false, 4, true,true);
		worldGenHickoryTallTreesFromSapling = new WorldGenCustomTallTrees(false, 5, true);
		worldGenMapleTallTreesFromSapling = new WorldGenCustomTallTrees(false, 6, true);
		worldGenOakTallTreesFromSapling = new WorldGenCustomTallTrees(false, 0, true);
		worldGenRedwoodTallTreesFromSapling = new WorldGenRedwoodXL(false,9,true);
		worldGenSycamoreTallTreesFromSapling = new WorldGenCustomTallTrees(false, 11, true);
		worldGenWhiteCedarTallTreesFromSapling = new WorldGenCustomCedarTrees(false, 12, true);
		worldGenWhiteElmTallTreesFromSapling = new WorldGenCustomTallTrees(false, 13, true);

		worldGenAshShortTreesFromSapling = new WorldGenCustomShortTrees(false, 7, true);
		worldGenAspenShortTreesFromSapling = new WorldGenBirchTree(false, 1, true);
		worldGenBirchShortTreesFromSapling = new WorldGenBirchTree(false, 2, true);
		worldGenChestnutShortTreesFromSapling = new WorldGenCustomShortTrees(false, 3, true);
		worldGenDouglasFirShortTreesFromSapling = new WorldGenDouglasFir(false, 4, false,true);
		worldGenHickoryShortTreesFromSapling = new WorldGenCustomShortTrees(false, 5, true);
		worldGenMapleShortTreesFromSapling = new WorldGenCustomMapleShortTrees(false, 6, true);
		worldGenPalmTreesFromSapling = new WorldGenCustomPalmTrees(false, 1);
		worldGenOakShortTreesFromSapling = new WorldGenCustomShortTrees(false, 0, true);
		worldGenPineShortTreesFromSapling = new WorldGenPineShort(false, 8, true);
		worldGenRedwoodShortTreesFromSapling = new WorldGenDouglasFir(false,9,true,true);
		worldGenPineTallFromSapling = new WorldGenDouglasFir(false, 8, false,true);
		worldGenSpruceTallFromSapling = new WorldGenDouglasFir(false, 10, false,true);
		worldGenCedarTallFromSapling = new WorldGenDouglasFir(false, 12, false,true);
		worldGenSpruceShortTreesFromSapling = new WorldGenPineShort(false, 10, true);
		worldGenSycamoreShortTreesFromSapling = new WorldGenCustomShortTrees(false, 11, true);
		worldGenWhiteElmShortTreesFromSapling = new WorldGenCustomShortTrees(false, 13, true);
		worldGenWillowShortTreesFromSapling = new WorldGenCustomWillowTrees(false, 14,true);

		worldGenUmbrellaAcaciaTrees = new WorldGenUmbrellaAcaciaTrees(false, 16, true);
		
		worldGenEbonyTallTreesFromSapling = new WorldGenCustomTallTrees(false,18,true);
		worldGenMahoganyTallTreesFromSapling = new WorldGenCustomTallTrees(false,22,true);
		worldGenLimbaFromSapling = new WorldGenLimbaTree(false,21,true);
		worldGenEbonyBigTreesFromSapling  = new WorldGenCustomBigTree(false,18,true);
		worldGenMahoganyBigTreesFromSapling  = new WorldGenCustomBigTree(false,22,true);

		worldGenBamboo = new WorldGenBamboo(false, 8);

		worldGenBigAsh = new WorldGenCustomBigTree(false, 7, false);
		worldGenBigAshFromSapling = new WorldGenCustomBigTree(false, 7, true);
		worldGenBigAspenFromSapling = new WorldGenCustomBigTree(false, 1, true);
		worldGenBigAspen = new WorldGenCustomBigTree(false, 1, false);
		worldGenBigBirchFromSapling = new WorldGenCustomBigTree(false, 2, true);
		worldGenBigBirch = new WorldGenCustomBigTree(false, 2, false);
		worldGenBigChestnutFromSapling = new WorldGenCustomBigTree(false, 3, true);
		worldGenBigChestnut = new WorldGenCustomBigTree(false, 3, false);
		worldGenBigHickoryFromSapling = new WorldGenCustomBigTree(false, 5, true);
		worldGenBigHickory = new WorldGenCustomBigTree(false, 5, false);
		worldGenBigMapleFromSapling = new WorldGenCustomBigTree(false, 6, true);
		worldGenBigMaple = new WorldGenCustomBigTree(false, 6, false);
		worldGenBigOakFromSapling = new WorldGenCustomBigTree(false, 0, true);
		worldGenBigOak = new WorldGenCustomBigTree(false, 0, false);
		worldGenBigSycamoreFromSapling = new WorldGenCustomBigTree(false, 11, true);
		worldGenBigSycamore = new WorldGenCustomBigTree(false, 11, false);
		worldGenBigWhiteElmFromSapling = new WorldGenCustomBigTree(false, 13, true);
		worldGenBigWhiteElm = new WorldGenCustomBigTree(false, 13, false);
		worldGenBigTeakFromSapling = new WorldGenCustomBigTree(false, 23, true);
		worldGenBigTeak = new WorldGenCustomBigTree(false, 23, false);

		worldGenBigKapok = new WorldGenCustomBigTree(false, 15, false);
		worldGenTallKapok = new WorldGenCustomTallTrees(false, 15, false);
		
		worldGenFeverTree = new WorldGenFeverTree(false,19,false);
		worldGenFeverTreeFromSapling = new WorldGenFeverTree(false,19,true);

		worldGenUnderGrowthPalm = new WorldGenForestPlants(TFCBlocks.undergrowthPalm);
		worldGenUnderGrowthShort = new WorldGenForestPlants(TFCBlocks.lowUndergrowth);
		worldGenUnderGrowthTall = new WorldGenForestPlants(TFCBlocks.undergrowth);
		worldGenUnderGrowthShrub = new WorldGenForestPlants(TFCBlocks.shrub);
		worldGenFern = new WorldGenForestPlants(TFCBlocks.fern);
		worldGenMoss = new WorldGenForestPlants(TFCBlocks.moss);

		// Default spawns. I didn't delete them so they could be referenced in
		// the future. Nerfing animal spawns.
		this.spawnableCreatureList.clear();
		/*
		 * this.spawnableCreatureList.add(new
		 * SpawnListEntry(EntitySheepTFC.class, 12, 4, 6));
		 * this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class,
		 * 10, 2, 4)); this.spawnableCreatureList.add(new
		 * SpawnListEntry(EntityChickenTFC.class, 10, 2, 4));
		 * this.spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class,
		 * 8, 2, 4));
		 */
		// This is to balance out the spawning, so that entities with weight 1
		// spawn less
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 16, 0, 0));

		this.spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 2, 1, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1, 1, 4));

		this.spawnableWaterCreatureList.clear();
		switch (par1)
		{
		case 0:
			this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 8, 1, 1));
			break;
		case 2:
			this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityFishTFC.class, 7, 1, 1));
		case 6:
			this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityFishTFC.class, 5, 0, 1));
			this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityFishTFC.class, 12, 0, 0));
			break;
		default:
			break;
		}

		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpiderTFC.class, 5, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieTFC.class, 10, 2, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonTFC.class, 8, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeperTFC.class, 3, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySlimeTFC.class, 8, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEndermanTFC.class, 1, 1, 2));

		// getBiomeGenArray()[par1] = this;
		biomeList[par1] = this;
		this.theBiomeDecorator = this.createBiomeDecorator();
	}

	public int getBiomeColor()
	{
		return biomeColor;
	}

	public TFCBiome setBiomeColor(int c)
	{
		biomeColor = c;
		return this;
	}

	/**
	 * Allocate a new BiomeDecorator for this BiomeGenBase
	 */
	@Override
	public BiomeDecoratorTFC createBiomeDecorator()
	{
		return new BiomeDecoratorTFC(this);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		this.theBiomeDecorator.decorateChunk(par1World, par2Random, this, par3, par4);
	}

	/**
	 * Sets the minimum and maximum height of this biome. Seems to go from -2.0
	 * to 2.0.
	 */
	// @Override
	public TFCBiome setMinMaxHeight(float par1, float par2)
	{
		this.rootHeight = par1 - 2.7F;
		this.heightVariation = par2 - 2.7F;
		return this;
	}

	@Override
	public TFCBiome setTemperatureRainfall(float par1, float par2)
	{
		temperatureTFC = par1;
		this.rainfall = par2;
		return this;
	}

	@Override
	public TFCBiome setBiomeName(String par1Str)
	{
		this.biomeName = par1Str;
		return this;
	}

	public TFCBiome setWaterMult(int par1)
	{
		this.waterColorMultiplier = par1;
		return this;
	}

	@Override
	public TFCBiome setColor(int par1)
	{
		this.color = par1;
		return this;
	}

	/**
	 * Disable the rain for the biome.
	 */
	@Override
	public TFCBiome setDisableRain()
	{
		this.enableRain = false;
		return this;
	}

	public static WorldGenerator getPlantGen(int i, Boolean j)
	{
		Random r = new Random();
		switch (i)
		{
		case 0:
		{
			return worldGenUnderGrowthPalm;
		}
		case 1:
		{
			return worldGenUnderGrowthTall;
		}
		case 2:
		{
			return worldGenUnderGrowthShort;
		}
		case 3:
		{
			return worldGenFern;
		}
		case 4:
		{
			return worldGenMoss;
		}
		case 5:
		{
			return worldGenUnderGrowthShrub;
		}
		}
		return null; 
	}

	public static EnumTree getEnumTreeFromId(int id)
	{
		if (id <= 17)
		{
			return EnumTree.values()[id];
		}
		else if(id + 1 < EnumTree.values().length)
		{
			return EnumTree.values()[id + 1];
		}
		else
		{
			return null;
		}
	}

	public static WorldGenerator getTreeGen(Boolean j, boolean fromSapling, EnumTree tree)
	{
		Random r = new Random();
		if(tree == null)
		{
			return null;
		}
		if (fromSapling)
		{
			switch (tree)
			{
			case ASH:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigAshFromSapling : worldGenAshTallTreesFromSapling;
				else
					return worldGenAshShortTreesFromSapling;
			}
			case ASPEN:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigAspenFromSapling : worldGenAspenShortTreesFromSapling;
				else
					return worldGenAspenShortTreesFromSapling;
			}
			case BIRCH:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigBirchFromSapling : worldGenBirchShortTreesFromSapling;
				else
					return worldGenBirchShortTreesFromSapling;
			}
			case CHESTNUT:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigChestnutFromSapling : worldGenChestnutTallTreesFromSapling;
				else
					return worldGenChestnutShortTreesFromSapling;
			}
			case DOUGLASFIR:
			{
				if (j)
					return worldGenDouglasFirTallTreesFromSapling;
				else
					return worldGenDouglasFirShortTreesFromSapling;
			}
			case HICKORY:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigHickoryFromSapling : worldGenHickoryTallTreesFromSapling;
				else
					return worldGenHickoryShortTreesFromSapling;
			}
			case MAPLE:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigMapleFromSapling : worldGenMapleTallTreesFromSapling;
				else
					return worldGenMapleShortTreesFromSapling;
			}
			case OAK:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigOakFromSapling : worldGenOakTallTreesFromSapling;
				else
					return worldGenOakShortTreesFromSapling;
			}
			case PINE:
			{
				// if (j)
				// return worldGenPineTallTreesFromSapling;
				// else
				return r.nextInt(120) == 0 ?worldGenPineTallFromSapling:worldGenPineShortTreesFromSapling;
			}
			case REDWOOD:
			{
				if (j)
					return worldGenRedwoodTallTreesFromSapling;
				else
					return worldGenRedwoodShortTreesFromSapling;
			}
			case SPRUCE:
			{
				return r.nextInt(120) == 0 ?worldGenSpruceTallFromSapling:worldGenSpruceShortTreesFromSapling;
			}
			case SYCAMORE:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigSycamoreFromSapling : worldGenSycamoreTallTreesFromSapling;
				else
					return worldGenSycamoreShortTreesFromSapling;
			}
			case WHITECEDAR:
			{
				return r.nextInt(150) == 0 ?worldGenCedarTallFromSapling:worldGenWhiteCedarTallTreesFromSapling;
			}
			case WHITEELM:
			{
				if (j)
					return r.nextInt(80) == 0 ? worldGenBigWhiteElmFromSapling : worldGenWhiteElmTallTreesFromSapling;
				else
					return worldGenWhiteElmShortTreesFromSapling;
			}
			case WILLOW:
			{
				return worldGenWillowShortTreesFromSapling;
			}
			case KAPOK:
			{
				return r.nextInt(20) == 0 ? worldGenBigKapok : worldGenTallKapok;// new
																					// WorldGenCustomShortTrees(false,
																					// 15,
																					// fromSapling);
			}
			case KOA:
			{
				return j&&r.nextInt(40)==0?worldGenAcaciaKoaBigTreesFromSapling:worldGenAcaciaKoaTreesFromSapling;
			}
			case PALM:
			{
				return worldGenPalmTreesFromSapling;
			}
			case UTACACIA:
			{
				return worldGenUmbrellaAcaciaTrees;
			}
			case EBONY:
			{
				return (j&&r.nextInt(40)==0? worldGenEbonyBigTreesFromSapling:worldGenEbonyTallTreesFromSapling);
			}
			
			case FEVERTREE:
			{
				return worldGenFeverTreeFromSapling;
			}
			case BAOBAB:
			{
				return worldGenBaobabTreeFromSapling;
			}
			case LIMBA:
			{
				return worldGenLimbaFromSapling;
			}
			case MAHOGANY:
			{
				return (j&r.nextInt(40)==0? worldGenMahoganyBigTreesFromSapling:worldGenMahoganyTallTreesFromSapling);
			}
			case TEAK:
			{
				return r.nextInt(20) == 0 ? worldGenBigTeakFromSapling : worldGenTeakTreesFromSapling;
			}
			case BAMBOO:
			{
				return worldGenBambooFromSapling;
			}
			case GINGKO:
			{
				if(j)
				{
					return (r.nextInt(32)==0?worldGenBigGingkoTreesFromSapling:worldGenTallGingkoTreesFromSapling);
				}
				return worldGenGingkoTreesFromSapling;
			}
			}
			return null;
		}
		switch (tree)
		{
		case ASH:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigAsh : worldGenAshTallTrees;
			else
				return worldGenAshShortTrees;
		}
		case ASPEN:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigAspen : worldGenAspenShortTrees;
			else
				return worldGenAspenShortTrees;
		}
		case BIRCH:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigBirch : worldGenBirchShortTrees;
			else
				return worldGenBirchShortTrees;
		}
		case CHESTNUT:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigChestnut : worldGenChestnutTallTrees;
			else
				return worldGenChestnutShortTrees;
		}
		case DOUGLASFIR:
		{
			if (j)
				return worldGenDouglasFirTallTrees;
			else
				return worldGenDouglasFirShortTrees;
		}
		case HICKORY:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigHickory : worldGenHickoryTallTrees;
			else
				return worldGenHickoryShortTrees;
		}
		case MAPLE:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigMaple : worldGenMapleTallTrees;
			else
				return worldGenMapleShortTrees;
		}
		case OAK:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigOak : worldGenOakTallTrees;
			else
				return worldGenOakShortTrees;
		}
		case PINE:
		{
			// if (j)
			// return worldGenPineTallTrees;
			// else
			return r.nextInt(80) == 0 ?worldGenPineTall:worldGenPineShortTrees;
		}
		case REDWOOD:
		{
			if (j)
				return worldGenRedwoodTallTrees;
			else
				return worldGenRedwoodShortTrees;
		}
		case SPRUCE:
		{
			return r.nextInt(80) == 0 ?worldGenSpruceTall:worldGenSpruceShortTrees;
		}
		case SYCAMORE:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigSycamore : worldGenSycamoreTallTrees;
			else
				return worldGenSycamoreShortTrees;
		}
		case WHITECEDAR:
		{
			return r.nextInt(80) == 0 ?worldGenCedarTall:worldGenWhiteCedarTallTrees;
		}
		case WHITEELM:
		{
			if (j)
				return r.nextInt(40) == 0 ? worldGenBigWhiteElm : worldGenWhiteElmTallTrees;
			else
				return worldGenWhiteElmShortTrees;
		}
		case WILLOW:
		{
			return worldGenWillowShortTrees;
		}
		case KAPOK:
		{
			return r.nextInt(15) == 0 ? worldGenBigKapok : worldGenTallKapok;// new
																				// WorldGenCustomShortTrees(false,
																				// 15,fromSapling);
		}
		case KOA:
		{
			return j&&r.nextInt(25)==0?worldGenAcaciaKoaBigTrees:worldGenAcaciaKoaTrees;
		}
		case PALM:
		{
			return worldGenPalmTrees;
		}
		case UTACACIA:
		{
			return worldGenUmbrellaAcaciaTrees;
		}
		case EBONY:
		{
			return (j&r.nextInt(32)==0? worldGenEbonyBigTrees:worldGenEbonyTallTrees);
		}
		case FEVERTREE:
		{
			return worldGenFeverTree;
		}
		case BAOBAB:
		{
			return worldGenBaobabTree;
		}
		case LIMBA:
		{
			return worldGenLimba;
		}
		case MAHOGANY:
		{
			return (j? worldGenMahoganyBigTrees:worldGenMahoganyTallTrees);
		}
		case TEAK:
		{
			return r.nextInt(30) == 0 ? worldGenBigTeak : worldGenTeakTrees;
		}
		case BAMBOO:
		{
			return worldGenBamboo;
		}
		case GINGKO:
		{
			if(j)
			{
				return (r.nextInt(16)==0?worldGenBigGingkoTrees:worldGenTallGingkoTrees);
			}
			return worldGenGingkoTrees;
		}
		}
		return null;
	}

	/**
	 * return the biome specified by biomeID, or 0 (ocean) if out of bounds
	 */
	public static TFCBiome getBiome(int id)
	{
		if (biomeList[id] == null)
		{
			TerraFirmaCraft.LOG.warn("Biome ID is null: " + id);
		}
		if (id >= 0 && id <= biomeList.length && biomeList[id] != null)
		{
			return biomeList[id];
		}
		else
		{
			TerraFirmaCraft.LOG.warn("Biome ID is out of bounds: " + id + ", defaulting to 0 (Ocean)");
			return OCEAN;
		}
	}

	public static TFCBiome getBiomeByName(String name)
	{
		for (int i = 0; i < getBiomeGenArray().length; i++)
		{
			if (getBiomeGenArray()[i] != null)
			{
				String n = getBiomeGenArray()[i].biomeName.toLowerCase();
				if (n.equalsIgnoreCase(name))
					return getBiomeGenArray()[i];
			}
		}
		return null;
	}

	public static TFCBiome[] getBiomeGenArray()
	{
		return biomeList.clone();
	}

	public TFCBiome setLilyPads(int i)
	{
		this.theBiomeDecorator.lilyPadPerChunk = i;
		return this;
	}

	public TFCBiome setWaterPlants(int i)
	{
		this.theBiomeDecorator.waterPlantsPerChunk = i;
		return this;
	}
}
