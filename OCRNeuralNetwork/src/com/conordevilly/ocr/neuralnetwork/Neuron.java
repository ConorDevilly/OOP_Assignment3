package com.conordevilly.ocr.neuralnetwork;

import java.util.ArrayList;

/*
 * Base Neuron class
 */
public class Neuron implements java.io.Serializable{
	private static final long serialVersionUID = -2651544557240828084L;

	//Class variables
	float bias;
	ArrayList<Float> weights;
	ArrayList<Float> inputs;
	float output;
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
	
	//Clear the neuron's inputs
	public void clearInputs(){
		inputs.clear();
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
		//Check the size of the inputted array matches the number of inputs
		if(numMatch(maxInputs, w.size())){
			this.weights = w;
		}else{
			throw new SizeMismatchException(maxInputs, w.size());
		}
	}
	
	//Add a weight
	public void addWeight(float w) throws TooManyInputsException{
		//Check there is space in the weight arraylist before we add it
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
	//Should probably be renamed to summation to reflect NN concepts
	public void process() throws SizeMismatchException{
		output = 0;
		
		//Check that the size of the weights and inputs match before processing 
		if(!arrSizeMatch(weights, inputs)) throw new SizeMismatchException(inputs.size(), weights.size());
		
		//Make the output = sum of all inputs once their weights are factored in
		for(int i = 0; i < inputs.size(); i++){
			output += calcInput(i);
		}
		output *= bias;
	}
	
	//Sets what an input should be once its weight is factored into account
	protected float calcInput(int index){
		return (inputs.get(index) * weights.get(index));
	}
	
	//Corrects the Neuron
	public void correct() throws InvalidInputException{
		float weight;
		float input;
		//Go through each weight
		for(int i = 0; i < weights.size(); i++){
			weight = weights.get(i);
			input = inputs.get(i);
			
			//The weight must be modified differently depending on whether or not the input is a postitve value
			if(input == 1){
				//We have special cases for when the weight = 1 or 0 as computing ln1 or ln0 leads to errors
				if(weight == 0){
					weight += 0.5f;
				}else{
					weight += Math.log(weight) * -0.1;
				}
				weight = (weight >= 1) ? 1 : weight;
			}else if(input == 0){
				if(weight == 1){
					weight -= 0.5f;
				}else{
					weight -= Math.log(1 - weight) * -0.1;
				}
				//Check weight != 0
				weight = (weight <= 0) ? 0 : weight;
			}else{
				throw new InvalidInputException();
			}
			weights.set(i, weight);
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
	
	/*
	 * Returns a representation of the neuron in the form:
	 * Bias: Weight1, Weight2, ... WeightN
	 */
	public String toString(){
		String s = bias + ": ";
		for(int i = 0; i < weights.size(); i++){
			s += (weights.get(i).toString() + ", ");
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

	//Check the size of two arrays or two numbers match
	private boolean arrSizeMatch(ArrayList<Float> arr1, ArrayList<Float> arr2){
		return numMatch(arr1.size(), arr2.size());
	}
	private boolean numMatch(int num1, int num2){
		return (num1 == num2);
	}
}