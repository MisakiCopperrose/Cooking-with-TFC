package com.dunk.tfc.api;

import com.dunk.tfc.Core.FluidBaseTFC;

import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;

public class TFCFluids
{
	public static final Fluid SALTWATER = new FluidBaseTFC("saltwater").setBaseColor(0x354d35);
	public static final Fluid FRESHWATER = new FluidBaseTFC("freshwater").setBaseColor(0x6974EC);//.setBaseColor(0x354d35);
	public static final Fluid HOTWATER = new FluidBaseTFC("hotwater").setBaseColor(0x1f5099).setTemperature(350/*Kelvin, Rough temp of spring in Aachen, Germany */);
	public static final Fluid LAVA = new FluidBaseTFC("lavatfc").setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setUnlocalizedName(Blocks.lava.getUnlocalizedName());
	public static final Fluid RUM = new FluidBaseTFC("rum").setBaseColor(0x6e0123);
	public static final Fluid BEER = new FluidBaseTFC("beer").setBaseColor(0xc39e37);
	public static final Fluid WHEATBEER = new FluidBaseTFC("wheatbeer").setBaseColor(0xFFD177);
	public static final Fluid CORNBEER = new FluidBaseTFC("cornbeer").setBaseColor(0xFFC90E);
	public static final Fluid RYEBEER = new FluidBaseTFC("ryebeer").setBaseColor(0xB79556);
	public static final Fluid RICEBEER = new FluidBaseTFC("ricebeer").setBaseColor(0xC6BE3A);
	public static final Fluid RYEWHISKEY = new FluidBaseTFC("ryewhiskey").setBaseColor(0xc77d51);
	public static final Fluid RICEWHISKEY = new FluidBaseTFC("ricewhiskey").setBaseColor(0xC7B651);
	public static final Fluid WHISKEY = new FluidBaseTFC("whiskey").setBaseColor(0x875327);
	public static final Fluid BARLEYWHISKEY = new FluidBaseTFC("barleywhiskey").setBaseColor(0x583719);
	public static final Fluid CORNWHISKEY = new FluidBaseTFC("cornwhiskey").setBaseColor(0xBE8C27);
	public static final Fluid SAKE = new FluidBaseTFC("sake").setBaseColor(0xb7d9bc);
	public static final Fluid WHEATWINE = new FluidBaseTFC("wheatwine").setBaseColor(0xB7C680);
	public static final Fluid BARLEYWINE = new FluidBaseTFC("barleywine").setBaseColor(0xDDB280);
	public static final Fluid RYEWINE = new FluidBaseTFC("ryewine").setBaseColor(0xC69F73);
	public static final Fluid CORNWINE = new FluidBaseTFC("cornwine").setBaseColor(0xF0CD73);
	public static final Fluid AGAVEWINE = new FluidBaseTFC("agavewine").setBaseColor(0xB2CD5D);
	public static final Fluid POTATOWINE = new FluidBaseTFC("potatowine").setBaseColor(0xB2CDBE);
	public static final Fluid CANEWINE = new FluidBaseTFC("canewine").setBaseColor(0x97CC94);
	public static final Fluid BRANDY = new FluidBaseTFC("brandy").setBaseColor(0xB26159);
	public static final Fluid BERRYBRANDY = new FluidBaseTFC("berrybrandy").setBaseColor(0xB26D7C);
	public static final Fluid APPLEJACK = new FluidBaseTFC("applejack").setBaseColor(0xF8AC4F);
	public static final Fluid VODKA = new FluidBaseTFC("vodka").setBaseColor(0xdcdcdc);
	public static final Fluid SHOCHU = new FluidBaseTFC("shochu").setBaseColor(0xdcdcdc);
	public static final Fluid TEQUILA = new FluidBaseTFC("tequila").setBaseColor(0xC2DCCD);
	public static final Fluid CIDER = new FluidBaseTFC("cider").setBaseColor(0xb0ae32);
	public static final Fluid TANNIN = new FluidBaseTFC("tannin").setBaseColor(0x63594e);
	public static final Fluid VINEGAR = new FluidBaseTFC("vinegar").setBaseColor(0xc7c2aa);
	public static final Fluid BRINE = new FluidBaseTFC("brine").setBaseColor(0xdcd3c9);
	public static final Fluid LIMEWATER = new FluidBaseTFC("limewater").setBaseColor(0xb4b4b4);
	public static final Fluid MILK = new FluidBaseTFC("milk").setBaseColor(0xffffff);
	public static final Fluid MILKCURDLED = new FluidBaseTFC("milkcurdled").setBaseColor(0xfffbe8);
	public static final Fluid MILKVINEGAR = new FluidBaseTFC("milkvinegar").setBaseColor(0xfffbe8);
	public static final Fluid REDDYE = new FluidBaseTFC("reddye").setBaseColor(0x990000);
	public static final Fluid BLUEDYE = new FluidBaseTFC("bluedye").setBaseColor(0x000099);
	public static final Fluid GREENDYE = new FluidBaseTFC("greendye").setBaseColor(0x009900);
	public static final Fluid YELLOWDYE = new FluidBaseTFC("yellowdye").setBaseColor(0x888800);
	public static final Fluid PURPLEDYE = new FluidBaseTFC("purpledye").setBaseColor(0x880088);
	public static final Fluid ORANGEDYE = new FluidBaseTFC("orangedye").setBaseColor(0x994400);
	public static final Fluid WHITEDYE = new FluidBaseTFC("whitedye").setBaseColor(0xeeeeee);
	public static final Fluid BLACKDYE = new FluidBaseTFC("blackdye").setBaseColor(0x101010);
	public static final Fluid BERRYWINE = new FluidBaseTFC("berrywine").setBaseColor(0x8F2F65);
	public static final Fluid FRUITWINE = new FluidBaseTFC("fruitwine").setBaseColor(0xC95561);
	public static final Fluid FRUITBRANDY = new FluidBaseTFC("fruitbrandy").setBaseColor(0xC98861);
	public static final Fluid HONEY = new FluidBaseTFC("honey").setBaseColor(0xEC8F00);
	public static final Fluid MEAD = new FluidBaseTFC("mead").setBaseColor(0xFFBA00);
	public static final Fluid HONEYWATER = new FluidBaseTFC("honeywater").setBaseColor(0xFFDB87);
	public static final Fluid HONEYBRANDY = new FluidBaseTFC("honeybrandy").setBaseColor(0xFF9739);
	//Steph helped me with the colour. thanks steph
	public static final Fluid WINE = new FluidBaseTFC("wine").setBaseColor(0x49192C);
	public static final Fluid OLIVEOIL = new FluidBaseTFC("oliveoil").setBaseColor(0x6a7537);
	public static final Fluid GRAPEJUICE = new FluidBaseTFC("grapejuice").setBaseColor(0xA342CD);
	public static final Fluid CANEJUICE = new FluidBaseTFC("canejuice").setBaseColor(0xB2D9B6);
	
}
