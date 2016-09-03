/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

import de.eternity.GameData;
import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.TextureStorage;
import de.eternity.support.tiled.TiledObject;

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
	
	private TileStorage tileStorage;
	private TextureStorage textureStorage;
	private TiledObject[] mapObjects;
	
	/**
	 * Creates a new game map object.
	 * @param name The name of the map.
	 * @param data The tile data of the map.
	 * @param width The width of the map.
	 * @param gameData The game data manager object.
	 */
	public GameMap(String name, int[] data, int width, TiledObject[] mapObjects, GameData gameData){
		
		this.name = name;
		this.data = data;
		this.width = width;
		this.mapObjects = mapObjects;
		
		this.tilesize = gameData.tileSize;
		this.tileStorage = gameData.getTileStorage();
		this.textureStorage = gameData.getTextureStorage();
		
		paddingTilesX = gameData.getSettings().getDisplayMode().getResolutionX() % tilesize > 0 ? 2 : 1; //either 2 or 1 padding
		paddingTilesY = gameData.getSettings().getDisplayMode().getResolutionY() % tilesize > 0 ? 2 : 1; //either 2 or 1 padding
	}
	
	/**
	 * @param index The index in the mapObjects array.
	 * @return The static map object at the given index.
	 */
	public TiledObject getMapObjects(int index){
		if(index >= 0 && index < mapObjects.length)
			return mapObjects[index];
		throw new ArrayIndexOutOfBoundsException("Cannot access index " + index + " in mapObjects array.");
	}
	
	/**
	 * @return The amount of map objects.
	 */
	public int getMapObjectCount(){
		return mapObjects.length;
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
	public void renderMap(IRenderQueue renderQueue){
		
		Camera camera = renderQueue.getCamera();
		
		//only draw needed tiles
		int startX = ((int)camera.getX()) / tilesize;
		int startY = ((int)camera.getY()) / tilesize;
		int endX = startX + camera.getResolutionX()/tilesize + paddingTilesX;
		int endY = startY + camera.getResolutionY()/tilesize + paddingTilesY;
		
		for(int x = startX; x < endX; x++)
			for(int y = startY; y < endY; y++)
				tileStorage.get(data[x + y * width])
						.applyRenderContext(renderQueue, textureStorage, x*tilesize, y*tilesize);
	}
	
	public boolean collidesWithMap(int x, int y, int w, int h){
		int xStart = x / tilesize;
		int xEnd = (x + w) / tilesize;
		int yStart = y / tilesize;
		int yEnd = (y + h) / tilesize;
		
		for(int ix = xStart; ix <= xEnd; ix++)
			for(int iy = yStart; iy <= yEnd; iy++)
				if(tileStorage.get(data[ix + iy * width]).collides(ix * tilesize, iy * tilesize, x, y, w, h))
					return true;
		
		return false;
	}
}
