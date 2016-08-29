/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;

import de.eternity.gfx.Color;
import de.eternity.support.lua.FourArgFunction;

/**
 * Creates a color from single argb values.
 * @author Julian Sven Baehr
 * @see Color#argbToColor(int, int, int, int)
 */
public class ArgbToColor extends FourArgFunction {

	@Override
	public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3, LuaValue arg4) {
		return LuaValue.valueOf(Color.argbToColor(arg1.checkint(), arg2.checkint(), arg3.checkint(), arg4.checkint()));
	}

}
