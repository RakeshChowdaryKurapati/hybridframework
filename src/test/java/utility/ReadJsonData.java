package utility;



import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static base.Base.scenarioTags;



public class ReadJsonData {

	
	 private Gson gson = new Gson();
	    JsonObject jsObj = null;
	    
	    
	    public JsonObject getTestDataFromJson(String filePath) throws IOException {

	        //LOGGER.debug("Test data is loaded from file " + filePath);
	        String jsonStr = FileUtils.readFileToString(new File(filePath), "UTF-8");
	        jsObj = gson.fromJson(jsonStr, JsonObject.class);
	        return jsObj;
	    }
	    
	    public String getTestDataValueFromJson(String testDataName, int dataSetIndexNo, String dataKey) {
	        JsonArray jsonKeyValueArr = jsObj.get(testDataName).getAsJsonArray();
	        String value = null;
	        try {
	            value = jsonKeyValueArr.get(dataSetIndexNo - 1).getAsJsonObject().get(dataKey).getAsString();
	        } catch (Exception e) {
	            value = null;
	        }
	        return value;
	    }
	    public void writeDataIntoJson(String filePath, String testDataName, int dataSetIndexNo, String dataKey, String dataValue){
	        JsonObject jsObject = null;
	        try {
	            if(scenarioTags.contains("@UAT"))
	            {
	                jsObject = getTestDataFromJson(System.getProperty("user.dir") +"\\src\\test\\resources\\TestData\\UATSerialNumber.json");
	            }else
	            {
	                jsObject = getTestDataFromJson(System.getProperty("user.dir") +"\\src\\test\\resources\\TestData\\SerialNumber.json");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        JsonArray jsonKeyValueArr = jsObject.get(testDataName).getAsJsonArray();
	        jsonKeyValueArr.get(dataSetIndexNo - 1).getAsJsonObject().addProperty(dataKey, dataValue);
	        String jsonStr = gson.toJson(jsObject);
	        FileWriter writer;
	        try {
	            writer = new FileWriter(filePath);
	            writer.write(jsonStr);
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
}
