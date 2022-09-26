package me.longday.juc.completableFuture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 君
 * @version 1.0
 * @desc 多线程秒杀案例
 * @date 2022/9/22
 */
public class CompletableFutureDemo {
    static List<NetMall> netMalls = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dd"),
            new NetMall("tb"),
            new NetMall("pdd")
    );
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        getPrice(netMalls,"mysql1").forEach(System.out::println);
        long endTime = System.currentTimeMillis();
        System.out.println("----花费时间为: "+(endTime - startTime)/1000.0+"秒");

        System.out.println("-------------------------------------");
        long startTime2 = System.currentTimeMillis();
        getCompletablePrice(netMalls,"mysql").forEach(System.out::println);
        long endTime2 = System.currentTimeMillis();
        System.out.println("----花费时间为: "+(endTime2 - startTime2)/1000.0+"秒");

    }

    static List<String> getPrice(List<NetMall> netMalls,String productName){
        return netMalls.stream().map(
                netMall -> String.format("《%s》 in %s price is %.2f",
                        productName,
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))).collect(Collectors.toList());
    }

    static List<String> getCompletablePrice(List<NetMall> netMalls,String productName){
       return netMalls.stream().map(
                       netMall -> CompletableFuture.supplyAsync(() -> String.format("《%s》 in %s price is %.2f",
                               productName,
                               netMall.getNetMallName(),
                               netMall.calcPrice(productName))

                       ))
               .toList()
               .stream()
               .map(CompletableFuture::join)
               .collect(Collectors.toList());
    }
}

@AllArgsConstructor
@Getter
class NetMall{
    private String netMallName;

    public double calcPrice(String productName){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble()*2+productName.charAt(0);
    }

}