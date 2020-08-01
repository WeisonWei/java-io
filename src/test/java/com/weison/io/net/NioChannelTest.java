package com.weison.io.net;

import com.weison.io.net.socket.SocketUtil;
import com.weison.io.net.socket.nio.NioChannel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class NioChannelTest {

    @Test
    public void readFileTest() throws IOException {
        NioChannel nioChannel = new NioChannel();
        nioChannel.readFile();
    }

    @Test
    public void socketTest() throws IOException {
        NioChannel nioChannel = new NioChannel();
        nioChannel.selector();
        SocketUtil.sendMessage("localhost", 1937, "Hello ErBao~,I'm dad~");
    }
}
