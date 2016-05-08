package de.eternity.gfx;

import java.io.IOException;
import java.util.LinkedList;

/**
 * A class with static methods to make image handling more resource friendly. Some method can be slow since they may need to do io operations or a sequential list lookup.
 * This class is threadsafe.
 * @author Julian Sven Baehr
 */
public class Textures  {

	private static LinkedList<Texture> textures = new LinkedList<>();
	
	/**
	 * Looks if the texture has already been loaded and returns it. If it is not already loaded into memory this method will load the image and return it.
	 * @param path The textures path.
	 * @return The texture at the given path.
	 * @throws IOException If the texture cannot be loaded.
	 */
	public synchronized static Texture get(String path) throws IOException{
		
		for(int i = 0; i < textures.size(); i++)
			if(textures.get(i).getPath().equals(path))
				return textures.get(i);
		
		Texture t = new Texture(path);
		textures.add(t);
		return t;
	}
	
	/**
	 * Adds a texture to the list if it has not already been added.
	 * @param texture The texture.
	 * @return True if the texture was added to the list, flase otherwise.
	 */
	public synchronized static boolean add(Texture texture){

		if(!textures.contains(texture)){

			textures.add(texture);
			return true;
		}
		
		return false;
	}
}