package de.eternity.demo;

import java.awt.KeyboardFocusManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import de.eternity.Game;
import de.eternity.GameData;
import de.eternity.gfx.Camera;
import de.eternity.gfx.Renderer;
import de.eternity.gfx.Texture;
import de.eternity.gui.Display;
import de.eternity.gui.DisplayMode;
import de.eternity.input.ButtonInput;
import de.eternity.input.KeyboardAdapter;
import de.eternity.support.lua.EngineLuaEnvironment;

public class DemoLauncher {

	public static void main(String[] args) throws IOException {
		
		//init input
		ButtonInput keyboard = new ButtonInput(256);
		
		//directly apply keyboard adapter to the keyboard focus manager
		//this way not every component must have a key listener
		KeyboardAdapter keyboardAdapter = new KeyboardAdapter(keyboard);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyboardAdapter);
		
		//init game data
		GameData gameData = new GameData(keyboard);
		
		//init display
		DisplayMode displayMode = gameData.getSettings().getDisplayMode();
		Display display = new Display("Demo", displayMode);
		
		//set listeners
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
				System.exit(0);//handle closing of window
			}
		});
		
		//init renderer and camera
		Camera camera = new Camera(displayMode.getResolutionX(), displayMode.getResolutionY());
		Renderer renderer = new Renderer(new Texture(display.getCanvas()), camera);
		
		//init game and lua
		Game game = new Game(gameData, renderer, display::refreshScreen);
		EngineLuaEnvironment engineLuaEnvironment = new EngineLuaEnvironment(game, gameData.getLuaGameStates());
		
		gameData.init(engineLuaEnvironment);
		
		//demo game states
//		game.pushGameState(new DemoState());
		game.pushGameState(gameData.getLuaGameState("lua_test_state"));
		
		//start the game
		game.start();
	}
}
