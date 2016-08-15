package de.eternity.map;

import de.eternity.gfx.Animation;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.TextureStorage;

public class Tile {
	
	private Animation animation;
	
	public Tile(Animation animation){
		this.animation = animation;
	}
	
	public void update(double delta){
		animation.update(delta);
	}
	
	public void applyRenderContext(IRenderQueue renderQueue, TextureStorage textureStorage, double x, double y){
		renderQueue.addBackground(animation.getCurrentTexture(textureStorage), x, y);
	}
}
