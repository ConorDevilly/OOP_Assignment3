package com.conordevilly.ocr.imageprocessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Testing {
	public static void main(String[] args){
		Testing t = new Testing();
	}
	
	Testing(){
		try {
			BufferedImage input = ImageIO.read(new File("input.png"));
			input = ImageProcessor.extractChar(input);
			//input = ImageProcessor.scale(input, 250, 250);
			//ImageIO.write(input, "png", new File("output.png"));
			ArrayList<BufferedImage> listChars = ImageProcessor.extractIndivChar(input);
			for(int i = 0; i < listChars.size(); i++){
				ImageIO.write(listChars.get(i), "png", new File(i + ".png"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
