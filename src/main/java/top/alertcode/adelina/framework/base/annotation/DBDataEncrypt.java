package top.alertcode.adelina.framework.base.annotation;

import java.lang.annotation.*;

/**

 * 创建时间：2020/4/9
 * 功能描述: 标记字段数据在存储到数据库中时需要进行加密
 * <p>
 * 修订记录：
 * @version 1.0
 **/
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DBDataEncrypt {
}
