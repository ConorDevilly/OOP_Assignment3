package com.conordevilly.ocr.trainer;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;
import com.conordevilly.ocr.neuralnetwork.PersistanceManager;

public class AutoTrainer {
	public static void main(String[] args){
		AutoTrainer trainer = new AutoTrainer();
		trainer.getOptions();
		trainer.run();
	}

	NeuralNetwork nn;
	int epochs;
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
	
	public void getOptions(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter directory of training data: ");
		dirPath = in.nextLine();
		System.out.println("How many times should the trainer run?");
		epochs = in.nextInt();
	}
	
	public void run(){
		File dataDir = new File(dirPath);
		File[] imgList = dataDir.listFiles();
		Arrays.sort(imgList);
		
		for(int i = 0; i < epochs; i++){
			numWrong = 0;
			for(int j = 0; j < imgList.length; j++){
				File currImg = imgList[j];
				try {
					BufferedImage in = ImageIO.read(currImg);
					char guess = nn.process(in);
					char actual = currImg.getName().toUpperCase().charAt(0);
					
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
			System.out.println("Epoch: " + i + "\tNumber wrong: " + numWrong);
			if(numWrong == 0){
				PersistanceManager.writeNN(nn, new File("src/neurons/nn.data"));
				break;
			}
		}
	}
}
