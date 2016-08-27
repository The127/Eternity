/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.GameData;

/**
 * Returns the game data manager object.
 * @author Julian Sven Baehr
 *
 */
public class GetGameData extends ZeroArgFunction {

	private GameData gameData;
	
	public GetGameData(GameData gameData){
		this.gameData = gameData;
	}
	
	@Override
	public LuaValue call() {
		
		return CoerceJavaToLua.coerce(gameData);
	}

}
