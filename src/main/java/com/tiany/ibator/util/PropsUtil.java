package com.tiany.ibator.util;

import com.tiany.util.io.PropertiesUtil;

import java.io.File;
import java.util.Map;
import java.util.Properties;


public class PropsUtil {
    /**
     * 文件路径找，再从类路径找
     *
     * @param fileName
     * @return
     */
    public static Map<String,Object> loadProps(String fileName) {
        PropertiesUtil.getProperty(fileName, "removePrefix");
        Map result = PropertiesUtil.loadProps(new File(fileName));
        Properties properties = PropertiesUtil.loadProps(fileName);
        if (result == null) {
            result = PropertiesUtil.loadProps(fileName);
        }
        return result;
    }
}
