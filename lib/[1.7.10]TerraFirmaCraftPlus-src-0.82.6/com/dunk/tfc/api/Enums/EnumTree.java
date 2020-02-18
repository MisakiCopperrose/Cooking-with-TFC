package com.dunk.tfc.api.Enums;

//Based on data from the internet, and poetic licence, we can attribute the rainfall level directly to rainfall in mm. While above the average
//of the rainiest place on earth, it is well below the rainiest year on record.
public enum EnumTree
{
	OAK("OAK", /*minRain*/500f, /*maxRain*/1200f, /*minTemp*/5, /*maxTemp*/25, /*minEVT*/0.5f, /*maxEVT*/2, false,0.8f),

	ASPEN("ASPEN", /*minRain*/300f, /*maxRain*/1600f, /*minTemp*/-3, /*maxTemp*/18, /*minEVT*/0.25f, /*maxEVT*/2, false,1),

	BIRCH("BIRCH", /*minRain*/200f, /*maxRain*/500f, /*minTemp*/-2, /*maxTemp*/18, /*minEVT*/0.25f, /*maxEVT*/2, false,1),

	CHESTNUT("CHESTNUT", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/28, /*minEVT*/0.25f, /*maxEVT*/2, false,0.7f),

	DOUGLASFIR("DOUGLASFIR", /*minRain*/750f, /*maxRain*/16000f, /*minTemp*/10, /*maxTemp*/19, /*minEVT*/0.5f, /*maxEVT*/4, true,0.9f),

	HICKORY("HICKORY", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/30, /*minEVT*/0.25f, /*maxEVT*/2, false,0.7f),

	MAPLE("MAPLE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/20, /*minEVT*/0.25f, /*maxEVT*/4, false,1),

	ASH("ASH", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/22, /*minEVT*/0.5f, /*maxEVT*/4, false,0.8f),

	PINE("PINE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/-10, /*maxTemp*/21, /*minEVT*/0.25f, /*maxEVT*/3, true,1),

	REDWOOD("REDWOOD", /*minRain*/2000f, /*maxRain*/16000f, /*minTemp*/10, /*maxTemp*/20, /*minEVT*/1, /*maxEVT*/5f, true,1f),

	SPRUCE("SPRUCE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/-8, /*maxTemp*/17, /*minEVT*/0.25f, /*maxEVT*/1, true,1),

	SYCAMORE("SYCAMORE", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/6, /*maxTemp*/30, /*minEVT*/0.25f, /*maxEVT*/1, false,0.7f),

	WHITECEDAR("WHITECEDAR", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/-5, /*maxTemp*/24, /*minEVT*/0.25f, /*maxEVT*/2, true,0.8f),

	WHITEELM("WHITEELM", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/30, /*minEVT*/0.25f, /*maxEVT*/1, false,1f),

	WILLOW("WILLOW", /*minRain*/1300f, /*maxRain*/16000f, /*minTemp*/10, /*maxTemp*/30, /*minEVT*/0.25f, /*maxEVT*/1f, false,0.7f),

	KAPOK("KAPOK", /*minRain*/1500f, /*maxRain*/16000f, /*minTemp*/20, /*maxTemp*/35, /*minEVT*/1f, /*maxEVT*/10f, true,1f),

	KOA("KOA", /*minRain*/750f, /*maxRain*/16000f, /*minTemp*/20f, /*maxTemp*/35, /*minEVT*/1, /*maxEVT*/10, true,0.7f),
	
	PALM("PALM", /*minRain*/750f, /*maxRain*/16000f, /*minTemp*/21f, /*maxTemp*/35, /*minEVT*/0, /*maxEVT*/10, true,1),

	UTACACIA("UTACACIA", /*minRain*/75f, /*maxRain*/500f, /*minTemp*/15, /*maxTemp*/50, /*minEVT*/0, /*maxEVT*/2, false,0.3f),
	
	EBONY("EBONY",/*minRain*/500f, /*maxRain*/7000f, /*minTemp*/20, /*maxTemp*/38, /*minEVT*/0.5f, /*maxEVT*/4, false,0.1f),
	
	FEVERTREE("FEVERTREE",/*minRain*/50f, /*maxRain*/250f, /*minTemp*/14, /*maxTemp*/50, /*minEVT*/0, /*maxEVT*/1, false,0.2f),
	
	BAOBAB("BAOBAB",/*minRain*/150f, /*maxRain*/3500f, /*minTemp*/21, /*maxTemp*/40, /*minEVT*/0, /*maxEVT*/4, false,0.2f),
	
	LIMBA("LIMBA",/*minRain*/1500f, /*maxRain*/10000f, /*minTemp*/18, /*maxTemp*/40, /*minEVT*/0.25f, /*maxEVT*/8, false,2),
	
	MAHOGANY("MAHOGANY",/*minRain*/1500f, /*maxRain*/10000f, /*minTemp*/22, /*maxTemp*/40, /*minEVT*/1.5f, /*maxEVT*/8, true,0.1f),
	
	TEAK("TEAK",/*minRain*/500f, /*maxRain*/8000f, /*minTemp*/17, /*maxTemp*/40, /*minEVT*/1, /*maxEVT*/8, true,0.1f),
	
	BAMBOO("BAMBOO",/*minRain*/600f, /*maxRain*/8000f, /*minTemp*/13, /*maxTemp*/35, /*minEVT*/0.5f, /*maxEVT*/4, true,1),
	
	GINGKO("GINGKO",/*minRain*/700f, /*maxRain*/2500f, /*minTemp*/10, /*maxTemp*/30, /*minEVT*/1, /*maxEVT*/4, false,0.2f);

	public final float minRain;
	public final float maxRain;
	public final float minTemp;
	public final float maxTemp;
	public final float minEVT;
	public final float maxEVT;
	public final boolean isEvergreen;
	public final float rarity;
	
	public static final EnumTree[] AMERICAS = new EnumTree[]{OAK,ASPEN,BIRCH,CHESTNUT,
			DOUGLASFIR,HICKORY,KOA,ASH,MAPLE,PINE,REDWOOD,SPRUCE,WHITECEDAR,WILLOW,KAPOK,PALM,MAHOGANY};
	public static final EnumTree[] EUROPE = new EnumTree[]{OAK,ASPEN,BIRCH,CHESTNUT,PINE,SPRUCE,SYCAMORE,WHITEELM,WILLOW,PALM};
	public static final EnumTree[] ASIA = new EnumTree[]{OAK,ASPEN,BIRCH,CHESTNUT,HICKORY,ASH,MAPLE,PINE,REDWOOD,SPRUCE,
			SYCAMORE,WHITEELM,WILLOW,PALM,EBONY,TEAK,BAMBOO,GINGKO};
	public static final EnumTree[] AFRICA = new EnumTree[]{OAK,ASH,MAPLE,PINE,SPRUCE,UTACACIA,WILLOW,PALM,EBONY,MAHOGANY,
			FEVERTREE,BAOBAB,LIMBA};

	public static final EnumTree[][]REGIONS = new EnumTree[][]{AMERICAS,EUROPE,AFRICA,ASIA};
	
	//Africa
	//Fevertree [native to Africa, Vachellia xanthophloea] [dry plains]
	//Baobab [dry plains and jungles? Africa]
	//Limba tree [native to Africa, Terminalia superba]
	//East African Mahogany [Khaya anthotheca]
	//Ebony
	
	//Asia
	//Teak [native to india and south east asia]
	//Bamboo
	//Gingko
	
	//General?
	//Fruitwood??
	//How would fruitwood behave? 
	
	
	

	private static final EnumTree MATERIALS[] = new EnumTree[] {
		OAK,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,KOA,ASH,MAPLE,PINE,REDWOOD,SPRUCE,
		SYCAMORE,UTACACIA,WHITECEDAR,WHITEELM,WILLOW,KAPOK,PALM};

	private EnumTree(String s, float i, float j, float mintemp, float maxtemp, float minevt, float maxevt, boolean e, float r)
	{
		minRain = i;
		maxRain = j;
		minTemp = mintemp;
		maxTemp = maxtemp;
		minEVT = minevt;
		maxEVT = maxevt;
		isEvergreen = e;
		rarity = Math.max(r, 0);
	}

}
