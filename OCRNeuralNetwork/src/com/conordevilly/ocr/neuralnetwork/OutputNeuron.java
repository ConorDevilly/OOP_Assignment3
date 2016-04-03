package com.conordevilly.ocr.neuralnetwork;

import java.util.HashMap;

public class OutputNeuron extends Neuron{
	private static final long serialVersionUID = -5626491854127149290L;
	HashMap<String, Float> results;

	public OutputNeuron(int numInputs) {
		super(numInputs, null);
		results = new HashMap<String, Float>();
	}
	
	@Override
	public void process() throws SizeMismatchException {
		
		for(int i = 0; i < inputs.size(); i++){
			results.put(Character.toString((char) (i + 65)), inputs.get(i));
		}
	}
	
	public HashMap<String, Float> getRes(){
		return results;
	}
	
	
	@Override
	public float feedforward(){
		return output;
	}
}
