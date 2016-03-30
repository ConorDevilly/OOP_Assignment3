package com.conordevilly.ocr.trainer;

import javafx.beans.NamedArg;
import javafx.beans.property.*;

public class Result {
	private final SimpleStringProperty key;
	private final SimpleFloatProperty val;
	
	public Result(@NamedArg("key") String k, @NamedArg("val") float v){
		key = new SimpleStringProperty(k);
		val = new SimpleFloatProperty(v); 
	}
	
	public String getKey(){
		return key.get();
	}
	public Float getVal(){
		return val.get();
	}
}
