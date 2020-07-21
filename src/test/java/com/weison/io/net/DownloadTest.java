package com.weison.io.net;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class DownloadTest {

    private final static String FILE_URL = "https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png";
    private final static String FILE_URL1 = "https://fcww25.com/get_file/1/8370f485fe4468c8c5054089edbf8d8b1938b7a4f7/51000/51244/51244.mp4/?rnd=1595173337505";
    private final static String FILE_URL2 = "https://fcww25.com/get_file/1/8370f485fe4468c8c5054089edbf8d8b1938b7a4f7/51000/51244/51244.mp4";

    @Test
    public void downloadFile() throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(FILE_URL);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("./baidu.png");

            byte[] buffer = new byte[1024];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadFile1() throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(FILE_URL2);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("./baidu.mp4");

            byte[] buffer = new byte[2048];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
