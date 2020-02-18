package com.dunk.tfc.Entities.Mobs;

import java.util.ArrayList;

import com.dunk.tfc.Entities.AI.AIEatGrass;
import com.dunk.tfc.api.Entities.IAnimal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public abstract class EntityAnimalTFC extends EntityAnimal implements IAnimal
{
	protected static final float GESTATION_PERIOD = 9.0f;
	/*
	 * 1 - dimorphism = the average relative size of females : males. This is
	 * calculated by cube-square law from the square root of the ratio of female
	 * mass : male mass
	 */
	protected static final float DIMORPHISM = 0.1822f;
	protected static final int DEGREE_OF_DIVERSION = 1;
	protected static final int FAMILIARITY_CAP = 30;
	protected final AIEatGrass aiEatGrass = new AIEatGrass(this);

	protected boolean isDomesticated;
	protected boolean cantSleep = false;
	protected int sleepTimer = 0;
	protected boolean isSleeping = false;
	public boolean mating = false;

	protected int numberCuts, numberBleeding;
	protected ArrayList<Integer> cuts, bleeding;

	protected long animalID;
	protected int sex;
	protected int hunger;
	protected long hasMilkTime;
	protected boolean canMilk;
	protected boolean pregnant;
	protected int pregnancyRequiredTime;
	protected long timeOfConception;
	protected float mateSizeMod;
	protected float mateStrengthMod;
	protected float mateAggroMod;
	protected float mateObedMod;
	protected float sizeMod; // How large the animal is
	protected float strengthMod; // how strong the animal is
	protected float aggressionMod = 1;// How aggressive / obstinate the animal is
	protected float obedienceMod = 1; // How well the animal responds to commands.
	protected boolean inLove;

	protected int familiarity;
	protected long lastFamiliarityUpdate;
	protected boolean familiarizedToday;
	
	public EntityAnimalTFC(World p_i1681_1_)
	{
		super(p_i1681_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean canFamiliarize()
	{
		return !isAdult() || isAdult() && this.familiarity <= FAMILIARITY_CAP;
	}

}
