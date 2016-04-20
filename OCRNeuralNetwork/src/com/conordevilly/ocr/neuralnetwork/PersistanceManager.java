package com.conordevilly.ocr.neuralnetwork;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/*
 * Persistance Manager
 * Utility class for reading & writing networks
 */
public class PersistanceManager {
	
	//Read a Network from a given file
	public static NeuralNetwork readNN(File f) throws FileNotFoundException{
		NeuralNetwork nn = null;
		FileInputStream in = new FileInputStream(f);
		try {
			ObjectInputStream reader = new ObjectInputStream(in);
			nn = (NeuralNetwork) reader.readObject();
			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nn;
	}
	
	//Write a given Network to a given File
	public static void writeNN(NeuralNetwork nn, File fout){
		try{
			FileOutputStream out = new FileOutputStream(fout);
			ObjectOutputStream writer = new ObjectOutputStream(out);
			writer.writeObject(nn);
			writer.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}