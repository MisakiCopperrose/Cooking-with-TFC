package com.dunk.tfc.api.Enums;

import com.dunk.tfc.Entities.Mobs.EntityBear;
import com.dunk.tfc.Entities.Mobs.EntityChickenTFC;
import com.dunk.tfc.Entities.Mobs.EntityCowTFC;
import com.dunk.tfc.Entities.Mobs.EntityDeer;
import com.dunk.tfc.Entities.Mobs.EntityFishTFC;
import com.dunk.tfc.Entities.Mobs.EntityHorseTFC;
import com.dunk.tfc.Entities.Mobs.EntityPheasantTFC;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;
import com.dunk.tfc.Entities.Mobs.EntitySheepTFC;
import com.dunk.tfc.Entities.Mobs.EntityWolfTFC;
import com.dunk.tfc.WorldGen.TFCBiome;

public enum EnumAnimalSpawn
{

	WILD_BOAR ("WILD_BOAR",EntityPigTFC.class, 600,4000,7,36,3,6,null,0.8f),
	PECCARY ("PECCARY",EntityPigTFC.class, 1200,4000,18,36,3,6,null,0.6f),
	WARTHOG ("WARTHOG",EntityPigTFC.class, 150,1500,7,36,1,4,null,0.6f),
	AUROCHS ("AUROCHS",EntityCowTFC.class,450,2400,3,30,3,12,null,0.5f),
	BUFFALO ("BUFFALO",EntityCowTFC.class,450,2400,3,30,3,12,new int[]{TFCBiome.SWAMPLAND.biomeID},0.5f),
	BISON ("BISON",EntityCowTFC.class,151,900,-3,25,6,18,null,0.25f),
	MOUFLON ("MOUFLON",EntitySheepTFC.class,100,800,7,39,2,6,new int[]{TFCBiome.MOUNTAINS.biomeID, TFCBiome.MOUNTAINS_EDGE.biomeID,TFCBiome.HIGH_HILLS.biomeID,TFCBiome.MOUNTAIN_RANGE.biomeID,TFCBiome.MOUNTAIN_RANGE_EDGE.biomeID,TFCBiome.FOOTHILLS.biomeID},0.7f),
	BIG_HORN_SHEEP ("BIG_HORN_SHEEP",EntitySheepTFC.class,100,1800,0,30,2,6,new int[]{TFCBiome.MOUNTAINS.biomeID, TFCBiome.MOUNTAINS_EDGE.biomeID,TFCBiome.HIGH_HILLS.biomeID,TFCBiome.HIGH_HILLS_EDGE.biomeID,TFCBiome.MOUNTAIN_RANGE.biomeID,TFCBiome.MOUNTAIN_RANGE_EDGE.biomeID,TFCBiome.FOOTHILLS.biomeID},0.5f),
	BROWN_BEAR ("BROWN_BEAR",EntityBear.class,600,2500,-18,19,1,2,null,0.3f),
	BLACK_BEAR("BLACK_BEAR",EntityBear.class,1000,5000,5,32,1,2,null,0.4f),
	WOLF ("WOLF",EntityWolfTFC.class,500,4000,-20,21,1,6,null,0.5f),
	PHEASANT("PHEASANT",EntityPheasantTFC.class,600,3000,0,25,2,5,null,0.8f),
	CHICKEN("CHICKEN",EntityChickenTFC.class,800,8000,15,35,1,6,null,0.7f),
	WHITE_TAILED_DEER("WHITE_TAILED_DEER",EntityDeer.class,700,4000,10,30,1,9,null,0.6f),
	ELK("ELK",EntityDeer.class,400,1800,-5,20,3,10,null,0.3f),
	CARIBOU("CARIBOU",EntityDeer.class,200,4000,-20,14,5,16,null,0.1f),
	RED_DEER("RED_DEER",EntityDeer.class,700,4000,-8,20,1,5,null,0.2f),
	WILD_HORSE("WILD_HORSE",EntityHorseTFC.class,151,1000,0,30,8,12,null,0.3f),
	ZEBRA("ZEBRA",EntityHorseTFC.class,130,900,14,35,8,14,null,0.5f),
	ASS("ASS",EntityHorseTFC.class,100,800,15,35,1,3,null,0.3f),
	BASS("BASS",EntityFishTFC.class,0,8000,-15,35,3,5,new int[] {TFCBiome.LAKE.biomeID,TFCBiome.SWAMPLAND.biomeID,TFCBiome.RIVER.biomeID},5f);
	
	public final String name;
	public final Class entityClass;
	public final float minRain;
	public final float maxRain;
	public final float minTemp;
	public final float maxTemp;
	public final int minGroupCount;
	public final int maxGroupCount;
	public final boolean specificBiomes;
	public final int[] allowableBiomes;
	public final float rarity;
	
	public static final EnumAnimalSpawn[] AMERICAS = new EnumAnimalSpawn[]{PECCARY,BISON,BIG_HORN_SHEEP,BROWN_BEAR,BLACK_BEAR,WOLF,WHITE_TAILED_DEER,ELK,CARIBOU,WILD_HORSE,BASS};
	public static final EnumAnimalSpawn[] EUROPE = new EnumAnimalSpawn[]{WILD_BOAR,AUROCHS,BISON,MOUFLON,BROWN_BEAR,WOLF,CARIBOU,RED_DEER,WILD_HORSE,BASS};
	public static final EnumAnimalSpawn[] ASIA = new EnumAnimalSpawn[]{WILD_BOAR,AUROCHS,BUFFALO,BISON,MOUFLON,BROWN_BEAR,BLACK_BEAR,WOLF,PHEASANT,CHICKEN,RED_DEER,CARIBOU,WILD_HORSE,BASS};
	public static final EnumAnimalSpawn[] AFRICA = new EnumAnimalSpawn[]{WARTHOG,BUFFALO,MOUFLON,WOLF,ZEBRA,ASS,BASS};
	
	public static final EnumAnimalSpawn[][]REGIONS = new EnumAnimalSpawn[][]{AMERICAS,EUROPE,AFRICA,ASIA};
	
	private EnumAnimalSpawn(String s, Class theEntity, float minRain, float maxRain, float minTemp, float maxTemp,  int minSpawn, int maxSpawn, int[] biomes, float rarity)
	{
		this.name = s;
		this.entityClass = theEntity;
		this.minRain = minRain;
		this.maxRain = maxRain;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		if(biomes == null)
		{
			this.specificBiomes = false;
			this.allowableBiomes = new int[]{};
		}
		else
		{
			this.specificBiomes = true;
			this.allowableBiomes = biomes;
		}
		this.minGroupCount = minSpawn;
		this.maxGroupCount = maxSpawn;
		this.rarity = rarity;
	}
}
