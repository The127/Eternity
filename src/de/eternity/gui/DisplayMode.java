/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gui;

/**
 * A data holder class for window/display information.
 * @author Julian Sven Baehr
 *
 */
public class DisplayMode {
	
	private int displayWidth, displayHeight, resolutionX, resolutionY, canvasBufferSize;

	/**
	 * Creates a new DisplayMode instance. Make width and height 2 to the n times the value of the corresponding resolution value for best results.
	 * @param displayWidth The width of the window.
	 * @param displayHeight The height of the window.
	 * @param resolutionX The in-game x/width resolution.
	 * @param resolutionY The in-game y/height resolution.
	 */
	public DisplayMode(int displayWidth, int displayHeight, int resolutionX, int resolutionY) {

		this(displayWidth, displayHeight, resolutionX, resolutionY, 2);
	}
	
	/**
	 * Creates a new DisplayMode instance. Make width and height 2 to the n times the value of the corresponding resolution value for best results.
	 * @param displayWidth The width of the window.
	 * @param displayHeight The height of the window.
	 * @param resolutionX The in-game x/width resolution.
	 * @param resolutionY The in-game y/height resolution.
	 * @param canvasBufferSize The canvas buffer size (2 is default).
	 */
	public DisplayMode(int displayWidth, int displayHeight, int resolutionX, int resolutionY, int canvasBufferSize) {
		
		super();
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.canvasBufferSize = canvasBufferSize;
	}

	/**
	 * @return The display width.
	 */
	public int getDisplayWidth() {
		
		return displayWidth;
	}

	/**
	 * @return The display height.
	 */
	public int getDisplayHeight() {
		
		return displayHeight;
	}

	/**
	 * @return The game x resolution.
	 */
	public int getResolutionX() {
		
		return resolutionX;
	}

	/**
	 * @return The game y resolution.
	 */
	public int getResolutionY() {
		
		return resolutionY;
	}

	/**
	 * @return The buffer size (1,2 or 3).
	 */
	public int getBufferSize() {
		
		return canvasBufferSize;
	}
}
