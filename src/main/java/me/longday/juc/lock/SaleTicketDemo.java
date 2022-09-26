package me.longday.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 君
 * @version 1.0
 * @desc 卖票案例 公平锁和不公平锁
 * @date 2022/9/26
 */
public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(()->{
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        },"a").start();

        new Thread(()->{
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        },"b").start();

        new Thread(()->{
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        },"c").start();
        ticket.sale();
    }
}

class Ticket{
    private int num = 50;
    ReentrantLock lock = new ReentrantLock(true);
    public void sale(){
        lock.lock();
        try{
            if (num > 0){
                System.out.println(Thread.currentThread().getName()+"卖出第: "+(num--)+"张票"+"还剩下: "+num+"张票");
            }
        }finally {
            lock.unlock();
        }
    }
}