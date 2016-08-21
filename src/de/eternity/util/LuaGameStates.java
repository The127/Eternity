package de.eternity.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;

import de.eternity.GameState;
import de.eternity.support.lua.EngineLuaEnvironment;
import de.eternity.support.lua.LuaGameState;

public class LuaGameStates {

	private LinkedList<LuaGameState> gameStates = new LinkedList<>();
	
	public void loadGameStates(String path, EngineLuaEnvironment engineLuaEnvironment){
		
		File root = new File(Paths.get("res", path).toAbsolutePath().toString());
		String[] subFiles = root.list();
		
		//there are always two files that belong together (.toml and .png)
		for(int i = 0; i < subFiles.length; i++){
			
			System.out.println(subFiles[i]);
			gameStates.add(engineLuaEnvironment.loadGameState(Paths.get("res"+path, subFiles[i]).toAbsolutePath()));
		}
	}
	
	public GameState getGameState(String name){
		
		for(int i = 0; i < gameStates.size(); i++)
			if(gameStates.get(i).getName().equals(name))
				return gameStates.get(i);
		
		throw new IllegalArgumentException("No lua game state '" + name + "' found!");
	}
}
