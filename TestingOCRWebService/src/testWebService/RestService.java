package testWebService;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.conordevilly.ocr.neuralnetwork.*;
 
@Path("/")
public class RestService {
	@javax.ws.rs.core.Context 
	ServletContext context;
	NeuralNetwork nn;
	
	@POST
	@Path("/ocr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response process(InputStream incomingData) {
		if(nn == null){
			nn = PersistanceManager.readNN(new File("nn.data"));
		}
		StringBuilder respBuilder = new StringBuilder();
		String encodedData = null;
		String guess = null;
		File data = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				respBuilder.append(line);
			}
			JSONObject json = new JSONObject(respBuilder.toString());
			encodedData = (String) json.get("fileData");
			data = convStringToFile(encodedData);
			BufferedImage input = ImageIO.read(data);
			FileOutputStream out = new FileOutputStream(new File("camPic"));
			ImageIO.write(input, "png", out);
			guess = nn.processMultiImage(input);
		}catch(IOException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(guess).build();
	}
	
	/*
	@POST
	@Path("/ocr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response process(JSONObject inputJSON) throws JSONException, IOException{
		System.out.println("Got request");
		String fileData = (String) inputJSON.get("fileData");
		System.out.println("Data: " + fileData);
		File fileIn = convStringToFile(fileData);
		BufferedImage imgIn = ImageIO.read(fileIn);
		String guess = nn.processMultiImage(imgIn);
		System.out.println("Guess: " + guess);
		
		return Response.status(200).entity(guess).build();
	}
	*/
	
	//Converts a string of encoded data to a file
	private File convStringToFile(String encodedData) throws IOException{
		File ret = new File("tmp");
		//byte[] rawData = Base64.decode(encodedData);
		byte[] rawData = Base64.getDecoder().decode(encodedData);

		FileOutputStream writer = new FileOutputStream(ret);
		writer.write(rawData);
		writer.close();

		return ret;
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testREST() {
		String result = "Hello World - Why are you bothering me?";
		return Response.status(200).entity(result).build();
	}
}