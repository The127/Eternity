/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.Toml;

import de.eternity.gfx.TextureStorage;
import de.eternity.input.ButtonInput;
import de.eternity.map.GameMap;
import de.eternity.map.TileStorage;
import de.eternity.support.lua.EngineLuaEnvironment;
import de.eternity.util.GameMaps;
import de.eternity.util.LuaGameStates;
import de.eternity.util.TextureLoader;

/**
 * Holds all the global information for the game.
 * Also handles loading and pre-computing game assets.
 * @author Julian Sven Baehr
 */
public class GameData {

	public final int tileSize;
	public final int oneMeter;
	
	private final ButtonInput keyboard, mouse;
	
	private TextureStorage textureStorage = new TextureStorage();
	private TileStorage tileStorage = new TileStorage();
	
	private GameSettings gameSettings;
	
	private LuaGameStates luaGameStates = new LuaGameStates();
	private GameMaps gameMaps = new GameMaps();
	
	/**
	 * Loads the settings and sets some 'global' variables.
	 * @param keyboard The ButtonInput object for the keyboard.
	 * @throws IOException If the character set cannot be found.
	 */
	public GameData(ButtonInput keyboard, ButtonInput mouse) throws IOException{
		
		//load character set first
		textureStorage.loadTileset("/abc.png", "abc", 8, 10);
		
		gameSettings = new Toml().read(new File("res/settings.toml")).to(GameSettings.class);
		
		this.tileSize = gameSettings.getTilesize();
		this.oneMeter = gameSettings.getOneMeter();
		
		this.keyboard = keyboard;
		this.mouse = mouse;
	}
	
	/**
	 * Initializes the game data.
	 * Loads all textures from their tilesets.
	 * Loads all game maps.
	 * Loads all lua game states.
	 * @param engineLuaEnvironment The lua scripting environment object for the game.
	 * @throws IOException If a tileset cannot be found.
	 */
	public void init(EngineLuaEnvironment engineLuaEnvironment) throws IOException{
		
		TextureLoader.loadTextures(gameSettings.getTilesetsPath(), textureStorage);
		
		gameMaps.loadGameMaps(gameSettings.getMapsPath(), this);
		
		//load game states from lua files
		luaGameStates.loadGameStates(gameSettings.getGameStatesPath(), engineLuaEnvironment);
	}
	
	/**
	 * @return The game maps manager object.
	 */
	public GameMaps getGameMaps(){
		return gameMaps;
	}
	
	/**
	 * @param name The name of the lua game state.
	 * @return The corresponding lua game state.
	 */
	public GameState getLuaGameState(String name){
		return luaGameStates.getGameState(name);
	}
	
	/**
	 * @return The ButtonInput instance that is connected to the keyboard.
	 */
	public ButtonInput getKeyboard(){
		return keyboard;
	}
	
	/**
	 * @return The ButtonInput instance that is connected to the mouse.
	 */
	public ButtonInput getMouse(){
		return mouse;
	}
	
	/**
	 * @return The global texture storage.
	 */
	public TextureStorage getTextureStorage(){
		return textureStorage;
	}
	
	/**
	 * @return The global tile storage.
	 */
	public TileStorage getTileStorage(){
		return tileStorage;
	}
	
	/**
	 * @param mapName The name of the map.
	 * @return The corresponding map.
	 */
	public GameMap getGameMap(String mapName){
		return gameMaps.getGameMap(mapName);
	}
	
	/**
	 * @return The game settings instance.
	 */
	public GameSettings getSettings(){
		return gameSettings;
	}

	/**
	 * @return The lua game state manager.
	 */
	public LuaGameStates getLuaGameStates() {
		return luaGameStates;
	}
}
