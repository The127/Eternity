package de.eternity.gfx;

public interface IRenderQueue {

	Camera getCamera();
	void addEntity(Texture texture, double x, double y);
	void addBackground(Texture texture, double x, double y);
}
