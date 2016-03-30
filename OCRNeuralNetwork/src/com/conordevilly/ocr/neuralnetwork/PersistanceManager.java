package com.conordevilly.ocr.neuralnetwork;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class PersistanceManager {
	public static Neuron read(File f){
		Neuron n = null;
		try{
			FileInputStream in = new FileInputStream(f);
			ObjectInputStream reader = new ObjectInputStream(in);
			n = (Neuron) reader.readObject();
			reader.close();
			in.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return n;
	}
	
	public static NeuralNetwork readNN(File f){
		NeuralNetwork nn = null;
		try{
			FileInputStream in = new FileInputStream(f);
			ObjectInputStream reader = new ObjectInputStream(in);
			nn = (NeuralNetwork) reader.readObject();
			reader.close();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return nn;
		
	}
	
	public static void write(NeuralNetwork nn){
		try{
			FileOutputStream out = new FileOutputStream(new File("src/neurons/nn.data"));
			ObjectOutputStream writer = new ObjectOutputStream(out);
			writer.writeObject(nn);
			writer.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void write(Neuron n, String name){
		try{
			FileOutputStream out = new FileOutputStream(new File("src/neurons/" + name + ".data"));
			ObjectOutputStream writer = new ObjectOutputStream(out);
			writer.writeObject(n);
			writer.close();
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}