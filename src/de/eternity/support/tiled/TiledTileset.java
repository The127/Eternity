package de.eternity.support.tiled;

import java.util.Map;

import de.eternity.gfx.Animation;
import de.eternity.gfx.TextureStorage;
import de.eternity.map.Tile;
import de.eternity.map.TileStorage;

class TiledTileset {

	int firstgid, tilecount;
	String name;
	
	private Map<String, TiledTile> tiles;
	
	/**
	 * Makes a game tile for every texture tile in the tileset and loads the animations.
	 * @param tileStorage
	 * @param textureStorage
	 * @param tileset
	 */
	void initialize(TileStorage tileStorage, TextureStorage textureStorage){
		
		//TODO: load lua file
		
		//add all tiles with their animations
		for(int i = 0; i < tilecount; i++){
			//-1 because the tile id 0 is empty in the editor
			int globalId = textureStorage.translateToGlobalTextureId(name, firstgid + i) -1;
			
			boolean isPresent = tiles.containsKey("" + i);
			Animation tileAnimation;
			if(isPresent && tiles.get("" + i).hasAnimation()){
				
				//repackage the loaded information
				TiledTile loadedTile = tiles.get("" + i);
				
				int[] animationFrameTimesMillis = new int[loadedTile.animation.length];
				int[] translatedAnimationTextures = new int[animationFrameTimesMillis.length];
				
				for(int n = 0; n < animationFrameTimesMillis.length; n++){
					animationFrameTimesMillis[n] = loadedTile.animation[n].duration;
					translatedAnimationTextures[n] = textureStorage.translateToGlobalTextureId(name, firstgid + loadedTile.animation[n].tileid -1);
				}
				
				tileAnimation = new Animation(translatedAnimationTextures, animationFrameTimesMillis);
			}else{
				//make a single frame animation that does not update
				tileAnimation = new Animation(new int[]{globalId}, new int[]{-1});
			}
			//store the tile at the index for the start texture
			tileStorage.addTile(new Tile(tileAnimation), globalId);
		}
	}
}
