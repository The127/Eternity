/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.tiled;

import java.awt.Rectangle;
import java.util.Map;

import de.eternity.gfx.Animation;
import de.eternity.gfx.TextureStorage;
import de.eternity.map.Tile;
import de.eternity.map.TileStorage;


/**
 * A wrapper class for tiled editor json export file support.
 * @author Julian Sven Baehr
 */
class TiledTileset {

	int firstgid, tilecount;
	String name;
	
	private Map<String, TiledTile> tiles;
	private Map<String, TiledTileProperties> tileproperties;
	
	/**
	 * Makes a game tile for every texture tile in the tileset and loads the animations.
	 * @param tileStorage The global tile storage.
	 * @param textureStorage The global texture storage.
	 */
	void initialize(TileStorage tileStorage, TextureStorage textureStorage){
		
		//add all tiles with their animations
		for(int i = 0; i < tilecount; i++){
			
			boolean isSolid = false;
			
			//-1 because the tile id 0 is empty in the editor
			int globalId = textureStorage.translateToGlobalTextureId(name, firstgid + i) -1;
			
			boolean isPresent = tiles != null ? tiles.containsKey("" + i) : false;//consider that tiles might be null
			Animation tileAnimation = null;
			Rectangle[] collisionRectangles = null;
			
			if(tileproperties.containsKey("" + i))
				isSolid = tileproperties.get("" + i).solid;
			
			if(isPresent){
				
				if(!isSolid && tiles.get("" + i).hasCollisionRectangles()){

					TiledObject[] objects = tiles.get("" + i).objectgroup.objects;
					collisionRectangles = new Rectangle[objects.length];
					
					for(int n = 0; n < collisionRectangles.length; n++){
						TiledObject current = objects[n];
						collisionRectangles[n] = new Rectangle(current.x, current.y, current.width, current.height);
					}
				}
				
				//if the tile has a custom animation
				if(tiles.get("" + i).hasAnimation()){
				
					//repackage the loaded information
					TiledTile loadedTile = tiles.get("" + i);
					
					int[] animationFrameTimesMillis = new int[loadedTile.animation.length];
					int[] translatedAnimationTextures = new int[animationFrameTimesMillis.length];
					
					for(int n = 0; n < animationFrameTimesMillis.length; n++){
						animationFrameTimesMillis[n] = loadedTile.animation[n].duration;
						translatedAnimationTextures[n] = textureStorage.translateToGlobalTextureId(name, firstgid + loadedTile.animation[n].tileid -1);
					}
					
					tileAnimation = new Animation(translatedAnimationTextures, animationFrameTimesMillis);
				}
			}
			
			if(tileAnimation == null)
				//make a single frame animation that does not update
				tileAnimation = new Animation(new int[]{globalId}, new int[]{-1});
			
			//store the tile at the index for the start texture
			tileStorage.addTile(new Tile(tileAnimation, isSolid, collisionRectangles), globalId);
		}
	}
}
