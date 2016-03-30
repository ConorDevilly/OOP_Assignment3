package com.conordevilly.ocr.neuralnetwork;

import java.util.ArrayList;

public abstract class Neuron implements java.io.Serializable{
	
	private static final long serialVersionUID = 1339248459008642578L;
	float bias;
	transient ArrayList<Float> inputs;
	ArrayList<Float> weights;
	transient float output;
	ArrayList<Neuron> nextLayer;
	int maxInputs;
	
	//Create a new neuron
	public Neuron(int numInputs, ArrayList<Neuron> nextLayer){
		this.maxInputs = numInputs;
		inputs = new ArrayList<Float>();
		weights = new ArrayList<Float>();
		this.nextLayer = nextLayer;
		bias = 1;
	}
	
	//Give and input, process it and feed forward to the next layer
	public void run(ArrayList<Float> in) throws TooManyInputsException, SizeMismatchException{
		setInputs(in);
		run();
	}
	public void run() throws SizeMismatchException{
		process();
		feedforward();
	}
	
	//Set the inputs
	public void setInputs(ArrayList<Float> in) throws TooManyInputsException{
		if(inputSizeOk(in)){
			this.inputs = in;
		}else{
			throw new TooManyInputsException(maxInputs, in.size());
		}
	}
	
	//Add an input
	public void addInput(float in) throws TooManyInputsException{
		if(inputSizeOk(inputs.size() + 1)){
			inputs.add(in);
		}else{
			throw new TooManyInputsException(maxInputs, inputs.size() + 1); 
		}
	}
	
	//Set weights
	public void setWeights(ArrayList<Float> w) throws SizeMismatchException{
		if(arrSizeMatch(maxInputs, w.size())){
			this.weights = w;
		}else{
			throw new SizeMismatchException();
		}
	}
	
	//Add a weight
	public void addWeight(float w) throws TooManyInputsException{
		if(inputSizeOk(weights.size() + 1)){
			weights.add(w);
		}else{
			throw new TooManyInputsException(maxInputs, weights.size() + 1);
		}
	}
	
	//Set a bias
	public void setBias(float f){
		bias = f;
	}
	
	//Randomise all weights
	public void randomiseWeights(){
		for(int i = 0; i < weights.size(); i++){
			weights.set(i, (float) Math.random());
		}
	}
	
	//Basic template for processing inputs
	public void process() throws SizeMismatchException{
		output = 0;
		
		//Check that the size of the weights and inputs match before processing 
		if(arrSizeMatch(weights, inputs)) throw new SizeMismatchException();
		
		for(int i = 0; i < inputs.size(); i++){
			output += calcOutputDif(i);
		}
		output *= bias;
	}
	
	public float calcOutputDif(int index){
		return (inputs.get(index) * weights.get(index));
	}

	//General rule for correcting weights
	public void correctWeights(ArrayList<Float> corrections) throws SizeMismatchException{
		if(arrSizeMatch(weights, corrections)){
			for(int i = 0; i < corrections.size(); i++){
				weights.set(i, weights.get(i) * corrections.get(i));
			}
		}else{
			throw new SizeMismatchException();
		}
	}
	
	//Forward output to the next layer
	public float feedforward(){
		for(Neuron n : nextLayer){
			try {
				n.addInput(output);
			} catch (TooManyInputsException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	//Printable rep of neuron
	public String toString(){
		String s = bias + ": ";
		for(int i = 0; i < weights.size(); i++){
			if(i % 10 == 0) s += "\n";
			s += (weights.get(i) + ", ");
		}
		return s;
	}

	//Check that the size of the input is ok
	private boolean inputSizeOk(ArrayList<Float> toChk){
		return inputSizeOk(toChk.size());
	}
	private boolean inputSizeOk(int num){
		return (num <= maxInputs);
	}
	private boolean arrSizeMatch(ArrayList<Float> arr1, ArrayList<Float> arr2){
		return arrSizeMatch(arr1.size(), arr2.size());
	}
	private boolean arrSizeMatch(int num1, int num2){
		return (num1 == num2);
	}
}