package com.cjq.SpringBootDemo.daily.proxy;

/**
 * @Author: cuijq
 */
public class StaticProxy implements Subject{

    // 要代理那个实现类
    private Subject subject;

    // 创建被代理者
    public StaticProxy(){
        subject = new RealSubject();
    }

    // 通过构造函数传递被代理者
    public StaticProxy(Subject _subject) {
        subject = _subject;
    }

    @Override
    public void doSomeThing() {
        before();
        subject.doSomeThing();
        after();
    }

    @Override
    public String backStr() {
        before();
        String str = subject.backStr();
        after();
        return str;
    }

    private void before(){
        System.out.println("do before");
    }

    private void after(){
        System.out.println("do after");
    }

}
