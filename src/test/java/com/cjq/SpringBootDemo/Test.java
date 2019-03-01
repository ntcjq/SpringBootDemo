package com.cjq.SpringBootDemo;

import com.cjq.SpringBootDemo.daily.Parent;
import com.cjq.SpringBootDemo.domain.User;
import javafx.scene.input.KeyCode;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
