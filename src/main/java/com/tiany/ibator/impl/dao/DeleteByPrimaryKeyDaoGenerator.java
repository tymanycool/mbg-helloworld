package com.tiany.ibator.impl.dao;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Generator;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeleteByPrimaryKeyDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        return "  int deleteByPrimaryKey(" + getSimpleClassName((String) MapUtil.getIgnoreCase((Map) typesConfig,table.getPrimaryKeys().get(0).getType())) + " " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + ")";
    }
}
