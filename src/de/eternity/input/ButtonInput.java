/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.input;

/**
 * Handles button related input.
 * @author Julian Sven Baehr
 *
 */
public class ButtonInput {

	public static final boolean STATE_PRESSED = true, STATE_RELEASED = false;
	
	private boolean[] buffer, active;
	private Object eventLock = new Object(){};
	
	/**
	 * Creates a new button input instance.
	 * @param buttonCount The amount of buttons.
	 */
	public ButtonInput(int buttonCount){
		buffer = new boolean[buttonCount];
		active = new boolean[buttonCount];
	}
	
	/**
	 * Flips the buffer of currently pressed keys.
	 * Must be called once every game tick.
	 * @throws InterruptedException
	 */
	public void flip() throws InterruptedException{
		
		//use the atomic integer value as a lock
		synchronized (eventLock) {
			for(int i = 0; i < buffer.length; i++)
				active[i] = buffer[i];
		}
	}
	
	/**
	 * Sets the buffer at the key to the given state.
	 * @param key The key.
	 * @param state The keys state.
	 */
	void setBuffer(int key, boolean state){
		
		//for listeners there will always only be one thread in here
		//therefore the simple synchronized(eventLock) is the most efficient way
		//to synchronize between reading/flipping and writinga
		synchronized (eventLock) {
			//update the button state
			if(key >= 0 && key < buffer.length)
				buffer[key] = state;
		}
	}
	
	/**
	 * @param key The key.
	 * @return True if the key is pressed.
	 */
	public boolean isPressed(int key){
		
		if(key >= 0 && key < buffer.length)
			return active[key];
		
		else
			//this is true since the button does not exist or is not accounted for
			return false;
	}
}
