package com.lsdx.service;

import com.lsdx.util.AppUtil;
import com.lsdx.util.MyGson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-10 11:29
 * @Modified By：
 */
public class AuthorService {

    public void save(String author) {
        Writer write = null;
        try {
            String json = MyGson.getInstance().toJson(author);

            String fileNamePath = AppUtil.getOutputPath("Author");//获取生成的文件路径

            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");

            FileUtils.forceMkdir(new File(fileDir));

            // 将格式化后的字符串写入文件
            write = new OutputStreamWriter(new FileOutputStream(fileNamePath), "UTF-8");

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

    public String read() {

        try {

            String fileNamePath = AppUtil.getOutputPath("Author");//获取生成的文件路径

            String fileDirStr = StringUtils.substringBeforeLast(fileNamePath, "/");

            File fileDir = new File(fileDirStr);

            FileUtils.forceMkdir(fileDir);

            File fileJson = new File(fileNamePath);
            if(!fileJson.exists()){
                return null;
            }

            String json = FileUtils.readFileToString(new File(fileNamePath), "UTF-8");

            return MyGson.getInstance().fromJson(json, String.class);

        }catch (Exception e){

            e.printStackTrace();
        }

        return null;
    }
}
