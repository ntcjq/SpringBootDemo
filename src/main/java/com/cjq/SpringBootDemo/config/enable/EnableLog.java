package com.cjq.SpringBootDemo.config.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = LogImportBeanDefinitionRegistrar.class)
public @interface EnableLog {
    String[] packages();
}
