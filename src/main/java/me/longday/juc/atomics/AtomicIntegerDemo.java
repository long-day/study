package me.longday.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 君
 * @version 1.0
 * @desc 原子整形类
 * @date 2022/10/3
 */
public class AtomicIntegerDemo {
    public static final int THREAD_NUMBER = 50;
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPP();
                    }
                }finally {
                    countDownLatch.countDown();
                }


            },"线程: "+i).start();
        }
        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"result: "+myNumber.atomicInteger.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyNumber{
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPP(){
        atomicInteger.getAndIncrement();
    }
}
