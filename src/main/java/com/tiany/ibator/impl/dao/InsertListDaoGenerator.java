package com.tiany.ibator.impl.dao;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class InsertListDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "  Object insertList(List<"+table.getEntityName()+"> "+getBeanNameByClassName(table.getEntityName())+"List)";
    }
}
