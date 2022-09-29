package me.longday.juc.CAS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 君
 * @version 1.0
 * @desc 原子类
 * @date 2022/9/28
 *
 * CAS:
 *  在硬件层面实现类似锁的机制 ==> CPU 锁。使用的是汇编 --> cmpxchg()方法
 *
 *  缺点:
 *      1. x循环时间长，开销大  ==> 解决方案,无法解决....
 *      2. ABA问题(也称:老王拜访问题 → . →) ==>解决方案: 增加修订版本号。
 *
 */
public class CasDemo {

    public static void main(String[] args) {
        //想更换运行结果切换这里的值
        int choice = 3;
        switch (choice) {
            case 1 -> // 原子类泛型
                    customType();
            case 2 -> // 自定义自旋锁
                    spinLock();
            case 3 -> // ABA问题解决方案
                    abaIssue();
            default -> System.out.println("请输入合法数字");
        }
    }

    private static void abaIssue() {
        User root = new User("root", 1);
        AtomicStampedReference<User> userAtoStaReference = new AtomicStampedReference<>(root, 1);

        new Thread(()->{
            int stamp = userAtoStaReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t首次版本号:"+stamp);
            try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            User user = new User("宝宝", 2);
            userAtoStaReference.compareAndSet(root,user,stamp,stamp+1);

            System.out.println(Thread.currentThread().getName()+"\t二次版本号:"+userAtoStaReference.getStamp());
            userAtoStaReference.compareAndSet(user,root, userAtoStaReference.getStamp(), userAtoStaReference.getStamp()+1);

            System.out.println(Thread.currentThread().getName()+"\t三次版本号:"+userAtoStaReference.getStamp());
        },"A线程").start();
        new Thread(()->{
            int stamp = userAtoStaReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t首次版本号:"+stamp);

            try { TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
            boolean result = userAtoStaReference.compareAndSet(root, new User("test", 2), stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+" 是否修改成功: "+result+"\t现存变量信息(想更改为User(name=test, id=2)): "+userAtoStaReference.getReference()+"\t当前版本号: "+userAtoStaReference.getStamp());
        },"B线程").start();
    }

    private static void spinLock() {
        SpinLock spinLock = new SpinLock();
        new Thread(()->{
            spinLock.lock();
            try { TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            spinLock.unlock();
        },"A线程").start();

        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            spinLock.lock();
            spinLock.unlock();

        },"B线程").start();
    }

    /**
     * 自定义原子类型
     */
    private static void customType() {
        //自定义封装的原子类
        AtomicReference<User> integerA = new AtomicReference<>();
        integerA.set(new User("root",1));
        System.out.println(integerA.get());

        integerA.set(new User("宝宝",2));
        System.out.println(integerA.get());
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class User{
    private String name;
    private long id;
}

/**
 * 根据原子类来自定义一个自旋锁.
 */
class SpinLock{
    //这个变量相当于一个标志，线程去抢占这个标志，拿到这个标志的线程才会正常运行自己的流程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 模拟加锁
     */
    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t ----come in");
        while(!atomicReference.compareAndSet(null,thread)){
            try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println(Thread.currentThread().getName()+"循环抢锁...");
        }
        System.out.println(Thread.currentThread().getName()+"\t ----抢锁成功");

    }

    /**
     * 模拟解锁
     */
    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t ----解锁成功");

    }

}