package de.eternity.map;

import java.util.ArrayList;

public class TileStorage {

	private ArrayList<Tile> tiles = new ArrayList<>();
	
	public void addTile(Tile tile, int index){
		tiles.add(tile);
	}
}
