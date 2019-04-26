package com.tiany.ibator.impl.dao;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForListNotDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "  List<"+table.getEntityName()+"> selectForListNot(Map<String, Object> params)";
    }
}
