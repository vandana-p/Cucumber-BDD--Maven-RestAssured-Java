package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base {
	public Properties urlprop;
	public Properties testdataprop;

	private static Logger logger = LoggerFactory.getLogger(Base.class);

	public Base() {
		urlprop = new Properties();
		testdataprop = new Properties();
		try {
			FileInputStream url_prop = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/environments/endpointUrl.properties");
			urlprop.load(url_prop);

			FileInputStream testdata_prop = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/environments/testDataFilePath.properties");
			testdataprop.load(testdata_prop);
			logger.info("INFO: Loaded properties file:");
			
		} catch (IOException e) {
			logger.error("ERROR: Exception occurred while reading properties file:- {}", e.getMessage());
			e.printStackTrace();
		}
	}
}
