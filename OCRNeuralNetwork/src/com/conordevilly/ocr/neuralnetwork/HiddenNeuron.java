package com.conordevilly.ocr.neuralnetwork;

import java.util.ArrayList;

public class HiddenNeuron extends Neuron{
	private static final long serialVersionUID = 136667151777922479L;

	public HiddenNeuron(int numInputs, ArrayList<Neuron> nextLayer) {
		super(numInputs, nextLayer);
	}
	
	/*
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
	*/
	
	@Override
	public float calcOutputDif(int index){
		float ret = 0;
		float val = inputs.get(index);
		
		//If the value is a one, this input "stimulates" the neuron
		if(val == 1){
			ret += weights.get(index);
		}else if(val != 0){
			try {
				throw new InvalidInputException();
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}
}