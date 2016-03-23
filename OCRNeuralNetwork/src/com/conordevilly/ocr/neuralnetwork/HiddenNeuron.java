package com.conordevilly.ocr.neuralnetwork;

import java.util.ArrayList;

public class HiddenNeuron extends Neuron{
	private static final long serialVersionUID = 136667151777922479L;

	public HiddenNeuron(int numInputs, ArrayList<Neuron> nextLayer) {
		super(numInputs, nextLayer);
	}
	
	@Override
	public float calcOutputDif(int index){
		float ret = 0;
		if(inputs.get(index) == 1){
			ret = weights.get(index);
		}else if(inputs.get(index) == 0){
			ret = 1 - weights.get(index);
		}else{
			try {
				throw new InvalidInputException();
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}