package com.weison.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileTest {

    public static void main(String[] args) {
        File file = new File("weison.md");
        boolean isFile = file.isFile();
        //Assertions.assertEquals(isFile, true);
    }

    @Test
    @Order(1)
    @DisplayName("创建文件")
    public void createFile() throws IOException {
        File file = new File("weison.md");
        boolean b = file.canExecute();
        boolean newFile = file.createNewFile();
        boolean isFile = file.isFile();

        File file1 = new File("./weison1.md");
        boolean b1 = file1.canExecute();
        boolean newFile1 = file1.createNewFile();
        boolean isFile1 = file1.isFile();

        File path = new File("files");
        boolean mkdir = path.mkdir();
        File file2 = new File(path, "weison2.md");
        boolean b2 = file2.canExecute();
        boolean newFile2 = file2.createNewFile();
        boolean isFile2 = file2.isFile();

        Assertions.assertEquals(isFile, true);
        Assertions.assertEquals(isFile1, true);
        Assertions.assertEquals(isFile2, true);
    }
}
