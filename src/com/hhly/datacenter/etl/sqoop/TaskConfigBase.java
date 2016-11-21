package com.hhly.datacenter.etl.sqoop;

import java.util.StringTokenizer;

/**
 * Created by Verson on 2016/11/19.
 */
public abstract class TaskConfigBase implements TaskConfig {

    /**
     * 任务标识
     */
    private int taskId = -1;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 应用标识
     */
    private int appId = -1;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 分组标识
     */
    private int groupId = -1;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 是否存在依赖关系
     */
    private boolean isDependency;

    /**
     * 依赖任务标识列表，分隔符","
     */
    private int[] dependencyTaskIds;

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return new StringBuilder().append(appId).append("-")
                .append(groupId).append("-")
                .append(taskId)
                .toString();
    }

    @Override
    public boolean isValidate() {
        if (appId == -1 || groupId == -1 || taskId == -1)
            return false ;
        if (isDependency && (dependencyTaskIds == null || dependencyTaskIds.length == 0))
            return false;
        return true;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = Integer.valueOf(taskId);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public void setAppId(String appId) {
        this.appId = Integer.valueOf(appId);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = Integer.valueOf(groupId);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isDependency() {
        return isDependency;
    }

    public void setDependency(boolean dependency) {
        isDependency = dependency;
    }

    public void setDependency(String dependency) {
        isDependency = Boolean.valueOf(dependency);
    }

    public int[] getDependencyTaskIds() {
        return dependencyTaskIds;
    }

    public void setDependencyTaskIds(int[] dependencyTaskIds) {
        this.dependencyTaskIds = dependencyTaskIds;
    }

    public void setDependencyTaskIds(String dependencyTaskIds) {

        StringTokenizer tokenizer = new StringTokenizer(dependencyTaskIds,",");
        int index = 0;
        this.dependencyTaskIds = new int[tokenizer.countTokens()];

        while (tokenizer.hasMoreElements()) {
            this.dependencyTaskIds[index++] = Integer.valueOf(tokenizer.nextElement().toString());
        }

    }

    @Override
    public String getSystemId() {
        return String.valueOf(taskId);
    }

}
