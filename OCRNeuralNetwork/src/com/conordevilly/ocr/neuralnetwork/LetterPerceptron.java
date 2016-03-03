package com.conordevilly.ocr.neuralnetwork;

import java.awt.image.BufferedImage;

public class LetterPerceptron extends Perceptron<BufferedImage>{
	
	float[][] weights;
	int width;
	int height;
	
	public LetterPerceptron(float bias) {
		super(bias);
	}


	public float process(BufferedImage img) {
		//TODO: Set width & height to set value (correct w/t scale)
		weights = new float[img.getWidth()][img.getHeight()];
		resetWeights();
		width = input.getWidth();
		height = input.getHeight();
		output = 0;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//TODO: Does this crap actually work?
				//TODO: Try calc dif?
				//TODO: Try division? Subtraction?
				int sectionColour = input.getRGB(i, j);
				output += weights[i][j] * sectionColour;
			}
		}
		
		return output;
	}
	
	private void resetWeights(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				weights[i][j] = 0.5f;
			}
		}
	}
}
