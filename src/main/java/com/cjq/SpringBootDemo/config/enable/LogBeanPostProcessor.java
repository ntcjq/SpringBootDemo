package com.cjq.SpringBootDemo.config.enable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;

/**
 * 启动 @EnableLog 特性，在加载Bean时，打印指定包名下的类的信息
 */
public class LogBeanPostProcessor implements BeanPostProcessor {
    List<String> packageList;

    public List<String> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<String> packageList) {
        this.packageList = packageList;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 遍历 LogImportBeanDefinitionRegistrar 穿过来的所有包名
        for (String packageName : packageList) {
            if (bean.getClass().getName().startsWith(packageName)) {
                System.out.println(bean.getClass().getName() + " Log ...");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
