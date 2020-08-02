package com.weison.io.net.socket.weichat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioChatServer {

    public void start() throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();
        new Thread(() -> run(serverSelector, clientSelector)).start();
    }

    private void run(Selector serverSelector, Selector clientSelector) {
        try {
            // 对应IO编程中服务端启动
            ServerSocketChannel listenerChannel = ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(8000));
            listenerChannel.configureBlocking(false);
            listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                boolean haveNew = serverSelector.select(1) > 0;
                if (haveNew) {
                    Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                            channel.configureBlocking(false);
                            channel.register(clientSelector, SelectionKey.OP_READ);
                            iterator.remove();
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
