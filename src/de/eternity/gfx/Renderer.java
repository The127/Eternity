/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class handles the rendering of textures to the screen buffer.
 * @author Julian Sven Baehr
 *
 */
public class Renderer {
	
	public static final int BACKGROUND_DEPTH = -1337;
	public static final int CLEAR_COLOR = 0x00000000;
	
	private Object waitLock = new Object(){};
	AtomicBoolean waitState = new AtomicBoolean(false);
	//use 2 render queues to flip between rendering and updating
	private int renderContext = 0;
	private RenderQueue[] renderQueues;
	
	private Camera camera, activeCamera;
	
	private int[] colorBuffer;
	private int width;
	
	/**
	 * Creates a new renderer that draws on the given texture within the area of the given camera.
	 * @param texture The render texture.
	 * @param camera The render camera.
	 */
	public Renderer(Texture texture, Camera camera){
		
		this.width = texture.getWidth();
		this.camera = camera;
		activeCamera = new Camera(camera.getResolutionX(), camera.getResolutionY());
		
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
				
				handleContextSwitch();
				
				//wake the other thread
				waitLock.notifyAll();
			}else
				try {
					//wait for the other thread
					waitLock.wait();
				} catch (InterruptedException e) {
					//TODO: Logging
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Handles the actual context switch.
	 */
	private void handleContextSwitch(){

		//switch context
		renderQueues[renderContext].reset();
		renderContext = (renderContext + 1) % 2;
		
		//switch camera context
		activeCamera.set(camera.getX(), camera.getY());
	}
	
	/**
	 * Switches the render context.
	 * There are 2 render contexts.
	 * One for rendering(active/shown) and one for updating(passive/hidden).
	 */
	public void switchRenderContext(){

		awaitContextSwitch(); 
	}
	
	/**
	 * Lets the update thread wait on the next context.
	 * @return The next context.
	 * @throws InterruptedException
	 */
	public RenderQueue getUpdateContext() {

		awaitContextSwitch();
		return renderQueues[(renderContext + 1) % 2];
	}
	
	/**
	 * Renders the render queue.
	 */
	public void renderQueue(){
		
		RenderQueue queue = renderQueues[renderContext];
		int size = queue.size();
		
		for(int i = 0; i < size; i++)
			renderEntry(queue.get(i));
	}
	
	/**
	 * Clears the color buffer with the clear color.
	 */
	public void clearScreen(){
		
		Arrays.fill(colorBuffer, CLEAR_COLOR);
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
		
		int bufferOffset;
		
		int textureColor, currentColor;
		int textureA, textureR, textureG, textureB;
		int currentR, currentG, currentB;
		
		int cameraXOffset = activeCamera.getCameraArea().x;
		int cameraYOffset = activeCamera.getCameraArea().y;
		
		for(int x = 0; x < endX; x++){
			for(int y = 0; y < endY; y++){
				
				textureColor = texture[textureXOffset + x + (y + textureYOffset) * textureWidth];
				textureA = (textureColor >>> 24);

				if(textureA != 0){//if visible
					
					//calculate index in buffer
					bufferOffset = x + startX - cameraXOffset + (y + startY - cameraYOffset) * width;
					
					if(textureA == 0xff){//full alpha -> overwrite

						colorBuffer[bufferOffset] = textureColor;
					}else{//alpha support code
						
						//calculate new color
						currentColor = colorBuffer[bufferOffset];
						
						//texture rgb
						textureR = (textureColor >> 16) & 0xff;
						textureG = (textureColor >> 8) & 0xff;
						textureB = (textureColor) & 0xff;
						
						//current rgb
						currentR = (currentColor >> 16) & 0xff;
						currentG = (currentColor >> 8) & 0xff;
						currentB = (currentColor) & 0xff;
						
						//calculate new current rgb
						currentR = textureR + currentR * (0xff-textureA) / 0xff;
						currentG = textureG + currentG * (0xff-textureA) / 0xff;
						currentB = textureB + currentB * (0xff-textureA) / 0xff;
						
						//calculate and set the effective color
						colorBuffer[bufferOffset] = (currentR<<16) | (currentG<<8) | currentB;//no alpha channel needed
					}
				}
			}
		}
	}
}
