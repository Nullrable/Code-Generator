package com.lsdx.data.conveter;

import com.lsdx.data.DbColumnType;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 15:32
 * @Modified By：
 */
public interface ITypeConvert {

    DbColumnType processTypeConvert(String fieldType);
}
