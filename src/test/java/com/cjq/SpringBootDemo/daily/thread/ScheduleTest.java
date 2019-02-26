package com.cjq.SpringBootDemo.daily.thread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * @Author: cuijq
 */
public class ScheduleTest {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args){

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "  " + formatter.format(LocalDateTime.now()));
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        pool.scheduleAtFixedRate(runnable, 5, 3, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() +"  "+ formatter.format(LocalDateTime.now()));

        try{
            Thread.sleep(60000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();

    }
}
