/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import java.nio.file.Paths;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.eternity.GameData;

public class GetDataFilePath extends OneArgFunction{

	private GameData gameData;
	
	public GetDataFilePath(GameData gameData) {
		this.gameData = gameData;
	}
	
	@Override
	public LuaValue call(LuaValue file) {
		return LuaValue.valueOf(Paths.get("res" + gameData.getSettings().getDataFilesPath() , file.checkjstring()).toAbsolutePath().toString());
	}

}
