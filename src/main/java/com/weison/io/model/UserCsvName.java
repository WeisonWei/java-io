package com.weison.io.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@Accessors(chain = true)
public class UserCsvName implements Serializable {

    @CsvBindByName(column = "name", required = false)
    private String name;
    @CsvBindByName(column = "address", required = false)
    private String address;
    @CsvBindByName(column = "age", required = false)
    private String age;
    //不需要从csv中取值
    private Integer sex;

}
