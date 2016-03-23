package com.conordevilly.ocr.neuralnetwork;

import com.conordevilly.ocr.imageprocessing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class NeuralNetwork {
	
	ArrayList<Neuron> inputLayer;
	ArrayList<Neuron> hiddenLayer1;
	ArrayList<Neuron> outputLayer;

	int picSize;
	int numPossiblities;
	
	public NeuralNetwork(int picSize, int numPossiblities){
		this.picSize = picSize;
		this.numPossiblities = numPossiblities;

		inputLayer = new ArrayList<Neuron>();
		hiddenLayer1 = new ArrayList<Neuron>();
		outputLayer = new ArrayList<Neuron>();

		try{
			initNeurons();
		}catch(TooManyNeuronsException e){
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Float> process(BufferedImage input){
		HashMap<String, Float> results = new HashMap<String, Float>();
		
		/*
		 * ImageProcessing shit
		 */
		
		/*
		 * NN Shit
		 */
		
		return results;		
	}
	
	//Read neurons if they exist, else create them
	public void initNeurons() throws TooManyNeuronsException{
		//Create the input layer
		int res = (int) Math.pow(picSize, 2);
		for(int i = 0; i < res; i++){
			inputLayer.add(new InputNeuron(hiddenLayer1));
		}
		
		//Load the hidden layer, else create a new one
		try{
			File neuronDir = new File("src/neurons/");
			for(File f : neuronDir.listFiles()){
				HiddenNeuron n = (HiddenNeuron) PersistanceManager.read(f);
				hiddenLayer1.add(n);
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
	
	//Write the hidden layer
	public void saveNeurons(){
		for(int i = 0; i < hiddenLayer1.size(); i++){
			PersistanceManager.write(hiddenLayer1.get(i), i);
		}
	}
}
