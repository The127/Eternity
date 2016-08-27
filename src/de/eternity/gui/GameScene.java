/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Canvas for the game.
 * @author Julian Sven Baehr
 *
 */
@SuppressWarnings("serial")
public class GameScene extends Canvas{

	protected BufferedImage buffer;
	
	protected DisplayMode displayMode;
	
	/**
	 * Creates a new game scene.
	 * @param displayMode The display mode for the scene.
	 */
	public GameScene(DisplayMode displayMode){
		
		if(displayMode == null)
			throw new NullPointerException("Display Mode not defined in settings!");
		
		this.displayMode = displayMode;
		
		setPreferredSize(new Dimension(displayMode.getDisplayWidth(), displayMode.getDisplayHeight()));
		
		//create compatible buffered image for hopefully the best possible performance
		buffer = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration()
					.createCompatibleImage(displayMode.getResolutionX(), displayMode.getResolutionY(), Transparency.OPAQUE);;
	}
	
	/**
	 * Refreshes the screen. This method should be the most time consuming call in the whole engine.
	 * Is called by the game (rendering thread).
	 */
	public void refreshScreen(){
		
		BufferStrategy bs = getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(buffer, 0, 0, displayMode.getDisplayWidth(), displayMode.getDisplayHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	/**
	 * @return The rendering buffer.
	 */
	public BufferedImage getCanvas(){
		
		return buffer;
	}
}
