package me.longday.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 君
 * @version 1.0
 * @desc 原子引用类型
 * @date 2022/10/3
 */
public class AtomicReferenceDemo {
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 200);
    static AtomicMarkableReference<Integer> atomicMarkAbleReference = new AtomicMarkableReference<>(300, false);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                boolean a = atomicMarkAbleReference.compareAndSet(
                        atomicMarkAbleReference.getReference(),
                        atomicMarkAbleReference.getReference() + 1,
                        atomicMarkAbleReference.isMarked(),
                        !atomicMarkAbleReference.isMarked());
                System.out.println(Thread.currentThread().getName() + " 修改返回值 " + a);
                System.out.println(Thread.currentThread().getName() + " 新reference " + atomicMarkAbleReference.getReference());
            }, "线程A"+i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                boolean a = atomicStampedReference.compareAndSet(
                        atomicStampedReference.getReference(),
                        atomicStampedReference.getReference() + 1,
                        atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp()+1);
                System.out.println(Thread.currentThread().getName() + " stamp修改返回值 " + a);
                System.out.println(Thread.currentThread().getName() + " 新Stamp " + atomicStampedReference.getReference());
            }, "线程A"+i).start();
        }
        try { TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

    }
}
