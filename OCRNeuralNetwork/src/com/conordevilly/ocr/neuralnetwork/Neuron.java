package com.conordevilly.ocr.neuralnetwork;

public abstract class Neuron implements java.io.Serializable{
	public abstract float process();
	public abstract void forward();
	public abstract void backPropagate();
}
