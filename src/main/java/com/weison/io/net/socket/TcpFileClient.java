package com.weison.io.net.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpFileClient {

    public void sendFile(CountDownLatch countDownLatch) {
        try {
            // 和服务器创建连接
            InetAddress localhost = InetAddress.getByName("localhost");
            int port = 8088;
            Socket socket = new Socket(localhost, port);
            OutputStream outputStream = socket.getOutputStream();

            FileInputStream fileInputStream = new FileInputStream("./user.md");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                outputStream.write(str.getBytes());
            }
            outputStream.flush();
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
