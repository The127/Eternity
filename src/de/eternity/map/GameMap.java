/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

import de.eternity.GameData;
import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;

/**
 * A game map holds information about a map of the game.
 * @author Julian Sven Baehr
 *
 */
public class GameMap {
	
	private final String name;

	private final int width;
	private final int[] data;
	
	private final int tilesize;
	private final int paddingTilesX;
	private final int paddingTilesY;
	
	/**
	 * Creates a new game map object.
	 * @param name The name of the map.
	 * @param data The tile data of the map.
	 * @param width The width of the map.
	 * @param gameData The game data manager object.
	 */
	public GameMap(String name, int[] data, int width, GameData gameData){
		this.name = name;
		this.data = data;
		this.width = width;
		this.tilesize = gameData.tileSize;
		paddingTilesX = gameData.getSettings().getDisplayMode().getResolutionX() % tilesize > 0 ? 2 : 1; //either 2 or 1 padding
		paddingTilesY = gameData.getSettings().getDisplayMode().getResolutionY() % tilesize > 0 ? 2 : 1; //either 2 or 1 padding
	}
	
	/**
	 * @return The map name.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Renders the game map.
	 * @param renderQueue The render queue.
	 * @param gameData The game data manager object.
	 */
	public void renderMap(IRenderQueue renderQueue, GameData gameData){
		
		Camera camera = renderQueue.getCamera();
		
		//only draw needed tiles
		int startX = ((int)camera.getX()) / gameData.tileSize;
		int startY = ((int)camera.getY()) / gameData.tileSize;
		int endX = startX + camera.getResolutionX()/gameData.tileSize + paddingTilesX;
		int endY = startY + camera.getResolutionY()/gameData.tileSize + paddingTilesY;
		
		for(int x = startX; x < endX; x++)
			for(int y = startY; y < endY; y++)
				gameData.getTileStorage().get(data[x + y * width])
						.applyRenderContext(renderQueue, gameData.getTextureStorage(), x*gameData.tileSize, y*gameData.tileSize);
	}
}
