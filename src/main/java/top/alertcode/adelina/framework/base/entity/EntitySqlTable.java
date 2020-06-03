package top.alertcode.adelina.framework.base.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import top.alertcode.adelina.framework.base.annotation.*;
import top.alertcode.adelina.framework.base.struct.CommonSQLHelper;
import top.alertcode.adelina.framework.base.utils.CommonsUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
public class EntitySqlTable {

    private static CommonSQLHelper commonSQLHelper;

    private String tableName;
    private List<EntitySqlColumn> sqlColumns;
    private String className;
    private Boolean userCache;
    private Class entityClass;
    private Boolean enabledRevise;
    private String[] diffExclude;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<EntitySqlColumn> getSqlColumns() {
        return sqlColumns;
    }

    public void setSqlColumns(List<EntitySqlColumn> sqlColumns) {
        this.sqlColumns = sqlColumns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void addColumn(EntitySqlColumn column) {
        if (sqlColumns == null) {
            sqlColumns = new ArrayList<>();
        }
        sqlColumns.add(column);
    }

    public Boolean getUserCache() {
        return userCache;
    }

    public void setUserCache(Boolean userCache) {
        this.userCache = userCache;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String[] getDiffExclude() {
        return diffExclude;
    }

    public void setDiffExclude(String[] diffExclude) {
        this.diffExclude = diffExclude;
    }

    public Boolean getEnabledRevise() {
        return enabledRevise;
    }

    public void setEnabledRevise(Boolean enabledRevise) {
        this.enabledRevise = enabledRevise;
    }

    public void sortField(Field[] allField) {
        if (sqlColumns == null) {
            return;
        }
        sqlColumns.sort(new Comparator<EntitySqlColumn>() {
            @Override
            public int compare(EntitySqlColumn o1, EntitySqlColumn o2) {
                int i1 = getIndex(o1.getAttributeName(), allField);
                int i2 = getIndex(o2.getAttributeName(), allField);
                return i1 - i2;
            }
        });
    }

    static int getIndex(String name, Field[] allField) {
        for (int i = 0; i < allField.length; i++) {
            if (name.equals(allField[i].getName())) {
                return i;
            }
        }
        return -1;
    }

    public EntitySqlColumn getEntitySqlColumn(String javaName) {
        for (EntitySqlColumn sqlColumn : getSqlColumns()) {
            if (sqlColumn.getAttributeName().equals(javaName)) {
                return sqlColumn;
            }
        }
        return null;
    }

    /**
     * Freemarker 获取对象的属性值
     * @param expr
     * @param data
     * @return
     */
    public Object getOgnlValue(String expr, Object data) {
        if (commonSQLHelper == null) {
            commonSQLHelper = new CommonSQLHelper();
        }
        Object obj = commonSQLHelper.getOgnlValue(expr, data);
        return obj;
    }


    public String getSQLValue(String sqlText) {
        return CommonsUtils.escapeSql(sqlText.replace("\\", "\\\\"));
    }


    <T> T getAnnotationAll(Class clazz, Class<T> clazzT) {
        if (clazz == null || clazz == Object.class) {
            return null;
        }
        T dbIndex = (T) clazz.getAnnotation(clazzT);
        if (dbIndex != null) {
            return dbIndex;
        }
        return getAnnotationAll(clazz.getSuperclass(), clazzT);
    }



    void addDBIndex(StringBuffer buffer, String unique, String name, String[] keys, String index, String comment) {
        buffer.append(",\n ").append(unique).append((unique == null || unique.length() == 0) ? "" : " ").append("INDEX `").append(name).append("`(");
        boolean first = true;
        for (String c : keys) {
            if (first) {
                first = false;
            } else {
                buffer.append(",");
            }
            EntitySqlColumn sqlColumn = getEntitySqlColumn(c);
            if (sqlColumn != null) {
                c = sqlColumn.getColumnName();
            }
            buffer.append("`").append(c).append("`");
        }
        buffer.append(") USING ").append(index);
        buffer.append(" COMMENT '").append(comment).append("'");
    }


}
