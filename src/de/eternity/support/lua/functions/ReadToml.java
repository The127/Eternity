package de.eternity.support.lua.functions;

import java.io.File;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.moandjiezana.toml.Toml;

import de.eternity.GameData;

public class ReadToml extends OneArgFunction{

	String dataFilesPath;
	
	public ReadToml(GameData gameData){
		dataFilesPath = gameData.getSettings().getDataFilesPath();
	}

	@Override
	public LuaValue call(LuaValue file) {
		return CoerceJavaToLua.coerce(new Toml().read(new File(dataFilesPath + "/" + file + ".toml")));
	}
}
