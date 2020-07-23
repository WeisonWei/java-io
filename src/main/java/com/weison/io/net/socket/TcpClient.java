package com.weison.io.net.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TcpClient {

    public void send(CountDownLatch countDownLatch) {
        try {
            // 和服务器创建连接
            Socket socket = new Socket("localhost", 8088);
            log.info("Time:" + System.currentTimeMillis() + " TcpClient start");

            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("Time:" + System.currentTimeMillis() + " 客户端发送信息");
            pw.flush();

            socket.shutdownOutput();

            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                log.info("Time:" + System.currentTimeMillis() + " 我是客户端，服务器返回信息：" + info);
            }

            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
