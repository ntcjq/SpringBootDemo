package com.cjq.SpringBootDemo.config.enable;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  实现 ImportBeanDefinitionRegistrar 接口
 */
public class LogImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 EnableLog 的所有属性
        Map<String, Object> attr = importingClassMetadata.getAnnotationAttributes(EnableLog.class.getName());
        // 得到 packages 属性所有的值
        String[] packages = (String[]) attr.get("packages");
        List<String> packageList = Arrays.asList(packages);
        // 生成 LogBeanPostProcessor 对象，并将所有包含的包传给该对象
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(LogBeanPostProcessor.class.getName());
        builder.addPropertyValue("packageList", packageList);
        // 将 LogBeanPostProcessor 对象注册到 Spring 中
        registry.registerBeanDefinition(LogBeanPostProcessor.class.getName(), builder.getBeanDefinition());
    }
}
