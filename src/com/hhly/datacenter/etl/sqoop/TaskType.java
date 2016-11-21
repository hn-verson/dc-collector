package com.hhly.datacenter.etl.sqoop;

/**
 * Created by Verson on 2016/11/21.
 */
public enum TaskType {

    IMPORT("import",SyncTaskConfig.class),
    CLEAN("clean",SyncTaskConfig.class),
    EXPORT("export",SyncTaskConfig.class);

    private String type;
    private Class taskConfigClazz;

    TaskType(String type,Class taskConfigClazz) {
        this.type = type;
        this.taskConfigClazz = taskConfigClazz;
    }

    public String getType(){
        return type;
    }

    public String getTaskConfigClazz() {
        return taskConfigClazz.getCanonicalName();
    }

}
