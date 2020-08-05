package com.weison.io.net;

import com.weison.io.net.socket.SocketUtil;
import com.weison.io.net.socket.nio.NioChannel;
import com.weison.io.net.socket.nio.NioClient;
import com.weison.io.net.socket.nio.NioServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

class NioTest {

    @Test
    void nioTest() throws InterruptedException {
        new Thread(() -> new NioServer().start(7001)).start();
        new Thread(() -> new NioClient().start(7001)).start();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    void fileChannelTest() throws IOException {
        NioChannel nioChannel = new NioChannel();
        nioChannel.fileChannel();
    }

    @Test
    void socketChannelTest() throws InterruptedException, IOException {
        NioChannel nioChannel = new NioChannel();
        new Thread(() -> nioChannel.nioServer("localhost", 6666)).start();
        new Thread(() -> SocketUtil.bioSendMessage("localhost", 6666, "Hello ErBao~,I'm dad~")).start();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    void socketChannelSelectorTest() {
        NioChannel nioChannel = new NioChannel();
        new Thread(() -> nioChannel.selector("localhost", 6666)).start();
        new Thread(() -> SocketUtil.bioSendMessage("localhost", 6666, "Hello ErBao~,I'm dad~")).start();
    }
}
