package com.conordevilly.ocr.neuralnetwork;

public class OutputNeuron extends Neuron{
	private static final long serialVersionUID = 136667151777922479L;
	int[] inputs;
	int[] weights;
	float bias;
	float output;
	char letter;
	
	public OutputNeuron(char letter, float bias, int[] weights){
		this.letter = letter;
		this.bias = bias;
		this.weights = weights;
	}
	
	public String toString(){
		String s = letter + ": " + bias + ": ";
		for(int i = 0; i < weights.length; i++){
			if(i % 10 == 0) s += "\n";
			s += (weights[i] + ", ");
		}
		return s;
	}
	
	@Override
	public float process(){
		//Calc output
		for(int i = 0; i < inputs.length; i++){
			//To improve: Increase percent if dif <= 0.5f
			output += (inputs[i] == weights[i]) ? 0.1 : 0;
		}
		
		//Add bias (bias expressed as fraction as opposed to percent)
		output *= (bias / 100);
		
		return output;
	}

	@Override
	public void forward() {
		//This layer does not forward		
	}

	@Override
	public void backPropagate() {
		//How the hell does this work?		
	}
}
