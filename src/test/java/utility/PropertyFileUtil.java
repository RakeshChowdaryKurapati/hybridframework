package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileUtil {
	
	 public Properties readProperty(String propertyFilePath) {
	        InputStream input = null;
	        Properties prop = null;
	        try {
	            input = new FileInputStream(propertyFilePath);
	            prop = new Properties();
	            prop.load(input);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return prop;
	    }
}
