package com.cjq.SpringBootDemo.pattern.observer;

import java.util.Observable;

public class Teacher extends Observable {

    private String info;

    public String getInfo() {
        return info;
    }

    public void publishHomeWork(String info){
        this.info = info;
        setChanged();
        notifyObservers();
    }

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        Student student = new Student("小王",teacher);
        Student student2 = new Student("小红",teacher);
        teacher.publishHomeWork("作业一");
        teacher.publishHomeWork("作业四");

    }
}