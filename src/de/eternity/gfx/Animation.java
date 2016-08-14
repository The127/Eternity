package de.eternity.gfx;

public class Animation {
	
	private class AnimationTimer{
		
		private int currentTexture;
		private double[] frameTimesSeconds;
		
		private transient double unaccountedTimeSeconds;
		
		private void update(double delta){
			
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
				}
			}
			
			//update current texture id
			currentTexture = (currentTexture + skippedFrames) % Animation.this.textureCount;
		}
	}
	
	//non saved variables
	private transient boolean isInitialized = false;
	private transient int textureCount;

	//saved and loaded variables
	private String tileset;
	private int[] textureIds;
	
	private AnimationTimer animationTimer;
	
	/**
	 * Initializes the animation.
	 * @param textureStorage
	 */
	public synchronized void init(TextureStorage textureStorage){
		//only initialize once
		if(!isInitialized){
			isInitialized = true;
			
			textureCount = textureIds.length;
			
			//translate the texture ids
			for(int i = 0; i < textureIds.length; i++)
				textureIds[i] = textureStorage.translateToGlobalTextureId(tileset, i);
		}
	}
	
	public void update(double delta){
		animationTimer.update(delta);
	}
	
	public Texture getCurrentTexture(TextureStorage textureStorage){
		return textureStorage.getTexture(animationTimer.currentTexture);
	}
}
