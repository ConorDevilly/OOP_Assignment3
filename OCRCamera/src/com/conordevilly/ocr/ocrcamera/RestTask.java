package com.conordevilly.ocr.ocrcamera;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/*
 * Task to connect to the internet to send the data
 * Android does not allow network activity on the main task so we need to send data via another task
 */
public class RestTask extends AsyncTask<String, Void, String>{
	String url;
	Context context;

	public RestTask(String url, Context context){
		this.url = url;
		this.context = context;
	}
	
	//Execute the task
	@Override
	protected String doInBackground(String... params) {
		//Create an instance of the client & send the data
		OCRRESTClient cli = new OCRRESTClient(url);
		String toSend = params[0];
		String guess = cli.send(toSend);

		Log.i("OCR Guess:", guess);
		return guess;
	}
	
	//Display the guess once the task has been completed
	protected void onPostExecute(String result){
		super.onPostExecute(result);
		Builder alertBuilder = new AlertDialog.Builder(context);
		AlertDialog dia = alertBuilder.create();
		dia.setTitle("Guess");
		dia.setMessage("Guess: " + result);
		dia.show();
	}
}
