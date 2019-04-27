package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.DefaultClassComment;
import org.springframework.stereotype.Component;

@Component
public class EntityClassComment extends DefaultClassComment{
    @Override
    protected String getTypeDesc() {
        return "Entity";
    }
}
