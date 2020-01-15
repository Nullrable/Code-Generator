package com.lsdx.data;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-10 14:53
 * @Modified By：
 */
public enum Template {

    Entity("Entity", ""),
    DAO("Dao", "Dao"),
    DTO("DTO", "DTO"),
    SERVICE("Service", "Service"),
    SERVICE_IMPL("ServiceImpl", "ServiceImpl"),
    MODEL_2_DTO_CONVERTER("Model2DTOConverter", "Converter");

    private final String value;
    private final String suffix;

    private Template(String value, String suffix) {

        this.value = value;
        this.suffix = suffix;
    }

    public String getValue() {
        return value;
    }

    public String getSuffix() {
        return suffix;
    }}
