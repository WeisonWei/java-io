package com.weison.io.net;

import com.weison.io.net.socket.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SocketTest {

    @Test
    public void tcp() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        TcpServer tcpServer = new TcpServer();
        TcpClient tcpClient = new TcpClient();
        Thread serverThread = new Thread(() -> tcpServer.start(countDownLatch));
        Thread clientThread = new Thread(() -> tcpClient.send(countDownLatch));
        serverThread.start();
        clientThread.start();
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void udp() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        UdpServer udpServer = new UdpServer();
        UdpClient udpClient = new UdpClient();
        Thread serverThread = new Thread(() -> udpServer.start(countDownLatch));
        Thread clientThread = new Thread(() -> udpClient.send(countDownLatch));
        serverThread.start();
        clientThread.start();
        countDownLatch.await(10, TimeUnit.SECONDS);
    }


    @Test
    public void tcpFile() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        TcpFileServer tcpServer = new TcpFileServer();
        TcpFileClient tcpClient = new TcpFileClient();
        Thread serverThread = new Thread(() -> tcpServer.start(countDownLatch));
        Thread clientThread = new Thread(() -> tcpClient.sendFile(countDownLatch));
        serverThread.start();
        clientThread.start();
        countDownLatch.await(10, TimeUnit.SECONDS);
    }
}