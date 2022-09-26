package me.longday.juc.lock;

/**
 * @author 君
 * @version 1.0
 * @desc 深入理解 synchronized
 * @date 2022/9/26
 */
public class LockSyncDemo {
    final Object o = new Object();

    public void m1(){
        synchronized (o){
            System.out.println("hello synchronized code block!");
        }
    }
    public synchronized void m2(){
            System.out.println("hello synchronized m2");
    }

    public static synchronized void m3(){
        System.out.println("hello synchronized static m3");
    }
    public static void main(String[] args) {
        LockSyncDemo lockSyncDemo = new LockSyncDemo();
        lockSyncDemo.m1();
        lockSyncDemo.m2();
    }
}

