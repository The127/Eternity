package de.eternity.gfx;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class RenderQueueEntry {
	
	private Rectangle toDraw = new Rectangle();
	private Texture texture;
	private int x, y;
	private int depth;
	
	public void enableBackgroundMode(){
		depth = Renderer.BACKGROUND_DEPTH;
	}
	
	/**
	 * Sets the values and calculates the area of the texture that needs to be drawn.
	 * @return True if the area is greater than zero.
	 */
	public boolean setValues(Texture texture, double x, double y, Rectangle cameraArea){
		this.texture = texture;
		this.x = (int)(x + 0.5d);
		this.y = (int)(y + 0.5d);
		this.depth = (int) (y + texture.getHeight());
		
		toDraw.x = this.x;
		toDraw.y = this.y;
		toDraw.width = texture.getWidth();
		toDraw.height = texture.getHeight();
		
		Rectangle2D.intersect(toDraw, cameraArea, toDraw);
			
		return toDraw.getWidth() > 0 && toDraw.getHeight() > 0;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public int getDrawX(){
		return toDraw.x;
	}
	
	public int getDrawY(){
		return toDraw.y;
	}
	
	public int getDrawWidth(){
		return toDraw.width;
	}
	
	public int getDrawHeight(){
		return toDraw.height;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getDepth(){
		return depth;
	}
}
