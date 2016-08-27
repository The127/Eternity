/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity;

import de.eternity.gui.DisplayMode;

/**
 * This class represents all game settings.
 * The contents of this class are loaded from the settings.toml file.
 * @author Julian Sven Baehr
 *
 */
public class GameSettings {

	private int tilesize;
	private int oneMeter;
	
	private String dataFilesPath;
	private String mapsPath;
	private String soundPath;
	private String tilesetsPath;
	private String gameStatesPath;
	private String animationsPath;
	private String luaScriptsPath;
	
	private DisplayMode displayMode;

	/**
	 * @return The tile size of the tiles in the game maps.
	 */
	public int getTilesize() {
		return tilesize;
	}
	
	/**
	 * @return The number in pixels for one meter/one unit in the game.
	 */
	public int getOneMeter() {
		return oneMeter;
	}

	/**
	 * @return The path to the data file directory.
	 */
	public String getDataFilesPath() {
		return dataFilesPath;
	}

	/**
	 * @return The path to the maps file directory.
	 */
	public String getMapsPath() {
		return mapsPath;
	}

	/**
	 * @return The path to the tilesets file directory.
	 */
	public String getTilesetsPath() {
		return tilesetsPath;
	}

	/**
	 * @return The path to the lua game states file directory.
	 */
	public String getGameStatesPath() {
		return gameStatesPath;
	}

	/**
	 * @return The path to the sounds file directory.
	 */
	public String getSoundPath() {
		return soundPath;
	}

	/**
	 * @return The path to the animations file directory.
	 */
	public String getAnimationsPath() {
		return animationsPath;
	}

	/**
	 * @return The path to the root lua file directory.
	 */
	public String getLuaScriptsPath() {
		return luaScriptsPath;
	}

	/**
	 * @return The games display mode as defined in the settings.toml.
	 */
	public DisplayMode getDisplayMode() {
		return displayMode;
	}
}
