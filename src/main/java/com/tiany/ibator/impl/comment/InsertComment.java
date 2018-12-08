package com.tiany.ibator.impl.comment;

import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class InsertComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\r\n";
        ret += "   * 插入一条新的纪录 .\r\n";
        ret += "   */\r\n";
        return ret;
    }
}
