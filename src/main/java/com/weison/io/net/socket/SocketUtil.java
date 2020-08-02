package com.weison.io.net.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketUtil {

    public static void sendMessage(String host, int port, String message) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.shutdownOutput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
