/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.tiled;

import de.eternity.GameData;
import de.eternity.gfx.RenderQueue;

public class TiledObject {

	int gid, width, height;
	float x, y;
	String name;

	private transient GameData gameData;
	
	void initialize(GameData gameData, String tileset, int firstgid){
		this.gameData = gameData;
		gid = gameData.getTextureStorage().translateToGlobalTextureId(tileset, gid - firstgid);
	}
	
	public void render(RenderQueue renderQueue){
		renderQueue.addEntity(gameData.getTextureStorage().getTexture(gid), x, y);
	}
	
	public String getName(){
		return name;
	}
	
	public int getTileId(){
		return gid;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
