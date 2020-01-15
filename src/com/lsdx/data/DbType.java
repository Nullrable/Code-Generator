package com.lsdx.data;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 15:31
 * @Modified By：
 */
public enum DbType {
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver"),
    ORACLE("oracle", ""),
    SQL_SERVER("sql_server", ""),
    POSTGRE_SQL("postgre_sql", "");

    private final String value;
    private final String driver;

    private DbType(String value, String driver) {
        this.value = value;
        this.driver = driver;
    }

    public String getValue() {
        return this.value;
    }

    public String getDriver() {
        return driver;
    }}