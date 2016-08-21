package de.eternity.map;

import de.eternity.GameData;
import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;

public class GameMap {
	
	private String name;

	private final int width;
	private final int[] data;
	
	public GameMap(String name, int[] data, int width){
		this.name = name;
		this.data = data;
		this.width = width;
	}
	
	public String getName(){
		return name;
	}
	
	public void renderMap(IRenderQueue renderQueue, GameData gameData){
		
		Camera camera = renderQueue.getCamera();
		
		//only draw needed tiles
		int startX = ((int)camera.getX()) / gameData.tileSize;
		int startY = ((int)camera.getY()) / gameData.tileSize;
		int endX = startX + camera.getResolutionX()/gameData.tileSize + 2;
		int endY = startY + camera.getResolutionY()/gameData.tileSize + 2;
		
		for(int x = startX; x < endX; x++)
			for(int y = startY; y < endY; y++)
				gameData.getTileStorage().get(data[x + y * width])
						.applyRenderContext(renderQueue, gameData.getTextureStorage(), x*gameData.tileSize, y*gameData.tileSize);
	}
}
