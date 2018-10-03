package com.tiany.ibator.impl.comment;

import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyForcibleComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "\t/**\r\n";
        ret += "\t * 根据主键强制覆盖更新"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }
}