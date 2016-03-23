package com.conordevilly.ocr.trainer;

import com.conordevilly.ocr.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;

public class Trainer extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
		Scene scene = new Scene(root, 500, 500);
		stage.setTitle("OCR Trainer");
		stage.setScene(scene);
		stage.show();
	}
}
