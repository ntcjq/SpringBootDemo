package com.cjq.SpringBootDemo.config.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * @Import说明：如果你引入的是一个正常的component， 那么会作为@Compoent或者@Configuration来处理， 这样在BeanFactory里边可以通过getBean拿到，
 * 但如果你是 ImportSelector 或者 ImportBeanDefinitionRegistrar 接口的实现， 那么spring并不会将他们的实现注册到beanFactory中，而只是调用他们的方法。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = EnableBeanImportSelector.class)
public @interface EnableBean {
}
