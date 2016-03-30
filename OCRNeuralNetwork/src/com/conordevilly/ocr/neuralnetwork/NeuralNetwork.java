package com.conordevilly.ocr.neuralnetwork;

import com.conordevilly.ocr.imageprocessing.*;


import java.awt.image.BufferedImage;
import java.io.File;
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
	HashMap<String, ArrayList<Float>> references;
	
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

		inNeurons();
		loadReferences(new File("src/Weights"));
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
				ArrayList<Float> vals;
				vals = convIntListToFloatList(ImageProcessor.process(ImageIO.read(f), 10));
				references.put(key, vals);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void correct(String s){
		ArrayList<Float> cmp = references.get(s);
		for(Neuron n : hiddenLayer1){
			try {
				n.correctWeights(cmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public HashMap<String, Float> process(BufferedImage input){
		HashMap<String, Float> results = new HashMap<String, Float>();
		ArrayList<Float> numbers = convIntListToFloatList(ImageProcessor.process(input, 10));
		clearInputs();
		
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
		
		results = out.getRes();
		return results;		
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
				hiddenLayer1.add(new HiddenNeuron(numPossiblities, outputLayer));
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
			HiddenNeuron n = new HiddenNeuron(numInputs, outputLayer);
			for(int j = 0; j < numInputs; j++){
				try {
					n.addWeight((float) Math.random());
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
