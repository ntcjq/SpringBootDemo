package com.cjq.SpringBootDemo.daily.proxy;

import com.google.common.math.DoubleMath;

/**
 * @Author: cuijq
 */
public class RealSubject implements Subject{

    @Override
    public void doSomeThing() {
        System.out.println("real doSomething");
    }


    @Ha
    @Override
    public String backStr() {
        return "RealBack";
    }


}
