package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.startup.CollectorProperties;
import com.hhly.datacenter.util.PropertiesUtil;
import com.hhly.datacenter.util.StringUtil;
import java.io.File;
import java.util.*;

/**
 * Created by Verson on 2016/11/22.
 */
public final class JdbcProperties {

    public static HashMap<String,Properties> nameProperties = new HashMap<>();

    static {
        loadNameProperties();
    }

    private static void loadNameProperties(){

        File home = new File(CollectorProperties.getCollectorHome());
        File conf = new File(home,"conf" + File.separator + "jdbc.properties");

        Properties properties = PropertiesUtil.loadProperties(conf);

        if (properties == null)
            properties = new Properties();

        divideProperties(properties);

    }

    private static void divideProperties(Properties properties){

        if (properties == null)
            return;

        Set<String> propertyNames = properties.stringPropertyNames();

        for (String property : propertyNames) {

            String sourceName = property.substring(0,property.indexOf("/"));
            String key = property.substring(property.indexOf("/") + 1);
            String value = properties.getProperty(property);

            if (!nameProperties.containsKey(sourceName))
                nameProperties.put(sourceName,new Properties());

            Properties p = nameProperties.get(sourceName);
            p.setProperty(key,value);

        }

    }

    public static String getProperty(String sourceName,String key) {

        if (StringUtil.isEmpty(sourceName) || StringUtil.isEmpty(key))
            return null;

        Properties p = nameProperties.get(sourceName);
        if (p == null)
            return null;

        return p.getProperty(key);

    }

}
