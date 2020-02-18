package com.dunk.tfc.api.Enums;

public enum EnumForestPlant
{
	UNDERGROWTH_PALM ("UNDERGROWTH_PALM",1000f,16000f,21f,35f,0.25f,4f),
	
	UNDERGROWTH_TREE ("UNDERGROWTH_TREE",500f,16000f,9f,40f,0.250f,4f),
	
	UNDERGROWTH_SHORT ("UNDERGROWTH_SHORT",1000f,16000f,14f,35f,0.250f,8f),
	
	FERN ("FERN",750f,16000f,15f,36f,0.125f,8f),
	
	MOSS ("MOSS",1000f,16000f,12f,36f,0.25f,8f),
	
	SHRUB ("SHRUB",100f,2000F,-15f,45f,0f,8f),
	
	VINE ("VINE",1300f,16000f,22,35,0.5f,8f);
	
	public final float minRain;
	public final float maxRain;
	public final float minTemp;
	public final float maxTemp;
	public final float minEVT;
	public final float maxEVT;
	
	public static final EnumForestPlant[] AMERICAS = new EnumForestPlant[]{UNDERGROWTH_PALM,UNDERGROWTH_TREE,UNDERGROWTH_SHORT,FERN,MOSS,SHRUB};
	public static final EnumForestPlant[] EUROPE = new EnumForestPlant[]{UNDERGROWTH_PALM,UNDERGROWTH_TREE,UNDERGROWTH_SHORT,FERN,MOSS,SHRUB};
	public static final EnumForestPlant[] ASIA = new EnumForestPlant[]{UNDERGROWTH_PALM,UNDERGROWTH_TREE,UNDERGROWTH_SHORT,FERN,MOSS,SHRUB};
	public static final EnumForestPlant[] AFRICA = new EnumForestPlant[]{UNDERGROWTH_PALM,UNDERGROWTH_TREE,UNDERGROWTH_SHORT,FERN,MOSS,SHRUB};
	
	public static final EnumForestPlant[][]REGIONS = new EnumForestPlant[][]{AMERICAS,EUROPE,AFRICA,ASIA};
	
	private EnumForestPlant(String s, float i, float j, float mintemp, float maxtemp, float minevt, float maxevt)
	{
		minRain = i;
		maxRain = j;
		minTemp = mintemp;
		maxTemp = maxtemp;
		minEVT = minevt;
		maxEVT = maxevt;
	}
	
}
