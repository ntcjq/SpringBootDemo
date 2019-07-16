package com.cjq.SpringBootDemo.pattern.decorator;

public class DigFeature implements Feature {
    @Override
    public void load() {
        System.out.println("增加挖地能力");
    }
}
