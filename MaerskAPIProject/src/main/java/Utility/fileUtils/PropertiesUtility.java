package Utility.fileUtils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtility {

	/**
	 * Method to get all the properties form the configuration file. Properties
	 * file should be present under project directory
	 *
	 */
	public static Properties getApplicationProperties(String configFileName) {
		Properties properties;
		try (InputStream input = new FileInputStream(System.getProperty("user.dir") + "//" + configFileName)) {
			properties = new Properties();
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Error while reading the properties file.", e);
		}
		return properties;
	}

	/**
	 * Method to set the properties in the configuration file in key value format.
	 * Configuration file should be present under project directory
	 */
	public static Properties setApplicationProperties(String propertiesFileName, String key, String value) {
		Properties properties = new Properties();
		File filepath = new File(System.getProperty("user.dir") + "//" + propertiesFileName);

		try (InputStream inStream = new FileInputStream(filepath)) {
			properties.load(inStream);

			try (OutputStream outStream = new FileOutputStream(filepath)) {
				properties.setProperty(key, value);
				properties.store(outStream, null);

				return properties;
			} catch (Exception e) {
				throw new RuntimeException("Error while storing the values in properties file.", e);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error while storing the values in properties file.", e);
		}
	}
}
