/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.tiled;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.eternity.GameData;
import de.eternity.gfx.TextureStorage;
import de.eternity.map.GameMap;
import de.eternity.map.TileStorage;
import de.eternity.map.Trigger;

/**
 * A wrapper class for tiled editor json export file support.
 * It creates game maps from the loaded data.
 * @author Julian Sven Baehr
 */
public class TiledMap{
	
	private String path;
	
	private int width;
	
	private TiledLayer[] layers;
	private TiledTileset[] tilesets;
	
	/**
	 * Loads a tiled editor json export file.
	 * @param path The path to the json file.
	 * @return A tiled map object that represents the file.
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 * @throws FileNotFoundException
	 */
	public static TiledMap readMap(String path) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		
		TiledMap map = new Gson().fromJson(new FileReader(path), TiledMap.class);
		map.path = path;
		
		return map;
	}
	
	/**
	 * Private empty constructor.
	 */
	private TiledMap(){
		//private constructor
	}
	
	/**
	 * Translates and calculates a game map from the loaded map data.
	 * @param textureStorage The global texture storage.
	 * @param tileStorage The global tile storage.
	 * @param gameData The game data manager object.
	 * @return The game map of this map.
	 */
	public GameMap createGameMap(TextureStorage textureStorage, TileStorage tileStorage, GameData gameData){
		
		//load tiles into the tile storage
		for(int i = 0; i < tilesets.length; i++){
			tilesets[i].initialize(tileStorage, textureStorage);
		}
		
		//translate tile texture ids
		int tileCount = layers[0].data.length;//layer 0 (first) is background layer
		for(int i = 0; i < tileCount; i++){
			//get tileset
			int tilesetIndex = getTilesetIndex(layers[0].data[i]);
			//get local tileset id
			
			int localTileId = layers[0].data[i] - tilesets[tilesetIndex].firstgid;
			
			//translate to global id
			layers[0].data[i] = textureStorage.translateToGlobalTextureId(tilesets[tilesetIndex].name, localTileId);
			
		}
		
		//get name
		String[] split = path.replace("\\", "/").split("/");
		String name = split[split.length-1].split("\\.")[0];
		
		//handle map objects
		if(layers.length > 1)
			for(int i = 0; i < layers[1].objects.length; i++){
				
				int tilesetIndex = getTilesetIndex(layers[1].objects[i].gid);
				layers[1].objects[i].initialize(gameData, tilesets[tilesetIndex].name, tilesets[tilesetIndex].firstgid);
			}
		
		//handle trigger areas
		HashMap<String, Trigger> triggers = new HashMap<>();
		if(layers.length > 2);
			for(int i = 0; i < layers[2].objects.length; i++){
				
				TiledObject rawTrigger = layers[2].objects[i];
				triggers.put(rawTrigger.name, new Trigger(rawTrigger.x, rawTrigger.y, rawTrigger.width, rawTrigger.height));
			}
		
		//return new map
		return new GameMap(name, layers[0].data, width, layers[1].objects, triggers, gameData);
	}
	
	/**
	 * @param mapTileId The id of the tile.
	 * @return The index of the tileset of the tile.
	 */
	private int getTilesetIndex(int mapTileId){
		
		for(int i = 0; i < tilesets.length; i++)
			if(mapTileId >= tilesets[i].firstgid 
					&& mapTileId < tilesets[i].firstgid + tilesets[i].tilecount)
				return i;
		
		throw new IllegalArgumentException("No local tileset in map '" + path + "' found with mapTileId '" + mapTileId + "'!");
	}
}
