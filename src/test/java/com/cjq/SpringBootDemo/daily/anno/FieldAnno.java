package com.cjq.SpringBootDemo.daily.anno;


import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAnno {


    String value() default "FieldAnno";

}
