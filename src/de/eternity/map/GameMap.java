package de.eternity.map;

import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.Texture;

public class GameMap {

	private final int width, height, tilewidth;
	private final int[] data;
	
	public GameMap(int[] data, int width, int height, int tilewidth){
		this.data = data;
		this.width = width;
		this.height = height;
		this.tilewidth = tilewidth;
	}
	
	public void renderMap(IRenderQueue renderQueue){
		
		Texture t = new Texture(32, 32);
		t.foo();
		
		Camera camera = renderQueue.getCamera();
		
		//only draw needed tiles
		int startX = ((int)camera.getX()) / tilewidth;
		int startY = ((int)camera.getY()) / tilewidth;
		int endX = startX + camera.getResolutionX()/tilewidth + 1;
		int endY = startY + camera.getResolutionY()/tilewidth + 1;
		
		for(int x = startX; x < endX; x++){
			for(int y = startY; y < endY; y++){
				
				//TODO: use actual background textures
				renderQueue.addBackground(t, camera.getX() + x*tilewidth, camera.getY() + y*tilewidth);
			}
		}
	}
}
