package com.cjq.SpringBootDemo.daily.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: cuijq
 */
public class SubjectInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib 前置处理");
        Object obj = methodProxy.invokeSuper(o,objects);
        System.out.println("Cglib 后置处理");
        return obj;
    }
}
