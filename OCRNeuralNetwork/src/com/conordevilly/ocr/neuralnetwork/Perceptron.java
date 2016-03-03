package com.conordevilly.ocr.neuralnetwork;

//This class models a Perceptron
public abstract class Perceptron<T> {
	T input;
	float output;
	float bias;
	float[] weights;
	
	public Perceptron(T input){
		this.input = input;
	}
	
	public Perceptron(float bias){
		this.bias = bias;
	}

	public Perceptron(T input, float bias){
		this(input);
		this.bias = bias;
	}

	public Perceptron(T input, float[] weights){
		this(input);
		this.weights = weights;
	}

	public Perceptron(T input, float bias, float[] weights){
		this(input, bias);
		this.weights = weights;
	}
}
