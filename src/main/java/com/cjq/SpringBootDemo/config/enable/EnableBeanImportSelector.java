package com.cjq.SpringBootDemo.config.enable;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportSelector 实现类
 */
public class EnableBeanImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 将 bean 注入到 spring 容器
        return new String[]{EnableBeanConfiguration.class.getName()};
    }
}