package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.input.ButtonInput;

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
