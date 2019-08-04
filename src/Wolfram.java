import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Wolfram {

	private static final String appID = "U54U9R-G9HHG79XX6";
	private static final String urlFragment = "http://api.wolframalpha.com/v1/conversation.jsp?appid=";
	private String question;

	public Wolfram(String question) {
		//these 2 lines replace "+" and "/" signs respectively with their Wolfram API-friendly Strings
		question = question.replaceAll("\\+", "%2B");
		question = question.replaceAll("/", "%2F");
		
		//sets question to same string without spaces (as well as + or / signs)
		this.question = question.replaceAll("\\s", "+");
	}

	public String getAnswer() throws IOException {
		String result = "";
		URL url = new URL(urlFragment + appID + "&i=" + question);

		URLConnection request = url.openConnection(); //open connection to the URL
		request.connect(); //connect to URL

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));																		
		JsonObject rootobj = root.getAsJsonObject();
		
		try {
			result = rootobj.get("result").getAsString(); // store result
		} catch (NullPointerException e) {
			result = rootobj.get("error").getAsString(); //store error
		}
		
		return result;
	}
}
