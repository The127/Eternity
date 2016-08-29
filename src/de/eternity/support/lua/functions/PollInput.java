/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.support.lua.functions;

import java.awt.MouseInfo;
import java.awt.Point;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.eternity.gui.Display;
import de.eternity.gui.GameScene;
import de.eternity.input.ButtonInput;

/**
 * Polls the keyboard for button states.
 * @author Julian Sven Baehr
 * @see ButtonInput#flip()
 */
public class PollInput extends ZeroArgFunction{

	private final ButtonInput keyboard, mouse;
	private final GameScene gameScene;
	
	public static int m_x, m_y;
	
	public PollInput(ButtonInput keyboard, ButtonInput mouse, Display display) {
		this.keyboard = keyboard;
		this.mouse = mouse;
		this.gameScene = display.getScene();
	}
	
	@Override
	public LuaValue call() {
		try {
			
			//calculate mouse position
			Point framePoint = gameScene.getLocationOnScreen();
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			mousePoint.translate(-framePoint.x, -framePoint.y);
			m_x = mousePoint.x;
			m_y = mousePoint.y;
			
			//flip buffers
			keyboard.flip();
			mouse.flip();
		} catch (InterruptedException e) {
			//should never happen
			//TODO: logging
			e.printStackTrace();
		}
		return null;
	}

}
