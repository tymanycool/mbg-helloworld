package com.tiany.ibator.impl.dao;

import com.tiany.ibator.inf.DaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectForObjectDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "\t"+table.getEntityName()+" selectForObject("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+")";
    }
}
