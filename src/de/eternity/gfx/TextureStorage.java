package de.eternity.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class TextureStorage {
	
	private Map<String, Integer> tilesets = new HashMap<>();
	private List<Texture> allTextures = new ArrayList<>();
	
	/**
	 * Loads a tileset into memory.
	 * @param path The path to the tileset.
	 * @param name The name of the tileset.
	 * @param textureWidth The width of a single texture within the tileset.
	 * @param textureHeight The height of a single texture within the tileset.
	 * @throws IOException
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
			
			int c = 0;
			//cut the texture into single tiles
			for(int y = 0; y < tilesVertical; y++)
				for(int x = 0; x < tilesHorizontal; x++)
					allTextures.add(tileset.subTexture(x * textureWidth, y * textureHeight, textureWidth, textureHeight));
		}
	}
	
	/**
	 * Translates a local texture id from a tileset to the corresponding global texture id.
	 * @param tileset The name of the tileset.
	 * @param localTileId The local texture id.
	 * @return The global texture id.
	 */
	public int translateToGlobalTextureId(String tileset, int localTextureId){
		
		if(!tilesets.containsKey(tileset))
			throw new IllegalArgumentException("No tileset '" + tileset + "' found!");
		
		return tilesets.get(tileset) + localTextureId;
	}
	
	/**
	 * @param globalTextureId
	 * @return The texture with the given global tile id.
	 */
	public Texture getTexture(int globalTextureId){
		return allTextures.get(globalTextureId);
	}
}
