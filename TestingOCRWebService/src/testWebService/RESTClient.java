package testWebService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
 
import org.json.JSONObject;
 
public class RESTClient {
	public static void main(String[] args) {
		String str= "";
		try {
			InputStream fileIn = new FileInputStream("src/test.json");
			InputStreamReader reader = new InputStreamReader(fileIn);
			BufferedReader br = new BufferedReader(reader);
			String line;

			while ((line = br.readLine()) != null) {
				str += line + "\n";
			}
 
			JSONObject jsonObject = new JSONObject(str);
			System.out.println(jsonObject);
 
			try {
				URL url = new URL("http://localhost:8080/TestingOCRWebService/api/ocr");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				while (in.readLine() != null) {
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
 
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}