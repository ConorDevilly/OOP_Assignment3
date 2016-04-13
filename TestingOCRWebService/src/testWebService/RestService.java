package testWebService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/")
public class RestService {
	@POST
	@Path("/ocr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response processData(InputStream incomingData) {
		StringBuilder strBuilder = new StringBuilder();

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;

			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return Response.status(200).entity(strBuilder.toString()).build();
	}
 
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testREST(InputStream incomingData) {
		String result = "Hello World!";
		return Response.status(200).entity(result).build();
	}
}