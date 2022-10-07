package me.longday.juc.objectMemoryDistribution;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author 君
 * @version 1.0
 * @desc object memory distribution
 * @date 2022/10/5
 */
public class JolDemo {
    public static void main(String[] args) {
        //System.out.println(VM.current().details());
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
