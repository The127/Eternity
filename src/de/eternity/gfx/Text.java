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
	
	private boolean hasChanged = false;
	
	public Text(int horizontalChars, int verticalChars, int textColor, GameData gameData) {
		super(horizontalChars*gameData.getSettings().getTextWidth(), 
				(verticalChars == 1 ? gameData.getSettings().getTextHeight() : (verticalChars*(gameData.getSettings().getTextHeight() +1) - 1))
				/*include spacing between lines*/);

		this.textColor = textColor & 0x00FFFFFF;//get rid of alpha
		this.gameData = gameData;
		
		//safe buffer
		buffer2 = buffer;
	}
	
	public Text(String oneLine, int textColor, GameData gameData){
		this(oneLine.length(), 1, textColor, gameData);
		setText(oneLine);
		update();
	}
	
	public void setTextColor(int textColor){
		this.textColor = textColor & 0x00FFFFFF;//get rid of alpha
		hasChanged = true;
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
			
			int drawWidth = 0, drawHeight = 0;
			int cWidth = gameData.getSettings().getTextWidth();
			int cHeight = gameData.getSettings().getTextHeight();
			
			drawStringLoop : for(int i = 0; i < chars.length; i++){
				
				char c = chars[i];
				
				//non drawable character
				if(c < 32 || c > 126){
					if(c == '\n'){
						//no more space downwards
						if(drawHeight == this.height)
							break drawStringLoop;

						//new line
						drawHeight += 1 + cHeight;
						drawWidth = 0;
					}else if(c == '\t'){
						
						if(drawWidth == this.width){
							drawHeight += 1 + cHeight;
							drawWidth = 0;
							
							//still space?
							if(drawHeight >= this.height)
								break drawStringLoop;
						}

						//a tab is at most 3 white spaces
						int tab =  3 - (drawWidth / cWidth) % 3;
						
						tabLoop : for(int n = 0; n < tab; n++){

							drawWidth += cWidth;
							//new line?
							if(drawWidth == this.width){
								drawHeight += 1 + cHeight;
								drawWidth = 0;
								
								//still space?
								if(drawHeight >= this.height)
									break drawStringLoop;
								
								//if new line in tab then stop tab
								break tabLoop;
							}
						}
					}
				}else{
					//new line?
					if(drawWidth == this.width){
						drawHeight += 1 + cHeight;
						drawWidth = 0;
						
						//still space?
						if(drawHeight >= this.height)
							break drawStringLoop;
					}
					
					//draw char
					Texture charTex = gameData.getTextureStorage().getTexture(chars[i] - 32);
					for(int y = 0; y < cHeight; y++)
						for(int x = 0; x < cWidth; x++)
							buffer[x + drawWidth + (y + drawHeight) * width] = 
									textColor | charTex.buffer[x + y * cWidth];
					
					drawWidth += cWidth;
				}
				
				//hit end of text area
				if(drawWidth == this.width && drawHeight == this.height)
					break drawStringLoop;
			}
		}
	}
	
	public void clear(){
		
		//clear image (invisible)
		Arrays.fill(buffer, 0x00000000);
		
		//for faster rendering set buffer temporary to null (invisible flag)
		buffer = null;
		
		//clear flag
		hasChanged = false;
	}
}
