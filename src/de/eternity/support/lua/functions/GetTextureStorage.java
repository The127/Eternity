package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.gfx.TextureStorage;

public class GetTextureStorage extends ZeroArgFunction{

	private TextureStorage textureStorage;
	
	public GetTextureStorage(TextureStorage textureStorage) {
		this.textureStorage = textureStorage;
	}
	
	@Override
	public LuaValue call() {
		
		return CoerceJavaToLua.coerce(textureStorage);
	}

}
