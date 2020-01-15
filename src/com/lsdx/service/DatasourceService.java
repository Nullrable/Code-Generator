package com.lsdx.service;

import com.lsdx.context.AppContext;
import com.lsdx.data.DataSourceConfig;
import com.lsdx.data.Entity;
import com.lsdx.data.EntityField;
import com.lsdx.data.conveter.ITypeConvert;
import com.lsdx.data.conveter.TypeConveterFactory;
import com.lsdx.excetion.DataSourceConfigNotFoundException;
import com.lsdx.util.AppUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-10 09:46
 * @Modified By：
 */
public class DatasourceService {

    private Connection conn = null;

    private Connection connect(DataSourceConfig cfg)throws Exception  {

        try {
            Class.forName(cfg.getDbType().getDriver());
            Properties props =new Properties();
            props.setProperty("user", cfg.getUserName());
            props.setProperty("password", cfg.getUserPassword() );
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");//mysql设置可以获取tables remarks信息
            conn = DriverManager.getConnection(cfg.getUrl(), props);
            return conn;
        }catch (Exception e){
            throw e;
        }
    }

    //关闭数据库连接
    private void close() {
        if(null != conn) {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ping(DataSourceConfig dataSourceConfig)throws Exception{

        try {
            connect(dataSourceConfig);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            close();
        }

    }

    public Entity excute(String tableName){

        try {

            DataSourceConfig dataSourceConfig = AppContext.DATASOURCE_CONFIG_SERVICE.read();
            if(dataSourceConfig == null){
                throw new DataSourceConfigNotFoundException("Datasource config not found");
            }

            Connection conn = connect(dataSourceConfig);

            ResultSet pkRs = conn.getMetaData().getPrimaryKeys( conn.getCatalog(),null, tableName.toUpperCase());

            Entity entity = new Entity();
            entity.setTableName(tableName);

            String entityName = StringUtils.capitalize(AppUtil.getNoUnderlineStr(tableName));

            entity.setEntityName(entityName);
            entity.setEntityFields(new ArrayList<>());
            entity.setPkFields(new ArrayList<>());

            Set<String> pkSet = new HashSet<>();

            while(pkRs.next()){

                String pkColumnName = pkRs.getString("COLUMN_NAME");//列名

                pkSet.add(pkColumnName);

            }

            ResultSet rsColumns = conn.getMetaData().getColumns(   conn.getCatalog(), getSchema(conn),tableName.toUpperCase(), "%");

            ITypeConvert iTypeConvert = TypeConveterFactory.create(dataSourceConfig.getDbType());

            while(rsColumns.next()){

                String columnName = rsColumns.getString("COLUMN_NAME").toLowerCase();
                String remark = rsColumns.getString("REMARKS");
                String columnType = rsColumns.getString("TYPE_NAME");

                EntityField entityField = new EntityField();

                entityField.setColumnName(columnName);
                entityField.setColumnType(columnType);

                entityField.setPropertyName(AppUtil.getNoUnderlineStr(columnName));
                entityField.setPropertyType(iTypeConvert.processTypeConvert(columnType).getProperType());
                entityField.setComment(StringUtils.trim(remark));

                if( pkSet.contains(columnName)){
                    entityField.setPrimaryKey(true);
                    entity.getPkFields().add(entityField);
                }

                entity.getEntityFields().add(entityField);

            }

            return entity;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }

        return null;


    }

    //其他数据库不需要这个方法 oracle需要
    private  String getSchema(Connection conn) throws Exception {

        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase();

    }

//    public static void main(String args[]){
//
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//
//        Entity entity = dataSourceConfig.ex("wholesale_order_detail");
//
//        Map map = new HashMap();
//        map.put("fileName", entity.getEntityName());
//        map.put("table", entity);
//        map.put("entityPackage", "com.nhsoft.lsd.model");
//        map.put("author", "nhsoft.lsd");
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        map.put("date", dateFormat.format(Calendar.getInstance().getTime()));
//
//        try {
//            AppContext.CODE_GENERATE_SERVICE.generateFile("Entity.ftl", map);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
