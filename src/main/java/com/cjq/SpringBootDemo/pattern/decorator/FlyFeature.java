package com.cjq.SpringBootDemo.pattern.decorator;

public class FlyFeature implements Feature{
    @Override
    public void load() {
        System.out.println("增加一双翅膀");
    }
}
