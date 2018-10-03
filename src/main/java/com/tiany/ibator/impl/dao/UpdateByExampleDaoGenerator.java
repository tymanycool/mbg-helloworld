package com.tiany.ibator.impl.dao;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UpdateByExampleDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String entityName = table.getEntityName();
        return "\tint updateByExample("+ entityName +" "+getBeanNameByClassName(entityName)+","+entityName+"Example example)";
    }
}
