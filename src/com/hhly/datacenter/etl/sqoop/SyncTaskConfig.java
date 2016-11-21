package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.util.StringUtil;

/**
 * Created by Verson on 2016/11/19.
 */
public class SyncTaskConfig extends TaskConfigBase {

    /**
     * 数据源JNDI
     */
    private String srcNaming;

    /**
     * 目标库JNDI
     */
    private String destNaming;

    /**
     * 数据提取SELECT
     */
    private String query;

    /**
     * 目标表名称
     */
    private String targetTable;

    /**
     * 输出目标表字段列表
     */
    private String targetTableCols;

    /**
     * 源表与目标表字段类型映射
     * example: srcFieldType1=destFieldType1,srcFieldType2=destFieldType2
     */
    private String columnTypeMap;

    /**
     * 抽取数据的起始时间
     */
    private String startDate;

    /**
     * 抽取数据的截止时间
     */
    private String endDate;

    /**
     * 是否增量，默认为增量
     */
    private boolean isIncrement = true;

    @Override
    public boolean isValidate(){
        if (StringUtil.isEmpty(srcNaming) ||
                StringUtil.isEmpty(destNaming) ||
                StringUtil.isEmpty(query) ||
                StringUtil.isEmpty(targetTable)) {
            return false ;
        }
        return super.isValidate();
    }

    public String getSrcNaming() {
        return srcNaming;
    }

    public void setSrcNaming(String srcNaming) {
        this.srcNaming = srcNaming;
    }

    public String getDestNaming() {
        return destNaming;
    }

    public void setDestNaming(String destNaming) {
        this.destNaming = destNaming;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getTargetTableCols() {
        return targetTableCols;
    }

    public void setTargetTableCols(String targetTableCols) {
        this.targetTableCols = targetTableCols;
    }

    public String getColumnTypeMap() {
        return columnTypeMap;
    }

    public void setColumnTypeMap(String columnTypeMap) {
        this.columnTypeMap = columnTypeMap;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isIncrement() {
        return isIncrement;
    }

    public void setIncrement(boolean increment) {
        isIncrement = increment;
    }

}
