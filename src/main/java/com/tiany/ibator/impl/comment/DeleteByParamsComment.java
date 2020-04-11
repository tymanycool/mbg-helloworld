package com.tiany.ibator.impl.comment;

import com.tiany.ibator.infs.Comment;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class DeleteByParamsComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\n";
        ret += "   * 根据params删除"+table.getEntityName()+" .\n";
        ret += "   */\n";
        return ret;
    }
}
