package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.eternity.Game;

public class GetFps extends ZeroArgFunction{
	
	@Override
	public LuaValue call() {
		return LuaValue.valueOf(Game.getFps());
	}

}
