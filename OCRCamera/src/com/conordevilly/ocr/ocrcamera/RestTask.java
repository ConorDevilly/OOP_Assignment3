package com.conordevilly.ocr.ocrcamera;

import android.content.Context;
import android.os.AsyncTask;

/*
 * Task to connect to the internet to send the data
 * Android does not allow network activity on the main task so we need to send data via another task
 */
public class RestTask extends AsyncTask<String, Void, String>{
	String url;
	Context context;
	
	//Create an interface to send resposne back to main
	public interface AsyncResponse{
		void processingFinished(String out);
	}
	public AsyncResponse delegate = null;

	public RestTask(String url, Context context, AsyncResponse delegate){
		this.url = url;
		this.context = context;
		this.delegate = delegate;
	}
	
	//Execute the task
	@Override
	protected String doInBackground(String... params) {
		//Create an instance of the client & send the data
		OCRRESTClient cli = new OCRRESTClient(url);
		String toSend = params[0];
		String guess = cli.send(toSend);

		return guess;
	}
	
	//Display the guess once the task has been completed
	protected void onPostExecute(String result){
		super.onPostExecute(result);
		delegate.processingFinished(result);
	}
}
