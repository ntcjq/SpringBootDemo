package com.cjq.SpringBootDemo.daily.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: cuijq
 */
public class DateTest {


    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    public static void main(String[] args) throws ParseException {
        zone();
        duration();

    }

    /**
     * 获取时区
     */
    public static void zone(){
        //北京时区
        ZoneId bjZone = ZoneId.of("GMT+08:00");
        System.out.println(bjZone);
        //当前时区
        ZoneId systemDefaultZone = ZoneId.systemDefault();
        System.out.println(systemDefaultZone);
    }

    /**
     * LocalDateTime与字符串互转
     */
    public static void transform1(){
        //String <- LocalDateTime
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);

        //String -> LocalDateTime
        LocalDateTime ldt = LocalDateTime.parse("2018-06-01 10:12:05",df);
        System.out.println("LocalDateTime转成String类型的时间："+localTime);
        System.out.println("String类型的时间转成LocalDateTime："+ldt);

    }

    /**
     * LocalDateTime与Date互转
     */
    public static void transform2(){
        //LocalDateTime <- Date
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        System.out.println(localDateTime);
        //LocalDateTime -> Date
        LocalDateTime now = LocalDateTime.now();
        Instant instant1 = now.atZone(zoneId).toInstant();
        Date date2 = Date.from(instant1);
        System.out.println(date2);

    }


    /**
     * LocalDateTime与LocalDate互转
     */
    public static void transform3(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDate localDate = now.toLocalDate();
        System.out.println(localDate);

        ZoneId zoneId = ZoneId.systemDefault();
        //atStartOfDay先转成LocalDateTime 再转成instant
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        Date date = Date.from(instant);
        System.out.println(date);

    }

    /**
     * LocalDateTime转成long 毫秒值  == System.currentTimeMillis()
     */
    public static void transform4(){
        LocalDateTime now = LocalDateTime.now();
        long current = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(current);
        System.out.println(System.currentTimeMillis());

    }


    /**
     *  时间间隔计算
     *  使用Duration进行 day,hour,minute,second等的计算
     *  使用Period进行Year,Month的计算
     */
    public static void duration(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.parse("2019-01-02 12:12:12",df);
        LocalDateTime localDateTime2 = LocalDateTime.parse("2019-01-04 11:12:12",df);
        Duration between = Duration.between(localDateTime1, localDateTime2);
        System.out.println(between.toDays());
        System.out.println(between.toHours());
        Period period = Period.between(localDateTime1.toLocalDate(), localDateTime2.toLocalDate());
        System.out.println(period.getDays());
        System.out.println(period.getMonths());
    }






}
