package com.tiany.ibator.impl.comment;

import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectCountByParamsComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\r\n";
        ret += "   * 根据params查询"+table.getEntityName()+"记录的条数，params为null表示查询所有 .\r\n";
        ret += "   */\r\n";
        return ret;
    }
}
