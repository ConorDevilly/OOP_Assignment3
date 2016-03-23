package com.conordevilly.ocr.neuralnetwork;

public class TooManyNeuronsException extends Exception{
	private static final long serialVersionUID = -8922410527961916664L;

	public TooManyNeuronsException(int numCreated, int numExpected){
		super("Created " + numCreated + " instead of " + numExpected);
	}
}
