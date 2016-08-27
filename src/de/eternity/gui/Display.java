/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gui;

import java.awt.Frame;
import java.awt.image.BufferedImage;

/**
 * The window of a game.
 * @author Julian Sven Baehr
 *
 */
@SuppressWarnings("serial")
public class Display extends Frame {

	private DisplayMode displayMode;
	private GameScene gameScene;
	
	/**
	 * Create a new window.
	 * @param title The window title.
	 * @param displayMode The display mode (dimensions,...) of the window.
	 */
	public Display(String title, DisplayMode displayMode){
		
		this.displayMode = displayMode;
		
		gameScene = new GameScene(displayMode);
		
		add(gameScene);
		setResizable(false);//cannot handle resizes
		pack();
		
		setTitle(title);
		setLocationRelativeTo(null);
		setVisible(true);
		
		gameScene.createBufferStrategy(displayMode.getBufferSize());
	}
	
	/**
	 * @return The game scene.
	 */
	public GameScene getScene(){
		
		return gameScene;
	}
	
	/**
	 * @return The display mode.
	 */
	public DisplayMode getDisplayMode(){
		
		return displayMode;
	}
	
	/**
	 * Renders the buffer to the screen.
	 */
	public void refreshScreen(){
		
		gameScene.refreshScreen();
	}
	
	/**
	 * @return The screen buffer.
	 */
	public BufferedImage getCanvas(){
		
		return gameScene.getCanvas();
	}
}
