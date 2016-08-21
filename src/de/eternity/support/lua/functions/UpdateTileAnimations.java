package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.map.TileStorage;

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
