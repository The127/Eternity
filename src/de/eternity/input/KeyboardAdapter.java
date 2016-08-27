/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.input;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 * A keyboard listener.
 * @author Julian Sven Baehr
 *
 */
public class KeyboardAdapter implements KeyEventDispatcher{

	private ButtonInput buttonInput;
	
	/**
	 * Creates a new keyboard adapter for the given button input.
	 * @param buttonInput
	 */
	public KeyboardAdapter(ButtonInput buttonInput){
		this.buttonInput = buttonInput;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() == KeyEvent.KEY_PRESSED)
			buttonInput.setBuffer(e.getKeyCode(), ButtonInput.STATE_PRESSED);
		else if(e.getID() == KeyEvent.KEY_RELEASED)
			buttonInput.setBuffer(e.getKeyCode(), ButtonInput.STATE_RELEASED);
		return false;
	}
}
