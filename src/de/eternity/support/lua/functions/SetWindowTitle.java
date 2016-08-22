package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.gui.Display;

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
