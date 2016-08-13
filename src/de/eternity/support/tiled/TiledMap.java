package de.eternity.support.tiled;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.eternity.gfx.TextureStorage;
import de.eternity.map.GameMap;

public class TiledMap{
	
	private String path;
	
	private int width, height, tilewidth;
	
	private TiledLayer[] layers;
	private TiledTileset[] tilesets;
	
	public static TiledMap readMap(String path) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		
		TiledMap map = new Gson().fromJson(new FileReader(path), TiledMap.class);
		map.path = path;
		
		return map;
	}
	
	/**
	 * Translates and calculates a game map from the loaded map data.
	 * @param textureStorage
	 * @return The game map of this map.
	 */
	public GameMap createGameMap(TextureStorage textureStorage){
		
		//tilesets are already loaded into memory
		
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
		
		return new GameMap(layers[0].data, width, height, tilewidth);
	}
	
	private int getTilesetIndex(int mapTileId){
		
		for(int i = 0; i < tilesets.length; i++)
			if(mapTileId >= tilesets[i].firstgid 
					&& mapTileId < tilesets[i].firstgid + tilesets[i].tilecount)
				return i;
		
		throw new IllegalArgumentException("No local tileset in map '" + path + "' found with mapTileId '" + mapTileId + "'!");
	}
}