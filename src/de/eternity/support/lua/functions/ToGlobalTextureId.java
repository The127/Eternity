package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import de.eternity.gfx.TextureStorage;

public class ToGlobalTextureId extends TwoArgFunction{

	private TextureStorage textureStorage;
	
	public ToGlobalTextureId(TextureStorage textureStorage){
		this.textureStorage = textureStorage;
	}
	
	@Override
	public LuaValue call(LuaValue tileset, LuaValue localId) {
		return LuaValue.valueOf(textureStorage.translateToGlobalTextureId(tileset.checkjstring(), localId.checkint()));
	}

}
