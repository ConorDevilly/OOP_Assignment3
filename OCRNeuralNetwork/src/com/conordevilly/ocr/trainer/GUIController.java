package com.conordevilly.ocr.trainer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.conordevilly.ocr.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class GUIController {
	private File[] imgList;
	private int imgListIterator;
	@FXML private ImageView imageView;
	
	@FXML protected void openFileChooser(ActionEvent event){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File imgDir = dirChooser.showDialog(null);
		imgList = imgDir.listFiles();
		imgListIterator = 0;
		loadImage(null);
	}
	
	@FXML protected void loadImage(ActionEvent event){
		try {
			Image img = new Image(imgList[imgListIterator].toURI().toString());
			imageView.setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML protected void correctAnswer(ActionEvent event){
		
	}
}
