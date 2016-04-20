package com.conordevilly.ocr.neuralnetwork;

/*
 * Exception to be thrown when a value that is not 1 or 0 is put into the input layer
 */
public class InvalidInputException extends Exception{
	private static final long serialVersionUID = 5636176397020442325L;

	public InvalidInputException(){
		super("Invalid Input: Only accepted values are 1 and 0");
	}
}
