/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.input.ButtonInput;

public class IsMousePressed extends OneArgFunction{

	private final ButtonInput mouse;
	
	public IsMousePressed(ButtonInput mouse) {
		this.mouse = mouse;
	}
	
	@Override
	public LuaValue call(LuaValue arg0) {
		return LuaValue.valueOf(mouse.isPressed(arg0.checkint()));
	}

}
