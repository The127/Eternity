package de.eternity.gfx;

import java.awt.Rectangle;

public class Camera {
	
	private Rectangle areaOfView;
	private double x = 0, y = 0;
	
	/**
	 * Creates a new camera instance.
	 * @param resolutionX The x resolution/width.
	 * @param resolutionY The y resolution/height.
	 */
	public Camera(int resolutionX, int resolutionY){
		
		areaOfView = new Rectangle(0, 0, resolutionX, resolutionY);
	}

	/**
	 * @return The current camera area.
	 */
	public Rectangle getCameraArea() {
		
		return areaOfView;
	}
	
	/**
	 * Sets the current camera position.
	 * @param x
	 * @param y
	 */
	public void set(double x, double y){
		
		this.x = x;
		this.y = y;
		
		//+0.5d -> rounding
		areaOfView.x = (int)(x + 0.5d);
		areaOfView.y = (int)(y + 0.5d);
	}
	
	/**
	 * Moves the camera by the specified amount in the x and y axis.
	 * @param deltaX The movement in the x axis.
	 * @param deltaY The movement in the y axis.
	 */
	public void move(double deltaX, double deltaY){
		
		x += deltaX;
		y += deltaY;
		
		areaOfView.x = (int)(x + 0.5d);
		areaOfView.y = (int)(y + 0.5d);
	}
	
	/**
	 * @return The current x coordinate.
	 */
	public double getX(){
		
		return x;
	}
	
	/**
	 * @return The current y coordinate.
	 */
	public double getY(){
		
		return y;
	}
	
	/**
	 * @return The x resolution/width.
	 */
	public int getResolutionX(){
		
		return areaOfView.width;
	}
	
	/**
	 * @return The y resolution/height.
	 */
	public int getResolutionY(){
		
		return areaOfView.height;
	}
}
