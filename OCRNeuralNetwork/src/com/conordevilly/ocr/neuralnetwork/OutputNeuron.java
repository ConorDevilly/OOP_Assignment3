package com.conordevilly.ocr.neuralnetwork;

public class OutputNeuron extends Neuron{
	private static final long serialVersionUID = -5626491854127149290L;

	public OutputNeuron(int numInputs) {
		super(numInputs, null);
	}
	
	@Override
	public float feedforward(){
		return output;
	}
}
