package com.dunk.tfc.Entities.Mobs;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCorpse extends EntityLiving {

	
	private float rotation;
	private float growth;
	private NBTTagCompound animal;

	/* 0 = Bear
	 * 1 = chicken
	 * 2 = cow
	 * 3 = deer
	 * 4 = fish
	 * 5 = horse
	 * 6 = pheasant
	 * 7 = pig
	 * 8 = sheep
	 * 9 = wolf
	 */
	private int animalType;
	
	public EntityCorpse(World world, NBTTagCompound animal, int type)
	{
		this(world);
		this.animal = animal;
		this.animalType = type;
		this.growth = animal.getFloat("Growth");
	}
	
	public EntityCorpse(World world) {
		super(world);
		setSize(1f,1f);
		//noClip = false;
		ignoreFrustumCheck = true;
		if(animal == null)
		{
			animal = this.getEntityData();
		}	
		// TODO Auto-generated constructor stub
	}

	
	public NBTTagCompound getAnimalNBT()
	{
		return animal;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		animal = (NBTTagCompound) nbttagcompound.getTag("Animal");
		growth = animal.getFloat("Growth");
		animalType = nbttagcompound.getInteger("Type");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setTag("Animal",animal);
		nbttagcompound.setInteger("Type", animalType);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();	
		this.dataWatcher.addObject(13, Integer.valueOf(0)); //sex (1 or 0)
		this.dataWatcher.addObject(15, Integer.valueOf(0));		//age
		//this.dataWatcher.//The Animal NBT isn't getting passed from server to client, so the client neeeds that fixed
		
		this.dataWatcher.addObject(22, Float.valueOf(0f)); //Size, strength, aggression, obedience
		this.dataWatcher.addObject(23, Integer.valueOf(0)); //familiarity, familiarizedToday, pregnant, empty slot
		this.dataWatcher.addObject(24, String.valueOf("0")); // Time of conception, stored as a string since we can't do long
	}
	
	@Override
	public boolean isEntityInvulnerable(){
		return false;//true;
	}
	
	@Override
	public boolean canBePushed(){
		return true;
	}
	
	public float getGrowth()
	{
		return growth;
	}
	
	@Override
	public boolean canDespawn(){
		return true;
	}
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		syncData();
	}

	public void syncData()
	{
		if(dataWatcher!= null)
		{
			if(!this.worldObj.isRemote)
			{
				this.dataWatcher.updateObject(13, Integer.valueOf(animal.getInteger("Sex")));
				this.dataWatcher.updateObject(22, animal.getFloat("Size Modifier"));
				this.dataWatcher.updateObject(23,animalType);
			}
			else
			{
				animal.setInteger("Sex", this.dataWatcher.getWatchableObjectInt(13));
				animal.setFloat("Size Modifier",this.dataWatcher.getWatchableObjectFloat(22));
				animalType = this.dataWatcher.getWatchableObjectInt(23);
			}
		}
	}
	
	public int getAnimalType()
	{
		return animalType;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}
	
	
	
}
