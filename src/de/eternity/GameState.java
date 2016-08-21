package de.eternity;

import de.eternity.gfx.IRenderQueue;

public abstract class GameState {
	
	protected boolean doesRemainOnStack = true;

	private GameData gameData;

	public GameState(){
		this(true);
	}
	
	public GameState(boolean doesRemainOnStack){
		this.doesRemainOnStack = doesRemainOnStack;
	}
	
	void setGameData(GameData gameData){
		this.gameData = gameData;
	}
	
	public boolean doesRemainOnStack(){
		return doesRemainOnStack;
	}
	
	protected GameData getGameData(){
		return gameData;
	}
	
	protected abstract void startup();
	protected abstract void shutdown();
	
	protected abstract void pause();
	protected abstract void unpause();
	
	protected abstract void update(double delta);
	protected abstract void applyRenderContext(IRenderQueue renderQueue);
}
