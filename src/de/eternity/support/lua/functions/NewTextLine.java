/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.GameData;
import de.eternity.gfx.Text;

public class NewTextLine extends TwoArgFunction{

	private GameData gameData;
	
	public NewTextLine(GameData gameData) {
		this.gameData = gameData;
	}
	
	@Override
	public LuaValue call(LuaValue text, LuaValue color) {
		return CoerceJavaToLua.coerce(new Text(text.checkjstring(), color.checkint(), gameData));
	}
}
