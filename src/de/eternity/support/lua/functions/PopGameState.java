package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.eternity.Game;

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
