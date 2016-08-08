package de.eternity.gfx;

import java.util.Arrays;

public class Renderer {
	
	public static final int BACKGROUND_DEPTH = -1;
	
	//use 2 render queues to flip between rendering and updating
	private int renderContext = 0, updateContext = 1;
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
	 * Switches the render context.
	 * There are 2 render contexts.
	 * One for rendering(active/shown) and one for updating(passive/hidden).
	 */
	public void switchContext(){

		synchronized (renderQueues[renderContext]) {
			
			renderQueues[renderContext].reset();
			renderQueues[renderContext].notifyAll();
		}
		renderContext = (renderContext + 1) % 2; 
	}
	
	/**
	 * Lets the update thread wait on the next context.
	 * @return The next context.
	 * @throws InterruptedException
	 */
	public RenderQueue getUpdateContext() throws InterruptedException{

		updateContext = (updateContext + 1) % 2; 
		synchronized (renderQueues[updateContext]) {
			renderQueues[updateContext].wait();
		}
		return renderQueues[updateContext];
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
