package com.hhly.datacenter.test;

import com.hhly.datacenter.etl.sqoop.JdbcProperties;

import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Administrator on 2016/11/18.
 */
public class test {
    public static void main(String args[]) throws Exception{
        HashMap<String,Properties> nameProperties = JdbcProperties.nameProperties;
        System.out.println(nameProperties);
    }
}
