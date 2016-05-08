package de.eternity.gfx;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RenderQueue implements IRenderQueue, Iterable<RenderQueueEntry> {
	
	private List<RenderQueueEntry> entries = new LinkedList<>();
	private int size = 0;
	
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
		
		if(entries.size() < size +1)
			entries.add(new RenderQueueEntry());
		
		RenderQueueEntry entry = entries.get(size);
		entry.setValues(texture, x, y, null);
		
		if(!entry.calculateArea())//if not on screen
			return null;
		
		size++;
		
		return entry;
	}
	
	public void sort(){
		
		entries.sort(new Comparator<RenderQueueEntry>() {

			@Override
			public int compare(RenderQueueEntry e1, RenderQueueEntry e2) {
				
				return 0;
			}
		});
	}

	public void reset() {

		size = 0;
	}

	@Override
	public Iterator<RenderQueueEntry> iterator() {
		
		return entries.iterator();
	}
}
