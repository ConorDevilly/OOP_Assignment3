package com.conordevilly.ocr.ocrcamera;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RestTask extends AsyncTask<File, Void, String>{
	String url;
	Context context;

	public RestTask(String url, Context context){
		this.url = url;
		this.context = context;
	}
	
	@Override
	protected String doInBackground(File... params) {
		OCRRESTClient cli = new OCRRESTClient(url);
		File toSend = params[0];
		String guess = cli.sendJSONFile(toSend);
		Log.i("OCR Guess:", guess);
		return guess;
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
		Builder alertBuilder = new AlertDialog.Builder(context);
		AlertDialog dia = alertBuilder.create();
		dia.setTitle("Guess");
		dia.setMessage("Guess: " + result);
		dia.show();
	}
}
