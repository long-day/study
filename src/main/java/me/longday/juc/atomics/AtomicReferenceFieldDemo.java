package me.longday.juc.atomics;

import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author 君
 * @version 1.0
 * @desc 字段锁
 * @date 2022/10/3
 */
public class AtomicReferenceFieldDemo {
    public static void main(String[] args) {
        BankCount bankCount = new BankCount();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            try{
                for (int j = 0; j < 1000; j++) {
                    bankCount.transMoney();
                }
            }finally {
                countDownLatch.countDown();
            }

        }
        try {
            countDownLatch.await();
            System.out.println(bankCount.getMoney());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
@Data
class BankCount{
    private final String name = "BBB";
    private volatile int money = 0;

    private static final AtomicIntegerFieldUpdater<BankCount> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankCount.class,"money");

    public void transMoney(){
        fieldUpdater.getAndIncrement(this);
    }
}
