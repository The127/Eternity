package de.eternity.demo;

import java.io.IOException;

import de.eternity.GameState;
import de.eternity.gfx.IRenderQueue;
import de.eternity.map.GameMap;
import de.eternity.support.tiled.TiledMap;

/**
 * Draws a default background with a red and a green rectangle. One rectangle is moving sideways.
 * @author Julian Sven Baehr
 *
 */
public class DemoState extends GameState {
	
	GameMap map;
	
	@Override
	public void update(double delta) {

		//update tiles
		getGameData().getTileStorage().update(delta);
	}

	@Override
	public void applyRenderContext(IRenderQueue renderQueue) {

		map.renderMap(renderQueue, getGameData().getTextureStorage(), getGameData().getTileStorage());
	}

	@Override
	public void startup() {
		try {
			getGameData().getTextureStorage().loadTileset("/walls.png", "walls", 32, 32);
			getGameData().getTextureStorage().loadTileset("/icetiles.png", "icetiles", 32, 32);
			
			TiledMap tm = TiledMap.readMap("res/test.json");
			map = tm.createGameMap(getGameData().getTextureStorage(), getGameData().getTileStorage());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
}
