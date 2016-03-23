package com.conordevilly.ocr.trainer;

import com.conordevilly.ocr.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.Text;

public class GUIController {
	@FXML private Text actionTarget;
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		actionTarget.setText("Button Pressed");
	}
}
