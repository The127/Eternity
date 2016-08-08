package de.eternity.gfx;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue implements IRenderQueue {
	
	private Camera camera;
	
	//ArrayList has best list.get(index) time
	private List<RenderQueueEntry> entries = new ArrayList<>();
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
	
	public int size(){
		return entries.size();
	}
	
	private RenderQueueEntry addEntry(Texture texture, double x, double y){
		
		//request new RenderQueueEntry
		if(entries.size() < size +1)
			entries.add(new RenderQueueEntry());
		
		//reuse old RenderQueueEntry
		RenderQueueEntry entry = entries.get(size);
		
		if(!entry.setValues(texture, x, y, camera.getCameraArea()))//if not on screen
			return null;
		
		//increment current size
		size++;
		
		return entry;
	}
	
	public RenderQueueEntry get(int index){
		return entries.get(index);
	}
	
	public void sort(){
		
		entries.sort((e1, e2) ->{
			return e1.getDepth() - e2.getDepth();
		});
	}

	public void reset() {
		//reset current size for next iteration
		size = 0;
	}
}
