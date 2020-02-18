package com.dunk.tfc.api.Enums;

public enum EnumFoodGroup
{
	Fruit(new float[]{0.667f,0.333f,0f,0f,0f}),
	Vegetable(new float[]{0.1f,0.1f,0.5f,0.1f,0.2f}),
	Grain(new float[]{0.15f,0.05f,0.5f,0f,0.15f}),
	Protein(new float[]{0.128f,0.107f,0.286f,0.071f,0.357f}),
	Dairy(new float[]{0.083f,0.083f,0.417f,0f,0.417f}),
	None(new float[]{0f,0f,0f,0f,0f});
	
	//Sweet, Sour, Salty, Bitter, Savory
	public final float[] tastePreferences;
	
	private EnumFoodGroup(float[] tastes)
	{
		tastePreferences = tastes;
	}
}
