package com.conordevilly.ocr;

public class UnknownCharacterException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownCharacterException(char c){
		super("Unknown character: " + c);
	}
}
