package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.Game;
import de.eternity.support.lua.EngineLuaEnvironment;

public class PushGameState extends OneArgFunction{

	private Game game;
	private EngineLuaEnvironment engineLuaEnvironment;
	
	public PushGameState(Game game, EngineLuaEnvironment engineLuaEnvironment){
		this.game = game;
		this.engineLuaEnvironment = engineLuaEnvironment;
	}
	
	@Override
	public LuaValue call(LuaValue gameStateFileName) {
		
		LuaString sGameStateFileName = gameStateFileName.checkstring();
		String jsGameStateFileName = sGameStateFileName.tojstring();
		
		//TODO:
		game.pushGameState(engineLuaEnvironment.loadGameState(jsGameStateFileName));
		
		return null;
	}

}
