package com.tiany.ibator.impl.comment;

import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForListComment implements Comment {
    @Override
    public String remark(Table table) {
        String ret ="";
        ret += "\t/**\r\n";
        ret += "\t * 根据params查询"+table.getEntityName()+"的List集合，params为null表示查询所有\r\n";
        ret += "\t */\r\n";
        return ret;
    }
}
