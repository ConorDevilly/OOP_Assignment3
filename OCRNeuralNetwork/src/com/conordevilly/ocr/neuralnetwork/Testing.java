package com.conordevilly.ocr.neuralnetwork;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import com.conordevilly.ocr.imageprocessing.ImageProcessor;

public class Testing {
	public static void main(String args[]){
		Testing t = new Testing();
	}
	
	Testing(){
		//Create 26 output neurons, one for each letter
		//Create 100 input neurons, one for each pixel of our preprocessed images
		//Input Ns -> Output Ns -> Percentage chance
		//TODO: if letter exists, read. Else, initalise it

		try{
			File weightDir = new File(getClass().getResource("/Weights").toURI());
			BufferedReader biasReader = new BufferedReader(new FileReader(new File(getClass().getResource("/LetterFrequency.txt").toURI())));

			for(File f : weightDir.listFiles()){
				ImageProcessor ip = new ImageProcessor(f);
				ip.binarise();
				ip.extractChars();
				BufferedImage out = ip.scale();

				String[] line = biasReader.readLine().split(":");
				float bias = Float.parseFloat(line[1].trim());
				int[] weights = ip.convertToNumbers(out);

				//TODO: Check input at biasReader
				OutputNeuron n = new OutputNeuron(f.getName().charAt(0), bias, weights);
				System.out.println(n.toString());
				PersistanceManager.write(n);
			}
			
			biasReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
