package com.tiany.ibator.impl.comment;

import com.tiany.ibator.infs.Comment;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForObjectComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\n";
        ret += "   * 根据对象字段查询"+table.getEntityName()+"对象 .\n";
        ret += "   */\n";
        return ret;
    }
}
