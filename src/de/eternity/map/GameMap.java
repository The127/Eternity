package de.eternity.map;

import de.eternity.GameData;
import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;

public class GameMap {
	
	private final String name;

	private final int width;
	private final int[] data;
	
	private final int tilesize;
	private final int paddingTilesX;
	private final int paddingTilesY;
	
	public GameMap(String name, int[] data, int width, GameData gameData){
		this.name = name;
		this.data = data;
		this.width = width;
		this.tilesize = gameData.tileSize;
		paddingTilesX = gameData.getSettings().getDisplayMode().getResolutionX() % tilesize > 0 ? 2 : 1; //either 2 or 1 padding
		paddingTilesY = gameData.getSettings().getDisplayMode().getResolutionY() % tilesize > 0 ? 2 : 1; //either 2 or 1 padding
	}
	
	public String getName(){
		return name;
	}
	
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
