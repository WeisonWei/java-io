package com.weison.io.net.socket;

import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class UdpServer {

    public static void start(CountDownLatch countDownLatch) {
        try {
            // 要接收的报文
            byte[] bytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

            // 创建socket并指定端口
            DatagramSocket socket = new DatagramSocket(8088);
            log.info("Time:" + System.currentTimeMillis() + " UdpServer start" );

            // 接收socket客户端发送的数据。如果未收到会一致阻塞
            socket.receive(packet);
            String receiveMsg = new String(packet.getData(), 0, packet.getLength());
            log.info("Time:" + System.currentTimeMillis() + " packet.getLength:" + packet.getLength());
            log.info("Time:" + System.currentTimeMillis() + " receiveMsg:" + receiveMsg);

            // 关闭socket
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

}
