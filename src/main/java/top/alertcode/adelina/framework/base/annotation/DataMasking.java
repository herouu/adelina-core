package top.alertcode.adelina.framework.base.annotation;

import java.lang.annotation.*;

/**

 * 创建时间：2020/4/10
 * 功能描述: 标记需要脱敏处理的方法名
 * <p>
 * 修订记录：
 * @version 1.0
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataMasking {
    /**
     * 需要进行脱敏的字段
     * 参数规则为：
     * 脱敏类型:字段名
     * */
    String[] propertieNames() default {};
}
