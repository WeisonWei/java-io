package com.weison.io.file;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileOperations {

    public static void deleteZipFiles() {
        String path = "./";
        File file = new File(path);
        List<String> list = FileUtil.listFileNames(file.getAbsolutePath());
        List<Boolean> collect = list
                .stream()
                .filter(fileName -> fileName.endsWith(".zip"))
                .map(fileName -> file.getAbsolutePath() + File.separator + fileName)
                .map(aaa -> {
                    log.info(aaa);
                    return aaa;
                })
                .map(FileUtil::del)
                .collect(Collectors.toList());
        log.info("delete over");
    }
}
