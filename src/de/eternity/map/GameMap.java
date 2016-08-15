package de.eternity.map;

import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.TextureStorage;

public class GameMap {

	private final int width, tilewidth;
	private final int[] data;
	
	public GameMap(int[] data, int width, int tilewidth){
		this.data = data;
		this.width = width;
		this.tilewidth = tilewidth;
	}
	
	public void renderMap(IRenderQueue renderQueue, TextureStorage textureStorage, TileStorage tileStorage){
		
		Camera camera = renderQueue.getCamera();
		
		//only draw needed tiles
		int startX = ((int)camera.getX()) / tilewidth;
		int startY = ((int)camera.getY()) / tilewidth;
		int endX = startX + camera.getResolutionX()/tilewidth + 1;
		int endY = startY + camera.getResolutionY()/tilewidth + 1;
		
		for(int x = startX; x < endX; x++){
			for(int y = startY; y < endY; y++){
				
				tileStorage.get(data[x + y * width]).applyRenderContext(renderQueue, textureStorage, camera.getX() + x*tilewidth, camera.getY() + y*tilewidth);
			}
		}
	}
}
