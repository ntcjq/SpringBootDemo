package com.cjq.SpringBootDemo.daily.anno;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAnno {

    String value() default "MethodAnno";

}
