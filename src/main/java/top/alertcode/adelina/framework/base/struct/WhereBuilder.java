package top.alertcode.adelina.framework.base.struct;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.alertcode.adelina.framework.base.annotation.DBWhere;
import top.alertcode.adelina.framework.base.annotation.DBWhereItem;
import top.alertcode.adelina.framework.base.entity.DbOperatorType;
import top.alertcode.adelina.framework.base.utils.StringJavaSqlName;
import top.alertcode.adelina.framework.utils.AssertUtils;
import top.alertcode.adelina.framework.utils.BeanUtils;
import top.alertcode.adelina.framework.utils.MapUtils;
import top.alertcode.adelina.framework.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**

 * 创建时间：2019/3/22
 * 功能描述:
 * <p>
 * 修订记录：
 * @version 1.0
 **/
@Slf4j
public class WhereBuilder {
    private WhereBuilder() {
        //no nothing
    }

    /***
     * 根据输入的查询对象生成WhereCondition查询条件对象
     * @param whereCriteria
     * @return
     */
    public static WhereCondition buildWhereCondition(Object whereCriteria) {
        return buildWhereCondition(whereCriteria, null);
    }

    /***
     * 根据输入的查询对象生成WhereCondition查询条件对象
     * @param whereCriteria
     * @param label         条件标签
     * @return
     */
    public static WhereCondition buildWhereCondition(Object whereCriteria, String label) {
        Class clazz = whereCriteria.getClass();
        //clazz.get
        DBWhere dbWhere = (DBWhere) clazz.getAnnotation(DBWhere.class);
        Map<String, DBWhereItem> itemMap = getFieldWhereItemMapper(clazz);
        if (MapUtils.isNotEmpty(itemMap)) {
            return buildWhereConditionByAnnotation(whereCriteria, dbWhere, itemMap, label);
        } else {
            /* 暂时注释掉，后续扩展
             List<String> fieldList = ClassUtils.getEntityJavaFields(clazz);*／
            */
        }
        return null;
    }


    /***
     * 根据注解构建查询条件
     * @param whereCriteria     查询条件Dto对象
     * @param dbWhere           查询条件注解
     * @param itemMap           字段名称对应的注解
     * @param label             标签（用于筛选出不同的查询设置）
     * @return
     */
    private static WhereCondition buildWhereConditionByAnnotation(Object whereCriteria,
                                                                  DBWhere dbWhere, Map<String, DBWhereItem> itemMap, String label) {
        WhereCondition where = new WhereCondition();
        String useLabel = StringUtils.isEmpty(label) ? "default" : label;
        List<ConditionItem> list = getConditionItemList(dbWhere, itemMap, useLabel);
        for (ConditionItem item : list) {
            Object objValue = BeanUtils.getPropertyValue(whereCriteria, item.getValueFieldName());
            if (objValue != null) {
                //判断是否是数组
                if (isArray(objValue)) {
                    // 判断数组是否是空
                    if (((Object[]) objValue).length > 0) {
                        where.addCondition(item.getDbFieldName(), item.getOperatorType(), (Object[]) objValue);
                    }
                } else if (objValue instanceof String && item.getOperatorType() != DbOperatorType.BETWEEN) {
                    String value = (String) objValue;
                    // 字符串是""时，不添加条件
                    if (StringUtils.isBlank(value)) {
                        continue;
                    }
                    where.addCondition(item.getDbFieldName(), item.getOperatorType(), value);
                } else {
                    Object otherValue = null;
                    if (StringUtils.isNotEmpty(item.getOtherField())) {
                        otherValue = BeanUtils.getPropertyValue(whereCriteria, item.getOtherField());
                    }
                    if (otherValue != null) {
                        where.addCondition(item.getDbFieldName(), item.getOperatorType(), objValue, otherValue);
                    } else {
                        log.warn("--------------------------------> @DBWhereItem注解中的otherValueFiledName指定的字段值为null");
                        where.addCondition(item.getDbFieldName(), item.getOperatorType(), objValue, StringUtils.EMPTY);
                    }
                }
            }
        }
        if (dbWhere != null && dbWhere.selectFields() != null && dbWhere.selectFields().length > 0) {
            where.addQueryFields(dbWhere.selectFields());
        }
        if (dbWhere != null && StringUtils.isNotEmpty(dbWhere.orderBy())) {
            where.addOrderBy(dbWhere.orderBy());
        }
        return where;
    }

    /***
     * 判断对象是否是数组
     * @param objValue 对象值
     * @return
     */
    private static boolean isArray(Object objValue) {
        if (objValue == null) {
            return false;
        }
        return objValue.getClass().isArray();
    }

    /***
     * 根据属性字段名称自动构建查询条件
     * @param whereCriteria 查询条件Dto对象
     * @param fieldList     字段名称
     * @return
     */
    private static WhereCondition buildWhereConditonByDefault(Object whereCriteria,
                                                              List<String> fieldList) {
        //TODO：
        return null;
    }


    /***
     * 获取Bean字段对应的条件项
     * @param clazz  类对象
     * @return
     */
    private static Map<String, DBWhereItem> getFieldWhereItemMapper(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, DBWhereItem> result = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            DBWhereItem item = getFieldWhereItem(field);
            if (item != null) {
                result.put(field.getName(), item);
            }
        }
        return result;
    }

    /***
     * 获取单个字段的条件项（DBWhereItem）
     * @param field
     * @return
     */
    private static DBWhereItem getFieldWhereItem(Field field) {
        AssertUtils.notNull(field, "字段不能是空");
        DBWhereItem item = field.getAnnotation(DBWhereItem.class);
        return item;
    }

    /**
     * 返回设定的查询条件列表，并按index顺序按小到大排序
     * @param dbWhere
     * @param itemMap
     * @param label     选择标签
     * @return
     */
    private static List<ConditionItem> getConditionItemList(DBWhere dbWhere,
                                                            Map<String, DBWhereItem> itemMap,
                                                            String label) {
        List<ConditionItem> list = new ArrayList<>();
        DBWhereItem[] whereItems = dbWhere == null ? null : dbWhere.whereItem();
        if (whereItems != null && whereItems.length > 0) {
            for (DBWhereItem item : whereItems) {
                if (item.label().equals(label)) {
                    list.add(toConditionItem(item, null));
                }
            }
        }
        if (MapUtils.isNotEmpty(itemMap)) {
            for (Map.Entry<String, DBWhereItem> entry : itemMap.entrySet()) {
                if (entry.getValue().label().equals(label)) {
                    list.add(toConditionItem(entry.getValue(), entry.getKey()));
                }
            }
        }
        //按index排序
        Collections.sort(list);
        return list;
    }


    /**
     * 转换成查询条件项
     * @param item       注解中的条件设置项
     * @param fieldName  Java字段名称
     * @return
     */
    private static ConditionItem toConditionItem(DBWhereItem item, String fieldName) {
        if (StringUtils.isEmpty(item.beanField()) && StringUtils.isEmpty(fieldName)) {
            throw new IllegalArgumentException("未指定查询字段名称");
        }
        ConditionItem conditionItem = new ConditionItem();
        conditionItem.setIndex(item.index());
        conditionItem.setOperatorType(item.operateType());
        if (StringUtils.isNotEmpty(item.beanField())) {
            conditionItem.setValueFieldName(item.beanField());
        } else {
            conditionItem.setValueFieldName(fieldName);
        }
        //数据库字段名称
        if ((StringUtils.isNotEmpty(item.dbField()))) {
            conditionItem.setDbFieldName(item.dbField());
        } else {
            conditionItem.setDbFieldName(StringJavaSqlName.getSQLNameFormJava(conditionItem.getValueFieldName()));
        }
        //其他字段名称
        if (StringUtils.isNotEmpty(item.otherValueFiledName())) {
            conditionItem.setOtherField(item.otherValueFiledName());
        }
        return conditionItem;
    }

    @Data
    public static class ConditionItem implements Comparable<ConditionItem> {
        /**查询条件顺序*/
        private int index;
        /**查询条件运算类型*/
        private DbOperatorType operatorType;
        /**查询条件字段名称（数据库字段名）*/
        private String dbFieldName;
        /**查询条件字段名称（java）*/
        private String valueFieldName;
        /**其他字段取值（用于Between）*/
        private String otherField;

        @Override
        public int compareTo(ConditionItem o) {
            return this.index - o.index;
        }
    }
}
