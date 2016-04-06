package com.conordevilly.ocr.neuralnetwork;

import java.util.HashMap;

public class OutputNeuron extends Neuron{
	private static final long serialVersionUID = -5626491854127149290L;
	HashMap<Character, Float> results;
	private float max;
	private char mostLikely;

	public OutputNeuron(int numInputs) {
		super(numInputs, null);
		results = new HashMap<Character, Float>();
		max = 0;
		mostLikely = 0;
	}
	
	@Override
	public void process() throws SizeMismatchException {
		max = 0;
		for(int i = 0; i < inputs.size(); i++){
			float input = inputs.get(i);
			//String letter = Character.toString((char) (i + 65));
			char letter = (char) (i + 65);
			results.put(letter, input);
			if(input > max){
				max = input;
				mostLikely = letter;
			}
		}
	}
	
	public HashMap<Character, Float> getRes(){
		return results;
	}
	
	public char getGuess(){
		return mostLikely;
	}
	
	@Override
	public float feedforward(){
		return output;
	}
}
