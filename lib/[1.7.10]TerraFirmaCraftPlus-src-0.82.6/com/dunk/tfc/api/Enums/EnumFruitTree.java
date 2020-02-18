package com.dunk.tfc.api.Enums;

public enum EnumFruitTree
{
	APPLE ("APPLE", 250,3000,7,25,0.5f,8f), 		//Central Asia [Europe, Asia]
	BANANA ("BANANA",1500,16000,19,35,0.5f,8f),		//Asia
	ORANGE ("ORANGE",750,8000,17,35,0.5f,8f),		//Asia
	GRAPPLE ("GREEN APPLE", 350,3000,8,26,0.5f,8f),	//Central Asia [Europe, Asia]
	LEMON ("LEMON",1000,9000,20,32,0.5f,8f),		//Asia
	OLIVE ("OLIVE",125,1500,10,35,0.5f,8f),			//Europe and Africa
	CHERRY ("CHERRY",500f,4000f,7f,30f,0.5f,8f),	//Europe and Asia and Americas
	PEACH ("PEACH",650f,4000f,9f,28f,0.75f,8f),		//Asia
	PLUM ("Plum",700f,8000f,14f,30f,0.5f,8f),		//Asia and Americas
	PAPAYA ("Papaya",750f,8000f,21f,35f,0.5f,8f),	//Americas
	DATE ("Date",125f,250f,21f,35f,0f,8f);
	//Date? Africa
	
	public final float minRain;
	public final float maxRain;
	public final float minTemp;
	public final float maxTemp;
	public final float minEVT;
	public final float maxEVT;
	
	public static final EnumFruitTree[] AMERICAS = new EnumFruitTree[]{PLUM,CHERRY,PAPAYA};
	public static final EnumFruitTree[] EUROPE = new EnumFruitTree[]{APPLE,GRAPPLE,OLIVE,DATE};
	public static final EnumFruitTree[] ASIA = new EnumFruitTree[]{APPLE,GRAPPLE,BANANA,ORANGE,LEMON, CHERRY,PEACH,PLUM,DATE};
	public static final EnumFruitTree[] AFRICA = new EnumFruitTree[]{OLIVE,DATE};
	
	public static final EnumFruitTree[][]REGIONS = new EnumFruitTree[][]{AMERICAS,EUROPE,AFRICA,ASIA};
	
	
	private EnumFruitTree(String s, float i, float j, float mintemp, float maxtemp, float minevt, float maxevt)
	{
		minRain = i;
		maxRain = j;
		minTemp = mintemp;
		maxTemp = maxtemp;
		minEVT = minevt;
		maxEVT = maxevt;
	}
}
