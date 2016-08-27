/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.Game;
import de.eternity.util.LuaGameStates;

/**
 * Pushes a new game state on the game state stack.
 * Takes the name of a lua game state and loads the corresponding game state.
 * @author Julian Sven Baehr
 * @see Game#pushGameState(de.eternity.GameState)
 */
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
