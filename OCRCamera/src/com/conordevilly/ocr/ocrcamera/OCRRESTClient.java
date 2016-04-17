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

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class OCRRESTClient extends AsyncTask<File, Object, String>{
	String url;

	public OCRRESTClient(String url){
		this.url = url;
	}
	
	public String sendJSONFile(File toSend){
		return doInBackground(toSend);
	}
	
	@Override
	protected String doInBackground(File... params){
		File toSend = (File) params[0];
		String ret = "";
		String encoded;
		try {
			encoded = convFileToString(toSend);
			JSONObject json = new JSONObject();
			json.put("fileData", encoded);
			URL url = new URL("http://localhost:8080/CrunchifyTutorials/api/crunchifyService");
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
		} catch (IOException e) {
			Log.e("OCR", e.getMessage());
		} catch (JSONException e) {
			Log.e("OCR", e.getMessage());
		}

		return ret;
	}
	/*
	public String sendJSONFile(File toSend) throws IOException, JSONException{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url + "/api/ocr");

        Log.d("OCR", "Set target");

        JSONObject data_file = new JSONObject();
        data_file.put("fileData", convFileToString(toSend));

        Log.d("OCR", "Create Json");

        Response res = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(data_file, MediaType.APPLICATION_JSON));

        Log.d("OCR", "Got resp");

        String guess = (String) res.getEntity();
        client.close();

        return guess;

        //System.out.println("Status: "+ client_response.getStatus());
	}*/
	
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