package com.cjq.SpringBootDemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Dog implements Serializable{
    private String name;
    private Integer age;
    private boolean live;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
