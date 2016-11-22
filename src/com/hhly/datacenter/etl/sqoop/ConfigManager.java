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
                if (!f.getName().endsWith(".xml"))
                    continue;
                createConfig(f.getAbsolutePath());
            }
        }

        /** 监测配置文件目录的变化 */
        TaskConfigDirectoryWatcher.startWatching();

    }

    private static void setConfigLocation(){
        configLocation = Bootstrap.getCollectorHome() + File.separator + "conf" + File.separator + "task" ;
    }

    private static void createConfig(String path){

        Element root = DomUtil.getRootElement(path);
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
                taskConfigMap.put(path,taskConfig);

        } catch (Exception e) {
            //TODO
        }

    }

    private static void modifyConfig(String path){

        Element root = DomUtil.getRootElement(path);
        if (root == null)
            return;

        TaskConfig taskConfig = taskConfigMap.get(path);
        if (taskConfig == null)
            return;

        for (Element child : root.getChildren())
            ReflectUtil.setProperty(taskConfig,child.getName(),child.getText());

    }

    private static void removeConfig(String path){
        taskConfigMap.remove(path);
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

                        String absolutePath = ConfigManager.configLocation + File.separator + fileName;

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE)
                            ConfigManager.createConfig(absolutePath);
                        else if(kind == StandardWatchEventKinds.ENTRY_MODIFY)
                            ConfigManager.modifyConfig(absolutePath);
                        else if (kind == StandardWatchEventKinds.ENTRY_DELETE)
                            ConfigManager.removeConfig(absolutePath);

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
