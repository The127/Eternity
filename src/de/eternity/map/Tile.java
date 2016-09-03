/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

import java.awt.Rectangle;

import de.eternity.gfx.Animation;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.TextureStorage;

/**
 * A single tile.
 * @author Julian Sven Baehr
 *
 */
public class Tile {
	
	private Animation animation;
	private boolean isSolid;
	private Rectangle[] collisionRectangles;
	
	/**
	 * Creates a new tile with the given animation.
	 * @param animation The tile animation.
	 */
	public Tile(Animation animation, boolean isSolid, Rectangle[] collisionRectangles){
		this.animation = animation;
		this.isSolid = isSolid;
		this.collisionRectangles = collisionRectangles;
	}
	
	/**
	 * Updates the tile.
	 * @param delta The time since the last call to this method.
	 */
	public void update(double delta){
		animation.update(delta);
	}
	
	/**
	 * Call this method only on those tiles that truly are under the rectangle anyway.
	 * @param tileX The world x of the tile.
	 * @param tileY The world y of the tile.
	 * @param rectX The world x of the rectangle.
	 * @param rectY The world y of the rectangle.
	 * @param rectWidth The width of the rectangle.
	 * @param rectHeight The height of the rectangle.
	 * @return True if there is a collision.
	 */
	public boolean collides(double tileX, double tileY, int rectX, int rectY, int rectWidth, int rectHeight){
		
		if(isSolid) return true;//is fully solid
		
		if(collisionRectangles != null)
			for(int i = 0; i < collisionRectangles.length; i++){
				Rectangle cRect = collisionRectangles[i];
				if(!(cRect.x + tileX > rectX + rectWidth || cRect.x + cRect.width + tileX < rectX
						|| cRect.y + tileY > rectY + rectHeight || cRect.y + cRect.height + tileY < rectY))
					//collision found
					return true;
			}
		
		//no collision found
		return false;
	}
	
	/**
	 * Renders the tile.
	 * @param renderQueue The render queue.
	 * @param textureStorage The global texture storage.
	 * @param x The tile x coordinate.
	 * @param y The tile y coordinate.
	 */
	public void applyRenderContext(IRenderQueue renderQueue, TextureStorage textureStorage, double x, double y){
		renderQueue.addBackground(animation.getCurrentTexture(textureStorage), x, y);
	}
}
