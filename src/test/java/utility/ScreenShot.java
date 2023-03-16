package utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import base.Base;

public class ScreenShot extends Base {

	public void getScreenShot() throws IOException {


		Date currentDate = new Date();
		String screenShotFileName = currentDate.toString().replace(" ", "_").replace(":", "_");
		String base64code = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		byte[] byteArr = Base64.getDecoder().decode(base64code);
		File destFile = new File(".//screenshot//" + screenShotFileName + ".png");
		FileOutputStream fos = new FileOutputStream(destFile);
		fos.write(byteArr);
		fos.close();

		//FileUtils.copyFile(screenShotFile, new File(".//screenshot//" + screenShotFileName + ".png"));



	}
}
