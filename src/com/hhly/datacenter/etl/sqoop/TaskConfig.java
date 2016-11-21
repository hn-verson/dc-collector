package com.hhly.datacenter.etl.sqoop;

/**
 * Created by Verson on 2016/11/19.
 */
public interface TaskConfig {

    String IMPORT_TYPE = "import";
    String CLEAN_TYPE = "clean";
    String EXPORT_TYPE = "export";

    /**
     * 任务配置有效性验证
     * @return
     */
    boolean isValidate();

    /**
     * 任务配置唯一标识
     * @return
     */
    String getSystemId();

}
