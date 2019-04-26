package com.tiany.ibator.util;

import com.tiany.util.CollectionUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListUtil {
    /**
     * 得到name之后的，偏移offset
     *
     * @param list
     * @param name
     * @param offset
     * @return
     */
    public static String getAfter(List<String> list, String name, int offset) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toUpperCase().equals(name.toUpperCase())) {
                return list.get(i + offset);
            }
        }
        return null;
    }

    /**
     * 得到name之前的，偏移offset
     *
     * @param list
     * @param name
     * @param offset
     * @return
     */
    public static String getPre(List<String> list, String name, int offset) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toUpperCase().equals(name.toUpperCase())) {
                if (i - offset >= 0) {
                    return list.get(i - offset);
                } else {
                    return null;
                }
            }
        }
        return null;
    }


    /**
     * 得到name前一个
     *
     * @param list
     * @param name
     * @return
     */
    public static String getPre(List<String> list, String name) {
        return getPre(list, name, 1);
    }

    /**
     * 得到name后一个
     *
     * @param list
     * @param name
     * @return
     */
    public static String getAfter(List<String> list, String name) {
        return getAfter(list, name, 1);
    }

    /**
     * 集合list里面是否包含map中的任何的一个值(key)
     *
     * @param list
     * @param map
     * @return
     */
    public static boolean contains(List<String> list, Map<String, Object> map) {
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            // 忽略大小写
            if (CollectionUtil.containsIgnoreCase(list, next)) {
                return true;
            }
        }
        return false;
    }
}
