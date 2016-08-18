package de.eternity;

import de.eternity.input.ButtonInput;

/**
 * Holds all the global information for the game.
 * Also handles loading and precomputing game assets.
 * @author Julzenberger
 *
 */
public class GameData {

	public final int tileSize;
	
	private final ButtonInput keyboard;
	
	//TODO: create from a file
	public GameData(int tileSize, ButtonInput keyboard){
		this.tileSize = tileSize;
		this.keyboard = keyboard;
	}
	
	public ButtonInput getKeyboard(){
		return keyboard;
	}
}
