package de.eternity.map;

import java.util.ArrayList;

public class TileStorage {

	private ArrayList<Tile> tiles = new ArrayList<>();
	
	public void addTile(Tile tile, int index){
		tiles.add(tile);
	}
	
	/**
	 * Updates all tiles.
	 * @param delta
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
	
	public Tile get(int index){
		return tiles.get(index);
	}
}
