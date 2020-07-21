package com.weison.io.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@Accessors(chain = true)
public class UserCsvFiler implements Serializable {

    @CsvBindByPosition(position = 0,required = false)
    private String name;
    @CsvBindByPosition(position = 1,required = false)
    private String address;
    @CsvBindByPosition(position = 2,required = false)
    private String age;

}
