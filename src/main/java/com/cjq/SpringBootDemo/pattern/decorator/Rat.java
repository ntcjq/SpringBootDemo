package com.cjq.SpringBootDemo.pattern.decorator;

public class Rat implements Animal {
    @Override
    public void doStuff() {
        System.out.println("Rat doStuff");
    }
}
