package de.eternity.gfx;

import java.awt.Rectangle;

public class RenderQueueEntry {
	
	private Rectangle cameraArea;
	
	private Rectangle toDraw = new Rectangle();
	private Texture texture;
	private double x, y;
	private int depth = 0;
	
	public void enableBackgroundMode(){
		depth = -1;
	}
	
	public void setValues(Texture texture, double x, double y, Rectangle cameraArea){
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.depth = (int) ((y + 0.5d) + texture.getHeight());
		this.cameraArea = cameraArea;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public int getXAsInt(){
		return (int)(x + 0.d);
	}

	public int getYAsInt(){
		return (int)(y + 0.d);
	}
	
	/**
	 * Calculates the area of the texture that needs to be drawn.
	 * @return True if the area is greate than zero.
	 */
	boolean calculateArea(){
		
		//TODO calc
		return true;
	}
}
