package de.eternity;

import de.eternity.gfx.TextureStorage;
import de.eternity.input.ButtonInput;
import de.eternity.map.TileStorage;

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
	
	//TODO: create from a file
	public GameData(int tileSize, ButtonInput keyboard){
		this.tileSize = tileSize;
		this.keyboard = keyboard;
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
}
