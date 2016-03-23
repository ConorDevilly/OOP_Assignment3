package com.conordevilly.ocr.trainer;

import java.io.File;

import com.conordevilly.ocr.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class GUIController {
	@FXML protected void openFileChooser(ActionEvent event){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File imgDir = dirChooser.showDialog(null);
	}
	
	@FXML protected void loadImage(ActionEvent event){
		
	}
	
	@FXML protected void correctAnswer(ActionEvent event){
		
	}
}
