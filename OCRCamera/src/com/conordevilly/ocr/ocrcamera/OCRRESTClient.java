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
	
	public String send(String toSend){
		String ret = "";
		URLConnection connection = null;
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put("fileData", toSend);
			Log.i("OCR", json.toString());
			URL url = new URL(address + "/api/ocr");
			connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(json.toString());
			out.close();
		}catch(IOException e){
			Log.e("OCR IOException", e.getMessage());
		} catch (JSONException e) {
			Log.e("OCR JSONException", e.getMessage());
		}

		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		}catch(IOException e){
			Log.e("OCR IOException", "Getting response");
		}
		try{
			String line;
			while((line = in.readLine()) != null){
				ret += line;
			}
			in.close();
		} catch(IOException e) {
			Log.e("OCR: IOException", "Reading Response"); 
		}

		return ret;
	}
}