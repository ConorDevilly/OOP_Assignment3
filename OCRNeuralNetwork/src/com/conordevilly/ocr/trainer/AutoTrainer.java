package com.conordevilly.ocr.trainer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;
import com.conordevilly.ocr.neuralnetwork.PersistanceManager;

/*
 * Auto Trainer
 * Script used to automatically run data through the network.
 * The first character of all files in this data must be named what the data actually is.
 * E.g: An image of X will be named X1.png
 * Will ask for a directory that contains images & the number of times to run through the data
 * If the NN runs through the entire directory without errors it will write the network
 */
public class AutoTrainer {
	//Main
	public static void main(String[] args){
		AutoTrainer trainer = new AutoTrainer();
		trainer.getOptions();
		trainer.run();
	}
	
	NeuralNetwork nn;
	int noRuns;
	String dirPath;
	int numWrong;

	public AutoTrainer(){
		try{
			nn = PersistanceManager.readNN(new File("src/neurons/nn.data"));
		}catch(FileNotFoundException e){
			System.out.println("WARN: NN data file not found.");
			System.out.println("Creating new nn...");
			nn = new NeuralNetwork(10, 26);
			System.out.println("NN created.");
		}
	}
	
	//Read in the data dir & no. times to run
	public void getOptions(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter directory of training data: ");
		dirPath = in.nextLine();
		System.out.println("How many times should the trainer run?");
		noRuns = in.nextInt();
		in.close();
	}
	
	//Go through each entry, making a guess for each one
	public void run(){
		File dataDir = new File(dirPath);
		File[] imgList = dataDir.listFiles();
		
		//Go through the data an inputted number of times
		for(int i = 1; i <= noRuns; i++){
			numWrong = 0;
			//Go through each file in the data dir
			for(int j = 0; j < imgList.length; j++){
				File currImg = imgList[j];
				try {
					//Process the image
					BufferedImage in = ImageIO.read(currImg);
					char guess = nn.process(in);
					//Get the correct name
					char actual = currImg.getName().toUpperCase().charAt(0);
					
					//Correct the network if necessary
					if(guess != actual){
						//Convert from ASCII to place in alphabet
						System.out.println("Guessed: " + guess + "\tActual: " + actual);
						int actInd = actual - 65;
						numWrong++;
						nn.correct(actInd);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Run: " + i + "\tNumber wrong: " + numWrong);
			
			//If no incorrect answers, write the network
			if(numWrong == 0){
				PersistanceManager.writeNN(nn, new File("src/neurons/nn.data"));
				break;
			}
		}
	}
}
