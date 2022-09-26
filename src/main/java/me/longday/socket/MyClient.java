package me.longday.socket;

import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author 君
 * @version 1.0
 * @desc TODO
 * @date 2022/9/24
 */
public class MyClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("192.168.43.25", 33333);
                InputStream socketInputStream = socket.getInputStream();
                OutputStream socketOutputStream = socket.getOutputStream();
        ) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
            while (true) {
                String s = bufferedReader.readLine();
                System.out.println("数据："+s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
