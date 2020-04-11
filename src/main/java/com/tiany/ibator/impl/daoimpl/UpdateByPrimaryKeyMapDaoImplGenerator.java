package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Generator;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyMapDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    return sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKeyMap\",params);\n";
        ret += "  }\n\n";
        return ret;
    }
}
