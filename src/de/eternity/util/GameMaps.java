package de.eternity.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.LinkedList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.eternity.gfx.TextureStorage;
import de.eternity.map.GameMap;
import de.eternity.map.TileStorage;
import de.eternity.support.tiled.TiledMap;

public class GameMaps {

	private LinkedList<GameMap> gameMaps = new LinkedList<>();
	
	public void loadGameMaps(String path, TextureStorage textureStorage, TileStorage tileStorage){
		
		File root = new File(Paths.get("res", path).toAbsolutePath().toString());
		String[] subFiles = root.list();
		
		for(int i = 0; i < subFiles.length; i++){
			
//			System.out.println(subFiles[i]);
			String mapPath = Paths.get("res" + path, subFiles[i]).toAbsolutePath().toString();
			try {
				TiledMap tiledMap = TiledMap.readMap(mapPath);
				GameMap gameMap = tiledMap.createGameMap(textureStorage, tileStorage);
				gameMaps.add(gameMap);
				
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				throw new RuntimeException("Could not load map '" + mapPath + "'!", e);
			}
			
		}
	}
	
	public GameMap getGameMap(String name){
		
		for(int i = 0; i < gameMaps.size(); i++)
			if(gameMaps.get(i).getName().equals(name))
				return gameMaps.get(i);
		
		throw new IllegalArgumentException("No lua game state '" + name + "' found!");
	}
}
