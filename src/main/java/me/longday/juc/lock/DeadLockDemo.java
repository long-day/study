package me.longday.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc 死锁和查询死锁
 * @date 2022/9/26

 * 排查死锁方式:
 * 1. jps -l 查看JVM现在正在运行的程序 ==> jstack [进程号] 追踪线程,如果有死锁会输出
 * 2. jconsole Java控制台. ==> 连接运行的线程 ==>找到进程页面 ==> 查找死锁  即可查询是否有死锁
 *
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        final Object o1 = new Object();
        final Object o2 = new Object();
        new Thread(()->{
            synchronized(o1){
                System.out.println(Thread.currentThread().getName()+"持有o1锁，想要获取o2锁");
                try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
                synchronized(o2){
                    System.out.println(Thread.currentThread().getName()+"成功获得o2锁");
                }
            }
        },"A").start();

        new Thread(()->{
            synchronized(o2){
                System.out.println(Thread.currentThread().getName()+"持有o2锁，想要获取o1锁");
                try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
                synchronized(o1){
                    System.out.println(Thread.currentThread().getName()+"成功获得o1锁");
                }
            }
        },"B").start();
    }
}
