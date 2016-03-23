package com.conordevilly.ocr.neuralnetwork;

/*
 * Simple perceptron.
 * Takes a single integer input and forwards it another layer
 */
public class InputNeuron extends Neuron{
	private static final long serialVersionUID = 5294755918675102781L;

	public InputNeuron() {
		super(1);
		weights[0] = 1;
	}

	@Override
	public void forward(){};
}