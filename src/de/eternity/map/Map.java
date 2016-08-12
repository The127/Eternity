package de.eternity.map;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.eternity.gfx.Camera;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.Texture;

public class Map{
	
	private String path;
	
	private int width, height, tilewidth;
	
	private Layer[] layers;
	private Tileset[] tilesets;
	
	public static Map readMap(String path) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		
		Map map = new Gson().fromJson(new FileReader(path), Map.class);
		map.path = path;
		
		return map;
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
				
				renderQueue.addBackground(t, camera.getX() + x*tilewidth, camera.getY() + y*tilewidth);
			}
		}
	}
}
