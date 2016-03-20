package com.conordevilly.ocr.neuralnetwork;

public class ImageWrongSizeException extends Exception {
	public ImageWrongSizeException(){
		super("Image is not the right size!");
	}
}
