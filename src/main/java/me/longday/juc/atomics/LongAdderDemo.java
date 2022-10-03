package me.longday.juc.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 君
 * @version 1.0
 * @desc LongAdder TODO  这个没听懂  草了
 * @date 2022/10/3
 */
public class LongAdderDemo {
    static LongAdder longAdder = new LongAdder();
    public static void main(String[] args) {
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((x,y)-> x*y,1);

        longAccumulator.accumulate(2);
        System.out.println(longAccumulator.get());
        longAccumulator.accumulate(4);
        System.out.println(longAccumulator.get());

    }
}
