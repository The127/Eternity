package de.eternity.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A Texture represents the color data and the dimensional data of an image.
 * See the Textures class for utility methods.
 * @author Julian Sven Baehr
 */
public class Texture {

	public static final String GENERATED = "GENERATED";
	
	private int[] buffer = null;
	private int width = -1, height = -1;
	
	private String path = GENERATED;
	
	/**
	 * Loads a texture from an image file.
	 * @param path The path to the image.
	 * @throws IOException Thrown by ImageIO.read.
	 */
	public Texture(String path) throws IOException{
		
		this.path = path;
		
		BufferedImage tempImage = ImageIO.read(Texture.class.getResource(path));
		
		width = tempImage.getWidth();
		height = tempImage.getHeight();
		
		buffer = tempImage.getRGB(0, 0, width, height, null, 0, width);
	}
	
	/**
	 * Creates a new Texture with the given color as its color with the given dimensions.
	 * @param width The textures width.
	 * @param height The textures height.
	 * @param color The textures color.
	 */
	public Texture(int width, int height, int color){

		this(width, height);
		
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				buffer[x + y * width] = color;
	}
	
	/**
	 * Creates a new Texture with 0x0 as its color with the given dimensions.
	 * @param width The textures width.
	 * @param height The textures height.
	 */
	public Texture(int width, int height){

		this(new int[width * height], width, height);
	}
	
	/**
	 * Creates a new Texture with the specified buffer as this textures buffer and the given dimensions.
	 * @param buffer The textures color buffer.
	 * @param width The textures width.
	 * @param height The textures height.
	 */
	public Texture(int[] buffer, int width, int height){
		
		this.buffer = buffer;
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates a new Texture object with the specified BufferedImage as the drawing canvas. Changes to this texture will result in changes to the image. <br/>
	 * Make sure to pass a compatible TYPE_INT_RGB BufferedImage.
	 * @param canvas The drawing canvas and base image.
	 */
	public Texture(BufferedImage canvas){
		
		if(canvas.getType() != BufferedImage.TYPE_INT_ARGB && canvas.getType() != BufferedImage.TYPE_INT_RGB)
			throw new IllegalArgumentException("This framework does not support the image type: " + canvas.getType() + "!" + System.lineSeparator() + " Please use the methods Texture#createCompatibleImage(int, int) or Texture#convertToCompatibleImage(BufferedImage) to ensure compatibility.");
		
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		
		this.buffer = ((DataBufferInt)canvas.getRaster().getDataBuffer()).getData();
	}
	
	/**
	 * Creates a framework compatible image.
	 * @param width The image width.
	 * @param height The image height.
	 * @return A new framework compatible image.
	 */
	public static BufferedImage createCompatibleImage(int width, int height){
		
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public static BufferedImage convertToCompatibleImage(BufferedImage baseImage){
		
		BufferedImage result = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = result.getGraphics();
		
		g.drawImage(baseImage, 0, 0, baseImage.getWidth(), baseImage.getHeight(), null);
		g.dispose();
		
		return result;
	}
	
	public Texture copy(){
		
		int[] copyBuffer = new int[buffer.length];
		System.arraycopy(buffer, 0, copyBuffer, 0, buffer.length);
		
		return new Texture(copyBuffer, width, height);
	}
	
	/**
	 * @return The buffer of this texture.
	 */
	int[] getBuffer(){
		
		return buffer;
	}
	
	/**
	 * @return The width of the texture.
	 */
	public int getWidth(){
		
		return width;
	}
	
	/**
	 * @return The height of the texture.
	 */
	public int getHeight(){
		
		return height;
	}
	
	/**
	 * @return The path of this image or GENERATED if it has no path and was generated.
	 */
	public String getPath(){
		
		return path;
	}
}
