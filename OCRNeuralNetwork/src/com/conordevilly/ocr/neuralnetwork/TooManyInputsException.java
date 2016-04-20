package com.conordevilly.ocr.neuralnetwork;

/*
 * Exception to be thrown when a neuron receives too many inputs
 */
public class TooManyInputsException extends Exception{
	private static final long serialVersionUID = 4971937495808657837L;

	public TooManyInputsException(int max, int inputted){
		super("Too many inputs provided. Max inputs: " + max + "\tInputted: " + inputted);
	}
}
