package de.eternity;

import de.eternity.gfx.IRenderQueue;

public abstract class GameState {

	protected final GameData gameData;
	
	public GameState(GameData gameData){
		this.gameData = gameData;
	}
	
	protected abstract void startup();
	protected abstract void shutdown();
	
	protected abstract void update(double delta);
	protected abstract void applyRenderContext(IRenderQueue renderQueue);
}
