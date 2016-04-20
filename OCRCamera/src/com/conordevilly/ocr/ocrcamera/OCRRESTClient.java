package com.conordevilly.ocr.ocrcamera;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.util.Log;

public class OCRRESTClient{
	String address;

	public OCRRESTClient(String address){
		this.address = address;
	}
	
	//Sends a string to the service
	public String send(String toSend){
		String ret = null;

		try {
			//Put the data into a JSON object
			JSONObject json = new JSONObject();
			json.put("fileData", toSend);

			//Make a connection
			URL url = new URL(address + "/api/ocr");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			//Send the data
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(json.toString());
			out.close();

			//Read the response
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				ret += line;
			}
			in.close();
		} catch(Exception e) {
			Log.e("OCR", e.getMessage());
		}

		return ret;
	}
}