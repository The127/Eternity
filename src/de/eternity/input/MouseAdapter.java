/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseAdapter implements MouseListener{

	private ButtonInput buttonInput;
	
	public MouseAdapter(ButtonInput buttonInput) {
		this.buttonInput = buttonInput;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttonInput.setBuffer(e.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttonInput.setBuffer(e.getButton(), false);
	}
	
	//ignore these
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
