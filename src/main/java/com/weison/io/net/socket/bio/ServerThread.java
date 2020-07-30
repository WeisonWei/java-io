package com.weison.io.net.socket.bio;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * https://blog.csdn.net/u014209205/article/details/80461122
 */
public class ServerThread extends Thread {

    private Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String info = null;

            while ((info = br.readLine()) != null) {
                System.out.println("Time:" + System.currentTimeMillis() + " 我是服务器，客户端说：" + info);
            }
            socket.shutdownInput();

            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("Time:" + System.currentTimeMillis() + " 服务器欢迎你");

            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(pw);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(socket);
        }
    }

}
