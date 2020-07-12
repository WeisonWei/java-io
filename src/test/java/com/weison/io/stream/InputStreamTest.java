package com.weison.io.stream;

import com.weison.io.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.*;

@Slf4j
@DisplayName("InputStream测试")
public class InputStreamTest {

    @Test
    @Order(1)
    @DisplayName("read")
    public void fileRead() throws IOException {
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }

        InputStream fileInputStream = new FileInputStream(file);
        int content, i;
        content = fileInputStream.read();
        for (i = 0; content != -1; i++) {
            log.info("-1->" + (char) content);
            content = fileInputStream.read();
        }

        log.info("-length->" + i);
        fileInputStream.close();
    }

    @Test
    @Order(2)
    @DisplayName("read byte")
    public void fileReadByte() throws IOException {
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }

        InputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[8];

        int content, i;
        content = fileInputStream.read(bytes);
        for (i = 0; content != -1; i++) {
            log.info("-1->" + new String(bytes));
            content = fileInputStream.read(bytes);
        }

        log.info("-length->" + i);
        fileInputStream.close();
    }

    @Test
    @Order(4)
    @DisplayName("read bytes")
    public void fileReadBytes() throws IOException {
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }

        InputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[8];

        int content, i;
        content = fileInputStream.read(bytes, 0, 2);
        for (i = 0; content != -1; i++) {
            log.info("-1->" + new String(bytes));
            content = fileInputStream.read(bytes, 0, 2);
        }

        log.info("-length->" + i);
        fileInputStream.close();
    }

    /**
     * cost --> 32316170
     *
     * @throws IOException
     */
    @Test
    @Order(5)
    @DisplayName("性能比对 read")
    public void readTest() throws IOException {
        long start = System.nanoTime();
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }

        InputStream fileInputStream = new FileInputStream(file);

        for (int i = 0; fileInputStream.read() != -1; i++) {
            //log.info("-1->" + new String(bytes));
        }
        fileInputStream.close();
        long end = System.nanoTime();
        log.info("-fileWriteByte-cost->" + (end - start));
    }

    /**
     * cost --> 1382950
     *
     * @throws IOException
     */
    @Test
    @Order(6)
    @DisplayName("性能比对 buffered read bytes")
    public void bufferedReadTest() throws IOException {
        long start = System.nanoTime();
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }

        InputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        for (int i = 0; bufferedInputStream.read() != -1; i++) {
            //log.info("-1->" + new String(bytes));
        }
        bufferedInputStream.close();
        long end = System.nanoTime();
        log.info("-fileWriteByte-cost->" + (end - start));
    }

    @Test
    @Order(7)
    @DisplayName("objectInputStream times")
    public void objectInputStream() throws IOException, ClassNotFoundException {
        File file = new File("user.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        User user = (User) object;
        log.info("user-->" + user);
    }
}
