/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.eternity.Game;

/**
 * Pops the current game state from the game state stack.
 * @author Julian Sven Baehr
 * @see Game#popGameState()
 */
public class PopGameState extends ZeroArgFunction{

	private Game game;
	
	public PopGameState(Game game){
		this.game = game;
	}
	
	@Override
	public LuaValue call() {
		
		game.popGameState();
		return null;
	}

}
