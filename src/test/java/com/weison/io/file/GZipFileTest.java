package com.weison.io.file;

import cn.hutool.core.util.ZipUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@DisplayName("文件压缩")
public class GZipFileTest {

    @Test
    @DisplayName("gzip工具类压缩")
    @Order(1)
    public void huToolGZip() {
        byte[] bytes = ZipUtil.gzip("123", "GBK");
        String str = ZipUtil.unGzip(bytes, "GBK");
        System.out.printf(str);
    }

    @Test
    @DisplayName("gzip压缩")
    @Order(2)
    public void gZip() throws Exception {
        byte[] bytes = compress("123", "GBK");
        String str = unCompress(bytes);
        System.out.printf(str);
    }

    /**
     * 压缩字符串
     * https://www.cnblogs.com/xiaohanlin/p/12888396.html
     *
     * @param str 待压缩的字符串
     * @param charset
     * @return 压缩后的字符串
     * @throws Exception 压缩过程中的异常
     */
    public static byte[] compress(String str, String charset) throws Exception {
        if (str == null || str.length() == 0) {
            return null;
        }
        // ByteArrayOutputStream 和 ByteArrayInputStream 是一个虚拟的流,
        // JDk源码中关闭方法是空的, 所以无需关闭, 为了代码整洁，还是放到了try-with-resource里面
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes());
            gzip.finish();
            return out.toByteArray();
        }
    }

    /**
     * 解压字符串
     *
     * @param bytes 待解压的字符串
     * @return 解压后的字符串
     * @throws Exception 解压过程中的异常
     */
    public static String unCompress(byte[] bytes) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             GZIPInputStream gunzip = new GZIPInputStream(in)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        }
    }

}
