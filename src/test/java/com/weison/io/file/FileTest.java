package com.weison.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileTest {


    @Test
    @Order(1)
    @DisplayName("is文件")
    void isFile() {
        File file = new File("weison.md");
        boolean isFile = file.isFile();
        Assertions.assertEquals(isFile, true);
    }

    @Test
    @Order(2)
    @DisplayName("创建文件")
    void createFile() throws IOException {
        File file = new File("weison.md");
        file.canExecute();
        file.createNewFile();
        boolean isFile = file.isFile();

        File file1 = new File("./weison1.md");
        file1.canExecute();
        file1.createNewFile();
        boolean isFile1 = file1.isFile();

        File path = new File("files");
        path.mkdir();
        File file2 = new File(path, "weison2.md");
        file2.canExecute();
        file2.createNewFile();
        boolean isFile2 = file2.isFile();

        Assertions.assertEquals(isFile, true);
        Assertions.assertEquals(isFile1, true);
        Assertions.assertEquals(isFile2, true);
    }

    @Test
    @Order(2)
    @DisplayName("删除文件")
    void deleteFile() {
        FileOperations.deleteZipFiles();
    }
}
