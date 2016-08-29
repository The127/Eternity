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
public class PollInput extends ZeroArgFunction{

	private final ButtonInput keyboard, mouse;
	
	public PollInput(ButtonInput keyboard, ButtonInput mouse) {
		this.keyboard = keyboard;
		this.mouse = mouse;
	}
	
	@Override
	public LuaValue call() {
		try {
			keyboard.flip();
			mouse.flip();
		} catch (InterruptedException e) {
			//should never happen
			//TODO: logging
			e.printStackTrace();
		}
		return null;
	}

}
