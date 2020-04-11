package com.tiany.ibator.impl.comment;

import com.tiany.ibator.infs.Comment;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForMapListComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\n";
        ret += "   * 根据params查询Map类型的List集合，params为null表示查询所有 .\n";
        ret += "   */\n";
        return ret;
    }
}
