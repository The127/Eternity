package de.eternity.gfx;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Renderer {
	
	public static final int BACKGROUND_DEPTH = -1;
	
	private Object waitLock = new Object(){};
	AtomicBoolean waitState = new AtomicBoolean(false);
	
	//use 2 render queues to flip between rendering and updating
	private int renderContext = 0;
	private RenderQueue[] renderQueues;
	
	private int clearColor = 0xFFFF00FF;
	private int[] colorBuffer;
	
	private int width;
	
	public Renderer(Texture texture, Camera camera){
		
		this.width = texture.getWidth();
		
		colorBuffer = texture.getBuffer();
		
		renderQueues = new RenderQueue[]{
			new RenderQueue(camera),
			new RenderQueue(camera)
		};
		
		clearScreen();
	}
	
	/**
	 * Handles concurrency issues between the update and render thread.
	 */
	private void awaitContextSwitch(){
		
		synchronized (waitLock) {
			
			//determine if the other thread is waiting
			if(!waitState.compareAndSet(false, true)){
				waitState.set(false);
				
				//switch context
				renderContext = (renderContext + 1) % 2;
				//wake the other thread
				waitLock.notifyAll();
			}else{
				
				try {
					//wait for the other thread
					waitLock.wait();
				} catch (InterruptedException e) {
					//TODO: Logging
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Switches the render context.
	 * There are 2 render contexts.
	 * One for rendering(active/shown) and one for updating(passive/hidden).
	 */
	public void switchContext(){

		awaitContextSwitch(); 
	}
	
	/**
	 * Lets the update thread wait on the next context.
	 * @return The next context.
	 * @throws InterruptedException
	 */
	public RenderQueue getUpdateContext() throws InterruptedException{

		awaitContextSwitch();
		return renderQueues[(renderContext + 1) % 2];
	}
	
	/**
	 * Renders the render queue.
	 */
	public void renderQueue(){
		
		RenderQueue queue = renderQueues[renderContext];
		int size = queue.size();
		
		for(int i = 0; i < size; i++){
			
			renderEntry(queue.get(i));
		}
		renderQueues[renderContext].reset();
	}
	
	/**
	 * Clears the color buffer with the clear color.
	 */
	public void clearScreen(){
		
		Arrays.fill(colorBuffer, clearColor);
	}
	
	/**
	 * Renders an entry to the screen.
	 * @param entry The entry from the render queue.
	 */
	void renderEntry(RenderQueueEntry entry){
		
		int[] texture = entry.getTexture().getBuffer();
		
		int startX = entry.getDrawX();
		int startY = entry.getDrawY();
		
		int textureXOffset = Math.abs(entry.getX() - startX);
		int textureYOffset = Math.abs(entry.getY() - startY);
		
		int endX = entry.getDrawWidth();
		int endY = entry.getDrawHeight();
		
		int textureWidth = entry.getTexture().getWidth();
		
		for(int x = 0; x < endX; x++){
			for(int y = 0; y < endY; y++){
				colorBuffer[x + startX + (y + startY) * width] = texture[textureXOffset + x + (y + textureYOffset) * textureWidth];
			}
		}
	}
}
