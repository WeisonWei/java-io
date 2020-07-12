package com.weison.io.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@Slf4j
@DisplayName("Scanner 测试")
public class ScannerTest {

    /**
     * help ->Edit Custom VM Options... -> 加上
     * -Deditable.java.test.console=true
     */
    @Test
    @Order(1)
    public void in() {
        Scanner scanner = new Scanner(System.in);
        //log.info("请输入：");
        System.out.println("请输入：");
        while (true) {
            String str = scanner.nextLine();
            if (str.equals("exit")) {
                log.info("---->" + str);
                break;
            }
        }

        while (true) {
            int i = scanner.nextInt();
            if (i == 110) {
                log.info("---->" + i);
                break;
            }
        }

        while (true) {
            byte b = scanner.nextByte();
            if (b == 10) {
                log.info("---->" + b);
                break;
            }
        }

        while (true) {
            double v = scanner.nextDouble();
            if (v == 1.34) {
                log.info("---->" + v);
                break;
            }
        }
    }
}
