package com.weison.io.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MainTest {
    /**
     * 执行 java MainTest 1,2,3
     * 00:59:04.209 [main] INFO com.weison.stream.MainTest - 1,2,3
     * @param args
     */
    public static void main(String[] args) {
        Arrays.stream(args).forEach(log::info);
    }
}
