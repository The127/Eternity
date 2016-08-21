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
		DisplayMode displayMode = gameData.getDisplayMode();
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
				//handle closing of window
				//maybe handle saving of game
				System.exit(0);
			}
		});
		
		//init renderer and camera
		Camera camera = new Camera(displayMode.getResolutionX(), displayMode.getResolutionY());
		Renderer renderer = new Renderer(new Texture(display.getCanvas()), camera);
		
		//init game
		
		Game game = new Game(gameData, renderer, display::refreshScreen);
		EngineLuaEnvironment engineLuaEnvironment = new EngineLuaEnvironment(game);
		
		game.pushGameState(new DemoState());
		game.pushGameState(engineLuaEnvironment.loadGameState(
				   "local luaGameState = {}\n\r"
				
				+  "local does_remain_on_stack = true\n\r"
				
				+  "local function startup()\n\r"
				+  "	print('startup lua')\n\r"
				+  "end\n\r"
				
				+  "local function shutdown()\n\r"
				+  "	print('shutdown lua')\n\r"
				+  "end\n\r"

				+  "local function pause()\n\r"
				+  "	print('pause lua')\n\r"
				+  "end\n\r"

				+  "local function unpause()\n\r"
				+  "	print('unpause lua')\n\r"
				+  "end\n\r"
				
				+  "local function update(delta)\n\r"
//				+  "	print('update lua')\n\r"
				+  "	poll_keyboard()\r\n"
				+  "	if is_key_pressed(VK_A) then\r\n"
				+  "		pop_game_state()\r\n"
				+  "	end\r\n"
				+  "end\n\r"

				+  "local function apply_render_context(renderQueue)\n\r"
//				+  "	print('render lua')\n\r"
				+  "end\n\r"

				+  "luaGameState.does_remain_on_stack = does_remain_on_stack\n\r"
				
				+  "luaGameState.startup = startup\n\r"
				+  "luaGameState.shutdown = shutdown\n\r"
				
				+  "luaGameState.pause = pause\n\r"
				+  "luaGameState.unpause = unpause\n\r"
				
				+  "luaGameState.update = update\n\r"
				+  "luaGameState.apply_render_context = apply_render_context\n\r"

				+  "return luaGameState\n\r"
		));
		
		//start the game
		game.start();
	}
}
