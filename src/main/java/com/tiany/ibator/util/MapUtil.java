package com.tiany.ibator.util;

import com.tiany.util.ObjectUtil;
import com.tiany.util.StringUtil;
import com.tiany.util.validate.AssertUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, String> copyToMap(Map<Object, Object> source) {
        Map<String, String> retMap = new LinkedHashMap<>();
        if (source != null) {
            Iterator<Map.Entry<Object, Object>> iterator = source.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Object, Object> next = iterator.next();
                Object key = next.getKey();
                Object value = next.getValue();
                retMap.put(String.valueOf(key), String.valueOf(value));
            }
        }
        return retMap;
    }

    public static void ifEmptyPut(Map map, String key, Object value) {
        if (StringUtil.isEmpty(map.get(key))) {
            map.put(key,value);
        }
    }
}
