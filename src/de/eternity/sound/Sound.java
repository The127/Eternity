package de.eternity.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.eternity.Launcher;

public class Sound {
	
	public static final boolean 
		LOOPING = true,
		PLAYING = false;
	
	private Clip clip;
	
	private boolean playState = PLAYING, pauseState = false;
	
	private BooleanControl mute;
	private FloatControl
		pan,
		balance,
		gain;
	
	private final AudioInputStream audioIn;
	
	public Sound(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		
		audioIn = AudioSystem.getAudioInputStream(Launcher.class.getResource(path));
		
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		
		mute = (BooleanControl)	clip.getControl(BooleanControl.Type.MUTE);
		
		pan 		= (FloatControl)	clip.getControl(FloatControl.Type.PAN);
		gain 		= (FloatControl)	clip.getControl(FloatControl.Type.MASTER_GAIN);
		balance 	= (FloatControl)	clip.getControl(FloatControl.Type.BALANCE);
	}
	
	public void mute(boolean mute){
		this.mute.setValue(mute);;
	}
	
	public boolean getMute(){
		return this.mute.getValue();
	}
	
	/**
	 * +6 to -80 (auto fix to min or max if boundaries crossed)
	 * @param gain
	 */
	public void setGain(float gain){
		
		if(gain > this.gain.getMaximum())
			this.gain.setValue(this.gain.getMaximum());
		
		else if(gain < this.gain.getMinimum())
			this.gain.setValue(this.gain.getMinimum());
		
		else
			this.gain.setValue(gain);
	}
	
	public float getGain(){
		return this.gain.getValue();
	}
	
	/**
	 * -1 to 1 (auto fix to min or max if boundaries crossed)
	 * @param pan
	 */
	public void setPan(float pan){
		
		if(pan > this.pan.getMaximum())
			this.pan.setValue(this.pan.getMaximum());
		
		else if(pan < this.pan.getMinimum())
			this.pan.setValue(this.pan.getMinimum());
		
		else
			this.pan.setValue(pan);
	}
	
	public float getPan(){
		return this.pan.getValue();
	}
	
	/**
	 * -1 to 1 (auto fix to min or max if boundaries crossed)
	 * @param balance
	 */
	public void setBalance(float balance){
		
		if(balance > this.balance.getMaximum())
			this.balance.setValue(this.balance.getMaximum());
		
		else if(balance < this.balance.getMinimum())
			this.balance.setValue(this.balance.getMinimum());
		
		else
			this.balance.setValue(balance);
	}
	
	public float getBalance(){
		return this.balance.getValue();
	}
	
	/**
	 * Loops the sound continuously.
	 * If the sound is already running it will be stopped.
	 */
	public void loop(){
		
		reset();
		
		playState = LOOPING;
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Finishes the current play back.
	 */
	public void finishLoop(){
		
		if(clip.isRunning()){
			
			clip.stop();
			clip.start();
		}
	}
	
	/**
	 * Starts playing the sound from the beginning.
	 * If the sound is already running it will be stopped.
	 */
	public void play(){
		
		reset();
		playState = PLAYING;
		clip.start();
	}
	
	/**
	 * Resets the clip.
	 */
	private void reset(){

		pauseState = false;
		if(clip.isRunning())
			clip.stop();
		
		clip.getFramePosition();//somehow this is needed
		clip.setFramePosition(0);
	}
	
	/**
	 * If the clip is playing the clip will be paused.
	 */
	public void pause(){
		
		if(clip.isRunning()){
			clip.stop();
			pauseState = true;
		}
	}
	
	/**
	 * If the clip is not playing the clip will resume either playing or looping.
	 * This method will not do anything if the clip was not previously paused.
	 */
	public void unpause(){
		
		if(pauseState){//only start if it was paused
			
			pauseState = false;
			
			//loop or play
			if(playState == LOOPING)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
	}
	
	/**
	 * Stops the clip from playing.
	 */
	public void stop(){
		if(clip.isRunning())
			clip.stop();
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		super.finalize();
		
		stop();
		clip.close();
		clip.addLineListener(e -> {
			if(e.getType() == LineEvent.Type.CLOSE)
				try {
					audioIn.close();
				} catch (Exception exeption) {
					exeption.printStackTrace();
				}
		});
	}
	
	/**
	 * Applies a directional effect to the sound.
	 * @param soundX The sound x position in the world.
	 * @param soundY The sound y position in the world.
	 * @param originX The current center of the screen x.
	 * @param originY The current center of the screen y.
	 * @param renderQueue
	 */
	public void applyDirection(double soundX, double soundY, double originX, double originY){
		
		double x = soundX - originX;
		double y = soundY - originY;
		
		//calculate the length of the vecor
		double length = Math.sqrt((x*x) + (y*y));
		//normalize the x value
		x /= length;
//		y /= length;//y only usefull for 3d sound
		
		//Set the balance to the x value (-1f to 1f)
		setBalance((float)x);
	}
}
