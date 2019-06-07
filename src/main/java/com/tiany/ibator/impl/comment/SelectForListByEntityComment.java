package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Comment;
import org.springframework.stereotype.Component;

@Component
public class SelectForListByEntityComment implements Comment {
    @Override
    public String remark(Table table) {
        String beanNameByClassName = getBeanNameByClassName(table.getEntityName());
        String ret = "";
        ret += "  /**\r\n";
        ret += "   * 根据" + beanNameByClassName + "查询" + table.getEntityName() + "的List集合，" + beanNameByClassName + "为null表示查询所有 .\r\n";
        ret += "   */\r\n";
        return ret;
    }

    protected String getBeanNameByClassName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
