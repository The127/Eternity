/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity;

import de.eternity.gfx.IRenderQueue;

/**
 * This class represents a single game state.
 * Game states are significant portions of the game like the start menu or the game itself, the inventory or a mini game inside the game.
 * @author Julian Sven Baehr
 *
 */
public abstract class GameState {
	
	protected boolean doesRemainOnStack = true;

	private GameData gameData;

	/**
	 * Creates a new game state that remains on the stack if a new game state is pushed above it on the stack.
	 */
	public GameState(){
		this(true);
	}
	
	/**
	 * Creates a new game state.
	 * If doesRemainOnStack is true this game state remains on the stack if a new game state is pushed above it on the stack.
	 * Otherwise it will be removed from the stack first.
	 * @param doesRemainOnStack True if it should remain on the stack when another game state is pushed on the stack.
	 */
	public GameState(boolean doesRemainOnStack){
		this.doesRemainOnStack = doesRemainOnStack;
	}
	
	/**
	 * Sets the game data manager object.
	 * @param gameData
	 */
	void setGameData(GameData gameData){
		this.gameData = gameData;
	}
	
	/**
	 * @return True if the game state should remain on the stack if another game state is pushed above it on the stack.
	 */
	public boolean doesRemainOnStack(){
		return doesRemainOnStack;
	}
	
	/**
	 * @return The game data manager object.
	 */
	protected GameData getGameData(){
		return gameData;
	}
	
	/**
	 * Called when the game state is created.
	 */
	protected abstract void startup();
	/**
	 * Created when the game state is removed from the stack.
	 */
	protected abstract void shutdown();
	
	/**
	 * Called when another game state is pushed above this game state on the stack.
	 */
	protected abstract void pause();
	/**
	 * Called when this game state becomes the top game state on the stack.
	 * This happens when the game state above this one is removed from the stack.
	 */
	protected abstract void unpause();
	
	/**
	 * Called by the game.
	 * This method should handle all updating of all game assets needed.
	 * @param delta The time passed in seconds since the last call to this method.
	 */
	protected abstract void update(double delta);
	/**
	 * Called by the game.
	 * This method should handle all registration of rendering data.
	 * @param renderQueue The render queue.
	 */
	protected abstract void applyRenderContext(IRenderQueue renderQueue);
}
