package com.weison.io.net.socket.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpSocketClient {

    public void send(CountDownLatch countDownLatch) {
        try (
                // 和服务器创建连接
                Socket socket = new Socket("localhost", 8088);
                // 要发送给服务器的信息
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                // 从服务器接收的信息
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ) {
            log.info("[client Time:" + System.currentTimeMillis() + "] TcpSocketClient start");
            log.info("[client Time:" + System.currentTimeMillis() + "] 客户端发送信息");
            pw.write("你好👋，我是客户端樱木花道～");
            pw.flush();
            socket.shutdownOutput();

            String info;
            while ((info = br.readLine()) != null) {
                log.info("[client Time:" + System.currentTimeMillis() + "]  收到服务器返回信息：" + info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
