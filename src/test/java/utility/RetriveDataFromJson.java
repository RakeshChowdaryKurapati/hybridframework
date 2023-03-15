package utility;

public class RetriveDataFromJson {

	
	JSONParser parser = new JSONParser();
    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("path/to/json/file.json"));
}
