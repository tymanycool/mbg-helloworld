package com.tiany.ibator.impl.comment;

import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForMapComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\r\n";
        ret += "   * 根据params查询，返回Map类型 .\r\n";
        ret += "   */\r\n";
        return ret;
    }
}
