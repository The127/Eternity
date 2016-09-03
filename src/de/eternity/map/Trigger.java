/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

import java.util.ArrayList;

import org.luaj.vm2.LuaFunction;

public class Trigger {

	private int x, y, w, h;
	
	private ArrayList<LuaFunction> onEnter = new ArrayList<>();
	private ArrayList<LuaFunction> onExit = new ArrayList<>();
	
	public Trigger(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.y = y;
	}
	
	public void fireOnEnter(){
		for(LuaFunction f : onEnter)
			f.call();
	}
	
	public void fireOnExit(){
		for(LuaFunction f : onExit)
			f.call();
	}
	
	public int registerOnEnter(LuaFunction onEnter){
		this.onEnter.add(onEnter);
		return this.onEnter.size()-1;
	}

	public void unregisterOnEnter(int index){
		this.onEnter.remove(index);
	}
	
	public int registerOnExit(LuaFunction onExit){
		this.onExit.add(onExit);
		return this.onEnter.size()-1;
	}
	
	public void unregisterOnExit(int index){
		this.onExit.remove(index);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	
}
