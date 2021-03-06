/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.eternity.GameData;
import de.eternity.Launcher;

/**
 * A sound class.
 * It supports multiple controlls and directional/position based sound.
 * @author Julzenberger
 *
 */
public class Sound {
	
	public static final boolean 
		LOOPING = true,
		PLAYING = false;
	
	private Clip clip;
	
	private boolean isStopped = true;
	
	private float desiredVolume = 0, currentVolume;
	private boolean isDesiredVolume = true;
	private double increasePerSecond;
	
	private final float gainMaxMinusMin;
	
	private boolean playState = PLAYING, pauseState = false;
	
	private BooleanControl mute;
	private FloatControl
		pan,
		balance,
		gain;
	
	private final AudioInputStream audioIn;
	
	/**
	 * Loads a new sound into a sound object.
	 * @param path The path to the sound file.
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public Sound(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		
		audioIn = AudioSystem.getAudioInputStream(Launcher.class.getResource(path));
		
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		
		clip.addLineListener(new LineListener() {
			
			@Override
			public void update(LineEvent event) {
				if(event.getType() == Type.STOP)
					isStopped = true;
			}
		});
		
		mute = (BooleanControl)	clip.getControl(BooleanControl.Type.MUTE);
		
		pan 		= (FloatControl)	clip.getControl(FloatControl.Type.PAN);
		gain 		= (FloatControl)	clip.getControl(FloatControl.Type.MASTER_GAIN);
		balance 	= (FloatControl)	clip.getControl(FloatControl.Type.BALANCE);
		
		//assume that gain.max should be '0' because everything over zero will result in noise
		gainMaxMinusMin = -gain.getMinimum();
		currentVolume = gain.getValue();
	}
	
	/**
	 * @return True if the sound is stopped.
	 */
	public boolean isStopped(){
		return isStopped;
	}
	
	/**
	 * Updates the sound if it must be updated.
	 * @param delta The time since the last call to this method.
	 */
	public void update(double delta){
		
		if(!isDesiredVolume){
			
			float volume = (float) (currentVolume + delta*increasePerSecond);
			
			if(volume >= 0){
				volume = 0;
				isDesiredVolume = true;
			}else if(volume <= -gainMaxMinusMin){
				volume = -gainMaxMinusMin;
				isDesiredVolume = true;
				pause();
			}
			setGain(volume);
		}
	}
	
	/**
	 * @return True if this sound has reached its desired volume.
	 */
	public boolean isDesiredVolume(){
		return isDesiredVolume;
	}
	
	/**
	 * Slowly sets the desired volume for the specified amount of time.
	 * The sound will increase or decrease linearly.
	 * @param volume The desired volume in percent.
	 * @param seconds The time in seconds for it to reach the volume.
	 */
	public void setDesiredVolume(int volume, double seconds){
		desiredVolume = volume * gainMaxMinusMin / 100f + gain.getMinimum();

		if(seconds <= 0){
			isDesiredVolume = true;
			setGain(desiredVolume);
			pause();
		}else{
			
			//unpause if is paused because volume is too low
			if(volume <= 0 && pauseState == true)
				unpause();
			
			isDesiredVolume = false;
			increasePerSecond = (currentVolume + desiredVolume) / seconds;
		}
	}
	
	/**
	 * Mutes or unmutes the sound.
	 * @param mute The mute value (mute=true/unmute=false)
	 */
	public void mute(boolean mute){
		this.mute.setValue(mute);;
	}
	
	/**
	 * @return The current mute state (mute=true/unmute=false).
	 */
	public boolean getMute(){
		return this.mute.getValue();
	}
	
	/**
	 * Sets the sounds gain value.
	 * Automatically fixes the value to the minimum or maximum if these boundaries crossed.
	 * @param gain The gain value.
	 */
	private void setGain(float gain){
		
		if(gain > this.gain.getMaximum())
			this.gain.setValue(this.gain.getMaximum());
		
		else if(gain < this.gain.getMinimum())
			this.gain.setValue(this.gain.getMinimum());
		
		else
			this.gain.setValue(gain);
		
		currentVolume = this.gain.getValue();
	}
	
	/**
	 * @return The sounds current gain value.
	 */
	public float getGain(){
		return this.gain.getValue();
	}
	
	/**
	 * Sets the sounds pan value.
	 * Automatically fixes the value to the minimum or maximum if these boundaries crossed.
	 * @param pan The pan value.
	 */
	private void setPan(float pan){
		
		if(pan > this.pan.getMaximum())
			this.pan.setValue(this.pan.getMaximum());
		
		else if(pan < this.pan.getMinimum())
			this.pan.setValue(this.pan.getMinimum());
		
		else
			this.pan.setValue(pan);
	}
	
	/**
	 * @return The sounds current pan value.
	 */
	public float getPan(){
		return this.pan.getValue();
	}
	
	/**
	 * Sets the sounds balance value.
	 * Automatically fixes the value to the minimum or maximum if these boundaries crossed.
	 * @param balance The balance value.
	 */
	@SuppressWarnings("unused")
	private void setBalance(float balance){
		
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
	 * If the sound is already running it will be reset first.
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
		isStopped = false;
	}
	
	/**
	 * Resets the clip.
	 */
	private void reset(){

		isStopped = false;
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
		isStopped = true;
		if(clip.isRunning())
			clip.stop();
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		super.finalize();
		
		stop();
		isStopped = true;
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
	 * @param gameData The game data manager object.
	 */
	public void applyPosition(double soundX, double soundY, double originX, double originY, GameData gameData){
		
		//approximation
		if((int)soundX == (int)originX)
			soundX = originX;
		if((int)soundX == (int)originX)
			soundX = originX;
		
		//calcaulate vector
		double x = soundX - originX;
		double y = soundY - originY;
		
		//calculate the length of the vecor
		double length = Math.sqrt((x*x) + (y*y));
		//normalize the x value
		x /= length;
		
		//get the length in meters/units
		length /= gameData.oneMeter;
		
		//Set the gain to -length^2
		setGain((float)-(length*length));
		//Set the balance to the x value (-1f to 1f)
		setPan((float)x);
	}
}
