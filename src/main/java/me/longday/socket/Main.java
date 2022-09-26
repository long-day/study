package me.longday.socket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/9/24
 */
public class Main {
    public static void main(String[] args) {

        try (
                Socket socket = new Socket("192.168.43.25", 33333);
                InputStream socketInputStream = socket.getInputStream();
                OutputStream socketOutputStream = socket.getOutputStream();
        ) {
            byte[] bytes = new byte[1024];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socketInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
            while (true) {
//                int len = bufferedInputStream.read(bytes);
//                for (int i = 0; i < len; i++) {
//                    System.out.printf("数据: % 1 $c" + bytes[i]);
//                }
                String s = bufferedReader.readLine();
                System.out.println("数据: "+s);
//                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
