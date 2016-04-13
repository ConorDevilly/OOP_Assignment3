package testWebService;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
 
public class RESTClient {
	public static void main(String[] args) {
		RESTClient cli = new RESTClient();
		cli.sendJSONFile(new File("src/Test/test.png"));
	}
	
	private void sendJSONFile(File in){
		JSONObject json = new JSONObject();
		
		try{
			String fileData = convFileToString(in);
			json.put("fileData", fileData);

			URL url = new URL("http://localhost:8080/TestingOCRWebService/api/ocr");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(json.toString());
			out.close();

			BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = response.readLine()) != null) {
				System.out.println(line);
			}
			response.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Converts a file to a Base64 encoded string
	private String convFileToString(File in) throws IOException{
		byte[] bytes = Files.readAllBytes(in.toPath());
		String ret = new String(Base64.encode(bytes));
		return ret;

	}
}