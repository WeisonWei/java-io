package com.weison.io.socket;

import org.junit.jupiter.api.Test;

public class TcpTest {

    @Test
    public void tcp() throws InterruptedException {
        TcpServer.start();
        TcpClient.connect();
    }
}
