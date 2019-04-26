package com.tiany.ibator.impl.dao;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByExampleDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String entityName = table.getEntityName();
        return "  int updateByExample("+ entityName +" "+getBeanNameByClassName(entityName)+","+entityName+"Example example)";
    }
}
