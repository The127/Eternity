package de.eternity.demo;

import java.util.Random;

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
	
	Texture[] textures = new Texture[2000];
	int[] xs = new int[textures.length], ys = new int[xs.length];
	{
		Random rand = new Random();
		for(int i = 0; i < xs.length; i++){
			textures[i] = new Texture(10, 10, rand.nextInt());
			xs[i] = rand.nextInt(380);
			ys[i] = rand.nextInt(280);
		}
		
	}
	
	double x = 0, speed = 100;
	
	@Override
	public void update(double delta) {

		//update x
		x += speed * delta;
		if(x >= 319)x = 0;
	}

	@Override
	public void applyRenderContext(IRenderQueue renderQueue) {

		//render rectangles
		renderQueue.addEntity(t1, x, 100);
		renderQueue.addEntity(t2, 200, 0);
		
		for(int i = 0; i < xs.length; i++){
			renderQueue.addEntity(textures[i], xs[i], ys[i]);
		}
		
	}

	@Override
	public void startup() {
		
	}

	@Override
	public void shutdown() {
		
	}
}
