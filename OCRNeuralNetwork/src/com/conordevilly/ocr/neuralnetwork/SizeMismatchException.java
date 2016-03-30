package com.conordevilly.ocr.neuralnetwork;

public class SizeMismatchException extends Exception{
	private static final long serialVersionUID = -394965576407598678L;

	public SizeMismatchException(int expected, int got){
		super("Number of weights do not match the number of inputs. Expected: " + expected + " but Got: " + got);
	}
}
