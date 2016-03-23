package com.conordevilly.ocr.neuralnetwork;

public class InvalidInputException extends Exception{
	private static final long serialVersionUID = 5636176397020442325L;

	public InvalidInputException(){
		super("Invalid Input: Only accepted values are 1 and 0");
	}
}
