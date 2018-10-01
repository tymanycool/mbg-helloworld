package com.tiany.ibator.impl.dao;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SelectByPrimaryKeyDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        return "\t"+table.getEntityName()+ " selectByPrimaryKey(" + getSimpleClassName((String) MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + " " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + ")";
    }
}
