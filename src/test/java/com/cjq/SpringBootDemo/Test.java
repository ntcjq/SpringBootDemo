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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Random random = new Random();
        String[] passwords = {"A","B","C"};
        List<User> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            User user = new User();
            user.setUserName("name"+i);
            user.setId((long)random.nextInt(1000));
            user.setPassWord(passwords[random.nextInt(3)]);
            list.add(user);
        }
        List<Map> collect = list.stream().map(user -> {
            Map map = new HashMap();
            map.put("username", user.getUserName());
            map.put("password", user.getPassWord());
            return map;
        }).filter(user-> user != null).collect(Collectors.toList());

        long sum = list.stream().mapToLong(user -> user.getId()).sum();
        long sum2 = list.stream().mapToLong(User::getId).sum();
        System.out.println(sum);
        System.out.println(sum2);


        Stream<BigDecimal> bigDecimalStream = Stream.of(null,new BigDecimal(100), new BigDecimal(102), new BigDecimal(103), new BigDecimal(104));
        BigDecimal bigDecimal = bigDecimalStream.filter(x -> x != null).reduce(BigDecimal::add).get();


        Map<String, List<User>> collect1 = list.stream().collect(Collectors.groupingBy(User::getPassWord));


        for(Map.Entry<String,List<User>> listEntry : collect1.entrySet()){
            String key = listEntry.getKey();
            List<User> value = listEntry.getValue();
        }


        collect1.forEach((k,v) -> System.out.println(k+":"+v));


        Stream<Double> generate = Stream.generate(new Supplier<Double>() {

            @Override
            public Double get() {
                return Math.random();
            }
        });
        System.out.println(generate);
        List<Double> collect2 = generate.skip(99).limit(100).collect(Collectors.toList());
        System.out.println(collect2);

        test1();

    }

    public static void test1(){
        Date date=new Date();
        //c的使用
        System.out.printf("全部日期和时间信息：%tc%n",date);
        //f的使用
        System.out.printf("年-月-日格式：%tF%n",date);
        //d的使用
        System.out.printf("月/日/年格式：%tD%n",date);
        //r的使用
        System.out.printf("HH:MM:SS PM格式（12时制）：%tr%n",date);
        //t的使用
        System.out.printf("HH:MM:SS格式（24时制）：%tT%n", date);
        //R的使用
        System.out.printf("HH:MM格式（24时制）：%tR%n",date);

        System.out.println(String.format("%tF",date).replaceAll("-",""));
    }
}
