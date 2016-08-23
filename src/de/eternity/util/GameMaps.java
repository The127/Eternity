package de.eternity.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.LinkedList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.eternity.GameData;
import de.eternity.map.GameMap;
import de.eternity.support.tiled.TiledMap;

public class GameMaps {

	private LinkedList<GameMap> gameMaps = new LinkedList<>();
	
	public void loadGameMaps(String path, GameData gameData){
		
		File root = new File(Paths.get("res", path).toAbsolutePath().toString());
		String[] subFiles = root.list();
		
		if(subFiles != null)
			for(int i = 0; i < subFiles.length; i++){
				
				String mapPath = Paths.get("res" + path, subFiles[i]).toAbsolutePath().toString();
				try {
					TiledMap tiledMap = TiledMap.readMap(mapPath);
					GameMap gameMap = tiledMap.createGameMap(gameData.getTextureStorage(), gameData.getTileStorage(), gameData);
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
