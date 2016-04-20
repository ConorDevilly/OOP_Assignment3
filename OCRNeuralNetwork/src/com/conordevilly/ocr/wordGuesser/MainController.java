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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/*
 * Controller for the Word Guesser GUI
 */
public class MainController {
	@FXML private Text guess;
	@FXML private ImageView imageView;
	@FXML private TextField ansBox;
	File img;
	NeuralNetwork nn;
	
	//Used to pass the NN to the controller
	public void init(NeuralNetwork network){
		nn = network;
	}

	//Allow the user to pick a file
	@FXML protected void openFileChooser(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
		img = fileChooser.showOpenDialog(null);
		displayImage(img);
	}
	
	//Refresh an image
	@FXML protected void refresh(ActionEvent event){
		displayImage(img);
	}
	
	//Display an image, make the NN guess it then display the guess
	@FXML protected void displayImage(File f){
		try{
			Image img = new Image(f.toURI().toString());
			BufferedImage bufImg = SwingFXUtils.fromFXImage(img, null);
			String result = nn.processMultiImage(bufImg);
			setGuess(result);
			imageView.setImage(img);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//TODO: Error checking
	//Correct the NN
	@FXML protected void correct(ActionEvent event){
		char[] ans = ansBox.getText().toUpperCase().toCharArray();
		ansBox.clear();
		//Check each character
		for(int i = 0; i < ans.length; i++){
			char g = guess.getText().charAt(i);
			char actual = ans[i];

			if(g != actual){
				nn.correct((actual - 65));
			}
		}
		refresh(null);
	}
	
	//Display the NN guess
	@FXML protected void setGuess(String s){
		guess.setText(s);
	}
}
