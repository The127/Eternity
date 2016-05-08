package de.eternity.demo;

import de.eternity.IGameState;
import de.eternity.gfx.IRenderQueue;
import de.eternity.gfx.Texture;

/**
 * Draws a default background with a red and a green rectangle. One rectangle is moving sideways.
 * @author Julian Sven Baehr
 *
 */
public class DemoState implements IGameState {
	
	//no alpha channel is used in rendering
	Texture t1 = new Texture(100, 100, 0xff<<16);//full red
	Texture t2 = new Texture(100, 100, 0xff<<8);//full green
	
	double x = 0, speed = 100;
	
	@Override
	public void update(double delta) {

		//update x
		x += speed * delta;
		if(x >= 299)x = 0;
	}

	@Override
	public void applyRenderContext(IRenderQueue renderQueue) {
		
		//render rectangles
		renderQueue.addEntity(t1, x, 100);
		renderQueue.addEntity(t2, 200, 0);
	}

	@Override
	public void startup() {
		
	}

	@Override
	public void shutdown() {
		
	}
}
