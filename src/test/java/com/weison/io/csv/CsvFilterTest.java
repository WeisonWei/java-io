package com.weison.io.csv;

import com.opencsv.bean.*;
import com.weison.io.csv.filter.AliPayBillCsvFilter;
import com.weison.io.csv.filter.UserCsvFilter;
import com.weison.io.model.AliPayBill;
import com.weison.io.model.UserCsvPosition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class CsvFilterTest {


    /**
     * https://www.jianshu.com/p/6414185b2f01
     * @throws IOException
     */
    @Test
    @DisplayName("read CSV file")
    @Order(3)
    public void readCsvFilter1() throws IOException {
        File file = new File("./user.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream);
        List<UserCsvPosition> csvData4 = readUserCsv(UserCsvPosition.class, reader);
        log.info("-4->" + csvData4);
    }

    public static <T> List<T> readUserCsv(Class<T> clazz, InputStreamReader in) {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(clazz);
        UserCsvFilter userCsvFilter = new UserCsvFilter();

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withFilter(userCsvFilter)
                .withIgnoreLeadingWhiteSpace(true)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }


    @Test
    public void readCsvFilter2() throws Exception {
        FileInputStream fileIs = new FileInputStream("./weison.csv");
        try {
            InputStreamReader reader = new InputStreamReader(fileIs);
            List<AliPayBill> aliPayBills = readAliPayCsv(AliPayBill.class, reader);
            log.info("============>" + aliPayBills);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileIs.close();
        }
        log.info("--------->>>");
    }

    public static <T> List<T> readAliPayCsv(Class<T> clazz, InputStreamReader in) {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withFilter(new AliPayBillCsvFilter())
                .withQuoteChar('\'')
                //.withSkipLines(6)
                .withIgnoreLeadingWhiteSpace(true)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }
}
