package de.eternity.support.lua.functions;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.sound.Sound;

public class LoadSound extends OneArgFunction{
	
	@Override
	public LuaValue call(LuaValue sound) {
		try {
			return CoerceJavaToLua.coerce(new Sound(sound.checkjstring()));
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			throw new LuaError(e);
		}
	}

}
