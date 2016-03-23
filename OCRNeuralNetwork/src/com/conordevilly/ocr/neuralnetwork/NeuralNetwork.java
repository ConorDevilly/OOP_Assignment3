package com.conordevilly.ocr.neuralnetwork;

import java.io.File;
import java.util.ArrayList;

public class NeuralNetwork {
	
	ArrayList<HiddenNeuron> hiddenLayer1;
	ArrayList<InputNeuron> inputLayer;
	OutputNeuron outputLayer;
	int picSize;
	int numOutputs;
	
	public NeuralNetwork(int picSize, int numOutputs){
		this.picSize = picSize;
		this.numOutputs = numOutputs;

		hiddenLayer1 = new ArrayList<HiddenNeuron>();
		inputLayer = new ArrayList<InputNeuron>();
		outputLayer = new OutputNeuron();

		try{
			initNeurons();
		}catch(TooManyNeuronsException e){
			e.printStackTrace();
		}
	}
	
	public void initNeurons() throws TooManyNeuronsException{
		//Create the input layer
		int res = (int) Math.pow(picSize, 2);
		for(int i = 0; i < res; i++){
			inputLayer.add(new InputNeuron(in, ind, out));
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
		if(hiddenLayer1.size() > numOutputs){
			throw new TooManyNeuronsException(hiddenLayer1.size(), numOutputs);
		}else{
			//Create more neurons if we do not have enough
			while(hiddenLayer1.size() < numOutputs){
				hiddenLayer1.add(new HiddenNeuron(letter, bias, weights));
			}
		}
	}
	
	//Write the hidden layer
	public void saveNeurons(){
		for(HiddenNeuron n : hiddenLayer1){
			PersistanceManager.write(n);
		}
	}
}
