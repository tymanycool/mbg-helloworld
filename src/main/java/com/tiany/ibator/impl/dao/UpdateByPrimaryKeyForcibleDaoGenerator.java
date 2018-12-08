package com.tiany.ibator.impl.dao;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyForcibleDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        return "  int updateByPrimaryKeyForcible("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+")";
    }
}
