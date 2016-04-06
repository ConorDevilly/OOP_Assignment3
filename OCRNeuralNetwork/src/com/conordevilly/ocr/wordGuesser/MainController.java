package com.conordevilly.ocr.wordGuesser;

import java.awt.image.BufferedImage;
import java.io.File;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MainController {
	@FXML private TextField guess;
	@FXML private ImageView imageView;
	NeuralNetwork nn;
	
	public void init(NeuralNetwork network){
		nn = network;
	}

	@FXML protected void openFileChooser(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
		File img = fileChooser.showOpenDialog(null);
		displayImage(img);
	}
	
	@FXML protected void displayImage(File f){
		try{
			Image img = new Image(f.getPath());
			BufferedImage bufImg = SwingFXUtils.fromFXImage(img, null);
			String result = nn.processMultiImage(bufImg);
			setGuess(result);
			imageView.setImage(img);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML protected void setGuess(String s){
		guess.setText(s);
	}
}
