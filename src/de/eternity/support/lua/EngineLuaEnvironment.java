package de.eternity.support.lua;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.nio.file.Path;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import de.eternity.Game;
import de.eternity.gui.Display;
import de.eternity.support.lua.functions.GetFps;
import de.eternity.support.lua.functions.GetGameData;
import de.eternity.support.lua.functions.GetGameMap;
import de.eternity.support.lua.functions.GetTextureStorage;
import de.eternity.support.lua.functions.IsKeyPressed;
import de.eternity.support.lua.functions.PollKeyboard;
import de.eternity.support.lua.functions.PopGameState;
import de.eternity.support.lua.functions.PushGameState;
import de.eternity.support.lua.functions.SetWindowTitle;
import de.eternity.support.lua.functions.UpdateTileAnimations;
import de.eternity.util.LuaGameStates;

public class EngineLuaEnvironment {

	private Globals _G = JsePlatform.standardGlobals();
	
	public EngineLuaEnvironment(Display display, Game game, LuaGameStates luaGameStates){
		
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
		
		//init game state handling lua functions
		_G.set("push_game_state", new PushGameState(game, luaGameStates));
		_G.set("pop_game_state", new PopGameState(game));
		
		//init keyboard handling lua functions
		_G.set("poll_keyboard", new PollKeyboard(game.getGameData().getKeyboard()));
		_G.set("is_key_pressed", new IsKeyPressed(game.getGameData().getKeyboard()));
		
		//init game map methods
		_G.set("get_game_map", new GetGameMap(game.getGameData().getGameMaps()));
		
		//init utility methods
		_G.set("get_game_data", new GetGameData(game.getGameData()));
		_G.set("get_fps", new GetFps());
		
		//init graphics methods
		_G.set("set_window_title", new SetWindowTitle(display));
		_G.set("update_tile_animations", new UpdateTileAnimations(game.getGameData().getTileStorage()));
		_G.set("get_texture_storage", new GetTextureStorage(game.getGameData().getTextureStorage()));
	}
	
	public LuaGameState loadGameState(Path scriptPath){
		return new LuaGameState(_G.loadfile(scriptPath.toString()), scriptPath.getFileName().toString().split("\\.")[0]);
	}
}
