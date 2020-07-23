package com.weison.io.model;

import com.weison.io.poi.ExcelFieldBind;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户实体
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserExcelPoi {

    /**
     * 姓名
     */
    @ExcelFieldBind(cellIndex = 0)
    private String name;

    /**
     * 地址
     */
    @ExcelFieldBind(cellIndex = 2)
    private String city;

    /**
     * 年龄
     */
    @ExcelFieldBind(cellIndex = 1)
    private String age;


}
