package de.eternity.support.lua;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import de.eternity.Game;
import de.eternity.support.lua.functions.IsKeyPressed;
import de.eternity.support.lua.functions.PollKeyboard;
import de.eternity.support.lua.functions.PopGameState;
import de.eternity.support.lua.functions.PushGameState;

public class EngineLuaEnvironment {

	private Globals _G = JsePlatform.standardGlobals();
	
	private Game game;
	
	public EngineLuaEnvironment(Game game){
		this.game = game;
		
		init();
	}
	
	private void init(){
		
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
		_G.set("push_game_state", new PushGameState(game, this));
		_G.set("pop_game_state", new PopGameState(game));
		
		//init keyboard handling lua functions
		_G.set("poll_keyboard", new PollKeyboard(game.getGameData().getKeyboard()));
		_G.set("is_key_pressed", new IsKeyPressed(game.getGameData().getKeyboard()));
	}
	
	public LuaGameState loadGameState(String script){
		return new LuaGameState(_G.load(script));
	}
}
