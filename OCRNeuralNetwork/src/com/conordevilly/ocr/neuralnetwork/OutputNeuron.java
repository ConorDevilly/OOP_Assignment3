package com.conordevilly.ocr.neuralnetwork;

public class OutputNeuron {
	int[] inputs;
	int[] weights;
	float bias;
	float output;
	
	public OutputNeuron(float bias, int[] weights){
		this.bias = bias;
		this.weights = weights;
	}
	
	public float process(){
		//Calc output
		for(int i = 0; i < inputs.length; i++){
			//To improve: Increase percent if dif <= 0.5f
			output += (inputs[i] == weights[i]) ? 0.1 : 0;
		}
		
		//Add bias
		output *= bias;
		
		return output;
	}
}
