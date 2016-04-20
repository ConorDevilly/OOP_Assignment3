package com.conordevilly.ocr.neuralnetwork;

import java.util.HashMap;

/*
 * Output Neuron
 * Returns the network's 'guess'
 */
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
	
	//We need to handle the process method differently as the output will not feedforward to anywhere
	@Override
	public void process() throws SizeMismatchException {
		max = 0;
		for(int i = 0; i < inputs.size(); i++){
			float input = inputs.get(i);
			char letter = (char) (i + 65);
			results.put(letter, input);
			
			//If one Neuron is more stimulated than anyother, set the guess to be the char represented by that Neuron
			if(input > max){
				max = input;
				mostLikely = letter;
			}
		}
	}
	
	/*
	 * Returns a map of each character and how stimulated its Neuron is.
	 * Essentially returns the inputs to the Output Neuron
	*/
	public HashMap<Character, Float> getRes(){
		return results;
	}
	
	//Return the most likely guess
	public char getGuess(){
		return mostLikely;
	}
	
	//The output doesn't need to feedforward, so it just returns the most likely Neuron
	@Override
	public float feedforward(){
		return output;
	}
}
