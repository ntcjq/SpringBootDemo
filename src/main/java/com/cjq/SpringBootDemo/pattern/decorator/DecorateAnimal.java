package com.cjq.SpringBootDemo.pattern.decorator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class DecorateAnimal implements Animal{
    // 被包装的动物
    private Animal animal;
    // 使用哪一个包装器
    private Class<? extends Feature> clz;

    public DecorateAnimal(Animal _animal, Class<? extends Feature> _clz) {
        animal = _animal;
        clz = _clz;
    }

    @Override
    public void doStuff() {
        InvocationHandler handler = new InvocationHandler() {
            // 具体包装行为
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                Object obj = null;
                if (Modifier.isPublic(method.getModifiers())) {
                    obj = method.invoke(clz.newInstance(), args);
                }
                animal.doStuff();
                return obj;
            }
        };
        //当前加载器
        ClassLoader cl = getClass().getClassLoader();
        //动态代理，由handler决定如何包装
        Feature proxy = (Feature) Proxy.newProxyInstance(cl, clz.getInterfaces(), handler);
        proxy.load();
    }

    public static void main(String[] args) {
        //定义Jerry这只老鼠
        Animal jerry = new Rat();
        //为Jerry增加飞行能力
        jerry = new DecorateAnimal(jerry, FlyFeature.class);
        //jerry增加挖掘能力
        jerry = new DecorateAnimal(jerry, DigFeature.class);
        //Jerry开始戏弄毛了
        jerry.doStuff();
    }
}
