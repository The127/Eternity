/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.gui.Display;

/**
 * Sets the window title.
 * @author Julian Sven Baehr
 *
 */
public class SetWindowTitle extends OneArgFunction{

	Display display;
	
	public SetWindowTitle(Display display) {
		this.display = display;
	}
	
	@Override
	public LuaValue call(LuaValue title) {
		
		display.setTitle(title.checkjstring());
		
		return null;
	}

}
