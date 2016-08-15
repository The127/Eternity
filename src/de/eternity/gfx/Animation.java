package de.eternity.gfx;

public class Animation {
	
	public class AnimationTimer{
		
		private double[] frameTimesSeconds;

		private transient int currentTexture = 0;
		private transient double unaccountedTimeSeconds = 0;
		
		public AnimationTimer(int[] animationFrameTimesMillis){
			
			frameTimesSeconds = new double[animationFrameTimesMillis.length];
			for(int i = 0; i < frameTimesSeconds.length; i++)
				frameTimesSeconds[i] = animationFrameTimesMillis[i] / 1000d;
		}
		
		private void update(double delta){
			
			//only if there are more than 1 frames in the animation
			if(Animation.this.textureCount > 1){
				//add delta to unaccounted time
				unaccountedTimeSeconds += delta;
				
				boolean isSkipping = true;
				int skippedFrames = 0;
				//calculate how many frames are to be skipped
				while(isSkipping){
					
					//calculate if the next frame has been reached
					double timeForNextFrame = frameTimesSeconds[(currentTexture + skippedFrames) % Animation.this.textureCount];
					if(unaccountedTimeSeconds > timeForNextFrame){
						skippedFrames++;
						unaccountedTimeSeconds -= timeForNextFrame;
					}else
						isSkipping = false;
				}
				
				//update current texture id
				currentTexture = (currentTexture + skippedFrames) % Animation.this.textureCount;
			}
		}
	}
	
	//non saved variables
//	private transient boolean isInitialized = false;
	private transient int textureCount;

	//saved and loaded variables
	private int[] textureIds;
	
	private AnimationTimer animationTimer;
	
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
	
	public void update(double delta){
		animationTimer.update(delta);
	}
	
	public Texture getCurrentTexture(TextureStorage textureStorage){
		return textureStorage.getTexture(textureIds[animationTimer.currentTexture]);
	}
}
