package com.conordevilly.ocr.trainer;

import java.io.File;
import java.util.Arrays;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

public class GUIController {
	private File[] imgList;
	private int imgListIterator;
	@FXML private ImageView imageView;
	@FXML private TextField ansBox;
	
	NeuralNetwork nn;
	
	public void setNetwork(NeuralNetwork network){
		nn = network;
	}
	
	@FXML protected void openFileChooser(ActionEvent event){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File imgDir = dirChooser.showDialog(null);
		imgList = imgDir.listFiles();
		Arrays.sort(imgList);
		imgListIterator = 0;
		loadImage(null);
	}
	
	@FXML protected void loadImage(ActionEvent event){
		try {
			Image img = new Image(imgList[imgListIterator].toURI().toString());
			imageView.setImage(img);
			imgListIterator++;
			
			//Reset the iterator if all images ran
			//TODO: Warning text
			imgListIterator = (imgListIterator + 1 == imgList.length) ? 0 : imgListIterator++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML protected void recordAnswer(ActionEvent event){
		//TODO: Check text set
		System.out.println(ansBox.getText(0, 1).toUpperCase());
		ansBox.clear();
		loadImage(null);
	}
}
