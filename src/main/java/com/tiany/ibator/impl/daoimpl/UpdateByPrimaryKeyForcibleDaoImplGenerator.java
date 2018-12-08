package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyForcibleDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\r\n";
        ret += "    return sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKeyForcible\","+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
