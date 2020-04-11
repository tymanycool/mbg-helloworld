package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyForcibleDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    return sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKeyForcible\","+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "  }\n\n";
        return ret;
    }
}
