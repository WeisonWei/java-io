package com.weison.io.net.weichat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

@Slf4j
public class NioChatClient {

    public void sendMessage() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        new Thread(() -> sendMsg(socket)).start();
    }

    private void sendMsg(Socket socket) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
