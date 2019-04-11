package com.cjq.SpringBootDemo;

import com.cjq.SpringBootDemo.daily.Child;
import com.cjq.SpringBootDemo.daily.Parent;
import com.cjq.SpringBootDemo.daily.anno.ClassAnno;
import com.cjq.SpringBootDemo.daily.anno.FieldAnno;
import com.cjq.SpringBootDemo.daily.anno.MethodAnno;
import com.cjq.SpringBootDemo.domain.User;
import javafx.scene.input.KeyCode;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.ldap.PagedResultsControl;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static javafx.scene.input.KeyCode.L;

/**
 * @Author: cuijq
 */
public class Test {


    private Test(){}

    private static class TestIn{
        private static Test test = new Test();
    }

    public static Test getInstance(){

        return TestIn.test;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName()+"  a");
//            }
//        });
//
//        Future<String> feature2 = executorService.submit(new Callable<String>() {
//
//            @Override
//            public String call() throws Exception {
//                System.out.println(Thread.currentThread().getName()+"  b");
//                return "haha";
//            }
//        });
//        try {
//            System.out.println(feature2.get());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        executorService.shutdown();




    }

}
