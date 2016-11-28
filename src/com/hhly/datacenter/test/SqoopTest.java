package com.hhly.datacenter.test;

import com.cloudera.sqoop.tool.SqoopTool;
import com.cloudera.sqoop.util.OptionsFileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.sqoop.Sqoop;

import java.util.concurrent.TimeUnit;

/**
 * Created by verson on 16-11-25.
 */
public class SqoopTest {

    public static void main(String[] args){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("Current date is:" + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        //NOOP
                    }
                }
            }
        }).start();

        args = new String[]{
                "--connect","jdbc:mysql://localhost:3306/test",
                "--username","root",
                "--password","Verson0804",
                "--table","user_info",
                "--columns","name,sex,age,create_time",
                "--hive-table","user_info",
                "--hive-import",
                "--outdir",
                "/tmp",
                "--m","1"
        };

        String[] expandedArgs = null;
        try {
            expandedArgs = OptionsFileUtil.expandArguments(args);
        } catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println("Try 'sqoop help' for usage.");
        }

        SqoopTool tool = SqoopTool.getTool("import");

        Configuration conf = new Configuration();
        Configuration pluginConf = SqoopTool.loadPlugins(conf);

        Sqoop sqoop = new Sqoop(tool, pluginConf);
        Sqoop.runSqoop(sqoop, expandedArgs);

    }

}
