package com.conordevilly.ocr.neuralnetwork;

/*
 * Basic Neuron.
 * Recieves a single pixel as input.
 * Simply forwards this to all output neurons
 */
public class InputNeuron extends Neuron{
	private static final long serialVersionUID = 5294755918675102781L;
	int input;
	int index;
	OutputNeuron[] outputs;
	
	public InputNeuron(int in, int ind, OutputNeuron[] out){
		input = in;
		index = ind;
		outputs = out;
	}
	
	@Override
	public void forward(){
		for(int i = 0; i < outputs.length; i++){
			outputs[i].inputs[index] = (int) process();
		}
	}
	
	@Override
	public float process(){
		return input;
	}
}