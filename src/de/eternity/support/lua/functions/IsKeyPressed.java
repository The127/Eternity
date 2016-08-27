/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.input.ButtonInput;

/**
 * Checks if a key is currently being pressed.
 * @author Julian Sven Baehr
 * @see ButtonInput#isPressed(int)
 */
public class IsKeyPressed extends OneArgFunction{

	private ButtonInput keyboard;
	
	public IsKeyPressed(ButtonInput keyboard) {
		this.keyboard = keyboard;
	}
	
	@Override
	public LuaValue call(LuaValue arg) {
		
		int keyCode = arg.checkint();
		return LuaValue.valueOf(keyboard.isPressed(keyCode));
	}

}
