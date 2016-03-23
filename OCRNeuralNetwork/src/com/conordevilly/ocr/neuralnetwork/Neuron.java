package com.conordevilly.ocr.neuralnetwork;

public abstract class Neuron implements java.io.Serializable{
	
	float[] inputs;
	float[] weights;
	float output;
	
	//Basic template for processing inputs
	public float process(){
		float ret = 0;
		
		for(int i = 0; i < inputs.length; i++){
			ret += inputs[i] * weights[i];
		}
		
		return ret;
	}

	//General rule for correcting weights
	public void correctWeights(float[] corrections){
		for(int i = 0; i < corrections.length; i++){
			weights[i] += corrections[i];
		}
	}
	
	public abstract void forward();
}
