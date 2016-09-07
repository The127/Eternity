/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.map;

import java.util.ArrayList;

import org.luaj.vm2.LuaFunction;

public class Trigger {

	private int w, h;
	private float x, y;
	
	private ArrayList<LuaFunction> onEnter = new ArrayList<>();
	private ArrayList<LuaFunction> onExit = new ArrayList<>();
	private ArrayList<LuaFunction> onActivate = new ArrayList<>();
	
	public Trigger(float x, float y, int w, int h){
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
	
	public int registerOnActivate(LuaFunction onActivate){
		this.onActivate.add(onActivate);
		return this.onEnter.size()-1;
	}

	public void unregisterOnActivate(int index){
		this.onEnter.remove(index);
	}
	
	public int registerOnExit(LuaFunction onExit){
		this.onExit.add(onExit);
		return this.onEnter.size()-1;
	}
	
	public void unregisterOnExit(int index){
		this.onExit.remove(index);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	
}
