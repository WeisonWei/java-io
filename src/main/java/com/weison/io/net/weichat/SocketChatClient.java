package com.weison.io.net.weichat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SocketChatClient {

    public void sendMessage(CountDownLatch countDownLatch) throws IOException {
        Socket socket = new Socket("localhost", 6666);
        new Thread(() -> sendMsg(socket, countDownLatch)).start();
    }

    private void sendMsg(Socket socket, CountDownLatch countDownLatch) {
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStream = socket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write("Hi,I'm Weison~" + System.currentTimeMillis());
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStreamWriter.close();
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
