package com.hhly.datacenter.startup;

import com.hhly.datacenter.Globals;
import com.hhly.datacenter.util.PropertiesUtil;
import java.io.*;
import java.util.Properties;

/**
 * Created by Verson on 2016/11/19.
 */
public final class CollectorProperties {

    private static Properties properties = null;

    static {

        loadProperties();

    }

    private static void loadProperties(){

        File home = new File(getCollectorHome());
        File conf = new File(home,File.separator + "conf" + File.separator + "collector.properties");

        properties = PropertiesUtil.loadProperties(conf);

        if (properties == null)
            properties = new Properties();

    }

    /**
     * Return specified property value.
     */
    public static String getProperty(String name){
        return properties.getProperty(name);
    }

    /**
     * Get the value of the collector.home environment variable.
     */
    public static String getCollectorHome(){
        return System.getProperty(Globals.COLLECTOR_HOME_PROP,
                                  System.getProperty("user.dir"));
    }

}
