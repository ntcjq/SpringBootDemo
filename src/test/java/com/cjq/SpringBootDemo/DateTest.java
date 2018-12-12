package com.cjq.SpringBootDemo;


import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @Author: cuijq
 */
public class DateTest {


    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    public static void main(String[] args){

        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(Instant.now());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plus(1,ChronoUnit.DAYS);
        System.out.println(localDateTime.isBefore(localDateTime));
        System.out.println(localDateTime.isAfter(localDateTime));
        System.out.println(localDateTime.isEqual(localDateTime));
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
        System.out.println(localDateTime.getNano());
        System.out.println(localDateTime);
        System.out.println(localDateTime.format(dtf));

        System.out.println(System.currentTimeMillis());

        Instant.now();
    }
}
