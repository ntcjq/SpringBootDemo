package com.cjq.SpringBootDemo.domain;

import java.io.Serializable;
import java.util.Objects;

public class Ehcache implements Serializable {

    private String key;
    private String value;

    public Ehcache(){

    }

    public Ehcache(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ehcache{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ehcache ehcache = (Ehcache) o;
        return Objects.equals(key, ehcache.key) &&
                Objects.equals(value, ehcache.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, value);
    }
}
