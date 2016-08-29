/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * Returns the current mouse position on the game screen (not world position).
 * @author Julian Sven Baehr
 */
public class GetMousePosition extends VarArgFunction{

	@Override
	public Varargs invoke(Varargs arg0) {
		return LuaValue.varargsOf(LuaValue.valueOf(PollInput.m_x), LuaValue.valueOf(PollInput.m_y));
	}
}
