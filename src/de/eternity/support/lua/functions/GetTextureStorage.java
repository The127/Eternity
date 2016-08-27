/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.gfx.TextureStorage;

/**
 * Returns the global texture stroage object.
 * @author Julian Sven Baehr
 *
 */
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
