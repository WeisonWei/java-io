package com.weison.io.net.socket;

import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class UdpClient {

    public static void send(CountDownLatch countDownLatch) {
        try {
            // 要发送的消息
            String sendMsg = "客户端发送的消息";

            // 获取服务器的地址
            InetAddress addr = InetAddress.getByName("localhost");

            // 创建packet包对象，封装要发送的包数据和服务器地址和端口号
            DatagramPacket packet = new DatagramPacket(sendMsg.getBytes(),
                    sendMsg.getBytes().length, addr, 8088);

            // 创建Socket对象
            DatagramSocket socket = new DatagramSocket();
            log.info("Time:" + System.currentTimeMillis() + " UdpClient start" );

            // 发送消息到服务器
            socket.send(packet);
            log.info("Time:" + System.currentTimeMillis() + " send:" + sendMsg);

            // 关闭socket
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
