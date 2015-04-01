package com.demandforce.bluebox.admin.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils
{
    public static final String PROPERTIES_ROOT_PATH = "META-INF/properties";
    //public static final String PROPERTIES_JDBC = "/jdbc.properties";
    //public static final String PROPERTIES_EMAIL_FINDER = "/email_finder.properties";
    public static final String PROPERTIES_ENV = "/env.properties";

    //public static final Properties JDBC = new Properties();
    public static final Properties EMAIL_FINDER = new Properties();
    public static final Properties ENV = new Properties();

    private static Logger logger = Logger.getLogger(PropertiesUtils.class.getName());

    static
    {
        //loadProperties(JDBC, PROPERTIES_JDBC);
        //loadProperties(EMAIL_FINDER, PROPERTIES_EMAIL_FINDER);
        loadProperties(ENV, PROPERTIES_ENV);
    }

    private static Properties loadProperties(Properties properties, String propertiesFileName)
    {
        try
        {
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(
                    PROPERTIES_ROOT_PATH + propertiesFileName));
        } catch (IOException e)
        {
            logger.error("Error occured while loading " + propertiesFileName, e);
        }
        return properties;
    }
}
