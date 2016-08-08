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
		RenderQueueEntry entry;
		
		for(int i = 0; i < size; i++){
			
			entry = queue.get(i);
			renderTexture(entry.getTexture(), entry.getX(), entry.getY());
		}
	}
	
	/**
	 * Clears the color buffer with the clear color.
	 */
	public void clearScreen(){
		
		Arrays.fill(colorBuffer, clearColor);
	}
	
	/**
	 * Renders a texture to the screen.
	 * @param texture The texture.
	 * @param worldX The world x coordinate of the texture.
	 * @param worldY The world y coordinate of the texture.
	 */
	void renderTexture(Texture texture, int worldX, int worldY){

		//TODO: maybe there is some more stuff to do here later
		for(int x = 0; x < texture.getWidth(); x++){
			for(int y = 0; y < texture.getHeight(); y++){
				colorBuffer[x + worldX + (y + worldY) * width] = texture.getBuffer()[x + y * texture.getWidth()];
			}
		}
	}
}
