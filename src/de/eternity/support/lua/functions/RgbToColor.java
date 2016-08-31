/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

import de.eternity.gfx.Color;

/**
 * Creates a color from single argb values.
 * @author Julian Sven Baehr
 * @see Color#argbToColor(int, int, int, int)
 */
public class RgbToColor extends ThreeArgFunction {

	@Override
	public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
		return LuaValue.valueOf(Color.argbToColor(0xFF, arg1.checkint(), arg2.checkint(), arg3.checkint()));
	}

}
