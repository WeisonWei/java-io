package com.weison.io.poi;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelFieldBind {
	int cellIndex();//Excel Index
}
