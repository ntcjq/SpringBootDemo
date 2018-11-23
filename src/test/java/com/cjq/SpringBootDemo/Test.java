package com.cjq.SpringBootDemo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: cuijq
 */
public class Test {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InterruptedException {

        Class clazz = Child.class;
        Field[] dfields = clazz.getDeclaredFields();
        Field[] fields = clazz.getFields();
        for (Field field : dfields){
            System.out.println("DeclaredField："+field.getName()  +",isAccessible:"+field.isAccessible());
        }
        for (Field field : fields){
            System.out.println("Field："+field.getName() +",isAccessible:"+field.isAccessible());
        }

        Method[] dmethods = clazz.getDeclaredMethods();
        Method[] methods = clazz.getMethods();
        for (Method method : dmethods){
            System.out.println("DeclaredMethod："+method.getName()+",isAccessible:"+method.isAccessible());
        }
        for (Method method : methods){
            System.out.println("Method："+method.getName()+",isAccessible:"+method.isAccessible());
        }
        Method method = clazz.getDeclaredMethod("cmethodPri");
        if(!method.isAccessible()){
            method.setAccessible(true);
        }
        Object object = method.invoke(new Child(),null);
        System.out.println(object);
        Class[] interfaces = clazz.getInterfaces();
        for (Class c : interfaces){
            System.out.println(c.getName());
        }
        Class superclass = clazz.getSuperclass();
        System.out.println(superclass.getName());
        System.out.println(Parent.class.getSuperclass().getName());
        Field cpri = clazz.getDeclaredField("cpri");

        if(!cpri.isAccessible()){
            cpri.setAccessible(true);
        }
        Child child = new Child();
        System.out.println(child.getCpri());
        cpri.set(child,"new_Private");
        System.out.println(child.getCpri());
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 线程执行后的期望值
        Future future = es.submit(new Callable<Integer>(){

            @Override
            public Integer call() throws Exception {
                return null;
            }
        });
        System.out.println(future.isDone());
        Thread.sleep(1000);
        System.out.println(future.isDone());
    }

}
