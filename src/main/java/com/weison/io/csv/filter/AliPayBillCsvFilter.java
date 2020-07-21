package com.weison.io.csv.filter;

import com.opencsv.bean.CsvToBeanFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliPayBillCsvFilter implements CsvToBeanFilter {
    @Override
    public boolean allowLine(String[] line) {
        if (line.length > 0 && line[0].startsWith("#")) {
            return false;
        }
        return true;
    }
}
