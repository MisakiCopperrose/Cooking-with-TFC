package com.dunk.tfc.Commands;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.DataLayer;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PrintImageMapCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "printimage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if(!TFCOptions.enableDebugMode)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Debug Mode Required"));
			return;
		}

		MinecraftServer server = MinecraftServer.getServer();
		WorldServer world = server.worldServerForDimension(player.getEntityWorld().provider.dimensionId);
		if(params.length >= 2)
		{
			String name = params[1];
			if(params[0].equals("biome"))
			{
				int size = params.length >= 3 ? Integer.parseInt(params[2]) : 512;
				int skipSize = params.length >= 4 ? Integer.parseInt(params[3]) : 1;
				drawBiomeImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name, skipSize);
			}
			else if(params[0].equals("height"))
			{
				int size = params.length >= 3 ? Integer.parseInt(params[2]) : 512;
				int skipSize = params.length >= 4 ? Integer.parseInt(params[3]) : 1;
				drawHeightImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name, skipSize);
			}
			else if(params[0].equals("temp"))
			{
				int size = params.length >= 3 ? Integer.parseInt(params[2]) : 512;
				int skipSize = params.length >= 4 ? Integer.parseInt(params[3]) : 1;
				drawTempImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name, skipSize);
			}
			else if(params[0].equals("drainage"))
			{
				int size = params.length >= 3 ? Integer.parseInt(params[2]) : 512;
				int skipSize = params.length >= 4 ? Integer.parseInt(params[3]) : 1;
				drawDrainageImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name, skipSize);
			}
			else if(params[0].equals("ph"))
			{
				int size = params.length >= 3 ? Integer.parseInt(params[2]) : 512;
				int skipSize = params.length >= 4 ? Integer.parseInt(params[3]) : 1;
				drawPhImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name, skipSize);
			}
		}
	}
	public static void drawPhImage(int xCoord, int zCoord, int size, World world, String name, int skipSize)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			TerraFirmaCraft.LOG.info(name+".bmp");
			float perc = 0.1f;
			int sizeHalf = size/2;
			float count = 0;
			for(int x = -sizeHalf; x < sizeHalf; x++)
			{
				for(int z = -sizeHalf; z < sizeHalf; z++)
				{
					count++;
					int ph = TFC_Climate.getCacheManager(world).getDrainageLayerAt(xCoord+x*skipSize, zCoord+z*skipSize).data1;
					int g = ph * 50;
					graphics.setColor(Color.getColor("", g << 8));
					graphics.drawRect(x+sizeHalf, z+sizeHalf, 1, 1);
					if(count / (size*size) > perc)
					{
						TerraFirmaCraft.LOG.info((int)(perc*100)+"%");
						perc+=0.1f;
					}
				}
			}
			TerraFirmaCraft.LOG.info(name+".bmp Done!");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}
	public static void drawDrainageImage(int xCoord, int zCoord, int size, World world, String name, int skipSize)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			TerraFirmaCraft.LOG.info(name+".bmp");
			float perc = 0.1f;
			int sizeHalf = size/2;
			float count = 0;
			for(int x = -sizeHalf; x < sizeHalf; x++)
			{
				for(int z = -sizeHalf; z < sizeHalf; z++)
				{
					count++;
					DataLayer dl = TFC_Climate.getCacheManager(world).getDrainageLayerAt(xCoord+x*skipSize, zCoord+z*skipSize);
					int drainage = dl.data1;
					int r = (drainage*42)/2;
					int g = (drainage*42)/4;
					graphics.setColor(Color.getColor("", (r << 16) + (g << 8)));	
					graphics.drawRect(x+sizeHalf, z+sizeHalf, 1, 1);
					if(count / (size*size) > perc)
					{
						TerraFirmaCraft.LOG.info((int)(perc*100)+"%");
						perc+=0.1f;
					}
				}
			}
			TerraFirmaCraft.LOG.info(name+".bmp Done!");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}
	public static void drawTempImage(int xCoord, int zCoord, int size, World world, String name, int skipSize)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			TerraFirmaCraft.LOG.info(name+".bmp");
			float perc = 0.1f;
			int sizeHalf = size/2;
			float count = 0;
			for(int x = -sizeHalf; x < sizeHalf; x++)
			{
				for(int z = -sizeHalf; z < sizeHalf; z++)
				{
					count++;
					int temp = (int)(255*(TFC_Climate.getBioTemperature(world, xCoord+x*skipSize, zCoord+z*skipSize)/50));
					graphics.setColor(Color.getColor("", (temp << 16) + (temp << 8) + temp));	
					graphics.drawRect(x+sizeHalf, z+sizeHalf, 1, 1);
					if(count / (size*size) > perc)
					{
						TerraFirmaCraft.LOG.info((int)(perc*100)+"%");
						perc+=0.1f;
					}
				}
			}
			TerraFirmaCraft.LOG.info(name+".bmp Done!");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}
	
	public static void drawHeightImage(int xCoord, int zCoord, int size, World world, String name, int skipSize)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			TerraFirmaCraft.LOG.info(name+".bmp");
			float perc = 0.1f;
			float count = 0;
			double expansion = 256d / (256d - Global.SEALEVEL);
			double seaExpansion = 256d / (Global.SEALEVEL - 128);
			for(int x = -size/2; x < size/2; x++)
			{
				for(int z = -size/2; z < size/2; z++)
				{
					count++;
					int height = world.getTopSolidOrLiquidBlock(x*skipSize+xCoord, z*skipSize+zCoord);
					if(height > Global.SEALEVEL + 10)
					{
						height -=0;
					}
					int r,g,b;
					if(height <= Global.SEALEVEL)
					{
						r = 0;
						g = 0;
						b = (int) Math.min(255,((height - 128) * 4)  +64);
					}
					else
					{
						r = (int) ((height-Global.SEALEVEL)*expansion) + 16;
						g = Math.min(255, Math.min(255, (int) ((height-Global.SEALEVEL)*expansion))+32);
						b = (int) ((height-Global.SEALEVEL)*expansion) ;
					}
					
					graphics.setColor(Color.getColor("green",b + (g<<8) + (r<<16)));
					int bl = graphics.getColor().getBlue();
					graphics.drawRect(x+size/2, z+size/2, 1, 1);
					if(count / (size*size) > perc)
					{
						TerraFirmaCraft.LOG.info((int)(perc*100)+"%");
						perc+=0.1f;
					}
				}
			}
			TerraFirmaCraft.LOG.info(name+".bmp Done!");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}
	
	public static void drawBiomeImage(int xCoord, int zCoord, int size, World world, String name, int skipSize)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			TerraFirmaCraft.LOG.info(name+".bmp");
			float perc = 0.1f;
			float count = 0;
			for(int x = -size/2; x < size/2; x++)
			{
				for(int z = -size/2; z < size/2; z++)
				{
					count++;
					graphics.setColor(Color.getColor("", ((TFCBiome)world.getWorldChunkManager().getBiomeGenAt(x*skipSize+xCoord, z*skipSize+zCoord)).getBiomeColor()));	
					graphics.drawRect(x+size/2, z+size/2, 1, 1);
					if(count / (size*size) > perc)
					{
						TerraFirmaCraft.LOG.info((int)(perc*100)+"%");
						perc+=0.1f;
					}
				}
			}
			TerraFirmaCraft.LOG.info(name+".bmp Done!");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "";
	}

}
