package de.eternity;

import de.eternity.gfx.IRenderQueue;

public interface IGameState {

	void startup();
	void shutdown();
	
	void update(double delta);
	void applyRenderContext(IRenderQueue renderQueue);
}
