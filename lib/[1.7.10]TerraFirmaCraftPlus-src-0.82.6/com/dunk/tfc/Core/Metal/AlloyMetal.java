package com.dunk.tfc.Core.Metal;

import com.dunk.tfc.api.Metal;

public class AlloyMetal 
{
	public Metal metalType;
	/**
	 * Value represented as a percentage.
	 */
	public float metal;
	
	public AlloyMetal(Metal e, float m)
	{
		metalType = e;
		metal = m;
	}
}
