package com.conordevilly.ocr.neuralnetwork;

import com.conordevilly.ocr.imageprocessing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Neural Network Class
 * Creates a Neural Network
 * Requires the number of possible outputs to be known
 */
public class NeuralNetwork implements java.io.Serializable{
	
	private static final long serialVersionUID = -5086224275711465547L;
	ArrayList<Neuron> inputLayer;
	ArrayList<Neuron> hiddenLayer1;
	ArrayList<Neuron> outputLayer;
	ArrayList<ArrayList<Neuron>> layers;

	int picSize;
	int numPossiblities;
	
	//Constructor
	//TODO: Remove need for picSize to be known, make ImageProcessor handle this
	//TODO: Add option to create multiple hidden layers
	public NeuralNetwork(int picSize, int numPossiblities){
		this.picSize = picSize;
		this.numPossiblities = numPossiblities;

		//Create the Neurons that are in the network
		inputLayer = new ArrayList<Neuron>();
		hiddenLayer1 = new ArrayList<Neuron>();
		outputLayer = new ArrayList<Neuron>();
		layers = new ArrayList<ArrayList<Neuron>>();
		layers.add(inputLayer);
		layers.add(hiddenLayer1);
		layers.add(outputLayer);

		initNeurons();
	}
	
	//Clear the inputs for all layers
	public void clearInputs(){
		for(ArrayList<Neuron> l : layers){
			for(Neuron n : l){
				n.clearInputs();
			}
		}
	}
	
	//Correct every Neuron in the hidden layer
	//TODO: Allow for multiple hidden layers
	public void correct(int actual){
		Neuron n = hiddenLayer1.get(actual);
		try {
			n.correct();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}
	
	//Returns a string contained in an image
	//TODO: This name is stupid
	public String processMultiImage(BufferedImage input){
		String result = "";

		input = ImageProcessor.binarise(input);
		//We must remove whitespace from the margins before we can extract individual characters
		input = ImageProcessor.extractChar(input);
		ArrayList<BufferedImage> subImgs = ImageProcessor.extractIndivChar(input);
		
		
		for(BufferedImage bufImg : subImgs){
			clearInputs();
			result += this.process(bufImg);
		}
		
		return result;		
	}
	
	//TODO: This mehtod is not needed
	public char process(BufferedImage input){
		char ret = 0;
		ArrayList<Float> numbers = convIntListToFloatList(ImageProcessor.process(input, 10));
		clearInputs();
		fireNeurons(numbers);

		OutputNeuron out = (OutputNeuron) outputLayer.get(0);
		ret = out.getGuess();

		return ret;
	}

	//Gives input to the input layer, then runs all layers sequentially
	//TODO: Allow for multiple layers
	private void fireNeurons(ArrayList<Float> numbers){
		for(int i = 0; i < inputLayer.size(); i++){
			Neuron n = inputLayer.get(i);
			try {
				n.addInput(numbers.get(i));
				n.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(Neuron n : hiddenLayer1){
			try {
				n.run();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		OutputNeuron out = (OutputNeuron) outputLayer.get(0);
		try {
			out.process();
		} catch (SizeMismatchException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Returns a Hashmap. In this hashmap is each letter of the alphabet and how 'stimulated' its corresponding Neuron is
	 * Essentially, this allows us to see what's going on in between the hidden & output layers
	 */
	public HashMap<Character, Float> trainingProcess(BufferedImage input){
		HashMap<Character, Float> results = new HashMap<Character, Float>();
		
		//Process an image through the network
		ArrayList<Float> numbers = convIntListToFloatList(ImageProcessor.process(input, 10));
		clearInputs();
		fireNeurons(numbers);
		
		OutputNeuron out = (OutputNeuron) outputLayer.get(0);
		results = out.getRes();

		return results;		
	}
	
	//Return the output from the output layer
	public char getGuess(){
		OutputNeuron out = (OutputNeuron) outputLayer.get(0);
		return out.getGuess();
	}
	
	//Create the Neurons
	public void initNeurons(){
		int numInputs = (int) Math.pow(picSize, 2);

		//Create the ouptut layer
		OutputNeuron out = new OutputNeuron(numPossiblities);
		for(int i = 0; i < numPossiblities; i++){
			try {
				out.addWeight(1);
			} catch (TooManyInputsException e) {
				e.printStackTrace();
			}
		}
		outputLayer.add(out);
		
		//Create the hidden layer
		//TODO: Allow for multiple layers
		for(int i = 0; i < numPossiblities; i++){
			Neuron n = new Neuron(numInputs, outputLayer);

			//Set all weights to be 0.5 initally
			for(int j = 0; j < numInputs; j++){
				try {
					n.addWeight(0.5f);
				} catch (TooManyInputsException e) {
					e.printStackTrace();
				}
			}
			hiddenLayer1.add(n);
		}
		
		//Create the input layer
		for(int i = 0; i < numInputs; i++){
			inputLayer.add(new InputNeuron(hiddenLayer1));
		}
	}
	
	//Write the network to a file
	public void saveNetwork(){
		PersistanceManager.writeNN(this, new File("Data/nn.data"));
	}
	
	//Converts a list of integers to a list of floats
	private ArrayList<Float> convIntListToFloatList(ArrayList<Integer> l){
		ArrayList<Float> ret = new ArrayList<Float>();
		for(Integer n : l){
			ret.add(n.floatValue());
		}
		return ret;
	}
}
