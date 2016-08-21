package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.Game;
import de.eternity.util.LuaGameStates;

public class PushGameState extends OneArgFunction{

	private Game game;
	private LuaGameStates luaGameStates;
	
	public PushGameState(Game game, LuaGameStates luaGameStates){
		this.game = game;
		this.luaGameStates = luaGameStates;
	}
	
	@Override
	public LuaValue call(LuaValue gameStateFileName) {
		
		String jsGameStateFileName = gameStateFileName.checkjstring();
		game.pushGameState(luaGameStates.getGameState(jsGameStateFileName));
		
		return null;
	}

}
