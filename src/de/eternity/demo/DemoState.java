package de.eternity.demo;

import de.eternity.GameState;
import de.eternity.gfx.IRenderQueue;
import de.eternity.map.GameMap;

/**
 * Draws a default background with a red and a green rectangle. One rectangle is moving sideways.
 * @author Julian Sven Baehr
 *
 */
public class DemoState extends GameState {
	
	GameMap map;

	@Override
	public void startup() {
		map = getGameData().getGameMap("test");
	}

	@Override
	public void shutdown() {
		
	}

	@Override
	protected void pause() {
		
	}

	@Override
	protected void unpause() {
		
	}
	
	@Override
	public void update(double delta) {

		//update tiles
		getGameData().getTileStorage().update(delta);
	}

	@Override
	public void applyRenderContext(IRenderQueue renderQueue) {

		map.renderMap(renderQueue, getGameData());
	}
}
