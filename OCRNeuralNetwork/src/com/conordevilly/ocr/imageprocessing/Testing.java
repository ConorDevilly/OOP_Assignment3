package com.conordevilly.ocr.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Testing {
	public static void main(String[] args){
		Testing t = new Testing();
	}
	
	public Testing(){
		try {
			BufferedImage test = ImageIO.read(new File(getClass().getResource("/TestGS.png").toURI()));
			BufferedImage out = ImageProcessor.greyScale(test);
			//ImageIO.write(out, "png", new File(getClass().getResource("/out.png").toURI()));
			ImageIO.write(out, "png", new File("out.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
