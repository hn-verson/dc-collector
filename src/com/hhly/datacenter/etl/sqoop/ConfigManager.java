package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.startup.Bootstrap;
import com.hhly.datacenter.util.ReflectUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Verson on 2016/11/21.
 */
public class ConfigManager {

    private static String configLocation;
    private static ConcurrentHashMap<String,TaskConfig> taskConfigMap = new ConcurrentHashMap<>();

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
                parse(f.getAbsolutePath());
            }
        }

        /** 监测配置文件目录的变化 */
        TaskConfigDirectoryWatcher.startWatching();

    }

    private static void setConfigLocation(){
        configLocation = Bootstrap.getCollectorHome() + File.separator + "conf/task" ;
    }

    private static void parse(String absolutePath){
        SAXBuilder builder = new SAXBuilder();
        TaskConfig taskConfig;
        try {

            Document document = builder.build(absolutePath);
            Element root = document.getRootElement();
            Element taskTypeElement = root.getChild("type");
            if (taskTypeElement == null)
                //TODO
                return;
            String taskType = taskTypeElement.getText();

            if (TaskConfig.IMPORT_TYPE.equals(taskType))
                taskConfig = (TaskConfig)Class.forName(TaskType.IMPORT.getTaskConfigClazz()).newInstance();
            else if (TaskConfig.CLEAN_TYPE.equals(taskType))
                taskConfig = (TaskConfig)Class.forName(TaskType.CLEAN.getTaskConfigClazz()).newInstance();
            else if (TaskConfig.EXPORT_TYPE.equals(taskType))
                taskConfig = (TaskConfig)Class.forName(TaskType.EXPORT.getTaskConfigClazz()).newInstance();
            else
                //TODO
                return;

            for (Element child : root.getChildren()) {
                ReflectUtil.setProperty(taskConfig,child.getName(),child.getText());
            }

            if (taskConfig.isValidate())
                taskConfigMap.put(taskConfig.getSystemId(),taskConfig);

        } catch (Exception e) {
            //TODO
        }
    }

    private static void removeConfig(String key){
        taskConfigMap.remove(key);
    }

    private static class TaskConfigDirectoryWatcher{

        protected static void startWatching(){

            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(ConfigManager.configLocation);
                path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

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
                        Path absolutePath = ev.context().toAbsolutePath();

                        if (kind == StandardWatchEventKinds.OVERFLOW)
                            continue;

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY)
                            ConfigManager.parse(absolutePath.toString());
                        else if (kind == StandardWatchEventKinds.ENTRY_DELETE)
                            ConfigManager.removeConfig(absolutePath.toString());

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
