package com.conordevilly.ocr.trainer;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;

public class Trainer extends Application{
	public static void main(String args[]){
		launch(args);
	}
	
	NeuralNetwork nn;
	GUIController ctrl;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
		Parent root = loader.load();
		ctrl = loader.<GUIController>getController();
		nn = new NeuralNetwork(10, 26);
		Scene scene = new Scene(root, 800, 450);
		stage.setTitle("OCR Trainer");
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	@Override
	public void stop(){
		nn.saveNeurons();
	}
	*/
}
