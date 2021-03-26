package com.cjq.SpringBootDemo.domain;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private Integer code;
    private String msg;
    private Dog dog;
    private List<Dog> dogs;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
}
