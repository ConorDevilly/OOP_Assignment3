package com.conordevilly.ocr.imageprocessing;

import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
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

	//Convert to greyscale thenn apply threshold
	public static BufferedImage greyScale(BufferedImage in){
		//Create an image of the same height & width in Binary (0 or 255) mode
		BufferedImage greyImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		//Draw our input onto our current image
		Graphics g2d = greyImage.createGraphics();
		g2d.drawImage(in, 0, 0, null);
		g2d.dispose();
				
		return greyImage;
	}
	
	public void scale(){
		//image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	}
	
	public void correctSkew(){
		//TODO
	}
}
