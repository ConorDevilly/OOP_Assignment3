package com.conordevilly.ocr.trainer;

import javafx.beans.NamedArg;
import javafx.beans.property.*;

public class Result {
	private final SimpleStringProperty key;
	private final SimpleFloatProperty val;
	
	public Result(@NamedArg("key") char k, @NamedArg("val") float v){
		key = new SimpleStringProperty(Character.toString(k));
		val = new SimpleFloatProperty(v); 
	}
	
	public String getKey(){
		return key.get();
	}
	public Float getVal(){
		return val.get();
	}
}
