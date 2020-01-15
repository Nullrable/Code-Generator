package com.lsdx.service;

import com.lsdx.data.DataSourceConfig;
import com.lsdx.util.AppUtil;
import com.lsdx.util.MyGson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 20:26
 * @Modified By：
 */
public class DatasourceConfigService {

    public void save(DataSourceConfig dataSourceConfig)throws IOException {


        String json = MyGson.getInstance().toJson(dataSourceConfig);

        String fileNamePath = AppUtil.getOutputPath("DataSourceConfig");//获取生成的文件路径

        String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");

        FileUtils.forceMkdir(new File(fileDir));

        // 将格式化后的字符串写入文件
        Writer write = new OutputStreamWriter(new FileOutputStream(fileNamePath), "UTF-8");

        try {
            write.write(json);
            write.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(write != null){
                try {
                    write.close();
                }catch (Exception e){
                }
            }
        }
    }

    public DataSourceConfig read() {

        try {

            String fileNamePath = AppUtil.getOutputPath("DataSourceConfig");//获取生成的文件路径

            String fileDirStr = StringUtils.substringBeforeLast(fileNamePath, "/");

            File fileDir = new File(fileDirStr);

            FileUtils.forceMkdir(fileDir);

            File fileJson = new File(fileNamePath);
            if(!fileJson.exists()){
                return null;
            }

            String json = FileUtils.readFileToString(new File(fileNamePath), "UTF-8");

            return MyGson.getInstance().fromJson(json, DataSourceConfig.class);

        }catch (Exception e){

            e.printStackTrace();
        }

        return null;
    }

}
