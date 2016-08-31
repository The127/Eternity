package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.GameData;
import de.eternity.gfx.Text;

public class NewTextArea extends ThreeArgFunction{

	private GameData gameData;
	
	public NewTextArea(GameData gameData) {
		this.gameData = gameData;
	}
	
	@Override
	public LuaValue call(LuaValue width, LuaValue height, LuaValue color) {
		return CoerceJavaToLua.coerce(new Text(width.checkint(), height.checkint(), color.checkint(), gameData));
	}

}
