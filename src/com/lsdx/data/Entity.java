package com.lsdx.data;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 14:36
 * @Modified By：
 */
@Data
@ToString
public class Entity {

    private String tableName;
    private String entityName;
    private String comment;
    private List<EntityField> entityFields;
    private List<EntityField> pkFields;
}
