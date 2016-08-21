package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.eternity.input.ButtonInput;

public class PollKeyboard extends ZeroArgFunction{

	private ButtonInput keyboard;
	
	public PollKeyboard(ButtonInput keyboard) {
		this.keyboard = keyboard;
	}
	
	@Override
	public LuaValue call() {
		try {
			keyboard.flip();
		} catch (InterruptedException e) {
			//should never happen
			//TODO: logging
			e.printStackTrace();
		}
		return null;
	}

}
