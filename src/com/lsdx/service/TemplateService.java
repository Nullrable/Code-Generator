package com.lsdx.service;

import com.lsdx.data.Template;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-10 14:51
 * @Modified By：
 */
public class TemplateService  {



    public void save(Template template, String value) throws IOException {

        String path = getFilePath(template.getValue());

        // 将格式化后的字符串写入文件
        Writer write = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");

        try {
            write.write(value);
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

    public String read(Template template){

        try {
            String path = getFilePath(template.getValue());


            String html = FileUtils.readFileToString(new File(path), "UTF-8");

            return html;
        }catch (Exception e){

            e.printStackTrace();

        }
        return null;
    }

    public static String getFilePath(String fileName) {

        String path = "resources/template/" + fileName + ".ftl";
        return path;
    }
}
