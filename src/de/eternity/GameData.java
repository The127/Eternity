package de.eternity;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.Toml;

import de.eternity.gfx.TextureStorage;
import de.eternity.input.ButtonInput;
import de.eternity.map.GameMap;
import de.eternity.map.TileStorage;
import de.eternity.support.lua.EngineLuaEnvironment;
import de.eternity.util.LuaGameStates;
import de.eternity.util.TextureLoader;

/**
 * Holds all the global information for the game.
 * Also handles loading and precomputing game assets.
 * @author Julzenberger
 *
 */
public class GameData {

	public final int tileSize;
	
	private final ButtonInput keyboard;
	
	private TextureStorage textureStorage = new TextureStorage();
	private TileStorage tileStorage = new TileStorage();
	
	private GameSettings gameSettings;
	
	private LuaGameStates luaGameStates = new LuaGameStates();
	
	//TODO: create from a file
	public GameData(ButtonInput keyboard){
		
		gameSettings = new Toml().read(new File("res/settings.toml")).to(GameSettings.class);
		
		this.tileSize = gameSettings.getTilesize();
		this.keyboard = keyboard;
	}
	
	public void init(EngineLuaEnvironment engineLuaEnvironment) throws IOException{
		
		TextureLoader.loadTextures(gameSettings.getTilesetsPath(), textureStorage);
		
		//load game states from lua files
		luaGameStates.loadGameStates(gameSettings.getGameStatesPath(), engineLuaEnvironment);
	}
	
	public GameState getLuaGameState(String name){
		return luaGameStates.getGameState(name);
	}
	
	public ButtonInput getKeyboard(){
		return keyboard;
	}
	
	public TextureStorage getTextureStorage(){
		return textureStorage;
	}
	
	public TileStorage getTileStorage(){
		return tileStorage;
	}
	
	public GameMap getGameMap(String mapName){
		return null;
	}
	
	public GameSettings getSettings(){
		return gameSettings;
	}

	public LuaGameStates getLuaGameStates() {
		return luaGameStates;
	}
}
