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
	{t1.foo();t2.foo();}
	
	Texture[] textures = new Texture[100000];
	int[] xs = new int[textures.length], ys = new int[xs.length];
	{
		Random rand = new Random();
		for(int i = 0; i < xs.length; i++){
			textures[i] = new Texture(10, 10, rand.nextInt());
			xs[i] = rand.nextInt(390);
			ys[i] = rand.nextInt(290);
		}
		
	}
	
	double x = 0, speed = 100;
	
	@Override
	public void update(double delta) {

		//update x
		x += speed * delta;
		if(x >= 50)speed *= -1;
		if(x < -50)speed *= -1;
	}

	@Override
	public void applyRenderContext(IRenderQueue renderQueue) {

		//render rectangles
		renderQueue.addBackground(t1, x, x);
		renderQueue.addBackground(t1, 300+x, -x);
		renderQueue.addBackground(t1, x, 200-x);
		renderQueue.addBackground(t1, 300+x, 200+x);
		
		renderQueue.addBackground(t1, x, 100);

		renderQueue.addBackground(t1, x+300, 100);
		
		renderQueue.addBackground(t1, 150, x);
		renderQueue.addBackground(t1, 150, 200+x);
		renderQueue.addBackground(t2, 200, 0);
		
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
