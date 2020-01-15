package com.lsdx.service;

import com.lsdx.context.AppContext;
import com.lsdx.data.Entity;
import com.lsdx.data.PackageConfig;
import com.lsdx.data.Template;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-10 10:25
 * @Modified By：
 */
public class FileService {

    public void generate(String tableName, String filePath) throws Exception {


        //获取表对应属性
        Entity entity = AppContext.DATASOURCE_SERVICE.excute(tableName);

        //获取类包名
        PackageConfig packageConfig = AppContext.PACKAGE_CONFIG_SERVICE.read();

        Map map = new HashMap();
        map.put("fileName", entity.getEntityName());
        map.put("table", entity);
        map.put("author", "nhsoft.lsd");

        for (Template template : Template.values()) {
            if (StringUtils.isEmpty(packageConfig.getPackage(template))) {
                continue;
            }
            map.put(template.getValue() + "Package", packageConfig.getPackage(template));
        }

        for (Template template : Template.values()) {

            if (StringUtils.isEmpty(packageConfig.getPackage(template))) {
                continue;
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            StringBuffer sbPath = new StringBuffer();
            sbPath.append(filePath);
            sbPath.append("/");
            sbPath.append(template.getValue());
            sbPath.append("/");
            sbPath.append(entity.getEntityName());
            sbPath.append(template.getSuffix());
            sbPath.append(".java");

            map.put("date", dateFormat.format(Calendar.getInstance().getTime()));

            try {
                AppContext.CODE_GENERATE_SERVICE.generateFile(template.getValue() + ".ftl", map, sbPath.toString());
            } catch (Exception e) {
                throw e;
            }
        }


    }

}
