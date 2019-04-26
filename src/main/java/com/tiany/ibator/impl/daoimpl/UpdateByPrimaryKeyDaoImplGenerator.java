package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\r\n";
        ret += "    return sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKey\","+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
