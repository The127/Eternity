package de.eternity.gfx;

import java.awt.Rectangle;

public class RenderQueueEntry {
	
	private Rectangle toDraw = new Rectangle();
	private Texture texture;
	private int x, y;
	private int depth;
	
	public void enableBackgroundMode(){
		depth = Renderer.BACKGROUND_DEPTH;
	}
	
	/**
	 * Sets the values and calculates the area of the texture that needs to be drawn.
	 * @return True if the area is greater than zero.
	 */
	public boolean setValues(Texture texture, double x, double y, Rectangle cameraArea){
		this.texture = texture;
		this.x = (int)(x + 0.5d);
		this.y = (int)(y + 0.5d);
		this.depth = (int) (y + texture.getHeight());

		//TODO calc draw area
		return true;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getDepth(){
		return depth;
	}
}
