package com.lsdx.service;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Locale;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 14:43
 * @Modified By：
 */
public class CodeGenerateService {

    private Configuration cfg;

    public CodeGenerateService() {

        initConfiguration();
    }

    private Configuration initConfiguration() {
        cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(new File("resources/template/")));
        }catch (Exception e){
            e.printStackTrace();
        }

        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateUpdateDelay(0);
        return cfg;
    }

    public void generateFile(String templateFileName, Map data, String filePath) throws IOException {

        Writer out = null;
        try {

            String fileDir = StringUtils.substringBeforeLast(filePath, "/");

            Template template = cfg.getTemplate(templateFileName);//获取模版信息

            FileUtils.forceMkdir(new File(fileDir + "/"));

            out = new OutputStreamWriter(
                    new FileOutputStream(filePath), "utf-8");//生成的文件编码
            template.process(data, out);//结合模版生成代码文件
            out.flush();
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            if(out != null){
                out.close();
            }
        }


    }


}
