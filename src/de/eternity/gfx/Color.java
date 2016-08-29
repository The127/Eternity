/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

/**
 * Color function class.
 * @author Julian Sven Baehr
 *
 */
public class Color {

	/**
	 * Keep in mind that only the first byte of each integer is used.
	 * @param a The alpha value.
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 * @return The created color from the argb values.
	 */
	public static final int argbToColor(int a, int r, int g, int b){
		a &= 0xFF;
		r &= 0xFF;
		g &= 0xFF;
		b &= 0xFF;
		return (a<<24)|(r<<24)|(g<<24)|b;
	}
}
