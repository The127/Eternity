/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

/**
 * Four arg abstract lua function.
 * Since not in standard lib.
 * @author Julian Sven Baehr
 *
 */
public abstract class FourArgFunction extends LibFunction{

	/**
	 * @param arg1 The first argument.
	 * @param arg2 The second argument.
	 * @param arg3 The third argument.
	 * @param arg4 The fourth argument.
	 */
	public abstract LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3, LuaValue arg4);
	
	@Override
	public Varargs invoke(Varargs varargs){
		return call(varargs.arg1(), varargs.arg(2), varargs.arg(3), varargs.arg(4));
	}
}
