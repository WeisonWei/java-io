package com.weison.io.net.socket.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpSocketServer {

    /**
     * https://blog.csdn.net/u014209205/article/details/80461122
     */
    public void start(CountDownLatch countDownLatch) {
        try (// 创建服务端socket
             ServerSocket serverSocket = new ServerSocket(8088);
             // 创建客户端socket
             Socket socket = new Socket()) {
            log.info("Time:" + System.currentTimeMillis() + " TcpSocketServer start");
            //循环监听等待客户端的连接
            do {
                // 监听客户端
                new Thread(() -> acceptAndReadClient(serverSocket)).start();
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    private void acceptAndReadClient(ServerSocket serverSocket) {

        try (
                Socket socket = serverSocket.accept();
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
        ) {
            InetAddress address = socket.getInetAddress();
            log.info("[Server Time:" + System.currentTimeMillis() + "] 当前连接客户端的IP：" + address.getHostAddress());

            String info;
            while ((info = br.readLine()) != null) {
                log.info("[Server Time:" + System.currentTimeMillis() + "] 收到客户端消息：" + info);
            }
            socket.shutdownInput();

            log.info("[Server Time:" + System.currentTimeMillis() + "] 发送消息：服务器欢迎你@@！");
            pw.write("服务器欢迎你@@！");
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
