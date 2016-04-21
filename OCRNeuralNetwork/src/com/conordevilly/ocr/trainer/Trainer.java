package com.conordevilly.ocr.trainer;

import java.io.File;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;
import com.conordevilly.ocr.neuralnetwork.PersistanceManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * A GUI to train the NN
 */
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

		nn = PersistanceManager.readNN(new File("Data/nn.data"));
		//Check nn read successfully, if not, create a new one
		if(nn == null){
			nn = new NeuralNetwork(10, 26);
		}

		//Get the Controller so we can pass it in the NN
		ctrl = loader.<GUIController>getController();
		ctrl.init(nn);

		Scene scene = new Scene(root, 800, 450);
		stage.setTitle("OCR Trainer");
		stage.setScene(scene);
		stage.show();
	}
	
	//Write the network
	@Override
	public void stop(){
		nn.saveNetwork();
	}
}
