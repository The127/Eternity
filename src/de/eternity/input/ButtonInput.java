package de.eternity.input;

public class ButtonInput {

	public static final boolean STATE_PRESSED = true, STATE_RELEASED = false;
	
	private boolean[] buffer, active;
	private Object eventLock = new Object(){};
	
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
	
	public boolean isPressed(int key){
		
		if(key >= 0 && key < buffer.length)
			return active[key];
		
		else
			//this is true since the button does not exist or is not accounted for
			return false;
	}
}
