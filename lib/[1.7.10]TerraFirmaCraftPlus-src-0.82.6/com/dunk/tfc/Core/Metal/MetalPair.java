package com.dunk.tfc.Core.Metal;

import com.dunk.tfc.api.Metal;

public class MetalPair 
{
	public Metal type;
	public float amount;
	public MetalPair(Metal t, float amnt)
	{
		type = t;
		amount = amnt;
	}
}
