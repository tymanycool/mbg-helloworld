package com.tiany.ibator.util;

import com.tiany.util.StringUtil;
import com.tiany.util.io.PropertiesUtil;

import java.io.File;
import java.io.IOException;

public class SerializableNoUtil {

    /**
     * 得到序列化号，如果有则加载，如果没有则用生成的 .
     *
     * @param key
     * @param defaultNo
     * @return
     */
    public static String getSerializableNo(String key, String defaultNo) {
        try {
            File file = new File("serializable.properties");
            if (!file.exists()) {
                file.createNewFile();
            }
            String property = PropertiesUtil.getProperty(file, key);
            if (StringUtil.isNotEmpty(property)) {
                return property;
            } else {
                return getDefaultNo(key, defaultNo);
            }
        } catch (IOException e) {
        }
        return getDefaultNo(key, defaultNo);
    }

    private static String getDefaultNo(String key, String defaultNo) {
        try {
            File file = new File("serializable.properties");
            if (!file.exists()) {
                file.createNewFile();
            }
            PropertiesUtil.setProperty(file, key, defaultNo);
        } catch (IOException e) {
        }
        return defaultNo;
    }

}
