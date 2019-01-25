package com.cjq.SpringBootDemo.daily.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: cuijq
 */
public class Reflect {

    public static void main(String[] args){
        Animal animal = new Cat();
//        StaticProxy proxy = new StaticProxy(animal);
//        proxy.eat();
//        proxy.run();

        Class[] classes = animal.getClass().getInterfaces();
        for (Class cla:classes){
            System.out.println(cla.getName());
        }
        //
        SubjectHandler handler = new SubjectHandler(animal);
        Animal animalP = (Animal)java.lang.reflect.Proxy.newProxyInstance(animal.getClass().getClassLoader(),animal.getClass().getInterfaces(),handler);
        animalP.eat();
        animalP.run();

    }
}
interface Animal{
    public void eat();

    public void run();
}
class Dog implements Animal{

    @Override
    public void eat() {
        System.out.println("dog eat");
    }

    @Override
    public void run() {
        System.out.println("dog run");
    }
}
class Cat implements Animal{

    @Override
    public void eat() {
        System.out.println("Cat eat");
    }

    @Override
    public void run() {
        System.out.println("Cat run");
    }
}
class Proxy implements Animal{

    Animal animal;
    public Proxy(Animal animal){
        this.animal = animal;
    }
    @Override
    public void eat() {
        System.out.println("do eat Before");
        animal.eat();
        System.out.println("do eat After");
    }

    @Override
    public void run() {
        System.out.println("do run Before");
        animal.run();
        System.out.println("do run After");
    }
}

class SubjectHandler implements InvocationHandler{


    private Animal animal;
    public SubjectHandler(Animal animal){
        this.animal = animal;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("do Before");
        Object object = method.invoke(animal,args);
        System.out.println("do After");
        return object;
    }
}
