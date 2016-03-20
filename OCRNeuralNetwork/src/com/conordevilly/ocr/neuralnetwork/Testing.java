package com.conordevilly.ocr.neuralnetwork;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

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

		/*
		try{
			File weightDir = new File(getClass().getResource("/Weights").toURI());
			File[] files = weightDir.listFiles();
			Arrays.sort(files);
			BufferedReader biasReader = new BufferedReader(new FileReader(new File(getClass().getResource("/LetterFrequency.txt").toURI())));

			for(int i = 0; i < files.length; i++){
				File f = files[i];
				ImageProcessor ip = new ImageProcessor(f);
				ip.binarise();
				ip.extractChars();
				BufferedImage out = ip.scale();

				char name = f.getName().charAt(0);
				String[] line = biasReader.readLine().split(":");
				float bias = Float.parseFloat(line[1].trim());
				int[] weights = ip.convertToNumbers(out);

				//TODO: Check input at biasReader
				OutputNeuron n = new OutputNeuron(name, bias, weights);
				//System.out.println(n.toString());
				PersistanceManager.write(n);
			}
			
			biasReader.close();
			System.out.println("Wrote all files");
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		try{
			File neuronDir = new File("src/neurons/");
			for(File f : neuronDir.listFiles()){
				OutputNeuron n = (OutputNeuron) PersistanceManager.read(f);
				System.out.println(n.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
