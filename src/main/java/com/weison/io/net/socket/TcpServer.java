package com.weison.io.net.socket;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpServer {

    public void start(CountDownLatch countDownLatch) {
        try {
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);

            // 创建客户端socket
            Socket socket = new Socket();
            log.info("Time:" + System.currentTimeMillis() + " TcpServer start" );

            //循环监听等待客户端的连接
            while (true) {
                // 监听客户端
                socket = serverSocket.accept();

                ServerThread thread = new ServerThread(socket);
                thread.start();

                InetAddress address = socket.getInetAddress();
                log.info("Time:" + System.currentTimeMillis() + " 当前客户端的IP：" + address.getHostAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
