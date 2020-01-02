package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Comment;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyMapComment extends BaseComment implements Comment {
    @Override
    public String remark(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret ="";
        ret += "  /**\r\n";
        ret += "   * 根据主键选择性更新"+table.getEntityName()+" .\r\n";
        ret += "   */\r\n";
        return ret;
    }
}