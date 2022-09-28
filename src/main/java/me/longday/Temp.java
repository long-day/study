package me.longday;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/9/22
 */
public class Temp {
    public static void main(String[] args) throws InterruptedException {
        //Data ==> Instant
        Instant now = Instant.now();
        System.out.println(now);
        //Calender ==> LocalDateTime
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(now1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss.SSS");
        String format = dateTimeFormatter.format(now1);
        System.out.println(format);

        LocalTime now2 = LocalTime.now();
        System.out.println(now2);

        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

        new Thread(()->{
            stringThreadLocal.set(Thread.currentThread().getName()+" :添加一个String");
            System.out.println(stringThreadLocal.get());
            stringThreadLocal.remove();
        },"A线程").start();

        new Thread(()->{
            stringThreadLocal.set(Thread.currentThread().getName()+" :添加一个String");
            System.out.println(stringThreadLocal.get());
            stringThreadLocal.remove();
        },"B线程").start();
//        Thread.currentThread().join();
        System.out.println(stringThreadLocal.get());
    }
}



