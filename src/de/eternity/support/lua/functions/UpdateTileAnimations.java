/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.map.TileStorage;

/**
 * Updates all tile animations.
 * @author Julian Sven Baehr
 * @see TileStorage#update(double)
 */
public class UpdateTileAnimations extends OneArgFunction{

	private TileStorage tileStorage;
	
	public UpdateTileAnimations(TileStorage tileStorage) {
		this.tileStorage = tileStorage;
	}
	
	@Override
	public LuaValue call(LuaValue delta) {
		
		tileStorage.update(delta.checkdouble());
		
		return null;
	}

}
