package de.eternity.gfx;

public interface IRenderQueue {

	public void addEntity(Texture texture, double x, double y);
	public void addBackground(Texture texture, double x, double y);
}
