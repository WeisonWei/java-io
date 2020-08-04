package com.weison.io.net.socket.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpFileClient {

    public void sendFile(CountDownLatch countDownLatch) {
        try ( // 和服务器创建连接
              Socket socket = new Socket("localhost", 8088);
              OutputStream outputStream = socket.getOutputStream();
              FileInputStream fileInputStream = new FileInputStream("./user.md");
              InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                outputStream.write(str.getBytes());
            }
            outputStream.flush();
            socket.shutdownOutput();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
