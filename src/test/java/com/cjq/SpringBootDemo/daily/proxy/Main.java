package com.cjq.SpringBootDemo.daily.proxy;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: cuijq
 */
public class Main {


    public static void main(String[] args){
        //静态代理方式
//        StaticProxy staticProxy = new StaticProxy();
//        staticProxy.doSomeThing();

        //动态代理方式
        RealSubject subject = new RealSubject();
        InvocationHandler handler = new SubjectHandler(subject);
        ClassLoader classLoader = subject.getClass().getClassLoader();
        Subject proxySubject = (Subject)Proxy.newProxyInstance(classLoader,subject.getClass().getInterfaces(),handler);
        proxySubject.doSomeThing();
        System.out.println(proxySubject.backStr());

        //cglib
        Enhancer enchancer = new Enhancer();//字节码增强器
        enchancer.setSuperclass(RealSubject.class);//设置被代理类为父类
        enchancer.setCallback(new SubjectInterceptor());//设置回调
        RealSubject cglibProxySubject = (RealSubject)enchancer.create();//创建代理实例
        cglibProxySubject.doSomeThing();
    }
}
