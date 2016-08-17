package de.eternity.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardAdapter implements KeyListener{

	private ButtonInput buttonInput;
	
	public KeyboardAdapter(ButtonInput buttonInput){
		this.buttonInput = buttonInput;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		buttonInput.setBuffer(e.getKeyCode(), ButtonInput.STATE_PRESSED);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		buttonInput.setBuffer(e.getKeyCode(), ButtonInput.STATE_RELEASED);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//ignored
	}
}
