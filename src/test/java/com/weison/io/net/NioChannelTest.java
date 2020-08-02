package com.weison.io.net;

import com.weison.io.net.socket.SocketUtil;
import com.weison.io.net.socket.nio.NioChannel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class NioChannelTest {

    @Test
    void fileChannelTest() throws IOException {
        NioChannel nioChannel = new NioChannel();
        nioChannel.readFile();
    }

    @Test
    void socketChannelTest() {
        NioChannel nioChannel = new NioChannel();
        new Thread(() -> nioChannel.nioServer(6666)).start();
        new Thread(() -> SocketUtil.sendMessage("localhost", 6666, "Hello ErBao~,I'm dad~")).start();
    }

    @Test
    void socketChannelSelectorTest() {
        NioChannel nioChannel = new NioChannel();
        new Thread(() -> nioChannel.selector(6666)).start();
        new Thread(() -> SocketUtil.sendMessage("localhost", 6666, "Hello ErBao~,I'm dad~")).start();
    }
}
