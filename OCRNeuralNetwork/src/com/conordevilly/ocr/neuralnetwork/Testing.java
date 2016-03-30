package com.conordevilly.ocr.neuralnetwork;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
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

		try{
			File weightDir = new File(getClass().getResource("/Weights").toURI());
			File[] files = weightDir.listFiles();
			Arrays.sort(files);
			BufferedReader biasReader = new BufferedReader(new FileReader(new File(getClass().getResource("/LetterFrequency.txt").toURI())));
			OutputNeuron out = new OutputNeuron(26);

			for(int i = 0; i < files.length; i++){
				File f = files[i];
				BufferedImage img = ImageIO.read(f);


				char name = f.getName().charAt(0);
				String[] line = biasReader.readLine().split(":");
				float bias = Float.parseFloat(line[1].trim());
				out.addWeight(bias);
				
				ArrayList<Integer> weights = ImageProcessor.process(img, 10);
				ArrayList<Float> convWeights = new ArrayList<Float>();
				//Convert the weights to float before we can set them
				for(Integer w : weights){
					convWeights.add(w.floatValue());
				}
				//int[] weights = numbers.stream().mapToInt((Integer num) -> num.intValue()).toArray();

				//TODO: Check input at biasReader
				HiddenNeuron n = new HiddenNeuron(100, null);
				n.setWeights(convWeights);
				//System.out.println(n.toString());
				PersistanceManager.write(n, Integer.toString(i));
			}
			
			biasReader.close();
			PersistanceManager.write(out, "output");
			System.out.println("Wrote all files");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			File neuronDir = new File("src/neurons/");
			for(File f : neuronDir.listFiles()){
				Neuron n = (Neuron) PersistanceManager.read(f);
				System.out.println(n.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
