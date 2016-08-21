package de.eternity.gfx;

import java.util.Arrays;

public class RenderQueue implements IRenderQueue {
	
	private Camera camera;
	
	//use array
	private RenderQueueEntry[] entries = {new RenderQueueEntry()};//new array with 1 entry
	private int size = 0;
	
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
	
	public Camera getCamera(){
		return camera;
	}
	
	public int size(){
		return size;
	}
	
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
	
	private void enlargeArray(){
		RenderQueueEntry[] temp = entries;
		entries = new RenderQueueEntry[temp.length*2];
		for(int i = 0; i < temp.length; i++)
			entries[i] = temp[i];
		for(int i = temp.length; i < entries.length; i++)
			entries[i] = new RenderQueueEntry();
	}
	
	public RenderQueueEntry get(int index){
		return entries[index];
	}
	
	public void sort(){
		Arrays.sort(entries, 0, size, (e1, e2) ->{
			return e1.getDepth() - e2.getDepth();
		});
	}
	
	void reset() {
		//reset current size for next iteration
		size = 0;
	}
}
