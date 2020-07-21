package com.weison.io.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(35)
@Data
@ToString
public class UserExcel {
    @ColumnWidth(50)
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("城市")
    private String city;

    @ExcelProperty("日期")
    @DateTimeFormat("yyyy年MM月dd天 HH时mm分ss秒")
    private Date date;

    @ExcelProperty("金额")
    @NumberFormat("#.##%")
    private Double amount;

    @ExcelIgnore
    private Double balance;
}
