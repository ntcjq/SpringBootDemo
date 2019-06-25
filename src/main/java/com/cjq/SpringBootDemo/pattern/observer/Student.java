package com.cjq.SpringBootDemo.pattern.observer;

import java.util.Observable;
import java.util.Observer;

public class Student implements Observer {
    private String name;
    private Observable observable;
    public Student(String name,Observable observable){
        this.name = name;
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Teacher teacher = (Teacher) o;
        String info = teacher.getInfo();
        System.out.println(this.name +"收到信息："+info);
    }
}
