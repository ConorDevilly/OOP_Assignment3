package com.conordevilly.ocr;

public class InitNN{
	public static void main(String[] args){
		//Create as many perceptrons as there are letters in the alphabet (lower case)
		int totalPossibilities = 26;
		Perceptron[] perceptrons = new Perceptron[26];
		
		for(int i = 0; i < totalPossibilities; i++){
			perceptrons[i] = new Perceptron();
		}
		
	}
}
