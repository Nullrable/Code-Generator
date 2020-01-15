package com.lsdx.data;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 11:12
 * @Modified By：
 */
@Data
@ToString
public class EntityField {


    private String columnName;
    private String columnType;

    private String propertyName;
    private String propertyType;
    private String comment;
    private Boolean primaryKey = false;


}
