/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

/**
 * An interface to restrict access to specific render queue functions.
 * @author Julzenberger
 *
 */
public interface IRenderQueue {

	/**
	 * @return The game camera.
	 */
	Camera getCamera();
	
	/**
	 * Adds an entity to the queue.
	 * @param texture The entities texture.
	 * @param x The entities x coordinate.
	 * @param y The entities y coordinate.
	 */
	void addEntity(Texture texture, double x, double y);
	
	/**
	 * Adds a background texture to the queue.
	 * @param texture The background texture.
	 * @param x The background x coordinate.
	 * @param y The background y coordinate.
	 */
	void addBackground(Texture texture, double x, double y);
}
