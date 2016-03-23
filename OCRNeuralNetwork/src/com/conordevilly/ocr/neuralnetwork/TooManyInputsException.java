package com.conordevilly.ocr.neuralnetwork;

public class TooManyInputsException extends Exception{
	private static final long serialVersionUID = 1125467234507978268L;

	public TooManyInputsException(int max, int inputted){
		super("Too many inputs provided. Max inputs: " + max + "\tInputted: " + inputted);
	}
}
