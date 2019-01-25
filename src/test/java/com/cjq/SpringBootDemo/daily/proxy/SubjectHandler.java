package com.cjq.SpringBootDemo.daily.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: cuijq
 */
public class SubjectHandler implements InvocationHandler {

    private Object object;

    public SubjectHandler(Object _object){
        object = _object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        if(declaredAnnotations.length > 0){
            beforeAnno();
            obj = method.invoke(object,args);
            afterAnno();
        }else{
            before();
            obj = method.invoke(object,args);
            after();
        }
        return obj;
    }

    private void before(){
        System.out.println("do before");
    }

    private void after(){
        System.out.println("do after");
    }

    private void beforeAnno(){
        System.out.println("do before Anno");
    }

    private void afterAnno(){
        System.out.println("do after Anno");
    }
}

