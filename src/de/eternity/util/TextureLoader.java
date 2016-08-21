package de.eternity.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.moandjiezana.toml.Toml;

import de.eternity.gfx.TextureStorage;

public class TextureLoader {

	public static void loadTextures(String path, TextureStorage textureStorage) throws IOException {
		
		File root = new File(Paths.get("res", path).toAbsolutePath().toString());
		String[] subFiles = root.list();
		
		//there are always two files that belong together (.toml and .png)
		for(int i = 0; i < subFiles.length; i+=2){
			
			//read the tolm file and then load the tileset
			String name =  subFiles[i].split("\\.")[0];
			Toml toml = new Toml().read(new File(Paths.get("res" + path, name + ".toml").toAbsolutePath().toString()));
			//use the tilewidth and height from the toml file
			textureStorage.loadTileset(Paths.get(path, name + ".png").toString().replace("\\", "/"), name, toml.getLong("tilewidth").intValue(), toml.getLong("tileheight").intValue());
		}
	}
}
