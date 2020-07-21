package com.weison.io.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.weison.io.excel.liscener.UserDataListener;
import com.weison.io.model.User;
import com.weison.io.model.UserExcel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    @Test
    public void read() {
        ExcelReaderBuilder read = EasyExcel.read("./user.xls", User.class, new UserDataListener());
        read.sheet().doRead();

        ExcelReaderBuilder read1 = EasyExcel.read("./user.xlsx", User.class, new UserDataListener());
        read1.sheet().doRead();
    }

    /**
     * 普通写
     */
    @Test
    public void write() {
        File file = new File("./wxx.xlsx");
        EasyExcel.write(file)
                .head(setHead())
                .sheet("用户信息表")
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(setData());
    }

    /**
     * 注解写
     */
    @Test
    public void writeAnnotation() {
        File file = new File("./wxx1.xlsx");
        EasyExcel.write(file, UserExcel.class)
                .sheet("用户信息表1")
                .registerWriteHandler(createExcelStyle())
                .doWrite(setData());
    }

    public List<List<String>> setHead() {
        List<List<String>> head = new ArrayList<>();
        List<String> head1 = new ArrayList<>();
        head1.add("用户信息");
        head1.add("姓名");
        head.add(head1);

        List<String> head2 = new ArrayList<>();
        head2.add("用户信息");
        head2.add("年龄");
        head.add(head2);

        List<String> head3 = new ArrayList<>();
        head3.add("用户信息");
        head3.add("城市");
        head.add(head3);
        return head;
    }

    public List<List<String>> setData() {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> hangData = new ArrayList<>();
            hangData.add("姓名" + i);
            hangData.add("年龄" + i);
            hangData.add("城市" + i);
            data.add(hangData);
        }
        return data;
    }

    public WriteHandler createExcelStyle() {
        //头 设置
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //头 背景颜色设置
        headWriteCellStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());

        //头 字体设置
        WriteFont writeFont = new WriteFont();
        writeFont.setFontHeightInPoints((short) 20);
        headWriteCellStyle.setWriteFont(writeFont);


        //行 设置
        WriteCellStyle rowWriteCellStyle = new WriteCellStyle();

        //行 背景颜色设置
        rowWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        rowWriteCellStyle.setFillBackgroundColor(IndexedColors.CORAL.getIndex());

        //行 字体设置
        WriteFont rowWriteFont = new WriteFont();
        rowWriteFont.setFontHeightInPoints((short) 20);
        rowWriteCellStyle.setWriteFont(rowWriteFont);

        //行 边框设置
        rowWriteCellStyle.setBorderBottom(BorderStyle.DASHED);
        rowWriteCellStyle.setBorderLeft(BorderStyle.DASHED);
        rowWriteCellStyle.setBorderRight(BorderStyle.DASHED);
        rowWriteCellStyle.setBorderTop(BorderStyle.DASHED);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy
                = new HorizontalCellStyleStrategy(headWriteCellStyle, rowWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}
