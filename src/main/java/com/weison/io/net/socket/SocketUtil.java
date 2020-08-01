package com.weison.io.net.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketUtil {

    public static void sendMessage(String host, int port, String message) throws IOException {
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes());
        outputStream.flush();
        outputStream.close();
        socket.shutdownOutput();
        socket.close();
    }
}
