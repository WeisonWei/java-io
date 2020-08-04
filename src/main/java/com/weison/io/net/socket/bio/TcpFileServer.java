package com.weison.io.net.socket.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpFileServer {

    public void start(CountDownLatch countDownLatch) {
        try ( // 创建服务端socket
              ServerSocket serverSocket = new ServerSocket(8088))
        {
            log.info("Time:" + System.currentTimeMillis() + " TcpSocketServer start");
            //循环监听等待客户端的连接
            while (true) {
                // 监听客户端
                try (Socket socket = serverSocket.accept()) {
                    readSocket(socket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    private void readSocket(Socket socket) throws IOException {
        try (InputStream inputStream = socket.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             FileOutputStream fileOutputStream = new FileOutputStream("./socketReceive.md");
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream)) {

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                log.info("str--->" + str);
                outputStreamWriter.write(str);
            }

            outputStreamWriter.flush();
            InetAddress address = socket.getInetAddress();
            log.info("Time:" + System.currentTimeMillis() + " 当前客户端的IP：" + address.getHostAddress());
        }
    }
}
