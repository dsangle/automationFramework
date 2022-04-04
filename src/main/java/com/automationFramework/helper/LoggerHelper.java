package com.automationFramework.helper;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * @author sangale_d
 * 
 */
@SuppressWarnings("rawtypes")
public class LoggerHelper {

	private static boolean root = false;

	public static Logger getLogger(Class clas){
		if (root) {
			return Logger.getLogger(clas);
		}
		String log4jLOcation = System.getProperty("user.dir")+"/src/main/resources/config/log4j.properties";
		PropertyConfigurator.configure(log4jLOcation);
		root = true;
		return Logger.getLogger(clas);
	}
}
