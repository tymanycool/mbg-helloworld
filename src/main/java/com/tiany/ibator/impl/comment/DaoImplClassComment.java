package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.DefaultClassComment;
import org.springframework.stereotype.Component;

@Component
public class DaoImplClassComment extends DefaultClassComment{
    @Override
    protected String getTypeDesc() {
        return "DaoImpl";
    }
}
