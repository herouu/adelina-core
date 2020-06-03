package top.alertcode.adelina.framework.base.annotation;



import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import top.alertcode.adelina.framework.base.entity.EntitySqlFactory;


@Aspect
@Component
@Slf4j
public class EntitySqlAddRegistrar implements ImportBeanDefinitionRegistrar {

    public EntitySqlAddRegistrar() {
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        try {
            Class<?>  clazz = Class.forName(importingClassMetadata.getClassName());
            EntitySqlFactory.addClassMap(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

