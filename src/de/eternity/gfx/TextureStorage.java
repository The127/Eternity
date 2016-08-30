/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a global texture storage.
 * It can handle the loading of a tileset image.
 * It can handle the translation from a tileset local id to a global id.
 * @author Julian Sven Baehr
 *
 */
public class TextureStorage {
	
	private Map<String, Integer> tilesets = new HashMap<>();
	private List<Texture> allTextures = new ArrayList<>();
	
	/**
	 * Loads a tileset into memory.
	 * @param path The path to the tileset.
	 * @param name The name of the tileset.
	 * @param textureWidth The width of a single texture within the tileset.
	 * @param textureHeight The height of a single texture within the tileset.
	 * @throws IOException If the tileset cannot be found.
	 */
	public void loadTileset(String path, String name, int textureWidth, int textureHeight) throws IOException{
		
		if(!tilesets.containsKey(name)){
			
			//add tileset to list with its global start value
			int firstTextureIndex = allTextures.size();
			tilesets.put(name, firstTextureIndex);
			
			//get the tileset texture
			Texture tileset = new Texture(path);
			int tilesHorizontal = tileset.getWidth() / textureWidth;
			int tilesVertical = tileset.getHeight() / textureHeight;

			//cut the texture into single tiles
			for(int y = 0; y < tilesVertical; y++)
				for(int x = 0; x < tilesHorizontal; x++){
					Texture texture = tileset.subTexture(x * textureWidth, y * textureHeight, textureWidth, textureHeight);
					allTextures.add(texture);
					
					//create generated name
					if(name.equals("abc"))
						texture.path = "GEN: ABC_" + (char)(x + y * tilesHorizontal + 32);
					else
						texture.path = "GEN: TILESET_" + name + " - LTID_" + (x + y * tilesHorizontal) + " - GTID_" + (allTextures.size()-1);
				}
		}
	}
	
	/**
	 * Translates a local texture id from a tileset to the corresponding global texture id.
	 * @param tileset The name of the tileset.
	 * @param localTextureId The local texture id.
	 * @return The global texture id.
	 */
	public int translateToGlobalTextureId(String tileset, int localTextureId){
		
		if(!tilesets.containsKey(tileset))
			throw new IllegalArgumentException("No tileset '" + tileset + "' found!");
		
		return tilesets.get(tileset) + localTextureId;
	}
	
	/**
	 * @param globalTextureId The global texture id.
	 * @return The texture with the given global tile id.
	 */
	public Texture getTexture(int globalTextureId){
		return allTextures.get(globalTextureId);
	}
}
