/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

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
	
	/**
	 * Creates a new tile with the given animation.
	 * @param animation
	 */
	public Tile(Animation animation){
		this.animation = animation;
	}
	
	/**
	 * Updates the tile.
	 * @param delta The time since the last call to this method.
	 */
	public void update(double delta){
		animation.update(delta);
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
