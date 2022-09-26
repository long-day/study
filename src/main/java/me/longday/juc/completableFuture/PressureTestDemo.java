package me.longday.juc.completableFuture;

import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/9/24
 */
public class PressureTestDemo {
    @Getter
    private final Socket socket;

    private final Random random = new Random();

    private int preAddr;
    private int newAddr;
    private int oldId;
    private int newId;
    public PressureTestDemo(String ip, int port) throws IOException {
        socket = new Socket(ip,port);
    }

    public Socket configSocket(){
        return this.socket;
    }

    /**
     * 改地址测试
     * @param beginId 老地址
     */
    public void changeAddrTest(int beginId) throws IOException {
        int sum = 1;
        preAddr = beginId;
        newAddr = getNewAddr();
        OutputStream socketOutputStream = socket.getOutputStream();
        InputStream socketInputStream = socket.getInputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketOutputStream));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"开启分支线程");
            int cnt = 1;
            while(true){
                try {
                    String s = bufferedReader.readLine();
                    System.out.println("第"+(cnt++)+"个返回值: "+s);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        },"socketReceive").start();
        while(true){
            System.out.println("已发送:"+(sum++)+"条数据");
            String newCommand = getCommandString();
            bufferedWriter.write(newCommand);
            bufferedWriter.flush();
            try {TimeUnit.SECONDS.sleep(4);} catch (InterruptedException e) {e.printStackTrace();}
        }

    }

    private int getNewAddr(){
        return random.nextInt(19)+1;
    }


    private String getCommandString(){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String oldId = decimalFormat.format(preAddr);
        String newId = decimalFormat.format(newAddr);
        String command = "change" + oldId + "_" + newId;

        preAddr = newAddr;
        newAddr = getNewAddr();
        if (preAddr == newAddr){
            newAddr++;
        }
        return command;
    }
    public static void main(String[] args) {
        try {
            PressureTestDemo pressureTestDemo = new PressureTestDemo("192.168.4.2", 32728);
            pressureTestDemo.changeAddrTest(1);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
