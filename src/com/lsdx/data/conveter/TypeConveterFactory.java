package com.lsdx.data.conveter;

import com.lsdx.data.DbType;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 15:33
 * @Modified By：
 */
public class TypeConveterFactory {

    public static ITypeConvert create(DbType dbType){

        if(dbType.equals(DbType.MYSQL)){

            return new MySqlConveter();

        }

        return null;

    }
}
