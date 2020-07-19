package com.weison.io.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@Accessors(chain = true)
public class UserCsv implements Serializable {

    @CsvBindByName(column = "name", required = true)
    private String name;
    @CsvBindByName(column = "address", required = false)
    private String address;
    @CsvBindByName(column = "age", required = false)
    private String age;
    //不需要从csv中取值
    private Integer sex;

}
