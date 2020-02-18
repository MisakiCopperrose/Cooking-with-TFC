package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class ModelPalmFrond extends ModelBox 
{
	/**
     * The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube
     */
    private PositionTextureVertex[] vertexPositions;

    /** An array of 6 TexturedQuads, one for each face of a cube */
    private TexturedQuad[] quadList;
	
	public ModelPalmFrond(ModelRenderer renderer, int textureOffsetX, int textureOffsetY) {
		super(renderer, textureOffsetX, textureOffsetY, 0.5F, 0, 0.5F, 32, 0, 16, 0);
		
		float originX = 0f;
		float originY = 0.1f;
		float originZ = 0f;
		
        this.vertexPositions = new PositionTextureVertex[16];
        this.quadList = new TexturedQuad[4];
        float maxX = originX + 16;
        float maxZ = originZ + 32;
        float maxX2 = originX - 16;
        
        //First part of frond
        PositionTextureVertex vert0 = new PositionTextureVertex(originX, originY+8f, originZ, 0.0F, 0.0F);
        PositionTextureVertex vert1 = new PositionTextureVertex(maxX-2, originY+4f, originZ, 16.0F, 0.0F);
        PositionTextureVertex vert2 = new PositionTextureVertex(maxX, originY+6f, maxZ, 16.0F, 32.0F);
        PositionTextureVertex vert3 = new PositionTextureVertex(originX, originY+12f, maxZ, 0.0F, 32.0F);
       
        this.vertexPositions[0] = vert0;
        this.vertexPositions[1] = vert1;
        this.vertexPositions[2] = vert2;
        this.vertexPositions[3] = vert3;
        
        //Second part of frond
        vert0 = new PositionTextureVertex(originX, originY+8f, originZ, 0.0F, 0.0F);
        vert1 = new PositionTextureVertex(maxX2+2, originY+4f, originZ, 16.0F, 0.0F);
        vert2 = new PositionTextureVertex(maxX2, originY+6f, maxZ, 16.0F, 32.0F);
        vert3 = new PositionTextureVertex(originX, originY+12f, maxZ, 0.0F, 32.0F);
       
        this.vertexPositions[4] = vert0;
        this.vertexPositions[5] = vert1;
        this.vertexPositions[6] = vert2;
        this.vertexPositions[7] = vert3;
        
      //Third part of frond
        vert0 = new PositionTextureVertex(originX, originY+12f, maxZ, 0.0F, 0.0F);
        vert1 = new PositionTextureVertex(maxX, originY+6f, maxZ, 16.0F, 0.0F);
        vert2 = new PositionTextureVertex(maxX-6, originY+2f, maxZ*2, 16.0F, 32.0F);
        vert3 = new PositionTextureVertex(originX, originY+6f, maxZ*2, 0.0F, 32.0F);
       
        this.vertexPositions[8] = vert0;
        this.vertexPositions[9] = vert1;
        this.vertexPositions[10] = vert2;
        this.vertexPositions[11] = vert3;
        
      //Fourth part of frond
        vert0 = new PositionTextureVertex(originX, originY+12f, maxZ, 0.0F, 0.0F);
        vert1 = new PositionTextureVertex(maxX2, originY+6f, maxZ, 16.0F, 0.0F);
        vert2 = new PositionTextureVertex(maxX2+6, originY+2f, maxZ*2, 16.0F, 32.0F);
        vert3 = new PositionTextureVertex(originX, originY+6f, maxZ*2, 0.0F, 32.0F);
       
        this.vertexPositions[12] = vert0;
        this.vertexPositions[13] = vert1;
        this.vertexPositions[14] = vert2;
        this.vertexPositions[15] = vert3;
        
        
        int x2 = textureOffsetX + 0;
        int x3 = textureOffsetX + 32;
            
        int y1 = textureOffsetY + 0;
        int y2 = textureOffsetY + 16;
        
        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] {this.vertexPositions[0], this.vertexPositions[3], this.vertexPositions[2], this.vertexPositions[1]}, 
        		x2, y1, x3, y2, 32, 16); // long
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] {this.vertexPositions[7], this.vertexPositions[4], this.vertexPositions[5], this.vertexPositions[6]}, 
        		x2, y1, x3, y2, 32, 16); // long
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] {this.vertexPositions[8], this.vertexPositions[11], this.vertexPositions[10], this.vertexPositions[9]}, 
        		x2, y1, x3, y2, 32, 16); // long
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] {this.vertexPositions[15], this.vertexPositions[12], this.vertexPositions[13], this.vertexPositions[14]}, 
        		x2, y1, x3, y2, 32, 16); // long
	}
	
	@SideOnly(Side.CLIENT)
    public void render(Tessellator par1Tessellator, float par2)
    {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
        for (int var3 = 0; var3 < this.quadList.length; ++var3)
        {
            this.quadList[var3].draw(par1Tessellator, par2);
        }
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

}
