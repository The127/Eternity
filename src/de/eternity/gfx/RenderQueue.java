/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

import java.util.Arrays;

/**
 * A render queue is a queue of reusable entries that hold information about entities and background that is to be drawn.
 * @author Julian Sven Baehr
 *
 */
public class RenderQueue implements IRenderQueue {
	
	private Camera camera;
	
	//use array
	private RenderQueueEntry[] entries = {new RenderQueueEntry()};//new array with 1 entry
	private int size = 0;
	
	/**
	 * Creates a new render queue.
	 * @param camera The game camera.
	 */
	public RenderQueue(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	public void addEntity(Texture texture, double x, double y) {
		addEntry(texture, x, y);
	}
	
	@Override
	public void addBackground(Texture texture, double x, double y) {
		
		RenderQueueEntry entry = addEntry(texture, x, y);
		if(entry != null)entry.enableBackgroundMode();
	}
	
	@Override
	public void addText(Text text, double x, double y) {
		
		RenderQueueEntry entry = addEntry(text, x, y);
		if(entry != null)entry.enableTextMode();
	}
	
	/**
	 * @return The game camera.
	 */
	public Camera getCamera(){
		return camera;
	}
	
	/**
	 * @return The current amount of render queue entries.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Adds a render queue entry to the queue.
	 * @param texture The texture that is to be drawn.
	 * @param x The world x coordinate.
	 * @param y The world y coordinate.
	 * @return The render queue entry or null if it is not within the camera area.
	 */
	private RenderQueueEntry addEntry(Texture texture, double x, double y){

		//request new RenderQueueEntry
		if(entries.length == size)
			enlargeArray();
		
		//reuse old RenderQueueEntry
		RenderQueueEntry entry = entries[size];
		
		if(!entry.setValues(texture, x, y, camera.getCameraArea()))//if not on screen
			return null;
		
		//increment current size
		size++;
		
		return entry;
	}
	
	/**
	 * Handles array enlargment.
	 */
	private void enlargeArray(){
		RenderQueueEntry[] temp = entries;
		entries = new RenderQueueEntry[temp.length*2];
		for(int i = 0; i < temp.length; i++)
			entries[i] = temp[i];
		for(int i = temp.length; i < entries.length; i++)
			entries[i] = new RenderQueueEntry();
	}
	
	/**
	 * @param index The index of the render queue entry.
	 * @return The render queue entry at the given index.
	 */
	public RenderQueueEntry get(int index){
		return entries[index];
	}
	
	/**
	 * Sorts the render queue entries by their depth.
	 */
	public void sort(){
		Arrays.sort(entries, 0, size, (e1, e2) ->{
			return e1.getDepth() - e2.getDepth();
		});
	}
	
	/**
	 * Resets the render queue.
	 */
	void reset() {
		//reset current size for next iteration
		size = 0;
	}
}
