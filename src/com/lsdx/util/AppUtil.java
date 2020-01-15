package com.lsdx.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 15:19
 * @Modified By：
 */
public class AppUtil {

    /**
     * 清除字符串下划线
     * @param strKey
     * @return
     */
    public static String getNoUnderlineStr(String strKey){
        if(strKey.indexOf("_")!=-1){
            String[] keyArray = strKey.split("_");
            StringBuffer sb = new StringBuffer();
            boolean flag = false;
            for(String key:keyArray){
                if(flag){
                    //下划线后的首字母大写
                    sb.append(StringUtils.capitalize(key));
                }else{
                    flag=true;
                    sb.append(key);
                }
            }
            strKey = sb.toString();
        }
        return strKey;
    }


    /**
     * 去掉字符串指定的前缀
     * @param str 字符串名称
     * @param prefix 前缀数组
     * @return
     */
    public static String removePrefix(String str, String[] prefix) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            if (null != prefix) {
                String[] prefixArray = prefix;

                for(int i = 0; i < prefix.length; ++i) {
                    String pf = prefixArray[i];
                    if (str.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                        return str.substring(pf.length());
                    }
                }
            }

            return str;
        }
    }

    public static String getOutputPath(String fileName) {
        StringBuilder path = new StringBuilder();
        path.append("resources/temp/" + fileName + ".json");//项目名
        return path.toString();
    }


}
