package me.longday.socket;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/9/24
 */
public class MyServer {
    public static void main(String[] args) {
        try (   ServerSocket serverSocket = new ServerSocket(32728);
        ){
            System.out.println("---正在监听---");
            Socket acceptSocket = serverSocket.accept();
            System.out.println(acceptSocket.getLocalAddress());
            OutputStream socketOutputStream = acceptSocket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketOutputStream));
            while (true){
                bufferedWriter.write("hello"+"\r");
                bufferedWriter.flush();
//                socketOutputStream.write("hello".getBytes(StandardCharsets.UTF_8));
                TimeUnit.SECONDS.sleep(1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
