package com.conordevilly.ocr.neuralnetwork;

public abstract class Neuron implements java.io.Serializable{
	
	float[] inputs;
	float[] weights;
	float output;
	
	public Neuron(int numInputs){
		inputs = new float[numInputs];
		weights = new float[numInputs];
	}
	
	//Set the inputs
	public void giveInputs(float[] in){
		chkArraySize(in, inputs);
		this.inputs = in;
	}
	
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
		chkArraySize(corrections, weights);
		for(int i = 0; i < corrections.length; i++){
			weights[i] += corrections[i];
		}
	}
	
	//If two arrays are not of the same size, throw an exception
	private void chkArraySize(float[] arr1, float[] arr2){
		if(arr1.length != arr2.length) throw new ArrayIndexOutOfBoundsException();
	}
	
	//Forward output to the next layer
	public abstract void forward();
}
