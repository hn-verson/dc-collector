package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.util.StringUtil;
import java.util.StringTokenizer;

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
     * 查询参数列表
     */
    private String[] queryParams;

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

    public String[] getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String queryParams) {
        StringTokenizer tokenizer = new StringTokenizer(queryParams.trim(),",");
        int index = 0;
        this.queryParams = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreElements()) {
            this.queryParams[index++] = tokenizer.nextElement().toString();
        }
    }

}
