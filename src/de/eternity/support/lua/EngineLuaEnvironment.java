/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import de.eternity.Game;
import de.eternity.gui.Display;
import de.eternity.support.lua.functions.ArgbToColor;
import de.eternity.support.lua.functions.GetFps;
import de.eternity.support.lua.functions.GetGameData;
import de.eternity.support.lua.functions.GetGameMap;
import de.eternity.support.lua.functions.GetMousePosition;
import de.eternity.support.lua.functions.GetTextureStorage;
import de.eternity.support.lua.functions.IsKeyPressed;
import de.eternity.support.lua.functions.IsMousePressed;
import de.eternity.support.lua.functions.LoadAnimation;
import de.eternity.support.lua.functions.LoadSound;
import de.eternity.support.lua.functions.NewTextArea;
import de.eternity.support.lua.functions.NewTextLine;
import de.eternity.support.lua.functions.PollInput;
import de.eternity.support.lua.functions.PopGameState;
import de.eternity.support.lua.functions.PushGameState;
import de.eternity.support.lua.functions.RgbToColor;
import de.eternity.support.lua.functions.SetWindowTitle;
import de.eternity.support.lua.functions.ToGlobalTextureId;
import de.eternity.support.lua.functions.UpdateTileAnimations;
import de.eternity.util.LuaGameStates;

/**
 * Encapsulates the lua support for the game engine.
 * @author Julian Sven Baehr
 *
 */
public class EngineLuaEnvironment {

	private Globals _G = JsePlatform.standardGlobals();
	
	/**
	 * Creates a new lua environment.
	 * Initializes all provided lua functions from the game engine.
	 * @param display The game display.
	 * @param game The game.
	 * @param luaGameStates The lua game states manager object.
	 */
	public EngineLuaEnvironment(Display display, Game game, LuaGameStates luaGameStates){
		
		//load all lua scripts in the dedicated folder and sub folders (first root folder, then subfolders (recursive))
		loadLuaFolder(new File(Paths.get("res", game.getGameData().getSettings().getLuaScriptsPath()).toAbsolutePath().toString()));
		
		//load hard coded key bindings from KeyEvent class
		Class<KeyEvent> keyEventClass = KeyEvent.class;
		Field[] fields = keyEventClass.getFields();
		for(int i = 0; i < fields.length; i++)
			if(fields[i].getName().startsWith("VK_"))
				try {
					_G.load(fields[i].getName() + " = " + fields[i].getInt(null)).call();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					//this error is not possible
					e.printStackTrace();
					System.exit(-1);
				}
		
		//load hard coded key bindings form the MouseEvent class
		Class<MouseEvent> mouseEventClass = MouseEvent.class;
		Field[] fields2 = mouseEventClass.getFields();
		for(int i = 0; i < fields2.length; i++)
			if(fields2[i].getName().startsWith("BUTTON"))
				try{
					_G.load(fields2[i].getName() + " = " + fields2[i].getInt(null)).call();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					//this error is not possible
					e.printStackTrace();
					System.exit(-1);
				}
		
		//init game state handling lua functions
		_G.set("push_game_state", new PushGameState(game, luaGameStates));
		_G.set("pop_game_state", new PopGameState(game));
		
		//init keyboard handling lua functions
		_G.set("poll_input", new PollInput(game.getGameData().getKeyboard(), game.getGameData().getMouse(), display));
		_G.set("is_key_pressed", new IsKeyPressed(game.getGameData().getKeyboard()));
		_G.set("is_mouse_pressed", new IsMousePressed(game.getGameData().getMouse()));
		_G.set("get_mouse_position", new GetMousePosition());
		
		//init game map methods
		_G.set("get_game_map", new GetGameMap(game.getGameData().getGameMaps()));
		
		//init utility methods
		_G.set("get_game_data", new GetGameData(game.getGameData()));
		_G.set("get_fps", new GetFps());
		
		//init graphics methods
		_G.set("new_text_area", new NewTextArea(game.getGameData()));
		_G.set("new_text_line", new NewTextLine(game.getGameData()));
		_G.set("argb_to_color", new ArgbToColor());
		_G.set("rgb_to_color", new RgbToColor());
		_G.set("load_animation", new LoadAnimation(game.getGameData()));
		_G.set("set_window_title", new SetWindowTitle(display));
		_G.set("update_tile_animations", new UpdateTileAnimations(game.getGameData().getTileStorage()));
		_G.set("get_texture_storage", new GetTextureStorage(game.getGameData().getTextureStorage()));
		_G.set("to_global_texture_id", new ToGlobalTextureId(game.getGameData().getTextureStorage()));
		
		//init sound methods
		_G.set("load_sound", new LoadSound(game.getGameData().getSettings().getSoundPath()));
	}
	
	/**
	 * Loads a lua folder.
	 * @param dir The directory file object.
	 */
	private void loadLuaFolder(File dir){
		
		String[] subFiles = dir.list();
		
		if(subFiles != null)//test if subFiles exist
			
			//files first
			for(int i = 0; i < subFiles.length; i++)
				//only .lua files
				if(subFiles[i].endsWith(".lua"))
					try{
						_G.get("dofile").call(dir.getAbsolutePath() + "/" + subFiles[i]);
					}catch(LuaError luaError){
						System.err.println("Error with file: " + subFiles[i]);
						throw luaError;
					}
			
			//folders second
			for(int i = 0; i < subFiles.length; i++)
				//only .lua files
				if(!subFiles[i].endsWith(".lua")){
					File subDir = new File(dir.getAbsolutePath() + "/" + subFiles[i]);
					if(subDir.exists() && subDir.isDirectory())
						loadLuaFolder(subDir);
				}
	}
	
	/**
	 * Sets a global lua method.
	 * @param methodName The name of the method.
	 * @param function The lua method.
	 */
	public void setMethod(String methodName, LuaValue function){
		_G.set(methodName, function);
	}
	
	/**
	 * Loads a lua game state.
	 * @param scriptPath The path to the script.
	 * @return The loaded script wrapped in a lua game state object.
	 */
	public LuaGameState loadGameState(Path scriptPath){
		return new LuaGameState(_G.loadfile(scriptPath.toString()), scriptPath.getFileName().toString().split("\\.")[0]);
	}
}
