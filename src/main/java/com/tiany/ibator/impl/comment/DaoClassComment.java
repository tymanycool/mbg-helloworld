package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.DefaultClassComment;
import org.springframework.stereotype.Component;

@Component
public class DaoClassComment extends DefaultClassComment{
    @Override
    protected String getTypeDesc() {
        return "Dao";
    }
}
