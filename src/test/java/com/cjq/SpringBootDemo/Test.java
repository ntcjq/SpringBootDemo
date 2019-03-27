package com.cjq.SpringBootDemo;

import com.cjq.SpringBootDemo.daily.Parent;
import com.cjq.SpringBootDemo.domain.Lom;
import com.cjq.SpringBootDemo.domain.User;
import javafx.scene.input.KeyCode;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.ldap.PagedResultsControl;
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


        Lom lom1 = new Lom("a",13,50,new Date());
        Lom lom2 = new Lom("a",2,23,new Date());
        Lom lom3 = new Lom("a",19,1,new Date());
        Lom lom4 = new Lom("a",1,100,new Date());

        HashSet<Lom> hashSet = new HashSet<>();
        hashSet.add(lom1);
        hashSet.add(lom2);
        hashSet.add(lom3);
        hashSet.add(lom4);



        TreeSet<Lom> treeSet = new TreeSet<>((o1,o2) -> {
                return o1.getXi() - o2.getXi();
        });
        treeSet.add(lom1);
        treeSet.add(lom2);
        treeSet.add(lom3);
        treeSet.add(lom4);
        System.out.println(hashSet);
        System.out.println(treeSet);

    }

}
