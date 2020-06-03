package top.alertcode.adelina.framework.base.annotation;


import top.alertcode.adelina.framework.base.entity.DbOperatorType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**

 * 创建时间：2019/3/22
 * 功能描述:
 * <p>
 * 修订记录：
 * @version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD})
@Documented
public @interface DBWhereItem {

    /**标签，用于多项配置，试用不同的查询*/
    String label() default "default";
    /**查询条件顺序索引*/
    int index() default 0;
    /**查询条件类型*/
    DbOperatorType operateType() default DbOperatorType.EQ;
    /**数据库字段名*/
    String dbField() default "";
    /**Java字段名*/
    String beanField() default "";
    /**其他值的获取字段名，用于Between运算符*/
    String otherValueFiledName() default "";

}
