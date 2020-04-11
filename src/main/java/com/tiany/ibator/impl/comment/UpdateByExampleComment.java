package com.tiany.ibator.impl.comment;

import com.tiany.ibator.infs.Comment;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByExampleComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "  /**\n";
        ret += "   * 根据example更新"+table.getEntityName()+" .\n";
        ret += "   */\n";
        return ret;
    }
}
