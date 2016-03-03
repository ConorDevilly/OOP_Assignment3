package com.conordevilly.ocr.neuralnetwork;

import java.io.*;
import java.util.ArrayList;

public class InitNN{
	public static void main(String[] args){
		ArrayList<Perceptron> network = new ArrayList<Perceptron>();

		try {
			//TODO: Fix paths
			BufferedReader reader = new BufferedReader(new FileReader("../LetterFrequency.txt"));
			String line;
			
			//Creates letter perceptrons
			while((line = reader.readLine()) != null){
				String[] words = line.split(" : ");
				LetterPerceptron p = new LetterPerceptron(Float.parseFloat(words[1].trim()));
				network.add(p);
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
