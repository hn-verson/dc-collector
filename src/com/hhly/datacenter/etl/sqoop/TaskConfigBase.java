package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.util.StringUtil;
import java.util.StringTokenizer;

/**
 * Created by Verson on 2016/11/19.
 */
public abstract class TaskConfigBase implements TaskConfig {

    /**
     * 任务类型
     */
    private String type;

    /**
     * 依赖任务文件列表，分隔符","
     */
    private String[] dependencyTasks;

    @Override
    public boolean isValidate() {
        return !StringUtil.isEmpty(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDependencyTasks(String dependencyTasks) {

        StringTokenizer tokenizer = new StringTokenizer(dependencyTasks.trim(),",");
        int index = 0;
        this.dependencyTasks = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreElements()) {
            this.dependencyTasks[index++] = tokenizer.nextElement().toString();
        }

    }

}
