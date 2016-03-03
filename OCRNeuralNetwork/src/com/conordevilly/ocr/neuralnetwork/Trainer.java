package com.conordevilly.ocr.neuralnetwork;

import java.io.*;
import java.util.Scanner;

public class Trainer {
	File trainDir;
	char actual;
	char output;
	
	public Trainer(File dir){
		trainDir = dir;
		actual = ' ';
	}
	
	void train(){
		for(File f : trainDir.listFiles()){
			if(f.isFile()){
				//This will pass the file to the NN
				output = process(f);
				
				//The verification process
				actual = verify(f);
			}
		}
	}
	
	char verify(File f) throws UnknownCharacterException{
		display(f);

		Scanner in = new Scanner(System.in);
		System.out.println("Enter the above letter: ");
		char c = in.next(".").charAt(0);
		in.close();
		
		if(c < 97 || c > 123) throw new UnknownCharacterException(c);
		return c;
	}
}
