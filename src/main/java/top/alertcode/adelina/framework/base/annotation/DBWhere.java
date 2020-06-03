package top.alertcode.adelina.framework.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**

 * 创建时间：2019/3/22
 * 功能描述: 描述Class的查询条件
 * <p>
 * 修订记录：
 * @version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
@Documented
public @interface DBWhere {

    /**标签，用于多项配置，试用不同的查询*/
    String label() default "default";

    /**排序条件*/
    String orderBy() default "id desc";

    /**获取的字典名称*/
    String[] selectFields() default {};

    /**具体的查询项目*/
    DBWhereItem[] whereItem() default {};

}
