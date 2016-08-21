package de.eternity.input;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class KeyboardAdapter implements KeyEventDispatcher{

	private ButtonInput buttonInput;
	
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
