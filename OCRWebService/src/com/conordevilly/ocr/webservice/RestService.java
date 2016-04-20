package com.conordevilly.ocr.webservice;

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
	
	/*
	 * Service to allow NN to guess over the internet
	 * Reads in JSON String
	 * This string contains a Base64 encoded string that, when decoded, is an Image
	 * This image is ran through the NN and a guess is returned
	 */
	@POST
	@Path("/ocr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response process(InputStream incomingData) {
		StringBuilder respBuilder = new StringBuilder();
		String encodedData = null;
		String guess = null;
		File data = null;

		//Create the NN if it does not exist, read it 
		if(nn == null){
			nn = PersistanceManager.readNN(new File("nn.data"));
			//If still null, create a new NN
			if(nn == null){
				nn = new NeuralNetwork(10, 26);
			}
		}

		try {
			//Read the input
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				respBuilder.append(line);
			}

			//Put input into json object
			JSONObject json = new JSONObject(respBuilder.toString());
			
			//Get encoded data, decode it and turn into an image
			encodedData = (String) json.get("fileData");
			data = convStringToFile(encodedData);
			BufferedImage input = ImageIO.read(data);
			
			//Run the image through the NN and get a guess
			guess = nn.processMultiImage(input);
		}catch(IOException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		//Return the guess
		return Response.status(200).entity(guess).build();
	}
	
	//Converts a string of encoded data to a file
	private File convStringToFile(String encodedData) throws IOException{
		File ret = new File("tmp");
		byte[] rawData = Base64.getDecoder().decode(encodedData);

		FileOutputStream writer = new FileOutputStream(ret);
		writer.write(rawData);
		writer.close();

		return ret;
	}

	//Test to check if service is reachable
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testREST() {
		String result = "Hello World - Why are you bothering me?";
		return Response.status(200).entity(result).build();
	}
}