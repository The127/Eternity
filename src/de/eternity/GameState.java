package de.eternity;

import de.eternity.gfx.IRenderQueue;

public abstract class GameState {
	
	private boolean remainOnStack = true;

	private GameData gameData;

	public GameState(){
		this(true);
	}
	
	public GameState(boolean remainOnStack){
		this.remainOnStack = remainOnStack;
	}
	
	void setGameData(GameData gameData){
		this.gameData = gameData;
	}
	
	public boolean doesRemainOnStack(){
		return remainOnStack;
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
