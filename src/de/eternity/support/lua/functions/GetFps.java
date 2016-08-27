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
 * Returns the current fps of the game.
 * @author Julian Sven Baehr
 * @see Game#getFps()
 */
public class GetFps extends ZeroArgFunction{
	
	@Override
	public LuaValue call() {
		return LuaValue.valueOf(Game.getFps());
	}

}
