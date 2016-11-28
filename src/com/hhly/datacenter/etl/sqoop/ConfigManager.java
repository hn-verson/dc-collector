package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.startup.Bootstrap;
import com.hhly.datacenter.util.DomUtil;
import com.hhly.datacenter.util.ReflectUtil;
import org.jdom2.Element;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Verson on 2016/11/21.
 */
public class ConfigManager {

    private static String configLocation;
    private static String fileSuffix = ".xml";
    protected static ConcurrentHashMap<String,TaskConfig> taskConfigMap = new ConcurrentHashMap<>();

    static {
        init();
    }

    private static void init(){

        setConfigLocation();
        File taskConfigDir = new File(configLocation);

        if (taskConfigDir.exists() && taskConfigDir.isDirectory()) {
            File[] fileList = taskConfigDir.listFiles();
            for (File f : fileList) {
                String fileName = f.getName();
                if (!fileName.endsWith(fileSuffix) || fileName.length() <= fileSuffix.length())
                    continue;
                createConfig(fileName);
            }
        }

        /** 监测配置文件目录的变化 */
        TaskConfigDirectoryWatcher.startWatching();

    }

    public static boolean isExistConfig(String fileName){
        return taskConfigMap.contains(fileName);
    }

    public static TaskConfig getTaskConfig(String fileName){
        return taskConfigMap.get(fileName);
    }

    private static void setConfigLocation(){
        configLocation = Bootstrap.getCollectorHome() + File.separator + "conf" + File.separator + "task" ;
    }

    private static String getConfigLocation(){
        return configLocation;
    }

    private static String getAbsolutePath(String fileName){
        return configLocation + File.separator + fileName;
    }

    private static void createConfig(String fileName){

        Element root = DomUtil.getRootElement(getAbsolutePath(fileName));
        if (root == null)
            return;

        TaskConfig taskConfig;

        try {

            Element taskTypeElement = DomUtil.getChild(root,"type");
            if (taskTypeElement == null)
                return;

            String taskType = taskTypeElement.getText();

            if (TaskConfig.IMPORT_TYPE.equals(taskType))
                taskConfig = (TaskConfig)Class.forName(TaskType.IMPORT.getTaskConfigClazz()).newInstance();
            else if (TaskConfig.CLEAN_TYPE.equals(taskType))
                taskConfig = (TaskConfig)Class.forName(TaskType.CLEAN.getTaskConfigClazz()).newInstance();
            else if (TaskConfig.EXPORT_TYPE.equals(taskType))
                taskConfig = (TaskConfig)Class.forName(TaskType.EXPORT.getTaskConfigClazz()).newInstance();
            else
                return;

            for (Element child : root.getChildren())
                ReflectUtil.setProperty(taskConfig,child.getName(),child.getText());

            if (taskConfig.isValidate())
                taskConfigMap.put(fileName,taskConfig);

        } catch (Exception e) {
            //TODO
        }

    }

    private static void modifyConfig(String fileName){

        Element root = DomUtil.getRootElement(getAbsolutePath(fileName));
        if (root == null)
            return;

        TaskConfig taskConfig = taskConfigMap.get(fileName);
        if (taskConfig == null)
            return;

        for (Element child : root.getChildren())
            ReflectUtil.setProperty(taskConfig,child.getName(),child.getText());

    }

    private static void removeConfig(String fileName){
        taskConfigMap.remove(getAbsolutePath(fileName));
    }

    private static class TaskConfigDirectoryWatcher{

        protected static void startWatching(){

            try {
                final WatchService watcher = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(ConfigManager.configLocation);
                path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

                //release resource
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            watcher.close();
                        } catch (IOException e) {
                            //NOOP
                        }
                    }
                });

                while (true) {

                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException ex) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {

                        WatchEvent.Kind<?> kind = event.kind();
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        String fileName = ev.context().toString();

                        if (!fileName.endsWith(".xml"))
                            continue;

                        if (kind == StandardWatchEventKinds.OVERFLOW)
                            continue;

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE)
                            ConfigManager.createConfig(fileName);
                        else if(kind == StandardWatchEventKinds.ENTRY_MODIFY)
                            ConfigManager.modifyConfig(fileName);
                        else if (kind == StandardWatchEventKinds.ENTRY_DELETE)
                            ConfigManager.removeConfig(fileName);

                    }

                    if (!key.reset())
                        break;

                }

            } catch (IOException e) {
                //TODO
            }

        }

    }

}
