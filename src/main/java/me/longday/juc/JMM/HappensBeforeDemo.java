package me.longday.juc.JMM;

/**
 * @author 君
 * @version 1.0
 * @desc 先行发生原则
 * @date 2022/9/28
 *<br/>
 * 1.次序规则: 同一线程内,写在前面的操作先行于后面的操作<br/>
 * 2.锁定规则: unlock操作先行于后续的对同一个锁的lock操作<br/>
 * 3.volatile: 对volatile修饰的变量的写先行发生于对该变量的对操作<br/>
 * 4.传递规则: 对A的操作先于对B的操作,对B的操作先于对C的操作,则对A的操作先于对C的操作<br/>
 * 5.线程启动规则: 线程的Start()方法先于发生该线程的每一个动作<br/>
 * 6.线程中断规则: 对线程的interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生<br/>
 * 7.线程终止规则: 线程中的所有操作都先行发生于对此线程的终止检测。<br/>
 * 8.对象终结规则: 一个对象的初始化完成先行发生于它的finalize()方法的开始<br/>
 *<br/>
 * 小总结:<br/>
 *  本质是一种可见性。<br/>
 *  一部分是面向我们程序员提供的，也就是happens-before规则， 它通俗易懂的向我们程序员阐述了一个强内存模型，我们只要理解
 *  happens-before规则，就可以编写并发安全的程序了。<br/>
 *  另一部分是针对JVM实现的，为了尽可能少的对编译器和处理器做约束从而提高性能，JMM在不影响程序执行结果的前提下对其不做
 *  要求，即允许优化重排序。我们只需要关注 前者就好了，也就是理解happens-before规则即可，其它繁杂的内容有JMM规范结合操作
 *  系统给我们搞定，我们只写好代码即可。<br/>
 */
public class HappensBeforeDemo {
    static Integer integer = 1;

    public static void main(String[] args) {

    }
}
