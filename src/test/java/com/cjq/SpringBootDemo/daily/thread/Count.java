package com.cjq.SpringBootDemo.daily.thread;


import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: cuijq
 */
public class Count implements Callable<Integer> {

    private static Random random = new Random();

    @Override
    public Integer call() throws Exception {
        Integer val = random.nextInt(1000);
        System.out.println(Thread.currentThread().getName()+"  "+ val);
        Thread.sleep(2000);
        return val;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(pool);
        Count count = new Count();
        Integer allCount = 0;

        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        queue.put(completionService.submit(count));
        queue.put(completionService.submit(count));
        queue.put(completionService.submit(count));
        queue.put(completionService.submit(count));
//        completionService.submit(count);
//        completionService.submit(count);
//        completionService.submit(count);
//        completionService.submit(count);
        pool.shutdown();


        for(int i=0;i<4;i++) {
            System.out.println("Wating........");
            Future<Integer> submit = (Future<Integer>)queue.take();
            System.out.println(submit.get());
        }

//        for(int i=0;i<4;i++){
//            //此处会阻塞，因为内部使用的是LinkedBlockingQueue，当队列里没有元素时await(),
//            Future<Integer> submit = completionService.take();
//            try {
//                allCount = allCount + submit.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println("合计："+allCount);

    }
}
