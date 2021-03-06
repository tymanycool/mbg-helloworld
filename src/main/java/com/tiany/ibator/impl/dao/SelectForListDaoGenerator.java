package com.tiany.ibator.impl.dao;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForListDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "  List<"+table.getEntityName()+"> selectForList(Map<String, Object> params)";
    }
}
