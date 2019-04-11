package com.cjq.SpringBootDemo.daily.anno;

import com.cjq.SpringBootDemo.daily.Child;
import com.cjq.SpringBootDemo.daily.Parent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * isAnnotationPresent方法说明：判断 class、method、field上是否有某个注解
 * 注意：子类继承父类的情况下，无法继承到父类的类上的注解（即class注解），在class注解上增加@Inherited可以让注解能够被子类继承
 * 方法和属性的注解是可以继承的（前提是不重写方法和在子类中定义和父类一样的属性），重写和定义相同属性后会无法继承
 */
public class Main {


    public static void main(String[] args) {
        System.out.println("Parent " + Parent.class.isAnnotationPresent(ClassAnno.class));

        System.out.println("Child " + Child.class.isAnnotationPresent(ClassAnno.class));

        try {
            Method pmethodPub = Parent.class.getMethod("pmethodPub");
            System.out.println("Parent " + pmethodPub.isAnnotationPresent(MethodAnno.class));

            Method pmethodPub_child = Child.class.getMethod("pmethodPub");
            System.out.println("Child " + pmethodPub_child.isAnnotationPresent(MethodAnno.class));

            Field ppub = Parent.class.getField("ppub");
            System.out.println("Parent " + ppub.isAnnotationPresent(FieldAnno.class));

            Field ppub_child = Child.class.getField("ppub");
            System.out.println("Child " + ppub_child.isAnnotationPresent(FieldAnno.class));



        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e2){
            e2.printStackTrace();
        }
    }
}
