package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.map.GameMap;
import de.eternity.util.GameMaps;

public class GetGameMap extends OneArgFunction{

	private GameMaps gameMaps;
	
	public GetGameMap(GameMaps gameMaps) {
		this.gameMaps = gameMaps;
	}
	
	@Override
	public LuaValue call(LuaValue name) {
		
		String jsName = name.checkjstring();
		GameMap gameMap = gameMaps.getGameMap(jsName);
		return CoerceJavaToLua.coerce(gameMap);
	}

}
