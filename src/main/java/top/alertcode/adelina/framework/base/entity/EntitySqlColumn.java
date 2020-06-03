package top.alertcode.adelina.framework.base.entity;


import java.lang.reflect.Field;

public class EntitySqlColumn {
    private String columnName;
    private String attributeName;
    private String attributeType;
    private Field field;
    /**标记是否对此字段在数据库中的存储进行加密*/
    private Boolean encrypt = Boolean.FALSE;

    public EntitySqlColumn(String columnName, String attributeName, String attributeType, Field field) {
        setColumnName(columnName);
        setAttributeName(attributeName);
        setAttributeType(attributeType);
        setField(field);
    }

    public EntitySqlColumn(String columnName, String attributeName, String attributeType, Field field, Boolean dbEncryptFlag) {
        setColumnName(columnName);
        setAttributeName(attributeName);
        setAttributeType(attributeType);
        setField(field);
        if (dbEncryptFlag == null) {
            setEncrypt(Boolean.FALSE);
        } else {
            setEncrypt(dbEncryptFlag);
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }
}
