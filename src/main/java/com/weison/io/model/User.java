package com.weison.io.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@Accessors(chain = true)
public class User implements Serializable {
    private String name;
    private Integer age;
    private String city;
}
