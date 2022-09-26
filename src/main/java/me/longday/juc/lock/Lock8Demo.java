package me.longday.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc Lock的8个示例
 * @date 2022/9/26
 */

/**
 * 8个案例 理解Lock：<br/>
 * 1.标准访问a,b两个线程,先打印‘发送邮件’还是先打印'发送短信'?                    ==> '发送邮件'<br/>
 * 2.sendEmail()方法中sleep(3s),先打印‘发送邮件’还是先打印'发送短信'?           ==>'发送短信'<br/>
 * 3.将sendMsg()方法换为普通方法hello(),先打印‘发送邮件’还是先打印'Hello'?       ==> 'Hello'<br/>
 * 4.两个手机对象,先打印发送邮件’还是先打印'发送短信'?                           ==> 'Hello'<br/>
 * 5.有两个静态同步方法,先打印‘发送邮件’还是先打印'发送短信'?                     ==> '发送邮件'<br/>
 * 6.有两个静态同步方法,有两部手机,先打印‘发送邮件’还是先打印'发送短信'?            ==> '发送邮件'<br/>
 * 7.一个静态,一个普通方法,先打印‘发送邮件’还是先打印'发送短信'?                   ==>'发送短信'<br/>
 * 8.一个静态,一个普通方法,两部手机,先打印‘发送邮件’还是先打印'发送短信'?           ==>'发送短信'<br/>
 * <br/><br/>
 *对比总结:
 * <br/>1-2<br/>
 * 一个对象里面如果有多个synchronized方法,某一个时刻内, 只要一个线程去调用其中的一个synchronized. 方法
 * 其它的线程都只能等待,换句话说,某一个时刻内,只能有唯一的一个线程 去访问这些synchronized方法
 * 锁的是当前对象this,被锁定后,其它的线程都不能进入到当前对象的其它的synchronized方法
 * <br/>3-4<br/>
 * 加个普通方法后发现和同步锁无关.
 * 换成两个对象后,不是同一把锁了,情况立刻变化.
 * <br/>5-6<br/>
 * 都换成静态同步方法后,情况又变化.
 * 三种synchronized锁的内容有一些差别:<br/>
 * 对于普通同步方法,锁的是当前实例对象,通常指this,具体的一部部手机,所有的普通同步方法用的都是同一把锁->实例对象本身<br/>
 * 对于静态同步方法,锁的是当前类的Class对象,如Phone.class唯一的一个模板<br/>
 * 对于同步方法块,锁的是synchronized括号内的对象
 * <br/>7-8<br/>
 * 当一个线程试图访间同步代码时它首先必须得到锁,正常退出或抛出异常时必须释放锁.<br/>
 * 所有的普通同步方法用的都是同一把锁实例对象本身,就是new出来的具体实例对象本身,本类this
 * 也就是说如果一个实例对象的普通同步 方法获取锁后,该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁.<br/>
 * 所有的静态同步方法用的也是同一把锁类对象 本身,就是我们说过的唯一模板Class
 * 具体实例对象this和唯一模板Class, 这两把锁是两个不同的对象,所以静态同步方法与普通同步方法之间是不会有竞态条件的
 * 但是一旦一个静态同步方法获取锁后,其他的静态同步方法都必须等待该方法释放锁后才能获取锁.
 */
public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(phone::sendEmail,"a").start();

        try {TimeUnit.MICROSECONDS.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}

        new Thread(phone::sendMsg,"b").start();
    }
}
class Phone {
    public synchronized void sendEmail() {
        try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("发送邮件");
    }

    public synchronized void sendMsg() {
        System.out.println("发送短信");
    }

    public void hello() {
        System.out.println("Hello");

    }
}