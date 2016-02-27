package com.conordevilly.ocr;

//This class models a Perceptron
//TODO: Abstract???
public class Perceptron {
	float[] inputs;
	float[] weights;
	float bias;
	float output;
	
	public Perceptron(float[] in){
		inputs = in;
		output = 0;
	}
	
	float calcChance(){
		output = 0;
		
		for(int i = 0; i < inputs.length; i++){
			output += inputs[i] * weights[i];
		}
		
		return output;
	}
}
