package me.longday.juc.atomics;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author 君
 * @version 1.0
 * @desc 原子整形数组
 * @date 2022/10/3
 */
public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        System.out.println(atomicIntegerArray);

        int temp0 = atomicIntegerArray.getAndSet(0, 200);
        System.out.println("原始0的值:"+temp0+"现在0的值:" + atomicIntegerArray.get(0));

        System.out.println("getAndIncrement: "+atomicIntegerArray.getAndIncrement(0));

        System.out.println("getAndIncrement后: "+atomicIntegerArray.get(0));

        System.out.println("incrementAndGet: "+atomicIntegerArray.incrementAndGet(0));
    }
}
