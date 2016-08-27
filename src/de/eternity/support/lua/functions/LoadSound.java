/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.sound.Sound;

/**
 * Loads a sound file from the dedicated sound directory.
 * Takes the name of the sound and locates it in the dedicated sound directory.
 * @author Julian Sven Baehr
 * @see Sound#Sound(String)
 */
public class LoadSound extends OneArgFunction{
	
	private final String soundPath;
	
	public LoadSound(String soundPath){
		this.soundPath = soundPath;
	}
	
	@Override
	public LuaValue call(LuaValue sound) {
		try {
			return CoerceJavaToLua.coerce(new Sound(soundPath + "/" + sound.checkjstring() + ".wav"));
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			throw new LuaError(e);
		}
	}

}
