package com.hhly.datacenter.etl.sqoop;

/**
 * Created by Verson on 2016/11/21.
 */
public enum TaskType {

    IMPORT(TaskConfig.IMPORT_TYPE,ImportTaskConfig.class,ImportHandler.class),
    CLEAN(TaskConfig.CLEAN_TYPE,CleanTaskConfig.class,CleanHandler.class),
    EXPORT(TaskConfig.EXPORT_TYPE,ExportTaskConfig.class,ExportHandler.class);

    private String type;
    private Class taskConfigClazz;
    private Class taskHandlerClazz;

    TaskType(String type,Class taskConfigClazz,Class taskHandlerClazz) {
        this.type = type;
        this.taskConfigClazz = taskConfigClazz;
        this.taskHandlerClazz = taskHandlerClazz;
    }

    public String getType(){
        return type;
    }

    public String getTaskConfigClazz() {
        return taskConfigClazz.getCanonicalName();
    }

    public Class getTaskHandlerClazz() {
        return taskHandlerClazz;
    }

}
