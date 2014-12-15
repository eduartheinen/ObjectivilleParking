package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {
	private static Properties prop = null;
	private static FileInputStream file = null;

	public static Properties loadProperties() throws IOException {
		if (prop == null) {
			prop = new Properties();
		}

		if (file == null) {
			file = new FileInputStream("./src/config.properties");
		}

		prop.load(file);
		return prop;
	}

	public static String getProperty(String properties) {
		try {
			Properties prop = loadProperties();
			return prop.getProperty(properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
