package com.dunk.tfc.Blocks.Devices;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Handlers.Network.PlayerUpdatePacket;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDrum extends BlockTerra
{
	boolean isBig;

	private IIcon[] drumIcons;

	public BlockDrum()
	{
		super(Material.wood);
		this.setBlockBounds(0.2f, 0, 0.2f, 0.8f, 0.65f, 0.8f);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		
	}

	public BlockDrum(boolean b)
	{
		super(Material.wood);
		this.isBig = b;
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 0.9f, 0.9f);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		drumIcons = new IIcon[4];
		drumIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Drum Top");
		drumIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Drum Side");
		drumIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Drum Bottom");
		drumIcons[3] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Drum Side Small");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{

		if (side == 1)
			return drumIcons[0];
		else if (side == 0)
			return drumIcons[2];
		else
		{
			if (this == TFCBlocks.bigDrum)
			{
				return drumIcons[1];
			}
			return drumIcons[3];
		}
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if (side == 1)
			return drumIcons[0];
		else if (side == 0)
			return drumIcons[2];
		else
		{
			if (this == TFCBlocks.bigDrum)
			{
				return drumIcons[1];
			}
			return drumIcons[3];
		}
	}
	
	@SideOnly(Side.CLIENT)
	protected boolean hitDrumRight(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7,
			float par8, float par9)
	{
		int meta = world.getBlockMetadata(i, j, k);
		
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		ItemStack item = entityplayer.getCurrentEquippedItem();
		float pitch = 1.01f - (world.rand.nextFloat() * 0.005f) - (isBig ? 0.5f : 0f);
		pitch += 0.1f * ((float) meta);
		if (item != null && item.getItem() == TFCItems.stick && item.stackSize == 2)
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_H))
			{
				//world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0, entityplayer.posZ + 0,
				//		TFC_Sounds.DRUMRIM, 0.8f, isBig ? 0.7f : 1.2f);
				
				PositionedSoundRecord drumHit = new PositionedSoundRecord(new ResourceLocation(TFC_Sounds.DRUMRIM),0.8f,pitch,
						(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
				//Minecraft.getMinecraft().getSoundHandler().playSound(drumHit);
				
				pi.playedPitch = pitch;
				pi.notePlayed = 2; //2 == rim
				PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,8);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
				
			}
			else
			{
				//world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0, entityplayer.posZ + 0,
				//		TFC_Sounds.DRUM, 0.6f, pitch + 0.2f);
				PositionedSoundRecord drumHit = new PositionedSoundRecord(new ResourceLocation(TFC_Sounds.DRUM),0.6f,pitch,
						(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
				//Minecraft.getMinecraft().getSoundHandler().playSound(drumHit);
				
				pi.playedPitch = pitch;
				pi.notePlayed = 3; //3 == drum
				PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,8);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			}
		}
		else
		{
			PositionedSoundRecord drumHit = new PositionedSoundRecord(new ResourceLocation(TFC_Sounds.BONGO),0.6f,pitch,
					(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
			//Minecraft.getMinecraft().getSoundHandler().playSound(drumHit);
			//world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0, entityplayer.posZ + 0, TFC_Sounds.BONGO,
			//		0.6f, pitch);
			pi.playedPitch = pitch;
			pi.notePlayed = 4; //4 == bongo
			PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,8);
			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean rightClickClient(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float m,
			float n, float o)
	{
int meta = world.getBlockMetadata(i, j, k);
		
		if (entityplayer.isSneaking() && entityplayer.getCurrentEquippedItem() == null)
		{

			meta = (meta + 1) % 4;
			world.setBlockMetadataWithNotify(i, j, k, meta, 2);
			float pitch = 1.01f - (world.rand.nextFloat() * 0.005f) - (isBig ? 0.5f : 0f);
			pitch += 0.1f * ((float) meta);
			world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0, entityplayer.posZ + 0, TFC_Sounds.BONGO,
					0.6f, pitch);
			return false;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_G))
		{
			float yaw = entityplayer.rotationYaw;
			yaw += (360 * Math.ceil((Math.abs(yaw)/360)));
			yaw %= 360;
			// North
			if (yaw >= 135 && yaw < 225)
			{
				Block b = world.getBlock(i + 1, j, k);
				if (b instanceof BlockDrum)
				{
					return ((BlockDrum)b).hitDrumRight(world,i+1,j,k,entityplayer,l,m,n,o);
				}
			}
			// South
			else if (yaw >= 315 || yaw < 45)
			{
				Block b = world.getBlock(i -1, j, k);
				if (b instanceof BlockDrum)
				{
					return ((BlockDrum)b).hitDrumRight(world,i-1,j,k,entityplayer,l,m,n,o);

				}
			}
			// East
			else if (yaw >= 225 && yaw < 315)
			{
				Block b = world.getBlock(i, j, k+1);
				if (b instanceof BlockDrum)
				{
					return ((BlockDrum)b).hitDrumRight(world,i,j,k+1,entityplayer,l,m,n,o);
					
				}
			}
			// West
			else
			{
				Block b = world.getBlock(i, j, k-1);
				if (b instanceof BlockDrum)
				{
					return ((BlockDrum)b).hitDrumRight(world,i,j,k-1,entityplayer,l,m,n,o);
				}
			}
			/*Vec3 look = entityplayer.getLookVec();
			// System.out.println(look.xCoord +", " + look.yCoord +", " +
			// look.zCoord);
			look = Vec3.createVectorHelper(look.xCoord * 3f, look.yCoord * 3f, look.zCoord * 3f);
			if (isBig)
			{
				look.rotateAroundY(-(float) Math.PI / 4f);
			}
			else
			{
				look.rotateAroundY(-(float) Math.PI / 4f);
			}
			// System.out.println(look.xCoord +", " + look.yCoord +", " +
			// look.zCoord);
			// entityplayer.getLook(4);

			Vec3 ePos = Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + entityplayer.eyeHeight,
					entityplayer.posZ);
			MovingObjectPosition mop0 = world.func_147447_a(ePos, look.addVector(ePos.xCoord, ePos.yCoord, ePos.zCoord),
					false, false, true);
			if (mop0.blockX != i || mop0.blockY != j || mop0.blockZ != k)
			{
				Block b = world.getBlock(mop0.blockX, mop0.blockY, mop0.blockZ);
				if (b instanceof BlockDrum)
				{
					return b.onBlockActivated(world, mop0.blockX, mop0.blockY, mop0.blockZ, entityplayer, l, m,
							n, o);
				}
			}*/
		}
		// MovingObjectPosition mop = mop0;//world.rayTraceBlocks(ePos,
		// look.addVector(ePos.xCoord,ePos.yCoord,ePos.zCoord));
		// System.out.println( entityplayer.posX +", " + entityplayer.posY +", "
		// + entityplayer.posZ);
		// System.out.println( mop.blockX +", " + mop.blockY +", " +
		// mop.blockZ);
		// System.out.println(world.getBlock(mop.blockX, mop.blockY, mop.blockZ)
		// + ", " + world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
		return hitDrumRight(world,i,j,k,entityplayer,l,m,n,o);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float m,
			float n, float o)
	{
		
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			if (entityplayer.isSneaking() && entityplayer.getCurrentEquippedItem() == null)
			{

				meta = (meta + 1) % 4;
				world.setBlockMetadataWithNotify(i, j, k, meta, 2);
				float pitch = 1.01f - (world.rand.nextFloat() * 0.005f) - (isBig ? 0.5f : 0f);
				pitch += 0.1f * ((float) meta);
				world.playSoundEffect(entityplayer.posX + 0, entityplayer.posY + 0, entityplayer.posZ + 0, TFC_Sounds.BONGO,
						0.6f, pitch);
				return false;
			}
			return !entityplayer.isSneaking();
		}
		return rightClickClient(world, i,j,k,entityplayer,l,m,n,o);
		
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.drumRenderId;
	}

	@SideOnly(Side.CLIENT)
	protected void hitDrumLeft(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		
		int meta = world.getBlockMetadata(i, j, k);
		ItemStack item = entityplayer.getCurrentEquippedItem();
		float pitch = 1.01f - (world.rand.nextFloat() * 0.005f) - (isBig ? 0.5f : 0f);
		pitch += 0.1f * ((float) meta);
		if (item != null && item.getItem() == TFCItems.stick && item.stackSize == 2)
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_H))
			{
				PositionedSoundRecord drumHit = new PositionedSoundRecord(new ResourceLocation(TFC_Sounds.DRUMRIM),0.8f,pitch,
						(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
				//Minecraft.getMinecraft().getSoundHandler().playSound(drumHit);
				
				pi.playedPitch = pitch;
				pi.notePlayed = 2; //2 == rim
				PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,8);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			}
			else
			{
				PositionedSoundRecord drumHit = new PositionedSoundRecord(new ResourceLocation(TFC_Sounds.DRUM),0.6f,pitch,
						(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
				//Minecraft.getMinecraft().getSoundHandler().playSound(drumHit);
				
				pi.playedPitch = pitch;
				pi.notePlayed = 3; //3 == drum
				PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,8);
				TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
			}
		}
		else
		{
			PositionedSoundRecord drumHit = new PositionedSoundRecord(new ResourceLocation(TFC_Sounds.BONGO),0.6f,pitch,
					(int)entityplayer.posX,(int)entityplayer.posY,(int)entityplayer.posZ);
			//Minecraft.getMinecraft().getSoundHandler().playSound(drumHit);
			
			pi.playedPitch = pitch;
			pi.notePlayed = 4; //4 == bongo
			PlayerUpdatePacket pkt = new PlayerUpdatePacket(entityplayer,8);
			TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void clickLeft(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			float yaw = entityplayer.rotationYaw;
			yaw += (360 * Math.ceil((Math.abs(yaw)/360)));
			yaw %= 360;
			// North
			if (yaw >= 135 && yaw < 225)
			{
				Block b = world.getBlock(i - 1, j, k);
				if (b instanceof BlockDrum)
				{
					((BlockDrum)b).hitDrumLeft(world, i-1, j, k, entityplayer);
					return;
				}
			}
			// South
			else if (yaw >= 315 || yaw < 45)
			{
				Block b = world.getBlock(i +1, j, k);
				if (b instanceof BlockDrum)
				{
					((BlockDrum)b).hitDrumLeft(world, i+1, j, k, entityplayer);
					return;
				}
			}
			// East
			else if (yaw >= 225 && yaw < 315)
			{
				Block b = world.getBlock(i, j, k-1);
				if (b instanceof BlockDrum)
				{
					((BlockDrum)b).hitDrumLeft(world, i, j, k-1, entityplayer);
					return;
				}
			}
			// West
			else
			{
				Block b = world.getBlock(i, j, k+1);
				if (b instanceof BlockDrum)
				{
					((BlockDrum)b).hitDrumLeft(world, i, j, k+1, entityplayer);
					return;
				}
			}
			// if (mop0.blockX != i || mop0.blockY != j || mop0.blockZ != k)
			// {
			// Block b = world.getBlock(mop0.blockX, mop0.blockY, mop0.blockZ);
			// if (b instanceof BlockDrum)
			// {
			// b.onBlockClicked(world, mop0.blockX, mop0.blockY, mop0.blockZ,
			// entityplayer);
			// return;
			// }
			// }
		}
		hitDrumLeft(world,i,j,k,entityplayer);
	}
	
	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (!world.isRemote)
		{
			return;
		}
		clickLeft(world,i,j,k,entityplayer);
	}
	
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}

}
