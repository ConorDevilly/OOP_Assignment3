package com.conordevilly.ocr.trainer;

import com.conordevilly.ocr.neuralnetwork.NeuralNetwork;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class GUIController {
	private File[] imgList;
	private int imgListIterator;
	@FXML private TableView<Result> resTable;
	@FXML private ImageView imageView;
	@FXML private TextField ansBox;
	@FXML private Text guess;
	private HashMap<Character, Float> results;
	
	NeuralNetwork nn;
	ObservableList<Result> resList;
	
	public void init(NeuralNetwork network){
		nn = network;
		resList = resTable.getItems();
		results = new HashMap<Character, Float>();
	}
	
	@FXML protected void openFileChooser(ActionEvent event){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File imgDir = dirChooser.showDialog(null);
		imgList = imgDir.listFiles();
		//DEBUG
		Arrays.sort(imgList);
		imgListIterator = 0;
		loadImage(null);
	}
	
	@FXML protected void loadImage(ActionEvent event){
		try {
			Image img = new Image(imgList[imgListIterator].toURI().toString());
			BufferedImage bufImg = SwingFXUtils.fromFXImage(img, null);
			results.clear();
			results = nn.trainingProcess(bufImg);
			imageView.setImage(img);
			updateTable(results);
			setGuess(nn.getGuess());
			imgListIterator++;
			
			//Reset the iterator if all images ran
			//TODO: Warning text
			imgListIterator = (imgListIterator + 1 > imgList.length) ? 0 : imgListIterator++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML protected void setGuess(char s){
		guess.setText(Character.toString(s));
	}
	
	@FXML protected void updateTable(HashMap<Character, Float> results){
		resList.clear();
		for(int i = 0; i < results.size(); i++){
			char key = ((char) (i + 65));
			float val = results.get(key);
			resList.add(new Result(key, val));
		}
	}
	
	@FXML protected void recordAnswer(ActionEvent event){
		//TODO: Check text set
		//Convert the string taken from the answer box to a char
		char ans = ansBox.getText(0, 1).toUpperCase().toCharArray()[0];
		ansBox.clear();

		if(!guess.getText().equals(ans)){
			//nn.correct(ans, results);
			//Convert the char to an int form (ASCII table).
			int actual = ans - 65;
			nn.correct(actual);
		}
		loadImage(null);
	}
}
