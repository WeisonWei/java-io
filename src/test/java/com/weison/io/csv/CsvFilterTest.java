package com.weison.io.csv;

import com.opencsv.bean.*;
import com.weison.csv.filter.AliPayBillCsvToBeanFilter;
import com.weison.csv.filter.UserCsvToBeanFilter;
import com.weison.io.model.AliPayBill;
import com.weison.io.model.UserCsvFiler;
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

    @Test
    @DisplayName("read CSV file")
    @Order(3)
    public void readCsv() throws IOException {
        File file = new File("./user21.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream);
        List<UserCsvFiler> csvData4 = readUserCsv(UserCsvFiler.class, reader);
        log.info("-4->" + csvData4);
    }


    public static <T> List<T> readUserCsv(Class<T> clazz, InputStreamReader in) {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(clazz);
        UserCsvToBeanFilter userCsvToBeanFilter = new UserCsvToBeanFilter();

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withFilter(userCsvToBeanFilter)
                .withIgnoreLeadingWhiteSpace(true)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }


    @Test
    public void testAliPayBill() throws Exception {
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
                .withFilter(new AliPayBillCsvToBeanFilter())
                .withQuoteChar('\'')
                //.withSkipLines(6)
                .withIgnoreLeadingWhiteSpace(true)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }
}
