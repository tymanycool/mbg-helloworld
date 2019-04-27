package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.BaseComponent;
import com.tiany.ibator.common.meta.Table;

public class BaseComment extends BaseComponent{
    protected boolean hasPrimatyKey(Table table){
        if(table.getPrimaryKeys().size()>=1){
            return true;
        }
        return false;
    }

    /**
     * 处理注释
     * @param comment
     * @return
     */
    protected String getCommentString(String comment){
        if(comment != null) {
            if (comment.startsWith("'")) {
                comment = comment.substring(1);
            }
            if (comment.endsWith("'")) {
                comment = comment.substring(0, comment.length() - 1);
            }
            comment = comment.replaceAll("\t"," ");
            comment = comment.replaceAll("\r"," ");
            comment = comment.replaceAll("\n"," ");

            comment = comment.replaceAll("\\t"," ");
            comment = comment.replaceAll("\\r"," ");
            comment = comment.replaceAll("\\n"," ");

            comment = comment.replaceAll("\\\\t"," ");
            comment = comment.replaceAll("\\\\r"," ");
            comment = comment.replaceAll("\\\\n"," ");
            comment = comment.replaceAll("[\\s]+"," ");

        }else {
            return "";
        }
        return comment;
    }
}
