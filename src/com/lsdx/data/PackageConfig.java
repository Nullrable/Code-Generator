package com.lsdx.data;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 21:05
 * @Modified By：
 */
@Data
public class PackageConfig {

   private Map<Template, String> map = new HashMap<>();

   public String getPackage(Template template ){

        return map.get(template);
    }

    public String addPackage(Template template, String packageStr){

        return map.put(template, packageStr);
    }
}
