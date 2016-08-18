package de.eternity.demo;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.eternity.Game;
import de.eternity.GameData;
import de.eternity.gfx.Camera;
import de.eternity.gfx.Renderer;
import de.eternity.gfx.Texture;
import de.eternity.gui.Display;
import de.eternity.gui.DisplayMode;
import de.eternity.input.ButtonInput;
import de.eternity.input.KeyboardAdapter;

public class DemoLauncher {

	public static void main(String[] args) {
		
		//init input
		ButtonInput keyboard = new ButtonInput(256);
		KeyboardAdapter keyboardAdapter = new KeyboardAdapter(keyboard);
		
		//init display
		DisplayMode displayMode = new DisplayMode(800, 600, 400, 300);
		Display display = new Display("Demo", displayMode);
		
		//set listeners
		display.addKeyListener(keyboardAdapter);
		display.addWindowListener(new WindowListener() {
			
			//ignore all these
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				//handle closing of window
				//maybe handle saving of game
				System.exit(0);
			}
		});
		
		//init renderer and camera
		Camera camera = new Camera(displayMode.getResolutionX(), displayMode.getResolutionY());
		Renderer renderer = new Renderer(new Texture(display.getCanvas()), camera);
		
		//init game
		GameData gameData = new GameData(32, keyboard);
		
		Game game = new Game(gameData, renderer, display::refreshScreen);
		
		game.pushGameState(new DemoState());
		
		//start the game
		game.start();
	}
}
