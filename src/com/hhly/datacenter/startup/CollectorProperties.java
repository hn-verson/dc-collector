package com.hhly.datacenter.startup;

import com.hhly.datacenter.Globals;
import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Verson on 2016/11/19.
 */
public class CollectorProperties {

    private static Properties properties = null;

    static {

        loadProperties();

    }

    private static void loadProperties(){

        InputStream is = null;

        try {
            String configUrl = getConfigUrl();
            if (configUrl != null) {
                is = (new URL(configUrl)).openStream();
            }
        } catch (IOException e) {
            //TODO
        }

        if (is == null) {
            try {
                File home = new File(getCollectorHome());
                File conf = new File(home,"collector.properties");
                is = new FileInputStream(conf);
            } catch (FileNotFoundException e) {
                //TODO
            }
        }

        if (is != null) {
            try {
                properties = new Properties();
                properties.load(is);
            } catch (IOException e) {
                //TODO
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    //TODO
                }
            }
        }

        if (is == null) {
            //TODO
            properties = new Properties();
        }

    }

    /**
     * Return specified property value.
     */
    public static String getProperty(String name){
        return properties.getProperty(name);
    }

    /**
     * Get the value of the configuration URL.
     */
    private static String getConfigUrl(){
        return System.getProperty("collector.config");
    }

    /**
     * Get the value of the collector.home environment variable.
     */
    private static String getCollectorHome(){
        return System.getProperty(Globals.COLLECTOR_HOME_PROP,
                                  System.getProperty("user.dir"));
    }

}
