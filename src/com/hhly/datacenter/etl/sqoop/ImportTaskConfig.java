package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.util.ArrayUtil;
import com.hhly.datacenter.util.StringUtil;
import java.util.StringTokenizer;

/**
 * Created by verson on 16-11-28.
 */
public class ImportTaskConfig extends TaskConfigBase {

    /**
     * 是否增量同步
     */
    private boolean isIncrement;

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
     * 任务切分字段
     */
    private String splitField;

    /**
     * 查询参数列表
     */
    private String[] params;

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
     * 分区方式
     */
    private String partitionWay;

    /**
     * 分区字段列表
     */
    private String[] partitionFields;

    protected enum PartitionWay{

        YEAR("year"),
        MONTH("month"),
        DAY("day");

        private String value;

        PartitionWay(String value) {
            this.value = value;
        }

        public String valueOf() {
            return value;
        }

        public static boolean contains(String value){
            return  YEAR.valueOf().equals(value) ||
                    MONTH.valueOf().equals(value) ||
                    DAY.valueOf().equals(value);
        }

    }

    @Override
    public boolean isValidate(){
        if (StringUtil.isEmpty(srcNaming) ||
                StringUtil.isEmpty(destNaming) ||
                StringUtil.isEmpty(query) ||
                StringUtil.isEmpty(targetTable) ||
                StringUtil.isEmpty(splitField) ||
                (isIncrement && (ArrayUtil.isEmpty(params) || !PartitionWay.contains(partitionWay) || ArrayUtil.isEmpty(partitionFields)))) {
            return false ;
        }
        return super.isValidate();
    }

    public boolean isIncrement() {
        return isIncrement;
    }

    public void setIsIncrement(String isIncrement) {
        this.isIncrement = StringUtil.isEmpty(isIncrement) ? false : Boolean.valueOf(isIncrement.trim());
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

    public String getSplitField() {
        return splitField;
    }

    public void setSplitField(String splitField) {
        this.splitField = splitField;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String params) {
        StringTokenizer tokenizer = new StringTokenizer(params.trim(),",");
        int index = 0;
        this.params = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreElements()) {
            this.params[index++] = tokenizer.nextElement().toString();
        }
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

    public String getPartitionWay() {
        return partitionWay;
    }

    public void setPartitionWay(String partitionWay) {
        this.partitionWay = partitionWay;
    }

    public String[] getPartitionFields() {
        return partitionFields;
    }

    public void setPartitionFields(String partitionFields) {
        StringTokenizer tokenizer = new StringTokenizer(partitionFields.trim(),",");
        int index = 0;
        this.partitionFields = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreElements()) {
            this.partitionFields[index++] = tokenizer.nextElement().toString();
        }
    }

}
