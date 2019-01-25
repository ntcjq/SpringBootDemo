package com.cjq.SpringBootDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: cuijq
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"  a");
            }
        });

        Future<String> feature2 = executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName()+"  b");
                return "haha";
            }
        });
        try {
            System.out.println(feature2.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

    }

}
