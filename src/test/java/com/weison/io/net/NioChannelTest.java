package com.weison.io.net;

import com.weison.io.net.socket.nio.NioChannel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class NioChannelTest {

    @Test
    public void readFileTest() throws IOException {
        NioChannel nioChannel = new NioChannel();
        nioChannel.readFile();
    }
}
