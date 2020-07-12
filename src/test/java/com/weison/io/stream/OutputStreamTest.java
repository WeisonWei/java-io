package com.weison.io.stream;

import com.weison.io.model.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.*;

@Slf4j
public class OutputStreamTest {

    @Ignore
    @Test
    @Order(1)
    @DisplayName("write once")
    public void fileWrite() throws IOException {
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }
        String content = "Today is a good Day";
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Test
    @Order(2)
    @DisplayName("write times")
    public void fileWriteByte() throws IOException {
        long start = System.nanoTime();
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        String content = "Today is a good Day,and I want to go to out side play basketball.";
        for (int i = 0; i < 100; i++) {
            //fileOutputStream.write(content.getBytes(), 0, content.length());
            fileOutputStream.write(content.getBytes());
        }
        fileOutputStream.close();
        long end = System.nanoTime();
        log.info("-fileWriteByte-cost->" + (end - start));
    }

    @Test
    @Order(3)
    @DisplayName("buffered write times")
    public void bufferedWriteByte() throws IOException {
        long start = System.nanoTime();
        File file = new File("weison.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }

        String content = "Today is a good Day,and I want to go to out side play basketball.";
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        for (int i = 0; i < 100; i++) {
            bufferedOutputStream.write(content.getBytes());
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        long end = System.nanoTime();
        log.info("-fileWriteByte-cost->" + (end - start));
    }

    @Test
    @Order(4)
    @DisplayName("objectOutputStream times")
    public void objectOutputStream() throws IOException {
        File file = new File("user.md");
        boolean exists = file.exists();
        if (!exists) {
            boolean newFile = file.createNewFile();
            log.info("-newFile->" + newFile);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        User user = new User().setName("Weison").setAge(18).setCity("Xi'An");
        //objectOutputStream.writeUTF("今天是好天气");
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
    }
}
