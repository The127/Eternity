package de.eternity.demo;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.eternity.Game;
import de.eternity.gfx.Renderer;
import de.eternity.gfx.Texture;
import de.eternity.gui.Display;
import de.eternity.gui.DisplayMode;

public class DemoLauncher {

	public static void main(String[] args) {
		
		//init display
		DisplayMode displayMode = new DisplayMode(800, 600, 400, 300);
		Display display = new Display("Demo", displayMode);
		
		display.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				//handle closing of window
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		//init renderer
		Renderer renderer = new Renderer(new Texture(display.getCanvas()));
		
		//init game
		Game game = new Game(renderer, () -> display.refreshScreen());
		
		int demoState = game.addGameState(new DemoState());
		game.setCurrentGameState(demoState);
		
		game.start();
	}
}
