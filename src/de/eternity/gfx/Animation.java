/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

/**
 * This class represents an animation.
 * An animation contains the ids of one or more textures and their respective display time.
 * Animations can be loaded from toml files within the dedicated animation directory.
 * @author Julian Sven Baehr
 *
 */
public class Animation {
	
	/**
	 * This class handles the timing of an animation.
	 * @author Julian Sven Baehr
	 *
	 */
	public class AnimationTimer{
		
		private double[] frameTimesSeconds;

		private transient int currentTexture = 0;
		private transient double unaccountedTimeSeconds = 0;
		private transient Animation enclosingThis = null;
		
		/**
		 * Creates a new AnimationTimer instance.
		 * @param animationFrameTimesMillis An array of the milliseconds for each animatino frame.
		 */
		public AnimationTimer(int[] animationFrameTimesMillis){
			
			frameTimesSeconds = new double[animationFrameTimesMillis.length];
			for(int i = 0; i < frameTimesSeconds.length; i++)
				frameTimesSeconds[i] = animationFrameTimesMillis[i] / 1000d;
		}
		
		/**
		 * Updates the animation.
		 * @param delta The time since the last call to this method.
		 */
		private void update(double delta){
			
			//only if there are more than 1 frames in the animation
			if((Animation.this == null ? enclosingThis : Animation.this).textureCount > 1){
				//add delta to unaccounted time
				unaccountedTimeSeconds += delta;
				
				boolean isSkipping = true;
				int skippedFrames = 0;
				//calculate how many frames are to be skipped
				while(isSkipping){
					
					//calculate if the next frame has been reached
					double timeForNextFrame = frameTimesSeconds[(currentTexture + skippedFrames) % (Animation.this == null ? enclosingThis : Animation.this).textureCount];
					if(unaccountedTimeSeconds > timeForNextFrame){
						skippedFrames++;
						unaccountedTimeSeconds -= timeForNextFrame;
					}else
						isSkipping = false;
				}
				
				//update current texture id
				currentTexture = (currentTexture + skippedFrames) % (Animation.this == null ? enclosingThis : Animation.this).textureCount;
			}
		}
	}
	
	//non saved variables
//	private transient boolean isInitialized = false;
	private transient int textureCount;

	//saved and loaded variables
	private int[] textureIds;
	private String tileset = null;
	
	private AnimationTimer animationTimer;
	
	/**
	 * Creates a new animation.
	 * @param textureIds An array with the ids for each animation frame.
	 * @param animationFrameTimesMillis An array of the milliseconds for each animation frame.
	 */
	public Animation(int[] textureIds, int[] animationFrameTimesMillis){
		this.textureIds = textureIds;
		textureCount = textureIds.length;
		this.animationTimer = new AnimationTimer(animationFrameTimesMillis);
	}

	//	/**
//	 * Initializes the animation.
//	 * @param textureStorage
//	 */
//	public synchronized void init(TextureStorage textureStorage){
//		//only initialize once
//		if(!isInitialized){
//			isInitialized = true;
//			
//			textureCount = textureIds.length;
//			
//			//translate the texture ids
//			for(int i = 0; i < textureIds.length; i++)
//				textureIds[i] = textureStorage.translateToGlobalTextureId(tileset, i);
//		}
//	}
	
	/**
	 * Initializes the animation.
	 * @param textureStorage The global texture storage.
	 * @return The animation itself.
	 */
	public Animation init(TextureStorage textureStorage){
		
		//translate from local ids to global ids only if is loaded from toml file
		if(tileset != null){
			textureCount = textureIds.length;
			
			animationTimer.enclosingThis = this;
			
			for(int i = 0; i < textureIds.length; i++)
				textureIds[i] = textureStorage.translateToGlobalTextureId(tileset, textureIds[i]);
		}
		
		return this;
	}
	
	/**
	 * Updates the animation.
	 * @param delta The time since the last call to this method.
	 */
	public void update(double delta){
		animationTimer.update(delta);
	}
	
	/**
	 * @param textureStorage The global texture storage.
	 * @return The current texture/animation frame of this animation.
	 */
	public Texture getCurrentTexture(TextureStorage textureStorage){
		return textureStorage.getTexture(textureIds[animationTimer.currentTexture]);
	}
	
	/**
	 * @return The global id of the current animation frame/texture.
	 */
	public int getCurrentTextureId(){
		return textureIds[animationTimer.currentTexture];
	}
}
