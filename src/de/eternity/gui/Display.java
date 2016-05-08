package de.eternity.gui;

import java.awt.Frame;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Display extends Frame {

	private DisplayMode displayMode;
	private GameScene gameScene;
	
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
	
	public GameScene getScene(){
		
		return gameScene;
	}
	
	public DisplayMode getDisplayMode(){
		
		return displayMode;
	}
	
	public void refreshScreen(){
		
		gameScene.refreshScreen();
	}
	
	public BufferedImage getCanvas(){
		
		return gameScene.getCanvas();
	}
}
