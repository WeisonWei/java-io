package com.weison.io.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.weison.csv.filter.AliPayBillCsvToBeanFilter;
import com.weison.io.model.AliPayBill;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class CsvTest1 {


    @Test
    public void testAliPayBill() throws Exception {
        FileInputStream fileIs = new FileInputStream("./");
        BufferedInputStream bufferIs = new BufferedInputStream(fileIs);
        ZipInputStream zipInputStream = new ZipInputStream(bufferIs, Charset.forName("GBK"));
        int available = zipInputStream.available();
        ZipEntry zipEntry = null;
        try {
            //遍历ZIP包中的文件
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                log.info(String.format("支付宝对账--读取对账ZIP文件[文件名:%s,文件大小:%s]", zipEntry.getName(), zipEntry.getSize()));
                //如果查找到业务明细csv则直接处理退出循环
                if (zipEntry.getName().lastIndexOf("业务明细.csv") > 0) {
                    //新建一个out,指向fname，fname是输出地址
                    InputStreamReader reader = new InputStreamReader(zipInputStream);
                    List<AliPayBill> aliPayBills = readCsv(AliPayBill.class, reader);
                    log.info("============>" + aliPayBills);
                    reader.close();
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            fileIs.close();
            bufferIs.close();
            zipInputStream.close();
        }
        log.info("--------->>>");
    }

    public static <T> List<T> readCsv(Class<T> clazz, InputStreamReader in) {
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withFilter(new AliPayBillCsvToBeanFilter())
                .withQuoteChar('\'')
                .withSkipLines(6)
                //.withIgnoreLeadingWhiteSpace(true)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }
}
