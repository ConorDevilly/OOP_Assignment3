package com.conordevilly.ocr.ocrcamera;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
/*
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
*/
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

public class OCRRESTClient{
	String address;

	public OCRRESTClient(String address){
		this.address = address;
	}
	
	public String sendJSONFile(File toSend){
		String ret = "";
		String encoded;
		try {
			encoded = convFileToString(toSend);
			JSONObject json = new JSONObject();
			json.put("fileData", encoded);
			URL url = new URL(address + "/api/ocr");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(json.toString());
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				ret += line;
			}
			in.close();
		}/* catch (IOException e) {
			Log.e("OCR: IOException", e.getMessage());
		}*/ catch (JSONException e) {
			Log.e("OCR: JSONException", e.getMessage());
		}

		return ret;
	}
	
	//Converts a file to a Base64 encoded string
	private String convFileToString(File in) throws IOException{
		int size = (int) in.length();
		byte[] bytes = new byte[size];
		
		try {
		    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(in));
		    buf.read(bytes, 0, bytes.length);
		    buf.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		
		String ret = new String(Base64.encode(bytes, Base64.DEFAULT));
		return ret;
	}

}