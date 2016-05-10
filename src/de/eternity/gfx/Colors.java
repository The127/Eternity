package de.eternity.gfx;

public class Colors {

	public static int getColorValue(int a, int r, int g, int b){
		
		return (a<< 24) | (r << 16) | (g << 8) | b;
	}
	
	public static int getColorValue(int r, int g, int b){
		
		return (255<<25) | (r << 16) | (g << 8) | b;
	}
}
