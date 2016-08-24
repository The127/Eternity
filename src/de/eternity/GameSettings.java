package de.eternity;

import de.eternity.gui.DisplayMode;

public class GameSettings {

	private int tilesize;
	private int oneMeter;
	
	private String modsPath;
	private String mapsPath;
	private String soundPath;
	private String tilesetsPath;
	private String gameStatesPath;
	private String animationsPath;
	private String luaScriptsPath;
	
	private DisplayMode displayMode;

	public int getTilesize() {
		return tilesize;
	}
	
	public int getOneMeter() {
		return oneMeter;
	}

	public String getModsPath() {
		return modsPath;
	}

	public String getMapsPath() {
		return mapsPath;
	}

	public String getTilesetsPath() {
		return tilesetsPath;
	}

	public String getGameStatesPath() {
		return gameStatesPath;
	}
	
	public String getSoundPath() {
		return soundPath;
	}
	
	public String getAnimationsPath() {
		return animationsPath;
	}

	public String getLuaScriptsPath() {
		return luaScriptsPath;
	}

	public DisplayMode getDisplayMode() {
		return displayMode;
	}
}
