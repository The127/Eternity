/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.tiled;

/**
 * A wrapper class for tiled editor json export file support.
 * @author Julian Sven Baehr
 */
public class TiledTile {

	TiledAnimation[] animation;
	
	/**
	 * @return True if the animation array is not null.
	 */
	boolean hasAnimation(){
		return animation != null;
	}
}
