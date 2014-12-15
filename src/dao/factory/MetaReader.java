package dao.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MetaReader {

	private Properties properties;
	
	public MetaReader(String filePath) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
