package com.conordevilly.ocr.imageprocessing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageProcessor {
	
	/*
	BufferedImage originImage;
	
	public ImageProcessor(File origin){
		try {
			originImage = ImageIO.read(origin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	private BufferedImage imgIn;
	private ArrayList<Pixel> traverseList;
	
	public ImageProcessor(File input){
		try {
			imgIn = ImageIO.read(input);
			traverseList = new ArrayList<Pixel>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getChar(){
		Character c = new Character(traverseList);
		BufferedImage extractedCharacter = imgIn.getSubimage(c.getMinX(), c.getMinY(), c.getWidth(), c.getHeight());
		return extractedCharacter ;
	}
	
	public void normalize(){
		//Create new 10 x 10 image
		BufferedImage normalized = new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_BINARY);
		//The character we want to normalize
		BufferedImage subChar = this.getChar();
		
		int scaleX = subChar.getWidth() * normalized.getWidth();
		int scaleY = subChar.getHeight() * normalized.getHeight();
		
		//Temp large image used to scale
		BufferedImage scaler = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_BYTE_BINARY);
		
		//Scale subChar up to scaler size by duplicating rows & cols
		//Rows
		for(int i = 0; i < scaleX; i += subChar.getWidth()){
			//Cols
			for(int j = 0; j < scaleY; j += subChar.getHeight()){
			}
		}
	}
	
	public BufferedImage getImg(){
		return imgIn;
	}
	
	public void setImg(BufferedImage in){
		imgIn = in;
	}

	//Convert image to binary (black / white) format
	public static BufferedImage binarise(BufferedImage in){
		//Create an image of the same height & width in Binary (0 or 255) mode
		BufferedImage greyImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		//Draw our input onto our current image
		Graphics g2d = greyImage.createGraphics();
		g2d.drawImage(in, 0, 0, null);
		g2d.dispose();
				
		return greyImage;
	}
	
	public void binarise(){
		//Create an image of the same height & width in Binary (0 or 255) mode
		BufferedImage greyImage = new BufferedImage(imgIn.getWidth(), imgIn.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		//Draw our input onto our current image
		Graphics g2d = greyImage.createGraphics();
		g2d.drawImage(imgIn, 0, 0, null);
		g2d.dispose();
		
		imgIn = greyImage;
	}
	
	public int[] convertToNumbers(BufferedImage in){
		int[] ret = new int[100];
		
		for(int i = 0; i < in.getWidth(); i++){
			for(int j = 0; j < in.getHeight(); j++){
				ret[(j * 10) + i] = (pixelAt(in, i, j)) ? 1 : 0;
			}
		}
		
		return ret;
	}
	
	public void extractChars(){
		for(int i = 0; i < imgIn.getWidth(); i++){
			for(int j = 0; j < imgIn.getHeight(); j++){
				if(pixelAt(i, j)){
					traverse(new Pixel(i, j));
				}
			}
		}
	}

	public void traverse(Pixel p){
		if(pixelAt(p.getX(), p.getY())){
			if(!traverseList.contains(p)){
				traverseList.add(p);
			}
		}
	}
	
	//Edge detection causes a stack overflow error.
	public void edgeDetection(Pixel p){
		traverseList.add(p);
		
		//Array of adjacent pixels
		Pixel[] adjacent = new Pixel[8];
		adjacent[0] = new Pixel(p.getX() - 1, p.getY() - 1);
		adjacent[1] = new Pixel(p.getX() - 1, p.getY());
		adjacent[2] = new Pixel(p.getX(), p.getY() - 1);
		adjacent[3] = new Pixel(p.getX() + 1, p.getY() + 1);
		adjacent[4] = new Pixel(p.getX() + 1, p.getY());
		adjacent[5] = new Pixel(p.getX(), p.getY() + 1);
		adjacent[6] = new Pixel(p.getX() + 1, p.getY() - 1);
		adjacent[7] = new Pixel(p.getX() - 1, p.getY() + 1);
		
		//Check each adjacent pixel
		for(Pixel adj : adjacent){
			if(pixelAt(adj.getX(), adj.getY())){
				if(!traverseList.contains(adj)){
					edgeDetection(adj);
				}
			}
		}
	}
	
	public Boolean pixelAt(BufferedImage img, int x, int y){
		Color c = new Color(img.getRGB(x, y));
		return c.equals(new Color(0, 0, 0));
	}
	
	public Boolean pixelAt(int x, int y){
		Color c = new Color(imgIn.getRGB(x, y));
		return c.equals(new Color(0, 0, 0));
	}
	
	//Dodgy replacment to normalize
	public BufferedImage scale(){
		Image scaledChar = this.getChar().getScaledInstance(10, 10, Image.SCALE_DEFAULT);
		BufferedImage scale = new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_BINARY);
		
		//Draw the scaledChar onto scale (basically casting an Image to BufferedImage
		Graphics2D g2d = scale.createGraphics();
		g2d.drawImage(scaledChar, 0, 0, null);
		g2d.dispose();
		
		return scale;
	}
	
	// Y = MX + C
	// M = (nExiyi - ExiEyi) / (nExi2 - (Exi)2)
	public void correctSkew(){
		AffineTransform transformer = new AffineTransform();
		
		
		
	}
}
