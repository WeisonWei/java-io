package com.weison.io.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class FileDownloadTest {

    private final static String FILE_URL = "https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png";

    @Test
    public void downloadFile() throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread;

        URL url = new URL(FILE_URL);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("./baidu.png");

            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
