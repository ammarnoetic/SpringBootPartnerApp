package com.noetic.gwpartner.timwe.Model;

import com.noetic.gwpartner.timwe.constants.Constants;
import org.apache.http.HttpClientConnection;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Initializer {

    static long instanceCounter = 0;
    static Logger log = Logger.getLogger(Initializer.class);
    static HttpClientConnection conn;
    private static HttpClientConnectionManager httpClientConnectionManager;

    public static void main(String[] args) throws Exception {
        //MoAlert moAlert = new MoAlert();
        initializeLogger();
        //moAlert.checkMo();

     //  new MoReceiver(Constants.FileConstants.MO_RECIEVE_PORT.getInt(), Constants.FileConstants.MO_RECIEVE_CONTEXT.getString());


    }

    private static void initializeLogger() {
        if (Constants.FileConstants.LOG4J_USE_PROPERTIES_FILE.getString().equals("true"))
        {
            Properties logProperties = new Properties();

            try
            {
                File file = new File(Constants.FileConstants.LOG4J_PROPERTIES_FILENAME.getString());
                System.out.println("Path of log4j.properties: " + file.getAbsolutePath());
                logProperties.load(new FileInputStream(file));
                PropertyConfigurator.configure(logProperties);
                log.info("Logging initialized from .properties");
            }
            catch (IOException e)
            {
                log.error(e,e);
                e.printStackTrace();
                //throw new RuntimeException("Unable to load logging property " + FileConstants.LOG4J_PROPERTIES_FILENAME.getString());
            }
        }
        else
        {
            DOMConfigurator.configure(Constants.FileConstants.LOG4J_XML_FILENAME.getString());
            log.info("Logging initialized from .xml");

        }
    }
}

