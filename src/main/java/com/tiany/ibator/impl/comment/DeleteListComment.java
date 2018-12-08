package com.tiany.ibator.impl.comment;

import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class DeleteListComment extends BaseComment implements Comment {
    @Override
    public String remark(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret ="";
        ret += "  /**\r\n";
        ret += "   * 根据主键列表批量删除"+table.getEntityName()+" .\r\n";
        ret += "   */\r\n";
        return ret;
    }
}
