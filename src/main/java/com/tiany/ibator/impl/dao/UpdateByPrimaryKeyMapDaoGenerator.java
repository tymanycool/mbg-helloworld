package com.tiany.ibator.impl.dao;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Generator;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyMapDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        return "  int updateByPrimaryKey(Map<String, Object> params)";
    }
}
