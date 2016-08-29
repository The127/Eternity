/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity;

import java.awt.KeyboardFocusManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import org.luaj.vm2.LuaFunction;

import de.eternity.gfx.Camera;
import de.eternity.gfx.Renderer;
import de.eternity.gfx.Texture;
import de.eternity.gui.Display;
import de.eternity.gui.DisplayMode;
import de.eternity.input.ButtonInput;
import de.eternity.input.KeyboardAdapter;
import de.eternity.input.MouseAdapter;
import de.eternity.support.lua.EngineLuaEnvironment;

/**
 * A class that handles nearly every initialization for the engine and provides hooks for new lua methods.
 * @author Julian Sven Baehr
 *
 */
public class Launcher {

	private static Game game;
	private static EngineLuaEnvironment engineLuaEnvironment;
	
	/**
	 * Initializes the game and the display.
	 * @param windowTitle The title of the display.
	 */
	public static void initGame(String windowTitle) {
		
		//init input
		ButtonInput mouse = new ButtonInput(16);
		ButtonInput keyboard = new ButtonInput(256);
		
		MouseAdapter mouseAdapter = new MouseAdapter(mouse);
		
		//directly apply keyboard adapter to the keyboard focus manager
		//this way not every component must have a key listener
		KeyboardAdapter keyboardAdapter = new KeyboardAdapter(keyboard);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyboardAdapter);
		
		//init game data
		GameData gameData = new GameData(keyboard, mouse);
		
		//init display
		DisplayMode displayMode = gameData.getSettings().getDisplayMode();
		Display display = new Display(windowTitle, displayMode);
		
		//set listeners
		display.addMouseListener(mouseAdapter);
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
		game = new Game(gameData, renderer, display::refreshScreen);
		engineLuaEnvironment = new EngineLuaEnvironment(display, game, gameData.getLuaGameStates());
	}
	
	/**
	 * @return The games data manager.
	 */
	public static GameData getGameData(){
		return game.getGameData();
	}
	
	/**
	 * Adds a new global lua method with the given name.
	 * This is useful to create new lua method based on java methods.
	 * @param methodName The name of the method.
	 * @param function The method.
	 */
	public static void addLuaMethod(String methodName, LuaFunction function){
		engineLuaEnvironment.setMethod(methodName, function);
	}
	
	/**
	 * Starts the game.
	 * @param startGameState The name of the first lua game state.
	 * @throws IOException
	 */
	public static void start(String startGameState) throws IOException{
		
		game.getGameData().init(engineLuaEnvironment);
		
		//push the start game state
		game.pushGameState(game.getGameData().getLuaGameState(startGameState));
		
		game.start();
	}
}
