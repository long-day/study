package me.longday.juc.synchronizePro;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/10/6
 */
public class SynchronizeProDemo {
    public static void main(String[] args) {

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("=================");
        synchronized (o){
            o.hashCode();
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }
}
