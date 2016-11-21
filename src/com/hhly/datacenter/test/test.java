package com.hhly.datacenter.test;

import java.io.File;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by Administrator on 2016/11/18.
 */
public class test {
    public static void main(String args[]) throws Exception{
        StringTokenizer tokenizer = new StringTokenizer("verson,sharon,jettyk,andrea",",");
        System.out.println(tokenizer.countTokens());

        while (tokenizer.hasMoreElements()) {
            System.out.println(tokenizer.nextElement());
        }

    }
}
