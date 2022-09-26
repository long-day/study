package me.longday.juc.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/9/22
 */
public class CompletableFutureApiDemo {
    public static void main(String[] args) {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        System.out.println(stringCompletableFuture.join());
    }
}
