package com.conordevilly.ocr.neuralnetwork;

import java.util.ArrayList;

/*
 * Input Neuron
 * Takes a single integer input and forwards it another layer
 */
public class InputNeuron extends Neuron{
	private static final long serialVersionUID = 5294755918675102781L;

	//Create a neuron taking 1 input & set its weight to one
	public InputNeuron(ArrayList<Neuron> nextLayer) {
		super(1, nextLayer);

		try {
			addWeight(1);
		} catch (TooManyInputsException e) {
			e.printStackTrace();
		}
	}
}