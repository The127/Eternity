/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

import java.util.ArrayList;

/**
 * A global tile storage.
 * @author Julian Sven Baehr
 *
 */
public class TileStorage {

	private ArrayList<Tile> tiles = new ArrayList<>();
	
	/**
	 * Adds a tile to the global storage.
	 * @param tile The tile.
	 * @param index The index of the tile.
	 */
	public void addTile(Tile tile, int index){
		
		//ensure capacity
		if(index + 1 >= tiles.size()){
			tiles.ensureCapacity(index+1);
			for(int i = tiles.size(); i < index+1; i++)
				tiles.add(null);
		}
		
		//set the tile
		tiles.set(index, tile);
	}
	
	/**
	 * Updates all tiles.
	 * @param delta The time since the last call to this method.
	 */
	public void update(double delta){
		
		int size = tiles.size();
		for(int i = 0; i < size; i++){
			
			Tile tile = tiles.get(i);
			if(tile != null){
				tile.update(delta);
			}
		}
	}
	
	/**
	 * @param index The tile index.
	 * @return The tile at the given index.
	 */
	public Tile get(int index){
		return tiles.get(index);
	}
}
