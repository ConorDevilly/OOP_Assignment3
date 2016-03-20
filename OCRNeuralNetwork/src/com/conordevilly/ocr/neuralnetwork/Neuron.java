package com.conordevilly.ocr.neuralnetwork;

import java.awt.image.BufferedImage;

/*
 * The Neuron class.
 * Takes 100 (10 x 10) pixels as input.
 * Outputs 26 percentages, one for each letter of the alphabet
 */
public class Neuron {
	BufferedImage input;
	float bias;
	float[] weights;
	float[] output;
	
	public void giveInput(BufferedImage in){
		try {
			chkInputSize(in);
			input = in;
		} catch (ImageWrongSizeException e) {
			e.printStackTrace();
		}
	}
	
	private void chkInputSize(BufferedImage in) throws ImageWrongSizeException{
		if(in.getHeight() != 10 || in.getWidth() != 10) throw new ImageWrongSizeException();
	}
}
