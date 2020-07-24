package com.weison.io.net;

import com.weison.io.net.weichat.NioChatClient;
import com.weison.io.net.weichat.NioChatServer;
import com.weison.io.net.weichat.SocketChatClient;
import com.weison.io.net.weichat.SocketChatServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChatTest {

    @Test
    public void chatTest() throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        SocketChatClient chatClient = new SocketChatClient();
        SocketChatServer chatServer = new SocketChatServer();
        chatServer.start(countDownLatch);
        int index = 0;
        while (true) {
            if (index == 25) {
                break;
            }
            chatClient.sendMessage(countDownLatch);
            index++;
        }
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void nioChatTest() throws IOException, InterruptedException {
        NioChatClient chatClient = new NioChatClient();
        NioChatServer chatServer = new NioChatServer();
        chatServer.start();
        chatClient.sendMessage();
    }
}
