/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * A render queue entry is a reusable data holder for render data.
 * @author Julian Sven Baehr
 */
public class RenderQueueEntry {
	
	private Rectangle toDraw = new Rectangle();
	private Texture texture;
	private int x, y;
	private int depth;
	
	/**
	 * Sets this render queue entry to background mode.
	 * Background entries are drawn in the background.
	 * Background entries should not overlap.
	 * Behavior for overlapping entries is not defined.
	 */
	public void enableBackgroundMode(){
		depth = Renderer.BACKGROUND_DEPTH;
	}
	
	/**
	 * Sets the values and calculates the area of the texture that needs to be drawn.
	 * If this entry is not within the camera area it will not be drawn.
	 * @param texture The texture that is to be drawn.
	 * @param x The x coordinate of this entry.
	 * @param y The y coordinate of this entry.
	 * @param cameraArea The area that the camera captures.
	 * @return True if the area is greater than zero.
	 */
	public boolean setValues(Texture texture, double x, double y, Rectangle cameraArea){
		this.texture = texture;
		this.x = (int)(x + 0.5d);
		this.y = (int)(y + 0.5d);
		this.depth = (int) (y + texture.getHeight());
		
		toDraw.x = this.x;
		toDraw.y = this.y;
		toDraw.width = texture.getWidth();
		toDraw.height = texture.getHeight();
		
		Rectangle2D.intersect(toDraw, cameraArea, toDraw);
			
		return toDraw.getWidth() > 0 && toDraw.getHeight() > 0;
	}
	
	/**
	 * @return The texture of this entry.
	 */
	public Texture getTexture(){
		return texture;
	}

	/**
	 * @return The draw x coordinate of this entry.
	 */
	public int getDrawX(){
		return toDraw.x;
	}

	/**
	 * @return The draw y coordinate of this entry.
	 */
	public int getDrawY(){
		return toDraw.y;
	}

	/**
	 * @return The width of this entry.
	 */
	public int getDrawWidth(){
		return toDraw.width;
	}

	/**
	 * @return The height of this entry.
	 */
	public int getDrawHeight(){
		return toDraw.height;
	}

	/**
	 * @return The world x of this entry.
	 */
	public int getX(){
		return x;
	}

	/**
	 * @return The world y of this entry.
	 */
	public int getY(){
		return y;
	}

	/**
	 * @return The draw depth of this entry.
	 */
	public int getDepth(){
		return depth;
	}
}
