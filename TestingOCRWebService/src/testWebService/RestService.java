package testWebService;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.conordevilly.ocr.neuralnetwork.*;
import com.sun.jersey.core.util.Base64;
 
@Path("/")
public class RestService {
	@javax.ws.rs.core.Context 
	ServletContext context;
	NeuralNetwork nn;
	
	public RestService(){
		/*
		NeuralNetwork nn = PersistanceManager.readNN(new File("/nn.data"));
		if(nn == null){
			nn = new NeuralNetwork(10, 26);
		}
		*/
	}
	
	@POST
	@Path("/ocr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response process(JSONObject inputJSON) throws JSONException, IOException{
		String fileData = (String) inputJSON.get("fileData");
		File fileIn = convStringToFile(fileData);
		BufferedImage imgIn = ImageIO.read(fileIn);
		String guess = nn.processMultiImage(imgIn);
		
		return Response.status(200).entity(guess).build();
	}
	
	//Converts a string of encoded data to a file
	private File convStringToFile(String encodedData) throws IOException{
		File ret = new File("/tmp.jpg");
		byte[] rawData = Base64.decode(encodedData);

		FileOutputStream writer = new FileOutputStream(ret);
		writer.write(rawData);
		writer.close();

		return ret;
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testREST() {
		String result = "Hello World!";
		return Response.status(200).entity(result).build();
	}
}