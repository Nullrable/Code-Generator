package com.lsdx.data;

import lombok.Data;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 11:00
 * @Modified By：
 */
@Data
public class DataSourceConfig {

    private String url;
    private String userName;
    private String userPassword;
    private DbType dbType ;


}
