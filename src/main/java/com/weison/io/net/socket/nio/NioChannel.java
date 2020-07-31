package com.weison.io.net.socket.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 通道（Channel）：
 * 由java.nio.Channels包定义的。
 * Channel表示IO源与目标打开的连接。
 * Channel类似于传统中的“流”，只不过Channel本身不能直接访问数据，Channel只能与Buffer进行交互。
 * Channel在java NIO中负责缓冲区的数据传输。
 * Channel本身不存在数据，因此需要配合缓冲区进行传输。
 * https://baijiahao.baidu.com/s?id=1657880147579914448
 */
public class NioChannel {

    /**
     * Java 为 Channel 接口提供的最主要实现类如下：
     * <p>
     * FileChannel：用于读取、写入、映射和操作文件的通道。
     * DatagramChannel：通过 UDP 读写网络中的数据通道。
     * SocketChannel：通过 TCP 读写网络中的数据。
     * ServerSocketChannel：可以监听新进来的 TCP 连接，对每一个新进来的连接都会创建一个SocketChannel。
     *
     * @throws IOException
     */
    public void readFile() throws IOException {

        String inPath = "./user.md";
        String outPath = "./user1.md";
        // 获取源文件和目标文件的输入输出流
        FileInputStream fileInputStream = new FileInputStream(inPath);
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        // 创建缓冲区，分配1K堆内存
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        // 获取输入输出通道
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();
        while (true) {
            // clear方法重设缓冲区，使它可以接受读入的数据
            byteBuffer.clear();
            // 从输入通道中读取数据数据并写入buffer
            int read = inChannel.read(byteBuffer);
            // read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
            int position = byteBuffer.position();
            if (position == 0 && read == -1) {
                break;
            }
            // flip方法将 buffer从写模式切换到读模式
            byteBuffer.flip();
            // 从buffer中读取数据然后写入到输出通道中
            outChannel.write(byteBuffer);
        }

        //关闭通道
        inChannel.close();
        outChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }


    // 超时时间，单位毫秒
    private static final int TimeOut = 3000;
    // 本地监听端口
    private static final int ListenPort = 1937;

    public void selector() throws IOException {
        // 创建选择器
        Selector selector = Selector.open();
        // 打开监听信道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 与本地端口绑定
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(ListenPort));
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 将选择器绑定到监听信道,只有非阻塞信道才可以注册选择器.并在注册过程中指出该信道可以进行Accept操作
        // 一个serversocket channel准备好接收新进入的连接称为“接收就绪”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 反复循环,等待IO
        while (true) {
            // 等待某信道就绪(或超时)
            int keys = selector.select(TimeOut);
            //刚启动时连续输出0，client连接后一直输出1
            if (keys == 0) {
                System.out.println("独自等待.");
                continue;
            }

            // 取得迭代器，遍历每一个注册的通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    // 可通过Channel()方法获取就绪的Channel并进一步处理
                    SocketChannel channel = (SocketChannel) key.channel();
                    // TODO

                } else if (key.isConnectable()) {
                    // TODO
                } else if (key.isReadable()) {
                    // TODO
                } else if (key.isWritable()) {
                    // TODO
                }
                // 删除处理过的事件
                keyIterator.remove();
            }
        }
    }
}
