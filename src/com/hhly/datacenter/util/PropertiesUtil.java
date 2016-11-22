package com.hhly.datacenter.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by Verson on 2016/11/22.
 */
public final class PropertiesUtil {

    public static Properties loadProperties(File file) {

        if (file == null || !file.exists() || !file.isFile())
            return null;

        InputStream is = null;
        Properties properties = null;

        try {
            is = new FileInputStream(file);
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            //Nothing
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    //Nothing
                }
            }
        }

        return properties;

    }

}
