package com.hhly.datacenter.startup;

import com.hhly.datacenter.Globals;

/**
 * Created by Administrator on 2016/11/18.
 */
public final class Bootstrap {

    public static void main(String[] args){
        // TODO: 2016/11/18
    }

    public void init(){

        setCollectorHome();

    }

    private void setCollectorHome(){

        if (System.getProperty(Globals.COLLECTOR_HOME_PROP) != null)
            return;
        System.setProperty(Globals.COLLECTOR_HOME_PROP,
                           System.getProperty("user.dir"));

    }

    public static String getCollectorHome(){
        return System.getProperty(Globals.COLLECTOR_HOME_PROP,
                                  System.getProperty("user.dir"));
    }

}
