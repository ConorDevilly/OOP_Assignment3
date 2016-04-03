package com.conordevilly.ocr.neuralnetwork;

import java.util.HashMap;

public class OutputNeuron extends Neuron{
	private static final long serialVersionUID = -5626491854127149290L;
	HashMap<String, Float> results;
	private float max;
	private String mostLikely;

	public OutputNeuron(int numInputs) {
		super(numInputs, null);
		results = new HashMap<String, Float>();
		max = 0;
		mostLikely = "";
	}
	
	@Override
	public void process() throws SizeMismatchException {
		max = 0;
		for(int i = 0; i < inputs.size(); i++){
			float input = inputs.get(i);
			String letter = Character.toString((char) (i + 65));
			results.put(letter, input);
			if(input > max){
				max = input;
				mostLikely = letter;
			}
		}
	}
	
	public HashMap<String, Float> getRes(){
		return results;
	}
	
	public String getGuess(){
		return mostLikely;
	}
	
	@Override
	public float feedforward(){
		return output;
	}
}
