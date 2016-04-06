package com.conordevilly.ocr.neuralnetwork;

import java.util.ArrayList;

public abstract class Neuron implements java.io.Serializable{
	
	private static final long serialVersionUID = 1339248459008642578L;
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
		if(arrSizeMatch(maxInputs, w.size())){
			this.weights = w;
		}else{
			throw new SizeMismatchException(maxInputs, w.size());
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
		if(!arrSizeMatch(weights, inputs)) throw new SizeMismatchException(inputs.size(), weights.size());
		
		for(int i = 0; i < inputs.size(); i++){
			output += calcOutputDif(i);
		}
		output *= bias;
	}
	
	public float calcOutputDif(int index){
		return (inputs.get(index) * weights.get(index));
	}
	
	//Broken. Possible fix is to modify the summation function to only increase chance iff positive value. 
	//I.e: A black pix "activates" assoc. neuron
	public void correct() throws InvalidInputException{
		float weight;
		float input;
		for(int i = 0; i < weights.size(); i++){
			weight = weights.get(i);
			input = inputs.get(i);
			
			if(input == 1){
				//We have special cases for when the weight = 1 or 0 as computing ln1 or ln0 leads to errors
				if(weight == 0){
					weight += 0.5f;
				}else{
					weight += -(Math.log(weight) * (1f / 10f));
				}
			}else if(input == 0){
				if(weight == 1){
					weight -= 0.5f;
				}else{
					weight -= -(Math.log(1 - weight) * (1f / 10f));
				}
			}else{
				throw new InvalidInputException();
			}
			
			weights.set(i, weight);
		}
	}
	
	//DEBUG
	public void correct(ArrayList<Float> corrections) throws SizeMismatchException, InvalidInputException{
		if(corrections.size() != weights.size()){
			throw new SizeMismatchException(weights.size(), corrections.size());
		}
		
		for(int i = 0; i < corrections.size(); i++){
			float correction = corrections.get(i);
			float weight = weights.get(i);
			
			if(correction == 1){
				if(weight == 0){
					weight += 0.5f;
				}else{
					weight += -(Math.log(weight) * (1f / 10f));
				}
			}else if(correction == 0){
				if(weight == 1){
					weight -= 0.5f;
				}else{
					weight -= -(Math.log(1 - weight) * (1f / 10f));
				}
			}else{
				throw new InvalidInputException();
			}
		}
	}
	
	//DEBUG
	public void correct(float actual, float output){
		//Need some shite to differentiate weight
		float weightChn = output- actual;
		weightChn /= 100f;
		for(int i = 0; i < weights.size(); i++){
			float correctWeight = weightChn + weights.get(i);
			correctWeight = (correctWeight >= 1.0) ? 1 : correctWeight;
			correctWeight = (correctWeight <= 0) ? 0 : correctWeight;
			weights.set(i, correctWeight);
		}
	}

	//General rule for correcting weights
	public void correctWeights(ArrayList<Float> corrections) throws SizeMismatchException, InvalidInputException{
		if(arrSizeMatch(weights, corrections)){
			for(int i = 0; i < corrections.size(); i++){
				/*
				 * x : 1	y += (ln(y) * (1/-10))	(y == 0) ? y += 0.5
				 * x : 0	y -= (ln(10y) / 10)		(y < 1) ? y -= y/2
				 * 
				 * x : 1	(y == 0)	? y += 0.5 	: y += (ln(y) * (1/-10))
				 * x : 0	(y < 1) 	? y -= y/2	: y -= (ln(10y) / 10)
				 */
				float correct = corrections.get(i);
				float weight = weights.get(i);
				
				/*
				 * Forumulae for the learning gradient:
				 * 
				 * x : 1 => {y : 0  => y += 0.5
				 * 			{y !: 0 => y -= ln(y) * 1/10
				 * 
				 * x : 0 => {y : 1  => y -= 0.5
				 * 			{y !: 1 => y += ln(1 - y) * 1/10
				 * 
				 * Where: 	x = Correct value
				 * 			y = Current Weight
				 */
				
				if(correct == 1){
					//Trying to get ln0 is a calculation error, hence we handle it manually
					if(weight == 0){
						weight += 0.5;
					}else{
						weight -= (Math.log(weight) * (1f / 10f));
					}
				}else if(correct == 0){
					if(weight == 1){
						weight -= 0.5;
					}else{
						weight += (Math.log(1 - weight) * (1f / 10f));
					}
				}else{
					throw new InvalidInputException();
				}
				
				weights.set(i, weight);
				
				/*
				float dif = corrections.get(i) - weights.get(i);
				weights.set(i, weights.get(i) + dif);
				*/
			}
		}else{
			throw new SizeMismatchException(weights.size(), corrections.size());
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
			//if(i % 10 == 0) s += "\n";
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
	private boolean arrSizeMatch(ArrayList<Float> arr1, ArrayList<Float> arr2){
		return arrSizeMatch(arr1.size(), arr2.size());
	}
	private boolean arrSizeMatch(int num1, int num2){
		return (num1 == num2);
	}
}