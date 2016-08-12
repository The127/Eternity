package de.eternity.gfx;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

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
	
	private void handleContextSwitch(){

		//switch context
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
		
		renderQueues[renderContext].reset();
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
		
		int textureColor, originalColor;
		int textureA, textureR, textureG, textureB;
		int originalR, originalG, originalB;
		
		int cameraXOffset = activeCamera.getCameraArea().x;
		int cameraYOffset = activeCamera.getCameraArea().y;
		
		for(int x = 0; x < endX; x++){
			for(int y = 0; y < endY; y++){
				
				bufferOffset = x + startX - cameraXOffset + (y + startY - cameraYOffset) * width;
				
				textureColor = texture[textureXOffset + x + (y + textureYOffset) * textureWidth];
				textureA = (textureColor >>> 24);

				if(textureA != 0){//if not invisible
				
					//full alpha -> copy
					if(textureA == 0xff){

						colorBuffer[bufferOffset] = textureColor;
					}else{//alpha support code
						
						//calculate new color
						originalColor = colorBuffer[bufferOffset];
						
						//texture rgb
						textureR = (textureColor >> 16) & 0xff;
						textureG = (textureColor >> 8) & 0xff;
						textureB = (textureColor) & 0xff;
						
						//current rgb
						originalR = (originalColor >> 16) & 0xff;
						originalG = (originalColor >> 8) & 0xff;
						originalB = (originalColor) & 0xff;
						
						//calculate new current rgb
						originalR = textureR + originalR * (0xff-textureA) / 0xff;
						originalG = textureG + originalG * (0xff-textureA) / 0xff;
						originalB = textureB + originalB * (0xff-textureA) / 0xff;
						
						//calculate and set the effective color
						colorBuffer[bufferOffset] = (0xff<<24) | (originalR<<16) | (originalG<<8) | originalB;
					}
				}
			}
		}
	}
}
