package com.conordevilly.ocr.wordGuesser;

import java.io.File;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;
import com.conordevilly.ocr.neuralnetwork.PersistanceManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WordGuesser extends Application{
	public static void main(String args[]){
		launch(args);
	}

	NeuralNetwork nn;
	MainController ctrl;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
		Parent root = loader.load();

		nn = PersistanceManager.readNN(new File("src/neurons/nn.data"));
		//Check nn read successfully
		if(nn == null){
			nn = new NeuralNetwork(10, 26);
		}

		ctrl = loader.<MainController>getController();
		ctrl.init(nn);

		Scene scene = new Scene(root, 800, 450);
		stage.setTitle("OCR Trainer");
		stage.setScene(scene);
		stage.show();
	}
}
