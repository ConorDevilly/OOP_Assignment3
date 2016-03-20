package com.conordevilly.ocr.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Testing {
	public static void main(String[] args){
		Testing t = new Testing();
	}
	
	public Testing(){
		try{
			ImageProcessor ip = new ImageProcessor(new File(getClass().getResource("/H.png").toURI()));
			ip.binarise();
			ip.extractChars();
			
			//BufferedImage out = ip.getChar();
			BufferedImage out = ip.scale();
			ImageIO.write(out, "png", new File("out.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
