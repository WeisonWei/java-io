package com.weison.io.net.socket.bio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

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
        try {
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);

            // 创建客户端socket
            Socket socket = new Socket();
            log.info("Time:" + System.currentTimeMillis() + " TcpSocketServer start");

            //循环监听等待客户端的连接
            while (true) {
                // 监听客户端
                new Thread(() -> acceptAndReadClient(serverSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    private void acceptAndReadClient(ServerSocket serverSocket) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info;
            while ((info = br.readLine()) != null) {
                System.out.println("Time:" + System.currentTimeMillis() + " 我是服务器，客户端说：" + info);
            }
            socket.shutdownInput();

            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("Time:" + System.currentTimeMillis() + " 服务器欢迎你");
            pw.flush();

            InetAddress address = socket.getInetAddress();
            log.info("Time:" + System.currentTimeMillis() + " 当前客户端的IP：" + address.getHostAddress());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(pw);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(socket);
        }
    }
}
