package de.eternity.gui;

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

		this(displayHeight, displayWidth, resolutionX, resolutionY, 2);
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

	public int getDisplayWidth() {
		
		return displayWidth;
	}

	public int getDisplayHeight() {
		
		return displayHeight;
	}

	public int getResolutionX() {
		
		return resolutionX;
	}

	public int getResolutionY() {
		
		return resolutionY;
	}

	public int getBufferSize() {
		
		return canvasBufferSize;
	}
}
