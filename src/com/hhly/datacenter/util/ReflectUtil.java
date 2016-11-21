package com.hhly.datacenter.util;

import java.lang.reflect.Method;
import java.util.Hashtable;

/**
 * Created by Verson on 2016/11/21.
 */
public class ReflectUtil {

    public static boolean setProperty(Object o,String name,String value){

        String setter = "set" + capitalize(name);

        Method methods[] = findMethods(o.getClass());

        try {
            for (int i = 0; i < methods.length; i++) {
                Class<?> paramT[] = methods[i].getParameterTypes();
                if (setter.equals(methods[i].getName()) && paramT.length == 1
                        && "java.lang.String".equals(paramT[0].getName())) {

                    methods[i].invoke(o, new Object[] { value });
                    return true;
                }
            }
        } catch (Exception e) {
            //TODO
        }

        return false;

    }

    /**
     * Reverse of Introspector.decapitalize
     */
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    static Hashtable<Class<?>,Method[]> objectMethods =
            new Hashtable<Class<?>,Method[]>();

    public static Method[] findMethods(Class<?> c) {
        Method methods[] = objectMethods.get(c);
        if (methods != null)
            return methods;

        methods = c.getMethods();
        objectMethods.put(c, methods);
        return methods;
    }

}
