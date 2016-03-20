package com.conordevilly.ocr.neuralnetwork;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class PersistanceManager {
	public static OutputNeuron read(File f){
		OutputNeuron n = null;

		try{
			FileInputStream in = new FileInputStream(f);
			ObjectInputStream reader = new ObjectInputStream(in);
			n = (OutputNeuron) reader.readObject();
			reader.close();
			in.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return n;
	}
	
	public static void write(OutputNeuron n){
		try{
			FileOutputStream out = new FileOutputStream(new File("src/neurons/" + n.letter + ".data"));
			ObjectOutputStream writer = new ObjectOutputStream(out);
			writer.writeObject(n);
			writer.close();
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}