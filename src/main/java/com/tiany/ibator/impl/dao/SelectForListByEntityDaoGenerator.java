package com.tiany.ibator.impl.dao;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Generator;
import org.springframework.stereotype.Component;

@Component
public class SelectForListByEntityDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "  List<" + table.getEntityName() + "> selectForList(" + table.getEntityName() + " " + getBeanNameByClassName(table.getEntityName()) + ")";
    }
}
