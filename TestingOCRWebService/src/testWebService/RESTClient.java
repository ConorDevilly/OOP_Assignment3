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

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
 
public class RESTClient {
	public static void main(String[] args) {
		RESTClient cli = new RESTClient();
		try {
			cli.sendJSONFile(new File("src/Test/test.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendJSONFile(File toSend) throws IOException, JSONException{
		JSONObject json = new JSONObject();
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource("my_rest_address_path");

        JSONObject data_file = new JSONObject();
        data_file.put("fileData", convFileToString(toSend));

        ClientResponse client_response = service.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, data_file);
        System.out.println("Status: "+ client_response.getStatus());

        client.destroy();
	}
	
	//Converts a file to a Base64 encoded string
	private String convFileToString(File in) throws IOException{
		byte[] bytes = Files.readAllBytes(in.toPath());
		String ret = new String(Base64.encode(bytes));
		return ret;

	}
}