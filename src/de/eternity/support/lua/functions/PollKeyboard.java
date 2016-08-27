/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.eternity.input.ButtonInput;

/**
 * Polls the keyboard for button states.
 * @author Julian Sven Baehr
 * @see ButtonInput#flip()
 */
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
