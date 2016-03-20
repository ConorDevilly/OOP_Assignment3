package com.conordevilly.ocr.neuralnetwork;

/*
 * Basic Neuron.
 * Recieves a single pixel as input.
 * Simply forwards this to all output neurons
 */
public class InputNeuron{
	int input;
	int index;
	OutputNeuron[] outputs;
	
	public InputNeuron(int in, int ind, OutputNeuron[] out){
		input = in;
		index = ind;
		outputs = out;
	}
	
	public void process(){
		for(int i = 0; i < outputs.length; i++){
			outputs[i].inputs[index] = input;
		}
	}
}