package de.eternity;

import de.eternity.gui.DisplayMode;

public class GameSettings {

	private int tilesize;
	
	private String modsPath;
	private String mapsPath;
	private String tilesetsPath;
	private String gameStatesPath;
	
	private DisplayMode displayMode;

	public int getTilesize() {
		return tilesize;
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

	public DisplayMode getDisplayMode() {
		return displayMode;
	}
}