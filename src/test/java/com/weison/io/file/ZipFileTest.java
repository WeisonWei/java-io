package com.weison.io.file;

import cn.hutool.core.util.ZipUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@DisplayName("文件压缩")
public class ZipFileTest {
    /**
     * 在JAVA IO中，不仅可以实现ZIP压缩格式的输入、输出，也可以实现JAR及GZIP文件格式的压缩：
     * 1、JAR压缩的支持类保存在java.util.jar包中
     * 常用的类有 JarOutputStream（JAR压缩输出流）、JarInputStream（JAR压缩输入流）、JARFile（JAR文件）、JAREntry（JAR实体）
     * <p>
     * 2、GZIP是用于UNIX系统的文件压缩，在Linux中经常会使用到*.gz的文件，就是GZIP格式，GZIP压缩的支持类保存在java.util.zip包中
     * 常用的类有  GZIPOutputStream（GZIP压缩输出流）、GZIPInputStream（GZIP压缩输入流）
     * <p>
     * 注意：
     * 1、压缩文件中的每一个压缩实体都使用ZipEntry保存，一个压缩文件中可能包含一个或多个ZipEntry对象。
     * 2、在JAVA中可以进行zip、jar、gz三种格式的压缩支持，操作流程基本上是一致的。
     * 3、ZipOutputStream可以进行压缩的输出，但是输出的位置不一定是文件。
     * 4、ZipFile表示每一个压缩文件，可以得到每一个压缩实体的输入流。
     * <p>
     * 原文链接：https://blog.csdn.net/qq_36761831/article/details/80643163
     */

    @Test
    @DisplayName("zip工具类压缩")
    @Order(1)
    void huToolZip() {
        File zip = ZipUtil.zip("../../user.csv", "../../user-2020-07-11.zip");
    }

    /**
     * [参考](https://www.cnblogs.com/zeng1994/p/7862288.html)
     *
     * @throws IOException
     */
    @Test
    @DisplayName("zip原生类压缩")
    @Order(2)
    void zip() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("user-2020-07-12.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);


        FileInputStream fileInputStream = new FileInputStream("user.csv");
        BufferedInputStream bis = new BufferedInputStream(fileInputStream);

        byte[] bytes = new byte[1024];
        ZipEntry zipEntry = new ZipEntry("./user.csv");
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


    @Test
    @DisplayName("zip工具类un压缩")
    @Order(4)
    void huToolUnZip() {
        File zip = ZipUtil.unzip("../../user-2020-07-12.zip");
    }

    @Test
    @DisplayName("zip工具类un压缩")
    @Order(4)
    void unZip() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./user-2020-07-12.zip");
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);

        byte doc[];
        ZipEntry nextEntry;
        while ((nextEntry = zipInputStream.getNextEntry()) != null &&
                !nextEntry.isDirectory()) {
            File file = new File("./" + nextEntry.getName());

            if (nextEntry.isDirectory()) {
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                //fname是文件名,fileoutputstream与该文件名关联
                try {
                    //新建一个out,指向fname，fname是输出地址
                    FileOutputStream out = new FileOutputStream(file);
                    doc = new byte[512];
                    int n;
                    //若没有读到，即读取到末尾，则返回-1
                    while ((n = zipInputStream.read(doc, 0, 512)) != -1) {
                        //这就把读取到的n个字节全部都写入到指定路径了
                        out.write(doc, 0, n);
                    }
                    out.close();
                } catch (Exception ex) {
                    System.out.println("there is a problem");
                }
            }
            zipInputStream.closeEntry(); // 关闭当前entry
        }
        zipInputStream.close();
    }

}
