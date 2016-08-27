/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import java.io.File;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.moandjiezana.toml.Toml;

import de.eternity.GameData;
import de.eternity.gfx.Animation;
import de.eternity.gfx.TextureStorage;

/**
 * Loads an animation from the dedicated animation directory.
 * Takes an animation name and locates it in the dedicated animatino directory.
 * @author Julian Sven Baehr
 */
public class LoadAnimation extends OneArgFunction{

	private String animationsPath;
	private TextureStorage textureStorage;
	
	public LoadAnimation(GameData gameData){
		textureStorage = gameData.getTextureStorage();
		animationsPath = gameData.getSettings().getAnimationsPath();
	}
	
	@Override
	public LuaValue call(LuaValue animation) {
		
		String animationName = animation.checkjstring();
		return CoerceJavaToLua.coerce(
				new Toml().read(new File("res/" + animationsPath + "/" + animationName + ".toml"))
				.to(Animation.class).init(textureStorage));
	}

}
