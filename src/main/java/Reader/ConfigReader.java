package Reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;
    private final String propertyFilePath = "config.properties"; 

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertyFilePath)) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getChromeDriverPath(){
        String driverPath = properties.getProperty("webdriver.chrome.driver");
        if(driverPath != null) return driverPath;
        else throw new RuntimeException("webdriver.chrome.driver not specified in the config.properties file.");
    }

    public String getFirefoxDriverPath() {
        String driverPath = properties.getProperty("webdriver.FF.driver");
        if(driverPath != null) return driverPath;
        else throw new RuntimeException("webdriver.FF.driver not specified in the config.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicit.wait");
        if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicit.wait not specified in the config.properties file.");
    }

    public long getPageLoadWait() {
        String pageLoadWait = properties.getProperty("page.load.wait");
        if(pageLoadWait != null) return Long.parseLong(pageLoadWait);
        else throw new RuntimeException("page.load.wait not specified in the config.properties file.");
    }

    public long getExplicitlyWait() {
        String explicitlyWait = properties.getProperty("explicit.wait");
        if(explicitlyWait != null) return Long.parseLong(explicitlyWait);
        else throw new RuntimeException("explicit.wait not specified in the config.properties file.");
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if(url != null) return url;
        else throw new RuntimeException("url not specified in the config.properties file.");
    }

    public String getScreenshotPath() {
        return properties.getProperty("screenshot.path");
    }

	public String getChromeLogFilePath() {
		String path = properties.getProperty("webdriver.chrome.logfile");
        if(path != null) return path;
        else throw new RuntimeException("path not specified in the config.properties file.");
	}
}
