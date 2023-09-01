package ar.com.jsuper.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadFileConfig {

	private Properties prop;
	private InputStream input = null;

	public ReadFileConfig() {
		prop = new Properties();
		String filename = "config.properties";
		input = ReadFileConfig.class.getClassLoader().getResourceAsStream(filename);
		if (input == null) {
			return;
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getValue(String  value) {
		return prop.getProperty(value);

	}

}
