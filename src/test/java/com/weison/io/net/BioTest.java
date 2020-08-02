package com.weison.io.net;

import com.weison.io.net.socket.bio.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BioTest {

    @Test
    void tcpSocketTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        TcpSocketServer tcpSocketServer = new TcpSocketServer();
        TcpSocketClient tcpSocketClient = new TcpSocketClient();
        Thread serverThread = new Thread(() -> tcpSocketServer.start(countDownLatch));
        Thread clientThread = new Thread(() -> tcpSocketClient.send(countDownLatch));
        serverThread.start();
        clientThread.start();
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    void udpSocketTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        UdpSocketServer udpSocketServer = new UdpSocketServer();
        UdpSocketClient udpSocketClient = new UdpSocketClient();
        Thread serverThread = new Thread(() -> udpSocketServer.start(countDownLatch));
        Thread clientThread = new Thread(() -> udpSocketClient.send(countDownLatch));
        serverThread.start();
        clientThread.start();
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    void tcpFileTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        TcpFileServer tcpServer = new TcpFileServer();
        TcpFileClient tcpClient = new TcpFileClient();
        Thread serverThread = new Thread(() -> tcpServer.start(countDownLatch));
        Thread clientThread = new Thread(() -> tcpClient.sendFile(countDownLatch));
        serverThread.start();
        clientThread.start();
        countDownLatch.await(5, TimeUnit.SECONDS);
    }
}
