package me.longday.juc.volatileAndJMM;

import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc volatile
 * @date 2022/9/28
 *
 * 被volatile修饰的变量的写操作直接写到主内存中，读操作是从主内存中获取
 *
 * volatile 写之前的操作禁止重排序到volatile写操作之后
 * volatile 读之后的操作禁止重排序到volatile读操作之前
 * volatile 写之后的volatile读 禁止重排序
 */
public class VolatileAndJmm {
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t ---- come in");
            while(flag){

            }
            System.out.println(Thread.currentThread().getName()+"\t ---- flag == false 进程结束---");

        },"线程A").start();

        try { TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

        flag = false;
        System.out.println("main 线程修改完成");
    }
}
