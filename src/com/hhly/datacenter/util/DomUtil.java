package com.hhly.datacenter.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * Created by Verson on 2016/11/22.
 */
public final class DomUtil {

    public static Element getRootElement(String path){

        SAXBuilder builder = new SAXBuilder();
        Element root = null;
        try {
            Document document = builder.build(path);
            root = document.getRootElement();
        } catch (Exception e) {
            //TODO
            return null;
        }
        return root;

    }

    public static Element getChild(Element parent,String child){
        if (parent == null || StringUtil.isEmpty(child))
            return null;
        return parent.getChild(child);
    }

}
