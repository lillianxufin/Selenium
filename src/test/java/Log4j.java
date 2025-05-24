package main;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j {

	private static Logger Log = LogManager.getLogger(Log4j.class);
	
	public static void main(String[] args) {
		Log.error("Error");
		Log.debug("Debug");
		Log.info("Info");
	}


}