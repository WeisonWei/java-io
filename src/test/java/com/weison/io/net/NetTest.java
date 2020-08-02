package com.weison.io.net;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Slf4j
public class NetTest {

    /**
     * https://www.bilibili.com/video/BV1LJ411z7vY?p=4
     *
     * @throws InterruptedException
     * @throws UnknownHostException
     */
    @Test
    void ip() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        InetAddress address1 = InetAddress.getByName("localhost");
        InetAddress address2 = InetAddress.getByName("www.baidu.com");
        log.info("-->" + address);
        log.info("-1->" + address1);
        log.info("-2->" + address2);

        String hostAddress = address2.getHostAddress();//ip
        byte[] address3 = address2.getAddress();
        String canonicalHostName = address2.getCanonicalHostName();//规范的名字
        String hostName = address2.getHostName();//域名或主机名

        log.info("-3->" + hostAddress);
        log.info("-4->" + new String(address3));
        log.info("-5->" + canonicalHostName);
        log.info("-6->" + hostName);
    }

    @Test
    void socketIp() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
        InetSocketAddress address1 = new InetSocketAddress("localhost", 8080);
        log.info("-1->" + address);
        log.info("-2->" + address1);
    }

}
