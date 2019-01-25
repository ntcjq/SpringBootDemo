package com.cjq.SpringBootDemo.daily.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: cuijq
 */
public class Ticket implements Runnable{


    private static int count = 100;


    public synchronized void sellTicket(){
        if(count > 0){
            System.out.println(count+"张，卖出一张票，剩余"+(count-1)+"张 "+ Thread.currentThread().getName());
            count--;
        }else{
            System.out.println("sell out "+Thread.currentThread().getName());
        }
    }

    @Override
    public void run() {
        if(count>0){
            sellTicket();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Ticket ticket = new Ticket();
        for(int i =0;i<100;i++){
            pool.execute(ticket);
        }
        pool.shutdown();
    }
}
