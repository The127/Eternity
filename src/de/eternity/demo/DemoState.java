package de.eternity.demo;

import java.io.IOException;

import de.eternity.GameData;
import de.eternity.GameState;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.TextureStorage;
import de.eternity.map.GameMap;
import de.eternity.map.TileStorage;
import de.eternity.support.tiled.TiledMap;

/**
 * Draws a default background with a red and a green rectangle. One rectangle is moving sideways.
 * @author Julian Sven Baehr
 *
 */
public class DemoState extends GameState {

	TextureStorage texS = new TextureStorage();
	TileStorage tilS = new TileStorage();
	
	GameMap map;
	
	public DemoState(GameData gameData) {
		super(gameData);
		
		try {
//			texS.loadTileset("/walls.png", "walls", 32, 32);
			texS.loadTileset("/icetiles.png", "icetiles", 32, 32);
			
			TiledMap tm = TiledMap.readMap("res/test.json");
			map = tm.createGameMap(texS, tilS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(double delta) {

		//update x
		tilS.update(delta);
	}

	@Override
	public void applyRenderContext(IRenderQueue renderQueue) {

		map.renderMap(renderQueue, texS, tilS);
	}

	@Override
	public void startup() {
		
	}

	@Override
	public void shutdown() {
		
	}
}
