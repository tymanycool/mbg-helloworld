package com.tiany.ibator.impl.dao;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class DeleteByParamsDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "\tint deleteByParams(Map<String,? extends Object> params)";
    }
}