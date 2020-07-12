package com.weison.io.file;

import cn.hutool.core.util.ZipUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@DisplayName("文件压缩")
public class ZipFileTest {

    @Test
    @DisplayName("zip工具类压缩")
    @Order(1)
    public void hutoolZip() {
        File zip = ZipUtil.zip("../../user.md", "../../user-2020-07-11.zip");
    }

    /**
     * [参考](https://www.cnblogs.com/zeng1994/p/7862288.html)
     * @throws IOException
     */
    @Test
    @DisplayName("zip原生类压缩")
    @Order(2)
    public void zip() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("user-2020-07-12.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);


        FileInputStream fileInputStream = new FileInputStream("user.md");
        BufferedInputStream bis = new BufferedInputStream(fileInputStream);

        byte[] bytes = new byte[1024];
        ZipEntry zipEntry = new ZipEntry("./user.md");
        zipOutputStream.putNextEntry(zipEntry);
        int len;
        while ((len = bis.read(bytes, 0, bytes.length)) != -1) {
            zipOutputStream.write(bytes, 0, len);
        }
        zipOutputStream.closeEntry();
        bis.close();
        fileInputStream.close();
        zipOutputStream.close();
    }

    @Test
    @DisplayName("zip原生类压缩")
    @Order(3)
    public void zip(String input, String output, String name) throws Exception {
        //要生成的压缩文件
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(output));
        String[] paths = input.split("\\|");
        File[] files = new File[paths.length];
        byte[] buffer = new byte[1024];
        for (int i = 0; i < paths.length; i++) {
            files[i] = new File(paths[i]);
        }
        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files[i]);
            BufferedInputStream bis = new BufferedInputStream(fis);
            if (files.length == 1 && name != null) {
                out.putNextEntry(new ZipEntry(name));
            } else {
                out.putNextEntry(new ZipEntry(files[i].getName()));
            }
            int len;
            // 读入需要下载的文件的内容，打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
    }

}
