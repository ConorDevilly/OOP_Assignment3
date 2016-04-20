package com.conordevilly.ocr.imageprocessing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * ImageProcessor class
 * Used to prepare images before running them through the Neural Network
 * An image is fed into the processor which then returns list of numbers representing the image.
 * The proccess of converting the image to numbers involves:
 * 	Converting Images to black and white
 * 	Extracting characters
 * 	Converting image to int arrays based on each pixel: 0 if a pixel is white, 1 if black	
 */
public class ImageProcessor {
	
	//Run all methods in this class in order on a given image
	public static ArrayList<Integer> process(BufferedImage input, int scaleSize){
		BufferedImage binarised = binarise(input);
		BufferedImage extracted = extractChar(binarised);
		BufferedImage scaled = scale(extracted, scaleSize, scaleSize);
		ArrayList<Integer> numbericRep = convertToNumbers(scaled);
		return numbericRep;
	}
	
	//Convert an image to black & white
	public static BufferedImage binarise(BufferedImage input){
		//Create a new canvas only allowing black / white
		BufferedImage blackWhiteImg = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		//Paint the existing image
		Graphics g2d = blackWhiteImg.createGraphics();
		g2d.drawImage(input, 0, 0, null);
		g2d.dispose();
		return blackWhiteImg;
	}
	
	/*
	 * Character extraction algorithm:
	 * Scan each column.
	 * If a pixel exists in a column, set pixFound = true
	 * Once a row has been scanned, set prev = pixFound
	 * If a pixel had been found, but none was found in the row before that, it means that an entire character has been scanned
	 * If that is the case, create a subimage of the character scanned and add it to the list
	 */
	public static ArrayList<BufferedImage> extractIndivChar(BufferedImage input){
		ArrayList<BufferedImage> ret = new ArrayList<BufferedImage>();
		boolean pixFound;
		boolean prev = true; //Images should have marginal whitespace removed => for first col: pixFound == true
		int lastX = 0;

		//Go through each column
		for(int i = 0; i < input.getWidth(); i++){
			pixFound = false;

			//Scan the column and see if a black pixel is found
			for(int j = 0; j < input.getHeight(); j++){
				pixFound = (pixelAt(input, i, j)) ? true : pixFound;
			}

			//If a pixel is found, but none has been found in the previous col, make a new subimg
			if((pixFound == true) && (pixFound != prev)){
				ret.add(input.getSubimage(lastX, 0, (i - lastX), input.getHeight()));
				lastX = i;
			}

			prev = pixFound;
		}
		
		//Add the remaining character to the list
		ret.add(input.getSubimage(lastX, 0, (input.getWidth() - lastX), input.getHeight()));
		
		return ret;
	}
	
	//Extracts a character from an image
	public static BufferedImage extractChar(BufferedImage input){
		ArrayList<Pixel> traverseList = new ArrayList<Pixel>();
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int height = 0;
		int width = 0;

		//Create a list of pixels. Also store the min & max pixels in the list
		for(int i = 0; i < input.getWidth(); i++){
			for(int j = 0; j < input.getHeight(); j++){
				//Check if there's a black pixel at i, j
				if(pixelAt(input, i, j)){
					
					//If there is, add it to the traverse list
					Pixel p = new Pixel(i, j);
					if(!traverseList.contains(p)){
						//Add the pixel to the list
						traverseList.add(p);
						
						//Check if the pixel is a min or max value
						minX = (p.x < minX) ? p.x : minX;
						minY = (p.y < minY) ? p.y : minY;
						maxX = (p.x > maxX) ? p.x : maxX;
						maxY = (p.y > maxY) ? p.y : maxY;
					}
				}
			}
		}
		
		width = maxX - minX;
		height = maxY - minY;
		
		//Return a sub image that contains the exact dimensions of the extracted character
		return input.getSubimage(minX, minY, width, height);
	}
	
	//Scales an image down
	public static BufferedImage scale(BufferedImage input, int scaleX, int scaleY){
		//Scale the imge
		Image scaledChar = input.getScaledInstance(scaleX, scaleY, Image.SCALE_DEFAULT);

		//Create an image with buffered inputted width & height
		BufferedImage scale = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_BYTE_BINARY);
		
		//Paint the scaled image onto the new one
		//Essentially just converting an Image to a BufferedImage
		Graphics2D g2d = scale.createGraphics();
		g2d.drawImage(scaledChar, 0, 0, null);
		g2d.dispose();
		
		return scale;
	}

	//Returns true if the pixel at a given coord is black, else false
	public static Boolean pixelAt(BufferedImage img, int x, int y){
		Color c = new Color(img.getRGB(x, y));
		return c.equals(new Color(0, 0, 0));
	}
	
	//Takes an image as input and returns an arraylist of integers. If a given pixel is black, its representation in the array list is a 1. Else its a 0.
	public static ArrayList<Integer> convertToNumbers(BufferedImage input){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		//Go through the image pixel by pixel
		for(int i = 0; i < input.getWidth(); i++){
			for(int j = 0; j < input.getHeight(); j++){
				//If the pixel is black, add a one to the list. Else add a 0
				ret.add((pixelAt(input, i, j)) ? 1 : 0);
			}
		}
		return ret;
	}
	
	/* Unused. Left in because it could be a useful method going forward
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
	*/
	
	/* Unused method. Could be useful going forward
	// Y = MX + C
	// M = (nExiyi - ExiEyi) / (nExi2 - (Exi)2)
	public static BufferedImage correctSkew(BufferedImage input){
		//Could use AffineTransformation to try this
		return input;
	}
	*/
}