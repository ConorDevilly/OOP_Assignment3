package com.conordevilly.ocr.neuralnetwork;

import com.conordevilly.ocr.imageprocessing.*;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class NeuralNetwork implements java.io.Serializable{
	
	private static final long serialVersionUID = -5086224275711465547L;
	ArrayList<Neuron> inputLayer;
	ArrayList<Neuron> hiddenLayer1;
	ArrayList<Neuron> outputLayer;
	ArrayList<ArrayList<Neuron>> layers;

	int picSize;
	int numPossiblities;
	HashMap<String, ArrayList<Integer>> references;
	
	public void test(){
		System.out.println("NN initalised");
	}
	
	public NeuralNetwork(int picSize, int numPossiblities){
		this.picSize = picSize;
		this.numPossiblities = numPossiblities;

		inputLayer = new ArrayList<Neuron>();
		hiddenLayer1 = new ArrayList<Neuron>();
		outputLayer = new ArrayList<Neuron>();
		layers = new ArrayList<ArrayList<Neuron>>();
		layers.add(inputLayer);
		layers.add(hiddenLayer1);
		layers.add(outputLayer);

		references = new HashMap<String, ArrayList<Integer>>();
		loadReferences(new File("src/Weights"));
		inNeurons();
		/*
		try{
			initNeurons();
		}catch(TooManyNeuronsException e){
			e.printStackTrace();
		}
		*/
	}
	
	public void clearInputs(){
		for(ArrayList<Neuron> l : layers){
			for(Neuron n : l){
				n.clearInputs();
			}
		}
	}
	
	public void loadReferences(File dir){
		for(File f : dir.listFiles()){
			try {
				String key = f.getName().substring(0, 1);
				ArrayList<Integer> vals;
				vals = ImageProcessor.process(ImageIO.read(f), 10);
				references.put(key, vals);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\n");
	}
	
	//TODO: THIS ONE IS RIGHT
	public void correct(int actual){
		Neuron n = hiddenLayer1.get(actual);

		try {
			n.correct();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}
	
	public void correct(String actual, HashMap<String, Float> results){
		for(int i = 0; i < hiddenLayer1.size(); i++){
			String iStr = Character.toString((char) (i + 65));
			float percentSimilar = computeSimilarity(actual, iStr);
			float output = results.get(iStr);

			System.out.println("1: " + actual + "\t2: " + iStr + "\t%: " + percentSimilar + "\t\tOut: " + output);

			Neuron n = hiddenLayer1.get(i);
			n.correct(percentSimilar, output);
		}
	}
	
	//Return how similar two characters are
	private float computeSimilarity(String str1, String str2){
		float similar = 0;
		ArrayList<Integer> s1 = references.get(str1);
		ArrayList<Integer> s2 = references.get(str2);
		
		for(int i = 0; i < s1.size(); i++){
			similar += (s1.get(i) == s2.get(i)) ? 1 : 0;
		}
		
		
		return similar;
	}
	
	//Returns a string contained in an image
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

	public HashMap<Character, Float> trainingProcess(BufferedImage input){
		HashMap<Character, Float> results = new HashMap<Character, Float>();
		ArrayList<Float> numbers = convIntListToFloatList(ImageProcessor.process(input, 10));
		clearInputs();
		
		fireNeurons(numbers);
		
		OutputNeuron out = (OutputNeuron) outputLayer.get(0);
		results = out.getRes();
		return results;		
	}
	
	public char getGuess(){
		OutputNeuron out = (OutputNeuron) outputLayer.get(0);
		return out.getGuess();
	}
	
	
	//Read neurons if they exist, else create them
	public void initNeurons() throws TooManyNeuronsException{
		//Create the input layer
		int res = (int) Math.pow(picSize, 2);
		for(int i = 0; i < res; i++){
			inputLayer.add(new InputNeuron(hiddenLayer1));
		}
		
		//Load the hidden & outer layer, else create a new one
		try{
			File neuronDir = new File("src/neurons/");
			for(File f : neuronDir.listFiles()){
				Neuron n = (Neuron) PersistanceManager.read(f);
				if(n instanceof OutputNeuron){
					outputLayer.add(n);
				}else{
					hiddenLayer1.add(n);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Verify that the correct number of HiddenNeurons have been created
		if(hiddenLayer1.size() > numPossiblities){
			throw new TooManyNeuronsException(hiddenLayer1.size(), numPossiblities);
		}else{
			//Create more neurons if we do not have enough
			while(hiddenLayer1.size() < numPossiblities){
				hiddenLayer1.add(new Neuron(numPossiblities, outputLayer));
			}
		}
	}
	
	public void inNeurons(){
		int numInputs = (int) Math.pow(picSize, 2);
		OutputNeuron out = new OutputNeuron(numPossiblities);
		for(int i = 0; i < numPossiblities; i++){
			try {
				out.addWeight(1);
			} catch (TooManyInputsException e) {
				e.printStackTrace();
			}
		}
		outputLayer.add(out);
		
		for(int i = 0; i < numPossiblities; i++){
			Neuron n = new Neuron(numInputs, outputLayer);
			ArrayList<Float> initVals = convIntListToFloatList(references.get(Character.toString((char) (i+65))));

			//TODO: Is any of this crap necessary?
			for(int j = 0; j < numInputs; j++){
				try {
					if(initVals.get(j) == 1){
						//n.addWeight(0.75f);
						n.addWeight(0.5f);
					}else{
						//n.addWeight(0);
						n.addWeight(0.5f);
					}
					//n.addWeight(initVals.get(j));
				} catch (TooManyInputsException e) {
					e.printStackTrace();
				}
			}
			hiddenLayer1.add(n);
		}
		
		for(int i = 0; i < numInputs; i++){
			inputLayer.add(new InputNeuron(hiddenLayer1));
		}
	}
	
	public void saveNetwork(){
		PersistanceManager.write(this);
	}
	
	//Write the hidden & outer layers
	//TODO: Remove?
	public void saveNeurons(){
		//Hidden layer
		for(int i = 0; i < hiddenLayer1.size(); i++){
			PersistanceManager.write(hiddenLayer1.get(i), Integer.toString(i));
		}
		//Output layer
		for(int i = 0; i < outputLayer.size(); i++){
			PersistanceManager.write(outputLayer.get(i), "output");
		}
	}

	private ArrayList<Float> convIntListToFloatList(ArrayList<Integer> l){
		ArrayList<Float> ret = new ArrayList<Float>();
		for(Integer n : l){
			ret.add(n.floatValue());
		}
		return ret;
	}
}
