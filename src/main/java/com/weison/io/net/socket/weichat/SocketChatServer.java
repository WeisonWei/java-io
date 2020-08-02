package com.weison.io.net.socket.weichat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SocketChatServer {

    public void start(CountDownLatch countDownLatch) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        //线程1
        AtomicInteger index = new AtomicInteger();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket capturedSocket = serverSocket.accept();
                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> capture(capturedSocket, countDownLatch, index.get())).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                index.getAndIncrement();
            }
        });
        thread.start();
    }

    private void capture(Socket capturedSocket, CountDownLatch countDownLatch, int index) {
        try {
            int len;
            byte[] data = new byte[1024];
            InputStream inputStream = capturedSocket.getInputStream();
            // (3) 按字节流方式读取数据
            while ((len = inputStream.read(data)) != -1) {
                log.info("收到新消息-" + index + "->" + new String(data, 0, len));
                countDownLatch.countDown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
