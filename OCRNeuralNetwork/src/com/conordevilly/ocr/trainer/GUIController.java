package com.conordevilly.ocr.trainer;

import java.io.File;

import com.conordevilly.ocr.*;
import java.util.List;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class GUIController {
	@FXML private Text actiontarget;
	
	@FXML protected void openFileChooser(ActionEvent event){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File imgDir = dirChooser.showDialog(null);
	}
}
