/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity.gfx;

import java.util.Arrays;

import de.eternity.GameData;

public class Text extends Texture{

	private int textColor;
	private String text;
	
	private GameData gameData;
	
	private int[] buffer2;
	
	private int maxChars = 0;
	
	private boolean hasChanged = true;
	
	public Text(int horizontalChars, int verticalChars, int textColor, GameData gameData) {
		super(horizontalChars*8, (verticalChars == 1 ? 10 : verticalChars*11 - 1)/*include spacing between lines*/);
		
		this.textColor = textColor & 0x00FFFFFF;//get rid of alpha
		this.gameData = gameData;
		
		maxChars = horizontalChars*verticalChars;
		//safe buffer
		buffer2 = buffer;
		
		update();
	}
	
	public Text(String oneLine, int textColor, GameData gameData){
		this(1, oneLine.length(), textColor, gameData);
		text = oneLine;
	}
	
	public void setTextColor(int textColor){
		this.textColor = textColor & 0x00FFFFFF;//get rid of alpha
		hasChanged = true;
		maxChars = text.length();
	}
	
	public void setText(String text){
		this.text = text;
		hasChanged = true;
	}
	
	public void update(){
		if(hasChanged){
			clear();
			//restore buffer it has been set to null
			buffer = buffer2;
			//clear flag
			hasChanged = false;
			
			char[] chars = text.toCharArray();
			
			int width = 0, height = 0;
			for(int i = 0; i < chars.length && i < maxChars; i++){
				Texture character = gameData.getTextureStorage().getTexture(chars[i] - 32);
				
				int cWidth = character.getWidth();
				int cHeight = character.getHeight();
				
				for(int x = 0; x < cWidth; x++)
					for(int y = 0; y < cHeight; y++)
						buffer[x + width + (y + height) * cWidth] = textColor | character.buffer[x + y * cWidth];
			}
		}
	}
	
	public void clear(){
		
		//for faster rendering set buffer temporary to null (invisible flag)
		buffer = null;
		
		//clear flag
		hasChanged = false;
		
		//clear image (invisible)
		Arrays.fill(buffer, 0x00000000);
	}
}
