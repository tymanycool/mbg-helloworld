package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.meta.Table;

public class BaseComment {
    protected boolean hasPrimatyKey(Table table){
        if(table.getPrimaryKeys().size()>=1){
            return true;
        }
        return false;
    }
}
