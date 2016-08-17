package de.eternity.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.eternity.demo.DemoLauncher;

public class Sound {
	
	public static final boolean 
		LOOPING = true,
		PLAYING = false;
	
	private Clip clip;
	
	private boolean playState = PLAYING;
	
	private BooleanControl mute;
	private FloatControl
		pan,
		balance,
		gain;
	
	public Sound(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(DemoLauncher.class.getResource(path));
		
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
	
	/**
	 * +6 to -80
	 * @param gain
	 */
	public void setGain(float gain){
		this.gain.setValue(gain);
	}
	
	/**
	 * -1 to 1
	 * @param pan
	 */
	public void setPan(float pan){
		this.pan.setValue(pan);
	}
	
	/**
	 * -1 to 1
	 * @param balance
	 */
	public void setBalance(float balance){
		this.balance.setValue(balance);
	}
	
	public void loop(){
		
		reset();
		
		playState = LOOPING;
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * 
	 */
	public void finishLoop(){
		
		if(clip.isRunning()){
			
			clip.stop();
			clip.start();
		}
	}
	
	public void play(){
		
		reset();
		playState = PLAYING;
		clip.start();
	}
	
	private void reset(){
		
		if(clip.isRunning())
			clip.stop();
		
		clip.getFramePosition();//somehow this is needed
		clip.setFramePosition(0);
	}
	
	public void switchPause(){
		
		if(clip.isRunning())
			clip.stop();
		
		else{
			if(playState == LOOPING)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		super.finalize();
		clip.close();
	}
}
