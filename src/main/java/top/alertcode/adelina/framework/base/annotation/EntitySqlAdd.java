package top.alertcode.adelina.framework.base.annotation;


import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * 将Entity添加到SQL解析器中
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
@Documented
@Component
@Import({EntitySqlAddRegistrar.class})
public @interface EntitySqlAdd {
    String value() default "";

}
