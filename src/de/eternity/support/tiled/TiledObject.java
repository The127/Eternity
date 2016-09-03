/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.tiled;

import de.eternity.GameData;
import de.eternity.gfx.RenderQueue;

public class TiledObject {

	int gid, x, y, width, height;

	private transient GameData gameData;
	
	void initialize(GameData gameData, String tileset, int firstgid){
		this.gameData = gameData;
		gid = gameData.getTextureStorage().translateToGlobalTextureId(tileset, gid - firstgid);
		System.out.println(gid);
	}
	
	public void render(RenderQueue renderQueue){
		renderQueue.addEntity(gameData.getTextureStorage().getTexture(gid), x, y);
	}
	
	public int getTileId(){
		return gid;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
